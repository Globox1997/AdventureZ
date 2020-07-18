package net.adventurez.mixin;

import net.adventurez.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SpawnRestriction.class)
public class SpawnRestrictionMixin {

  @Shadow
  private static <T extends MobEntity> void register(EntityType<T> type, SpawnRestriction.Location location,
      Heightmap.Type heightmapType, SpawnRestriction.SpawnPredicate<T> predicate) {
  }

  static {
    register(EntityInit.NIGHTMARE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        MobEntity::canMobSpawn);
    register(EntityInit.SMALLSTONEGOLEM_ENTITY, SpawnRestriction.Location.ON_GROUND,
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
  }
}