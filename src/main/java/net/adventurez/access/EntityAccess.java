package net.adventurez.access;

import net.minecraft.network.syncher.EntityDataAccessor;

public interface EntityAccess {

    public EntityDataAccessor<Boolean> getTrackedDataBoolean();
}
