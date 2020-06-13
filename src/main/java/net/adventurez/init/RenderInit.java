package net.adventurez.init;

import net.adventurez.entity.render.*;
import net.adventurez.network.EntitySpawnPacket;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

public class RenderInit {
  public static void registerClientboundPackets() {
    ClientSidePacketRegistry.INSTANCE.register(EntitySpawnPacket.ID, EntitySpawnPacket::onPacket);
  }
  public static void init() {

    EntityRendererRegistry.INSTANCE.register(EntityInit.STONEGOLEM_ENTITY,
        (dispatcher, context) -> new StoneGolemRenderer(dispatcher));
    EntityRendererRegistry.INSTANCE.register(EntityInit.THROWNROCK_ENTITY,
        (dispatcher, context) -> new ThrownRockRenderer(dispatcher));
    registerClientboundPackets();


  }
}