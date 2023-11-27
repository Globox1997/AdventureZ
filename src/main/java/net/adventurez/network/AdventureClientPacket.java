package net.adventurez.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public class AdventureClientPacket {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(AdventureServerPacket.VELOCITY_PACKET, (client, handler, buf, sender) -> {
            int entityId = buf.readInt();
            float velocity = buf.readFloat();
            client.execute(() -> {
                client.world.getEntityById(entityId).addVelocity(0.0D, velocity, 0.0D);
            });
        });
    }

}
