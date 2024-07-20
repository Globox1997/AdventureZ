package net.adventurez.network;

import net.adventurez.network.packet.VelocityPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public class AdventureClientPacket {

    @SuppressWarnings("resource")
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(VelocityPacket.PACKET_ID, (payload, context) -> {
            int entityId = payload.entityId();
            float velocity = payload.velocity();
            context.client().execute(() -> {
                context.client().world.getEntityById(entityId).addVelocity(0.0D, velocity, 0.0D);
            });
        });
    }

}
