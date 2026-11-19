package net.adventurez.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.ItemInit;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(Arrow.class)
public abstract class ArrowEntityMixin extends AbstractArrow {

    @Shadow
    @Mutable
    @Final
    private static EntityDataAccessor<Integer> ID_EFFECT_COLOR;

    public ArrowEntityMixin(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
    private void initMixin(Level world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom, CallbackInfo info) {
        if (stack.is(ItemInit.IVORY_ARROW)) {
            this.setBaseDamage(this.getBaseDamage() + 4.0D);
        }
    }

    @Inject(method = "makeParticle", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/projectile/Arrow;getColor()I"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void spawnParticlesMixin(int amount, CallbackInfo info, int i) {
        if (i == 14340520) {
            info.cancel();
        }
    }

    @Inject(method = "updateColor", at = @At("TAIL"), cancellable = true)
    private void initColorMixin(CallbackInfo info) {
        if (this.getPickupItemStackOrigin().is(ItemInit.IVORY_ARROW)) {
            this.entityData.set(ID_EFFECT_COLOR, 14340520);
        }
    }

}
