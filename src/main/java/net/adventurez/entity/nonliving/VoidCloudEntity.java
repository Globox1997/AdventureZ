package net.adventurez.entity.nonliving;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.adventurez.init.EntityInit;
import net.adventurez.init.ParticleInit;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class VoidCloudEntity extends Entity {
    private static final TrackedData<Float> RADIUS;
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

    public VoidCloudEntity(EntityType<? extends VoidCloudEntity> entityType, World world) {
        super(entityType, world);
        this.affectedEntities = Maps.newHashMap();
        this.duration = 800;
        this.waitTime = 20;
        this.reapplicationDelay = 20;
        this.noClip = true;
        this.setRadius(3.0F);
        this.radiusGrowth = -0.01F;
    }

    public VoidCloudEntity(World world, double x, double y, double z) {
        this(EntityInit.VOID_CLOUD_ENTITY, world);
        this.setPosition(x, y, z);
    }

    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(RADIUS, 0.5F);
    }

    @Override
    public void calculateDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.calculateDimensions();
        this.setPosition(d, e, f);
    }

    public void setRadius(float radius) {
        if (!this.world.isClient) {
            this.getDataTracker().set(RADIUS, MathHelper.clamp(radius, 0.0F, 32.0F));
        }
    }

    public float getRadius() {
        return (Float) this.getDataTracker().get(RADIUS);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void tick() {
        super.tick();
        float f = this.getRadius();
        if (this.world.isClient) {
            particleTicker += (float) Math.PI / 128F;
            double angle = 2 * Math.PI * particleTicker;
            for (float i = 1.2F; i >= 0.2F; i -= 0.1F) {
                this.world.addParticle(ParticleInit.VOID_CLOUD_PARTICLE, (double) this.getX() + this.getRadius() * -(i - 1.2F) * Math.sin(angle + Math.PI * i),
                        (double) this.getY() + 0.4F + this.world.random.nextFloat() * 0.1F, (double) this.getZ() + this.getRadius() * -(i - 1.2F) * Math.cos(angle + Math.PI * i), 0.0D, 0.0D, 0.0D);
                this.world.addParticle(ParticleInit.VOID_CLOUD_PARTICLE, (double) this.getX() + this.getRadius() * -(i - 1.2F) * Math.sin(angle + Math.PI * i + Math.PI),
                        (double) this.getY() + 0.4F + this.world.random.nextFloat() * 0.1F, (double) this.getZ() + this.getRadius() * -(i - 1.2F) * Math.cos(angle + Math.PI * i + Math.PI), 0.0D,
                        0.0D, 0.0D);
            }
        } else {
            if (this.age >= this.waitTime + this.duration) {
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

            if (this.age % 5 == 0) {
                this.affectedEntities.entrySet().removeIf((entry) -> {
                    return this.age >= (Integer) entry.getValue();
                });
                List<PlayerEntity> list2 = this.world.getNonSpectatingEntities(PlayerEntity.class, this.getBoundingBox());
                if (!list2.isEmpty()) {
                    Iterator<PlayerEntity> var27 = list2.iterator();

                    while (true) {
                        double aa;
                        PlayerEntity livingEntity;
                        do {
                            do {
                                do {
                                    if (!var27.hasNext()) {
                                        return;
                                    }

                                    livingEntity = (PlayerEntity) var27.next();
                                } while (this.affectedEntities.containsKey(livingEntity));
                            } while (!livingEntity.isAffectedBySplashPotions());

                            double y = livingEntity.getX() - this.getX();
                            double z = livingEntity.getZ() - this.getZ();
                            aa = y * y + z * z;
                        } while (!(aa <= (double) (f * f)));
                        this.affectedEntities.put(livingEntity, this.age + this.reapplicationDelay);
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 50, 2, false, false, false));
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
        if (this.owner == null && this.ownerUuid != null && this.world instanceof ServerWorld) {
            Entity entity = ((ServerWorld) this.world).getEntity(this.ownerUuid);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity) entity;
            }
        }

        return this.owner;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.age = nbt.getInt("Age");
        this.duration = nbt.getInt("Duration");
        this.waitTime = nbt.getInt("WaitTime");
        this.reapplicationDelay = nbt.getInt("ReapplicationDelay");
        this.durationOnUse = nbt.getInt("DurationOnUse");
        this.radiusOnUse = nbt.getFloat("RadiusOnUse");
        this.radiusGrowth = nbt.getFloat("RadiusPerTick");
        this.setRadius(nbt.getFloat("Radius"));
        if (nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("Age", this.age);
        nbt.putInt("Duration", this.duration);
        nbt.putInt("ReapplicationDelay", this.reapplicationDelay);
        nbt.putInt("DurationOnUse", this.durationOnUse);
        nbt.putFloat("RadiusOnUse", this.radiusOnUse);
        nbt.putFloat("RadiusPerTick", this.radiusGrowth);
        nbt.putFloat("Radius", this.getRadius());
        if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (RADIUS.equals(data)) {
            this.calculateDimensions();
        }
        super.onTrackedDataSet(data);
    }

    @Override
    public PistonBehavior getPistonBehavior() {
        return PistonBehavior.IGNORE;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.changing(this.getRadius() * 2.0F, 0.5F);
    }

    static {
        RADIUS = DataTracker.registerData(VoidCloudEntity.class, TrackedDataHandlerRegistry.FLOAT);
    }
}
