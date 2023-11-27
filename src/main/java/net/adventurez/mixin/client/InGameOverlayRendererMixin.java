package net.adventurez.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.adventurez.init.ItemInit;
import net.adventurez.item.armor.GildedNetheriteArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin {

    @Inject(method = "renderOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnFire()Z"), cancellable = true)
    private static void fireOverlayMixin(MinecraftClient minecraftClient, MatrixStack matrixStack, CallbackInfo info) {
        ItemStack itemStack = minecraftClient.player.getEquippedStack(EquipmentSlot.CHEST);
        if (!itemStack.isEmpty() && itemStack.isOf(ItemInit.GILDED_NETHERITE_CHESTPLATE) && GildedNetheriteArmor.isStoneGolemArmorActive(itemStack)) {
            info.cancel();
        }
    }
}