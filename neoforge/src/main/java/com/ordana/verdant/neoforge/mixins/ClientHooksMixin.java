package com.ordana.verdant.neoforge.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.ordana.verdant.Verdant;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.ordana.verdant.items.FlowerCrownItem.getModelTexture;

@Mixin(ClientHooks.class)
public abstract class ClientHooksMixin {

    @WrapOperation(method = "getArmorTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getArmorTexture(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ArmorMaterial$Layer;Z)Lnet/minecraft/resources/ResourceLocation;"))
	private static ResourceLocation renderFlowerCrown(Item instance, ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean b, Operation<ResourceLocation> original) {
        String res = getModelTexture(stack);
        if (res != null) return Verdant.res(res);
        return original.call(instance, stack, entity, slot, layer, b);
    }
}
