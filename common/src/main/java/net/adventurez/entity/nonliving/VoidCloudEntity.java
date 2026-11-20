package net.adventurez.entity.nonliving;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.adventurez.init.EntityInit;
import net.adventurez.init.ParticleInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.damagesource.DamageSource;

public class VoidCloudEntity extends Entity {
    private static final EntityDataAccessor<Float> RADIUS;
    private final Map<Entity, Integer> affectedEntities;
    private int duration;
    private int waitTime;
    private int reapplicationDelay;
    private int durationOnUse;
    private float radiusOnUse;
    private float radiusGrowth;
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUuid;
    private float particleTicker;

    public VoidCloudEntity(EntityType<? extends VoidCloudEntity> entityType, Level world) {
        super(entityType, world);
        this.affectedEntities = Maps.newHashMap();
        this.duration = 800;
        this.waitTime = 20;
        this.reapplicationDelay = 20;
        this.noPhysics = true;
        this.setRadius(3.0F);
        this.radiusGrowth = -0.01F;
    }

    public VoidCloudEntity(Level world, double x, double y, double z) {
        this(EntityInit.VOID_CLOUD, world);
        this.setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        builder.define(RADIUS, 0.5F);
    }

    @Override
    public void refreshDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.refreshDimensions();
        this.setPos(d, e, f);
    }

    public void setRadius(float radius) {
        if (!this.level().isClientSide()) {
            this.getEntityData().set(RADIUS, Mth.clamp(radius, 0.0F, 32.0F));
        }
    }

    public float getRadius() {
        return (Float) this.getEntityData().get(RADIUS);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void tick() {
        super.tick();
        float f = this.getRadius();
        if (this.level().isClientSide()) {
            particleTicker += (float) Math.PI / 128F;
            double angle = 2 * Math.PI * particleTicker;
            for (float i = 1.2F; i >= 0.2F; i -= 0.1F) {
                this.level().addParticle(ParticleInit.VOID_CLOUD_PARTICLE, (double) this.getX() + this.getRadius() * -(i - 1.2F) * Math.sin(angle + Math.PI * i),
                        (double) this.getY() + 0.4F + this.level().getRandom().nextFloat() * 0.1F, (double) this.getZ() + this.getRadius() * -(i - 1.2F) * Math.cos(angle + Math.PI * i), 0.0D,
                        0.0D, 0.0D);
                this.level().addParticle(ParticleInit.VOID_CLOUD_PARTICLE, (double) this.getX() + this.getRadius() * -(i - 1.2F) * Math.sin(angle + Math.PI * i + Math.PI),
                        (double) this.getY() + 0.4F + this.level().getRandom().nextFloat() * 0.1F, (double) this.getZ() + this.getRadius() * -(i - 1.2F) * Math.cos(angle + Math.PI * i + Math.PI),
                        0.0D, 0.0D, 0.0D);
            }
        } else {
            if (this.tickCount >= this.waitTime + this.duration) {
                this.discard();
                return;
            }

            if (this.radiusGrowth != 0.0F) {
                f += this.radiusGrowth;
                if (f < 0.5F) {
                    this.discard();
                    return;
                }

                this.setRadius(f);
            }

            if (this.tickCount % 5 == 0) {
                this.affectedEntities.entrySet().removeIf((entry) -> {
                    return this.tickCount >= (Integer) entry.getValue();
                });
                List<Player> list2 = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox());
                if (!list2.isEmpty()) {
                    Iterator<Player> var27 = list2.iterator();

                    while (true) {
                        double aa;
                        Player livingEntity;
                        do {
                            do {
                                do {
                                    if (!var27.hasNext()) {
                                        return;
                                    }

                                    livingEntity = (Player) var27.next();
                                } while (this.affectedEntities.containsKey(livingEntity));
                            } while (!livingEntity.isAffectedByPotions());

                            double y = livingEntity.getX() - this.getX();
                            double z = livingEntity.getZ() - this.getZ();
                            aa = y * y + z * z;
                        } while (!(aa <= (double) (f * f)));
                        this.affectedEntities.put(livingEntity, this.tickCount + this.reapplicationDelay);
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 50, 2, false, false, false));
                        if (this.radiusOnUse != 0.0F) {
                            f += this.radiusOnUse;
                            if (f < 0.5F) {
                                this.discard();
                                return;
                            }

                            this.setRadius(f);
                        }
                        if (this.durationOnUse != 0) {
                            this.duration += this.durationOnUse;
                            if (this.duration <= 0) {
                                this.discard();
                                return;
                            }
                        }
                    }
                }

            }
        }

    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUuid != null && this.level() instanceof ServerLevel) {
            Entity entity = ((ServerLevel) this.level()).getEntity(this.ownerUuid);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity) entity;
            }
        }

        return this.owner;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput nbt) {
        this.tickCount = nbt.getIntOr("Age", 0);
        this.duration = nbt.getIntOr("Duration", 0);
        this.waitTime = nbt.getIntOr("WaitTime", 0);
        this.reapplicationDelay = nbt.getIntOr("ReapplicationDelay", 0);
        this.durationOnUse = nbt.getIntOr("DurationOnUse", 0);
        this.radiusOnUse = nbt.getFloatOr("RadiusOnUse", 0.0F);
        this.radiusGrowth = nbt.getFloatOr("RadiusPerTick", 0.0F);
        this.setRadius(nbt.getFloatOr("Radius", 0.0F));
        nbt.read("Owner", UUIDUtil.CODEC).ifPresent(uuid -> this.ownerUuid = uuid);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput nbt) {
        nbt.putInt("Age", this.tickCount);
        nbt.putInt("Duration", this.duration);
        nbt.putInt("ReapplicationDelay", this.reapplicationDelay);
        nbt.putInt("DurationOnUse", this.durationOnUse);
        nbt.putFloat("RadiusOnUse", this.radiusOnUse);
        nbt.putFloat("RadiusPerTick", this.radiusGrowth);
        nbt.putFloat("Radius", this.getRadius());
        if (this.ownerUuid != null)
            nbt.store("Owner", UUIDUtil.CODEC, this.ownerUuid);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        return false;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        if (RADIUS.equals(data))
            this.refreshDimensions();
        super.onSyncedDataUpdated(data);
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE_ENTITY;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(this.getRadius() * 2.0F, 0.5F);
    }

    static {
        RADIUS = SynchedEntityData.defineId(VoidCloudEntity.class, EntityDataSerializers.FLOAT);
    }
}
