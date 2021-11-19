package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {

    @Inject(method = "registerDefaults", at = @At("TAIL"))
    private static void registerDefaultsMixin(CallbackInfo info) {
        registerPotionRecipe(Potions.AWKWARD, ItemInit.ORC_SKIN, Potions.TURTLE_MASTER);
        registerPotionRecipe(Potions.AWKWARD, ItemInit.ENDER_WHALE_SKIN, Potions.SLOW_FALLING);
    }

    @Shadow
    private static void registerPotionRecipe(Potion input, Item item, Potion output) {
    }
}
