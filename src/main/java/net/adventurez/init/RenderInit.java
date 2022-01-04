package net.adventurez.init;

import net.adventurez.block.renderer.*;
import net.adventurez.entity.model.*;
import net.adventurez.entity.render.*;
import net.adventurez.init.ParticleInit.ShardParticle;
import net.adventurez.init.ParticleInit.SprintParticle;
import net.adventurez.init.ParticleInit.VoidCloudParticle;
import net.adventurez.network.EntitySpawnPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RenderInit {
    public static final boolean isCanvasLoaded = FabricLoader.getInstance().isModLoaded("canvas");
    public static final boolean isIrisLoaded = FabricLoader.getInstance().isModLoaded("iris");
    public static final boolean isSodiumLoaded = FabricLoader.getInstance().isModLoaded("sodium");

    public static final EntityModelLayer STONE_GOLEM_LAYER = new EntityModelLayer(new Identifier("adventurez:stone_golem_render_layer"), "stone_golem_render_layer");
    public static final EntityModelLayer THROWN_ROCK_LAYER = new EntityModelLayer(new Identifier("adventurez:thrown_rock_render_layer"), "thrown_rock_render_layer");
    public static final EntityModelLayer GILDED_STONE_LAYER = new EntityModelLayer(new Identifier("adventurez:gilded_stone_render_layer"), "gilded_stone_render_layer");
    public static final EntityModelLayer SMALL_STONE_GOLEM_LAYER = new EntityModelLayer(new Identifier("adventurez:small_stome_golem_render_layer"), "small_stome_golem_render_layer");
    public static final EntityModelLayer PIGLIN_BEAST_LAYER = new EntityModelLayer(new Identifier("adventurez:piglin_beast_render_layer"), "piglin_beast_render_layer");
    public static final EntityModelLayer NIGHTMARE_LAYER = new EntityModelLayer(new Identifier("adventurez:nightmare_render_layer"), "nightmare_render_layer");
    public static final EntityModelLayer SOUL_REAPER_LAYER = new EntityModelLayer(new Identifier("adventurez:soul_reaper_render_layer"), "soul_reaper_render_layer");
    public static final EntityModelLayer NECROMANCER_LAYER = new EntityModelLayer(new Identifier("adventurez:necromancer_render_layer"), "necromancer_render_layer");
    public static final EntityModelLayer WITHER_PUPPET_LAYER = new EntityModelLayer(new Identifier("adventurez:wither_puppet_render_layer"), "wither_puppet_render_layer");
    public static final EntityModelLayer SKELETON_VANGUARD_LAYER = new EntityModelLayer(new Identifier("adventurez:skeleton_vanguard_render_layer"), "skeleton_vanguard_render_layer");
    public static final EntityModelLayer SUMMONER_LAYER = new EntityModelLayer(new Identifier("adventurez:summoner_render_layer"), "summoner_render_layer");
    public static final EntityModelLayer BLAZE_GUARDIAN_LAYER = new EntityModelLayer(new Identifier("adventurez:blaze_guardian_render_layer"), "blaze_guardian_render_layer");
    public static final EntityModelLayer THE_EYE_LAYER = new EntityModelLayer(new Identifier("adventurez:the_eye_render_layer"), "the_eye_render_layer");
    public static final EntityModelLayer VOID_SHADOW_LAYER = new EntityModelLayer(new Identifier("adventurez:void_shadow_render_layer"), "void_shadow_render_layer");
    public static final EntityModelLayer TINY_EYE_LAYER = new EntityModelLayer(new Identifier("adventurez:tiny_eye_render_layer"), "tiny_eye_render_layer");
    public static final EntityModelLayer RED_FUNGUS_LAYER = new EntityModelLayer(new Identifier("adventurez:red_fungus_render_layer"), "red_fungus_render_layer");
    public static final EntityModelLayer BROWN_FUNGUS_LAYER = new EntityModelLayer(new Identifier("adventurez:brown_fungus_render_layer"), "brown_fungus_render_layer");
    public static final EntityModelLayer ORC_LAYER = new EntityModelLayer(new Identifier("adventurez:orc_render_layer"), "orc_render_layer");
    public static final EntityModelLayer DRAGON_LAYER = new EntityModelLayer(new Identifier("adventurez:dragon_render_layer"), "dragon_render_layer");
    public static final EntityModelLayer MAMMOTH_LAYER = new EntityModelLayer(new Identifier("adventurez:mammoth_render_layer"), "mammoth_render_layer");
    public static final EntityModelLayer VOID_FRAGMENT_LAYER = new EntityModelLayer(new Identifier("adventurez:void_fragment_render_layer"), "void_fragment_render_layer");
    public static final EntityModelLayer VOID_SHADE_LAYER = new EntityModelLayer(new Identifier("adventurez:void_shade_render_layer"), "void_shade_render_layer");
    public static final EntityModelLayer VOID_BULLET_LAYER = new EntityModelLayer(new Identifier("adventurez:void_bullet_render_layer"), "void_bullet_render_layer");
    public static final EntityModelLayer PIGLIN_FLAG_LAYER = new EntityModelLayer(new Identifier("adventurez:piglin_flag_render_layer"), "piglin_flag_render_layer");
    public static final EntityModelLayer ENDER_WHALE_LAYER = new EntityModelLayer(new Identifier("adventurez:ender_whale_render_layer"), "ender_whale_render_layer");
    public static final EntityModelLayer IGUANA_LAYER = new EntityModelLayer(new Identifier("adventurez:iguana_render_layer"), "iguana_render_layer");
    public static final EntityModelLayer AMETHYST_GOLEM_LAYER = new EntityModelLayer(new Identifier("adventurez:amethyst_golem_render_layer"), "amethyst_golem_render_layer");
    public static final EntityModelLayer AMETHYST_SHARD_LAYER = new EntityModelLayer(new Identifier("adventurez:amethyst_shard_render_layer"), "amethyst_shard_render_layer");
    public static final EntityModelLayer DESERT_RHINO_LAYER = new EntityModelLayer(new Identifier("adventurez:desert_rhino_render_layer"), "desert_rhino_render_layer");
    public static final EntityModelLayer SHAMAN_LAYER = new EntityModelLayer(new Identifier("adventurez:shaman_render_layer"), "shaman_render_layer");
    public static final EntityModelLayer DEER_LAYER = new EntityModelLayer(new Identifier("adventurez:deer_render_layer"), "deer_render_layer");
    public static final EntityModelLayer ENDERWARTHOG_LAYER = new EntityModelLayer(new Identifier("adventurez:enderwarthog_render_layer"), "enderwarthog_render_layer");

    public static void init() {
        // Entity Renderer
        EntityRendererRegistry.register(EntityInit.STONEGOLEM_ENTITY, StoneGolemRenderer::new);
        EntityRendererRegistry.register(EntityInit.THROWNROCK_ENTITY, ThrownRockRenderer::new);
        EntityRendererRegistry.register(EntityInit.GILDEDSTONE_ENTITY, GildedStoneRenderer::new);
        EntityRendererRegistry.register(EntityInit.SMALLSTONEGOLEM_ENTITY, SmallStoneGolemRenderer::new);
        EntityRendererRegistry.register(EntityInit.PIGLINBEAST_ENTITY, PiglinBeastRenderer::new);
        EntityRendererRegistry.register(EntityInit.NIGHTMARE_ENTITY, NightmareRenderer::new);
        EntityRendererRegistry.register(EntityInit.SOULREAPER_ENTITY, SoulReaperRenderer::new);
        EntityRendererRegistry.register(EntityInit.NECROMANCER_ENTITY, NecromancerRenderer::new);
        EntityRendererRegistry.register(EntityInit.WITHERPUPPET_ENTITY, WitherPuppetRenderer::new);
        EntityRendererRegistry.register(EntityInit.SKELETON_VANGUARD_ENTITY, SkeletonVanguardRenderer::new);
        EntityRendererRegistry.register(EntityInit.SUMMONER_ENTITY, SummonerRenderer::new);
        EntityRendererRegistry.register(EntityInit.BLAZEGUARDIAN_ENTITY, BlazeGuardianRenderer::new);
        EntityRendererRegistry.register(EntityInit.THE_EYE_ENTITY, TheEyeRenderer::new);
        EntityRendererRegistry.register(EntityInit.VOID_SHADOW_ENTITY, VoidShadowRenderer::new);
        EntityRendererRegistry.register(EntityInit.TINYEYE_ENTITY, TinyEyeRenderer::new);
        EntityRendererRegistry.register(EntityInit.RED_FUNGUS_ENTITY, RedFungusRenderer::new);
        EntityRendererRegistry.register(EntityInit.BROWN_FUNGUS_ENTITY, BrownFungusRenderer::new);
        EntityRendererRegistry.register(EntityInit.ORC_ENTITY, OrcRenderer::new);
        EntityRendererRegistry.register(EntityInit.DRAGON_ENTITY, DragonRenderer::new);
        EntityRendererRegistry.register(EntityInit.MAMMOTH_ENTITY, MammothRenderer::new);
        EntityRendererRegistry.register(EntityInit.VOID_FRAGMENT_ENTITY, VoidFragmentRenderer::new);
        EntityRendererRegistry.register(EntityInit.VOID_SHADE_ENTITY, VoidShadeRenderer::new);
        EntityRendererRegistry.register(EntityInit.VOID_BULLET_ENTITY, VoidBulletRenderer::new);
        EntityRendererRegistry.register(EntityInit.FIRE_BREATH_ENTITY, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(EntityInit.BLAZEGUARDIAN_SHIELD_ENTITY, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(EntityInit.ENDER_WHALE_ENTITY, EnderWhaleRenderer::new);
        EntityRendererRegistry.register(EntityInit.IGUANA_ENTITY, IguanaRenderer::new);
        EntityRendererRegistry.register(EntityInit.AMETHYST_GOLEM_ENTITY, AmethystGolemRenderer::new);
        EntityRendererRegistry.register(EntityInit.AMETHYST_SHARD_ENTITY, AmethystShardRenderer::new);
        EntityRendererRegistry.register(EntityInit.DESERT_RHINO_ENTITY, DesertRhinoRenderer::new);
        EntityRendererRegistry.register(EntityInit.VOID_CLOUD_ENTITY, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(EntityInit.SHAMAN_ENTITY, ShamanRenderer::new);
        EntityRendererRegistry.register(EntityInit.DEER_ENTITY, DeerRenderer::new);
        EntityRendererRegistry.register(EntityInit.ENDERWARTHOG_ENTITY, EnderwarthogRenderer::new);

        // Entity Layer
        EntityModelLayerRegistry.registerModelLayer(STONE_GOLEM_LAYER, StoneGolemModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(THROWN_ROCK_LAYER, RockModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GILDED_STONE_LAYER, GildedStoneModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SMALL_STONE_GOLEM_LAYER, SmallStoneGolemModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(PIGLIN_BEAST_LAYER, PiglinBeastModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SOUL_REAPER_LAYER, SoulReaperModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(NECROMANCER_LAYER, NecromancerModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WITHER_PUPPET_LAYER, WitherPuppetModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SKELETON_VANGUARD_LAYER, SkeletonVanguardModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SUMMONER_LAYER, SummonerModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BLAZE_GUARDIAN_LAYER, BlazeGuardianModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(THE_EYE_LAYER, TheEyeModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(VOID_SHADOW_LAYER, VoidShadowModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(TINY_EYE_LAYER, TinyEyeModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(RED_FUNGUS_LAYER, RedFungusModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BROWN_FUNGUS_LAYER, BrownFungusModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ORC_LAYER, OrcModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DRAGON_LAYER, DragonModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MAMMOTH_LAYER, MammothModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(VOID_FRAGMENT_LAYER, VoidFragmentModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(VOID_SHADE_LAYER, VoidShadeModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(VOID_BULLET_LAYER, VoidBulletModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(PIGLIN_FLAG_LAYER, PiglinFlagRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ENDER_WHALE_LAYER, EnderWhaleModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(IGUANA_LAYER, IguanaModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AMETHYST_GOLEM_LAYER, AmethystGolemModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AMETHYST_SHARD_LAYER, AmethystShardModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DESERT_RHINO_LAYER, DesertRhinoModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SHAMAN_LAYER, ShamanModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DEER_LAYER, DeerModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ENDERWARTHOG_LAYER, EnderwarthogModel::getTexturedModelData);

        // Network
        ClientPlayNetworking.registerGlobalReceiver(EntitySpawnPacket.ID, EntitySpawnPacket::onPacket);

        // Sprite Callback
        ClientSpriteRegistryCallback.event(new Identifier("textures/atlas/chest.png"))
                .register((spriteAtlasTexture, registry) -> registry.register(new Identifier("entity/chest/shadow_chest_block")));

        // Blocks
        BlockEntityRendererRegistry.register(BlockInit.STONE_HOLDER_ENTITY, StoneHolderRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.STONE_HOLDER_BLOCK, RenderLayer.getCutout());
        BlockEntityRendererRegistry.register(BlockInit.PIGLIN_FLAG_ENTITY, PiglinFlagRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PIGLIN_FLAG_BLOCK, RenderLayer.getCutout());
        BlockEntityRendererRegistry.register(BlockInit.SHADOW_CHEST_ENTITY, ChestBlockEntityRenderer::new);

        // Particles
        ParticleFactoryRegistry.getInstance().register(ParticleInit.AMETHYST_SHARD_PARTICLE, ShardParticle.ShardFactory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleInit.VOID_CLOUD_PARTICLE, VoidCloudParticle.CloudFactory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleInit.SPRINT_PARTICLE, SprintParticle.SprintFactory::new);
    }
}