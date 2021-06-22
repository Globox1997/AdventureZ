package net.adventurez.init;

import net.adventurez.block.renderer.*;
import net.adventurez.entity.model.*;
import net.adventurez.entity.render.*;
import net.adventurez.network.EntitySpawnPacket;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class RenderInit {

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
    public static final EntityModelLayer FIRE_BREATH_LAYER = new EntityModelLayer(new Identifier("adventurez:fire_breath_render_layer"), "fire_breath_render_layer");

    public static final EntityModelLayer PIGLIN_FLAG_LAYER = new EntityModelLayer(new Identifier("adventurez:piglin_flag_render_layer"), "piglin_flag_render_layer");

    public static void init() {
        // Entity Renderer
        EntityRendererRegistry.INSTANCE.register(EntityInit.STONEGOLEM_ENTITY, StoneGolemRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.THROWNROCK_ENTITY, ThrownRockRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.GILDEDSTONE_ENTITY, GildedStoneRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.SMALLSTONEGOLEM_ENTITY, SmallStoneGolemRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.PIGLINBEAST_ENTITY, PiglinBeastRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.NIGHTMARE_ENTITY, NightmareRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.SOULREAPER_ENTITY, SoulReaperRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.NECROMANCER_ENTITY, NecromancerRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.WITHERPUPPET_ENTITY, WitherPuppetRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.SKELETON_VANGUARD_ENTITY, SkeletonVanguardRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.SUMMONER_ENTITY, SummonerRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.BLAZEGUARDIAN_ENTITY, BlazeGuardianRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.THE_EYE_ENTITY, TheEyeRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.VOID_SHADOW_ENTITY, VoidShadowRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.TINYEYE_ENTITY, TinyEyeRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.RED_FUNGUS_ENTITY, RedFungusRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.BROWN_FUNGUS_ENTITY, BrownFungusRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.ORC_ENTITY, OrcRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.DRAGON_ENTITY, DragonRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.MAMMOTH_ENTITY, MammothRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.VOID_FRAGMENT_ENTITY, VoidFragmentRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.VOID_SHADE_ENTITY, VoidShadeRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.VOID_BULLET_ENTITY, VoidBulletRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityInit.FIRE_BREATH_ENTITY, FireBreathRenderer::new);

        // Entity Layer
        EntityModelLayerRegistry.registerModelLayer(STONE_GOLEM_LAYER, StoneGolemModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(THROWN_ROCK_LAYER, RockModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GILDED_STONE_LAYER, GildedStoneModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SMALL_STONE_GOLEM_LAYER, SmallStoneGolemModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(PIGLIN_BEAST_LAYER, PiglinBeastModel::getTexturedModelData);
        // EntityModelLayerRegistry.registerModelLayer(NIGHTMARE_LAYER, NightmareModel::getTexturedModelData);
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
        // EntityModelLayerRegistry.registerModelLayer(FIRE_BREATH_LAYER, FireBreathModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(PIGLIN_FLAG_LAYER, PiglinFlagRenderer::getTexturedModelData);

        // Network
        ClientPlayNetworking.registerGlobalReceiver(EntitySpawnPacket.ID, EntitySpawnPacket::onPacket);

        // Blocks
        BlockEntityRendererRegistry.INSTANCE.register(BlockInit.STONE_HOLDER_ENTITY, StoneHolderRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.STONE_HOLDER_BLOCK, RenderLayer.getCutout());
        BlockEntityRendererRegistry.INSTANCE.register(BlockInit.PIGLIN_FLAG_ENTITY, PiglinFlagRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PIGLIN_FLAG_BLOCK, RenderLayer.getCutout());
    }
}