package net.adventurez.network;

import net.adventurez.entity.DragonEntity;
import net.minecraft.server.level.ServerPlayer;

public class AdventureServerPacket {

    public static void handleDragonFireBreath(ServerPlayer player) {
        if (player.getVehicle() instanceof DragonEntity dragonEntity && dragonEntity.getEntityData().get(DragonEntity.DRAGON_SIZE) >= 3) {
            dragonEntity.setFireBreathActive(true);
        }
    }

}
