package net.adventurez.network.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DragonFireBreathPacket() implements CustomPayload {

    public static final CustomPayload.Id<DragonFireBreathPacket> PACKET_ID = new CustomPayload.Id<>(Identifier.of("adventurez", "dragon_fire_breath_packet"));

    public static final PacketCodec<RegistryByteBuf, DragonFireBreathPacket> PACKET_CODEC = PacketCodec.of((value, buf) -> {
    }, buf -> new DragonFireBreathPacket());

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

}
