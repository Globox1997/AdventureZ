package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
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
import net.minecraft.util.math.MathHelper;

@Mixin(ArmorFeatureRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>>
    extends FeatureRenderer<T, M> {
  private final A bodyModel;

  public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context, A leggingsModel, A bodyModel) {
    super(context);
    this.bodyModel = bodyModel;
  }

  @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFFLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V", at = @At("FIELD"))
  private void gildedNetheriteActivated(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i,
      T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo info) {
    ItemStack golemChestplate = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
    if (livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(ItemInit.STONE_GOLEM_CHESTPLATE)) {
      if (StoneGolemArmor.fireTime(golemChestplate)) {
        VertexConsumer vertexConsumer = ItemRenderer
            .getArmorVertexConsumer(vertexConsumerProvider,
                RenderLayer.getArmorCutoutNoCull(
                    new Identifier("minecraft:textures/models/armor/stone_golem_armor_layer_1_overlay.png")),
                true, false);
        long worldTime = livingEntity.getEntityWorld().getTime();
        float goodFloat = ((float) Math.floorMod((long) +worldTime, 100L) + h) / 100.0F;
        bodyModel.render(matrixStack, vertexConsumer, (int) (((MathHelper.cos(6.2831855F * goodFloat)) * 100) + 220),
            OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      }
    }
  }

  @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFFLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V", at = @At("TAIL"))
  private void gildedNetheriteActivatedHelmet(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider,
      int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo info) {
    ItemStack golemChestplate = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
    if (livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(ItemInit.STONE_GOLEM_CHESTPLATE)) {
      if (StoneGolemArmor.fireTime(golemChestplate)) {
        VertexConsumer vertexConsumer = ItemRenderer
            .getArmorVertexConsumer(vertexConsumerProvider,
                RenderLayer.getArmorCutoutNoCull(
                    new Identifier("textures/models/armor/stone_golem_armor_layer_1_overlay_helmet.png")),
                false, false);
        long worldTime = livingEntity.getEntityWorld().getTime();
        float goodFloat = ((float) Math.floorMod((long) worldTime, 100L) + h) / 100.0F;
        bodyModel.render(matrixStack, vertexConsumer, (int) (((MathHelper.cos(6.2831855F * goodFloat)) * 100) + 220),
            OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

      }
    }
  }

}