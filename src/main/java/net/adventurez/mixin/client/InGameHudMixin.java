package net.adventurez.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
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
import net.minecraft.nbt.NbtCompound;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow
    @Final
    @Mutable
    private MinecraftClient client;

    public InGameHudMixin(MinecraftClient client) {
        this.client = client;
    }

    @Inject(method = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(FLnet/minecraft/client/util/math/MatrixStack;)V"))
    private void renderFireIcon(MatrixStack matrixStack, float f, CallbackInfo info) {
        this.renderFireIconOverlay(matrixStack);
    }

    private void renderFireIconOverlay(MatrixStack matrixStack) {
        ItemStack golemChestplate = this.client.player.getEquippedStack(EquipmentSlot.CHEST);
        if (golemChestplate.getItem().equals(ItemInit.STONE_GOLEM_CHESTPLATE) && !this.client.player.isCreative()) {
            NbtCompound tag = golemChestplate.getNbt();
            if (tag != null && tag.contains("armor_time") && tag.contains("activating_armor")) {
                if (tag.getBoolean("activating_armor")) {
                    int scaledWidth = this.client.getWindow().getScaledWidth();
                    int scaledHeight = this.client.getWindow().getScaledHeight();
                    int worldTime = (int) this.client.player.world.getTime();
                    Sprite sprite = this.client.getStatusEffectSpriteManager().getSprite(StatusEffects.FIRE_RESISTANCE);
                    RenderSystem.setShaderTexture(0, sprite.getAtlas().getId());
                    int savedTagInt = tag.getInt("armor_time");

                    if (savedTagInt + 1200 > worldTime) {
                        int multiplier = 2;
                        if (savedTagInt + 1100 < worldTime) {
                            multiplier = 4;
                        }
                        float pulsating = (float) Math.sin((float) ((worldTime * multiplier) - (savedTagInt - 1)) / 6.2831855F);
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, pulsating + 0.5F);
                        drawSprite(matrixStack, (scaledWidth / 2) - 5, scaledHeight - 49, this.getZOffset(), 10, 10, sprite);
                    } else {
                        float fading = (1F - (((float) (worldTime - (savedTagInt + 1199F)) / 1200F) - 0.4F));
                        RenderSystem.setShaderColor(1.0F, 0.65F, 0.65F, fading);
                        drawSprite(matrixStack, (scaledWidth / 2) - 5, scaledHeight - 49, this.getZOffset(), 10, 10, sprite);
                    }
                }
            }
        }
    }

}