package net.adventurez.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.access.EntityAccess;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.entity.IronGolemEntityRenderer;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@Mixin(IronGolemEntityRenderer.class)
public class IronGolemEntityRendererMixin {

    private static final Identifier BLACKSTONED_TEXTURE = new Identifier("adventurez:textures/entity/stone_golem.png");

    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void getTextureMixin(IronGolemEntity ironGolemEntity, CallbackInfoReturnable<Identifier> info) {
        if (ironGolemEntity.getDataTracker().get(((EntityAccess) ironGolemEntity).getTrackedDataBoolean())) {
            info.setReturnValue(BLACKSTONED_TEXTURE);
        }
    }
}
