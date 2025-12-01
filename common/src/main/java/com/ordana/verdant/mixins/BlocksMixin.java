package com.ordana.verdant.mixins;

import com.ordana.verdant.blocks.ModPropaguleBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public abstract class BlocksMixin {

    @Redirect(method = "<clinit>", at = @At(
            value = "NEW",
            target = "(Lnet/minecraft/world/level/block/grower/TreeGrower;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/MangrovePropaguleBlock;",
            ordinal = 0
    ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=mangrove_propagule"
                    )
            )
    )
    private static MangrovePropaguleBlock mangrovePropaguleBlock(TreeGrower treeGrower, BlockBehaviour.Properties properties) {
        return new ModPropaguleBlock(treeGrower, properties);
    }
}