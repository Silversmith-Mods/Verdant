package com.ordana.verdant.reg;

import com.ordana.verdant.Verdant;
import com.ordana.verdant.blocks.*;
import com.ordana.verdant.blocks.fungi.*;
import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks {

    public static void init() {
        BlockSetAPI.addDynamicBlockRegistration(ModBlocks::registerLeafPiles, LeavesType.class);
    }

    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return true;
    }
    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }
    private static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {return false;}

    public static <T extends Block> Supplier<T> regBlock(String name, Supplier<T> block) {
        return RegHelper.registerBlock(Verdant.res(name), block);
    }

    public static <T extends Block> Supplier<T> regWithItem(String name, Supplier<T> blockFactory) {
        Supplier<T> block = regBlock(name, blockFactory);
        regBlockItem(name, block, new Item.Properties());
        return block;
    }

    public static void regBlockItem(String name, Supplier<? extends Block> blockSup, Item.Properties properties) {
        RegHelper.registerItem(Verdant.res(name), () -> new BlockItem(blockSup.get(), properties));
    }

    //predicates
    private static final BlockBehaviour.StateArgumentPredicate<EntityType<?>> CAN_SPAWN_ON_LEAVES = (a, b, c, t) ->
            t == EntityType.OCELOT || t == EntityType.PARROT;

    private static final BlockBehaviour.StatePredicate NEVER = (s, w, p) -> false;


    public static final BlockBehaviour.Properties LEAF_PILE_PROPERTIES = BlockBehaviour.Properties.of()
            .randomTicks().instabreak().sound(SoundType.GRASS)
            .noOcclusion().isValidSpawn(CAN_SPAWN_ON_LEAVES)
            .isSuffocating(NEVER).isViewBlocking(NEVER);


    public static final Map<LeavesType, LeafPileBlock> LEAF_PILES = new LinkedHashMap<>();

    public static final Supplier<LeafPileBlock> AZALEA_FLOWER_PILE = regBlock("azalea_flower_pile", () ->
            new LeafPileBlock(LEAF_PILE_PROPERTIES.sound(SoundType.AZALEA), LeavesTypeRegistry.OAK_TYPE));


    //vegetation

    public static final Supplier<Block> MOSS = regBlock("moss", () ->
            new MossMultifaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).randomTicks().instabreak().sound(SoundType.MOSS_CARPET).noOcclusion().noCollission()));
    public static final Supplier<Block> WEEDS = regWithItem("weeds", () ->
            new WeedsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).noCollission().strength(0.8f).sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> EDGE_GRASS = regWithItem("edge_grass", () ->
            new EdgeGrassBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).noOcclusion().replaceable()));
    public static final Supplier<Block> DOGWOOD = regWithItem("dogwood", () ->
            new DogwoodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).noCollission().strength(0.8f).offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> DUNE_GRASS = regWithItem("dune_grass", () ->
            new SandBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).noCollission().strength(0.8f).offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> BARLEY = regWithItem("barley", () ->
            new ModGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> ALOE_VERA = regWithItem("aloe_vera", () ->
            new SandBushBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> CATTAIL = regWithItem("cattail", () ->
            new CattailBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().instabreak().sound(SoundType.WET_GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> DENSE_GRASS = regWithItem("dense_grass", () ->
            new BigBushBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().strength(0.8f).sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> BOXWOOD = regWithItem("boxwood", () ->
            new BigBushBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().strength(0.8f).sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> SHRUB = regWithItem("shrub", () ->
            new ShrubBushBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().strength(0.8f).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> SAGEBRUSH = regWithItem("sagebrush", () ->
            new SandBushBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().strength(0.8f).offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> CLOVER = regWithItem("clover", () ->
            new CloverBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> MONSTERA = regWithItem("monstera", () ->
            new TallFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> JUNGLE_FERN = regWithItem("jungle_fern", () ->
            new ModGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> SAGUARO_BLOCK = regWithItem("saguaro_block", () ->
            new SaguaroBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.GRASS).strength(0.5f).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> SAGUARO_ARM = regWithItem("saguaro_arm", () ->
            new SaguaroArmBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.GRASS).strength(0.5f).pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> ANEMONE = regWithItem("anemone", () ->
            new FlowerBlock(MobEffects.NIGHT_VISION, 12, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> DAHLIA = regWithItem("dahlia", () ->
            new FlowerBlock(MobEffects.HEALTH_BOOST, 30, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> POKER = regWithItem("poker", () ->
            new FlowerBlock(MobEffects.FIRE_RESISTANCE, 12, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> MUSCARI = regWithItem("muscari", () ->
            new MuscariBlock(MobEffects.POISON, 12, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> TALL_MUSCARI = regBlock("tall_muscari", () ->
            new TallMuscariBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> SALVIA = regWithItem("salvia", () ->
            new TallFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> RED_HIBISCUS = regWithItem("red_hibiscus", () ->
            new FlowerBlock(MobEffects.DAMAGE_BOOST, 7, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> PURPLE_HIBISCUS = regWithItem("purple_hibiscus", () ->
            new FlowerBlock(MobEffects.DAMAGE_BOOST, 7, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> BLUE_HIBISCUS = regWithItem("blue_hibiscus", () ->
            new FlowerBlock(MobEffects.DAMAGE_BOOST, 7, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> POTTED_ANEMONE = regBlock("potted_anemone",
            () -> new FlowerPotBlock(ANEMONE.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_DAHLIA = regBlock("potted_dahlia",
            () -> new FlowerPotBlock(DAHLIA.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_POKER = regBlock("potted_poker",
            () -> new FlowerPotBlock(POKER.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_SALVIA = regBlock("potted_salvia",
            () -> new FlowerPotBlock(SALVIA.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_MUSCARI = regBlock("potted_muscari",
            () -> new FlowerPotBlock(MUSCARI.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_RED_HIBISCUS = regBlock("potted_red_hibiscus",
            () -> new FlowerPotBlock(RED_HIBISCUS.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_PURPLE_HIBISCUS = regBlock("potted_purple_hibiscus",
            () -> new FlowerPotBlock(PURPLE_HIBISCUS.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_BLUE_HIBISCUS = regBlock("potted_blue_hibiscus",
            () -> new FlowerPotBlock(BLUE_HIBISCUS.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_SAGUARO = regBlock("potted_saguaro",
            () -> new FlowerPotBlock(SAGUARO_ARM.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_JUNGLE_FERN = regBlock("potted_jungle_fern",
            () -> new FlowerPotBlock(JUNGLE_FERN.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_MONSTERA = regBlock("potted_monstera",
            () -> new FlowerPotBlock(MONSTERA.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_BOXWOOD = regBlock("potted_boxwood",
            () -> new FlowerPotBlock(BOXWOOD.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_SHRUB = regBlock("potted_shrub",
            () -> new FlowerPotBlock(SHRUB.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_ALOE_VERA = regBlock("potted_aloe_vera",
            () -> new FlowerPotBlock(ALOE_VERA.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_CATTAIL = regBlock("potted_cattail",
            () -> new FlowerPotBlock(CATTAIL.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_PEONY = regBlock("potted_peony",
            () -> new FlowerPotBlock(Blocks.PEONY, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_LILAC = regBlock("potted_lilac",
            () -> new FlowerPotBlock(Blocks.LILAC, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_ROSE = regBlock("potted_rose",
            () -> new FlowerPotBlock(Blocks.ROSE_BUSH, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_SUNFLOWER = regBlock("potted_sunflower",
            () -> new FlowerPotBlock(Blocks.SUNFLOWER, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));
    public static final Supplier<Block> POTTED_SEA_PICKLE = regBlock("potted_sea_pickle",
            () -> new FlowerPotBlock(Blocks.SEA_PICKLE, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS)));



    public static final Supplier<Block> RED_PRIMROSE = regWithItem("red_primrose", () ->
                new PrimroseBlock(DyeColor.RED, BlockBehaviour.Properties.of().mapColor(DyeColor.RED).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> ORANGE_PRIMROSE = regWithItem("orange_primrose", () ->
            new PrimroseBlock(DyeColor.ORANGE, BlockBehaviour.Properties.of().mapColor(DyeColor.ORANGE).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> YELLOW_PRIMROSE = regWithItem("yellow_primrose", () ->
            new PrimroseBlock(DyeColor.YELLOW, BlockBehaviour.Properties.of().mapColor(DyeColor.YELLOW).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> LIME_PRIMROSE = regWithItem("lime_primrose", () ->
            new PrimroseBlock(DyeColor.LIME, BlockBehaviour.Properties.of().mapColor(DyeColor.LIME).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> GREEN_PRIMROSE = regWithItem("green_primrose", () ->
            new PrimroseBlock(DyeColor.GREEN, BlockBehaviour.Properties.of().mapColor(DyeColor.GREEN).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> CYAN_PRIMROSE = regWithItem("cyan_primrose", () ->
            new PrimroseBlock(DyeColor.CYAN, BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> BLUE_PRIMROSE = regWithItem("blue_primrose", () ->
            new PrimroseBlock(DyeColor.BLUE, BlockBehaviour.Properties.of().mapColor(DyeColor.BLUE).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> LIGHT_BLUE_PRIMROSE = regWithItem("light_blue_primrose", () ->
            new PrimroseBlock(DyeColor.LIGHT_BLUE, BlockBehaviour.Properties.of().mapColor(DyeColor.LIGHT_BLUE).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> PURPLE_PRIMROSE = regWithItem("purple_primrose", () ->
            new PrimroseBlock(DyeColor.PURPLE, BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> MAGENTA_PRIMROSE = regWithItem("magenta_primrose", () ->
            new PrimroseBlock(DyeColor.MAGENTA, BlockBehaviour.Properties.of().mapColor(DyeColor.MAGENTA).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> PINK_PRIMROSE = regWithItem("pink_primrose", () ->
            new PrimroseBlock(DyeColor.PINK, BlockBehaviour.Properties.of().mapColor(DyeColor.PINK).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> WHITE_PRIMROSE = regWithItem("white_primrose", () ->
            new PrimroseBlock(DyeColor.WHITE, BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> BLACK_PRIMROSE = regWithItem("black_primrose", () ->
            new PrimroseBlock(DyeColor.BLACK, BlockBehaviour.Properties.of().mapColor(DyeColor.BLACK).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> GRAY_PRIMROSE = regWithItem("gray_primrose", () ->
            new PrimroseBlock(DyeColor.GRAY, BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> LIGHT_GRAY_PRIMROSE = regWithItem("light_gray_primrose", () ->
            new PrimroseBlock(DyeColor.LIGHT_GRAY, BlockBehaviour.Properties.of().mapColor(DyeColor.LIGHT_GRAY).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> BROWN_PRIMROSE = regWithItem("brown_primrose", () ->
            new PrimroseBlock(DyeColor.BROWN, BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));


    public static final Supplier<Block> DUCKWEED = regBlock("duckweed", () ->
            new DuckweedBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));

    public static final Supplier<Block> HANGING_ROOTS_WALL = regBlock("hanging_roots_wall", () ->
            new WallRootsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HANGING_ROOTS)));
    public static final Supplier<IvyBlock> IVY = regWithItem("ivy", () ->
            new IvyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE).noCollission().strength(0.2f)
                    .sound(SoundType.AZALEA_LEAVES)));


    public static final Supplier<Block> SPOROPHYTE = regWithItem("sporophyte", () ->
            new SporophyteBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).noCollission().instabreak().sound(SoundType.MOSS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final Supplier<Block> TALL_SPOROPHYTE = regWithItem("tall_sporophyte", () ->
            new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).noCollission().instabreak().sound(SoundType.MOSS).offsetType(BlockBehaviour.OffsetType.XZ)));


    //fungi
    public static final Supplier<Block> CONK_FUNGUS = regWithItem("conk_fungus", () ->
            new ConkFungusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.FUNGUS)));
    public static final Supplier<Block> PORTABELLA = regBlock("portabella", () ->
            new GrowableMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).noCollission().randomTicks().instabreak().sound(SoundType.FUNGUS).offsetType(BlockBehaviour.OffsetType.XZ).hasPostProcess(ModBlocks::always)));
    public static final Supplier<Block> CRIMINI = regBlock("crimini", () ->
            new ModMushroomBlock(BlockBehaviour.Properties.ofFullCopy(PORTABELLA.get())));
    public static final Supplier<Block> BUTTON_MUSHROOM = regBlock("button_mushroom", () ->
            new ModMushroomBlock(BlockBehaviour.Properties.ofFullCopy(PORTABELLA.get())));
    public static final Supplier<Block> STINKHORN_MUSHROOM = regWithItem("stinkhorn_mushroom", () ->
            new GrowableMushroomBlock(BlockBehaviour.Properties.ofFullCopy(PORTABELLA.get()).hasPostProcess(ModBlocks::always)));
    public static final Supplier<Block> WHITE_STINKHORN_MUSHROOM = regWithItem("white_stinkhorn_mushroom", () ->
            new GrowableMushroomBlock(BlockBehaviour.Properties.ofFullCopy(PORTABELLA.get()).hasPostProcess(ModBlocks::always)));
    public static final Supplier<Block> PHOSPHOR_FUNGUS = regWithItem("phosphor_fungus", () ->
            new FloorAndSidesMushroomBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.CONK_FUNGUS.get()).emissiveRendering(ModBlocks::always).lightLevel((blockStatex) -> 3)));
    public static final Supplier<Block> MUSHGLOOM = regWithItem("mushgloom", () ->
            new FloorAndSidesMushroomBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.CONK_FUNGUS.get()).emissiveRendering(ModBlocks::always).lightLevel((blockStatex) -> 1)));
    public static final Supplier<Block> MILLY_BUBCAP = regWithItem("milly_bubcap", () ->
            new MillyBubcapMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).noCollission().instabreak().sound(SoundType.FUNGUS).offsetType(BlockBehaviour.OffsetType.XZ)));

    public static final Supplier<Block> POTTED_PORTABELLA = regBlock("potted_portabella", () ->
            new FlowerPotBlock(PORTABELLA.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final Supplier<Block> POTTED_CRIMINI = regBlock("potted_crimini", () ->
            new FlowerPotBlock(CRIMINI.get(), BlockBehaviour.Properties.ofFullCopy(POTTED_PORTABELLA.get())));
    public static final Supplier<Block> POTTED_BUTTON_MUSHROOM = regBlock("potted_button_mushroom", () ->
            new FlowerPotBlock(BUTTON_MUSHROOM.get(), BlockBehaviour.Properties.ofFullCopy(POTTED_PORTABELLA.get())));
    public static final Supplier<Block> POTTED_STINKHORN_MUSHROOM = regBlock("potted_stinkhorn_mushroom", () ->
            new FlowerPotBlock(STINKHORN_MUSHROOM.get(), BlockBehaviour.Properties.ofFullCopy(POTTED_PORTABELLA.get())));
    public static final Supplier<Block> POTTED_WHITE_STINKHORN_MUSHROOM = regBlock("potted_white_stinkhorn_mushroom", () ->
            new FlowerPotBlock(WHITE_STINKHORN_MUSHROOM.get(), BlockBehaviour.Properties.ofFullCopy(POTTED_PORTABELLA.get())));
    public static final Supplier<Block> POTTED_PHOSPHOR_FUNGUS = regBlock("potted_phosphor_fungus", () ->
            new FlowerPotBlock(PHOSPHOR_FUNGUS.get(), BlockBehaviour.Properties.ofFullCopy(POTTED_PORTABELLA.get()).emissiveRendering(ModBlocks::always).lightLevel((blockStatex) -> 3)));
    public static final Supplier<Block> POTTED_MUSHGLOOM = regBlock("potted_mushgloom", () ->
            new FlowerPotBlock(MUSHGLOOM.get(), BlockBehaviour.Properties.ofFullCopy(POTTED_PORTABELLA.get()).emissiveRendering(ModBlocks::always).lightLevel((blockStatex) -> 1)));
    public static final Supplier<Block> POTTED_MILLY_BUBCAP = regBlock("potted_milly_bubcap", () ->
            new FlowerPotBlock(MILLY_BUBCAP.get(), BlockBehaviour.Properties.ofFullCopy(POTTED_PORTABELLA.get())));
    public static final Supplier<Block> POTTED_SPOROPHYTE = regBlock("potted_sporophyte", () ->
            new FlowerPotBlock(SPOROPHYTE.get(), BlockBehaviour.Properties.ofFullCopy(POTTED_PORTABELLA.get())));

    public static final Supplier<Block> CONK_FUNGUS_BLOCK = regWithItem("conk_fungus_block", () ->
            new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.DIRT).strength(0.2F).sound(SoundType.STEM)));
    public static final Supplier<Block> PORTABELLA_BLOCK = regWithItem("portabella_block", () ->
            new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.DIRT).strength(0.2F).sound(SoundType.WOOD)));
    public static final Supplier<Block> STINKHORN_MUSHROOM_BLOCK = regWithItem("stinkhorn_mushroom_block", () ->
            new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.TERRACOTTA_BLACK).strength(0.2F).sound(SoundType.WOOD)));
    public static final Supplier<Block> WHITE_STINKHORN_MUSHROOM_BLOCK = regWithItem("white_stinkhorn_mushroom_block", () ->
            new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.SAND).strength(0.2F).sound(SoundType.WOOD)));
    public static final Supplier<Block> MILLY_BUBCAP_BLOCK = regWithItem("milly_bubcap_block", () ->
            new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.TERRACOTTA_BROWN).strength(0.2F).sound(SoundType.WOOD)));
    public static final Supplier<Block> PHOSPHOR_FUNGUS_BLOCK = regWithItem("phosphor_fungus_block", () ->
            new PhosphorFungusBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(0.2F).sound(SoundType.WOOD).emissiveRendering(ModBlocks::always).noOcclusion().isValidSpawn(ModBlocks::never).isRedstoneConductor(ModBlocks::never).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never).emissiveRendering(ModBlocks::always).lightLevel((blockStatex) -> 1)));


    public static final Supplier<Block> PHOSPHOR_SHROOMLIGHT = regWithItem("phosphor_shroomlight", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SHROOMLIGHT).mapColor(MapColor.COLOR_CYAN).sound(SoundType.SHROOMLIGHT).emissiveRendering(ModBlocks::always).lightLevel((blockStatex) -> 8)));
    public static final Supplier<Block> MUSHGLOOM_BLOCK = regWithItem("mushgloom_block", () ->
            new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.TERRACOTTA_BLUE).strength(0.2F).sound(SoundType.WOOD).emissiveRendering(ModBlocks::always).lightLevel((blockStatex) -> 1)));
    public static final Supplier<Block> CAVE_MUSHROOM_STEM = regWithItem("cave_mushroom_stem", () ->
            new HugeMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM).mapColor(MapColor.TERRACOTTA_GRAY).strength(0.2F).sound(SoundType.WOOD)));




    private static void registerLeafPiles(Registrator<Block> event, Collection<LeavesType> leavesTypes) {
        for (LeavesType type : leavesTypes) {
            String name = type.getVariantId("leaf_pile", false);

            LeafPileBlock block = new LeafPileBlock(LEAF_PILE_PROPERTIES, type);
            event.register(Verdant.res(name), block);

            LEAF_PILES.put(type, block);
            type.addChild("verdant:leaf_pile", block);
        }
    }
}