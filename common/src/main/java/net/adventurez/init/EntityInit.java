package net.adventurez.init;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import net.adventurez.AdventureMain;
import net.adventurez.entity.*;
import net.adventurez.entity.nonliving.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class EntityInit {

    private static final Map<String, EntityType<? extends Mob>> SPAWN_EGG_TYPES = new LinkedHashMap<>();

    // Hostile
    public static EntityType<BlackstoneGolemEntity> BLACKSTONE_GOLEM;
    public static EntityType<MiniBlackstoneGolemEntity> MINI_BLACKSTONE_GOLEM;
    public static EntityType<PiglinBeastEntity> PIGLIN_BEAST;
    public static EntityType<SoulReaperEntity> SOUL_REAPER;
    public static EntityType<NecromancerEntity> NECROMANCER;
    public static EntityType<WitherPuppetEntity> WITHER_PUPPET;
    public static EntityType<SkeletonVanguardEntity> SKELETON_VANGUARD;
    public static EntityType<SummonerEntity> SUMMONER;
    public static EntityType<BlazeGuardianEntity> BLAZE_GUARDIAN;
    public static EntityType<TheEyeEntity> THE_EYE;
    public static EntityType<VoidShadowEntity> VOID_SHADOW;
    public static EntityType<OrcEntity> ORC;
    public static EntityType<VoidShadeEntity> VOID_SHADE;
    public static EntityType<AmethystGolemEntity> AMETHYST_GOLEM;
    public static EntityType<DesertRhinoEntity> DESERT_RHINO;
    public static EntityType<ShamanEntity> SHAMAN;
    public static EntityType<EnderwarthogEntity> ENDERWARTHOG;
    public static EntityType<DubiousPumpkinEntity> DUBIOUS_PUMPKIN;
    public static EntityType<CrystalBeetleEntity> CRYSTAL_BEETLE;
    public static EntityType<TerracottaGolemEntity> TERRACOTTA_GOLEM;

    // Passive
    public static EntityType<RedFungusEntity> RED_FUNGUS;
    public static EntityType<BrownFungusEntity> BROWN_FUNGUS;
    public static EntityType<NightmareEntity> NIGHTMARE;
    public static EntityType<DragonEntity> DRAGON;
    public static EntityType<MammothEntity> MAMMOTH;
    public static EntityType<EnderWhaleEntity> ENDER_WHALE;
    public static EntityType<IguanaEntity> IGUANA;
    public static EntityType<DeerEntity> DEER;
    public static EntityType<SkunkEntity> SKUNK;

    // Nonliving Entity
    public static EntityType<ThrownRockEntity> THROWN_ROCK;
    public static EntityType<GildedBlackstoneShardEntity> GILDED_BLACKSTONE_SHARD;
    public static EntityType<TinyEyeEntity> TINY_EYE;
    public static EntityType<VoidBulletEntity> VOID_BULLET;
    public static EntityType<FireBreathEntity> FIRE_BREATH;
    public static EntityType<BlazeGuardianShieldEntity> BLAZE_GUARDIAN_SHIELD;
    public static EntityType<AmethystShardEntity> AMETHYST_SHARD;
    public static EntityType<VoidCloudEntity> VOID_CLOUD;
    public static EntityType<VoidFragmentEntity> VOID_FRAGMENT;

    // Damage Types
    public static final ResourceKey<DamageType> AMETHYST_SHARD_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, AdventureMain.identifierOf("amethyst_shard"));
    public static final ResourceKey<DamageType> VOID_BULLET_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, AdventureMain.identifierOf("void_bullet"));
    public static final ResourceKey<DamageType> FIRE_BREATH_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, AdventureMain.identifierOf("fire_breath"));
    public static final ResourceKey<DamageType> TINY_EYE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, AdventureMain.identifierOf("tiny_eye"));
    public static final ResourceKey<DamageType> ROCK_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, AdventureMain.identifierOf("rock"));

    public static void registerEntityTypes(BiConsumer<Identifier, EntityType<?>> registrar) {
        BLACKSTONE_GOLEM = register(registrar, "blackstone_golem", 2956072, 1445648,
                EntityType.Builder.of(BlackstoneGolemEntity::new, MobCategory.MONSTER).fireImmune().sized(3.36F, 4.44F));
        MINI_BLACKSTONE_GOLEM = register(registrar, "mini_blackstone_golem", 4077380, 4400440,
                EntityType.Builder.of(MiniBlackstoneGolemEntity::new, MobCategory.MONSTER).fireImmune().sized(1.2F, 1.2F));
        PIGLIN_BEAST = register(registrar, "piglin_beast", 5121815, 14192743,
                EntityType.Builder.of(PiglinBeastEntity::new, MobCategory.MONSTER).fireImmune().sized(1.55F, 3.35F));
        SOUL_REAPER = register(registrar, "soul_reaper", 1381653, 5329747,
                EntityType.Builder.of(SoulReaperEntity::new, MobCategory.MONSTER).fireImmune().sized(0.7F, 2.4F).ridingOffset(-0.6F));
        NECROMANCER = register(registrar, "necromancer", 1447446, 15514145,
                EntityType.Builder.of(NecromancerEntity::new, MobCategory.MONSTER).fireImmune().sized(0.9F, 2.4F));
        WITHER_PUPPET = register(registrar, "wither_puppet", 1250067, 3092271,
                EntityType.Builder.of(WitherPuppetEntity::new, MobCategory.MONSTER).fireImmune().sized(0.7F, 1.32F));
        SKELETON_VANGUARD = register(registrar, "skeleton_vanguard", 12369084, 11766305,
                EntityType.Builder.of(SkeletonVanguardEntity::new, MobCategory.MONSTER).sized(0.7F, 2.1F));
        SUMMONER = register(registrar, "summoner", 12369084, 5847892,
                EntityType.Builder.of(SummonerEntity::new, MobCategory.MONSTER).sized(0.9F, 2.65F));
        BLAZE_GUARDIAN = register(registrar, "blaze_guardian", 16766248, 9122817,
                EntityType.Builder.of(BlazeGuardianEntity::new, MobCategory.MONSTER).fireImmune().sized(0.8F, 2.25F));
        THE_EYE = register(registrar, "the_eye", 1984565, 1059889,
                EntityType.Builder.of(TheEyeEntity::new, MobCategory.MONSTER).fireImmune().sized(2.8F, 3.5F).eyeHeight(2.0F));
        VOID_SHADOW = register(registrar, "void_shadow", 1441901, 393244,
                EntityType.Builder.of(VoidShadowEntity::new, MobCategory.MONSTER).fireImmune().sized(10.2F, 15.3F));
        ORC = register(registrar, "orc", 2255437, 3512689, EntityType.Builder.of(OrcEntity::new, MobCategory.MONSTER).sized(1.35F, 2.2F));
        VOID_SHADE = register(registrar, "void_shade", 1179727, 2956161,
                EntityType.Builder.of(VoidShadeEntity::new, MobCategory.MONSTER).sized(1.0F, 2.1F));
        AMETHYST_GOLEM = register(registrar, "amethyst_golem", 5395026, 9267916,
                EntityType.Builder.of(AmethystGolemEntity::new, MobCategory.MONSTER).sized(1.8F, 2.2F));
        DESERT_RHINO = register(registrar, "desert_rhino", 7884087, 12031588,
                EntityType.Builder.of(DesertRhinoEntity::new, MobCategory.MONSTER).sized(2.3F, 2.35F));
        SHAMAN = register(registrar, "shaman", 210734, 8739394, EntityType.Builder.of(ShamanEntity::new, MobCategory.MONSTER).sized(0.9F, 2.01F));
        ENDERWARTHOG = register(registrar, "enderwarthog", 2828080, 6553725,
                EntityType.Builder.of(EnderwarthogEntity::new, MobCategory.MONSTER).sized(2.3F, 2.15F));
        DUBIOUS_PUMPKIN = register(registrar, "dubious_pumpkin", 7377453, 12873492,
                EntityType.Builder.of(DubiousPumpkinEntity::new, MobCategory.CREATURE).sized(0.9F, 0.9F));
        CRYSTAL_BEETLE = register(registrar, "crystal_beetle", 2236963, 10770912,
                EntityType.Builder.of(CrystalBeetleEntity::new, MobCategory.CREATURE).sized(1.5F, 0.75F));
        TERRACOTTA_GOLEM = register(registrar, "terracotta_golem", 7684662, 9274753,
                EntityType.Builder.of(TerracottaGolemEntity::new, MobCategory.MONSTER).sized(2.7F, 3.2F));
        RED_FUNGUS = register(registrar, "red_fungus", 13084791, 13183785,
                EntityType.Builder.of(RedFungusEntity::new, MobCategory.CREATURE).sized(1.05F, 1.4F).eyeHeight(0.7F));
        BROWN_FUNGUS = register(registrar, "brown_fungus", 13084791, 9925201,
                EntityType.Builder.of(BrownFungusEntity::new, MobCategory.CREATURE).sized(1.35F, 1.8F).eyeHeight(0.9F));
        NIGHTMARE = register(registrar, "nightmare", 1381653, 3012863,
                EntityType.Builder.of(NightmareEntity::new, MobCategory.CREATURE).fireImmune().sized(1.4F, 1.6F).passengerAttachments(1.4F));
        DRAGON = register(registrar, "dragon", 1842204, 14711290,
                EntityType.Builder.of(DragonEntity::new, MobCategory.CREATURE).sized(4.8F, 3.3F).eyeHeight(2.4F).passengerAttachments(2.7F).fireImmune());
        MAMMOTH = register(registrar, "mammoth", 4732462, 6376763, EntityType.Builder.of(MammothEntity::new, MobCategory.CREATURE).sized(2.8F, 3.5F));
        ENDER_WHALE = register(registrar, "ender_whale", 1711667, 6179950,
                EntityType.Builder.of(EnderWhaleEntity::new, MobCategory.CREATURE).sized(4.0F, 2.5F));
        IGUANA = register(registrar, "iguana", 11485475, 8988193, EntityType.Builder.of(IguanaEntity::new, MobCategory.CREATURE).sized(1.5F, 0.5F));
        DEER = register(registrar, "deer", 5780491, 9725748, EntityType.Builder.of(DeerEntity::new, MobCategory.CREATURE).sized(1.4F, 1.8F));
        SKUNK = register(registrar, "skunk", 3091500, 15525848, EntityType.Builder.of(SkunkEntity::new, MobCategory.CREATURE).sized(1.1F, 0.7F));
        THROWN_ROCK = register(registrar, "thrown_rock", 0, 0,
                EntityType.Builder.<ThrownRockEntity>of(ThrownRockEntity::new, MobCategory.MISC).sized(1.5F, 1.5F));
        GILDED_BLACKSTONE_SHARD = register(registrar, "gilded_blackstone_shard", 0, 0,
                EntityType.Builder.<GildedBlackstoneShardEntity>of(GildedBlackstoneShardEntity::new, MobCategory.MISC).sized(0.4F, 0.7F));
        TINY_EYE = register(registrar, "tiny_eye", 0, 0, EntityType.Builder.<TinyEyeEntity>of(TinyEyeEntity::new, MobCategory.MISC).sized(0.4F, 0.4F));
        VOID_BULLET = register(registrar, "void_bullet", 0, 0,
                EntityType.Builder.<VoidBulletEntity>of(VoidBulletEntity::new, MobCategory.MISC).sized(0.5F, 0.5F));
        FIRE_BREATH = register(registrar, "fire_breath", 0, 0,
                EntityType.Builder.<FireBreathEntity>of(FireBreathEntity::new, MobCategory.MISC).sized(0.3F, 0.3F));
        BLAZE_GUARDIAN_SHIELD = register(registrar, "blaze_guardian_shield", 0, 0,
                EntityType.Builder.<BlazeGuardianShieldEntity>of(BlazeGuardianShieldEntity::new, MobCategory.MISC).fireImmune().sized(0.65F, 1.6F));
        AMETHYST_SHARD = register(registrar, "amethyst_shard", 0, 0,
                EntityType.Builder.<AmethystShardEntity>of(AmethystShardEntity::new, MobCategory.MISC).sized(0.6F, 0.8F));
        VOID_CLOUD = register(registrar, "void_cloud", 0, 0,
                EntityType.Builder.<VoidCloudEntity>of(VoidCloudEntity::new, MobCategory.MISC).sized(6.0F, 0.5F).fireImmune());
        VOID_FRAGMENT = register(registrar, "void_fragment", 1376335, 3670138,
                EntityType.Builder.of(VoidFragmentEntity::new, MobCategory.MONSTER).sized(1.0F, 1.0F));
    }

    public static void registerSpawnEggs(BiConsumer<Identifier, Item> registrar) {
        for (Map.Entry<String, EntityType<? extends Mob>> entry : SPAWN_EGG_TYPES.entrySet()) {
            ItemInit.register(registrar, "spawn_" + entry.getKey(), SpawnEggItem::new, new Item.Properties().spawnEgg(entry.getValue()));
        }
    }

    public static void registerAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> registrar) {
        registrar.accept(BLACKSTONE_GOLEM, BlackstoneGolemEntity.createStoneGolemAttributes());
        registrar.accept(MINI_BLACKSTONE_GOLEM, MiniBlackstoneGolemEntity.createSmallStoneGolemAttributes());
        registrar.accept(PIGLIN_BEAST, PiglinBeastEntity.createPiglinBeastAttributes());
        registrar.accept(NIGHTMARE, NightmareEntity.createNightmareAttributes());
        registrar.accept(SOUL_REAPER, SoulReaperEntity.createSoulReaperAttributes());
        registrar.accept(NECROMANCER, NecromancerEntity.createNecromancerAttributes());
        registrar.accept(WITHER_PUPPET, WitherPuppetEntity.createWitherPuppetAttributes());
        registrar.accept(SKELETON_VANGUARD, SkeletonVanguardEntity.createSkeletonVanguardAttributes());
        registrar.accept(SUMMONER, SummonerEntity.createSummonerAttributes());
        registrar.accept(BLAZE_GUARDIAN, BlazeGuardianEntity.createBlazeGuardianAttributes());
        registrar.accept(THE_EYE, TheEyeEntity.createTheEntityAttributes());
        registrar.accept(VOID_SHADOW, VoidShadowEntity.createVoidShadowAttributes());
        registrar.accept(RED_FUNGUS, RedFungusEntity.createRedFungusAttributes());
        registrar.accept(BROWN_FUNGUS, BrownFungusEntity.createBrownFungusAttributes());
        registrar.accept(ORC, OrcEntity.createOrcAttributes());
        registrar.accept(DRAGON, DragonEntity.createDragonAttributes());
        registrar.accept(MAMMOTH, MammothEntity.createMammothAttributes());
        registrar.accept(VOID_FRAGMENT, VoidFragmentEntity.createVoidFragmentAttributes());
        registrar.accept(VOID_SHADE, VoidShadeEntity.createVoidShadeAttributes());
        registrar.accept(ENDER_WHALE, EnderWhaleEntity.createEnderWhaleAttributes());
        registrar.accept(IGUANA, IguanaEntity.createIguanaAttributes());
        registrar.accept(AMETHYST_GOLEM, AmethystGolemEntity.createAmethystGolemAttributes());
        registrar.accept(DESERT_RHINO, DesertRhinoEntity.createDesertRhinoAttributes());
        registrar.accept(SHAMAN, ShamanEntity.createShamanAttributes());
        registrar.accept(DEER, DeerEntity.createDeerAttributes());
        registrar.accept(SKUNK, SkunkEntity.createSkunkAttributes());
        registrar.accept(ENDERWARTHOG, EnderwarthogEntity.createEnderwarthogAttributes());
        registrar.accept(DUBIOUS_PUMPKIN, DubiousPumpkinEntity.createDubiousPumpkinAttributes());
        registrar.accept(CRYSTAL_BEETLE, CrystalBeetleEntity.createCrystalBeetleAttributes());
        registrar.accept(TERRACOTTA_GOLEM, TerracottaGolemEntity.createTerracottaGolemAttributes());
    }

    @SuppressWarnings("unchecked")
    private static <T extends Entity> EntityType<T> register(BiConsumer<Identifier, EntityType<?>> registrar, String id, int primaryColor, int secondaryColor, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, AdventureMain.identifierOf(id));
        EntityType<T> entityType = builder.build(key);
        registrar.accept(key.identifier(), entityType);
        if (primaryColor != 0) {
            SPAWN_EGG_TYPES.put(id, (EntityType<? extends Mob>) entityType);
        }
        return entityType;
    }
}
