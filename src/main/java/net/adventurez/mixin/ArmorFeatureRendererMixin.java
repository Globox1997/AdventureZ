package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.adventurez.init.ItemInit;
import net.adventurez.item.armor.StoneGolemArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    @Shadow
    @Final
    @Mutable
    private A bodyModel;

    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context, A leggingsModel, A bodyModel) {
        super(context);
        this.bodyModel = bodyModel;
    }

    @Inject(method = "render", at = @At("FIELD"))
    private void renderGlowingArmorMixin(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l,
            CallbackInfo info) {
        ItemStack golemChestplate = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
        if (golemChestplate.getItem().equals(ItemInit.STONE_GOLEM_CHESTPLATE)) {
            if (StoneGolemArmor.fireTime(golemChestplate)) {
                VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider,
                        RenderLayer.getArmorCutoutNoCull(new Identifier("minecraft:textures/models/armor/stone_golem_layer_1_overlay.png")), true, false);
                bodyModel.render(matrixStack, vertexConsumer, 220, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }

    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderGlowingHelmetMixin(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l,
            CallbackInfo info) {
        ItemStack golemChestplate = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
        if (golemChestplate.getItem().equals(ItemInit.STONE_GOLEM_CHESTPLATE)) {
            if (StoneGolemArmor.fireTime(golemChestplate)) {
                VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider,
                        RenderLayer.getArmorCutoutNoCull(new Identifier("textures/models/armor/stone_golem_layer_1_overlay_helmet.png")), false, false);
                bodyModel.render(matrixStack, vertexConsumer, 220, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

            }
        }
    }

}