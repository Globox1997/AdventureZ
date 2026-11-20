package net.adventurez.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record BlackstonedGolemPacket(int entityId, boolean blackstoned) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<BlackstonedGolemPacket> PACKET_ID = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("adventurez", "blackstoned_golem_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, BlackstonedGolemPacket> PACKET_CODEC = StreamCodec.ofMember((value, buf) -> {
        buf.writeInt(value.entityId);
        buf.writeBoolean(value.blackstoned);
    }, buf -> new BlackstonedGolemPacket(buf.readInt(), buf.readBoolean()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }

}
