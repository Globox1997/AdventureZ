package net.adventurez.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;

import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record GildedActivationComponent(boolean activated, int time, boolean visuals) {
    public static final GildedActivationComponent DEFAULT = new GildedActivationComponent(false, 0, false);

    public static final Codec<GildedActivationComponent> CODEC = RecordCodecBuilder
            .create(instance -> instance.group(Codec.BOOL.fieldOf("activated").forGetter(GildedActivationComponent::activated), Codec.INT.fieldOf("time").forGetter(GildedActivationComponent::time),
                    Codec.BOOL.fieldOf("visuals").forGetter(GildedActivationComponent::visuals)).apply(instance, GildedActivationComponent::new));

    public static final PacketCodec<ByteBuf, GildedActivationComponent> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.BOOL, GildedActivationComponent::activated, PacketCodecs.INTEGER,
            GildedActivationComponent::time, PacketCodecs.BOOL, GildedActivationComponent::visuals, GildedActivationComponent::new);

}
