package net.adventurez.platform.services;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public interface INetworkHelper {

    boolean canSendToPlayer(ServerPlayer player, CustomPacketPayload.Type<?> type);

    boolean canSendToServer(CustomPacketPayload.Type<?> type);

    void sendToPlayer(ServerPlayer player, CustomPacketPayload payload);

    void sendToTracking(Entity entity, CustomPacketPayload payload);

    void sendToServer(CustomPacketPayload payload);

}
