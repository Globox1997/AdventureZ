package net.adventurez.mixin.client;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.fabricmc.api.Environment;
import net.adventurez.access.EntityAccess;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.IronGolemCrackFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@Mixin(IronGolemCrackFeatureRenderer.class)
public class IronGolemCrackFeatureRendererMixin {

    private static final Map<IronGolemEntity.Crack, Identifier> BLACKSTONED_CRACKINESS_TO_TEXTURE;

    @ModifyVariable(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/IronGolemCrackFeatureRenderer;getContextModel()Lnet/minecraft/client/render/entity/model/EntityModel;"), ordinal = 0)
    private Identifier renderMixin(Identifier original, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, IronGolemEntity ironGolemEntity, float f, float g, float h,
            float j, float k, float l) {
        if (ironGolemEntity.getDataTracker().get(((EntityAccess) ironGolemEntity).getTrackedDataBoolean())) {
            return BLACKSTONED_CRACKINESS_TO_TEXTURE.get(ironGolemEntity.getCrack());
        } else {
            return original;
        }
    }

    static {
        BLACKSTONED_CRACKINESS_TO_TEXTURE = ImmutableMap.of(IronGolemEntity.Crack.LOW, new Identifier("adventurez:textures/entity/feature/iron_golem_blackstoned_low.png"),
                IronGolemEntity.Crack.MEDIUM, new Identifier("adventurez:textures/entity/feature/iron_golem_blackstoned_medium.png"), IronGolemEntity.Crack.HIGH,
                new Identifier("adventurez:textures/entity/feature/iron_golem_blackstoned_high.png"));
    }
}
