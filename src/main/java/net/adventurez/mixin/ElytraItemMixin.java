package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.init.ItemInit;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

@Mixin(ElytraItem.class)
public class ElytraItemMixin {

    @Inject(method = "isValidRepairItem", at = @At("HEAD"), cancellable = true)
    private void canRepairMixin(ItemStack stack, ItemStack ingredient, CallbackInfoReturnable<Boolean> info) {
        if (ingredient.is(ItemInit.ENDER_WHALE_SKIN))
            info.setReturnValue(true);
    }
}
