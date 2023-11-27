package net.adventurez.entity.nonliving;

import java.util.UUID;

import net.adventurez.init.EntityInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.Nullable;

public class TinyEyeEntity extends ExplosiveProjectileEntity {

    private Entity target;
    @Nullable
    private Direction direction;
    private int stepCount;
    private double targetX;
    private double targetY;
    private double targetZ;
    @Nullable
    private UUID targetUuid;

    public TinyEyeEntity(EntityType<? extends TinyEyeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Environment(EnvType.CLIENT)
    public TinyEyeEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this(EntityInit.TINY_EYE, world);
        this.refreshPositionAndAngles(x, y, z, this.getYaw(), this.getPitch());
        this.setVelocity(velocityX, velocityY, velocityZ);
    }

    public TinyEyeEntity(World world, LivingEntity owner, Entity target, Direction.Axis axis) {
        this(EntityInit.TINY_EYE, world);
        this.setOwner(owner);
        BlockPos blockPos = owner.getBlockPos();
        double d = (double) blockPos.getX() + 0.5D;
        double e = (double) blockPos.getY() + 0.5D;
        double f = (double) blockPos.getZ() + 0.5D;
        this.refreshPositionAndAngles(d, e, f, this.getYaw(), this.getPitch());
        this.target = target;
        this.direction = Direction.UP;
        this.movingAround();
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        if (this.target != null)
            tag.putUuid("Target", this.target.getUuid());
        if (this.direction != null)
            tag.putInt("Dir", this.direction.getId());

        tag.putInt("Steps", this.stepCount);
        tag.putDouble("TXD", this.targetX);
        tag.putDouble("TYD", this.targetY);
        tag.putDouble("TZD", this.targetZ);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.stepCount = tag.getInt("Steps");
        this.targetX = tag.getDouble("TXD");
        this.targetY = tag.getDouble("TYD");
        this.targetZ = tag.getDouble("TZD");
        if (tag.contains("Dir", 99))
            this.direction = Direction.byId(tag.getInt("Dir"));

        if (tag.containsUuid("Target"))
            this.targetUuid = tag.getUuid("Target");

    }

    @Override
    protected void initDataTracker() {
    }

    private void movingAround() {
        double d = 0.5D;
        BlockPos blockPos;
        if (this.target == null)
            blockPos = this.getBlockPos().down();
        else {
            d = (double) this.target.getHeight() * 0.5D;
            blockPos = BlockPos.ofFloored(this.target.getX(), this.target.getY() + d, this.target.getZ());
        }

        double e = (double) blockPos.getX() + 0.5D;
        double f = (double) blockPos.getY() + d;
        double g = (double) blockPos.getZ() + 0.5D;
        double h = e - this.getX();
        double j = f - this.getY();
        double k = g - this.getZ();
        double l = (double) MathHelper.sqrt((float) (h * h + j * j + k * k));
        if (l == 0.0D) {
            this.targetX = 0.0D;
            this.targetY = 0.0D;
            this.targetZ = 0.0D;
        } else {
            this.targetX = h / l * 0.15D;
            this.targetY = j / l * 0.15D;
            this.targetZ = k / l * 0.15D;
        }

        this.velocityDirty = true;
        this.stepCount = 10 + this.random.nextInt(5) * 10;
    }

    @Override
    public void checkDespawn() {
        if (this.getWorld().getDifficulty() == Difficulty.PEACEFUL)
            this.discard();
    }

