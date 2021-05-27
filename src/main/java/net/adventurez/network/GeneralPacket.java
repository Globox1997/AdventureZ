package net.adventurez.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class GeneralPacket {
    public static final Identifier VELOCITY_PACKET = new Identifier("adventurez", "velocity");

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(VELOCITY_PACKET, (client, handler, buf, sender) -> {
            client.player.setVelocity(0, 1.2D, 0);
        });
    }
}
