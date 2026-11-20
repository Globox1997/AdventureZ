package net.adventurez.mixin.accessor;

import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractArrow.class)
public interface ProjectileEntityAccessor {

    @Invoker("setPierceLevel")
    void callSetPierceLevel(byte level);

    @Accessor("baseDamage")
    double getBaseDamage();

}
