package net.adventurez.mixin.compat;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.adventurez.init.ItemInit;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

@Mixin(Item.class)
public abstract class EnchantedBookItemMixin {

    @Unique
    private static final boolean isOverEnchantedLoaded = FabricLoader.getInstance().isModLoaded("overenchanted");

    @Inject(method = "appendHoverText", at = @At("TAIL"))
    private void appendHoverTextMixin(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag type, CallbackInfo info) {
        if (isOverEnchantedLoaded && stack.is(Items.ENCHANTED_BOOK) && Boolean.TRUE.equals(stack.get(ItemInit.VOID_DROP))) {
            tooltip.accept(Component.translatable("item.adventurez.enchanted_book.tooltip"));
            tooltip.accept(Component.translatable("item.adventurez.enchanted_book.tooltip2"));
        }
    }
}
