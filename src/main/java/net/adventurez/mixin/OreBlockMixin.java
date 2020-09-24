package net.adventurez.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.entity.PiglinBeastEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.Heightmap;

@Mixin(OreBlock.class)
public abstract class OreBlockMixin {
  private boolean isBeastNear = false;

  public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
    PlayerEntity player = world.getClosestPlayer((double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), 16D,
        false);
    if (state.isOf(Blocks.NETHER_GOLD_ORE) && !world.isClient() && player.world.getRegistryKey() == World.NETHER) {
      List<Entity> list = world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(40D),
          EntityPredicates.EXCEPT_SPECTATOR);
      for (int i = 0; i < list.size(); ++i) {
        Entity entity = (Entity) list.get(i);
        if (entity.getType() == EntityInit.PIGLINBEAST_ENTITY) {
          isBeastNear = true;
        }
      }
      int spawnChanceInt = world.getRandom().nextInt(ConfigInit.CONFIG.piglin_beast_ore_chance);
      if (spawnChanceInt == 0 && !isBeastNear) {
        PiglinBeastEntity beastEntity = (PiglinBeastEntity) EntityInit.PIGLINBEAST_ENTITY.create((World) world);
        for (int counter = 0; counter < 100; counter++) {
          float randomFloat = world.getRandom().nextFloat() * 6.2831855F;
          int posX = pos.getX() + MathHelper.floor(MathHelper.cos(randomFloat) * 26.0F + world.getRandom().nextInt(5));
          int posZ = pos.getZ() + MathHelper.floor(MathHelper.sin(randomFloat) * 26.0F + world.getRandom().nextInt(5));
          int posY = world.getTopY(Heightmap.Type.WORLD_SURFACE, posX, posZ);
          BlockPos spawnPos = new BlockPos(posX, posY, posZ);
          if (world.isRegionLoaded(spawnPos.getX() - 4, spawnPos.getY() - 4, spawnPos.getZ() - 4, spawnPos.getX() + 4,
              spawnPos.getY() + 4, spawnPos.getZ() + 4)
              && world.getChunkManager().shouldTickChunk(new ChunkPos(spawnPos)) && SpawnHelper
                  .canSpawn(SpawnRestriction.Location.ON_GROUND, world, spawnPos, EntityInit.PIGLINBEAST_ENTITY)) {
            beastEntity.refreshPositionAndAngles(spawnPos, 0.0F, 0.0F);
            world.spawnEntity(beastEntity);
            beastEntity.playSpawnEffects();
            break;
          }
        }
      }
    }
  }

}