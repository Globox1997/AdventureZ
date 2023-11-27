package net.adventurez.mixin.client;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    private static final SpriteIdentifier SHADOW = createChestTextureId("shadow_chest");

    @Inject(method = "Lnet/minecraft/client/render/TexturedRenderLayers;getChestTextureId(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;", at = @At("TAIL"), cancellable = true)
    private static void getChestTextureMixin(BlockEntity blockEntity, ChestType type, boolean christmas, CallbackInfoReturnable<SpriteIdentifier> info) {
        if (blockEntity instanceof ShadowChestEntity) {
            info.setReturnValue(SHADOW);
        }
    }

    @Inject(method = "addDefaultTextures", at = @At("TAIL"))
    private static void addDefaultTexturesMixin(Consumer<SpriteIdentifier> adder, CallbackInfo info) {
        adder.accept(SHADOW);
    }

    @Shadow
    private static SpriteIdentifier createChestTextureId(String variant) {
        return null;
    }
}
