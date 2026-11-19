package net.adventurez.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.DragonEntity;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
@Mixin(MultiPlayerGameMode.class)
public class ClientPlayerInteractionManagerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "isServerControlledInventory", at = @At(value = "HEAD"), cancellable = true)
    public void hasRidingInventoryMixin(CallbackInfoReturnable<Boolean> info) {
        if (this.minecraft.player.isPassenger() && this.minecraft.player.getVehicle() instanceof DragonEntity dragonEntity && dragonEntity.hasChest()) {
            info.setReturnValue(true);
        }
    }

}
