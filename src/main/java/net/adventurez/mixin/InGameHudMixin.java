package net.adventurez.mixin;

import com.mojang.blaze3d.systems.RenderSystem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.ItemInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.MathHelper;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public abstract class InGameHudMixin extends DrawableHelper {
  private final MinecraftClient client;

  public InGameHudMixin(MinecraftClient client) {
    this.client = client;
  }

  @Inject(method = "render", at = @At(value = "TAIL"))
  private void renderFireIcon(MatrixStack matrixStack, float f, CallbackInfo info) {
    this.renderFireIconOverlay(matrixStack);
  }

  private void renderFireIconOverlay(MatrixStack matrixStack) {
    int scaledWidth = this.client.getWindow().getScaledWidth();
    int scaledHeight = this.client.getWindow().getScaledHeight();
    int worldTime = (int) this.client.player.world.getTime();

    Sprite sprite = this.client.getStatusEffectSpriteManager().getSprite(StatusEffects.FIRE_RESISTANCE);
    this.client.getTextureManager().bindTexture(sprite.getAtlas().getId());

    ItemStack golemChestplate = this.client.player.getEquippedStack(EquipmentSlot.CHEST);
    CompoundTag tag = golemChestplate.getTag();

    if (this.client.player.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(ItemInit.STONE_GOLEM_CHESTPLATE)) {
      if (tag != null && tag.contains("armor_time") && tag.contains("activating_armor")) {
        if (tag.getBoolean("activating_armor")) {
          int savedTagInt = tag.getInt("armor_time");
          int multiplier = 2;
          if (savedTagInt + 600 > worldTime) {
            if (savedTagInt + 540 < worldTime) {
              multiplier = 4;
            }
            float pulsating = MathHelper.sin((float) ((worldTime * multiplier) - (savedTagInt - 1F)) / 6.2831855F);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, pulsating + 0.5F);
            drawSprite(matrixStack, (scaledWidth / 2) - 5, scaledHeight - 49, this.getZOffset(), 10, 10, sprite);
          } else {
            float fading = (1F - (((float) (worldTime - (savedTagInt + 599F)) / 1200F) - 0.4F));
            RenderSystem.color4f(1.0F, 0.8F, 0.8F, fading);
            drawSprite(matrixStack, (scaledWidth / 2) - 5, scaledHeight - 49, this.getZOffset(), 10, 10, sprite);
          }
        }
      }
    }
  }

}