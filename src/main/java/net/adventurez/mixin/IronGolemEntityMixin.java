package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.adventurez.access.EntityAccess;
import net.adventurez.init.ItemInit;
import net.adventurez.init.SoundInit;
import net.adventurez.init.TagInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntity implements EntityAccess {

    private static final TrackedData<Boolean> BLACKSTONED = DataTracker.registerData(IronGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initDataTracker", at = @At(value = "TAIL"))
    protected void initDataTrackerMixin(CallbackInfo info) {
        this.dataTracker.startTracking(BLACKSTONED, false);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At(value = "TAIL"))
    private void writeCustomDataToNbtMixin(NbtCompound nbt, CallbackInfo info) {
        nbt.putBoolean("Blackstoned", this.dataTracker.get(BLACKSTONED));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At(value = "TAIL"))
    private void readCustomDataFromNbtMixin(NbtCompound nbt, CallbackInfo info) {
        this.dataTracker.set(BLACKSTONED, nbt.getBoolean("Blackstoned"));
        if (nbt.getBoolean("Blackstoned"))
            this.targetSelector.add(3, new FollowTargetGoal<>(this, CreeperEntity.class, false, true));
    }

    @Inject(method = "interactMob", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    protected void interactMobMixin(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info, ItemStack itemStack) {
        if (this.dataTracker.get(BLACKSTONED)) {
            if (itemStack.getItem() instanceof BlockItem) {
                if (((BlockItem) itemStack.getItem()).getBlock().getDefaultState().isIn(TagInit.PLATFORM_NETHER_BLOCKS)) {
                    this.heal(10.0F);
                    info.setReturnValue(ActionResult.success(this.world.isClient));
                }
            } else if (itemStack.isOf(ItemInit.GILDED_STONE)) {
                this.heal(this.getMaxHealth() * 0.2F);
                info.setReturnValue(ActionResult.success(this.world.isClient));
            } else if (itemStack.isOf(ItemInit.STONE_GOLEM_HEART)) {
                this.heal(this.getMaxHealth());
                info.setReturnValue(ActionResult.success(this.world.isClient));
            }
        } else if (itemStack.isOf(ItemInit.STONE_GOLEM_HEART)) {
            if (!this.world.isClient) {
                this.targetSelector.add(3, new FollowTargetGoal<>(this, CreeperEntity.class, false, true));
                this.dataTracker.set(BLACKSTONED, true);
                this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(this.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH) + 100.0D);
                this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(this.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) + 5.0D);
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(this.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) + 0.04D);
                this.setHealth(this.getMaxHealth());
                itemStack.decrement(1);
            }
            this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundInit.GOLEM_AWAKENS_EVENT, SoundCategory.NEUTRAL, 1.4F, 1.0F);
            info.setReturnValue(ActionResult.success(this.world.isClient));
        }
    }

    @Inject(method = "canTarget", at = @At(value = "HEAD"), cancellable = true)
    private void canTargetMixin(EntityType<?> type, CallbackInfoReturnable<Boolean> info) {
        if (type == EntityType.CREEPER && this.dataTracker.get(BLACKSTONED) == true)
            info.setReturnValue(true);
    }

    @Override
    public TrackedData<Boolean> getTrackedDataBoolean() {
        return IronGolemEntityMixin.BLACKSTONED;
    }

}
