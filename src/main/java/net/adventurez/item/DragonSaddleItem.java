package net.adventurez.item;

import java.util.List;

import net.adventurez.init.ConfigInit;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class DragonSaddleItem extends Item {

    public DragonSaddleItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        if (ConfigInit.CONFIG.display_rareness) {
            tooltip.add(new TranslatableText("item.adventurez.uncommon_item.tooltip"));
        }
    }
}
