package net.adventurez.mixin;

import java.util.Set;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.world.World;

@Mixin(ArrowEntity.class)
public abstract class ArrowEntityMixin extends PersistentProjectileEntity {

    public ArrowEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    private Potion potion;
    @Shadow
    @Final
    private Set<StatusEffectInstance> effects;
    @Shadow
    @Final
    private static TrackedData<Integer> COLOR;

    @Inject(method = "initFromStack", at = @At("TAIL"))
    private void initFromStackMixin(ItemStack stack, CallbackInfo info) {
        if (stack.isOf(ItemInit.IVORY_ARROW)) {
            this.potion = Potions.EMPTY;
            this.effects.clear();
            this.setColor(14340520);
            this.setDamage(this.getDamage() + 4.0D);
        }
    }

    @Inject(method = "asItemStack", at = @At("HEAD"), cancellable = true)
    protected void asItemStackMixin(CallbackInfoReturnable<ItemStack> info) {
        if (this.dataTracker.get(COLOR).equals(14340520)) {
            info.setReturnValue(new ItemStack(ItemInit.IVORY_ARROW));
        }
    }

    @Inject(method = "spawnParticles", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/projectile/ArrowEntity;getColor()I"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void spawnParticlesMixin(int amount, CallbackInfo info, int i) {
        if (i == 14340520) {
            info.cancel();
        }
    }

    @Shadow
    private void setColor(int color) {
    }
}
