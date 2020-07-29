package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.adventurez.init.ItemInit;
import net.adventurez.item.armor.StoneGolemArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {

  @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnFire()Z"), method = "net/minecraft/client/gui/hud/InGameOverlayRenderer.renderOverlays(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V")
  private static boolean fireOverlayMixin(ClientPlayerEntity playerEntity, MinecraftClient minecraftClient,
      MatrixStack matrixStack) {
    ItemStack golemChestplate = playerEntity.getEquippedStack(EquipmentSlot.CHEST);
    boolean fireActivated = playerEntity.getEquippedStack(EquipmentSlot.CHEST).getItem()
        .equals(ItemInit.STONE_GOLEM_CHESTPLATE) && StoneGolemArmor.fireTime(golemChestplate);

    boolean isOnFire = playerEntity.isOnFire();
    return isOnFire && !fireActivated;
  }

}