package net.adventurez.item;

import java.util.List;
import java.util.Random;

import net.adventurez.init.SoundInit;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class StoneGolemHeart extends Item {

  public StoneGolemHeart(Settings settings) {
    super(settings);
  }

  @Override
  public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    tooltip.add(new TranslatableText("item.adventurez.stone_golem_heart.tooltip"));
  }

  @Override
  public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    Random random = new Random();
    double beat = (random.nextInt() % 30);
    if (beat == 22) {
      world.playSound(null, entity.getBlockPos(), SoundInit.HEART_BEAT_EVENT, SoundCategory.AMBIENT, 1F, 1F);
    }
  }

}