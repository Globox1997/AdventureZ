package net.adventurez.item;

import net.adventurez.init.SoundInit;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

public class StoneGolemHeartItem extends Item {

  public StoneGolemHeartItem(Settings settings) {
    super(settings);
  }

  @Override
  public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    if ((int) world.getTime() % 100 == 0) {
      world.playSound(null, entity.getBlockPos(), SoundInit.HEART_BEAT_EVENT, SoundCategory.AMBIENT, 1F, 1F);
    }
  }

}