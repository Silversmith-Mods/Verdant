package com.ordana.verdant.util;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.verdant.blocks.LeafPileBlock;
import com.ordana.verdant.configs.CommonConfigs;
import com.ordana.verdant.reg.ModBlocks;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesTypeRegistry;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class WeatheringHelper {

    public static void addOptional(ImmutableBiMap.Builder<Block, Block> map,
                                   String moddedId, String moddedId2) {
        var o1 = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse(moddedId));
        var o2 = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse(moddedId2));
        if (o1.isPresent() && o2.isPresent()) {
            map.put(o1.get(), o2.get());
        }
    }

    public static final Supplier<BiMap<Block, Block>> FLOWERY_BLOCKS = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<Block, Block>builder()
                .put(Blocks.FLOWERING_AZALEA, Blocks.AZALEA)
                .put(Blocks.FLOWERING_AZALEA_LEAVES, Blocks.AZALEA_LEAVES)
                .put(ModBlocks.LEAF_PILES.get(LeavesTypeRegistry.getValue(ResourceLocation.parse("flowering_azalea"))),
                        ModBlocks.LEAF_PILES.get(LeavesTypeRegistry.getValue(ResourceLocation.parse("azalea"))));
        addOptional(builder, "quark:flowering_azalea_hedge", "quark:azalea_hedge");
        addOptional(builder, "quark:flowering_azalea_leaf_carpet", "quark:azalea_leaf_carpet");
        return builder.build();
    });


    public static Optional<BlockState> getAzaleaGrowth(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().inverse().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

    public static Optional<BlockState> getAzaleaSheared(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }


    public static final Supplier<Map<Block, LeafPileBlock>> LEAVES_TO_PILES = Suppliers.memoize(() -> {
                var b = ImmutableMap.<Block, LeafPileBlock>builder();
                ModBlocks.LEAF_PILES.forEach((key, value) -> b.put(key.leaves, value));
                return b.build();
            }
    );

    public static Optional<Block> getFallenLeafPile(BlockState state) {
        Block b = state.getBlock();
        if (CommonConfigs.LEAF_PILES_BLACKLIST.get().contains(BuiltInRegistries.BLOCK.getKey(b).toString()))
            return Optional.empty();
        return Optional.ofNullable(LEAVES_TO_PILES.get().get(b));
    }

    @Nullable
    public static Item getBarkToStrip(BlockState normalLog) {
        WoodType woodType = BlockSetAPI.getBlockTypeOf(normalLog.getBlock(), WoodType.class);
        if (woodType != null) {
            boolean log = false;

            String childKey = woodType.getChildKey(normalLog.getBlock());
            if (("log".equals(childKey) && woodType.getChild("stripped_log") != null)
                || ("wood".equals(childKey)  && woodType.getChild("stripped_wood") != null)) {
                log = true;
            }
            if (log) {
                String s = CommonConfigs.GENERIC_BARK.get();
                if (!s.isEmpty()) {
                    var bark = BuiltInRegistries.ITEM.getOptional(ResourceLocation.parse(s));
                    if (bark.isPresent()) {
                        return bark.get();
                    }
                }
                return woodType.getItemOfThis("verdant:bark");
            }
        }
        return null;
    }

    public static Optional<Pair<Item, Block>> getBarkForStrippedLog(BlockState stripped) {
        WoodType woodType = BlockSetAPI.getBlockTypeOf(stripped.getBlock(), WoodType.class);
        if (woodType != null) {
            Object log = null;
            if (woodType.getChild("stripped_log") == stripped.getBlock()) {
                log = woodType.getChild("log");
            } else if (woodType.getChild("stripped_wood") == stripped.getBlock()) {
                log = woodType.getChild("wood");
            }
            if (log instanceof Block unStripped) {
                String s = CommonConfigs.GENERIC_BARK.get();
                if (!s.isEmpty()) {
                    var bark = BuiltInRegistries.ITEM.getOptional(ResourceLocation.parse(s));
                    if (bark.isPresent()) {
                        return Optional.of(Pair.of(bark.get(), unStripped));
                    }
                } else {
                    Item bark = woodType.getItemOfThis("verdant:bark");
                    if (bark != null) return Optional.of(Pair.of(bark, unStripped));
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<Pair<Item, Block>> getWoodFromLog(BlockState sourceLog) {
        WoodType woodType = BlockSetAPI.getBlockTypeOf(sourceLog.getBlock(), WoodType.class);
        if (woodType != null) {
            Object log = null;
            if (woodType.getChild("log") == sourceLog.getBlock()) {
                log = woodType.getChild("wood");
            }
            if (log instanceof Block unStripped) {
                String s = CommonConfigs.GENERIC_BARK.get();
                if (!s.isEmpty()) {
                    var bark = BuiltInRegistries.ITEM.getOptional(ResourceLocation.parse(s));
                    if (bark.isPresent()) {
                        return Optional.of(Pair.of(bark.get(), unStripped));
                    }
                } else {
                    Item bark = woodType.getItemOfThis("verdant:bark");
                    if (bark != null) return Optional.of(Pair.of(bark, unStripped));
                }
            }
        }
        return Optional.empty();
    }

    public static final Supplier<BiMap<DyeColor, Block>> COLOR_TO_PRIMROSE = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<DyeColor, Block>builder()
                .put(DyeColor.RED, ModBlocks.RED_PRIMROSE.get())
                .put(DyeColor.ORANGE, ModBlocks.ORANGE_PRIMROSE.get())
                .put(DyeColor.YELLOW, ModBlocks.YELLOW_PRIMROSE.get())
                .put(DyeColor.LIME, ModBlocks.LIME_PRIMROSE.get())
                .put(DyeColor.GREEN, ModBlocks.GREEN_PRIMROSE.get())
                .put(DyeColor.CYAN, ModBlocks.CYAN_PRIMROSE.get())
                .put(DyeColor.BLUE, ModBlocks.BLUE_PRIMROSE.get())
                .put(DyeColor.LIGHT_BLUE, ModBlocks.LIGHT_BLUE_PRIMROSE.get())
                .put(DyeColor.PURPLE, ModBlocks.PURPLE_PRIMROSE.get())
                .put(DyeColor.MAGENTA, ModBlocks.MAGENTA_PRIMROSE.get())
                .put(DyeColor.PINK, ModBlocks.PINK_PRIMROSE.get())
                .put(DyeColor.WHITE, ModBlocks.WHITE_PRIMROSE.get())
                .put(DyeColor.LIGHT_GRAY, ModBlocks.LIGHT_GRAY_PRIMROSE.get())
                .put(DyeColor.GRAY, ModBlocks.GRAY_PRIMROSE.get())
                .put(DyeColor.BLACK, ModBlocks.BLACK_PRIMROSE.get())
                .put(DyeColor.BROWN, ModBlocks.BROWN_PRIMROSE.get());
        return builder.build();
    });

    public static Optional<Block> getPrimroseColor(DyeColor color) {
        return Optional.ofNullable(COLOR_TO_PRIMROSE.get().get(color));
    }


    public static final Supplier<BiMap<Block, Block>> MOSS_LEVEL_INCREASES = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<Block, Block>builder()
                .put(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE)
                .put(Blocks.COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_STAIRS)
                .put(Blocks.COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB)
                .put(Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL)
                .put(Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS)
                .put(Blocks.STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS)
                .put(Blocks.STONE_BRICK_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB)
                .put(Blocks.STONE_BRICK_WALL, Blocks.MOSSY_STONE_BRICK_WALL);
        return builder.build();
    });

    public static BlockState getMossyBlock(BlockState state) {
        Block block2 = state.getBlock();
        Block block3 = MOSS_LEVEL_INCREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = MOSS_LEVEL_INCREASES.get().get(block2);
        }
        return block2.withPropertiesOf(state);
    }

    public static BlockState getUnaffectedMossBlock(BlockState state) {
        Block block2 = state.getBlock();
        Block block3 = MOSS_LEVEL_INCREASES.get().inverse().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = MOSS_LEVEL_INCREASES.get().inverse().get(block2);
        }
        return block2.withPropertiesOf(state);
    }


    public static void growHangingRoots(ServerLevel level, RandomSource random, BlockPos pos) {
        Direction dir = Direction.values()[1 + random.nextInt(5)].getOpposite();
        BlockPos targetPos = pos.relative(dir);
        BlockState targetState = level.getBlockState(targetPos);
        FluidState fluidState = level.getFluidState(targetPos);
        boolean bl = fluidState.is(Fluids.WATER);
        if (targetState.canBeReplaced()) {
            BlockState newState = dir == Direction.DOWN ?
                Blocks.HANGING_ROOTS.defaultBlockState() :
                ModBlocks.HANGING_ROOTS_WALL.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir);
            level.setBlockAndUpdate(targetPos, newState.setValue(BlockStateProperties.WATERLOGGED, bl));
        }
    }

}
