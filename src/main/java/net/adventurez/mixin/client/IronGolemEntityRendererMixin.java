package net.adventurez.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.access.BlackstonedRenderStateAccess;
import net.adventurez.access.EntityAccess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.client.renderer.entity.state.IronGolemRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.golem.IronGolem;

@Environment(EnvType.CLIENT)
@Mixin(IronGolemRenderer.class)
public class IronGolemEntityRendererMixin {

    private static final Identifier BLACKSTONED_TEXTURE = Identifier.parse("adventurez:textures/entity/blackstone_golem.png");

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void extractRenderStateMixin(IronGolem ironGolemEntity, IronGolemRenderState state, float partialTicks, CallbackInfo info) {
        ((BlackstonedRenderStateAccess) state).setBlackstoned(ironGolemEntity.getEntityData().get(((EntityAccess) ironGolemEntity).getTrackedDataBoolean()));
    }

    @Inject(method = "getTextureLocation", at = @At("HEAD"), cancellable = true)
    private void getTextureMixin(IronGolemRenderState state, CallbackInfoReturnable<Identifier> info) {
        if (((BlackstonedRenderStateAccess) state).isBlackstoned()) {
            info.setReturnValue(BLACKSTONED_TEXTURE);
        }
    }
}
