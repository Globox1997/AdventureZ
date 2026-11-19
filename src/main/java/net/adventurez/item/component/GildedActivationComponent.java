package net.adventurez.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record GildedActivationComponent(boolean activated, int time, boolean visuals) {
    public static final GildedActivationComponent DEFAULT = new GildedActivationComponent(false, 0, false);

    public static final Codec<GildedActivationComponent> CODEC = RecordCodecBuilder
            .create(instance -> instance.group(Codec.BOOL.fieldOf("activated").forGetter(GildedActivationComponent::activated), Codec.INT.fieldOf("time").forGetter(GildedActivationComponent::time),
                    Codec.BOOL.fieldOf("visuals").forGetter(GildedActivationComponent::visuals)).apply(instance, GildedActivationComponent::new));

    public static final StreamCodec<ByteBuf, GildedActivationComponent> PACKET_CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, GildedActivationComponent::activated, ByteBufCodecs.INT,
            GildedActivationComponent::time, ByteBufCodecs.BOOL, GildedActivationComponent::visuals, GildedActivationComponent::new);

}
