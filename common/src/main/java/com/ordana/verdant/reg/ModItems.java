package com.ordana.verdant.reg;

import com.ordana.verdant.Verdant;
import com.ordana.verdant.items.*;
import com.ordana.verdant.items.materials.FlowerCrownMaterial;
import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModItems {

    public static void init() {
        BlockSetAPI.addDynamicItemRegistration(ModItems::registerLeafPilesItems, LeavesType.class);
        //BlockSetAPI.addDynamicItemRegistration(ModItems::registerBark, WoodType.class);

    }

    public static <T extends Item> Supplier<T> regItem(String name, Supplier<T> itemSup) {
        return RegHelper.registerItem(Verdant.res(name), itemSup);
    }


    //leaf pile
    public static final Map<LeavesType, BlockItem> LEAF_PILES = new LinkedHashMap<>();

    //flowers
    public static final Supplier<Item> AZALEA_FLOWERS = regItem("azalea_flowers", () ->
            new AzaleaFlowersItem(ModBlocks.AZALEA_FLOWER_PILE.get(), new Item.Properties()));
    public static final Supplier<Item> FLOWER_CROWN = regItem("flower_crown", () ->
            new FlowerCrownItem(FlowerCrownMaterial.INSTANCE, ArmorItem.Type.HELMET,
                    new Item.Properties()));

    public static final Supplier<Item> DUCKWEED = regItem("duckweed", () ->
            new DuckweedItem(ModBlocks.DUCKWEED.get(), new Item.Properties()));

    public static final Supplier<Item> MOSS_CLUMP = regItem("moss_clump", () ->
            new MossClumpItem(ModBlocks.MOSS.get(), new Item.Properties().food(ModFoods.MOSS_CLUMP)));
    public static final Supplier<Item> GOLDEN_MOSS_CLUMP = regItem("golden_moss_clump", () ->
            new Item(new Item.Properties().food(ModFoods.GOLDEN_MOSS_CLUMP)));
    public static final Supplier<Item> ENCHANTED_GOLDEN_MOSS_CLUMP = regItem("enchanted_golden_moss_clump", () ->
            new EnchantedGoldenMossClumpItem(new Item.Properties()
                    .rarity(Rarity.EPIC).food(ModFoods.ENCHANTED_GOLDEN_MOSS_CLUMP)));

    public static final Supplier<Item> BUTTON_MUSHROOM = regItem("button_mushroom", () ->
            new BlockItem(ModBlocks.BUTTON_MUSHROOM.get(), new Item.Properties().food(ModFoods.BUTTON_MUSHROOM)));
    public static final Supplier<Item> CRIMINI = regItem("crimini", () ->
            new BlockItem(ModBlocks.CRIMINI.get(), new Item.Properties().food(ModFoods.CRIMINI)));
    public static final Supplier<Item> PORTABELLA = regItem("portabella", () ->
            new BlockItem(ModBlocks.PORTABELLA.get(), new Item.Properties().food(ModFoods.PORTABELLA)));
    public static final Supplier<Item> GRILLED_PORTABELLA = regItem("grilled_portabella", () ->
            new Item(new Item.Properties().food(ModFoods.GRILLED_PORTABELLA)));


    //bark

    /*
    public static final Map<WoodType, Item> BARK = new LinkedHashMap<>();


    private static void registerBark(Registrator<Item> event, Collection<WoodType> woodTypes) {
        for (WoodType type : woodTypes) {
            String name = !type.canBurn() ? type.getVariantId("scales", false) : type.getVariantId("bark", false);

            Item item = new WoodBasedItem(new Item.Properties(), type);
            PlatformSpecific.registerFuelValue(item, 200);
            event.register(Verdant.res(name), item);
            BARK.put(type, item);
            type.addChild("verdant:bark", item);
        }
    }

     */

    private static void registerLeafPilesItems(Registrator<Item> event, Collection<LeavesType> leavesTypes) {
        for (LeavesType type : leavesTypes) {
            var b = ModBlocks.LEAF_PILES.get(type);
            BlockItem i = new LeafPileBlockItem(b, new Item.Properties());
            event.register(Utils.getID(b), i);
            LEAF_PILES.put(type, i);
        }
    }
}
