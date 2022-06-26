package net.adventurez.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class GeneralPacket {
    public static final Identifier VELOCITY_PACKET = new Identifier("adventurez", "velocity");

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(VELOCITY_PACKET, (client, handler, buf, sender) -> {
            if (client.player != null)
                client.world.getEntityById(buf.readInt()).addVelocity(0.0D, buf.readFloat(), 0.0D);
        });
    }
}
