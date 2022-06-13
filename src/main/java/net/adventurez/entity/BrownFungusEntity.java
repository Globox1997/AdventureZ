package net.adventurez.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.adventurez.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.UniversalAngerGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.TimeHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class BrownFungusEntity extends PathAwareEntity implements Angerable {
    private static final UniformIntProvider ANGER_TIME_RANGE;
    private int angerTime;
    private UUID angryAt;

    public BrownFungusEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0F;
    }

    @Override
    public void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.SEAGRASS), false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new UniversalAngerGoal<>(this, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
    }

    public static DefaultAttributeContainer.Builder createBrownFungusAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D);
    }

    public static boolean canSpawn(EntityType<BrownFungusEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(BlockTags.MOOSHROOMS_SPAWNABLE_ON) && world.getBaseLightLevel(pos, 0) > 8;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.FUNGUS_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.FUNGUS_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.FUNGUS_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeAngerToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readAngerFromNbt(this.world, nbt);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient) {
            this.tickAngerLogic((ServerWorld) this.world, true);
            int randomMushroom = this.world.random.nextInt(20000);
            if (randomMushroom == 1997 && (world.getBlockState(this.getBlockPos().down()).isOf(Blocks.MYCELIUM) || world.getBlockState(this.getBlockPos().down()).isOf(Blocks.GRASS_BLOCK))
                    && this.world.getBlockState(this.getBlockPos()).isAir()) {
                this.world.setBlockState(this.getBlockPos(), Blocks.BROWN_MUSHROOM.getDefaultState());
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getSource() != null && source.getSource() instanceof PlayerEntity) {
            PlayerEntity attacker = (PlayerEntity) source.getSource();
            this.setAngryAt(attacker.getUuid());
        }
        return this.isInvulnerableTo(source) ? false : super.damage(source, amount);

    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Override
    public void setAngerTime(int ticks) {
        this.angerTime = ticks;
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {
        this.angryAt = uuid;
    }

    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6F;
    }

    @Override
    public int getLimitPerChunk() {
        return 4;
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (!super.tryAttack(target)) {
            return false;
        } else {
            if (!this.world.isClient && target instanceof LivingEntity && this.world.random.nextInt(160) > 100) {
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffect.byRawId(19), 80 + this.world.random.nextInt(100), 0, false, false), this);
            }
            return true;
        }
    }

    static {
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 60);
    }

}
