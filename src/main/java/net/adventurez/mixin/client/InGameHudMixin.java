package net.adventurez.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.EffectInit;
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
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow
    @Final
    @Mutable
    private final MinecraftClient client;

    private static final Identifier WITHERED_TEXTURE = new Identifier("adventurez:textures/misc/withered.png");

    public InGameHudMixin(MinecraftClient client) {
        this.client = client;
    }

    @Inject(method = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(FLnet/minecraft/client/util/math/MatrixStack;)V"))
    private void renderMixin(MatrixStack matrixStack, float f, CallbackInfo info) {
        this.renderOtherOverlay(matrixStack);
    }

    private void renderOtherOverlay(MatrixStack matrixStack) {
        if (!this.client.player.isCreative()) {
            ItemStack itemStack = this.client.player.getEquippedStack(EquipmentSlot.CHEST);
            if (!itemStack.isEmpty() && itemStack.isOf(ItemInit.STONE_GOLEM_CHESTPLATE)) {
                NbtCompound tag = itemStack.getNbt();
                if (tag != null && tag.contains("armor_time") && tag.contains("activating_armor")) {
                    if (tag.getBoolean("activating_armor")) {
                        int scaledWidth = this.client.getWindow().getScaledWidth();
                        int scaledHeight = this.client.getWindow().getScaledHeight();
                        int worldTime = (int) this.client.player.world.getTime();
                        Sprite sprite = this.client.getStatusEffectSpriteManager().getSprite(StatusEffects.FIRE_RESISTANCE);
                        RenderSystem.setShaderTexture(0, sprite.getAtlas().getId());
                        int savedTagInt = tag.getInt("armor_time");
                        float effectDuration = ConfigInit.CONFIG.stone_golem_armor_effect_duration;

                        if (savedTagInt + effectDuration > worldTime) {
                            int multiplier = 2;
                            if (savedTagInt + Math.max(effectDuration - 100, 0) < worldTime)
                                multiplier = 4;

                            float pulsating = (float) Math.sin((float) ((worldTime * multiplier) - (savedTagInt - 1)) / 6.2831855F);
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, pulsating + 0.5F);
                            drawSprite(matrixStack, (scaledWidth / 2) - 5, scaledHeight - 49, this.getZOffset(), 10, 10, sprite);
                        } else {
                            float fading = (1F - (((float) (worldTime - (savedTagInt + (effectDuration - 1f))) / effectDuration) - 0.4F));
                            RenderSystem.setShaderColor(1.0F, 0.65F, 0.65F, fading);
                            drawSprite(matrixStack, (scaledWidth / 2) - 5, scaledHeight - 49, this.getZOffset(), 10, 10, sprite);
                        }
                    }
                }
            }
            if (this.client.player.hasStatusEffect(EffectInit.WITHERING)) {
                RenderSystem.setShaderTexture(0, WITHERED_TEXTURE);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.client.player.getStatusEffect(EffectInit.WITHERING).getDuration() / 280F);
                int scaledWidth = this.client.getWindow().getScaledWidth();
                int scaledHeight = this.client.getWindow().getScaledHeight();
                drawTexture(matrixStack, scaledWidth / 2 - 64, scaledHeight / 2 - 64, 0.0F, 0.0F, 128, 128, 128, 128);
            }
        }

    }

}