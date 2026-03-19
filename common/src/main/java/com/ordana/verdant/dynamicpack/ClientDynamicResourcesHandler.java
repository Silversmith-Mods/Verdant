package com.ordana.verdant.dynamicpack;

import com.ordana.verdant.Verdant;
import com.ordana.verdant.reg.ModBlocks;
import net.mehvahdjukaar.moonlight.api.events.AfterLanguageLoadEvent;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.resources.RPUtils;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.mehvahdjukaar.moonlight.api.resources.assets.LangBuilder;
import net.mehvahdjukaar.moonlight.api.resources.pack.*;
import net.mehvahdjukaar.moonlight.api.resources.textures.Palette;
import net.mehvahdjukaar.moonlight.api.resources.textures.TextureImage;
import net.mehvahdjukaar.moonlight.api.resources.textures.TextureOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ClientDynamicResourcesHandler extends DynamicClientResourceProvider {

    public static final ClientDynamicResourcesHandler INSTANCE = new ClientDynamicResourcesHandler();

    public ClientDynamicResourcesHandler() {
        super(Verdant.res("generated_pack"), PackGenerationStrategy.CACHED);
    }

    public void addLeafPilesModel(StaticResource resource, String id, ResourceLocation texturePath, ResourceSink dynamicPack) {
        String string = new String(resource.data, StandardCharsets.UTF_8);

        String path = resource.location.getPath().replace("oak_leaf_pile", id);

        string = string.replace("verdant:block/light_oak_leaves", texturePath.toString());
        string = string.replace("verdant:block/medium_oak_leaves", texturePath.toString());
        string = string.replace("heavy_oak_leaves", id.replace("/", "/heavy_"));

        //adds modified under my namespace
        ResourceLocation newRes = Verdant.res(path);
        dynamicPack.addBytes(newRes, string.getBytes(), ResType.GENERIC);
    }


    //-------------resource pack dependant textures-------------

    @Override
    protected void regenerateDynamicAssets(Consumer<ResourceGenTask> consumer) {

        consumer.accept((manager, dynamicPack) -> {


        //------leaf piles------
        {

            StaticResource lpBlockState = StaticResource.getOrLog(manager,
                    ResType.BLOCKSTATES.getPath(Verdant.res("oak_leaf_pile")));
            StaticResource lpModel1 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(Verdant.res("leaf_piles/oak_leaf_pile_height1")));
            StaticResource lpModel2 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(Verdant.res("leaf_piles/oak_leaf_pile_height2")));
            StaticResource lpModel4 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(Verdant.res("leaf_piles/oak_leaf_pile_height4")));
            StaticResource lpModel6 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(Verdant.res("leaf_piles/oak_leaf_pile_height6")));
            StaticResource lpModel8 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(Verdant.res("leaf_piles/oak_leaf_pile_height8")));
            StaticResource lpModel10 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(Verdant.res("leaf_piles/oak_leaf_pile_height10")));
            StaticResource lpModel12 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(Verdant.res("leaf_piles/oak_leaf_pile_height12")));
            StaticResource lpModel14 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(Verdant.res("leaf_piles/oak_leaf_pile_height14")));
            StaticResource lpModel16 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(Verdant.res("leaf_piles/oak_leaf_pile_height16")));

            StaticResource lpItemModel = StaticResource.getOrLog(manager,
                    ResType.ITEM_MODELS.getPath(Verdant.res("oak_leaf_pile")));

            ModBlocks.LEAF_PILES.forEach((leafType, pile) -> {
                if (leafType.isVanilla() && PlatHelper.isDev()) return;

                String path = leafType.getNamespace() + "/" + leafType.getTypeName();
                String id = path + "_leaf_pile";

                try {
                    dynamicPack.addSimilarJsonResource(manager, lpBlockState, "oak_leaf_pile", id);
                } catch (Exception ex) {
                    Verdant.LOGGER.error("Failed to generate Leaf Pile blockstate definition for {} : {}", pile, ex);
                }

                try {
                    dynamicPack.addSimilarJsonResource(manager, lpItemModel, "oak_leaf_pile", id);
                } catch (Exception ex) {
                    Verdant.LOGGER.error("Failed to generate Leaf Pile item model for {} : {}", pile, ex);
                }

                //models
                try {
                    ResourceLocation leavesTexture;
                    try {
                        leavesTexture = RPUtils.findFirstBlockTextureLocation(manager, leafType.leaves, LOOKS_LIKE_LEAF_TEXTURE);
                    } catch (Exception exception) {
                        Verdant.LOGGER.warn("Failed to find texture for Leaf Pile {}, using oak one instead", pile);
                        leavesTexture = RPUtils.findFirstBlockTextureLocation(manager, Blocks.OAK_LEAVES, (s) -> true);
                    }
                    addLeafPilesModel(Objects.requireNonNull(lpModel1), id, leavesTexture, dynamicPack);
                    addLeafPilesModel(Objects.requireNonNull(lpModel2), id, leavesTexture, dynamicPack);
                    addLeafPilesModel(Objects.requireNonNull(lpModel4), id, leavesTexture, dynamicPack);
                    addLeafPilesModel(Objects.requireNonNull(lpModel6), id, leavesTexture, dynamicPack);
                    addLeafPilesModel(Objects.requireNonNull(lpModel8), id, leavesTexture, dynamicPack);
                    addLeafPilesModel(Objects.requireNonNull(lpModel10), id, leavesTexture, dynamicPack);
                    addLeafPilesModel(Objects.requireNonNull(lpModel12), id, leavesTexture, dynamicPack);
                    addLeafPilesModel(Objects.requireNonNull(lpModel14), id, leavesTexture, dynamicPack);
                    addLeafPilesModel(Objects.requireNonNull(lpModel16), id, leavesTexture, dynamicPack);
                } catch (Exception ex) {
                    Verdant.LOGGER.error("Failed to generate Leaf Pile model for {} : {}", pile, ex);
                }
            });
        }

        //bark

        /*
        {
            StaticResource barkModel = StaticResource.getOrLog(manager,
                    ResType.ITEM_MODELS.getPath(Verdant.res("oak_bark")));
            StaticResource scaleModel = StaticResource.getOrLog(manager,
                    ResType.ITEM_MODELS.getPath(Verdant.res("crimson_scales")));

            ModItems.BARK.forEach((woodType, bark) -> {
                if (!woodType.isVanilla() || !PlatHelper.isDev()) {

                    String id = Utils.getID(bark).getPath();

                    if(id.endsWith("scales")) {
                        try {
                            addSimilarJsonResource(manager, barkModel, "oak_bark", id);
                        } catch (Exception ex) {
                            getLogger().error("Failed to generate Bark item model for {} : {}", bark, ex);
                        }
                    } else {
                        try {
                            addSimilarJsonResource(manager, scaleModel, "crimson_scales", id);
                        } catch (Exception ex) {
                            getLogger().error("Failed to generate Scales item model for {} : {}", bark, ex);
                        }
                    }

                }
            });
        }

         */

        //heavy leaves textures
        ModBlocks.LEAF_PILES.forEach((type, pile) -> {
            if (type.isVanilla() && PlatHelper.isDev()) return;

            String path = type.getNamespace() + "/heavy_" + type.getTypeName() + "_leaf_pile";

            try (TextureImage baseTexture = TextureImage.open(manager, RPUtils.findFirstBlockTextureLocation(manager, type.leaves, LOOKS_LIKE_LEAF_TEXTURE))) {

                ResourceLocation textureRes = Verdant.res(
                        String.format("block/%s", path));
                if (!dynamicPack.alreadyHasAssetAtLocation(manager, textureRes)) {

                    Palette targetPalette = Palette.fromImage(baseTexture);
                    if (targetPalette.getDarkest().getOccurrence() > 5) {
                        targetPalette.increaseDown();
                    }
                    var dark = targetPalette.getDarkest();

                    TextureOps.makeOpaque(baseTexture, dark.value());

                    dynamicPack.addAndCloseTexture(textureRes, baseTexture);
                }
            } catch (Exception ex) {
                Verdant.LOGGER.error("Could not generate heavy leaf pile texture for type {}", type, ex);
            }
        });

        /*
        //bark textures
        try (TextureImage template = TextureImage.open(manager, Verdant.res("item/bark_template")); TextureImage scalesTemplate = TextureImage.open(manager, Verdant.res("item/scales_template"))) {

            ModItems.BARK.forEach((type, bark) -> {

                if (type.isVanilla() && PlatHelper.isDev()) return;

                ResourceLocation textureRes = Verdant.res(
                        "item/" + Utils.getID(bark).getPath());
                if (!alreadyHasTextureAtLocation(manager, textureRes)) {

                    String id = Utils.getID(bark).getPath();
                    TextureImage tempTemplate = template;
                    if(id.endsWith("scales"))
                    {
                        tempTemplate = scalesTemplate;
                    }
                    try (TextureImage logTexture = TextureImage.open(manager,
                            RPUtils.findFirstBlockTextureLocation(manager, type.log, s -> !s.contains("top")))) {
                        Palette palette = Palette.fromImage(logTexture);
                        //PaletteColor average = palette.calculateAverage();
                        palette.increaseDown();
                        PaletteColor dark = palette.getDarkest();
                        assert tempTemplate.imageWidth() <= logTexture.imageWidth() && tempTemplate.imageHeight() <= logTexture.imageHeight();
                        TextureImage newImage = tempTemplate.makeCopy();
                        var logImage = logTexture.getImage();
                        newImage.forEachFrame((i, x, y) -> {
                            var image = newImage.getImage();
                            int darkBorder = image.getPixelRGBA(x, y);
                            if (darkBorder == -1) {
                                image.setPixelRGBA(x, y, 0);
                            } else if (FastColor.ABGR32.alpha(darkBorder) == 0) { //TODO: check
                                image.setPixelRGBA(x, y, logImage.getPixelRGBA(x, y));
                            } else {
                                //HCLColor bc = new RGBColor(darkBorder).asHCL();
                                //image.setPixelRGBA(x, y, BaseColor.mixColors(dark.hcl(), average.hcl(), bc.asHCL()).asRGB().toInt());
                                image.setPixelRGBA(x, y, dark.value());
                            }
                        });

                        dynamicPack.addAndCloseTexture(textureRes, newImage);
                    } catch (Exception ex) {
                        getLogger().error("Failed to find log texture for bark {}", type, ex);
                    }
                }
            });
        } catch (Exception e) {
            getLogger().error("Could not generate any Bark texture : ", e);
        }

        */
        });
    }

    @Override
    public void addDynamicTranslations(AfterLanguageLoadEvent languageEvent) {

        /*
        ModItems.BARK.forEach((type, bark) -> {
            String id = Utils.getID(bark).getPath();
            if(id.endsWith("scales"))
            {
                LangBuilder.addDynamicEntry(lang, "item.verdant.scales", type, bark);
            }
            else{
                LangBuilder.addDynamicEntry(lang, "item.verdant.bark", type, bark);
            }
        });

         */

        ModBlocks.LEAF_PILES.forEach((type, leaf) -> {
            LangBuilder.addDynamicEntry(languageEvent, "block.verdant.leaf_pile", type, leaf);
        });
    }


    public static final Predicate<String> LOOKS_LIKE_LEAF_TEXTURE = s -> {
        s = ResourceLocation.parse(s).getPath();
        return !s.contains("_bushy") && !s.contains("_snow") && !s.contains("_overlay");
    };

    @Override
    protected Collection<String> gatherSupportedNamespaces() {
        return List.of("minecraft");
    }
}