package com.ordana.verdant;

import com.ordana.verdant.blocks.LeafPileBlock;
import com.ordana.verdant.dynamicpack.ClientDynamicResourcesHandler;
import com.ordana.verdant.items.FlowerCrownItem;
import com.ordana.verdant.particles.LeafParticle;
import com.ordana.verdant.particles.NormalGravityParticle;
import com.ordana.verdant.reg.ModBlocks;
import com.ordana.verdant.reg.ModEntities;
import com.ordana.verdant.reg.ModItems;
import com.ordana.verdant.reg.ModParticles;
import net.mehvahdjukaar.moonlight.api.client.renderer.FallingBlockRendererGeneric;
import net.mehvahdjukaar.moonlight.api.misc.EventCalled;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.util.math.colors.RGBColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class VerdantClient {

    public static void init() {
        ClientHelper.addClientSetup(VerdantClient::setup);
        RegHelper.registerDynamicResourceProvider(ClientDynamicResourcesHandler.INSTANCE);

        ClientHelper.registerOptionalTexturePack(Verdant.res("visual_waxed_iron_items"), false);

        ClientHelper.addEntityRenderersRegistration(VerdantClient::registerEntityRenderers);
        ClientHelper.addBlockColorsRegistration(VerdantClient::registerBlockColors);
        ClientHelper.addItemColorsRegistration(VerdantClient::registerItemColors);
        ClientHelper.addParticleRegistration(VerdantClient::registerParticles);
    }

    public static void setup() {

        ModBlocks.LEAF_PILES.values().forEach(b -> ClientHelper.registerRenderType(b, RenderType.cutoutMipped()));
        ClientHelper.registerRenderType(ModBlocks.AZALEA_FLOWER_PILE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.HANGING_ROOTS_WALL.get(), RenderType.cutoutMipped());

        ClientHelper.registerRenderType(ModBlocks.IVY.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.MOSS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.WEEDS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.BARLEY.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.ALOE_VERA.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.EDGE_GRASS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.DOGWOOD.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.DUNE_GRASS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.CATTAIL.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.DENSE_GRASS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.BOXWOOD.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.MUSCARI.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.TALL_MUSCARI.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.DUCKWEED.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.CLOVER.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.MONSTERA.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.JUNGLE_FERN.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.RED_HIBISCUS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.PURPLE_HIBISCUS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.BLUE_HIBISCUS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.DAHLIA.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.ANEMONE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POKER.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.SALVIA.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.SHRUB.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.SAGEBRUSH.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.SPOROPHYTE.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.TALL_SPOROPHYTE.get(), RenderType.cutout());

        ClientHelper.registerRenderType(ModBlocks.CONK_FUNGUS.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.STINKHORN_MUSHROOM.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.WHITE_STINKHORN_MUSHROOM.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.PHOSPHOR_FUNGUS.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.MUSHGLOOM.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.MILLY_BUBCAP.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.PORTABELLA.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.CRIMINI.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.BUTTON_MUSHROOM.get(), RenderType.cutout());

        ClientHelper.registerRenderType(ModBlocks.POTTED_STINKHORN_MUSHROOM.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.POTTED_WHITE_STINKHORN_MUSHROOM.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.POTTED_PHOSPHOR_FUNGUS.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.POTTED_MUSHGLOOM.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.POTTED_MILLY_BUBCAP.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.POTTED_PORTABELLA.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.POTTED_CRIMINI.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.POTTED_BUTTON_MUSHROOM.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.POTTED_SPOROPHYTE.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.PHOSPHOR_FUNGUS_BLOCK.get(), RenderType.translucent());

        ClientHelper.registerRenderType(ModBlocks.POTTED_DAHLIA.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_ANEMONE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_POKER.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_SALVIA.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_BOXWOOD.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_SHRUB.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_MUSCARI.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_MONSTERA.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_JUNGLE_FERN.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_RED_HIBISCUS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_PURPLE_HIBISCUS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_BLUE_HIBISCUS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_SAGUARO.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_ALOE_VERA.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_CATTAIL.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_PEONY.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_LILAC.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_ROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_SUNFLOWER.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.POTTED_SEA_PICKLE.get(), RenderType.cutoutMipped());

        ClientHelper.registerRenderType(ModBlocks.RED_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.ORANGE_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.YELLOW_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.LIME_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.GREEN_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.BLUE_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.LIGHT_BLUE_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.CYAN_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.PURPLE_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.MAGENTA_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.PINK_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.WHITE_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.BLACK_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.GRAY_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.LIGHT_GRAY_PRIMROSE.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.BROWN_PRIMROSE.get(), RenderType.cutoutMipped());

        ItemProperties.register(ModItems.FLOWER_CROWN.get(), Verdant.res("supporter"),
                (stack, world, entity, s) -> FlowerCrownItem.getItemTextureIndex(stack));
    }


    private static void registerEntityRenderers(ClientHelper.EntityRendererEvent event) {
        event.register(ModEntities.FALLING_LAYER.get(), FallingBlockRendererGeneric::new);
        event.register(ModEntities.FALLING_PROPAGULE.get(), FallingBlockRendererGeneric::new);
    }

    private static void registerParticles(ClientHelper.ParticleEvent event) {
        event.register(ModParticles.MOSS.get(), NormalGravityParticle.Particle::new);
        event.register(ModParticles.GRAVITY_AZALEA_FLOWER.get(), NormalGravityParticle.Particle::new);

        event.register(ModParticles.AZALEA_FLOWER.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_BEE.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_JAR.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_BOB.get(), LeafParticle.SimpleLeafParticle::new);

        event.register(ModParticles.FLOWER_ACE.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_ARO.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_BI.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_ENBY.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_GAY.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_LESBIAN.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_RAINBOW.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_TRANS.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_GENDERQUEER.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_FLUID.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_INTERSEX.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_PAN.get(), LeafParticle.SimpleLeafParticle::new);

        event.register(ModParticles.FLOWER_FLAX.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_NEKOMASTER.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_AKASHII.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_MOOFELLOW.get(), LeafParticle.SimpleLeafParticle::new);
    }

    @EventCalled
    private static void registerBlockColors(ClientHelper.BlockColorEvent event) {
        ModBlocks.LEAF_PILES.forEach((type, leafPile) ->
                event.register((blockState, blockAndTintGetter, blockPos, i) ->
                        getLeafTypeColor(event, type, blockState, blockAndTintGetter, blockPos, i), leafPile));


        //grass block tint
        event.register((blockState, level, blockPos, i) -> event.getColor(Blocks.GRASS_BLOCK.defaultBlockState(), level, blockPos, i),
                ModBlocks.JUNGLE_FERN.get(),
                ModBlocks.MONSTERA.get(),
                ModBlocks.EDGE_GRASS.get(),
                ModBlocks.DENSE_GRASS.get());

        //spruce leaves tint
        event.register((blockState, level, blockPos, i) -> {
                    if (i == 0) return -1;
                    return event.getColor(Blocks.SPRUCE_LEAVES.defaultBlockState(), level, blockPos, i);
                },
                ModBlocks.POTTED_BOXWOOD.get(),
                ModBlocks.BOXWOOD.get());

        //with exception for particles
        event.register((blockState, level, blockPos, i) -> {
                    if (i == 0) return -1;
                    return event.getColor(Blocks.GRASS_BLOCK.defaultBlockState(), level, blockPos, i);
                },

                ModBlocks.RED_PRIMROSE.get(),
                ModBlocks.ORANGE_PRIMROSE.get(),
                ModBlocks.YELLOW_PRIMROSE.get(),
                ModBlocks.LIME_PRIMROSE.get(),
                ModBlocks.GREEN_PRIMROSE.get(),
                ModBlocks.BLUE_PRIMROSE.get(),
                ModBlocks.LIGHT_BLUE_PRIMROSE.get(),
                ModBlocks.CYAN_PRIMROSE.get(),
                ModBlocks.PURPLE_PRIMROSE.get(),
                ModBlocks.MAGENTA_PRIMROSE.get(),
                ModBlocks.PINK_PRIMROSE.get(),
                ModBlocks.WHITE_PRIMROSE.get(),
                ModBlocks.BLACK_PRIMROSE.get(),
                ModBlocks.GRAY_PRIMROSE.get(),
                ModBlocks.LIGHT_GRAY_PRIMROSE.get(),
                ModBlocks.BROWN_PRIMROSE.get(),
                ModBlocks.SHRUB.get(),
                ModBlocks.BARLEY.get());
    }

    private static int getLeafTypeColor(ClientHelper.BlockColorEvent event, LeavesType type, BlockState state, BlockAndTintGetter level, BlockPos pos, int i) {
        int original = event.getColor(type.leaves.defaultBlockState(), level, pos, i);

        //interpolate between color and brown
        float percentage = state.getValue(LeafPileBlock.AGE) / 10f;
        int brown = 0x7D5212;
        return new RGBColor(original).asLAB().mixWith(new RGBColor(brown).asLAB(), percentage).asRGB().toInt();
    }

    private static void registerItemColors(ClientHelper.ItemColorEvent event) {
        ModItems.LEAF_PILES.forEach((type, leafPile) -> {
            event.register((itemStack, i) -> event.getColor(type.leaves.asItem().getDefaultInstance(), i), leafPile);
        });

        event.register((itemStack, i) -> event.getColor(Items.GRASS_BLOCK.getDefaultInstance(), i),
                ModBlocks.EDGE_GRASS.get(),
                ModBlocks.DENSE_GRASS.get());

    }

}
