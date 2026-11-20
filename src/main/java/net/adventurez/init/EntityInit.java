package net.adventurez.init;

import net.adventurez.AdventureMain;
import net.adventurez.entity.*;
import net.adventurez.entity.nonliving.*;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

@SuppressWarnings("unchecked")
public class EntityInit {
    // Hostile
    public static final EntityType<BlackstoneGolemEntity> BLACKSTONE_GOLEM = register("blackstone_golem", 2956072, 1445648,
            EntityType.Builder.of(BlackstoneGolemEntity::new, MobCategory.MONSTER).fireImmune().sized(3.36F, 4.44F));
    public static final EntityType<MiniBlackstoneGolemEntity> MINI_BLACKSTONE_GOLEM = register("mini_blackstone_golem", 4077380, 4400440,
            EntityType.Builder.of(MiniBlackstoneGolemEntity::new, MobCategory.MONSTER).fireImmune().sized(1.2F, 1.2F));
    public static final EntityType<PiglinBeastEntity> PIGLIN_BEAST = register("piglin_beast", 5121815, 14192743,
            EntityType.Builder.of(PiglinBeastEntity::new, MobCategory.MONSTER).fireImmune().sized(1.55F, 3.35F));
    public static final EntityType<SoulReaperEntity> SOUL_REAPER = register("soul_reaper", 1381653, 5329747,
            EntityType.Builder.of(SoulReaperEntity::new, MobCategory.MONSTER).fireImmune().sized(0.7F, 2.4F).ridingOffset(-0.6F));
    public static final EntityType<NecromancerEntity> NECROMANCER = register("necromancer", 1447446, 15514145,
            EntityType.Builder.of(NecromancerEntity::new, MobCategory.MONSTER).fireImmune().sized(0.9F, 2.4F));
    public static final EntityType<WitherPuppetEntity> WITHER_PUPPET = register("wither_puppet", 1250067, 3092271,
            EntityType.Builder.of(WitherPuppetEntity::new, MobCategory.MONSTER).fireImmune().sized(0.7F, 1.32F));
    public static final EntityType<SkeletonVanguardEntity> SKELETON_VANGUARD = register("skeleton_vanguard", 12369084, 11766305,
            EntityType.Builder.of(SkeletonVanguardEntity::new, MobCategory.MONSTER).sized(0.7F, 2.1F));
    public static final EntityType<SummonerEntity> SUMMONER = register("summoner", 12369084, 5847892,
            EntityType.Builder.of(SummonerEntity::new, MobCategory.MONSTER).sized(0.9F, 2.65F));
    public static final EntityType<BlazeGuardianEntity> BLAZE_GUARDIAN = register("blaze_guardian", 16766248, 9122817,
            EntityType.Builder.of(BlazeGuardianEntity::new, MobCategory.MONSTER).fireImmune().sized(0.8F, 2.25F));
    public static final EntityType<TheEyeEntity> THE_EYE = register("the_eye", 1984565, 1059889,
            EntityType.Builder.of(TheEyeEntity::new, MobCategory.MONSTER).fireImmune().sized(2.8F, 3.5F).eyeHeight(2.0F));
    public static final EntityType<VoidShadowEntity> VOID_SHADOW = register("void_shadow", 1441901, 393244,
            EntityType.Builder.of(VoidShadowEntity::new, MobCategory.MONSTER).fireImmune().sized(10.2F, 15.3F));
    public static final EntityType<OrcEntity> ORC = register("orc", 2255437, 3512689, EntityType.Builder.of(OrcEntity::new, MobCategory.MONSTER).sized(1.35F, 2.2F));
    public static final EntityType<VoidShadeEntity> VOID_SHADE = register("void_shade", 1179727, 2956161,
            EntityType.Builder.of(VoidShadeEntity::new, MobCategory.MONSTER).sized(1.0F, 2.1F));
    public static final EntityType<AmethystGolemEntity> AMETHYST_GOLEM = register("amethyst_golem", 5395026, 9267916,
            EntityType.Builder.of(AmethystGolemEntity::new, MobCategory.MONSTER).sized(1.8F, 2.2F));
    public static final EntityType<DesertRhinoEntity> DESERT_RHINO = register("desert_rhino", 7884087, 12031588,
            EntityType.Builder.of(DesertRhinoEntity::new, MobCategory.MONSTER).sized(2.3F, 2.35F));
    public static final EntityType<ShamanEntity> SHAMAN = register("shaman", 210734, 8739394, EntityType.Builder.of(ShamanEntity::new, MobCategory.MONSTER).sized(0.9F, 2.01F));
    public static final EntityType<EnderwarthogEntity> ENDERWARTHOG = register("enderwarthog", 2828080, 6553725,
            EntityType.Builder.of(EnderwarthogEntity::new, MobCategory.MONSTER).sized(2.3F, 2.15F));
    public static final EntityType<DubiousPumpkinEntity> DUBIOUS_PUMPKIN = register("dubious_pumpkin", 7377453, 12873492,
            EntityType.Builder.of(DubiousPumpkinEntity::new, MobCategory.CREATURE).sized(0.9F, 0.9F));
    public static final EntityType<CrystalBeetleEntity> CRYSTAL_BEETLE = register("crystal_beetle", 2236963, 10770912,
            EntityType.Builder.of(CrystalBeetleEntity::new, MobCategory.CREATURE).sized(1.5F, 0.75F));
    public static final EntityType<TerracottaGolemEntity> TERRACOTTA_GOLEM = register("terracotta_golem", 7684662, 9274753,
            EntityType.Builder.of(TerracottaGolemEntity::new, MobCategory.MONSTER).sized(2.7F, 3.2F));

