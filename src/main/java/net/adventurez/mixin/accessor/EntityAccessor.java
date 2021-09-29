package net.adventurez.mixin.accessor;

import com.google.common.collect.ImmutableList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public interface EntityAccessor {

    @Accessor("passengerList")
    ImmutableList<Entity> getPassengerList();
}
