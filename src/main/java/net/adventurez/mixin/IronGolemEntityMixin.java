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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(IronGolem.class)
public abstract class IronGolemEntityMixin extends AbstractGolem implements EntityAccess {

    private static final EntityDataAccessor<Boolean> BLACKSTONED = SynchedEntityData.defineId(IronGolem.class, EntityDataSerializers.BOOLEAN);

    public IronGolemEntityMixin(EntityType<? extends AbstractGolem> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "defineSynchedData", at = @At(value = "TAIL"))
    protected void initDataTrackerMixin(SynchedEntityData.Builder builder, CallbackInfo info) {
        builder.define(BLACKSTONED, false);
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
    private void writeCustomDataToNbtMixin(CompoundTag nbt, CallbackInfo info) {
        nbt.putBoolean("Blackstoned", this.entityData.get(BLACKSTONED));
    }

    @Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
    private void readCustomDataFromNbtMixin(CompoundTag nbt, CallbackInfo info) {
        this.entityData.set(BLACKSTONED, nbt.getBoolean("Blackstoned"));
        if (nbt.getBoolean("Blackstoned")) {
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Creeper.class, false, true));
        }
    }

    @Inject(method = "mobInteract", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    protected void interactMobMixin(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> info, ItemStack itemStack) {
        if (this.entityData.get(BLACKSTONED)) {
            if (itemStack.getItem() instanceof BlockItem blockItem) {
                if (blockItem.getBlock().defaultBlockState().is(TagInit.PLATFORM_NETHER_BLOCKS)) {
                    this.heal(10.0F);
                    itemStack.consume(1, player);
                    info.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide()));
                }
            } else if (itemStack.is(ItemInit.GILDED_BLACKSTONE_SHARD)) {
                this.heal(this.getMaxHealth() * 0.2F);
                itemStack.consume(1, player);
                info.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide()));
            } else if (itemStack.is(ItemInit.BLACKSTONE_GOLEM_HEART)) {
                this.heal(this.getMaxHealth());
                itemStack.consume(1, player);
                info.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide()));
            }
        } else if (itemStack.is(ItemInit.BLACKSTONE_GOLEM_HEART)) {
            if (!this.level().isClientSide()) {
                this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Creeper.class, false, true));
                this.entityData.set(BLACKSTONED, true);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getAttributeBaseValue(Attributes.MAX_HEALTH) + 100.0D);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) + 5.0D);
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) + 0.04D);
                this.setHealth(this.getMaxHealth());
                itemStack.consume(1, player);
            }
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundInit.BLACKSTONE_GOLEM_AWAKENS_EVENT, SoundSource.NEUTRAL, 1.4F, 1.0F);
            info.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide()));
        }
    }

    @Inject(method = "canAttackType", at = @At(value = "HEAD"), cancellable = true)
    private void canTargetMixin(EntityType<?> type, CallbackInfoReturnable<Boolean> info) {
        if (type == EntityType.CREEPER && this.entityData.get(BLACKSTONED)) {
            info.setReturnValue(true);
        }
    }

    @Override
    public EntityDataAccessor<Boolean> getTrackedDataBoolean() {
        return IronGolemEntityMixin.BLACKSTONED;
    }

}
