package net.adventurez.init;

import net.adventurez.block.renderer.*;
import net.adventurez.entity.model.*;
import net.adventurez.entity.render.*;
import net.adventurez.init.ParticleInit.ShardParticle;
import net.adventurez.init.ParticleInit.SprintParticle;
import net.adventurez.init.ParticleInit.VoidCloudParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RenderInit {

    private static final Identifier WITHERED_TEXTURE = new Identifier("adventurez:textures/misc/withered.png");
    private static final Identifier ACTIVE_ARMOR_TEXTURE = new Identifier("adventurez:textures/misc/active_armor.png");

    public static final boolean isCanvasLoaded = FabricLoader.getInstance().isModLoaded("canvas");
    public static final boolean isIrisLoaded = FabricLoader.getInstance().isModLoaded("iris");
    public static final boolean isSodiumLoaded = FabricLoader.getInstance().isModLoaded("sodium");

    public static final EntityModelLayer BLACKSTONE_GOLEM_LAYER = new EntityModelLayer(new Identifier("adventurez:blackstone_golem_render_layer"), "blackstone_golem_render_layer");
    public static final EntityModelLayer THROWN_ROCK_LAYER = new EntityModelLayer(new Identifier("adventurez:thrown_rock_render_layer"), "thrown_rock_render_layer");
    public static final EntityModelLayer GILDED_STONE_LAYER = new EntityModelLayer(new Identifier("adventurez:gilded_stone_render_layer"), "gilded_stone_render_layer");
    public static final EntityModelLayer MINI_BLACKSTONE_GOLEM_LAYER = new EntityModelLayer(new Identifier("adventurez:mini_blackstone_golem_render_layer"), "mini_blackstone_golem_render_layer");
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
        EntityRendererRegistry.register(EntityInit.BLACKSTONE_GOLEM, BlackstoneGolemRenderer::new);
        EntityRendererRegistry.register(EntityInit.THROWN_ROCK, ThrownRockRenderer::new);
        EntityRendererRegistry.register(EntityInit.GILDED_BLACKSTONE_SHARD, GildedStoneRenderer::new);
        EntityRendererRegistry.register(EntityInit.MINI_BLACKSTONE_GOLEM, MiniBlackstoneGolemRenderer::new);
        EntityRendererRegistry.register(EntityInit.PIGLIN_BEAST, PiglinBeastRenderer::new);
        EntityRendererRegistry.register(EntityInit.NIGHTMARE, NightmareRenderer::new);
        EntityRendererRegistry.register(EntityInit.SOUL_REAPER, SoulReaperRenderer::new);
        EntityRendererRegistry.register(EntityInit.NECROMANCER, NecromancerRenderer::new);
        EntityRendererRegistry.register(EntityInit.WITHER_PUPPET, WitherPuppetRenderer::new);
        EntityRendererRegistry.register(EntityInit.SKELETON_VANGUARD, SkeletonVanguardRenderer::new);
        EntityRendererRegistry.register(EntityInit.SUMMONER, SummonerRenderer::new);
        EntityRendererRegistry.register(EntityInit.BLAZE_GUARDIAN, BlazeGuardianRenderer::new);
        EntityRendererRegistry.register(EntityInit.THE_EYE, TheEyeRenderer::new);
        EntityRendererRegistry.register(EntityInit.VOID_SHADOW, VoidShadowRenderer::new);
        EntityRendererRegistry.register(EntityInit.TINY_EYE, TinyEyeRenderer::new);
        EntityRendererRegistry.register(EntityInit.RED_FUNGUS, RedFungusRenderer::new);
        EntityRendererRegistry.register(EntityInit.BROWN_FUNGUS, BrownFungusRenderer::new);
        EntityRendererRegistry.register(EntityInit.ORC, OrcRenderer::new);
        EntityRendererRegistry.register(EntityInit.DRAGON, DragonRenderer::new);
        EntityRendererRegistry.register(EntityInit.MAMMOTH, MammothRenderer::new);
        EntityRendererRegistry.register(EntityInit.VOID_FRAGMENT, VoidFragmentRenderer::new);
        EntityRendererRegistry.register(EntityInit.VOID_SHADE, VoidShadeRenderer::new);
        EntityRendererRegistry.register(EntityInit.VOID_BULLET, VoidBulletRenderer::new);
        EntityRendererRegistry.register(EntityInit.FIRE_BREATH, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(EntityInit.BLAZE_GUARDIAN_SHIELD, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(EntityInit.ENDER_WHALE, EnderWhaleRenderer::new);
        EntityRendererRegistry.register(EntityInit.IGUANA, IguanaRenderer::new);
        EntityRendererRegistry.register(EntityInit.AMETHYST_GOLEM, AmethystGolemRenderer::new);
        EntityRendererRegistry.register(EntityInit.AMETHYST_SHARD, AmethystShardRenderer::new);
        EntityRendererRegistry.register(EntityInit.DESERT_RHINO, DesertRhinoRenderer::new);
        EntityRendererRegistry.register(EntityInit.VOID_CLOUD, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(EntityInit.SHAMAN, ShamanRenderer::new);
        EntityRendererRegistry.register(EntityInit.DEER, DeerRenderer::new);
        EntityRendererRegistry.register(EntityInit.ENDERWARTHOG, EnderwarthogRenderer::new);

        // Entity Layer
        EntityModelLayerRegistry.registerModelLayer(BLACKSTONE_GOLEM_LAYER, BlackstoneGolemModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(THROWN_ROCK_LAYER, RockModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GILDED_STONE_LAYER, GildedStoneModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MINI_BLACKSTONE_GOLEM_LAYER, MiniBlackstoneGolemModel::getTexturedModelData);
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

        // Blocks
        BlockEntityRendererFactories.register(BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY, ChiseledPolishedBlackstoneHolderRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(BlockInit.PIGLIN_FLAG_ENTITY, PiglinFlagRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PIGLIN_FLAG, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(BlockInit.SHADOW_CHEST_ENTITY, ChestBlockEntityRenderer::new);

        // Particles
        ParticleFactoryRegistry.getInstance().register(ParticleInit.AMETHYST_SHARD_PARTICLE, ShardParticle.ShardFactory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleInit.VOID_CLOUD_PARTICLE, VoidCloudParticle.CloudFactory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleInit.SPRINT_PARTICLE, SprintParticle.SprintFactory::new);

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (!client.options.hudHidden) {
                ItemStack itemStack = client.player.getEquippedStack(EquipmentSlot.CHEST);
                if (!itemStack.isEmpty() && itemStack.isOf(ItemInit.GILDED_NETHERITE_CHESTPLATE)) {
                    NbtCompound tag = itemStack.getNbt();
                    if (tag != null && tag.contains("armor_time") && tag.contains("activating_armor")) {
                        if (tag.getBoolean("activating_armor")) {
                            int scaledWidth = client.getWindow().getScaledWidth();
                            int scaledHeight = client.getWindow().getScaledHeight();
                            int worldTime = (int) client.player.getWorld().getTime();
                            int savedTagInt = tag.getInt("armor_time");
                            float effectDuration = ConfigInit.CONFIG.gilded_netherite_armor_effect_duration;
                            drawContext.getMatrices().push();
                            if (savedTagInt + effectDuration > worldTime) {
                                int multiplier = 2;
                                if (savedTagInt + Math.max(effectDuration - 100, 0) < worldTime) {
                                    multiplier = 4;
                                }

                                float pulsating = (float) Math.sin((float) ((worldTime * multiplier) - (savedTagInt - 1)) / 6.2831855F);
                                drawContext.setShaderColor(1.0F, 1.0F, 1.0F, pulsating + 0.5F);
                                drawContext.drawTexture(ACTIVE_ARMOR_TEXTURE, (scaledWidth / 2) - 5, scaledHeight - 49, 10, 10, 0, 0, 16, 16, 16, 16);
                            } else {
                                float fading = (1F - (((float) (worldTime - (savedTagInt + (effectDuration - 1f))) / effectDuration) - 0.4F));
                                drawContext.setShaderColor(1.0F, 0.65F, 0.65F, fading);
                                drawContext.drawTexture(ACTIVE_ARMOR_TEXTURE, (scaledWidth / 2) - 5, scaledHeight - 49, 10, 10, 0, 0, 16, 16, 16, 16);
                            }
                            drawContext.getMatrices().pop();
                            drawContext.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        }
                    }
                }
                if (client.player.hasStatusEffect(EffectInit.WITHERING)) {
                    drawContext.setShaderColor(1.0F, 1.0F, 1.0F, client.player.getStatusEffect(EffectInit.WITHERING).getDuration() / 280F);
                    int scaledWidth = client.getWindow().getScaledWidth();
                    int scaledHeight = client.getWindow().getScaledHeight();
                    drawContext.drawTexture(WITHERED_TEXTURE, scaledWidth / 2 - 64, scaledHeight / 2 - 64, 0.0F, 0.0F, 128, 128, 128, 128);
                    drawContext.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        });
    }
}