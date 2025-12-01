package com.ordana.verdant.reg;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties MOSS_CLUMP = (new FoodProperties.Builder())
            .nutrition(1).saturationModifier(0.2F).alwaysEdible().fast()
            .build();

    public static final FoodProperties GOLDEN_MOSS_CLUMP = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.6F).alwaysEdible()
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 1F)
            .build();

    public static final FoodProperties ENCHANTED_GOLDEN_MOSS_CLUMP = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.8F).alwaysEdible()
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 1600, 2), 1F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 300, 1), 1F)
            .effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 1600, 2), 1F).build();

    public static final FoodProperties GRILLED_PORTABELLA = (new FoodProperties.Builder())
            .nutrition(6).saturationModifier(0.8F)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 1800, 2), 1F)
            .build();

    public static final FoodProperties PORTABELLA = (new FoodProperties.Builder())
            .nutrition(3).saturationModifier(0.4F)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 300, 1), 0.5F)
            .build();

    public static final FoodProperties CRIMINI = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(1.5F)
            .build();

    public static final FoodProperties BUTTON_MUSHROOM = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.3F).fast()
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 18000, 2), 0.01F)
            .build();
}
