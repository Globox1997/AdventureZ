package net.adventurez.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.PiglinBeastEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.adventurez.init.ItemInit;
import net.adventurez.item.armor.StoneGolemArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
  private int ticker = 0;
  private int killedPiglins = 0;
  private int piglinCooldown = 0;

  public PlayerEntityMixin(EntityType<PlayerEntity> type, World world) {
    super(type, world);
  };

  @Inject(method = "tick()V", at = @At(value = "TAIL"))
  private void piglinBeastSpawn(CallbackInfo info) {
    if (!world.isClient && ConfigInit.CONFIG.piglin_beast_attack_piglin_spawn_chance != 0) {
      PlayerEntity playerEntity = (PlayerEntity) (Object) this;
      if (playerEntity != null && !playerEntity.isCreative() && playerEntity.world.getRegistryKey() == World.NETHER) {
        if (this.getAttacking() != null && this.getAttacking() instanceof PiglinEntity) {
          int spawnChanceInt = world.random.nextInt(ConfigInit.CONFIG.piglin_beast_attack_piglin_spawn_chance) + 1;
          if (this.getAttacking().isDead() && spawnChanceInt != 0) {
            ticker++;
            if (ticker == 5 && getBeasts()) {
              piglinCooldown = 12000;
              killedPiglins++;
              if (killedPiglins == 6) {
                if (spawnChanceInt == 1) {
                  PiglinBeastEntity beastEntity = (PiglinBeastEntity) EntityInit.PIGLINBEAST_ENTITY.create(world);
                  int posYOfPlayer = playerEntity.getBlockPos().getY();
                  for (int counter = 0; counter < 100; counter++) {
                    float randomFloat = world.random.nextFloat() * 6.2831855F;
                    int posX = this.getBlockPos().getX()
                        + MathHelper.floor(MathHelper.cos(randomFloat) * 26.0F + world.random.nextInt(5));
                    int posZ = this.getBlockPos().getZ()
                        + MathHelper.floor(MathHelper.sin(randomFloat) * 26.0F + world.random.nextInt(5));
                    int posY = posYOfPlayer - 20 + world.getRandom().nextInt(40);
                    BlockPos spawnPos = new BlockPos(posX, posY, posZ);
                    if (this.world.isRegionLoaded(spawnPos.getX() - 4, spawnPos.getY() - 4, spawnPos.getZ() - 4,
                        spawnPos.getX() + 4, spawnPos.getY() + 4, spawnPos.getZ() + 4)
                        && this.world.getChunkManager().shouldTickChunk(new ChunkPos(spawnPos)) && SpawnHelper.canSpawn(
                            SpawnRestriction.Location.ON_GROUND, this.world, spawnPos, EntityInit.PIGLINBEAST_ENTITY)) {
                      beastEntity.refreshPositionAndAngles(spawnPos, 0.0F, 0.0F);
                      world.spawnEntity(beastEntity);
                      beastEntity.playSpawnEffects();
                      break;
                    }
                  }
                }
                killedPiglins = 0;
              } else if (killedPiglins > 6) {
                killedPiglins = 0;
              }
              ticker = 0;
            }
          }
        }
        if (piglinCooldown > 0) {
          if (piglinCooldown == 1) {
            killedPiglins = 0;
          }
          piglinCooldown--;
        }
      }
    }
  }

  private boolean getBeasts() {
    List<Entity> list = this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(40D),
        EntityPredicates.EXCEPT_SPECTATOR);
    for (int i = 0; i < list.size(); ++i) {
      Entity entity = (Entity) list.get(i);
      if (entity.getType() == EntityInit.PIGLINBEAST_ENTITY) {
        return false;
      }
    }
    return true;
  }

  @Override
  @Environment(EnvType.CLIENT)
  public boolean doesRenderOnFire() {
    ItemStack golemChestplate = this.getEquippedStack(EquipmentSlot.CHEST);
    boolean fireActivated = this.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(ItemInit.STONE_GOLEM_CHESTPLATE)
        && StoneGolemArmor.fireTime(golemChestplate);
    return this.isOnFire() && !this.isSpectator() && !fireActivated;
  }

}