package net.adventurez.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.entity.monster.Creeper;

@Mixin(Creeper.class)
public interface CreeperAccessor {

    @Accessor("droppedSkulls")
    boolean getDroppedSkulls();

    @Accessor("droppedSkulls")
    void setDroppedSkulls(boolean droppedSkulls);
}
