package net.adventurez.entity;

import java.util.EnumSet;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class SpellCastingEntity extends HostileEntity {
  public SpellCastingEntity(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
    this.spell = SpellCastingEntity.Spell.NONE;
  }

  private static final TrackedData<Byte> SPELL;
  protected int spellTicks;
  private SpellCastingEntity.Spell spell;

  protected void initDataTracker() {
    super.initDataTracker();
    this.dataTracker.startTracking(SPELL, (byte) 0);
  }

  public void readCustomDataFromTag(CompoundTag tag) {
    super.readCustomDataFromTag(tag);
    this.spellTicks = tag.getInt("SpellTicks");
  }

  public void writeCustomDataToTag(CompoundTag tag) {
    super.writeCustomDataToTag(tag);
    tag.putInt("SpellTicks", this.spellTicks);
  }

  public boolean isSpellcasting() {
    if (this.world.isClient) {
      return (Byte) this.dataTracker.get(SPELL) > 0;
    } else {
      return this.spellTicks > 0;
    }
  }

  public void setSpell(SpellCastingEntity.Spell spell) {
    this.spell = spell;
    this.dataTracker.set(SPELL, (byte) spell.id);
  }

  protected SpellCastingEntity.Spell getSpell() {
    return !this.world.isClient ? this.spell : SpellCastingEntity.Spell.byId((Byte) this.dataTracker.get(SPELL));
  }

  protected void mobTick() {
    super.mobTick();
    if (this.spellTicks > 0) {
      --this.spellTicks;
    }

  }

  public void tick() {
    super.tick();
    if (this.world.isClient && this.isSpellcasting()) {
      SpellCastingEntity.Spell spell = this.getSpell();
      double d = spell.particleVelocity[0];
      double e = spell.particleVelocity[1];
      double f = spell.particleVelocity[2];
      float g = this.bodyYaw * 0.017453292F + MathHelper.cos((float) this.age * 0.6662F) * 0.25F;
      float h = MathHelper.cos(g);
      float i = MathHelper.sin(g);
      this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double) h * 0.92D, this.getY() + 2.32D,
          this.getZ() + (double) i * 0.92D, d, e, f);
    }

  }

  protected int getSpellTicks() {
    return this.spellTicks;
  }

  protected abstract SoundEvent getCastSpellSound();

  static {
    SPELL = DataTracker.registerData(SpellCastingEntity.class, TrackedDataHandlerRegistry.BYTE);
  }

  public static enum Spell {
    NONE(0, 0.0D, 0.0D, 0.0D), SUMMON_PUPPET(1, 0.01D, 0.01D, 0.015D), WITHERING(2, 0.01D, 0.01D, 0.015D),
    SHIELD(3, 0.01D, 0.01D, 0.01D), TELEPORT(4, 0.005D, 0.005D, 0.015D), THUNDERBOLT(5, 0.015D, 0.015D, 0.015D);

    private final int id;
    private final double[] particleVelocity;

    private Spell(int id, double particleVelocityX, double particleVelocityY, double particleVelocityZ) {
      this.id = id;
      this.particleVelocity = new double[] { particleVelocityX, particleVelocityY, particleVelocityZ };
    }

    public static SpellCastingEntity.Spell byId(int id) {
      SpellCastingEntity.Spell[] var1 = values();
      int var2 = var1.length;

      for (int var3 = 0; var3 < var2; ++var3) {
        SpellCastingEntity.Spell spell = var1[var3];
        if (id == spell.id) {
          return spell;
        }
      }

      return NONE;
    }
  }

  public abstract class CastSpellGoal extends Goal {
    protected int spellCooldown;
    protected int startTime;

    protected CastSpellGoal() {
    }

    public boolean canStart() {
      LivingEntity livingEntity = SpellCastingEntity.this.getTarget();
      if (livingEntity != null && livingEntity.isAlive()) {
        if (SpellCastingEntity.this.isSpellcasting()) {
          return false;
        } else {
          return SpellCastingEntity.this.age >= this.startTime;
        }
      } else {
        return false;
      }
    }

    public boolean shouldContinue() {
      LivingEntity livingEntity = SpellCastingEntity.this.getTarget();
      return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
    }

    public void start() {
      this.spellCooldown = this.getInitialCooldown();
      SpellCastingEntity.this.spellTicks = this.getSpellTicks();
      this.startTime = SpellCastingEntity.this.age + this.startTimeDelay();
      SoundEvent soundEvent = this.getSoundPrepare();
      if (soundEvent != null) {
        SpellCastingEntity.this.playSound(soundEvent, 1.0F, 1.0F);
      }

      SpellCastingEntity.this.setSpell(this.getSpell());
    }

    public void tick() {
      --this.spellCooldown;
      if (this.spellCooldown == 0) {
        this.castSpell();
        SpellCastingEntity.this.playSound(SpellCastingEntity.this.getCastSpellSound(), 1.0F, 1.0F);
      }

    }

    protected abstract void castSpell();

    protected int getInitialCooldown() {
      return 20;
    }

    protected abstract int getSpellTicks();

    protected abstract int startTimeDelay();

    @Nullable
    protected abstract SoundEvent getSoundPrepare();

    protected abstract SpellCastingEntity.Spell getSpell();
  }

  public class LookAtTargetGoal extends Goal {
    public LookAtTargetGoal() {
      this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    public boolean canStart() {
      return SpellCastingEntity.this.getSpellTicks() > 0;
    }

    public void start() {
      super.start();
      SpellCastingEntity.this.navigation.stop();
    }

    public void stop() {
      super.stop();
      SpellCastingEntity.this.setSpell(SpellCastingEntity.Spell.NONE);
    }

    public void tick() {
      if (SpellCastingEntity.this.getTarget() != null) {
        SpellCastingEntity.this.getLookControl().lookAt(SpellCastingEntity.this.getTarget(),
            (float) SpellCastingEntity.this.getBodyYawSpeed(), (float) SpellCastingEntity.this.getLookPitchSpeed());
      }

    }
  }
}
