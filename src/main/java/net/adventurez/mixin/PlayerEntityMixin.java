package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.ItemInit;
import net.adventurez.item.armor.StoneGolemArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(EntityType<PlayerEntity> type, World world) {
        super(type, world);
    };

    @Inject(method = "attack", at = @At(value = "HEAD"))
    private void attackMixin(Entity target, CallbackInfo info) {

    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;dropShoulderEntities()V", shift = Shift.AFTER), cancellable = true)
    private void damageMixin(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (this.getEquippedStack(EquipmentSlot.CHEST).getItem() == ItemInit.STONE_GOLEM_CHESTPLATE) {
            if (StoneGolemArmor.fullGolemArmor((PlayerEntity) (Object) this) && this.world.random.nextFloat() <= 0.3F) {
                info.setReturnValue(false);
            }
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean doesRenderOnFire() {
        ItemStack golemChestplate = this.getEquippedStack(EquipmentSlot.CHEST);
        boolean fireActivated = this.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(ItemInit.STONE_GOLEM_CHESTPLATE) && StoneGolemArmor.fireTime(golemChestplate);
        return this.isOnFire() && !this.isSpectator() && !fireActivated;
    }

}