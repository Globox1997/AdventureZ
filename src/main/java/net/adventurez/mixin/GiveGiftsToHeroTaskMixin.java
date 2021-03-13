package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.EffectInit;
import net.minecraft.entity.ai.brain.task.GiveGiftsToHeroTask;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(GiveGiftsToHeroTask.class)
public class GiveGiftsToHeroTaskMixin {

    @Inject(method = "isHero", at = @At(value = "HEAD"), cancellable = true)
    private void isHeroMixin(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        if (player.hasStatusEffect(EffectInit.FAME)) {
            info.setReturnValue(true);
        }
    }

}
