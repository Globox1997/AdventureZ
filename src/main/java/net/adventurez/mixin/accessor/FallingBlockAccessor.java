package net.adventurez.mixin.accessor;

import net.minecraft.world.entity.item.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FallingBlockEntity.class)
public interface FallingBlockAccessor {

    @Accessor("cancelDrop")
    void setDestroyedOnLanding(boolean destroyedOnLanding);
}