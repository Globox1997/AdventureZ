package net.adventurez.init;

// import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
// import net.minecraft.util.registry.BuiltinRegistries;
// import net.minecraft.util.registry.Registry;
// import net.minecraft.world.biome.Biome;
// import net.minecraft.world.biome.Biomes;
// import net.minecraft.entity.SpawnGroup;
// import net.minecraft.entity.mob.ZombieEntity;
// import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ImmutableMap;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
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
            new SpawnSettings.SpawnEntry(EntityInit.SMALLSTONEGOLEM_ENTITY, 1, 1, 1),
            new SpawnSettings.SpawnEntry(EntityInit.NIGHTMARE_ENTITY, 5, 1, 1),
            new SpawnSettings.SpawnEntry(EntityInit.NECROMANCER_ENTITY, 1, 1, 1));
      }
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
    SpawnRestriction.register(EntityInit.NIGHTMARE_ENTITY, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
    SpawnRestriction.register(EntityInit.SMALLSTONEGOLEM_ENTITY, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
    SpawnRestriction.register(EntityInit.NECROMANCER_ENTITY, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
  }

}
