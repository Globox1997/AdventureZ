package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.BlazeGuardianEntity;
import net.adventurez.entity.nonliving.BlazeGuardianShieldEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

@Mixin(SmallFireballEntity.class)
public abstract class SmallFireballEntityMixin extends AbstractFireballEntity {

    public SmallFireballEntityMixin(EntityType<? extends AbstractFireballEntity> entityType, LivingEntity livingEntity, double d, double e, double f, World world) {
        super(entityType, livingEntity, d, e, f, world);
    }

    @Inject(method = "onCollision", at = @At(value = "HEAD"), cancellable = true)
    protected void onCollision(HitResult hitResult, CallbackInfo info) {
        if (this.getOwner() != null && this.getOwner() instanceof BlazeGuardianEntity && hitResult.getType() == HitResult.Type.ENTITY
                && ((EntityHitResult) hitResult).getEntity() instanceof BlazeGuardianShieldEntity) {
            info.cancel();
        }
    }
}
