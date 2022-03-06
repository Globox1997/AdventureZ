package net.adventurez.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TagInit {

    // Block
    public static final TagKey<Block> UNBREAKABLE_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier("adventurez", "unbreakable_blocks"));
    public static final TagKey<Block> PLATFORM_NETHER_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier("adventurez", "platform_nether_blocks"));
    public static final TagKey<Block> PLATFORM_END_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier("adventurez", "platform_end_blocks"));
    // Item
    public static final TagKey<Item> LEATHER_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier("adventurez", "leather_items"));
    public static final TagKey<Item> HOLDER_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier("adventurez", "holder_items"));
    public static final TagKey<Item> PIGLIN_NOT_ATTACK_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier("adventurez", "piglin_not_attack_items"));

    public static void init() {
    }

}