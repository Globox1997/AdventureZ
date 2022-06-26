package net.adventurez.entity;

import org.jetbrains.annotations.Nullable;

import io.netty.buffer.Unpooled;

import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.UnmodifiableIterator;

import net.adventurez.entity.goal.DragonFindOwnerGoal;
import net.adventurez.entity.goal.DragonFlyRandomlyGoal;
import net.adventurez.entity.goal.DragonSitGoal;
import net.adventurez.entity.nonliving.FireBreathEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.ItemInit;
import net.adventurez.init.SoundInit;
import net.adventurez.mixin.accessor.LivingEntityAccessor;
import net.adventurez.network.GeneralPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class DragonEntity extends PathAwareEntity implements InventoryChangedListener, Tameable {

    public static final TrackedData<Boolean> IS_FLYING;
    public static final TrackedData<Boolean> IS_START_FLYING;
    public static final TrackedData<Boolean> CLIENT_START_FLYING;
    public static final TrackedData<Boolean> CLIENT_END_FLYING;
    public static final TrackedData<Byte> TAMEABLE_FLAGS;
    public static final TrackedData<Optional<UUID>> OWNER_UUID;
    public static final TrackedData<Boolean> HAS_SADDLE;
    public static final TrackedData<Boolean> HAS_CHEST;
    public static final TrackedData<Boolean> OTHER_EARS;
    public static final TrackedData<Boolean> OTHER_TAIL;
    public static final TrackedData<Boolean> OTHER_EYES;
    public static final TrackedData<Integer> DRAGON_SIZE;
    public static final TrackedData<Boolean> FIRE_BREATH;
    public static final TrackedData<Boolean> RED_DRAGON;

    private boolean sitting;
    public boolean isFlying;
    private int startFlyingTimer = 0;
    private float dragonSideSpeed = 0.0F;
    private float dragonForwardSpeed = 0.0F;
    public int keyBind = 342;
    private float turningFloat;
    private boolean hasSaddle;
    private int healingFood;
    private SimpleInventory inventory;
    private int onGroundTicker;
    private int dragonAge;
    private int dragonAgeFoodBonus;
    private int fireBreathCooldown;
    public boolean fireBreathActive;
    private int startFlyingTime = 0;
    private int fluidTicker = 0;

    public DragonEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0F;
        this.onChestedStatusChanged();
    }

    public static DefaultAttributeContainer.Builder createDragonAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28D)
                .add(EntityAttributes.GENERIC_ARMOR, 6.0D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new DragonSitGoal(this));
        this.goalSelector.add(2, new DragonFindOwnerGoal(this, 0.1D, 10.0F, 2.0F));
        this.goalSelector.add(3, new DragonFlyRandomlyGoal(this));
        this.goalSelector.add(4, new WanderAroundGoal(this, 0.9D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F, 0.8F));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TAMEABLE_FLAGS, (byte) 0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
        this.dataTracker.startTracking(IS_FLYING, false);
        this.dataTracker.startTracking(CLIENT_END_FLYING, false);
        this.dataTracker.startTracking(IS_START_FLYING, false);
        this.dataTracker.startTracking(CLIENT_START_FLYING, false);
        this.dataTracker.startTracking(HAS_SADDLE, false);
        this.dataTracker.startTracking(HAS_CHEST, false);
        this.dataTracker.startTracking(OTHER_EARS, false);
        this.dataTracker.startTracking(OTHER_TAIL, false);
        this.dataTracker.startTracking(OTHER_EYES, false);
        this.dataTracker.startTracking(DRAGON_SIZE, 1);
        this.dataTracker.startTracking(FIRE_BREATH, false);
        this.dataTracker.startTracking(RED_DRAGON, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        if (this.getOwnerUuid() != null) {
            tag.putUuid("DragonOwner", this.getOwnerUuid());
        }
        tag.putBoolean("IsFlying", this.isFlying);
        tag.putBoolean("SittingDragon", this.sitting);
        tag.putBoolean("HasSaddle", this.hasSaddle);
        tag.putBoolean("HasChest", this.hasChest());
        if (this.hasChest()) {
            NbtList listTag = new NbtList();
            for (int i = 0; i < this.inventory.size(); ++i) {
                ItemStack itemStack = this.inventory.getStack(i);
                if (!itemStack.isEmpty()) {
                    NbtCompound compoundTag = new NbtCompound();
                    compoundTag.putByte("Slot", (byte) i);
                    itemStack.writeNbt(compoundTag);
                    listTag.add(compoundTag);
                }
            }
            tag.put("Items", listTag);
        }
        tag.putInt("DragonSize", this.getSize());
        tag.putBoolean("OtherDragonEars", this.getDataTracker().get(DragonEntity.OTHER_EARS));
        tag.putBoolean("OtherDragonTail", this.getDataTracker().get(DragonEntity.OTHER_TAIL));
        tag.putBoolean("OtherDragonEyes", this.getDataTracker().get(DragonEntity.OTHER_EYES));
        tag.putBoolean("RedDragon", this.getDataTracker().get(DragonEntity.RED_DRAGON));
        tag.putInt("DragonAge", this.dragonAge);
        tag.putInt("StartFlyingTime", this.startFlyingTime);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        UUID uUID2;
        if (tag.containsUuid("DragonOwner")) {
            uUID2 = tag.getUuid("DragonOwner");
        } else {
            String string = tag.getString("DragonOwner");
            uUID2 = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }
        if (uUID2 != null) {
            try {
                this.setOwnerUuid(uUID2);
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        }
        this.isFlying = tag.getBoolean("IsFlying");
        this.dataTracker.set(IS_FLYING, this.isFlying);
        this.sitting = tag.getBoolean("SittingDragon");
        this.setSitting(this.sitting);
        this.hasSaddle = tag.getBoolean("HasSaddle");
        this.dataTracker.set(HAS_SADDLE, this.hasSaddle);
        this.setHasChest(tag.getBoolean("HasChest"));
        if (this.hasChest()) {
            NbtList listTag = tag.getList("Items", 10);
            this.onChestedStatusChanged();

            for (int i = 0; i < listTag.size(); ++i) {
                NbtCompound compoundTag = listTag.getCompound(i);
                int j = compoundTag.getByte("Slot") & 255;
                if (j >= 0 && j < this.inventory.size()) {
                    this.inventory.setStack(j, ItemStack.fromNbt(compoundTag));
                }
            }
        }
        this.setSize(tag.getInt("DragonSize"));
        this.dataTracker.set(OTHER_EARS, tag.getBoolean("OtherDragonEars"));
        this.dataTracker.set(OTHER_TAIL, tag.getBoolean("OtherDragonTail"));
        this.dataTracker.set(OTHER_EYES, tag.getBoolean("OtherDragonEyes"));
        this.dataTracker.set(RED_DRAGON, tag.getBoolean("RedDragon"));
        this.dragonAge = tag.getInt("DragonAge");
        if (this.isFlying)
            this.startFlyingTime = tag.getInt("StartFlyingTime");
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isAlive()) {
            if (this.hasPassengers() && this.canBeControlledByRider()) {
                LivingEntity livingEntity = (LivingEntity) this.getPrimaryPassenger();
                double wrapper = MathHelper.wrapDegrees(this.bodyYaw - (double) this.getYaw());
                this.setYaw((float) ((double) this.getYaw() + wrapper));
                this.prevYaw = this.getYaw();
                this.setPitch(livingEntity.getPitch() * 0.5F);
                this.setRotation(this.getYaw(), this.getPitch());
                this.headYaw = livingEntity.headYaw;
                boolean shouldFlyUp = false;
                boolean shouldFlyDown = false;
                shouldFlyUp = ((LivingEntityAccessor) livingEntity).jumping(); // Pressing jump button for going upwards
                if (this.world.isClient && livingEntity instanceof ClientPlayerEntity) {
                    ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) livingEntity;
                    shouldFlyDown = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), this.keyBind);
                    if (clientPlayerEntity.input.pressingLeft) {
                        turningFloat -= 0.05F;
                    }
                    if (!clientPlayerEntity.input.pressingLeft && turningFloat < 0.0F) {
                        turningFloat = 0.0F;
                    }
                    if (clientPlayerEntity.input.pressingRight) {
                        turningFloat += 0.05F;
                    }
                    if (!clientPlayerEntity.input.pressingRight && turningFloat > 0.0F) {
                        turningFloat = 0.0F;
                    }
                }

                this.setYaw(ConfigInit.CONFIG.heavy_dragon_flight ? this.getYaw() + MathHelper.wrapDegrees(turningFloat) : livingEntity.getYaw());

                float f = livingEntity.sidewaysSpeed * 0.1F;
                float g = livingEntity.forwardSpeed * 0.5F;
                float flySpeed = 0.0F;
                float maxForwardSpeed = 0.6F;
                float maxSidewaysSpeed = 0.15F;

                if ((this.dragonForwardSpeed < maxForwardSpeed && livingEntity.forwardSpeed > 0.0F) || (this.dragonForwardSpeed > maxForwardSpeed * -0.3F && livingEntity.forwardSpeed < 0.0F)) {
                    this.dragonForwardSpeed += g * 0.04F;
                }
                if ((this.dragonSideSpeed < maxSidewaysSpeed && livingEntity.sidewaysSpeed > 0.0F) || (this.dragonSideSpeed > maxSidewaysSpeed * -1 && livingEntity.sidewaysSpeed < 0.0F)) {
                    this.dragonSideSpeed += f * 0.03F;
                }
                if (livingEntity.sidewaysSpeed == 0.0F) {
                    this.dragonSideSpeed *= 0.7F;
                }
                if (livingEntity.forwardSpeed == 0.0F) {
                    this.dragonForwardSpeed *= 0.7F;
                }

                // To do, set proper sit position
                if (shouldFlyUp && !this.isFlying && this.startFlyingTimer < 10) {
                    if (this.isTouchingWater() && this.getFluidHeight(FluidTags.WATER) > 0.5D) {
                        if (!this.world.isClient && this.getFirstPassenger() instanceof ServerPlayerEntity) {
                            CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(GeneralPacket.VELOCITY_PACKET, new PacketByteBuf(Unpooled.buffer().writeInt(this.getId()).writeFloat(0.05F)));
                            ((ServerPlayerEntity) this.getFirstPassenger()).networkHandler.sendPacket(packet);
                        }
                    } else {
                        this.startFlyingTimer++;
                        this.getDataTracker().set(CLIENT_START_FLYING, true);
                        this.getDataTracker().set(IS_START_FLYING, true);
                    }
                }
                if (!shouldFlyUp && !this.isFlying && this.startFlyingTimer > 0) {
                    this.startFlyingTimer--;
                    this.getDataTracker().set(IS_START_FLYING, false);
                }
                if ((this.startFlyingTimer <= 0 && !this.isFlying) || this.isFlying || this.getDataTracker().get(IS_FLYING)) {
                    this.getDataTracker().set(CLIENT_START_FLYING, false);
                }

                if (this.startFlyingTimer >= 10 && (!this.isFlying || !this.getDataTracker().get(IS_FLYING))) {
                    this.isFlying = true;
                    this.getDataTracker().set(IS_FLYING, true);
                    this.startFlyingTimer = 0;
                    this.startFlyingTime = (int) this.world.getTime();
                }
                if (this.isFlying && this.onGround) {// && shouldFlyDown only client: bad
                    this.onGroundTicker++;
                    if (this.onGroundTicker > 3) {
                        this.onGroundTicker = 0;
                        this.isFlying = false;
                        this.getDataTracker().set(IS_FLYING, false);
                        this.getDataTracker().set(CLIENT_END_FLYING, true);
                    }

                }
                if ((this.isFlying || this.getDataTracker().get(IS_FLYING)) && shouldFlyUp) {
                    flySpeed = 0.15F;
                }
                if ((this.isFlying || this.getDataTracker().get(IS_FLYING)) && shouldFlyDown) {
                    flySpeed = -0.2F;
                }
                if ((this.isFlying || this.getDataTracker().get(IS_FLYING)) && !shouldFlyDown && !shouldFlyUp) {
                    flySpeed *= 0.4F;
                }

                if (this.isLogicalSideForUpdatingMovement()) {
                    this.setMovementSpeed((float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    if ((!this.getDataTracker().get(IS_FLYING) && !this.isFlying) || (this.isTouchingWater() && this.getFluidHeight(FluidTags.WATER) > 0.2D)) {
                        super.travel(new Vec3d((double) f, movementInput.y, (double) g));
                    } else {
                        Vec3d vec3d;
                        if (ConfigInit.CONFIG.heavy_dragon_flight)
                            vec3d = new Vec3d((double) this.dragonSideSpeed, movementInput.y + flySpeed, (double) this.dragonForwardSpeed);
                        else {
                            vec3d = new Vec3d(livingEntity.sidewaysSpeed * 0.7D, movementInput.y + flySpeed, livingEntity.forwardSpeed * 0.7D);
                        }
                        this.updateVelocity(0.05F, vec3d);
                        this.move(MovementType.SELF, this.getVelocity());
                        this.setVelocity(this.getVelocity());
                    }
                } else if (livingEntity instanceof PlayerEntity) {
                    this.setVelocity(Vec3d.ZERO);
                }
                this.updateLimbs(this, false);
            } else {
                if (this.isFlying || this.getDataTracker().get(IS_FLYING)) {
                    this.updateVelocity(0.02F, movementInput);
                    this.move(MovementType.SELF, this.getVelocity());
                    this.setVelocity(this.getVelocity().multiply((0.91F)));
                    double wrapper = MathHelper.wrapDegrees(this.headYaw - (double) this.getYaw());
                    this.setYaw((float) ((double) this.getYaw() + wrapper));
                    BlockPos blockPos = this.getBlockPos().down(2);
                    if (this.world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
                        this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
                    }
                    if (this.onGround) {
                        this.getDataTracker().set(CLIENT_END_FLYING, true);
                        this.isFlying = false;
                        this.getDataTracker().set(IS_FLYING, false);
                    }
                } else {
                    this.airStrafingSpeed = 0.02F;
                    super.travel(movementInput);
                }
            }
        }
        if (this.isFlying && this.startFlyingTime != 0 && (int) (this.world.getTime() - this.startFlyingTime) % 25 == 0)
            this.playWingFlapSound();

    }

    private boolean canBeControlledByRider() {
        return this.getPrimaryPassenger() instanceof LivingEntity;
    }

    @Override
    public boolean isClimbing() {
        return false;
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    @Nullable
    public Entity getPrimaryPassenger() {
        return this.getPassengerList().isEmpty() ? null : (Entity) this.getPassengerList().get(0);
    }

    @Nullable
    private Vec3d method_27930(Vec3d vec3d, LivingEntity livingEntity) {
        double d = this.getX() + vec3d.x;
        double e = this.getBoundingBox().minY;
        double f = this.getZ() + vec3d.z;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        UnmodifiableIterator<EntityPose> var10 = livingEntity.getPoses().iterator();

        while (var10.hasNext()) {
            EntityPose entityPose = (EntityPose) var10.next();
            mutable.set(d, e, f);
            double g = this.getBoundingBox().maxY + 0.75D;

            while (true) {
                double h = this.world.getDismountHeight(mutable);
                if ((double) mutable.getY() + h > g) {
                    break;
                }

                if (Dismounting.canDismountInBlock(h)) {
                    Box box = livingEntity.getBoundingBox(entityPose);
                    Vec3d vec3d2 = new Vec3d(d, (double) mutable.getY() + h, f);
                    if (Dismounting.canPlaceEntityAt(this.world, livingEntity, box.offset(vec3d2))) {
                        livingEntity.setPose(entityPose);
                        return vec3d2;
                    }
                }

                mutable.move(Direction.UP);
                if ((double) mutable.getY() >= g) {
                    break;
                }
            }
        }

        return null;
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d vec3d = getPassengerDismountOffset((double) this.getWidth(), (double) passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.RIGHT ? 90.0F : -90.0F));
        Vec3d vec3d2 = this.method_27930(vec3d, passenger);
        if (vec3d2 != null) {
            return vec3d2;
        } else {
            Vec3d vec3d3 = getPassengerDismountOffset((double) this.getWidth(), (double) passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.LEFT ? 90.0F : -90.0F));
            Vec3d vec3d4 = this.method_27930(vec3d3, passenger);
            return vec3d4 != null ? vec3d4 : this.getPos();
        }
    }

    private void putPlayerOnBack(PlayerEntity player) {
        if (!this.world.isClient) {
            player.setYaw(this.getYaw());
            player.setPitch(this.getPitch());
            player.startRiding(this);
        }
    }

    public void dragonFireBreath() {
        if (this.fireBreathCooldown <= 60) {
            this.fireBreathCooldown++;
            if (!this.world.isClient) {
                if (this.fireBreathCooldown == 1) {
                    this.world.playSoundFromEntity((PlayerEntity) null, this, SoundInit.DRAGON_BREATH_EVENT, SoundCategory.HOSTILE, 1.0F, 1.0F);
                }
                if (this.fireBreathCooldown % 3 == 0) {
                    Vec3d vec3d = this.getRotationVector(prevPitch, headYaw);
                    Vec3d otherVec3d = this.getRotationVector(prevPitch, bodyYaw);
                    vec3d = vec3d.add(otherVec3d);
                    FireBreathEntity fireBreathEntity = new FireBreathEntity(world, this, vec3d.x, vec3d.y, vec3d.z);
                    fireBreathEntity.refreshPositionAndAngles(this.getX() + vec3d.x * 3D,
                            this.getY() + this.getBoundingBox().getYLength() * 0.65D + (this.getPitch() > 0F ? -this.getPitch() / 40F : -this.getPitch() / 80F), this.getZ() + vec3d.z * 3D,
                            this.getYaw(), this.getPitch());

                    world.spawnEntity(fireBreathEntity);
                }
            } else {
                if (!this.getDataTracker().get(FIRE_BREATH)) {
                    this.getDataTracker().set(FIRE_BREATH, true);
                }
            }
        } else {
            this.fireBreathActive = false;
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (!this.isBaby() && this.hasPassengers())
            return super.interactMob(player, hand);

        if (this.world.isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || this.dragonFood(item) && !this.isTamed();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            // Check for owner
            if (this.isTamed() && this.getSize() > 1 && this.isOwner(player)) {
                if (item == Items.CHEST && !this.hasChest() && this.getSize() > 2) {
                    this.world.playSoundFromEntity((PlayerEntity) null, this, SoundInit.EQUIP_CHEST_EVENT, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                    this.setHasChest(true);
                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                    }
                    return ActionResult.SUCCESS;
                }
                if (this.dragonFood(item) && this.getHealth() < this.getMaxHealth() && player.isSneaking()) {
                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                    }
                    this.heal((float) this.healingFood);
                    return ActionResult.SUCCESS;
                } else if (!player.isSneaking()) {
                    if (!this.hasSaddle) {
                        if (item == ItemInit.DRAGON_SADDLE) {
                            this.world.playSoundFromEntity((PlayerEntity) null, this, SoundEvents.ENTITY_HORSE_SADDLE, SoundCategory.NEUTRAL, 0.8F, 1.0F);
                            if (!player.isCreative()) {
                                itemStack.decrement(1);
                            }
                            this.hasSaddle = true;
                            this.getDataTracker().set(HAS_SADDLE, true);
                            return ActionResult.SUCCESS;
                        }
                        return ActionResult.FAIL;
                    }
                    this.setSitting(false);
                    this.putPlayerOnBack(player);
                    return ActionResult.SUCCESS;
                } else if (this.isInSittingPose()) {
                    this.setSitting(false);
                    return ActionResult.SUCCESS;
                } else if (this.onGround) {
                    this.setSitting(true);
                    return ActionResult.SUCCESS;
                } else
                    return ActionResult.PASS;
            } else if (this.dragonFood(item)) {
                if (!this.isTamed()) {
                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                    }
                    int tamer;
                    if (item == ItemInit.ORC_SKIN) {
                        tamer = 1;
                    } else {
                        tamer = healingFood;
                    }
                    if (this.random.nextInt(tamer) == 0) {
                        this.setOwner(player);
                        this.navigation.stop();
                        this.setTarget((LivingEntity) null);
                        this.world.sendEntityStatus(this, (byte) 7);
                    } else {
                        this.world.sendEntityStatus(this, (byte) 6);
                    }

                    return ActionResult.SUCCESS;
                } else {
                    if (this.canEatFood(this.dragonAge, 3, 13)) {
                        if (!player.isCreative()) {
                            itemStack.decrement(1);
                        }
                        dragonAgeFoodBonus++;
                        return ActionResult.SUCCESS;
                    } else {
                        return ActionResult.PASS;
                    }

                }
            }

            return super.interactMob(player, hand);
        }
    }

    private boolean dragonFood(Item item) {
        if (item == ItemInit.ORC_SKIN) {
            healingFood = 6;
            return true;
        } else if (item == Items.PORKCHOP || item == Items.BEEF || item == ItemInit.MAMMOTH_MEAT || item == ItemInit.RAW_VENISON || item == ItemInit.RHINO_MEAT || item == ItemInit.WARTHOG_MEAT
                || item == ItemInit.ENDER_WHALE_MEAT) {
            healingFood = 5;
            return true;
        } else if (item == Items.MUTTON || item == Items.CHICKEN || item == ItemInit.IGUANA_MEAT) {
            healingFood = 4;
            return true;
        } else if (item == Items.RABBIT) {
            healingFood = 3;
            return true;
        }
        return false;
    }

    private boolean canEatFood(int age, int minAge, int maxAge) {
        if (age < minAge || (age < maxAge && age > maxAge - (minAge * 2))) {
            return true;
        } else
            return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isClient) {
            if (this.getSize() < 3) {
                if (this.age % 1200 == 0) {
                    this.dragonAge++;
                }
                if (this.dragonAge == 5 && this.getSize() == 1) {
                    this.setSize(2);
                    this.healDragonIfGrowing();
                }
                if (this.dragonAge == 15 && this.getSize() == 2) {
                    this.setSize(3);
                    this.healDragonIfGrowing();
                }
                if (this.dragonAgeFoodBonus > 5) {
                    this.dragonAgeFoodBonus = 0;
                    this.dragonAge++;
                    this.world.sendEntityStatus(this, (byte) 9);
                }
            }
            if (this.isTouchingWater() || this.isInLava()) {
                if (this.isFlying && this.getFluidHeight(FluidTags.WATER) > 1.2D) {
                    this.fluidTicker++;
                    if (this.fluidTicker >= 30) {
                        this.isFlying = false;
                        this.getDataTracker().set(IS_FLYING, false);
                        this.getDataTracker().set(IS_START_FLYING, false);
                        this.getDataTracker().set(CLIENT_START_FLYING, false);
                        this.getDataTracker().set(CLIENT_END_FLYING, false);
                        this.startFlyingTimer = 0;
                        this.fluidTicker = 0;
                    }
                } else if (this.world.isClient && this.hasPassengers() && !this.isFlying && this.getFluidHeight(FluidTags.WATER) > 1.0D) {
                    this.addVelocity(0.0D, 0.05D, 0.0D);
                }
            } else if (this.fluidTicker != 0)
                this.fluidTicker = 0;
        }

        if (this.fireBreathCooldown > 60) {
            this.fireBreathCooldown++;
            if (this.getDataTracker().get(FIRE_BREATH)) {
                this.getDataTracker().set(FIRE_BREATH, false);
            }
            if (this.fireBreathCooldown >= 120) {
                this.fireBreathCooldown = 0;
            }
        }
        if (this.fireBreathActive) {
            this.dragonFireBreath();
        }
    }

    private void healDragonIfGrowing() {
        if (this.getHealth() < this.getMaxHealth()) {
            this.setHealth(this.getHealth() + 20F);
        }
    }

    @Override
    public boolean isPushable() {
        return !this.hasPassengers();
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.isLeashed();
    }

    @Environment(EnvType.CLIENT)
    protected void showEmoteParticle(boolean positive) {
        ParticleEffect particleEffect = ParticleTypes.HEART;
        if (!positive) {
            particleEffect = ParticleTypes.SMOKE;
        }

        for (int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02D;
            double e = this.random.nextGaussian() * 0.02D;
            double f = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(particleEffect, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), d, e, f);
        }

    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 7 || status == 9) {
            this.showEmoteParticle(true);
        } else if (status == 6) {
            this.showEmoteParticle(false);
        } else {
            super.handleStatus(status);
        }

    }

    public boolean isTamed() {
        return ((Byte) this.dataTracker.get(TAMEABLE_FLAGS) & 4) != 0;
    }

    public void setTamed(boolean tamed) {
        byte b = (Byte) this.dataTracker.get(TAMEABLE_FLAGS);
        if (tamed) {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b | 4));
        } else {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b & -5));
        }
    }

    @Nullable
    @Override
    public UUID getOwnerUuid() {
        return (UUID) ((Optional<UUID>) this.dataTracker.get(OWNER_UUID)).orElse((UUID) (Object) null);
    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public void setOwner(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerUuid(player.getUuid());
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        try {
            UUID uUID = this.getOwnerUuid();
            return uUID == null ? null : this.world.getPlayerByUuid(uUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        return this.isOwner(target) ? false : super.canTarget(target);
    }

    public boolean isOwner(LivingEntity entity) {
        return entity == this.getOwner();
    }

    @Override
    public boolean isTeammate(Entity other) {
        if (this.isTamed()) {
            LivingEntity livingEntity = this.getOwner();
            if (other == livingEntity) {
                return true;
            }

            if (livingEntity != null) {
                return livingEntity.isTeammate(other);
            }
        }

        return super.isTeammate(other);
    }

    @Override
    public void onDeath(DamageSource source) {
        if (!this.world.isClient) {
            if (this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayerEntity) {
                this.getOwner().sendMessage(this.getDamageTracker().getDeathMessage());
            }
            if (FabricLoader.getInstance().isModLoaded("dragonloot")) {
                this.dropStack(new ItemStack(net.dragonloot.init.ItemInit.DRAGON_SCALE_ITEM, this.getSize()));
            }
        }

        super.onDeath(source);
    }

    public boolean isInSittingPose() {
        return ((Byte) this.dataTracker.get(TAMEABLE_FLAGS) & 1) != 0;
    }

    private void setInSittingPose(boolean inSittingPose) {
        this.sitting = inSittingPose;
        byte b = (Byte) this.dataTracker.get(TAMEABLE_FLAGS);
        if (inSittingPose) {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b | 1));
        } else {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b & 0xFFFFFFFE));
        }

    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
        setInSittingPose(sitting);
    }

    @Override
    public int getMaxLookPitchChange() {
        if (this.isInSittingPose()) {
            return 20;
        }
        return super.getMaxLookPitchChange();
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
        if (!this.world.isClient && !this.isFlying && !onGround && heightDifference < -0.7D) {
            if (this.sitting)
                this.setSitting(false);
            if (!this.hasPassengers()) {
                this.isFlying = true;
                this.getDataTracker().set(IS_FLYING, true);
                this.getDataTracker().set(IS_START_FLYING, false);
                this.getDataTracker().set(CLIENT_START_FLYING, false);
                this.startFlyingTime = (int) this.world.getTime();
            }
        }
        super.fall(heightDifference, onGround, landedState, landedPosition);
    }

    private void playWingFlapSound() {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_ENDER_DRAGON_FLAP, this.getSoundCategory(), 5.0f, 0.8f + this.random.nextFloat() * 0.3f);
    }

    public void setKeyBind(String key) {
        this.keyBind = InputUtil.fromTranslationKey(key).getCode();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.DRAGON_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.DRAGON_HIT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.DRAGON_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundInit.DRAGON_STEP_EVENT, 0.7F, 1.0F);
    }

    @Override
    protected float calculateNextStepSoundDistance() {
        return (float) ((int) this.distanceTraveled + 2) - 0.01F;
    }

    @Override
    public boolean canImmediatelyDespawn(double num) {
        return false;
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
    }

    private void onChestedStatusChanged() {
        SimpleInventory simpleInventory = this.inventory;
        this.inventory = new SimpleInventory(27);
        if (simpleInventory != null) {
            simpleInventory.removeListener(this);
            int i = Math.min(simpleInventory.size(), this.inventory.size());

            for (int j = 0; j < i; ++j) {
                ItemStack itemStack = simpleInventory.getStack(j);
                if (!itemStack.isEmpty()) {
                    this.inventory.setStack(j, itemStack.copy());
                }
            }
        }

        this.inventory.addListener(this);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (this.inventory != null) {
            for (int i = 0; i < this.inventory.size(); ++i) {
                ItemStack itemStack = this.inventory.getStack(i);
                if (!itemStack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemStack)) {
                    this.dropStack(itemStack);
                }
            }

        }
        if (this.hasChest()) {
            if (!this.world.isClient) {
                this.dropItem(Blocks.CHEST);
            }

            this.setHasChest(false);
        }
    }

    public boolean hasChest() {
        return (Boolean) this.dataTracker.get(HAS_CHEST);
    }

    public void setHasChest(boolean hasChest) {
        this.dataTracker.set(HAS_CHEST, hasChest);
    }

    public void openInventory(PlayerEntity player) {
        if (!this.world.isClient && (!this.hasPassengers() || this.hasPassenger(player)) && this.isTamed()) {
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                    (syncId, inv, p) -> new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X3, syncId, p.getInventory(), this.inventory, 27 / 9), this.getName()));
        }

    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        this.getDataTracker().set(OTHER_EARS, world.getRandom().nextBoolean());
        this.getDataTracker().set(OTHER_TAIL, world.getRandom().nextBoolean());
        this.getDataTracker().set(OTHER_EYES, world.getRandom().nextBoolean());
        if (world.getDimension().ultrawarm())
            this.getDataTracker().set(RED_DRAGON, world.getRandom().nextBoolean());
        if (spawnReason.equals(SpawnReason.COMMAND)) {
            this.setSize(3);
        } else {
            this.setSize(1);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return super.getDimensions(pose).scaled((float) this.getSize() / 3.0F);
    }

    public int getSize() {
        return (Integer) this.dataTracker.get(DRAGON_SIZE);
    }

    public void setSize(int size) {
        this.dataTracker.set(DRAGON_SIZE, size);
        this.refreshPosition();
        this.calculateDimensions();
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((double) (size * 20));
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((double) size * 3);
        this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue((double) size * 2);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (DRAGON_SIZE.equals(data)) {
            this.calculateDimensions();
            this.setYaw(this.headYaw);
            this.setBodyYaw(this.headYaw);
        }
        super.onTrackedDataSet(data);
    }

    @Override
    public void calculateDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.calculateDimensions();
        this.updatePosition(d, e, f);
    }

    @Override
    protected float getSoundVolume() {
        return 0.3F * (float) this.getSize();
    }

    @Override
    public float getSoundPitch() {
        return 1.6F - ((float) 0.2F * this.getSize());
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        if (this.getSize() == 1) {
            return 0.80F * dimensions.height;
        }
        return 0.85F * dimensions.height;
    }

    @Override
    public double getMountedHeightOffset() {
        double flySubtraction = 0.92D;
        if (this.isFlying) {

            flySubtraction = 0.9D;
        }
        return (double) this.getSize() * 0.794 * flySubtraction;

    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        super.updatePassengerPosition(passenger);
        if (passenger instanceof MobEntity) {
            MobEntity mobEntity = (MobEntity) passenger;
            this.bodyYaw = mobEntity.bodyYaw;

            // float slowlyIncreasingFloat = ((float) Math.floorMod(this.world.getTime(), 100L) + animationProgress) / 100.0F;
            // float mediumSpeedSin = MathHelper.cos(12.566370614F * slowlyIncreasingFloat );
            // float bodyFloating = -mediumSpeedSin - 4.0F;
            // this.body.pivotY = bodyFloating;
            if (this.world.isClient) {
                float offSet = 12F;
                if (passenger.equals(this.getPrimaryPassenger()))
                    offSet = 1F;
                float f = MathHelper.sin(this.bodyYaw * 0.017453292F) * offSet;
                float g = MathHelper.cos(this.bodyYaw * 0.017453292F) * offSet;

                passenger.setPosition(this.getX() + (double) (0.1F * f), this.getBodyY(0.66F) + passenger.getHeightOffset(), this.getZ() - (double) (0.1F * g));
            }
        }

    }

    @Override
    public double getHeightOffset() {
        return 0.0D;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source == DamageSource.IN_WALL) {
            return false;
        }
        if (!this.world.isClient) {
            this.setSitting(false);
        }
        return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
    }

    static {
        IS_FLYING = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        IS_START_FLYING = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        CLIENT_START_FLYING = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        CLIENT_END_FLYING = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        TAMEABLE_FLAGS = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.BYTE);
        OWNER_UUID = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
        HAS_SADDLE = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        HAS_CHEST = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        OTHER_EARS = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        OTHER_TAIL = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        OTHER_EYES = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        DRAGON_SIZE = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.INTEGER);
        FIRE_BREATH = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        RED_DRAGON = DataTracker.registerData(DragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

}