    // Passive
    public static final EntityType<RedFungusEntity> RED_FUNGUS = register("red_fungus", 13084791, 13183785,
            EntityType.Builder.of(RedFungusEntity::new, MobCategory.CREATURE).sized(1.05F, 1.4F).eyeHeight(0.7F));
    public static final EntityType<BrownFungusEntity> BROWN_FUNGUS = register("brown_fungus", 13084791, 9925201,
            EntityType.Builder.of(BrownFungusEntity::new, MobCategory.CREATURE).sized(1.35F, 1.8F).eyeHeight(0.9F));
    public static final EntityType<NightmareEntity> NIGHTMARE = register("nightmare", 1381653, 3012863,
            EntityType.Builder.of(NightmareEntity::new, MobCategory.CREATURE).fireImmune().sized(1.4F, 1.6F).passengerAttachments(1.4F));
    public static final EntityType<DragonEntity> DRAGON = register("dragon", 1842204, 14711290,
            EntityType.Builder.of(DragonEntity::new, MobCategory.CREATURE).sized(4.8F, 3.3F).eyeHeight(2.4F).passengerAttachments(2.7F).fireImmune());
    public static final EntityType<MammothEntity> MAMMOTH = register("mammoth", 4732462, 6376763, EntityType.Builder.of(MammothEntity::new, MobCategory.CREATURE).sized(2.8F, 3.5F));
    public static final EntityType<EnderWhaleEntity> ENDER_WHALE = register("ender_whale", 1711667, 6179950,
            EntityType.Builder.of(EnderWhaleEntity::new, MobCategory.CREATURE).sized(4.0F, 2.5F));
    public static final EntityType<IguanaEntity> IGUANA = register("iguana", 11485475, 8988193, EntityType.Builder.of(IguanaEntity::new, MobCategory.CREATURE).sized(1.5F, 0.5F));
    public static final EntityType<DeerEntity> DEER = register("deer", 5780491, 9725748, EntityType.Builder.of(DeerEntity::new, MobCategory.CREATURE).sized(1.4F, 1.8F));
    public static final EntityType<SkunkEntity> SKUNK = register("skunk", 3091500, 15525848, EntityType.Builder.of(SkunkEntity::new, MobCategory.CREATURE).sized(1.1F, 0.7F));

