package net.adventurez.init;

import net.adventurez.block.renderer.StoneHolderRenderer;
import net.adventurez.entity.render.*;
import net.adventurez.network.EntitySpawnPacket;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.render.RenderLayer;

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
    EntityRendererRegistry.INSTANCE.register(EntityInit.REDSTONE_ENTITY,
        (dispatcher, context) -> new RedStoneRenderer(dispatcher));

    // Network
    registerClientboundPackets();

    // Blocks
    BlockEntityRendererRegistry.INSTANCE.register(BlockInit.STONE_HOLDER_ENTITY, StoneHolderRenderer::new);
    BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.STONE_HOLDER_BLOCK, RenderLayer.getCutout());

  }
}