    @Override
    public void tick() {
        Vec3d vec3d;
        if (!this.getWorld().isClient()) {
            if (this.target == null && this.targetUuid != null) {
                this.target = ((ServerWorld) this.getWorld()).getEntity(this.targetUuid);
                if (this.target == null) {
                    this.targetUuid = null;
                }
            }

            if (this.target == null || !this.target.isAlive() || this.target instanceof PlayerEntity && ((PlayerEntity) this.target).isSpectator()) {
                if (!this.hasNoGravity()) {
                    this.setVelocity(this.getVelocity().add(0.0D, -0.04D, 0.0D));
                }
            } else {
                this.targetX = MathHelper.clamp(this.targetX * 1.025D, -1.0D, 1.0D);
                this.targetY = MathHelper.clamp(this.targetY * 1.025D, -1.0D, 1.0D);
                this.targetZ = MathHelper.clamp(this.targetZ * 1.025D, -1.0D, 1.0D);
                vec3d = this.getVelocity();
                this.setVelocity(vec3d.add((this.targetX - vec3d.x) * 0.2D, (this.targetY - vec3d.y) * 0.2D, (this.targetZ - vec3d.z) * 0.2D));
            }

            HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
            if (hitResult.getType() != HitResult.Type.MISS) {
                this.onCollision(hitResult);
            }
        }

        this.checkBlockCollision();
        vec3d = this.getVelocity();
        this.updatePosition(this.getX() + vec3d.x, this.getY() + vec3d.y, this.getZ() + vec3d.z);
        ProjectileUtil.setRotationFromVelocity(this, 0.5F);
        if (this.getWorld().isClient()) {
        } else if (this.target != null && !this.target.isRemoved()) {
            if (this.stepCount > 0) {
                --this.stepCount;
                if (this.stepCount == 0) {
                    this.movingAround();
                }
            }

            if (this.direction != null) {
                BlockPos blockPos = this.getBlockPos();
                Direction.Axis axis = this.direction.getAxis();
                if (this.getWorld().isTopSolid(blockPos.offset(this.direction), this)) {
                    this.movingAround();
                } else {
                    BlockPos blockPos2 = this.target.getBlockPos();
                    if (axis == Direction.Axis.X && blockPos.getX() == blockPos2.getX() || axis == Direction.Axis.Z && blockPos.getZ() == blockPos2.getZ()
                            || axis == Direction.Axis.Y && blockPos.getY() == blockPos2.getY()) {
                        this.movingAround();
                    }
                }
            }
        }

    }

    @Override
    public boolean canHit(Entity entity) {
        return super.canHit(entity) && !entity.noClip;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distance) {
        return distance < 16384.0D;
    }

    @Override
    public float getBrightnessAtEyes() {
        return 1.0F;
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = this.getOwner();
        Entity hittedEntity = entityHitResult.getEntity();
        if (!this.getWorld().isClient() && entity != null && hittedEntity != entity && !(hittedEntity instanceof TinyEyeEntity) || hittedEntity instanceof ArrowEntity) {
            this.playSound(SoundEvents.ENTITY_ENDER_EYE_DEATH, 1.0F, 1.0F);
            if (hittedEntity instanceof LivingEntity) {
                this.teleportEntityRandom((LivingEntity) hittedEntity);
                hittedEntity.damage(createDamageSource(this), 3.0F);
            }
            this.discard();
        }

    }

    private DamageSource createDamageSource(Entity entity) {
        return entity.getDamageSources().create(EntityInit.TINY_EYE_KEY, entity);
    }

    @SuppressWarnings("deprecation")
    private void teleportEntityRandom(LivingEntity livingEntity) {
        for (int counter = 0; counter < 100; counter++) {
            float randomFloat = this.getWorld().getRandom().nextFloat() * 6.2831855F;
            int posX = livingEntity.getBlockPos().getX() + MathHelper.floor(MathHelper.cos(randomFloat) * 9.0F + livingEntity.getWorld().getRandom().nextInt(30));
            int posZ = livingEntity.getBlockPos().getZ() + MathHelper.floor(MathHelper.sin(randomFloat) * 9.0F + livingEntity.getWorld().getRandom().nextInt(30));
            int posY = livingEntity.getWorld().getTopY(Heightmap.Type.WORLD_SURFACE, posX, posZ);
            BlockPos teleportPos = new BlockPos(posX, posY, posZ);
            if (livingEntity.getWorld().isRegionLoaded(teleportPos.getX() - 4, teleportPos.getY() - 4, teleportPos.getZ() - 4, teleportPos.getX() + 4, teleportPos.getY() + 4, teleportPos.getZ() + 4)
                    && SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, livingEntity.getWorld(), teleportPos, EntityType.PLAYER)) {
                if (!this.getWorld().isClient()) {
                    livingEntity.teleport(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());
                }
                livingEntity.getWorld().playSound(null, teleportPos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.HOSTILE, 1.0F, 1.0F);
                break;
            }
        }
    }

    @Override
    public void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.playSound(SoundEvents.ENTITY_ENDER_EYE_DEATH, 1.0F, 1.0F);
        if (!this.getWorld().isClient())
            this.discard();
    }

    @Override
    public void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
    }

}
