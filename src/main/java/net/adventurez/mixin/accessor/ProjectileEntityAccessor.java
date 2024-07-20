package net.adventurez.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.projectile.PersistentProjectileEntity;

@Mixin(PersistentProjectileEntity.class)
public interface ProjectileEntityAccessor {

    @Invoker("setPierceLevel")
    void callSetPierceLevel(byte level);

}
