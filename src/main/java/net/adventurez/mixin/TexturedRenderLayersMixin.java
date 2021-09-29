package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.Environment;
import net.adventurez.block.entity.ShadowChestEntity;
import net.fabricmc.api.EnvType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;

@Environment(EnvType.CLIENT)
@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {

    @Inject(method = "getChestTexture", at = @At("HEAD"), cancellable = true)
    private static void getChestTextureMixin(BlockEntity blockEntity, ChestType type, boolean christmas, CallbackInfoReturnable<SpriteIdentifier> info) {
        if (blockEntity instanceof ShadowChestEntity) {
            info.setReturnValue(getChestTextureId("shadow_chest_block"));
        }
    }

    @Shadow
    private static SpriteIdentifier getChestTextureId(String variant) {
        return null;
    }
}
