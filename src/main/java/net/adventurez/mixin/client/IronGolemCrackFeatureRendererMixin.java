package net.adventurez.mixin.client;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.IronGolemCrackinessLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.entity.animal.IronGolem;
import net.adventurez.access.EntityAccess;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
@Mixin(IronGolemCrackinessLayer.class)
public class IronGolemCrackFeatureRendererMixin {

    private static final Map<Crackiness.Level, ResourceLocation> BLACKSTONED_CRACKINESS_TO_TEXTURE = ImmutableMap.of(Crackiness.Level.LOW,
            ResourceLocation.parse("adventurez:textures/entity/feature/iron_golem_blackstoned_low.png"), Crackiness.Level.MEDIUM,
            ResourceLocation.parse("adventurez:textures/entity/feature/iron_golem_blackstoned_medium.png"), Crackiness.Level.HIGH,
            ResourceLocation.parse("adventurez:textures/entity/feature/iron_golem_blackstoned_high.png"));

    @ModifyVariable(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/IronGolemCrackinessLayer;getParentModel()Lnet/minecraft/client/model/EntityModel;"), ordinal = 0)
    private ResourceLocation renderMixin(ResourceLocation original, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, IronGolem ironGolemEntity, float f, float g, float h,
            float j, float k, float l) {
        if (ironGolemEntity.getEntityData().get(((EntityAccess) ironGolemEntity).getTrackedDataBoolean())) {
            return BLACKSTONED_CRACKINESS_TO_TEXTURE.get(ironGolemEntity.getCrackiness());
        } else {
            return original;
        }
    }

}
