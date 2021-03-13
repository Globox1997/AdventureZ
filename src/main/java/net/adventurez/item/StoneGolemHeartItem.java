package net.adventurez.item;

import java.util.List;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.SoundInit;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class StoneGolemHeartItem extends Item {

  public StoneGolemHeartItem(Settings settings) {
    super(settings);
  }

  @Override
  public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    if (ConfigInit.CONFIG.display_rareness) {
      tooltip.add(new TranslatableText("item.adventurez.epic_item.tooltip"));
    }
  }

  @Override
  public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    if ((int) world.getTime() % 100 == 0) {
      world.playSound(null, entity.getBlockPos(), SoundInit.HEART_BEAT_EVENT, SoundCategory.AMBIENT, 1F, 1F);
    }
  }

}