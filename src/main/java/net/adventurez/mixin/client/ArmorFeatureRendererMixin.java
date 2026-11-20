package net.adventurez.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.adventurez.AdventureMain;
import net.adventurez.init.ItemInit;
import net.adventurez.item.GildedNetheriteArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;

@Environment(EnvType.CLIENT)
@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorFeatureRendererMixin<S extends HumanoidRenderState, M extends HumanoidModel<S>, A extends HumanoidModel<S>> {

    @Unique
    private static final Identifier GILDED_NETHERITE_ARMOR = AdventureMain.identifierOf("textures/models/armor/gilded_netherite_layer_1_overlay.png");
    @Unique
    private static final Identifier GILDED_NETHERITE_HELMET = AdventureMain.identifierOf("textures/models/armor/gilded_netherite_layer_1_overlay_helmet.png");

    @Shadow
    private A getArmorModel(S state, EquipmentSlot slot) {
        return null;
    }

    @Inject(method = "submit", at = @At("TAIL"))
    private void submitGlowingArmorMixin(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S state, float yRot, float xRot, CallbackInfo info) {
        if (state.chestEquipment.is(ItemInit.GILDED_NETHERITE_CHESTPLATE) && GildedNetheriteArmor.isStoneGolemArmorActive(state.chestEquipment)) {
            submitNodeCollector.submitModel(this.getArmorModel(state, EquipmentSlot.CHEST), state, poseStack, RenderTypes.armorCutoutNoCull(GILDED_NETHERITE_ARMOR), 220, OverlayTexture.NO_OVERLAY,
                    state.outlineColor);
            submitNodeCollector.submitModel(this.getArmorModel(state, EquipmentSlot.FEET), state, poseStack, RenderTypes.armorCutoutNoCull(GILDED_NETHERITE_ARMOR), 220, OverlayTexture.NO_OVERLAY,
                    state.outlineColor);
            submitNodeCollector.submitModel(this.getArmorModel(state, EquipmentSlot.HEAD), state, poseStack, RenderTypes.armorCutoutNoCull(GILDED_NETHERITE_HELMET), 220, OverlayTexture.NO_OVERLAY,
                    state.outlineColor);
        }
    }

}
