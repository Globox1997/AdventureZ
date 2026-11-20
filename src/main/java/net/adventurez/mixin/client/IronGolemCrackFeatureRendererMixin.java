package net.adventurez.mixin.client;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;

import net.adventurez.access.BlackstonedRenderStateAccess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.IronGolemCrackinessLayer;
import net.minecraft.client.renderer.entity.state.IronGolemRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Crackiness;

@Environment(EnvType.CLIENT)
@Mixin(IronGolemCrackinessLayer.class)
public class IronGolemCrackFeatureRendererMixin {

    private static final Map<Crackiness.Level, Identifier> BLACKSTONED_CRACKINESS_TO_TEXTURE = ImmutableMap.of(Crackiness.Level.LOW,
            Identifier.parse("adventurez:textures/entity/feature/iron_golem_blackstoned_low.png"), Crackiness.Level.MEDIUM,
            Identifier.parse("adventurez:textures/entity/feature/iron_golem_blackstoned_medium.png"), Crackiness.Level.HIGH,
            Identifier.parse("adventurez:textures/entity/feature/iron_golem_blackstoned_high.png"));

    @ModifyVariable(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/IronGolemCrackinessLayer;getParentModel()Lnet/minecraft/client/model/EntityModel;"), ordinal = 0)
    private Identifier renderMixin(Identifier original, PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int i, IronGolemRenderState state, float yRot, float xRot) {
        if (((BlackstonedRenderStateAccess) state).isBlackstoned()) {
            return BLACKSTONED_CRACKINESS_TO_TEXTURE.get(state.crackiness);
        } else {
            return original;
        }
    }

}
