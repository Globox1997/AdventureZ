package net.adventurez.network;

import net.adventurez.entity.DragonEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class AdventureServerPacket {

    public static final Identifier VELOCITY_PACKET = new Identifier("adventurez", "velocity");
    public static final Identifier FIRE_BREATH_PACKET = new Identifier("adventurez", "fire_breath");

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(FIRE_BREATH_PACKET, (server, player, handler, buffer, sender) -> {
            server.execute(() -> {
                if (player.getVehicle() instanceof DragonEntity) {
                    ((DragonEntity) player.getVehicle()).fireBreathActive = true;
                }
            });
        });
    }

}
