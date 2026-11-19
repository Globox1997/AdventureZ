package net.adventurez.mixin.accessor;

import net.minecraft.world.level.block.entity.ChestLidController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChestLidController.class)
public interface ChestLidAnimatorAccessor {

    @Accessor("shouldBeOpen")
    boolean getOpen();
}
