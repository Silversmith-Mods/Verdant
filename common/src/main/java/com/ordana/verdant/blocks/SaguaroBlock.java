package com.ordana.verdant.blocks;

import com.ordana.verdant.reg.ModBlocks;
import com.ordana.verdant.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SaguaroBlock extends RotatedPillarBlock implements BonemealableBlock {
    public static final IntegerProperty AGE;
    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    protected static final VoxelShape COLLISION_SHAPE;
    protected static final VoxelShape OUTLINE_SHAPE;

    public SaguaroBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(ATTACHED, true).setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        boolean bl = false;
        for (Direction dir : Direction.values()) {
            if (level.getBlockState(pos.relative(dir)).is(ModTags.SAGUARO_PLANTABLE_ON)) bl = true;
        }

        return bl;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos blockPos = pos.above();
        if (level.isEmptyBlock(blockPos)) {
            int i;
            for(i = 1; level.getBlockState(pos.below(i)).is(this); ++i) {
            }

            if (i < 3) {
                int j = state.getValue(AGE);
                if (j == 15) {
                    level.setBlockAndUpdate(blockPos, this.defaultBlockState());
                    BlockState blockState = state.setValue(AGE, 0);
                    level.setBlock(pos, blockState, 4);
                    level.neighborChanged(blockState, blockPos, this, pos, false);
                } else {
                    level.setBlock(pos, state.setValue(AGE, j + 1), 4);
                }

            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(AXIS).isVertical() && level.getBlockState(pos.above()).is(this)) return state.setValue(ATTACHED, false);
        if (!state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        entity.hurt(level.damageSources().cactus(), 1.0F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, ATTACHED, AXIS);
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }

    static {
        AGE = BlockStateProperties.AGE_15;
        COLLISION_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
        OUTLINE_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        var i = 0;
        for(Direction dir : Direction.Plane.HORIZONTAL) {
            if (!level.isEmptyBlock(pos.relative(dir))) i += 1;
        }

        return i < 4;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        var dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        if (level.isEmptyBlock(pos.relative(dir))) level.setBlockAndUpdate(pos.relative(dir), ModBlocks.SAGUARO_ARM.get().defaultBlockState().setValue(BlockStateProperties.FACING, dir));
        if (level.isEmptyBlock(pos.relative(dir).above())) level.setBlockAndUpdate(pos.relative(dir).above(), ModBlocks.SAGUARO_ARM.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN));

    }
}
