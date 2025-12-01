package com.ordana.verdant.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;

public class ShrubBushBlock extends BushBlock implements BonemealableBlock {
  public ShrubBushBlock(Properties properties) {
    super(properties);
  }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return simpleCodec(ShrubBushBlock::new);
    }

    @Override
  protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
    return state.isFaceSturdy(level, pos, Direction.DOWN, SupportType.CENTER);
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
    if (random.nextFloat() > 0.7f) popResource(level, pos, new ItemStack(this));
  }
}
