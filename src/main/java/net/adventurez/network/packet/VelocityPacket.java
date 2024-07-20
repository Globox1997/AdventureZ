package net.adventurez.network.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record VelocityPacket(int entityId, float velocity) implements CustomPayload {

    public static final CustomPayload.Id<VelocityPacket> PACKET_ID = new CustomPayload.Id<>(Identifier.of("adventurez", "velocity_packet"));

    public static final PacketCodec<RegistryByteBuf, VelocityPacket> PACKET_CODEC = PacketCodec.of((value, buf) -> {
        buf.writeInt(value.entityId);
        buf.writeFloat(value.velocity);
    }, buf -> new VelocityPacket(buf.readInt(), buf.readFloat()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

}
