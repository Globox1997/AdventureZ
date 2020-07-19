package net.adventurez.item;

import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class GildedNetheriteFragment extends Item {

  public GildedNetheriteFragment(Settings settings) {
    super(settings);
  }

  @Override
  public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    tooltip.add(new TranslatableText("item.adventurez.gilded_netherite_fragment.tooltip"));
    tooltip.add(new TranslatableText("item.adventurez.moreinfo.tooltip"));
    if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)) {
      tooltip.remove(new TranslatableText("item.adventurez.moreinfo.tooltip"));
      tooltip.add(new TranslatableText("item.adventurez.gilded_netherite_fragment.tooltip2"));
    }
  }
}
