package com.ordana.verdant.reg;

import net.mehvahdjukaar.moonlight.api.platform.RegHelper;

public class ModCompostable {
    public static void register() {
        //ModItems.BARK.values().forEach(b -> RegHelper.registerCompostable(b, 0.8f));
        ModBlocks.LEAF_PILES.values().forEach(b -> RegHelper.registerCompostable(b.asItem(), 0.1f));
        RegHelper.registerCompostable(ModItems.MOSS_CLUMP.get(), 0.5f);
        RegHelper.registerCompostable(ModItems.AZALEA_FLOWERS.get(), 0.5f);
        RegHelper.registerCompostable(ModItems.FLOWER_CROWN.get(), 0.8f);
        RegHelper.registerCompostable(ModBlocks.WEEDS.get().asItem(), 0.3f);
        RegHelper.registerCompostable(ModBlocks.IVY.get().asItem(), 0.3f);
        RegHelper.registerCompostable(ModBlocks.DOGWOOD.get().asItem(), 0.3f);
        RegHelper.registerCompostable(ModBlocks.SAGEBRUSH.get().asItem(), 0.3f);
        RegHelper.registerCompostable(ModBlocks.DUNE_GRASS.get().asItem(), 0.3f);
        RegHelper.registerCompostable(ModBlocks.DUCKWEED.get().asItem(), 0.1f);
        RegHelper.registerCompostable(ModBlocks.CATTAIL.get().asItem(), 0.3f);
        RegHelper.registerCompostable(ModBlocks.MUSCARI.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.ANEMONE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.DAHLIA.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.POKER.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.SALVIA.get().asItem(), 0.4f);
        RegHelper.registerCompostable(ModBlocks.BARLEY.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.ALOE_VERA.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.DENSE_GRASS.get().asItem(), 0.4f);
        RegHelper.registerCompostable(ModBlocks.EDGE_GRASS.get().asItem(), 0.15f);
        RegHelper.registerCompostable(ModBlocks.BOXWOOD.get().asItem(), 0.5f);
        RegHelper.registerCompostable(ModBlocks.SHRUB.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.SHRUB.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.CLOVER.get().asItem(), 0.3f);
        RegHelper.registerCompostable(ModBlocks.RED_HIBISCUS.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.PURPLE_HIBISCUS.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.BLUE_HIBISCUS.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.JUNGLE_FERN.get().asItem(), 0.3f);
        RegHelper.registerCompostable(ModBlocks.MONSTERA.get().asItem(), 0.3f);
        RegHelper.registerCompostable(ModBlocks.SAGUARO_BLOCK.get().asItem(), 0.5f);
        RegHelper.registerCompostable(ModBlocks.SAGUARO_ARM.get().asItem(), 0.3f);

        RegHelper.registerCompostable(ModBlocks.RED_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.ORANGE_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.YELLOW_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.LIME_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.GREEN_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.BLUE_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.LIGHT_BLUE_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.CYAN_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.PURPLE_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.MAGENTA_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.PINK_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.WHITE_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.BLACK_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.GRAY_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.LIGHT_GRAY_PRIMROSE.get().asItem(), 0.2f);
        RegHelper.registerCompostable(ModBlocks.BROWN_PRIMROSE.get().asItem(), 0.2f);


        RegHelper.registerCompostable(ModBlocks.CAVE_MUSHROOM_STEM.get().asItem(), 0.85f);
        RegHelper.registerCompostable(ModBlocks.STINKHORN_MUSHROOM_BLOCK.get().asItem(), 0.85f);
        RegHelper.registerCompostable(ModBlocks.WHITE_STINKHORN_MUSHROOM_BLOCK.get().asItem(), 0.85f);
        RegHelper.registerCompostable(ModBlocks.PHOSPHOR_FUNGUS_BLOCK.get().asItem(), 0.85f);
        RegHelper.registerCompostable(ModBlocks.PHOSPHOR_SHROOMLIGHT.get().asItem(), 0.65f);
        RegHelper.registerCompostable(ModBlocks.MUSHGLOOM_BLOCK.get().asItem(), 0.85f);
        RegHelper.registerCompostable(ModBlocks.CONK_FUNGUS_BLOCK.get().asItem(), 0.85f);
        RegHelper.registerCompostable(ModBlocks.PORTABELLA_BLOCK.get().asItem(), 0.85f);
        RegHelper.registerCompostable(ModBlocks.MILLY_BUBCAP_BLOCK.get().asItem(), 0.85f);
        RegHelper.registerCompostable(ModItems.GRILLED_PORTABELLA.get(), 0.85f);
        RegHelper.registerCompostable(ModBlocks.SPOROPHYTE.get().asItem(), 0.5f);
        RegHelper.registerCompostable(ModBlocks.TALL_SPOROPHYTE.get().asItem(), 0.5f);
        RegHelper.registerCompostable(ModBlocks.PORTABELLA.get().asItem(), 0.85f);
        RegHelper.registerCompostable(ModBlocks.CRIMINI.get().asItem(), 0.65f);
        RegHelper.registerCompostable(ModBlocks.BUTTON_MUSHROOM.get().asItem(), 0.5f);
        RegHelper.registerCompostable(ModBlocks.STINKHORN_MUSHROOM.get().asItem(), 0.65f);
        RegHelper.registerCompostable(ModBlocks.WHITE_STINKHORN_MUSHROOM.get().asItem(), 0.65f);
        RegHelper.registerCompostable(ModBlocks.PHOSPHOR_FUNGUS.get().asItem(), 0.65f);
        RegHelper.registerCompostable(ModBlocks.MUSHGLOOM_BLOCK.get().asItem(), 0.65f);
        RegHelper.registerCompostable(ModBlocks.CONK_FUNGUS.get().asItem(), 0.5f);
        RegHelper.registerCompostable(ModBlocks.MILLY_BUBCAP.get().asItem(), 0.01f);
    }
}
