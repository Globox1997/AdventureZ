package net.adventurez.init;

import net.adventurez.block.renderer.*;
import net.adventurez.entity.render.*;
import net.adventurez.network.EntitySpawnPacket;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class RenderInit {

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
                EntityRendererRegistry.INSTANCE.register(EntityInit.NECROMANCER_ENTITY,
                                (dispatcher, context) -> new NecromancerRenderer(dispatcher));
                EntityRendererRegistry.INSTANCE.register(EntityInit.WITHERPUPPET_ENTITY,
                                (dispatcher, context) -> new WitherPuppetRenderer(dispatcher));
                EntityRendererRegistry.INSTANCE.register(EntityInit.SKELETON_VANGUARD_ENTITY,
                                (dispatcher, context) -> new SkeletonVanguardRenderer(dispatcher));
                EntityRendererRegistry.INSTANCE.register(EntityInit.SUMMONER_ENTITY,
                                (dispatcher, context) -> new SummonerRenderer(dispatcher));
                EntityRendererRegistry.INSTANCE.register(EntityInit.BLAZEGUARDIAN_ENTITY,
                                (dispatcher, context) -> new BlazeGuardianRenderer(dispatcher));
                EntityRendererRegistry.INSTANCE.register(EntityInit.THEEYE_ENTITY,
                                (dispatcher, context) -> new TheEyeRenderer(dispatcher));
                EntityRendererRegistry.INSTANCE.register(EntityInit.VOID_SHADOW_ENTITY,
                                (dispatcher, context) -> new VoidShadowRenderer(dispatcher));
                EntityRendererRegistry.INSTANCE.register(EntityInit.TINYEYE_ENTITY,
                                (dispatcher, context) -> new TinyEyeRenderer(dispatcher));
                EntityRendererRegistry.INSTANCE.register(EntityInit.RED_FUNGUS_ENTITY,
                                (dispatcher, context) -> new RedFungusRenderer(dispatcher));
                EntityRendererRegistry.INSTANCE.register(EntityInit.BROWN_FUNGUS_ENTITY,
                                (dispatcher, context) -> new BrownFungusRenderer(dispatcher));
                EntityRendererRegistry.INSTANCE.register(EntityInit.ORC_ENTITY,
                                (dispatcher, context) -> new OrcRenderer(dispatcher));
                EntityRendererRegistry.INSTANCE.register(EntityInit.DRAGON_ENTITY,
                                (dispatcher, context) -> new DragonRenderer(dispatcher));

                // Network
                ClientPlayNetworking.registerGlobalReceiver(EntitySpawnPacket.ID, EntitySpawnPacket::onPacket);

                // Blocks
                BlockEntityRendererRegistry.INSTANCE.register(BlockInit.STONE_HOLDER_ENTITY, StoneHolderRenderer::new);
                BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.STONE_HOLDER_BLOCK, RenderLayer.getCutout());
                BlockEntityRendererRegistry.INSTANCE.register(BlockInit.PIGLIN_FLAG_ENTITY, PiglinFlagRenderer::new);
                BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PIGLIN_FLAG_BLOCK, RenderLayer.getCutout());
        }
}