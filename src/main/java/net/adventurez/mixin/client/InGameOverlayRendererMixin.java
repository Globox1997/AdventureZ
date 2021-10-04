package net.adventurez.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.adventurez.init.EffectInit;
import net.adventurez.init.ItemInit;
import net.adventurez.item.armor.StoneGolemArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin {
    private static final Identifier WITHERED_TEXTURE = new Identifier("adventurez:textures/misc/dark.png");

    @Inject(method = "renderOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnFire()Z"), cancellable = true)
    private static void fireOverlayMixin(MinecraftClient minecraftClient, MatrixStack matrixStack, CallbackInfo info) {
        PlayerEntity playerEntity = minecraftClient.player;
        ItemStack golemChestplate = playerEntity.getEquippedStack(EquipmentSlot.CHEST);
        boolean fireActivated = golemChestplate.getItem().equals(ItemInit.STONE_GOLEM_CHESTPLATE) && StoneGolemArmor.fireTime(golemChestplate);
        if (fireActivated) {
            info.cancel();
        }
    }

    @Inject(method = "renderOverlays", at = @At(value = "TAIL"))
    private static void renderOverlaysMixin(MinecraftClient minecraftClient, MatrixStack matrixStack, CallbackInfo info) {
        PlayerEntity playerEntity = minecraftClient.player;
        if (!playerEntity.isSpectator() && playerEntity.hasStatusEffect(EffectInit.WITHERING)) {
            int duration = playerEntity.getStatusEffect(EffectInit.WITHERING).getDuration();
            renderWitheredOverlay(minecraftClient, matrixStack, duration);
        }
    }

    private static void renderWitheredOverlay(MinecraftClient minecraftClient, MatrixStack matrixStack, int duration) {
        RenderSystem.enableTexture();
        RenderSystem.setShaderTexture(0, WITHERED_TEXTURE);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        float f = minecraftClient.player.getBrightnessAtEyes();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        float m = -minecraftClient.player.getYaw() / 64.0F;
        float n = minecraftClient.player.getPitch() / 64.0F;
        Matrix4f matrix4f = matrixStack.peek().getModel();
        float colorBlend = (float) duration / 200.0F;
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferBuilder.vertex(matrix4f, -1.0F, -1.0F, -0.5F).color(f, f, f, colorBlend).texture(4.0F + m, 4.0F + n).next();
        bufferBuilder.vertex(matrix4f, 1.0F, -1.0F, -0.5F).color(f, f, f, colorBlend).texture(0.0F + m, 4.0F + n).next();
        bufferBuilder.vertex(matrix4f, 1.0F, 1.0F, -0.5F).color(f, f, f, colorBlend).texture(0.0F + m, 0.0F + n).next();
        bufferBuilder.vertex(matrix4f, -1.0F, 1.0F, -0.5F).color(f, f, f, colorBlend).texture(4.0F + m, 0.0F + n).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.disableBlend();
    }
}