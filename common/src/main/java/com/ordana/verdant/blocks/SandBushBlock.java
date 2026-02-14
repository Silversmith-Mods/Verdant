package com.ordana.verdant.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SandBushBlock extends BushBlock implements BonemealableBlock {
  public SandBushBlock(Properties properties) {
    super(properties);
  }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return simpleCodec(SandBushBlock::new);
    }

    @Override
  protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
    return state.is(BlockTags.DIRT) || state.is(BlockTags.SAND);
  }

  @Override
  public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
    return true;
  }

  @Override
  public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
    return random.nextBoolean();
  }

  @Override
  public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
    popResource(level, pos, new ItemStack(this));
  }
}
