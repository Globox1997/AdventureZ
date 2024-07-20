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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(ArrowEntity.class)
public abstract class ArrowEntityMixin extends PersistentProjectileEntity {

    @Shadow
    @Mutable
    @Final
    private static TrackedData<Integer> COLOR;

    public ArrowEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "Lnet/minecraft/entity/projectile/ArrowEntity;<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)V", at = @At("TAIL"))
    private void initMixin(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom, CallbackInfo info) {
        if (stack.isOf(ItemInit.IVORY_ARROW)) {
            this.setDamage(this.getDamage() + 4.0D);
        }
    }

    @Inject(method = "spawnParticles", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/projectile/ArrowEntity;getColor()I"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void spawnParticlesMixin(int amount, CallbackInfo info, int i) {
        if (i == 14340520) {
            info.cancel();
        }
    }

    @Inject(method = "initColor", at = @At("TAIL"), cancellable = true)
    private void initColorMixin(CallbackInfo info) {
        if (this.getItemStack().isOf(ItemInit.IVORY_ARROW)) {
            this.dataTracker.set(COLOR, 14340520);
        }
    }

}
