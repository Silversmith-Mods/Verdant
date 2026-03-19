package com.ordana.verdant.dynamicpack;

import com.ordana.verdant.Verdant;
import com.ordana.verdant.reg.ModBlocks;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.SimpleTagBuilder;
import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.mehvahdjukaar.moonlight.api.resources.pack.*;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ServerDynamicResourcesHandler extends DynamicServerResourceProvider {

    public static final ServerDynamicResourcesHandler INSTANCE = new ServerDynamicResourcesHandler();

    public ServerDynamicResourcesHandler() {
        super(Verdant.res("generated_pack"), PackGenerationStrategy.CACHED);
    }

    @Override
    protected void regenerateDynamicAssets(Consumer<ResourceGenTask> executor) {

        executor.accept((manager, dynamicPack) ->  {
        //tag
        SimpleTagBuilder tag = SimpleTagBuilder.of(Verdant.res("leaf_piles"));
        tag.addEntries(ModBlocks.LEAF_PILES.values());
        dynamicPack.addTag(tag, Registries.BLOCK);
        dynamicPack.addTag(tag, Registries.BLOCK);

        /*
        dynamicPack.addTag(SimpleTagBuilder.of(Verdant.res("bark"))
                .addEntries(ModItems.BARK.values()), Registries.ITEM);

         */


        //data
        StaticResource lootTable = StaticResource.getOrLog(manager, ResType.BLOCK_LOOT_TABLES.getPath(Verdant.res("oak_leaf_pile")));
        StaticResource leafRecipe = StaticResource.getOrLog(manager, ResType.RECIPES.getPath(Verdant.res("oak_leaf_pile")));
        //StaticResource unstripLogRecipe = StaticResource.getOrLog(manager, ResType.RECIPES.getPath(Verdant.res("oak_log_unstrip")));
        //StaticResource unstripWoodRecipe = StaticResource.getOrLog(manager, ResType.RECIPES.getPath(Verdant.res("oak_wood_unstrip")));
        //StaticResource woodFromBarkRecipe = StaticResource.getOrLog(manager, ResType.RECIPES.getPath(Verdant.res("oak_wood_from_bark")));

        /*
        for (var e : ModItems.BARK.entrySet()) {
            WoodType woodType = e.getKey();
            if (!woodType.isVanilla()) {

                String path = woodType.getNamespace() + "/" + woodType.getTypeName();

                String woodFromBarkId = path + "_wood_from_bark";
                String unstripWoodId = path + "_wood_unstrip";
                String unstripLogId = path + "_log_unstrip";
                String barkId = path + "_bark";

                String logId = Utils.getID(woodType.log).toString();
                String slogId = Utils.getID(woodType.getChild("stripped_log")).toString();
                String woodId = Utils.getID(woodType.getChild("wood")).toString();
                String swoodId = Utils.getID(woodType.getChild("stripped_wood")).toString();


                try {
                    unstripLogBuilder(Objects.requireNonNull(unstripLogRecipe), "oak_log_unstrip", "minecraft:stripped_oak_log", "minecraft:oak_log", unstripLogId, barkId, slogId, logId);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Log Unstrip recipe for {} : {}", woodType, ex);
                }


                try {
                    unstripLogBuilder(Objects.requireNonNull(unstripWoodRecipe), "oak_wood_unstrip", "minecraft:stripped_oak_wood", "minecraft:oak_wood", unstripWoodId, barkId, swoodId, woodId);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Wood Unstrip recipe for {} : {}", woodType, ex);
                }


                try {
                    unstripLogBuilder(Objects.requireNonNull(woodFromBarkRecipe), "oak_wood_from_bark", "minecraft:oak_log", "minecraft:oak_wood", woodFromBarkId, barkId, logId, woodId);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Wood From Bark recipe for {} : {}", woodType, ex);
                }

            }
        }

         */

        for (var e : ModBlocks.LEAF_PILES.entrySet()) {
            LeavesType leafType = e.getKey();
            if (!leafType.isVanilla()) {
                var v = e.getKey();

                String path = leafType.getNamespace() + "/" + leafType.getTypeName();
                String id = path + "_leaf_pile";

                String leavesId = Utils.getID(leafType.leaves).toString();

                //TODO: use new system
                try {
                    addLeafPileJson(dynamicPack, Objects.requireNonNull(lootTable), id, leavesId);
                } catch (Exception ex) {
                    Verdant.LOGGER.error("Failed to generate Leaf Pile loot table for {} : {}", v, ex);
                }

                try {
                    addLeafPileJson(dynamicPack, Objects.requireNonNull(leafRecipe), id, leavesId);
                } catch (Exception ex) {
                    Verdant.LOGGER.error("Failed to generate Leaf Pile recipe for {} : {}", v, ex);
                }

            }
        }

        });

    }

    public void addLeafPileJson(ResourceSink dynamicPack, StaticResource resource, String id, String leafBlockId) {
        String string = new String(resource.data, StandardCharsets.UTF_8);

        String path = resource.location.getPath().replace("oak_leaf_pile", id);

        string = string.replace("oak_leaf_pile", id);
        string = string.replace("minecraft:oak_leaves", leafBlockId);

        //adds modified under my namespace
        ResourceLocation newRes = Verdant.res(path);
        dynamicPack.addBytes(newRes, string.getBytes(), ResType.GENERIC);
    }

    @Override
    protected Collection<String> gatherSupportedNamespaces() {
        return List.of("minecraft");
    }
}
