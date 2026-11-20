package net.adventurez.network;

import net.adventurez.access.EntityAccess;
import net.adventurez.network.packet.BlackstonedGolemPacket;
import net.adventurez.network.packet.VelocityPacket;
import net.minecraft.client.Minecraft;

public class AdventureClientPacket {

    @SuppressWarnings("resource")
    public static void handleVelocity(VelocityPacket payload) {
        Minecraft client = Minecraft.getInstance();
        if (client.level != null && client.level.getEntity(payload.entityId()) != null) {
            client.level.getEntity(payload.entityId()).push(0.0D, payload.velocity(), 0.0D);
        }
    }

    @SuppressWarnings("resource")
    public static void handleBlackstonedGolem(BlackstonedGolemPacket payload) {
        Minecraft client = Minecraft.getInstance();
        if (client.level != null && client.level.getEntity(payload.entityId()) instanceof EntityAccess entityAccess) {
            entityAccess.setBlackstoned(payload.blackstoned());
        }
    }

}
