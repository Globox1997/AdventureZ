package net.adventurez.network;

import net.adventurez.entity.DragonEntity;
import net.adventurez.network.packet.DragonFireBreathPacket;
import net.adventurez.network.packet.VelocityPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class AdventureServerPacket {

    public static void init() {
        PayloadTypeRegistry.serverboundPlay().register(DragonFireBreathPacket.PACKET_ID, DragonFireBreathPacket.PACKET_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(VelocityPacket.PACKET_ID, VelocityPacket.PACKET_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(DragonFireBreathPacket.PACKET_ID, (payload, context) -> {
            context.server().execute(() -> {
                if (context.player().getVehicle() instanceof DragonEntity dragonEntity && dragonEntity.getEntityData().get(DragonEntity.DRAGON_SIZE) >= 3) {
                    dragonEntity.setFireBreathActive(true);
                }
            });
        });
    }

}
