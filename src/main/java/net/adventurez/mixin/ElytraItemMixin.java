package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.init.ItemInit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Mixin(ItemStack.class)
public class ElytraItemMixin {

    @Inject(method = "isValidRepairItem", at = @At("HEAD"), cancellable = true)
    private void canRepairMixin(ItemStack ingredient, CallbackInfoReturnable<Boolean> info) {
        if (((ItemStack) (Object) this).is(Items.ELYTRA) && ingredient.is(ItemInit.ENDER_WHALE_SKIN))
            info.setReturnValue(true);
    }
}
