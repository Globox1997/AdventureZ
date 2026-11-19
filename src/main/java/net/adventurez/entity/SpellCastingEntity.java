package net.adventurez.entity;

import java.util.EnumSet;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class SpellCastingEntity extends Monster {
    public SpellCastingEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.spell = SpellCastingEntity.Spell.NONE;
    }

    private static final EntityDataAccessor<Byte> SPELL;
    protected int spellTicks;
    private SpellCastingEntity.Spell spell;

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SPELL, (byte) 0);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.spellTicks = tag.getInt("SpellTicks");
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SpellTicks", this.spellTicks);
    }

    public boolean isSpellcasting() {
        if (this.level().isClientSide()) {
            return (Byte) this.entityData.get(SPELL) > 0;
        } else {
            return this.spellTicks > 0;
        }
    }

    public void setSpell(SpellCastingEntity.Spell spell) {
        this.spell = spell;
        this.entityData.set(SPELL, (byte) spell.id);
    }

    protected SpellCastingEntity.Spell getSpell() {
        return !this.level().isClientSide() ? this.spell : SpellCastingEntity.Spell.byId((Byte) this.entityData.get(SPELL));
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }

    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide() && this.isSpellcasting() && !(this instanceof ShamanEntity)) {
            SpellCastingEntity.Spell spell = this.getSpell();
            float d = (float) spell.particleVelocity[0];
            float e = (float) spell.particleVelocity[1];
            float f = (float) spell.particleVelocity[2];
            float g = this.yBodyRot * 0.017453292F + Mth.cos((float) this.tickCount * 0.6662F) * 0.25F;
            float h = Mth.cos(g);
            float i = Mth.sin(g);
            this.level().addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, d, e, f), (float) this.getX() + (float) h * 0.92f, this.getY() + 2.32f,
                    this.getZ() + (float) i * 0.92f, 0.0, 0.0, 0.0);
        }

    }

    protected int getSpellTicks() {
        return this.spellTicks;
    }

    protected abstract SoundEvent getCastSpellSound();

    static {
        SPELL = SynchedEntityData.defineId(SpellCastingEntity.class, EntityDataSerializers.BYTE);
    }

    public static enum Spell {
        NONE(0, 0.0D, 0.0D, 0.0D), SUMMON_PUPPET(1, 0.01D, 0.01D, 0.015D), WITHERING(2, 0.01D, 0.01D, 0.015D), SHIELD(3, 0.01D, 0.01D, 0.01D), TELEPORT(4, 0.005D, 0.005D, 0.015D),
        THUNDERBOLT(5, 0.015D, 0.015D, 0.015D);

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

        public boolean canUse() {
            LivingEntity livingEntity = SpellCastingEntity.this.getTarget();
            if (livingEntity != null && livingEntity.isAlive()) {
                if (SpellCastingEntity.this.isSpellcasting()) {
                    return false;
                } else {
                    return SpellCastingEntity.this.tickCount >= this.startTime;
                }
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            LivingEntity livingEntity = SpellCastingEntity.this.getTarget();
            return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
        }

        public void start() {
            this.spellCooldown = this.getInitialCooldown();
            SpellCastingEntity.this.spellTicks = this.getSpellTicks();
            this.startTime = SpellCastingEntity.this.tickCount + this.startTimeDelay();
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
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
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
                SpellCastingEntity.this.getLookControl().setLookAt(SpellCastingEntity.this.getTarget(), (float) SpellCastingEntity.this.getMaxHeadYRot(),
                        (float) SpellCastingEntity.this.getMaxHeadXRot());
            }

        }
    }
}
