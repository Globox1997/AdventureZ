package net.adventurez.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.vertex.PoseStack;

import net.adventurez.init.ItemInit;
import net.adventurez.item.GildedNetheriteArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

@Mixin(ScreenEffectRenderer.class)
public abstract class InGameOverlayRendererMixin {

    @WrapWithCondition(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ScreenEffectRenderer;submitFire(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V"))
    private boolean fireOverlayMixin(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, TextureAtlasSprite sprite) {
        Minecraft minecraftClient = Minecraft.getInstance();
        if (minecraftClient.player != null) {
            ItemStack itemStack = minecraftClient.player.getItemBySlot(EquipmentSlot.CHEST);
            if (!itemStack.isEmpty() && itemStack.is(ItemInit.GILDED_NETHERITE_CHESTPLATE) && GildedNetheriteArmor.isStoneGolemArmorActive(itemStack)) {
                return false;
            }
        }
        return true;
    }
}
