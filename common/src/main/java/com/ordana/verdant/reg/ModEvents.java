package com.ordana.verdant.reg;

import com.ordana.verdant.configs.CommonConfigs;
import com.ordana.verdant.util.WeatheringHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;


public class ModEvents {

    @FunctionalInterface
    public interface InteractionEvent {
        InteractionResult run(Item i, ItemStack stack,
                              BlockPos pos,
                              BlockState state,
                              Player player, Level level,
                              InteractionHand hand,
                              BlockHitResult hit);
    }

    private static final List<InteractionEvent> EVENTS = new ArrayList<>();

    static {
        EVENTS.add(ModEvents::azaleaShearing);
        EVENTS.add(ModEvents::pseudoBonemeal);
        EVENTS.add(ModEvents::mossShearing);
        //EVENTS.add(ModEvents::axeStripping);
        //EVENTS.add(ModEvents::barkRepairing);
    }

    public static InteractionResult onBlockCLicked(ItemStack stack, Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.isEmpty()) return InteractionResult.PASS;
        Item i = stack.getItem();
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        for (var event : EVENTS) {
            var result = event.run(i, stack, pos, state, player, level, hand, hitResult);
            if (result != InteractionResult.PASS) return result;
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult azaleaShearing(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                    Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item instanceof ShearsItem) {
            //azalea shearing
            BlockState newState;
            newState = WeatheringHelper.getAzaleaSheared(state).orElse(null);
            if (newState != null) {
                if (level.isClientSide) {
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.GRAVITY_AZALEA_FLOWER.get(), UniformInt.of(4, 6));
                } else {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                        Block.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(ModItems.AZALEA_FLOWERS.get()));
                    }
                }
            }

            if (newState != null) {
                level.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlockAndUpdate(pos, newState);

                stack.hurtAndBreak(1, player, Player.getSlotForHand(hand));

                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger( serverPlayer, pos, stack);
                    level.gameEvent(player, GameEvent.SHEAR, pos);
                    player.awardStat(Stats.ITEM_USED.get(item));
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult pseudoBonemeal(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                    Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (!item.getDefaultInstance().is(Items.BONE_MEAL) || !state.is(ModTags.MOSSY)) return InteractionResult.PASS;

        boolean bl = false;
        var direction = Direction.getRandom(level.random);
        var targetPos = pos.relative(direction);
        var targetState = level.getBlockState(targetPos);
        BlockState mossyState = WeatheringHelper.getMossyBlock(targetState);

        if (!level.isClientSide()) {
            if (mossyState != targetState && level.random.nextBoolean()) {
                bl = true;
                level.setBlockAndUpdate(targetPos, mossyState);
            }
        }

        if (level.isClientSide) {
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.HAPPY_VILLAGER, UniformInt.of(4, 6));
            level.playSound(player, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
        if (player instanceof ServerPlayer serverPlayer) {
            if (bl) if (!player.getAbilities().instabuild) stack.shrink(1);
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
            level.gameEvent(player, GameEvent.ITEM_INTERACT_START, pos);
            player.awardStat(Stats.ITEM_USED.get(item));

        }
        return InteractionResult.sidedSuccess(level.isClientSide);

    }

    private static InteractionResult mossShearing(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                  Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item instanceof ShearsItem) {

            BlockState newState;
            newState = WeatheringHelper.getUnaffectedMossBlock(state);
            if (newState != state) {
                if (level.isClientSide) {
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.MOSS.get(), UniformInt.of(3, 5));
                } else {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                        Block.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(ModItems.MOSS_CLUMP.get()));
                    }
                }
            } else newState = null;

            //common logic
            if (newState != null) {
                level.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlockAndUpdate(pos, newState);

                stack.hurtAndBreak(1, player, Player.getSlotForHand(hand));

                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger( serverPlayer, pos, stack);
                    level.gameEvent(player, GameEvent.SHEAR, pos);
                    player.awardStat(Stats.ITEM_USED.get(item));
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    /*
    private static InteractionResult axeStripping(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                  Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item instanceof AxeItem) {
            Item bark = WeatheringHelper.getBarkToStrip(state);
            if (bark != null) {
                if (level.isClientSide) {
                    level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                    var barkParticle = new BlockParticleOption(ParticleTypes.BLOCK, state);
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, barkParticle, UniformInt.of(3, 5));
                } else {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    stack.hurtAndBreak(1, player, Player.getSlotForHand(hand));
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                        Block.popResourceFromFace(level, pos, hitResult.getDirection(), bark.getDefaultInstance());
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult barkRepairing(Item item, ItemStack stack, BlockPos pos, BlockState state, Player
            player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(ModTags.BARK)) {
            Pair<Item, Block> fixedLog = WeatheringHelper.getBarkForStrippedLog(state).orElse(null);
            Pair<Item, Block> woodFromLog = WeatheringHelper.getWoodFromLog(state).orElse(null);

            if (fixedLog != null && stack.getItem() == fixedLog.getFirst()) {
                BlockState newBlock = fixedLog.getSecond().withPropertiesOf(state);
                if (level.isClientSide) {
                    level.playSound(player, pos, newBlock.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
                } else {
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    level.setBlockAndUpdate(pos, newBlock);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            else if (woodFromLog != null && stack.getItem() == woodFromLog.getFirst()) {
                if (hitResult.getDirection().getAxis() == state.getValue(BlockStateProperties.AXIS)) {
                    BlockState newBlock = woodFromLog.getSecond().withPropertiesOf(state);
                    if (level.isClientSide) {
                        level.playSound(player, pos, newBlock.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    } else {
                        if (!player.getAbilities().instabuild) stack.shrink(1);
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                        level.setBlockAndUpdate(pos, newBlock);
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);

                }
            }
        }
        return InteractionResult.PASS;
    }

     */
}