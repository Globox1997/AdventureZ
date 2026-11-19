package net.adventurez.mixin.compat;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.init.ItemInit;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookItemMixin extends Item {

    public EnchantedBookItemMixin(Properties settings) {
        super(settings);
    }

    private final boolean isOverEnchantedLoaded = FabricLoader.getInstance().isModLoaded("overenchanted");

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (isOverEnchantedLoaded && stack.get(ItemInit.VOID_DROP) != null && stack.get(ItemInit.VOID_DROP)) {
            tooltip.add(Component.translatable("item.adventurez.enchanted_book.tooltip"));
            tooltip.add(Component.translatable("item.adventurez.enchanted_book.tooltip2"));
        }
    }

}
