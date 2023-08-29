package net.adventurez.mixin.compat;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {

    private final boolean isOverEnchantedLoaded = FabricLoader.getInstance().isModLoaded("overenchanted");

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    private void appendTooltipMixin(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo info) {
        if (isOverEnchantedLoaded && stack.hasNbt() && stack.getNbt().contains("void_drop") && stack.getNbt().getBoolean("void_drop")) {
            tooltip.add(Text.translatable("item.adventurez.enchanted_book.tooltip"));
            tooltip.add(Text.translatable("item.adventurez.enchanted_book.tooltip2"));
        }
    }
}
