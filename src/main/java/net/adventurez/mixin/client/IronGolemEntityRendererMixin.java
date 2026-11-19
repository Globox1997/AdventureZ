package net.adventurez.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.access.EntityAccess;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
@Mixin(IronGolemRenderer.class)
public class IronGolemEntityRendererMixin {

    private static final ResourceLocation BLACKSTONED_TEXTURE = ResourceLocation.parse("adventurez:textures/entity/blackstone_golem.png");

    @Inject(method = "getTextureLocation", at = @At("HEAD"), cancellable = true)
    private void getTextureMixin(IronGolem ironGolemEntity, CallbackInfoReturnable<ResourceLocation> info) {
        if (ironGolemEntity.getEntityData().get(((EntityAccess) ironGolemEntity).getTrackedDataBoolean())) {
            info.setReturnValue(BLACKSTONED_TEXTURE);
        }
    }
}
