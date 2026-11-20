package net.adventurez.init;

import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;

import net.adventurez.AdventureMain;
import net.adventurez.entity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;

public class SpawnInit {

    public static final Identifier BIOME_MODIFIER_ID = AdventureMain.identifierOf("spawns");
    public static final Identifier STRUCTURE_MODIFIER_ID = AdventureMain.identifierOf("fortress_spawns");

    private static final TagKey<Biome> IS_MUSHROOM = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath("c", "is_mushroom"));

    // MONSTER tries to spawn often, CREATURE tries more rarely to spawn + in groups
    public static void addSpawns(SpawnRegistrar registrar) {
        registrar.add(context -> context.is(Biomes.BASALT_DELTAS), MobCategory.MONSTER, EntityInit.MINI_BLACKSTONE_GOLEM, ConfigInit.CONFIG.mini_blackstone_golem_spawn_weight, 1, 1);
        registrar.add(context -> context.isInNether() && !context.is(Biomes.BASALT_DELTAS), MobCategory.MONSTER, EntityInit.BLAZE_GUARDIAN, ConfigInit.CONFIG.blaze_guardian_spawn_weight, 1, 1);
        registrar.add(context -> context.is(BiomeTags.HAS_NETHER_FOSSIL), MobCategory.MONSTER, EntityInit.SOUL_REAPER, ConfigInit.CONFIG.nightmare_spawn_weight, 1, 1);

        registrar.add(context -> context.is(IS_MUSHROOM), MobCategory.CREATURE, EntityInit.RED_FUNGUS, ConfigInit.CONFIG.fungus_spawn_weight, 2, 3);
        registrar.add(context -> context.is(IS_MUSHROOM), MobCategory.CREATURE, EntityInit.BROWN_FUNGUS, ConfigInit.CONFIG.fungus_spawn_weight, 2, 3);
        registrar.add(context -> context.is(BiomeTags.HAS_VILLAGE_PLAINS), MobCategory.MONSTER, EntityInit.ORC, ConfigInit.CONFIG.orc_spawn_weight, 2, 4);
        registrar.add(context -> context.is(BiomeTags.HAS_IGLOO), MobCategory.CREATURE, EntityInit.MAMMOTH, ConfigInit.CONFIG.mammoth_spawn_weight, 2, 2);
        registrar.add(BiomeContext::isInEnd, MobCategory.CREATURE, EntityInit.ENDER_WHALE, ConfigInit.CONFIG.ender_whale_spawn_weight, 1, 1);
        registrar.add(context -> context.is(BiomeTags.IS_BADLANDS), MobCategory.CREATURE, EntityInit.IGUANA, ConfigInit.CONFIG.iguana_spawn_weight, 1, 2);
        registrar.add(context -> context.is(BiomeTags.HAS_DESERT_PYRAMID), MobCategory.MONSTER, EntityInit.DESERT_RHINO, ConfigInit.CONFIG.desert_rhino_spawn_weight, 1, 1);
        registrar.add(context -> context.is(BiomeTags.HAS_SWAMP_HUT), MobCategory.MONSTER, EntityInit.SHAMAN, ConfigInit.CONFIG.shaman_spawn_weight, 1, 1);
        registrar.add(context -> context.isInEnd() && !context.is(Biomes.THE_END) && !context.is(Biomes.END_BARRENS), MobCategory.MONSTER, EntityInit.ENDERWARTHOG,
                ConfigInit.CONFIG.enderwarthog_spawn_weight, 1, 2);
        registrar.add(context -> context.is(BiomeTags.IS_FOREST), MobCategory.CREATURE, EntityInit.DEER, ConfigInit.CONFIG.deer_spawn_weight, 2, 3);
        registrar.add(context -> context.is(BiomeTags.IS_FOREST), MobCategory.CREATURE, EntityInit.SKUNK, ConfigInit.CONFIG.skunk_spawn_weight, 1, 2);
        registrar.add(context -> context.isInEnd() && !context.is(Biomes.THE_END) && !context.is(Biomes.END_BARRENS), MobCategory.CREATURE, EntityInit.CRYSTAL_BEETLE,
                ConfigInit.CONFIG.crystal_beetle_spawn_weight, 2, 3);
        registrar.add(context -> context.is(BiomeTags.IS_BADLANDS), MobCategory.MONSTER, EntityInit.TERRACOTTA_GOLEM, ConfigInit.CONFIG.terracotta_golem_spawn_weight, 1, 1);
    }

    public static void addFortressSpawns(ObjIntConsumer<MobSpawnSettings.SpawnerData> registrar) {
        if (ConfigInit.CONFIG.necromancer_spawn_weight > 0) {
            registrar.accept(new MobSpawnSettings.SpawnerData(EntityInit.NECROMANCER, ConstantInt.of(1)), ConfigInit.CONFIG.necromancer_spawn_weight);
        }
        if (ConfigInit.CONFIG.blaze_guardian_spawn_weight > 0) {
            registrar.accept(new MobSpawnSettings.SpawnerData(EntityInit.BLAZE_GUARDIAN, ConstantInt.of(1)), ConfigInit.CONFIG.blaze_guardian_spawn_weight);
        }
    }

    public static void registerSpawnPlacements(SpawnPlacementRegistrar registrar) {
        registrar.register(EntityInit.MINI_BLACKSTONE_GOLEM, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MiniBlackstoneGolemEntity::canSpawn);
        registrar.register(EntityInit.NECROMANCER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NecromancerEntity::canSpawn);
        registrar.register(EntityInit.SUMMONER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SummonerEntity::canSpawn);
        registrar.register(EntityInit.BLAZE_GUARDIAN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlazeGuardianEntity::canSpawn);
        registrar.register(EntityInit.PIGLIN_BEAST, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        registrar.register(EntityInit.SOUL_REAPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SoulReaperEntity::canSpawn);
        registrar.register(EntityInit.RED_FUNGUS, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RedFungusEntity::canSpawn);
        registrar.register(EntityInit.BROWN_FUNGUS, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BrownFungusEntity::canSpawn);
        registrar.register(EntityInit.ORC, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, OrcEntity::canSpawn);
        registrar.register(EntityInit.MAMMOTH, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        registrar.register(EntityInit.ENDER_WHALE, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnderWhaleEntity::canSpawn);
        registrar.register(EntityInit.IGUANA, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IguanaEntity::checkAnimalSpawnRules);
        registrar.register(EntityInit.DESERT_RHINO, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DesertRhinoEntity::canSpawn);
        registrar.register(EntityInit.SHAMAN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ShamanEntity::canSpawn);
        registrar.register(EntityInit.DEER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        registrar.register(EntityInit.SKUNK, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        registrar.register(EntityInit.ENDERWARTHOG, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnderwarthogEntity::canSpawn);
        registrar.register(EntityInit.CRYSTAL_BEETLE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrystalBeetleEntity::canSpawn);
        registrar.register(EntityInit.TERRACOTTA_GOLEM, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TerracottaGolemEntity::canSpawn);
    }

    public interface BiomeContext {

        boolean is(ResourceKey<Biome> biome);

        boolean is(TagKey<Biome> tag);

        boolean isInNether();

        boolean isInEnd();
    }

    public interface SpawnRegistrar {
        void add(Predicate<BiomeContext> biomes, MobCategory category, EntityType<?> entityType, int weight, int minGroupSize, int maxGroupSize);
    }

    public interface SpawnPlacementRegistrar {
        <T extends Mob> void register(EntityType<T> entityType, SpawnPlacementType placementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate);
    }

}
