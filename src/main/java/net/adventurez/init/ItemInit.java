package net.adventurez.init;

import net.adventurez.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemInit {

  public static final GildedStoneItem GILDED_STONE_ITEM = new GildedStoneItem(new Item.Settings().group(ItemGroup.MISC),
      () -> EntityInit.GILDEDSTONE_ENTITY);
  public static final StoneGolemHeart STONE_GOLEM_HEART = new StoneGolemHeart(
      new Item.Settings().group(ItemGroup.MISC));
  public static final StoneGolemArm STONE_GOLEM_ARM = new StoneGolemArm(
      new Item.Settings().group(ItemGroup.COMBAT).maxDamage(2506));

  public static void init() {
    Registry.register(Registry.ITEM, new Identifier("adventurez", "gilded_stone_item"), GILDED_STONE_ITEM);
    Registry.register(Registry.ITEM, new Identifier("adventurez", "stone_golem_heart"), STONE_GOLEM_HEART);
    Registry.register(Registry.ITEM, new Identifier("adventurez", "stone_golem_arm"), STONE_GOLEM_ARM);
  }

}