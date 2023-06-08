package net.adventurez.init;

import net.adventurez.entity.*;
import net.adventurez.entity.nonliving.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

@SuppressWarnings("unchecked")
public class EntityInit {
    // Hostile
    public static final EntityType<StoneGolemEntity> STONEGOLEM_ENTITY = register("stone_golem", 2956072, 1445648,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, StoneGolemEntity::new).fireImmune().dimensions(EntityDimensions.fixed(3.36F, 4.44F)).build());
    public static final EntityType<SmallStoneGolemEntity> SMALLSTONEGOLEM_ENTITY = register("small_stone_golem", 4077380, 4400440,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SmallStoneGolemEntity::new).fireImmune().dimensions(EntityDimensions.fixed(1.2F, 1.2F)).build());
    public static final EntityType<PiglinBeastEntity> PIGLINBEAST_ENTITY = register("piglin_beast", 5121815, 14192743,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PiglinBeastEntity::new).fireImmune().dimensions(EntityDimensions.fixed(1.55F, 3.35F)).build());
    public static final EntityType<SoulReaperEntity> SOULREAPER_ENTITY = register("soul_reaper", 1381653, 5329747,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SoulReaperEntity::new).fireImmune().dimensions(EntityDimensions.fixed(0.7F, 2.4F)).build());
    public static final EntityType<NecromancerEntity> NECROMANCER_ENTITY = register("necromancer", 1447446, 15514145,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, NecromancerEntity::new).fireImmune().dimensions(EntityDimensions.fixed(0.9F, 2.4F)).build());
    public static final EntityType<WitherPuppetEntity> WITHERPUPPET_ENTITY = register("wither_puppet", 1250067, 3092271,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WitherPuppetEntity::new).fireImmune().dimensions(EntityDimensions.fixed(0.7F, 1.32F)).build());
    public static final EntityType<SkeletonVanguardEntity> SKELETON_VANGUARD_ENTITY = register("skeleton_vanguard", 12369084, 11766305,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SkeletonVanguardEntity::new).dimensions(EntityDimensions.fixed(0.7F, 2.1F)).build());
    public static final EntityType<SummonerEntity> SUMMONER_ENTITY = register("summoner", 12369084, 5847892,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SummonerEntity::new).dimensions(EntityDimensions.fixed(0.9F, 2.65F)).build());
    public static final EntityType<BlazeGuardianEntity> BLAZEGUARDIAN_ENTITY = register("blaze_guardian", 16766248, 9122817,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BlazeGuardianEntity::new).fireImmune().dimensions(EntityDimensions.fixed(0.8F, 2.25F)).build());
    public static final EntityType<TheEyeEntity> THE_EYE_ENTITY = register("the_eye", 1984565, 1059889,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TheEyeEntity::new).fireImmune().dimensions(EntityDimensions.fixed(2.8F, 3.5F)).build());
    public static final EntityType<VoidShadowEntity> VOID_SHADOW_ENTITY = register("void_shadow", 1441901, 393244,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidShadowEntity::new).fireImmune().dimensions(EntityDimensions.fixed(10.2F, 15.3F)).build());
    public static final EntityType<OrcEntity> ORC_ENTITY = register("orc", 2255437, 3512689,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OrcEntity::new).dimensions(EntityDimensions.changing(1.35F, 2.2F)).build());
    public static final EntityType<VoidShadeEntity> VOID_SHADE_ENTITY = register("void_shade", 1179727, 2956161,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidShadeEntity::new).dimensions(EntityDimensions.fixed(1.0F, 2.1F)).build());
    public static final EntityType<AmethystGolemEntity> AMETHYST_GOLEM_ENTITY = register("amethyst_golem", 5395026, 9267916,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, AmethystGolemEntity::new).dimensions(EntityDimensions.fixed(1.8F, 2.2F)).build());
    public static final EntityType<DesertRhinoEntity> DESERT_RHINO_ENTITY = register("desert_rhino", 7884087, 12031588,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, DesertRhinoEntity::new).dimensions(EntityDimensions.fixed(2.3F, 2.35F)).build());
    public static final EntityType<ShamanEntity> SHAMAN_ENTITY = register("shaman", 210734, 8739394,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ShamanEntity::new).dimensions(EntityDimensions.fixed(0.9F, 2.01F)).build());
    public static final EntityType<EnderwarthogEntity> ENDERWARTHOG_ENTITY = register("enderwarthog", 2828080, 6553725,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EnderwarthogEntity::new).dimensions(EntityDimensions.fixed(2.3F, 2.15F)).build());

    // Passive
    public static final EntityType<RedFungusEntity> RED_FUNGUS_ENTITY = register("red_fungus", 13084791, 13183785,
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RedFungusEntity::new).dimensions(EntityDimensions.fixed(1.05F, 1.4F)).build());
    public static final EntityType<BrownFungusEntity> BROWN_FUNGUS_ENTITY = register("brown_fungus", 13084791, 9925201,
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BrownFungusEntity::new).dimensions(EntityDimensions.fixed(1.35F, 1.8F)).build());
    public static final EntityType<NightmareEntity> NIGHTMARE_ENTITY = register("nightmare", 1381653, 3012863,
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NightmareEntity::new).fireImmune().dimensions(EntityDimensions.fixed(1.4F, 1.6F)).build());
    public static final EntityType<DragonEntity> DRAGON_ENTITY = register("dragon", 1842204, 14711290,
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DragonEntity::new).dimensions(EntityDimensions.changing(4.8F, 3.3F)).fireImmune().build());
    public static final EntityType<MammothEntity> MAMMOTH_ENTITY = register("mammoth", 4732462, 6376763,
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MammothEntity::new).dimensions(EntityDimensions.changing(2.8F, 3.5F)).build());
    public static final EntityType<EnderWhaleEntity> ENDER_WHALE_ENTITY = register("ender_whale", 1711667, 6179950,
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EnderWhaleEntity::new).dimensions(EntityDimensions.fixed(4.0F, 2.5F)).build());
    public static final EntityType<IguanaEntity> IGUANA_ENTITY = register("iguana", 11485475, 8988193,
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, IguanaEntity::new).dimensions(EntityDimensions.changing(1.5F, 0.5F)).build());
    public static final EntityType<DeerEntity> DEER_ENTITY = register("deer", 5780491, 9725748,
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DeerEntity::new).dimensions(EntityDimensions.changing(1.4F, 1.8F)).build());

    // Nonliving Entity
    public static final EntityType<ThrownRockEntity> THROWNROCK_ENTITY = register("thrown_rock", 0, 0,
            FabricEntityTypeBuilder.<ThrownRockEntity>create(SpawnGroup.MISC, ThrownRockEntity::new).dimensions(EntityDimensions.fixed(1.5F, 1.5F)).build());
    public static final EntityType<GildedStoneEntity> GILDEDSTONE_ENTITY = register("gilded_stone", 0, 0,
            FabricEntityTypeBuilder.<GildedStoneEntity>create(SpawnGroup.MISC, GildedStoneEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.7F)).build());
    public static final EntityType<TinyEyeEntity> TINYEYE_ENTITY = register("tiny_eye", 0, 0,
            FabricEntityTypeBuilder.<TinyEyeEntity>create(SpawnGroup.MISC, TinyEyeEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.4F)).build());
    public static final EntityType<VoidBulletEntity> VOID_BULLET_ENTITY = register("void_bullet", 0, 0,
            FabricEntityTypeBuilder.<VoidBulletEntity>create(SpawnGroup.MISC, VoidBulletEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());
    public static final EntityType<FireBreathEntity> FIRE_BREATH_ENTITY = register("fire_breath", 0, 0,
            FabricEntityTypeBuilder.<FireBreathEntity>create(SpawnGroup.MISC, FireBreathEntity::new).dimensions(EntityDimensions.fixed(0.3F, 0.3F)).build());
    public static final EntityType<BlazeGuardianShieldEntity> BLAZEGUARDIAN_SHIELD_ENTITY = register("blaze_guardian_shield", 0, 0,
            FabricEntityTypeBuilder.<BlazeGuardianShieldEntity>create(SpawnGroup.MISC, BlazeGuardianShieldEntity::new).fireImmune().dimensions(EntityDimensions.fixed(0.65F, 1.6F)).build());
    public static final EntityType<AmethystShardEntity> AMETHYST_SHARD_ENTITY = register("amethyst_shard", 0, 0,
            FabricEntityTypeBuilder.<AmethystShardEntity>create(SpawnGroup.MISC, AmethystShardEntity::new).dimensions(EntityDimensions.fixed(0.6F, 0.8F)).build());
    public static final EntityType<VoidCloudEntity> VOID_CLOUD_ENTITY = register("void_cloud", 0, 0,
            FabricEntityTypeBuilder.<VoidCloudEntity>create(SpawnGroup.MISC, VoidCloudEntity::new).dimensions(EntityDimensions.changing(6.0F, 0.5F)).fireImmune().build());
    public static final EntityType<VoidFragmentEntity> VOID_FRAGMENT_ENTITY = register("void_fragment", 1376335, 3670138,
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidFragmentEntity::new).dimensions(EntityDimensions.changing(1.0F, 1.0F)).build());

    // Damage Types
    public static final RegistryKey<DamageType> AMETHYST_SHARD = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("adventurez", "amethyst_shard"));
    public static final RegistryKey<DamageType> VOID_BULLET = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("adventurez", "void_bullet"));
    public static final RegistryKey<DamageType> FIRE_BREATH = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("adventurez", "fire_breath"));
    public static final RegistryKey<DamageType> TINY_EYE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("adventurez", "tiny_eye"));
    public static final RegistryKey<DamageType> ROCK = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("adventurez", "rock"));

    private static <T extends Entity> EntityType<T> register(String id, int primaryColor, int secondaryColor, EntityType<T> entityType) {
        if (primaryColor != 0) {
            Item item = Registry.register(Registries.ITEM, new Identifier("adventurez", "spawn_" + id),
                    new SpawnEggItem((EntityType<? extends MobEntity>) entityType, primaryColor, secondaryColor, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemInit.ADVENTUREZ_ITEM_GROUP).register(entries -> entries.add(item));
        }
        return Registry.register(Registries.ENTITY_TYPE, new Identifier("adventurez", id), entityType);
    }

    public static void init() {
        // Attributes
        FabricDefaultAttributeRegistry.register(STONEGOLEM_ENTITY, StoneGolemEntity.createStoneGolemAttributes());
        FabricDefaultAttributeRegistry.register(SMALLSTONEGOLEM_ENTITY, SmallStoneGolemEntity.createSmallStoneGolemAttributes());
        FabricDefaultAttributeRegistry.register(PIGLINBEAST_ENTITY, PiglinBeastEntity.createPiglinBeastAttributes());
        FabricDefaultAttributeRegistry.register(NIGHTMARE_ENTITY, NightmareEntity.createNightmareAttributes());
        FabricDefaultAttributeRegistry.register(SOULREAPER_ENTITY, SoulReaperEntity.createSoulReaperAttributes());
        FabricDefaultAttributeRegistry.register(NECROMANCER_ENTITY, NecromancerEntity.createNecromancerAttributes());
        FabricDefaultAttributeRegistry.register(WITHERPUPPET_ENTITY, WitherPuppetEntity.createWitherPuppetAttributes());
        FabricDefaultAttributeRegistry.register(SKELETON_VANGUARD_ENTITY, SkeletonVanguardEntity.createSkeletonVanguardAttributes());
        FabricDefaultAttributeRegistry.register(SUMMONER_ENTITY, SummonerEntity.createSummonerAttributes());
        FabricDefaultAttributeRegistry.register(BLAZEGUARDIAN_ENTITY, BlazeGuardianEntity.createBlazeGuardianAttributes());
        FabricDefaultAttributeRegistry.register(THE_EYE_ENTITY, TheEyeEntity.createTheEntityAttributes());
        FabricDefaultAttributeRegistry.register(VOID_SHADOW_ENTITY, VoidShadowEntity.createVoidShadowAttributes());
        FabricDefaultAttributeRegistry.register(RED_FUNGUS_ENTITY, RedFungusEntity.createRedFungusAttributes());
        FabricDefaultAttributeRegistry.register(BROWN_FUNGUS_ENTITY, BrownFungusEntity.createBrownFungusAttributes());
        FabricDefaultAttributeRegistry.register(ORC_ENTITY, OrcEntity.createOrkAttributes());
        FabricDefaultAttributeRegistry.register(DRAGON_ENTITY, DragonEntity.createDragonAttributes());
        FabricDefaultAttributeRegistry.register(MAMMOTH_ENTITY, MammothEntity.createMammothAttributes());
        FabricDefaultAttributeRegistry.register(VOID_FRAGMENT_ENTITY, VoidFragmentEntity.createVoidFragmentAttributes());
        FabricDefaultAttributeRegistry.register(VOID_SHADE_ENTITY, VoidShadeEntity.createVoidShadeAttributes());
        FabricDefaultAttributeRegistry.register(ENDER_WHALE_ENTITY, EnderWhaleEntity.createEnderWhaleAttributes());
        FabricDefaultAttributeRegistry.register(IGUANA_ENTITY, IguanaEntity.createIguanaAttributes());
        FabricDefaultAttributeRegistry.register(AMETHYST_GOLEM_ENTITY, AmethystGolemEntity.createAmethystGolemAttributes());
        FabricDefaultAttributeRegistry.register(DESERT_RHINO_ENTITY, DesertRhinoEntity.createDesertRhinoAttributes());
        FabricDefaultAttributeRegistry.register(SHAMAN_ENTITY, ShamanEntity.createShamanAttributes());
        FabricDefaultAttributeRegistry.register(DEER_ENTITY, DeerEntity.createDeerAttributes());
        FabricDefaultAttributeRegistry.register(ENDERWARTHOG_ENTITY, EnderwarthogEntity.createEnderwarthogAttributes());
    }
}