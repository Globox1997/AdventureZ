package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.DragonEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "hasRidingInventory", at = @At(value = "HEAD"), cancellable = true)
    public void hasRidingInventoryMixin(CallbackInfoReturnable<Boolean> info) {
        if (this.client.player.hasVehicle() && this.client.player.getVehicle() instanceof DragonEntity && ((DragonEntity) this.client.player.getVehicle()).hasChest()) {
            info.setReturnValue(true);
        }
    }

}
