package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.init.TagInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;

@Mixin(PiglinAi.class)
public class PiglinBrainMixin {

    @Inject(method = "isWearingSafeArmor", at = @At(value = "HEAD"), cancellable = true)
    private static void wearsGoldArmorMixin(LivingEntity entity, CallbackInfoReturnable<Boolean> info) {
        for (EquipmentSlot slot : EquipmentSlotGroup.ARMOR) {
            if (entity.getItemBySlot(slot).is(TagInit.PIGLIN_NOT_ATTACK_ITEMS)) {
                info.setReturnValue(true);
                return;
            }
        }
    }

}
