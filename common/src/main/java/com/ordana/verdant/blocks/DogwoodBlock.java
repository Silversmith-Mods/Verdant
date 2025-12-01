package com.ordana.verdant.blocks;

import com.mojang.serialization.MapCodec;
import com.ordana.verdant.Verdant;
import com.ordana.verdant.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class DogwoodBlock extends BushBlock implements BonemealableBlock {
  public DogwoodBlock(Properties properties) {
    super(properties);
  }

   @Override
   protected MapCodec<? extends BushBlock> codec() {
       return simpleCodec(DogwoodBlock::new);
   }

   @Override
   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
     return state.is(BlockTags.DIRT) || state.is(Blocks.SNOW_BLOCK);
   }

   @Override
   public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
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
