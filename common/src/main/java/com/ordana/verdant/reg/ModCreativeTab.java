package com.ordana.verdant.reg;

import com.ordana.verdant.Verdant;
import com.ordana.verdant.configs.CommonConfigs;
import net.mehvahdjukaar.moonlight.api.misc.RegSupplier;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ModCreativeTab {

    public static final RegSupplier<CreativeModeTab> MOD_TAB = !CommonConfigs.CREATIVE_TAB.get() ? null :
            RegHelper.registerCreativeModeTab(Verdant.res("verdant"),
                    (c) -> c.title(Component.translatable("itemGroup.verdant.verdant"))
                            .icon(() -> ModBlocks.IVY.get().asItem().getDefaultInstance()));

    public static void init(){
        RegHelper.addItemsToTabsRegistration(ModCreativeTab::addItems);
    }

    public static void addItems(RegHelper.ItemToTabEvent e) {

        after(e, Items.FERN, CreativeModeTabs.NATURAL_BLOCKS,
                ModBlocks.BARLEY,
                ModBlocks.CLOVER,
                ModBlocks.DENSE_GRASS,
                ModBlocks.EDGE_GRASS,
                ModBlocks.WEEDS,
                ModBlocks.SPOROPHYTE, ModBlocks.TALL_SPOROPHYTE,

                ModBlocks.JUNGLE_FERN,
                ModBlocks.MONSTERA,
                ModBlocks.IVY,
                ModBlocks.DOGWOOD,
                ModBlocks.BOXWOOD
        );

        after(e, Items.LILY_PAD, CreativeModeTabs.NATURAL_BLOCKS,
                ModBlocks.CATTAIL,
                ModBlocks.DUCKWEED
        );

        after(e, Items.CACTUS, CreativeModeTabs.NATURAL_BLOCKS,
                ModBlocks.DUNE_GRASS,
                ModBlocks.SAGEBRUSH,
                ModBlocks.SHRUB,
                ModBlocks.ALOE_VERA,
                ModBlocks.SAGUARO_BLOCK,
                ModBlocks.SAGUARO_ARM
        );

        after(e, Items.LILY_OF_THE_VALLEY, CreativeModeTabs.NATURAL_BLOCKS,
                ModBlocks.RED_HIBISCUS, ModBlocks.PURPLE_HIBISCUS, ModBlocks.BLUE_HIBISCUS,
                ModBlocks.ANEMONE,
                ModBlocks.DAHLIA,
                ModBlocks.POKER,
                ModBlocks.MUSCARI,
                ModBlocks.SALVIA,
                ModBlocks.RED_PRIMROSE,
                ModBlocks.ORANGE_PRIMROSE,
                ModBlocks.YELLOW_PRIMROSE,
                ModBlocks.LIME_PRIMROSE,
                ModBlocks.GREEN_PRIMROSE,
                ModBlocks.BLUE_PRIMROSE,
                ModBlocks.LIGHT_BLUE_PRIMROSE,
                ModBlocks.CYAN_PRIMROSE,
                ModBlocks.PURPLE_PRIMROSE,
                ModBlocks.MAGENTA_PRIMROSE,
                ModBlocks.PINK_PRIMROSE,
                ModBlocks.WHITE_PRIMROSE,
                ModBlocks.BLACK_PRIMROSE,
                ModBlocks.GRAY_PRIMROSE,
                ModBlocks.LIGHT_GRAY_PRIMROSE,
                ModBlocks.BROWN_PRIMROSE
        );

        after(e, Items.MOSS_CARPET, CreativeModeTabs.NATURAL_BLOCKS,
                ModItems.MOSS_CLUMP, ModItems.GOLDEN_MOSS_CLUMP, ModItems.ENCHANTED_GOLDEN_MOSS_CLUMP
        );

        before(e, Items.INK_SAC, CreativeModeTabs.INGREDIENTS,
                ModItems.AZALEA_FLOWERS, ModItems.FLOWER_CROWN
        );

        after(e, Items.RED_MUSHROOM, CreativeModeTabs.NATURAL_BLOCKS,
                ModBlocks.BUTTON_MUSHROOM, ModBlocks.CRIMINI, ModBlocks.PORTABELLA, ModItems.GRILLED_PORTABELLA,
                ModBlocks.CONK_FUNGUS, ModBlocks.STINKHORN_MUSHROOM, ModBlocks.WHITE_STINKHORN_MUSHROOM,
                ModBlocks.PHOSPHOR_FUNGUS, ModBlocks.MUSHGLOOM, ModBlocks.MILLY_BUBCAP
        );

        after(e, Items.MUSHROOM_STEM, CreativeModeTabs.NATURAL_BLOCKS,
                ModBlocks.CAVE_MUSHROOM_STEM
        );

        after(e, Items.SHROOMLIGHT, CreativeModeTabs.NATURAL_BLOCKS,
                ModBlocks.PHOSPHOR_SHROOMLIGHT
        );

        after(e, Items.RED_MUSHROOM_BLOCK, CreativeModeTabs.NATURAL_BLOCKS,
                ModBlocks.PORTABELLA_BLOCK,
                ModBlocks.CONK_FUNGUS_BLOCK, ModBlocks.STINKHORN_MUSHROOM_BLOCK, ModBlocks.WHITE_STINKHORN_MUSHROOM_BLOCK,
                ModBlocks.PHOSPHOR_FUNGUS_BLOCK, ModBlocks.MUSHGLOOM_BLOCK, ModBlocks.MILLY_BUBCAP_BLOCK
        );


        after(e, Items.FLOWERING_AZALEA_LEAVES, CreativeModeTabs.NATURAL_BLOCKS,
                ModBlocks.LEAF_PILES.values().stream().map(s-> (Supplier<Object>) () -> s).toArray(Supplier[]::new)
        );

        before(e, Items.INK_SAC, CreativeModeTabs.INGREDIENTS,
                ModItems.BARK.values().stream().map(s-> (Supplier<Object>) () -> s).toArray(Supplier[]::new)
        );

    }

    private static void after(RegHelper.ItemToTabEvent event, Item target,
                              ResourceKey<CreativeModeTab> tab, Supplier<?>... items) {
        after(event, i -> i.is(target), tab, items);
    }

    private static void after(RegHelper.ItemToTabEvent event, Predicate<ItemStack> targetPred,
                              ResourceKey<CreativeModeTab> tab, Supplier<?>... items) {
        //if (CommonConfigs.isEnabled(key)) {
        ItemLike[] entries = Arrays.stream(items).map((s -> (ItemLike) (s.get()))).toArray(ItemLike[]::new);
        if(MOD_TAB != null){
            tab = (ResourceKey<CreativeModeTab>) MOD_TAB.getKey();
        }
        event.addAfter(tab, targetPred, entries);
    }

    private static void before(RegHelper.ItemToTabEvent event, Item target,
                               ResourceKey<CreativeModeTab> tab, Supplier<?>... items) {
        before(event, i -> i.is(target), tab, items);
    }

    private static void before(RegHelper.ItemToTabEvent event, Predicate<ItemStack> targetPred,
                               ResourceKey<CreativeModeTab> tab, Supplier<?>... items) {
        //if (CommonConfigs.isEnabled(key)) {
        ItemLike[] entries = Arrays.stream(items).map(s -> (ItemLike) s.get()).toArray(ItemLike[]::new);
        if(MOD_TAB != null){
            tab = (ResourceKey<CreativeModeTab>) MOD_TAB.getKey();
        }
        event.addBefore(tab, targetPred, entries);
        //}
    }
}
