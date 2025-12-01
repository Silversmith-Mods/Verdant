package com.ordana.verdant.blocks;

import com.ordana.verdant.reg.ModBlocks;
import com.ordana.verdant.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SaguaroArmBlock extends Block implements BonemealableBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final VoxelShape CAP_AABB = Block.box(4.0, 0.0, 4.0, 12.0, 4.0, 12.0);
    private static final VoxelShape STEM_AABB = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    private static final VoxelShape ELBOW_SOUTH_AABB = Shapes.or(Block.box(4.0, 4.0, 0.0, 12.0, 12.0, 12.0), Block.box(4.0, 12.0, 4.0, 12.0, 16.0, 12.0));
    private static final VoxelShape ELBOW_EAST_AABB = Shapes.or(Block.box(0.0, 4.0, 4.0, 12.0, 12.0, 12.0), Block.box(4.0, 12.0, 4.0, 12.0, 16.0, 12.0));
    private static final VoxelShape ELBOW_WEST_AABB = Shapes.or(Block.box(4.0, 4.0, 4.0, 16.0, 12.0, 12.0), Block.box(4.0, 12.0, 4.0, 12.0, 16.0, 12.0));
    private static final VoxelShape ELBOW_NORTH_AABB = Shapes.or(Block.box(4.0, 4.0, 4.0, 12.0, 12.0, 16.0), Block.box(4.0, 12.0, 4.0, 12.0, 16.0, 12.0));

    public SaguaroArmBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> ELBOW_NORTH_AABB;
            case SOUTH -> ELBOW_SOUTH_AABB;
            case EAST -> ELBOW_EAST_AABB;
            case WEST -> ELBOW_WEST_AABB;
            case DOWN -> CAP_AABB;
            case UP -> STEM_AABB;
        };
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }



    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockPos blockPos = pos.above();
        if (level.isEmptyBlock(blockPos)) level.setBlock(blockPos, state.setValue(FACING, Direction.DOWN), 3);
    }


    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var dir = state.getValue(FACING);
        var belowState = level.getBlockState(pos.below());
        if (dir == Direction.DOWN) return belowState.is(ModTags.SAGUARO);
        if (dir == Direction.UP) return belowState.is(ModTags.SAGUARO_PLANTABLE_ON) || belowState.is(ModTags.SAGUARO);
        var relativePos =  pos.relative(dir.getOpposite());
        return level.getBlockState(relativePos).is(ModTags.SAGUARO_PLANTABLE_ON) || level.getBlockState(relativePos).is(ModBlocks.SAGUARO_BLOCK.get());
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        return blockPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockPos.above()).canBeReplaced(context) ? this.defaultBlockState().setValue(FACING, context.getClickedFace()) : null;
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return !useContext.isSecondaryUseActive() && useContext.getItemInHand().is(this.asItem()) && (state.getValue(FACING) == Direction.DOWN) ? true : super.canBeReplaced(state, useContext);
    }



    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        entity.hurt(level.damageSources().cactus(), 1.0F);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return (level.isEmptyBlock(pos.above()) || level.getBlockState(pos.above()).is(ModBlocks.SAGUARO_ARM.get())) && level.isEmptyBlock(pos.above().above());
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        var dir = state.getValue(FACING);
        if (dir == Direction.DOWN) {
            level.setBlockAndUpdate(pos, ModBlocks.SAGUARO_ARM.get().defaultBlockState().setValue(FACING, Direction.UP));
            if (level.isEmptyBlock(pos.above())) level.setBlockAndUpdate(pos.above(), ModBlocks.SAGUARO_ARM.get().defaultBlockState().setValue(FACING, Direction.DOWN));
        }
        else {
            if (level.isEmptyBlock(pos.above()) || level.getBlockState(pos.above()).is(ModBlocks.SAGUARO_ARM.get())) level.setBlockAndUpdate(pos.above(), ModBlocks.SAGUARO_ARM.get().defaultBlockState().setValue(FACING, Direction.UP));
            if (level.isEmptyBlock(pos.above().above())) level.setBlockAndUpdate(pos.above().above(), ModBlocks.SAGUARO_ARM.get().defaultBlockState().setValue(FACING, Direction.DOWN));
        }

    }
}
