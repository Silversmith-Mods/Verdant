package com.ordana.verdant.blocks;

import com.ordana.verdant.util.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PrimroseBlock extends Block implements BonemealableBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private final DyeColor color;

    public PrimroseBlock(DyeColor color, BlockBehaviour.Properties properties) {
        super(properties);
        this.color = color;
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(BlockTags.DIRT);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState blockState) {
        boolean bl = false;
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            if (level.getBlockState(pos.relative(dir)).canBeReplaced() && canSurvive(this.defaultBlockState(), level, pos.relative(dir))) {
                bl = true;
            }
        }
        return bl;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        boolean bl = false;
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            if (level.getBlockState(pos.relative(dir)).canBeReplaced() && canSurvive(this.defaultBlockState(), level, pos.relative(dir))) {
                bl = true;
            }
        }

        return bl;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockState flowerState = this.defaultBlockState();
        BlockPos flowerPos = pos.relative(Direction.Plane.HORIZONTAL.getRandomDirection(random));
        for (Direction dir : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
            if (level.getBlockState(pos.relative(dir, 2)).getBlock() instanceof PrimroseBlock mate) {
                if (!mate.getColor().equals(color)) {
                    flowerState = WeatheringHelper.getPrimroseColor(getOffspringColor(level, mate)).get().defaultBlockState();
                    flowerPos = pos.relative(dir);
                }
            }
        }
        if (canSurvive(flowerState, level, flowerPos)) level.setBlockAndUpdate(flowerPos, flowerState);
    }

    public final DyeColor getColor() {
        return color;
    }

    private DyeColor getOffspringColor(ServerLevel level, PrimroseBlock mate) {
        DyeColor dyeColor = color;
        DyeColor dyeColor2 = mate.getColor();
        CraftingInput craftingInput = makeCraftInput(dyeColor, dyeColor2);
        Optional<Item> var10000 = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingInput, level).map((recipeHolder) -> ((CraftingRecipe)recipeHolder.value()).assemble(craftingInput, level.registryAccess())).map(ItemStack::getItem);
        Objects.requireNonNull(DyeItem.class);
        var10000 = var10000.filter(DyeItem.class::isInstance);
        Objects.requireNonNull(DyeItem.class);
        return var10000.map(DyeItem.class::cast).map(DyeItem::getDyeColor).orElseGet(() -> level.random.nextBoolean() ? dyeColor : dyeColor2);

    }

    private static CraftingInput makeCraftInput(DyeColor dyeColor, DyeColor dyeColor2) {
        return CraftingInput.of(2, 1, List.of(new ItemStack(DyeItem.byColor(dyeColor)), new ItemStack(DyeItem.byColor(dyeColor2))));
    }
}
