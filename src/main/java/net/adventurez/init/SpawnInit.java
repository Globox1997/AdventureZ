package net.adventurez.init;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class SpawnInit {

  public static void init() {
    Registry.BIOME.forEach(SpawnInit::handleBiome);
    RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> SpawnInit.handleBiome(biome));
  }

  private static void handleBiome(Biome biome) {
    if (biome.equals(Biomes.BASALT_DELTAS)) {
      biome.getEntitySpawnList(EntityInit.SMALLSTONEGOLEM_ENTITY.getSpawnGroup())
          .add(new Biome.SpawnEntry(EntityInit.SMALLSTONEGOLEM_ENTITY, 1, 1, 1));
    }
  }

}