    // Nonliving Entity
    public static final EntityType<ThrownRockEntity> THROWN_ROCK = register("thrown_rock", 0, 0,
            EntityType.Builder.<ThrownRockEntity>of(ThrownRockEntity::new, MobCategory.MISC).sized(1.5F, 1.5F));
    public static final EntityType<GildedBlackstoneShardEntity> GILDED_BLACKSTONE_SHARD = register("gilded_blackstone_shard", 0, 0,
            EntityType.Builder.<GildedBlackstoneShardEntity>of(GildedBlackstoneShardEntity::new, MobCategory.MISC).sized(0.4F, 0.7F));
    public static final EntityType<TinyEyeEntity> TINY_EYE = register("tiny_eye", 0, 0, EntityType.Builder.<TinyEyeEntity>of(TinyEyeEntity::new, MobCategory.MISC).sized(0.4F, 0.4F));
    public static final EntityType<VoidBulletEntity> VOID_BULLET = register("void_bullet", 0, 0,
            EntityType.Builder.<VoidBulletEntity>of(VoidBulletEntity::new, MobCategory.MISC).sized(0.5F, 0.5F));
    public static final EntityType<FireBreathEntity> FIRE_BREATH = register("fire_breath", 0, 0,
            EntityType.Builder.<FireBreathEntity>of(FireBreathEntity::new, MobCategory.MISC).sized(0.3F, 0.3F));
    public static final EntityType<BlazeGuardianShieldEntity> BLAZE_GUARDIAN_SHIELD = register("blaze_guardian_shield", 0, 0,
            EntityType.Builder.<BlazeGuardianShieldEntity>of(BlazeGuardianShieldEntity::new, MobCategory.MISC).fireImmune().sized(0.65F, 1.6F));
    public static final EntityType<AmethystShardEntity> AMETHYST_SHARD = register("amethyst_shard", 0, 0,
            EntityType.Builder.<AmethystShardEntity>of(AmethystShardEntity::new, MobCategory.MISC).sized(0.6F, 0.8F));
    public static final EntityType<VoidCloudEntity> VOID_CLOUD = register("void_cloud", 0, 0,
            EntityType.Builder.<VoidCloudEntity>of(VoidCloudEntity::new, MobCategory.MISC).sized(6.0F, 0.5F).fireImmune());
    public static final EntityType<VoidFragmentEntity> VOID_FRAGMENT = register("void_fragment", 1376335, 3670138,
            EntityType.Builder.of(VoidFragmentEntity::new, MobCategory.MONSTER).sized(1.0F, 1.0F));

