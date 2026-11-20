package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.entity.Entity;
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

    @Inject(method = "handlePlayerCommand", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;resetLastActionTime()V"))
    private void onClientCommandMixin(ServerboundPlayerCommandPacket packet, CallbackInfo info) {
        if (player.getVehicle() != null && player.getVehicle() instanceof DragonEntity && ((DragonEntity) this.player.getVehicle()).hasChest()) {
            ((DragonEntity) this.player.getVehicle()).openInventory(this.player);
        }
    }

    @Inject(method = "getMaximumFlyingTicks", at = @At(value = "HEAD"), cancellable = true)
    private void getMaximumFlyingTicksMixin(Entity entity, CallbackInfoReturnable<Integer> info) {
        if (entity instanceof DragonEntity || entity instanceof EnderWhaleEntity) {
            info.setReturnValue(Integer.MAX_VALUE);
        }
    }

}