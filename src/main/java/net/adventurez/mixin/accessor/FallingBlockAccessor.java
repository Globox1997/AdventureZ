package net.adventurez.mixin.accessor;

import net.minecraft.entity.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FallingBlockEntity.class)
public interface FallingBlockAccessor {
    @Accessor("destroyedOnLanding")
    void setDestroyedOnLanding(boolean destroyedOnLanding);
}