    // Damage Types
    public static final ResourceKey<DamageType> AMETHYST_SHARD_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, AdventureMain.identifierOf("amethyst_shard"));
    public static final ResourceKey<DamageType> VOID_BULLET_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, AdventureMain.identifierOf("void_bullet"));
    public static final ResourceKey<DamageType> FIRE_BREATH_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, AdventureMain.identifierOf("fire_breath"));
    public static final ResourceKey<DamageType> TINY_EYE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, AdventureMain.identifierOf("tiny_eye"));
    public static final ResourceKey<DamageType> ROCK_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, AdventureMain.identifierOf("rock"));

    private static <T extends Entity> EntityType<T> register(String id, int primaryColor, int secondaryColor, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, AdventureMain.identifierOf(id));
        EntityType<T> entityType = Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
        if (primaryColor != 0) {
            ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, AdventureMain.identifierOf("spawn_" + id));
            Item item = Registry.register(BuiltInRegistries.ITEM, itemKey, new SpawnEggItem(new Item.Properties().spawnEgg(entityType).setId(itemKey)));
            CreativeModeTabEvents.modifyOutputEvent(ItemInit.ADVENTUREZ_ITEM_GROUP).register(entries -> entries.accept(item));
        }
        return entityType;
    }

    public static void init() {
        // Attributes
        FabricDefaultAttributeRegistry.register(BLACKSTONE_GOLEM, BlackstoneGolemEntity.createStoneGolemAttributes());
        FabricDefaultAttributeRegistry.register(MINI_BLACKSTONE_GOLEM, MiniBlackstoneGolemEntity.createSmallStoneGolemAttributes());
        FabricDefaultAttributeRegistry.register(PIGLIN_BEAST, PiglinBeastEntity.createPiglinBeastAttributes());
        FabricDefaultAttributeRegistry.register(NIGHTMARE, NightmareEntity.createNightmareAttributes());
        FabricDefaultAttributeRegistry.register(SOUL_REAPER, SoulReaperEntity.createSoulReaperAttributes());
        FabricDefaultAttributeRegistry.register(NECROMANCER, NecromancerEntity.createNecromancerAttributes());
        FabricDefaultAttributeRegistry.register(WITHER_PUPPET, WitherPuppetEntity.createWitherPuppetAttributes());
        FabricDefaultAttributeRegistry.register(SKELETON_VANGUARD, SkeletonVanguardEntity.createSkeletonVanguardAttributes());
        FabricDefaultAttributeRegistry.register(SUMMONER, SummonerEntity.createSummonerAttributes());
        FabricDefaultAttributeRegistry.register(BLAZE_GUARDIAN, BlazeGuardianEntity.createBlazeGuardianAttributes());
        FabricDefaultAttributeRegistry.register(THE_EYE, TheEyeEntity.createTheEntityAttributes());
        FabricDefaultAttributeRegistry.register(VOID_SHADOW, VoidShadowEntity.createVoidShadowAttributes());
        FabricDefaultAttributeRegistry.register(RED_FUNGUS, RedFungusEntity.createRedFungusAttributes());
        FabricDefaultAttributeRegistry.register(BROWN_FUNGUS, BrownFungusEntity.createBrownFungusAttributes());
        FabricDefaultAttributeRegistry.register(ORC, OrcEntity.createOrcAttributes());
        FabricDefaultAttributeRegistry.register(DRAGON, DragonEntity.createDragonAttributes());
        FabricDefaultAttributeRegistry.register(MAMMOTH, MammothEntity.createMammothAttributes());
        FabricDefaultAttributeRegistry.register(VOID_FRAGMENT, VoidFragmentEntity.createVoidFragmentAttributes());
        FabricDefaultAttributeRegistry.register(VOID_SHADE, VoidShadeEntity.createVoidShadeAttributes());
        FabricDefaultAttributeRegistry.register(ENDER_WHALE, EnderWhaleEntity.createEnderWhaleAttributes());
        FabricDefaultAttributeRegistry.register(IGUANA, IguanaEntity.createIguanaAttributes());
        FabricDefaultAttributeRegistry.register(AMETHYST_GOLEM, AmethystGolemEntity.createAmethystGolemAttributes());
        FabricDefaultAttributeRegistry.register(DESERT_RHINO, DesertRhinoEntity.createDesertRhinoAttributes());
        FabricDefaultAttributeRegistry.register(SHAMAN, ShamanEntity.createShamanAttributes());
        FabricDefaultAttributeRegistry.register(DEER, DeerEntity.createDeerAttributes());
        FabricDefaultAttributeRegistry.register(SKUNK, SkunkEntity.createSkunkAttributes());
        FabricDefaultAttributeRegistry.register(ENDERWARTHOG, EnderwarthogEntity.createEnderwarthogAttributes());
        FabricDefaultAttributeRegistry.register(DUBIOUS_PUMPKIN, DubiousPumpkinEntity.createDubiousPumpkinAttributes());
        FabricDefaultAttributeRegistry.register(CRYSTAL_BEETLE, CrystalBeetleEntity.createCrystalBeetleAttributes());
        FabricDefaultAttributeRegistry.register(TERRACOTTA_GOLEM, TerracottaGolemEntity.createTerracottaGolemAttributes());
    }
}