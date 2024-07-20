package net.adventurez.mixin.compat;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.init.ItemInit;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookItemMixin extends Item {

    public EnchantedBookItemMixin(Settings settings) {
        super(settings);
    }

    private final boolean isOverEnchantedLoaded = FabricLoader.getInstance().isModLoaded("overenchanted");

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (isOverEnchantedLoaded && stack.get(ItemInit.VOID_DROP) != null && stack.get(ItemInit.VOID_DROP)) {
            tooltip.add(Text.translatable("item.adventurez.enchanted_book.tooltip"));
            tooltip.add(Text.translatable("item.adventurez.enchanted_book.tooltip2"));
        }
    }

}
