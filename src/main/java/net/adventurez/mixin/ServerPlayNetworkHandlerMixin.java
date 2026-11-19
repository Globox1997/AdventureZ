package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.DragonEntity;
import net.adventurez.entity.EnderWhaleEntity;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow
    public ServerPlayer player;

    @Shadow
    private int aboveGroundVehicleTickCount;

    @Inject(method = "handlePlayerCommand", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;resetLastActionTime()V"))
    private void onClientCommandMixin(ServerboundPlayerCommandPacket packet, CallbackInfo info) {
        if (player.getVehicle() != null && player.getVehicle() instanceof DragonEntity && ((DragonEntity) this.player.getVehicle()).hasChest()) {
            ((DragonEntity) this.player.getVehicle()).openInventory(this.player);
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getControllingPassenger()Lnet/minecraft/world/entity/LivingEntity;", shift = Shift.AFTER, ordinal = 1))
    private void tickMixin(CallbackInfo info) {
        if (aboveGroundVehicleTickCount >= 70 && player.getVehicle() != null && (player.getVehicle() instanceof DragonEntity || player.getVehicle() instanceof EnderWhaleEntity)) {
            aboveGroundVehicleTickCount = 0;
        }
    }

}