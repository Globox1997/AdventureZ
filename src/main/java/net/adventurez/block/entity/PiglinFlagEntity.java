package net.adventurez.block.entity;

import java.util.List;

import net.adventurez.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;

public class PiglinFlagEntity extends BlockEntity implements Tickable {
  private int flagWave;

  public PiglinFlagEntity() {
    super(BlockInit.PIGLIN_FLAG_ENTITY);
  }

  @Override
  public void fromTag(BlockState state, CompoundTag tag) {
    super.fromTag(state, tag);
    flagWave = tag.getInt("Flagging");
  }

  @Override
  public CompoundTag toTag(CompoundTag tag) {
    super.toTag(tag);
    tag.putInt("Flagging", flagWave);
    return tag;
  }

  @Override
  public void markDirty() {
    super.markDirty();
    sendUpdate();
  }

  private void sendUpdate() {
    if (this.world != null) {
      BlockState state = this.world.getBlockState(this.pos);
      (this.world).updateListeners(this.pos, state, state, 3);
    }
  }

  @Override
  public void tick() {
    this.update();
  }

  public void update() {

    if (this.flagWave < 2400) {
      this.flagWave++;
    }
    if (flagWave >= 2400 && this.world.getClosestPlayer((double) this.getPos().getX(), (double) this.getPos().getY(),
        (double) this.getPos().getZ(), 3D, null) != null) {
      getPiglins();
      markDirty();
      if (world.isClient) {
        for (int i = 0; i < 20; i++) {
          double d = (double) pos.getX() + (double) world.random.nextFloat();
          double e = (double) pos.getY() + (double) world.random.nextFloat() + 1D;
          double f = (double) pos.getZ() + (double) world.random.nextFloat();
          world.addParticle(ParticleTypes.HAPPY_VILLAGER, d, e, f, 0.0D, 1.0D, 0.0D);
        }
      }
      this.flagWave = 0;
    }
  }

  public void getPiglins() {
    List<Entity> list = this.world.getEntitiesByClass(LivingEntity.class, new Box(pos).expand(40D),
        EntityPredicates.EXCEPT_SPECTATOR);
    for (int i = 0; i < list.size(); ++i) {
      Entity entity = (Entity) list.get(i);
      if (entity.getType() == EntityType.PIGLIN) {
        PiglinEntity piglin = (PiglinEntity) entity;
        if (piglin.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) {
          piglin.getBrain().forget(MemoryModuleType.ANGRY_AT);
          piglin.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
          piglin.getBrain().forget(MemoryModuleType.WALK_TARGET);
        }
      }
    }
  }

}