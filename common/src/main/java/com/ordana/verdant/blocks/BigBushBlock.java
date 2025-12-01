package com.ordana.verdant.blocks;

import com.mojang.serialization.MapCodec;
import com.ordana.verdant.Verdant;
import com.ordana.verdant.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.MossBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class BigBushBlock extends BushBlock implements BonemealableBlock {
    public BigBushBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return simpleCodec(BigBushBlock::new);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.is(ModBlocks.DENSE_GRASS.get())) growPatch(level, pos, state, random);
        else popResource(level, pos, new ItemStack(this));
    }

    public boolean growPatch(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        level.removeBlock(pos, false);
        Holder<ConfiguredFeature<?, ?>> feature = (level.registryAccess().registry(Registries.CONFIGURED_FEATURE).get().getHolder(
                ResourceKey.create(Registries.CONFIGURED_FEATURE, Verdant.res("dense_grass_patch"))).get());

        if ((feature.value()).place(level, level.getChunkSource().getGenerator(), random, pos)) {
            return true;
        } else {
            level.setBlock(pos, state, 3);
            return false;
        }
    }
}
