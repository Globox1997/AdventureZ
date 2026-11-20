package net.adventurez.entity.nonliving;

import net.adventurez.entity.BlazeGuardianEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.server.level.ServerLevel;

public class BlazeGuardianShieldEntity extends Entity {
    public final BlazeGuardianEntity owner;
    public final String name;
    private int hit;

    public BlazeGuardianShieldEntity(EntityType<? extends Entity> entityType, Level world) {
        super(entityType, world);
        owner = null;
        name = "null";
    }

    public BlazeGuardianShieldEntity(EntityType<? extends Entity> entityType, BlazeGuardianEntity owner, String name) {
        super(entityType, owner.level());
        this.owner = owner;
        this.name = name;
    }

    @Override
    protected void defineSynchedData(Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(ValueInput nbt) {
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput nbt) {
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && (this.owner == null || this.owner.isDeadOrDying() || this.owner.isRemoved())) {
            this.discard();
        }
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            return false;
        }
        hit++;
        if (hit > 1 + this.level().getRandom().nextInt(2)) {
            this.level().playSound(null, this.blockPosition(), SoundEvents.SHIELD_BREAK.value(), SoundSource.HOSTILE, 1.0F, 1.0F);
            if (this.owner != null) {
                this.removeShield(this.name, this.owner);
            }
            if (!this.level().isClientSide()) {
                this.discard();
            }
        } else {
            this.level().playSound(null, this.blockPosition(), SoundEvents.SHIELD_BLOCK.value(), SoundSource.HOSTILE, 1.0F, 1.0F);
        }
        return this.isInvulnerableToBase(source);
    }

    @Override
    public boolean is(Entity entity) {
        return this == entity || this.owner == entity;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    private void removeShield(String string, BlazeGuardianEntity blazeGuardianEntity) {
        switch (string) {
        case "shield_north":
            blazeGuardianEntity.getEntityData().set(BlazeGuardianEntity.SHIELD_NORTH, false);
            break;
        case "shield_east":
            blazeGuardianEntity.getEntityData().set(BlazeGuardianEntity.SHIELD_EAST, false);
            break;
        case "shield_south":
            blazeGuardianEntity.getEntityData().set(BlazeGuardianEntity.SHIELD_SOUTH, false);
            break;
        case "shield_west":
            blazeGuardianEntity.getEntityData().set(BlazeGuardianEntity.SHIELD_WEST, false);
            break;
        default:
            return;
        }
    }

}
