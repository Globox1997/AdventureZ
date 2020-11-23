package net.adventurez.init;

// import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
// import net.minecraft.util.registry.BuiltinRegistries;
// import net.minecraft.util.registry.Registry;
// import net.minecraft.world.biome.Biome;
// import net.minecraft.world.biome.Biomes;
// import net.minecraft.entity.SpawnGroup;
// import net.minecraft.entity.mob.ZombieEntity;
// import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
//import net.minecraft.world.biome.BiomeKeys;
//import net.minecraft.entity.mob.BlazeEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ImmutableMap;

import net.adventurez.entity.*;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.SpawnSettings;

public class SpawnInit {

  public static void init() {
    addSpawnEntries();
    SpawnRestriction();
  }

  public static void addSpawnEntries() {
    for (Biome biome : BuiltinRegistries.BIOME) {
      if (biome.getCategory().equals(Biome.Category.NETHER)) {
        addMobSpawnToBiome(biome, SpawnGroup.MONSTER,
            new SpawnSettings.SpawnEntry(EntityInit.SMALLSTONEGOLEM_ENTITY,
                ConfigInit.CONFIG.small_stone_golem_spawn_weight, 1, 1),
            new SpawnSettings.SpawnEntry(EntityInit.SOULREAPER_ENTITY, ConfigInit.CONFIG.nightmare_spawn_weight, 1, 1));
      }
      if (biome.getCategory().equals(Biome.Category.MUSHROOM)) {
        addMobSpawnToBiome(biome, SpawnGroup.CREATURE,
            new SpawnSettings.SpawnEntry(EntityInit.BROWN_FUNGUS_ENTITY, ConfigInit.CONFIG.fungus_spawn_weight, 2, 3),
            new SpawnSettings.SpawnEntry(EntityInit.RED_FUNGUS_ENTITY, ConfigInit.CONFIG.fungus_spawn_weight, 2, 3));
      }

      // if (biome.getCategory().equals(Biome.Category.ICY) ||
      // biome.getCategory().equals(Biome.Category.TAIGA)) {
      // addMobSpawnToBiome(biome, SpawnGroup.MONSTER,
      // new SpawnSettings.SpawnEntry(EntityInit.SUMMONER_ENTITY,
      // ConfigInit.CONFIG.summoner_spawn_weight, 1, 1));
      // }
    }

  }

  public static void addMobSpawnToBiome(Biome biome, SpawnGroup classification, SpawnSettings.SpawnEntry... spawners) {
    convertImmutableSpawners(biome);
    List<SpawnSettings.SpawnEntry> spawnersList = new ArrayList<>(
        biome.getSpawnSettings().spawners.get(classification));
    spawnersList.addAll(Arrays.asList(spawners));
    biome.getSpawnSettings().spawners.put(classification, spawnersList);
  }

  private static void convertImmutableSpawners(Biome biome) {
    if (biome.getSpawnSettings().spawners instanceof ImmutableMap) {
      biome.getSpawnSettings().spawners = new HashMap<>(biome.getSpawnSettings().spawners);
    }
  }

  public static void SpawnRestriction() {
    SpawnRestriction.register(EntityInit.SMALLSTONEGOLEM_ENTITY, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SmallStoneGolemEntity::canSpawn);
    SpawnRestriction.register(EntityInit.NECROMANCER_ENTITY, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NecromancerEntity::canSpawn);
    SpawnRestriction.register(EntityInit.SUMMONER_ENTITY, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SummonerEntity::canSpawn);
    SpawnRestriction.register(EntityInit.BLAZEGUARDIAN_ENTITY, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
    SpawnRestriction.register(EntityInit.PIGLINBEAST_ENTITY, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnIgnoreLightLevel);
    SpawnRestriction.register(EntityInit.SOULREAPER_ENTITY, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SoulReaperEntity::canSpawn);
    SpawnRestriction.register(EntityInit.RED_FUNGUS_ENTITY, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RedFungusEntity::canSpawn);
    SpawnRestriction.register(EntityInit.BROWN_FUNGUS_ENTITY, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BrownFungusEntity::canSpawn);
  }

}
