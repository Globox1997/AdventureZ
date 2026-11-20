package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.ItemInit;
import net.adventurez.item.GildedNetheriteArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(EntityType<Player> type, Level world) {
        super(type, world);
    }

    @Inject(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;scalesWithDifficulty()Z"), cancellable = true)
    private void damageMixin(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (this.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.GILDED_NETHERITE_CHESTPLATE && GildedNetheriteArmor.fullGolemArmor((Player) (Object) this)) {
            if (source.is(DamageTypeTags.IS_FIRE) && !GildedNetheriteArmor.isStoneGolemArmorActive(this.getItemBySlot(EquipmentSlot.CHEST)) && GildedNetheriteArmor.activateStoneGolemArmor((Player) (Object) this, this.getItemBySlot(EquipmentSlot.CHEST))) {
                GildedNetheriteArmor.activateStoneGolemArmor((Player) (Object) this, this.getItemBySlot(EquipmentSlot.CHEST));
                info.setReturnValue(false);
            } else if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && this.level().getRandom().nextFloat() <= ConfigInit.CONFIG.gilded_netherite_armor_dodge_chance) {
                info.setReturnValue(false);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean displayFireAnimation() {
        ItemStack golemChestplate = this.getItemBySlot(EquipmentSlot.CHEST);
        boolean fireActivated = golemChestplate.getItem().equals(ItemInit.GILDED_NETHERITE_CHESTPLATE) && GildedNetheriteArmor.isStoneGolemArmorActive(golemChestplate);
        return this.isOnFire() && !this.isSpectator() && !fireActivated;
    }

}