package net.adventurez.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.adventurez.block.entity.ShadowChestEntity;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
@Mixin(Sheets.class)
public class TexturedRenderLayersMixin {

    private static final Material SHADOW = chestMaterial("shadow_chest");

    @Inject(method = "chooseMaterial(Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/level/block/state/properties/ChestType;Z)Lnet/minecraft/client/resources/model/Material;", at = @At("TAIL"), cancellable = true)
    private static void getChestTextureMixin(BlockEntity blockEntity, ChestType type, boolean christmas, CallbackInfoReturnable<Material> info) {
        if (blockEntity instanceof ShadowChestEntity) {
            info.setReturnValue(SHADOW);
        }
    }

    @Shadow
    private static Material chestMaterial(String variant) {
        return null;
    }
}
