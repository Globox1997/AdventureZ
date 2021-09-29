package net.adventurez.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.entity.ChestLidAnimator;

@Mixin(ChestLidAnimator.class)
public interface ChestLidAnimatorAccessor {

    @Accessor("open")
    boolean getOpen();
}
