package net.adventurez.entity.nonliving;

import net.adventurez.entity.BlazeGuardianEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class BlazeGuardianShieldEntity extends Entity {
    public final BlazeGuardianEntity owner;
    public final String name;
    private int hit;

    public BlazeGuardianShieldEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
        owner = null;
        name = "null";
    }

    public BlazeGuardianShieldEntity(EntityType<? extends Entity> entityType, BlazeGuardianEntity owner, String name) {
        super(entityType, owner.getWorld());
        this.owner = owner;
        this.name = name;
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient() && (this.owner == null || this.owner.isDead() || this.owner.isRemoved())) {
            this.discard();
        }
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isIn(DamageTypeTags.IS_PROJECTILE)) {
            return false;
        }
        hit++;
        if (hit > 1 + this.getWorld().getRandom().nextInt(2)) {
            this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.HOSTILE, 1.0F, 1.0F);
            if (this.owner != null) {
                this.removeShield(this.name, this.owner);
            }
            if (!this.getWorld().isClient()) {
                this.discard();
            }
        } else {
            this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.HOSTILE, 1.0F, 1.0F);
        }
        return this.isInvulnerableTo(source);
    }

    @Override
    public boolean isPartOf(Entity entity) {
        return this == entity || this.owner == entity;
    }

    @Override
    public boolean shouldSave() {
        return false;
    }

    private void removeShield(String string, BlazeGuardianEntity blazeGuardianEntity) {
        switch (string) {
        case "shield_north":
            blazeGuardianEntity.getDataTracker().set(BlazeGuardianEntity.SHIELD_NORTH, false);
            break;
        case "shield_east":
            blazeGuardianEntity.getDataTracker().set(BlazeGuardianEntity.SHIELD_EAST, false);
            break;
        case "shield_south":
            blazeGuardianEntity.getDataTracker().set(BlazeGuardianEntity.SHIELD_SOUTH, false);
            break;
        case "shield_west":
            blazeGuardianEntity.getDataTracker().set(BlazeGuardianEntity.SHIELD_WEST, false);
            break;
        default:
            return;
        }
    }

}
