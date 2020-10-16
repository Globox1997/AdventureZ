package net.adventurez.mixin;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.NetherFortressFeature;

@Mixin(NetherFortressFeature.class)
public class NetherFortressFeatureMixin {

  private static int blazeGuardianSpawnWeight = ConfigInit.CONFIG.blaze_guardian_spawn_weight;
  private static int necromancerSpawnWeight = ConfigInit.CONFIG.necromancer_spawn_weight;

  @Shadow
  @Final
  private static List<SpawnSettings.SpawnEntry> MONSTER_SPAWNS;
  private static final List<SpawnSettings.SpawnEntry> ADDED_SPAWNS;

  @Inject(method = "Lnet/minecraft/world/gen/feature/NetherFortressFeature;getMonsterSpawns()L;", at = @At(value = "HEAD"), cancellable = true)
  public void getMonsterSpawns(CallbackInfoReturnable<List<SpawnSettings.SpawnEntry>> info) {
    List<SpawnSettings.SpawnEntry> spawnersList = new ArrayList<>(MONSTER_SPAWNS);
    spawnersList.addAll(ADDED_SPAWNS);
    info.setReturnValue(spawnersList);
  }

  static {
    ADDED_SPAWNS = ImmutableList.of(
        new SpawnSettings.SpawnEntry(EntityInit.NECROMANCER_ENTITY, necromancerSpawnWeight, 1, 1),
        new SpawnSettings.SpawnEntry(EntityInit.BLAZEGUARDIAN_ENTITY, blazeGuardianSpawnWeight, 1, 1));
  }
}
