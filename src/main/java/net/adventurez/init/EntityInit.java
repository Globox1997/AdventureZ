package net.adventurez.init;

import net.adventurez.entity.*;
import net.adventurez.entity.nonliving.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityInit {
    // Hostile
    public static final EntityType<StoneGolemEntity> STONEGOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, StoneGolemEntity::new).fireImmune()
            .dimensions(EntityDimensions.fixed(3.36F, 4.44F)).build();
    public static final EntityType<SmallStoneGolemEntity> SMALLSTONEGOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SmallStoneGolemEntity::new).fireImmune()
            .dimensions(EntityDimensions.fixed(1.2F, 1.2F)).build();
    public static final EntityType<PiglinBeastEntity> PIGLINBEAST_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PiglinBeastEntity::new).fireImmune()
            .dimensions(EntityDimensions.fixed(1.55F, 3.35F)).build();
    public static final EntityType<SoulReaperEntity> SOULREAPER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SoulReaperEntity::new).fireImmune()
            .dimensions(EntityDimensions.fixed(0.7F, 2.4F)).build();
    public static final EntityType<NecromancerEntity> NECROMANCER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, NecromancerEntity::new).fireImmune()
            .dimensions(EntityDimensions.fixed(0.9F, 2.4F)).build();
    public static final EntityType<WitherPuppetEntity> WITHERPUPPET_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WitherPuppetEntity::new).fireImmune()
            .dimensions(EntityDimensions.fixed(0.7F, 1.32F)).build();
    public static final EntityType<SkeletonVanguardEntity> SKELETON_VANGUARD_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SkeletonVanguardEntity::new)
            .dimensions(EntityDimensions.fixed(0.7F, 2.1F)).build();
    public static final EntityType<SummonerEntity> SUMMONER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SummonerEntity::new).dimensions(EntityDimensions.fixed(0.9F, 2.65F)).build();
    public static final EntityType<BlazeGuardianEntity> BLAZEGUARDIAN_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BlazeGuardianEntity::new).fireImmune()
            .dimensions(EntityDimensions.fixed(0.8F, 2.25F)).build();
    public static final EntityType<TheEyeEntity> THE_EYE_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TheEyeEntity::new).fireImmune().dimensions(EntityDimensions.fixed(2.8F, 3.5F))
            .build();
    public static final EntityType<VoidShadowEntity> VOID_SHADOW_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidShadowEntity::new).fireImmune()
            .dimensions(EntityDimensions.fixed(10.2F, 15.3F)).build();
    public static final EntityType<OrcEntity> ORC_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OrcEntity::new).dimensions(EntityDimensions.changing(1.35F, 2.2F)).build();
    public static final EntityType<VoidFragmentEntity> VOID_FRAGMENT_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidFragmentEntity::new)
            .dimensions(EntityDimensions.changing(1.0F, 1.0F)).build();
    public static final EntityType<VoidShadeEntity> VOID_SHADE_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidShadeEntity::new).dimensions(EntityDimensions.fixed(1.0F, 2.1F))
            .build();
    // Passive
    public static final EntityType<RedFungusEntity> RED_FUNGUS_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RedFungusEntity::new).dimensions(EntityDimensions.fixed(1.05F, 1.4F))
            .build();
    public static final EntityType<BrownFungusEntity> BROWN_FUNGUS_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BrownFungusEntity::new).dimensions(EntityDimensions.fixed(1.35F, 1.8F))
            .build();
    public static final EntityType<NightmareEntity> NIGHTMARE_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NightmareEntity::new).fireImmune()
            .dimensions(EntityDimensions.fixed(1.4F, 1.6F)).build();
    public static final EntityType<DragonEntity> DRAGON_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DragonEntity::new).dimensions(EntityDimensions.changing(4.8F, 3.3F)).fireImmune()
            .build();
    public static final EntityType<MammothEntity> MAMMOTH_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MammothEntity::new).dimensions(EntityDimensions.changing(2.8F, 3.5F)).build();

    // Nonliving Entity
    public static final EntityType<ThrownRockEntity> THROWNROCK_ENTITY = FabricEntityTypeBuilder.<ThrownRockEntity>create(SpawnGroup.MISC, ThrownRockEntity::new)
            .dimensions(EntityDimensions.fixed(1.5F, 1.5F)).build();
    public static final EntityType<GildedStoneEntity> GILDEDSTONE_ENTITY = FabricEntityTypeBuilder.<GildedStoneEntity>create(SpawnGroup.MISC, GildedStoneEntity::new)
            .dimensions(EntityDimensions.fixed(0.4F, 0.7F)).build();
    public static final EntityType<TinyEyeEntity> TINYEYE_ENTITY = FabricEntityTypeBuilder.<TinyEyeEntity>create(SpawnGroup.MISC, TinyEyeEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.4F))
            .build();
    public static final EntityType<VoidBulletEntity> VOID_BULLET_ENTITY = FabricEntityTypeBuilder.<VoidBulletEntity>create(SpawnGroup.MISC, VoidBulletEntity::new)
            .dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build();
    public static final EntityType<FireBreathEntity> FIRE_BREATH_ENTITY = FabricEntityTypeBuilder.<FireBreathEntity>create(SpawnGroup.MISC, FireBreathEntity::new)
            .dimensions(EntityDimensions.fixed(0.3F, 0.3F)).build();
    public static final EntityType<BlazeGuardianShieldEntity> BLAZEGUARDIAN_SHIELD_ENTITY = FabricEntityTypeBuilder.<BlazeGuardianShieldEntity>create(SpawnGroup.MISC, BlazeGuardianShieldEntity::new)
            .fireImmune().dimensions(EntityDimensions.changing(0.65F, 1.6F)).build();

    public static void init() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "stone_golem"), STONEGOLEM_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "thrown_rock"), THROWNROCK_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "gilded_stone"), GILDEDSTONE_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "small_stone_golem"), SMALLSTONEGOLEM_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "piglin_beast"), PIGLINBEAST_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "nightmare"), NIGHTMARE_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "soul_reaper"), SOULREAPER_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "necromancer"), NECROMANCER_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "wither_puppet"), WITHERPUPPET_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "skeleton_vanguard"), SKELETON_VANGUARD_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "summoner"), SUMMONER_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "blaze_guardian"), BLAZEGUARDIAN_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "the_eye"), THE_EYE_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "void_shadow"), VOID_SHADOW_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "tiny_eye"), TINYEYE_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "red_fungus"), RED_FUNGUS_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "brown_fungus"), BROWN_FUNGUS_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "orc"), ORC_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "dragon"), DRAGON_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "mammoth"), MAMMOTH_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "void_fragment"), VOID_FRAGMENT_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "void_shade"), VOID_SHADE_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "void_bullet"), VOID_BULLET_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "fire_breath"), FIRE_BREATH_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "blaze_guardian_shield"), BLAZEGUARDIAN_SHIELD_ENTITY);

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

        // Spawn Eggs
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_stone_golem"), new SpawnEggItem(STONEGOLEM_ENTITY, 2956072, 1445648, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_small_stone_golem"),
                new SpawnEggItem(SMALLSTONEGOLEM_ENTITY, 4077380, 4400440, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_piglin_beast"), new SpawnEggItem(PIGLINBEAST_ENTITY, 5121815, 14192743, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_nightmare"), new SpawnEggItem(NIGHTMARE_ENTITY, 1381653, 3012863, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_soul_reaper"), new SpawnEggItem(SOULREAPER_ENTITY, 1381653, 5329747, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_necromancer"), new SpawnEggItem(NECROMANCER_ENTITY, 1447446, 15514145, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_wither_puppet"), new SpawnEggItem(WITHERPUPPET_ENTITY, 1250067, 3092271, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_skeleton_vanguard"),
                new SpawnEggItem(SKELETON_VANGUARD_ENTITY, 12369084, 11766305, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_summoner"), new SpawnEggItem(SUMMONER_ENTITY, 12369084, 5847892, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_blaze_guardian"), new SpawnEggItem(BLAZEGUARDIAN_ENTITY, 16766248, 9122817, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_the_eye"), new SpawnEggItem(THE_EYE_ENTITY, 1984565, 1059889, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_void_shadow"), new SpawnEggItem(VOID_SHADOW_ENTITY, 1441901, 393244, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_red_fungus"), new SpawnEggItem(RED_FUNGUS_ENTITY, 13084791, 13183785, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_brown_fungus"), new SpawnEggItem(BROWN_FUNGUS_ENTITY, 13084791, 9925201, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_orc"), new SpawnEggItem(ORC_ENTITY, 2255437, 3512689, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_dragon"), new SpawnEggItem(DRAGON_ENTITY, 1842204, 14711290, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_mammoth"), new SpawnEggItem(MAMMOTH_ENTITY, 4732462, 6376763, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_void_fragment"), new SpawnEggItem(VOID_FRAGMENT_ENTITY, 1376335, 3670138, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("adventurez", "spawn_void_shade"), new SpawnEggItem(VOID_SHADE_ENTITY, 1179727, 2956161, new Item.Settings().group(ItemGroup.MISC)));
    }
}