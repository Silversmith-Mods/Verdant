package com.ordana.verdant.items.materials;

import com.google.common.collect.Maps;
import com.ordana.verdant.Verdant;
import com.ordana.verdant.reg.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class FlowerCrownMaterial {

    public static final Holder<ArmorMaterial> INSTANCE = register("flower",
            Maps.newEnumMap(Map.of(
                    ArmorItem.Type.BOOTS, 0,
                    ArmorItem.Type.LEGGINGS, 0,
                    ArmorItem.Type.CHESTPLATE, 0,
                    ArmorItem.Type.HELMET, 0,
                    ArmorItem.Type.BODY, 0)),
            64, SoundEvents.ARMOR_EQUIP_LEATHER, 0, 0, ()-> Ingredient.of(ModItems.AZALEA_FLOWERS.get())
    );

    private static Holder<ArmorMaterial> register(String string, EnumMap<ArmorItem.Type, Integer> enumMap, int i, Holder<SoundEvent> holder, float f, float g, Supplier<Ingredient> supplier) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(Verdant.res(string)));
        return register(string, enumMap, i, holder, f, g, supplier, list);
    }

    private static Holder<ArmorMaterial> register(String string, EnumMap<ArmorItem.Type, Integer> enumMap, int i, Holder<SoundEvent> holder, float f, float g, Supplier<Ingredient> supplier, List<ArmorMaterial.Layer> list) {
        EnumMap<ArmorItem.Type, Integer> enumMap2 = new EnumMap<>(ArmorItem.Type.class);

        for(ArmorItem.Type type : ArmorItem.Type.values()) {
            enumMap2.put(type, enumMap.get(type));
        }

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, Verdant.res(string), new ArmorMaterial(enumMap2, i, holder, supplier, list, f, g));
    }

}
