package net.adventurez.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record VelocityPacket(int entityId, float velocity) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<VelocityPacket> PACKET_ID = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("adventurez", "velocity_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, VelocityPacket> PACKET_CODEC = StreamCodec.ofMember((value, buf) -> {
        buf.writeInt(value.entityId);
        buf.writeFloat(value.velocity);
    }, buf -> new VelocityPacket(buf.readInt(), buf.readFloat()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }

}
