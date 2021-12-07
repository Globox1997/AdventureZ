package net.adventurez.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;

import net.adventurez.entity.*;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.Heightmap;

public class SpawnInit {

    public static void init() {
        setSpawnRestriction();
        addSpawnEntries();
    }

    // MONSTER tries to spawn often, CREATURE tries more rarely to spawn + in groups
    private static void addSpawnEntries() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS), SpawnGroup.MONSTER, EntityInit.SMALLSTONEGOLEM_ENTITY, ConfigInit.CONFIG.small_stone_golem_spawn_weight, 1,
                1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES), SpawnGroup.MONSTER, EntityInit.BLAZEGUARDIAN_ENTITY, ConfigInit.CONFIG.blaze_guardian_spawn_weight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY), SpawnGroup.MONSTER, EntityInit.SOULREAPER_ENTITY, ConfigInit.CONFIG.nightmare_spawn_weight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.MUSHROOM), SpawnGroup.CREATURE, EntityInit.RED_FUNGUS_ENTITY, ConfigInit.CONFIG.fungus_spawn_weight, 2, 3);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.MUSHROOM), SpawnGroup.CREATURE, EntityInit.BROWN_FUNGUS_ENTITY, ConfigInit.CONFIG.fungus_spawn_weight, 2, 3);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.PLAINS), SpawnGroup.MONSTER, EntityInit.ORC_ENTITY, ConfigInit.CONFIG.orc_spawn_weight, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.ICY, Biome.Category.TAIGA), SpawnGroup.CREATURE, EntityInit.MAMMOTH_ENTITY, ConfigInit.CONFIG.mammoth_spawn_weight, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.THEEND), SpawnGroup.CREATURE, EntityInit.ENDER_WHALE_ENTITY, ConfigInit.CONFIG.ender_whale_spawn_weight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.MESA), SpawnGroup.CREATURE, EntityInit.IGUANA_ENTITY, ConfigInit.CONFIG.iguana_spawn_weight, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.DESERT), SpawnGroup.MONSTER, EntityInit.DESERT_RHINO_ENTITY, ConfigInit.CONFIG.desert_rhino_spawn_weight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.SWAMP), SpawnGroup.MONSTER, EntityInit.SHAMAN_ENTITY, ConfigInit.CONFIG.shaman_spawn_weight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS), SpawnGroup.MONSTER, EntityInit.ENDERWARTHOG_ENTITY,
                ConfigInit.CONFIG.enderwarthog_spawn_weight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.FOREST), SpawnGroup.CREATURE, EntityInit.DEER_ENTITY, ConfigInit.CONFIG.deer_spawn_weight, 2, 3);
    }

    private static void setSpawnRestriction() {
        SpawnRestrictionAccessor.callRegister(EntityInit.SMALLSTONEGOLEM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SmallStoneGolemEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.NECROMANCER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NecromancerEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.SUMMONER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SummonerEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.BLAZEGUARDIAN_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlazeGuardianEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.PIGLINBEAST_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnIgnoreLightLevel);
        SpawnRestrictionAccessor.callRegister(EntityInit.SOULREAPER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SoulReaperEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.RED_FUNGUS_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RedFungusEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.BROWN_FUNGUS_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BrownFungusEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.ORC_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, OrcEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.MAMMOTH_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.ENDER_WHALE_ENTITY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EnderWhaleEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.IGUANA_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IguanaEntity::isValidNaturalSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.DESERT_RHINO_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DesertRhinoEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.SHAMAN_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ShamanEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.DEER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestrictionAccessor.callRegister(EntityInit.ENDERWARTHOG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
    }

}
