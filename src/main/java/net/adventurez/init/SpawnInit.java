package net.adventurez.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

import net.adventurez.entity.*;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.Heightmap;

public class SpawnInit {

    public static void init() {
        setSpawnRestriction();
        addSpawnEntries();
    }

    // MONSTER tries to spawn often, CREATURE tries more rarely to spawn + in groups
    private static void addSpawnEntries() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS), SpawnGroup.MONSTER, EntityInit.MINI_BLACKSTONE_GOLEM, ConfigInit.CONFIG.mini_blackstone_golem_spawn_weight,
                1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether().and(BiomeSelectors.excludeByKey(BiomeKeys.BASALT_DELTAS)), SpawnGroup.MONSTER, EntityInit.BLAZE_GUARDIAN,
                ConfigInit.CONFIG.blaze_guardian_spawn_weight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.NETHER_FOSSIL_HAS_STRUCTURE), SpawnGroup.MONSTER, EntityInit.SOUL_REAPER, ConfigInit.CONFIG.nightmare_spawn_weight, 1, 1);

        BiomeModifications.addSpawn(BiomeSelectors.tag(TagInit.IS_MUSHROOM), SpawnGroup.CREATURE, EntityInit.RED_FUNGUS, ConfigInit.CONFIG.fungus_spawn_weight, 2, 3);
        BiomeModifications.addSpawn(BiomeSelectors.tag(TagInit.IS_MUSHROOM), SpawnGroup.CREATURE, EntityInit.BROWN_FUNGUS, ConfigInit.CONFIG.fungus_spawn_weight, 2, 3);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.VILLAGE_PLAINS_HAS_STRUCTURE), SpawnGroup.MONSTER, EntityInit.ORC, ConfigInit.CONFIG.orc_spawn_weight, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IGLOO_HAS_STRUCTURE), SpawnGroup.CREATURE, EntityInit.MAMMOTH, ConfigInit.CONFIG.mammoth_spawn_weight, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.CREATURE, EntityInit.ENDER_WHALE, ConfigInit.CONFIG.ender_whale_spawn_weight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_BADLANDS), SpawnGroup.CREATURE, EntityInit.IGUANA, ConfigInit.CONFIG.iguana_spawn_weight, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.DESERT_PYRAMID_HAS_STRUCTURE), SpawnGroup.MONSTER, EntityInit.DESERT_RHINO, ConfigInit.CONFIG.desert_rhino_spawn_weight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.SWAMP_HUT_HAS_STRUCTURE), SpawnGroup.MONSTER, EntityInit.SHAMAN, ConfigInit.CONFIG.shaman_spawn_weight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd().and(BiomeSelectors.excludeByKey(BiomeKeys.THE_END, BiomeKeys.END_BARRENS)), SpawnGroup.MONSTER, EntityInit.ENDERWARTHOG,
                ConfigInit.CONFIG.enderwarthog_spawn_weight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_FOREST), SpawnGroup.CREATURE, EntityInit.DEER, ConfigInit.CONFIG.deer_spawn_weight, 2, 3);
    }

    private static void setSpawnRestriction() {
        SpawnRestriction.register(EntityInit.MINI_BLACKSTONE_GOLEM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MiniBlackstoneGolemEntity::canSpawn);
        SpawnRestriction.register(EntityInit.NECROMANCER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NecromancerEntity::canSpawn);
        SpawnRestriction.register(EntityInit.SUMMONER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SummonerEntity::canSpawn);
        SpawnRestriction.register(EntityInit.BLAZE_GUARDIAN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlazeGuardianEntity::canSpawn);
        SpawnRestriction.register(EntityInit.PIGLIN_BEAST, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnIgnoreLightLevel);
        SpawnRestriction.register(EntityInit.SOUL_REAPER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SoulReaperEntity::canSpawn);
        SpawnRestriction.register(EntityInit.RED_FUNGUS, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RedFungusEntity::canSpawn);
        SpawnRestriction.register(EntityInit.BROWN_FUNGUS, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BrownFungusEntity::canSpawn);
        SpawnRestriction.register(EntityInit.ORC, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, OrcEntity::canSpawn);
        SpawnRestriction.register(EntityInit.MAMMOTH, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestriction.register(EntityInit.ENDER_WHALE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EnderWhaleEntity::canSpawn);
        SpawnRestriction.register(EntityInit.IGUANA, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IguanaEntity::isValidNaturalSpawn);
        SpawnRestriction.register(EntityInit.DESERT_RHINO, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DesertRhinoEntity::canSpawn);
        SpawnRestriction.register(EntityInit.SHAMAN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ShamanEntity::canSpawn);
        SpawnRestriction.register(EntityInit.DEER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestriction.register(EntityInit.ENDERWARTHOG, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EnderwarthogEntity::canSpawn);
    }

}
