package net.adventurez.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class GeneralPacket {
    public static final Identifier VELOCITY_PACKET = new Identifier("adventurez", "velocity");

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(VELOCITY_PACKET, (client, handler, buf, sender) -> {
            int entityId = buf.readInt();
            float velocity = buf.readFloat();
           client.execute(()->{
               client.world.getEntityById(entityId).addVelocity(0.0D, velocity, 0.0D);
           });
        });
    }
}
