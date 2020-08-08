package net.adventurez.init;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class TagInit {

  public static final Tag<Block> UNBREAKABLE_BLOCKS = TagRegistry
      .block(new Identifier("adventurez", "unbreakable_blocks"));
  public static final Tag<Block> PLATFORM_BLOCKS = TagRegistry.block(new Identifier("adventurez", "platform_blocks"));
  public static final Tag<Item> PIGLIN_NOT_ATTACK = TagRegistry
      .item(new Identifier("adventurez", "piglin_not_attack_items"));

  public static void init() {
  }

}