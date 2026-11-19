package net.adventurez.mixin.accessor;

import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractArrow.class)
public interface ProjectileEntityAccessor {

    @Invoker("setPierceLevel")
    void callSetPierceLevel(byte level);

}
