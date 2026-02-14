package com.ordana.verdant.mixins;

import com.ordana.verdant.Verdant;
import com.ordana.verdant.blocks.LeafPileBlock;
import com.ordana.verdant.network.SendCustomParticlesPacket;
import com.ordana.verdant.util.WeatheringHelper;
import net.mehvahdjukaar.moonlight.api.platform.network.NetworkHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesBlock.class)
public abstract class LeavesMixin extends Block implements BonemealableBlock {

    protected LeavesMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At(value = "INVOKE",
            shift = At.Shift.AFTER,
            target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"))
    public void onRemoved(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random, CallbackInfo ci) {
        decayLeavesPile(blockState, serverLevel, blockPos, random);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState blockState) {
        int i = 0;
        for (var direction : Direction.values()) {
                var targetPos = pos.relative(direction);
                BlockState targetBlock = level.getBlockState(targetPos);
                if (WeatheringHelper.getAzaleaGrowth(targetBlock).isPresent()) i += 1;

        }

        return i > 0;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return state.is(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.relative(direction);
                BlockState targetBlock = level.getBlockState(targetPos);
                WeatheringHelper.getAzaleaGrowth(targetBlock).ifPresent(s ->
                        level.setBlockAndUpdate(targetPos, s)
                );
            }
        }
    }

    private static void decayLeavesPile(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        //this is server side, cant access client configs. Also meet to send color and send particles doesn't support that
        if (!(state.getBlock() instanceof LeavesBlock)) {
            Verdant.LOGGER.error("Some mod tried to call leaves random tick without passing a leaf block blockstate as expected. This should be fixed on their end. Given blockstate : {}", state);
            return;
        }
        if (0.3 > level.random.nextFloat()) {
            Block leafPile = WeatheringHelper.getFallenLeafPile(state).orElse(null);
            if (leafPile == null) return;
            BlockState baseLeaf = leafPile.defaultBlockState();

            level.setBlock(pos, baseLeaf.setValue(LeafPileBlock.LAYERS, Mth.randomBetweenInclusive(level.random, 1, 5)).setValue(LeafPileBlock.AGE, 1), 2);
        }


        BlockPos downPos = pos.below();
        BlockState downState = level.getBlockState(downPos);
        if (!downState.canOcclude() || !downState.isFaceSturdy(level, downPos, Direction.UP)) {

            //packet here
            NetworkHelper.sendToAllClientPlayersInRange(level, pos, 32,
                    new SendCustomParticlesPacket(SendCustomParticlesPacket.EventType.DECAY_LEAVES,
                            pos, Block.getId(state)));
        }

    }
}

