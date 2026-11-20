package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.EffectInit;
import net.minecraft.world.entity.ai.behavior.GiveGiftToHero;
import net.minecraft.world.entity.player.Player;

@Mixin(GiveGiftToHero.class)
public class GiveGiftsToHeroTaskMixin {

    @Inject(method = "isHero", at = @At(value = "HEAD"), cancellable = true)
    private void isHeroMixin(Player player, CallbackInfoReturnable<Boolean> info) {
        if (player.hasEffect(EffectInit.FAME)) {
            info.setReturnValue(true);
        }
    }

}
