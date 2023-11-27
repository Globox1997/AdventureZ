package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.ItemInit;
import net.adventurez.item.armor.GildedNetheriteArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(EntityType<PlayerEntity> type, World world) {
        super(type, world);
    };

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;dropShoulderEntities()V", shift = Shift.AFTER), cancellable = true)
    private void damageMixin(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (this.getEquippedStack(EquipmentSlot.CHEST).getItem() == ItemInit.GILDED_NETHERITE_CHESTPLATE && GildedNetheriteArmor.fullGolemArmor((PlayerEntity) (Object) this)) {
            if (this.getWorld().getRandom().nextFloat() <= ConfigInit.CONFIG.gilded_netherite_armor_dodge_chance) {
                info.setReturnValue(false);
            } else if (source.isIn(DamageTypeTags.IS_FIRE) && !GildedNetheriteArmor.isStoneGolemArmorActive(this.getEquippedStack(EquipmentSlot.CHEST))) {
                GildedNetheriteArmor.activateStoneGolemArmor((PlayerEntity) (Object) this, this.getEquippedStack(EquipmentSlot.CHEST));
                info.setReturnValue(false);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean doesRenderOnFire() {
        ItemStack golemChestplate = this.getEquippedStack(EquipmentSlot.CHEST);
        boolean fireActivated = this.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(ItemInit.GILDED_NETHERITE_CHESTPLATE) && GildedNetheriteArmor.isStoneGolemArmorActive(golemChestplate);
        return this.isOnFire() && !this.isSpectator() && !fireActivated;
    }

}