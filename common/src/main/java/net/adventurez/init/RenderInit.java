package net.adventurez.init;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.adventurez.AdventureMain;
import net.adventurez.block.renderer.*;
import net.adventurez.entity.model.*;
import net.adventurez.entity.render.*;
import net.adventurez.init.ParticleInit.ShardParticle;
import net.adventurez.init.ParticleInit.SprintParticle;
import net.adventurez.init.ParticleInit.VoidCloudParticle;
import net.adventurez.init.ParticleInit.FartParticle;
import net.adventurez.platform.Services;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RenderInit {

    private static final Identifier WITHERED_TEXTURE = AdventureMain.identifierOf("textures/misc/withered.png");
    public static final Identifier HUD_ID = AdventureMain.identifierOf("armor_and_withering");
    private static final Identifier ACTIVE_ARMOR_TEXTURE = AdventureMain.identifierOf("textures/misc/active_armor.png");

    public static final boolean isCanvasLoaded = Services.PLATFORM.isModLoaded("canvas");
    public static final boolean isIrisLoaded = Services.PLATFORM.isModLoaded("iris");
    public static final boolean isSodiumLoaded = Services.PLATFORM.isModLoaded("sodium");

    public static final ModelLayerLocation BLACKSTONE_GOLEM_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("blackstone_golem_render_layer"), "blackstone_golem_render_layer");
    public static final ModelLayerLocation THROWN_ROCK_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("thrown_rock_render_layer"), "thrown_rock_render_layer");
    public static final ModelLayerLocation GILDED_STONE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("gilded_stone_render_layer"), "gilded_stone_render_layer");
    public static final ModelLayerLocation MINI_BLACKSTONE_GOLEM_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("mini_blackstone_golem_render_layer"), "mini_blackstone_golem_render_layer");
    public static final ModelLayerLocation PIGLIN_BEAST_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("piglin_beast_render_layer"), "piglin_beast_render_layer");
    public static final ModelLayerLocation NIGHTMARE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("nightmare_render_layer"), "nightmare_render_layer");
    public static final ModelLayerLocation SOUL_REAPER_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("soul_reaper_render_layer"), "soul_reaper_render_layer");
    public static final ModelLayerLocation NECROMANCER_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("necromancer_render_layer"), "necromancer_render_layer");
    public static final ModelLayerLocation WITHER_PUPPET_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("wither_puppet_render_layer"), "wither_puppet_render_layer");
    public static final ModelLayerLocation SKELETON_VANGUARD_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("skeleton_vanguard_render_layer"), "skeleton_vanguard_render_layer");
    public static final ModelLayerLocation SUMMONER_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("summoner_render_layer"), "summoner_render_layer");
    public static final ModelLayerLocation BLAZE_GUARDIAN_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("blaze_guardian_render_layer"), "blaze_guardian_render_layer");
    public static final ModelLayerLocation THE_EYE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("the_eye_render_layer"), "the_eye_render_layer");
    public static final ModelLayerLocation VOID_SHADOW_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("void_shadow_render_layer"), "void_shadow_render_layer");
    public static final ModelLayerLocation TINY_EYE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("tiny_eye_render_layer"), "tiny_eye_render_layer");
    public static final ModelLayerLocation RED_FUNGUS_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("red_fungus_render_layer"), "red_fungus_render_layer");
    public static final ModelLayerLocation BROWN_FUNGUS_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("brown_fungus_render_layer"), "brown_fungus_render_layer");
    public static final ModelLayerLocation ORC_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("orc_render_layer"), "orc_render_layer");
    public static final ModelLayerLocation DRAGON_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("dragon_render_layer"), "dragon_render_layer");
    public static final ModelLayerLocation MAMMOTH_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("mammoth_render_layer"), "mammoth_render_layer");
    public static final ModelLayerLocation VOID_FRAGMENT_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("void_fragment_render_layer"), "void_fragment_render_layer");
    public static final ModelLayerLocation VOID_SHADE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("void_shade_render_layer"), "void_shade_render_layer");
    public static final ModelLayerLocation VOID_BULLET_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("void_bullet_render_layer"), "void_bullet_render_layer");
    public static final ModelLayerLocation PIGLIN_FLAG_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("piglin_flag_render_layer"), "piglin_flag_render_layer");
    public static final ModelLayerLocation ENDER_WHALE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("ender_whale_render_layer"), "ender_whale_render_layer");
    public static final ModelLayerLocation IGUANA_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("iguana_render_layer"), "iguana_render_layer");
    public static final ModelLayerLocation IGUANA_LAYER_BABY = new ModelLayerLocation(AdventureMain.identifierOf("iguana_render_layer"), "iguana_render_layer_baby");
    public static final ModelLayerLocation AMETHYST_GOLEM_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("amethyst_golem_render_layer"), "amethyst_golem_render_layer");
    public static final ModelLayerLocation AMETHYST_SHARD_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("amethyst_shard_render_layer"), "amethyst_shard_render_layer");
    public static final ModelLayerLocation DESERT_RHINO_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("desert_rhino_render_layer"), "desert_rhino_render_layer");
    public static final ModelLayerLocation SHAMAN_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("shaman_render_layer"), "shaman_render_layer");
    public static final ModelLayerLocation DEER_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("deer_render_layer"), "deer_render_layer");
    public static final ModelLayerLocation DEER_LAYER_BABY = new ModelLayerLocation(AdventureMain.identifierOf("deer_render_layer"), "deer_render_layer_baby");
    public static final ModelLayerLocation SKUNK_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("skunk_render_layer"), "skunk_render_layer");
    public static final ModelLayerLocation SKUNK_LAYER_BABY = new ModelLayerLocation(AdventureMain.identifierOf("skunk_render_layer"), "skunk_render_layer_baby");
    public static final ModelLayerLocation ENDERWARTHOG_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("enderwarthog_render_layer"), "enderwarthog_render_layer");
    public static final ModelLayerLocation DUBIOUS_PUMPKIN_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("dubious_pumpkin_render_layer"), "dubious_pumpkin_render_layer");
    public static final ModelLayerLocation CRYSTAL_BEETLE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("crystal_beetle_render_layer"), "crystal_beetle_render_layer");
    public static final ModelLayerLocation TERRACOTTA_GOLEM_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("terracotta_golem_render_layer"), "terracotta_golem_render_layer");

    public static void registerEntityRenderers(EntityRendererRegistrar registrar) {
        registrar.register(EntityInit.BLACKSTONE_GOLEM, BlackstoneGolemRenderer::new);
        registrar.register(EntityInit.THROWN_ROCK, ThrownRockRenderer::new);
        registrar.register(EntityInit.GILDED_BLACKSTONE_SHARD, GildedStoneRenderer::new);
        registrar.register(EntityInit.MINI_BLACKSTONE_GOLEM, MiniBlackstoneGolemRenderer::new);
        registrar.register(EntityInit.PIGLIN_BEAST, PiglinBeastRenderer::new);
        registrar.register(EntityInit.NIGHTMARE, NightmareRenderer::new);
        registrar.register(EntityInit.SOUL_REAPER, SoulReaperRenderer::new);
        registrar.register(EntityInit.NECROMANCER, NecromancerRenderer::new);
        registrar.register(EntityInit.WITHER_PUPPET, WitherPuppetRenderer::new);
        registrar.register(EntityInit.SKELETON_VANGUARD, SkeletonVanguardRenderer::new);
        registrar.register(EntityInit.SUMMONER, SummonerRenderer::new);
        registrar.register(EntityInit.BLAZE_GUARDIAN, BlazeGuardianRenderer::new);
        registrar.register(EntityInit.THE_EYE, TheEyeRenderer::new);
        registrar.register(EntityInit.VOID_SHADOW, VoidShadowRenderer::new);
        registrar.register(EntityInit.TINY_EYE, TinyEyeRenderer::new);
        registrar.register(EntityInit.RED_FUNGUS, RedFungusRenderer::new);
        registrar.register(EntityInit.BROWN_FUNGUS, BrownFungusRenderer::new);
        registrar.register(EntityInit.ORC, OrcRenderer::new);
        registrar.register(EntityInit.DRAGON, DragonRenderer::new);
        registrar.register(EntityInit.MAMMOTH, MammothRenderer::new);
        registrar.register(EntityInit.VOID_FRAGMENT, VoidFragmentRenderer::new);
        registrar.register(EntityInit.VOID_SHADE, VoidShadeRenderer::new);
        registrar.register(EntityInit.VOID_BULLET, VoidBulletRenderer::new);
        registrar.register(EntityInit.FIRE_BREATH, NoopRenderer::new);
        registrar.register(EntityInit.BLAZE_GUARDIAN_SHIELD, NoopRenderer::new);
        registrar.register(EntityInit.ENDER_WHALE, EnderWhaleRenderer::new);
        registrar.register(EntityInit.IGUANA, IguanaRenderer::new);
        registrar.register(EntityInit.AMETHYST_GOLEM, AmethystGolemRenderer::new);
        registrar.register(EntityInit.AMETHYST_SHARD, AmethystShardRenderer::new);
        registrar.register(EntityInit.DESERT_RHINO, DesertRhinoRenderer::new);
        registrar.register(EntityInit.VOID_CLOUD, NoopRenderer::new);
        registrar.register(EntityInit.SHAMAN, ShamanRenderer::new);
        registrar.register(EntityInit.DEER, DeerRenderer::new);
        registrar.register(EntityInit.SKUNK, SkunkRenderer::new);
        registrar.register(EntityInit.ENDERWARTHOG, EnderwarthogRenderer::new);
        registrar.register(EntityInit.DUBIOUS_PUMPKIN, DubiousPumpkinRenderer::new);
        registrar.register(EntityInit.CRYSTAL_BEETLE, CrystalBeetleRenderer::new);
        registrar.register(EntityInit.TERRACOTTA_GOLEM, TerracottaGolemRenderer::new);

        // Entity Layer
    }

    public static void registerModelLayers(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> registrar) {
        registrar.accept(BLACKSTONE_GOLEM_LAYER, BlackstoneGolemModel::getTexturedModelData);
        registrar.accept(THROWN_ROCK_LAYER, RockModel::getTexturedModelData);
        registrar.accept(GILDED_STONE_LAYER, GildedStoneModel::getTexturedModelData);
        registrar.accept(MINI_BLACKSTONE_GOLEM_LAYER, MiniBlackstoneGolemModel::getTexturedModelData);
        registrar.accept(PIGLIN_BEAST_LAYER, PiglinBeastModel::getTexturedModelData);
        registrar.accept(SOUL_REAPER_LAYER, SoulReaperModel::getTexturedModelData);
        registrar.accept(NECROMANCER_LAYER, NecromancerModel::getTexturedModelData);
        registrar.accept(WITHER_PUPPET_LAYER, WitherPuppetModel::getTexturedModelData);
        registrar.accept(SKELETON_VANGUARD_LAYER, SkeletonVanguardModel::getTexturedModelData);
        registrar.accept(SUMMONER_LAYER, SummonerModel::getTexturedModelData);
        registrar.accept(BLAZE_GUARDIAN_LAYER, BlazeGuardianModel::getTexturedModelData);
        registrar.accept(THE_EYE_LAYER, TheEyeModel::getTexturedModelData);
        registrar.accept(VOID_SHADOW_LAYER, VoidShadowModel::getTexturedModelData);
        registrar.accept(TINY_EYE_LAYER, TinyEyeModel::getTexturedModelData);
        registrar.accept(RED_FUNGUS_LAYER, RedFungusModel::getTexturedModelData);
        registrar.accept(BROWN_FUNGUS_LAYER, BrownFungusModel::getTexturedModelData);
        registrar.accept(ORC_LAYER, OrcModel::getTexturedModelData);
        registrar.accept(DRAGON_LAYER, DragonModel::getTexturedModelData);
        registrar.accept(MAMMOTH_LAYER, MammothModel::getTexturedModelData);
        registrar.accept(VOID_FRAGMENT_LAYER, VoidFragmentModel::getTexturedModelData);
        registrar.accept(VOID_SHADE_LAYER, VoidShadeModel::getTexturedModelData);
        registrar.accept(VOID_BULLET_LAYER, VoidBulletModel::getTexturedModelData);
        registrar.accept(PIGLIN_FLAG_LAYER, PiglinFlagRenderer::getTexturedModelData);
        registrar.accept(ENDER_WHALE_LAYER, EnderWhaleModel::getTexturedModelData);
        registrar.accept(IGUANA_LAYER, IguanaModel::getTexturedModelData);
        registrar.accept(IGUANA_LAYER_BABY, () -> IguanaModel.getTexturedModelData().apply(new BabyModelTransform(Set.of())));
        registrar.accept(AMETHYST_GOLEM_LAYER, AmethystGolemModel::getTexturedModelData);
        registrar.accept(AMETHYST_SHARD_LAYER, AmethystShardModel::getTexturedModelData);
        registrar.accept(DESERT_RHINO_LAYER, DesertRhinoModel::getTexturedModelData);
        registrar.accept(SHAMAN_LAYER, ShamanModel::getTexturedModelData);
        registrar.accept(DEER_LAYER, DeerModel::getTexturedModelData);
        registrar.accept(DEER_LAYER_BABY, () -> DeerModel.getTexturedModelData().apply(new BabyModelTransform(true, 17.0F, 4.0F, Set.of("head"))));
        registrar.accept(SKUNK_LAYER, SkunkModel::getTexturedModelData);
        registrar.accept(SKUNK_LAYER_BABY, () -> SkunkModel.getTexturedModelData().apply(new BabyModelTransform(true, 10.0F, 4.0F, Set.of("head"))));
        registrar.accept(ENDERWARTHOG_LAYER, EnderwarthogModel::getTexturedModelData);
        registrar.accept(DUBIOUS_PUMPKIN_LAYER, DubiousPumpkinModel::getTexturedModelData);
        registrar.accept(CRYSTAL_BEETLE_LAYER, CrystalBeetleModel::getTexturedModelData);
        registrar.accept(TERRACOTTA_GOLEM_LAYER, TerracottaGolemModel::getTexturedModelData);
    }

    public static void registerBlockEntityRenderers(BlockEntityRendererRegistrar registrar) {
        registrar.register(BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY, ChiseledPolishedBlackstoneHolderRenderer::new);
        registrar.register(BlockInit.PIGLIN_FLAG_ENTITY, PiglinFlagRenderer::new);
        registrar.register(BlockInit.SHADOW_CHEST_ENTITY, ShadowChestRenderer::new);
    }

    public static void registerParticleProviders(ParticleProviderRegistrar registrar) {
        registrar.register(ParticleInit.AMETHYST_SHARD_PARTICLE, ShardParticle.ShardFactory::new);
        registrar.register(ParticleInit.VOID_CLOUD_PARTICLE, VoidCloudParticle.CloudFactory::new);
        registrar.register(ParticleInit.SPRINT_PARTICLE, SprintParticle.SprintFactory::new);
        registrar.register(ParticleInit.FART_PARTICLE, FartParticle.Factory::new);
    }

    public static void renderHud(GuiGraphicsExtractor drawContext, DeltaTracker tickDelta) {
        Minecraft client = Minecraft.getInstance();
        if (!client.gui.hud.isHidden() && !client.player.isCreative() && !client.player.isSpectator()) {
            ItemStack itemStack = client.player.getItemBySlot(EquipmentSlot.CHEST);
            if (!itemStack.isEmpty() && itemStack.is(ItemInit.GILDED_NETHERITE_CHESTPLATE)) {
                if (itemStack.get(ItemInit.GILDED_DATA) != null && itemStack.get(ItemInit.GILDED_DATA).activated()) {
                    int scaledWidth = client.getWindow().getGuiScaledWidth();
                    int scaledHeight = client.getWindow().getGuiScaledHeight();
                    int worldTime = (int) client.player.level().getGameTime();
                    int savedTagInt = itemStack.get(ItemInit.GILDED_DATA).time();
                    float effectDuration = ConfigInit.CONFIG.gilded_netherite_armor_effect_duration;
                    if (savedTagInt + effectDuration > worldTime) {
                        int multiplier = 2;
                        if (savedTagInt + Math.max(effectDuration - 100, 0) < worldTime) {
                            multiplier = 4;
                        }

                        float pulsating = (float) Math.sin((float) ((worldTime * multiplier) - (savedTagInt - 1)) / 6.2831855F);
                        drawContext.blit(RenderPipelines.GUI_TEXTURED, ACTIVE_ARMOR_TEXTURE, (scaledWidth / 2) - 5, scaledHeight - 49, 0, 0, 10, 10, 16, 16, 16, 16,
                                ARGB.colorFromFloat(Math.clamp(pulsating + 0.5F, 0.0F, 1.0F), 1.0F, 1.0F, 1.0F));
                    } else {
                        float fading = (1F - (((float) (worldTime - (savedTagInt + (effectDuration - 1f))) / effectDuration) - 0.4F));
                        drawContext.blit(RenderPipelines.GUI_TEXTURED, ACTIVE_ARMOR_TEXTURE, (scaledWidth / 2) - 5, scaledHeight - 49, 0, 0, 10, 10, 16, 16, 16, 16,
                                ARGB.colorFromFloat(Math.clamp(fading, 0.0F, 1.0F), 1.0F, 0.65F, 0.65F));
                    }
                }
            }
            if (client.player.hasEffect(EffectInit.WITHERING)) {
                int scaledWidth = client.getWindow().getGuiScaledWidth();
                int scaledHeight = client.getWindow().getGuiScaledHeight();
                drawContext.blit(RenderPipelines.GUI_TEXTURED, WITHERED_TEXTURE, scaledWidth / 2 - 64, scaledHeight / 2 - 64, 0.0F, 0.0F, 128, 128, 128, 128,
                        ARGB.colorFromFloat(Math.clamp(client.player.getEffect(EffectInit.WITHERING).getDuration() / 280F, 0.0F, 1.0F), 1.0F, 1.0F, 1.0F));
            }
        }
    }

    public interface EntityRendererRegistrar {
        <T extends Entity> void register(EntityType<? extends T> entityType, EntityRendererProvider<T> provider);
    }

    public interface BlockEntityRendererRegistrar {
        <T extends BlockEntity, S extends BlockEntityRenderState> void register(BlockEntityType<? extends T> blockEntityType, BlockEntityRendererProvider<T, S> provider);
    }

    public interface ParticleProviderRegistrar {
        void register(SimpleParticleType particleType, Function<SpriteSet, ParticleProvider<SimpleParticleType>> provider);
    }
}
