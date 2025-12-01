package com.ordana.verdant.dynamicpack;

import com.ordana.verdant.Verdant;
import com.ordana.verdant.reg.ModBlocks;
import com.ordana.verdant.reg.ModItems;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.SimpleTagBuilder;
import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynServerResourcesGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicDataPack;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ServerDynamicResourcesHandler extends DynServerResourcesGenerator {

    public static final ServerDynamicResourcesHandler INSTANCE = new ServerDynamicResourcesHandler();

    public ServerDynamicResourcesHandler() {
        super(new DynamicDataPack(Verdant.res("generated_pack")));
        this.dynamicPack.setGenerateDebugResources(PlatHelper.isDev());
    }

    @Override
    public Logger getLogger() {
        return Verdant.LOGGER;
    }

//    @Override
//    public boolean dependsOnLoadedPacks() {
//        return true;
//    }

    @Override
    public void regenerateDynamicAssets(ResourceManager manager) {

        //tag
        SimpleTagBuilder tag = SimpleTagBuilder.of(Verdant.res("leaf_piles"));
        tag.addEntries(ModBlocks.LEAF_PILES.values());
        dynamicPack.addTag(tag, Registries.BLOCK);
        dynamicPack.addTag(tag, Registries.BLOCK);

        dynamicPack.addTag(SimpleTagBuilder.of(Verdant.res("bark"))
                .addEntries(ModItems.BARK.values()), Registries.ITEM);


        //data
        StaticResource lootTable = StaticResource.getOrLog(manager, ResType.BLOCK_LOOT_TABLES.getPath(Verdant.res("oak_leaf_pile")));
        StaticResource leafRecipe = StaticResource.getOrLog(manager, ResType.RECIPES.getPath(Verdant.res("oak_leaf_pile")));
        StaticResource unstripLogRecipe = StaticResource.getOrLog(manager, ResType.RECIPES.getPath(Verdant.res("oak_log_unstrip")));
        StaticResource unstripWoodRecipe = StaticResource.getOrLog(manager, ResType.RECIPES.getPath(Verdant.res("oak_wood_unstrip")));
        StaticResource woodFromBarkRecipe = StaticResource.getOrLog(manager, ResType.RECIPES.getPath(Verdant.res("oak_wood_from_bark")));

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

        for (var e : ModBlocks.LEAF_PILES.entrySet()) {
            LeavesType leafType = e.getKey();
            if (!leafType.isVanilla()) {
                var v = e.getKey();

                String path = leafType.getNamespace() + "/" + leafType.getTypeName();
                String id = path + "_leaf_pile";

                String leavesId = Utils.getID(leafType.leaves).toString();

                //TODO: use new system
                try {
                    addLeafPileJson(Objects.requireNonNull(lootTable), id, leavesId);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Leaf Pile loot table for {} : {}", v, ex);
                }

                try {
                    addLeafPileJson(Objects.requireNonNull(leafRecipe), id, leavesId);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Leaf Pile recipe for {} : {}", v, ex);
                }

            }
        }

    }

    public void addLeafPileJson(StaticResource resource, String id, String leafBlockId) {
        String string = new String(resource.data, StandardCharsets.UTF_8);

        String path = resource.location.getPath().replace("oak_leaf_pile", id);

        string = string.replace("oak_leaf_pile", id);
        string = string.replace("minecraft:oak_leaves", leafBlockId);

        //adds modified under my namespace
        ResourceLocation newRes = Verdant.res(path);
        dynamicPack.addBytes(newRes, string.getBytes(), ResType.GENERIC);
    }

    public void unstripLogBuilder(StaticResource resource, String target1, String target2, String target3, String id, String barkId, String slogId, String logId) {
        String string = new String(resource.data, StandardCharsets.UTF_8);

        String path = resource.location.getPath().replace(target1, id);

        string = string.replace("oak_bark", barkId);
        string = string.replace(target2, slogId);
        string = string.replace(target3, logId);

        //adds modified under my namespace
        ResourceLocation newRes = Verdant.res(path);
        dynamicPack.addBytes(newRes, string.getBytes(), ResType.GENERIC);
    }
}
