package net.adventurez.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.adventurez.access.BlackstonedRenderStateAccess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.IronGolemRenderState;

@Environment(EnvType.CLIENT)
@Mixin(IronGolemRenderState.class)
public class IronGolemRenderStateMixin implements BlackstonedRenderStateAccess {

    @Unique
    private boolean blackstoned;

    @Override
    public void setBlackstoned(boolean blackstoned) {
        this.blackstoned = blackstoned;
    }

    @Override
    public boolean isBlackstoned() {
        return this.blackstoned;
    }
}
