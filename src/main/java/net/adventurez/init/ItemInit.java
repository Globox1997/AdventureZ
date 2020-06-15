package net.adventurez.init;

import net.adventurez.item.RedStoneItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemInit {

  public static final RedStoneItem RED_STONE_ITEM = new RedStoneItem(new Item.Settings(),
      () -> EntityInit.REDSTONE_ENTITY);

  public static void init() {

    Registry.register(Registry.ITEM, new Identifier("adventurez", "red_stone_item"), RED_STONE_ITEM);

  }

}