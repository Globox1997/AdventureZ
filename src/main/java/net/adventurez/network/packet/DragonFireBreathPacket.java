package net.adventurez.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DragonFireBreathPacket() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<DragonFireBreathPacket> PACKET_ID = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("adventurez", "dragon_fire_breath_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DragonFireBreathPacket> PACKET_CODEC = StreamCodec.ofMember((value, buf) -> {
    }, buf -> new DragonFireBreathPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }

}
