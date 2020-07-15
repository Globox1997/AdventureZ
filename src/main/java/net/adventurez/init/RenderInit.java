package net.adventurez.init;

import net.adventurez.block.renderer.*;
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
        EntityRendererRegistry.INSTANCE.register(EntityInit.GILDEDSTONE_ENTITY,
                (dispatcher, context) -> new GildedStoneRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(EntityInit.SMALLSTONEGOLEM_ENTITY,
                (dispatcher, context) -> new SmallStoneGolemRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(EntityInit.PIGLINBEAST_ENTITY,
                (dispatcher, context) -> new PiglinBeastRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(EntityInit.NIGHTMARE_ENTITY,
                (dispatcher, context) -> new NightmareRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(EntityInit.SOULREAPER_ENTITY,
                (dispatcher, context) -> new SoulReaperRenderer(dispatcher));

        // Network
        registerClientboundPackets();

        // Blocks
        BlockEntityRendererRegistry.INSTANCE.register(BlockInit.STONE_HOLDER_ENTITY, StoneHolderRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.STONE_HOLDER_BLOCK, RenderLayer.getCutout());
        BlockEntityRendererRegistry.INSTANCE.register(BlockInit.PIGLIN_FLAG_ENTITY, PiglinFlagRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PIGLIN_FLAG_BLOCK, RenderLayer.getCutout());
    }
}