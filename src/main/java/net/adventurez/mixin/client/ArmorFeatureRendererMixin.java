package net.adventurez.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.AdventureMain;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.adventurez.init.ItemInit;
import net.adventurez.item.GildedNetheriteArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    @Shadow
    @Final
    @Mutable
    private A outerModel;

    @Unique
    private boolean gildedNetheriteArmorOnFire = false;

    @Unique
    private static final ResourceLocation GILDED_NETHERITE_ARMOR = AdventureMain.identifierOf("textures/models/armor/gilded_netherite_layer_1_overlay.png");
    @Unique
    private static final ResourceLocation GILDED_NETHERITE_HELMET = AdventureMain.identifierOf("textures/models/armor/gilded_netherite_layer_1_overlay_helmet.png");

    public ArmorFeatureRendererMixin(RenderLayerParent<T, M> context) {
        super(context);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void renderMixin(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light, T livingEntity, float f, float g, float h, float j, float k, float l,
            CallbackInfo info) {
        ItemStack golemChestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        if (golemChestplate.is(ItemInit.GILDED_NETHERITE_CHESTPLATE) && GildedNetheriteArmor.isStoneGolemArmorActive(golemChestplate)) {
            gildedNetheriteArmorOnFire = true;
        } else if (gildedNetheriteArmorOnFire) {
            gildedNetheriteArmorOnFire = false;
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V", ordinal = 3))
    private void renderGlowingArmorOneMixin(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light, T livingEntity, float f, float g, float h, float j, float k, float l,
            CallbackInfo info) {
        if (gildedNetheriteArmorOnFire) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumerProvider, RenderType.armorCutoutNoCull(GILDED_NETHERITE_ARMOR), false);
            outerModel.renderToBuffer(matrixStack, vertexConsumer, 220, OverlayTexture.NO_OVERLAY);
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V", ordinal = 2))
    private void renderGlowingArmorTwoMixin(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light, T livingEntity, float f, float g, float h, float j, float k, float l,
            CallbackInfo info) {
        if (gildedNetheriteArmorOnFire) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumerProvider, RenderType.armorCutoutNoCull(GILDED_NETHERITE_ARMOR), false);
            outerModel.renderToBuffer(matrixStack, vertexConsumer, 220, OverlayTexture.NO_OVERLAY);
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderGlowingHelmetMixin(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light, T livingEntity, float f, float g, float h, float j, float k, float l,
            CallbackInfo info) {
        if (gildedNetheriteArmorOnFire) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumerProvider, RenderType.armorCutoutNoCull(GILDED_NETHERITE_HELMET), false);
            outerModel.renderToBuffer(matrixStack, vertexConsumer, 220, OverlayTexture.NO_OVERLAY);
        }
    }

}