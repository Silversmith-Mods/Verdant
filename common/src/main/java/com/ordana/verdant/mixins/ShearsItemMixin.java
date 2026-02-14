package com.ordana.verdant.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ordana.verdant.reg.ModTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShearsItem.class)
public class ShearsItemMixin {

    @WrapOperation(method = "createToolProperties",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/Tool$Rule;overrideSpeed(Lnet/minecraft/tags/TagKey;F)Lnet/minecraft/world/item/component/Tool$Rule;", ordinal = 0)

    )
    private static Tool.Rule addShearables(TagKey<Block> tagKey, float f, Operation<Tool.Rule> original) {
        return original.call(ModTags.SHEARABLE, f);
    }
}
