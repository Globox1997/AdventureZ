package net.adventurez.init;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class TagInit {

    public static final Tag<Block> UNBREAKABLE_BLOCKS = TagFactory.BLOCK.create(new Identifier("adventurez", "unbreakable_blocks"));
    public static final Tag<Block> PLATFORM_NETHER_BLOCKS = TagFactory.BLOCK.create(new Identifier("adventurez", "platform_nether_blocks"));
    public static final Tag<Item> PIGLIN_NOT_ATTACK = TagFactory.ITEM.create(new Identifier("adventurez", "piglin_not_attack_items"));
    public static final Tag<Block> PLATFORM_END_BLOCKS = TagFactory.BLOCK.create(new Identifier("adventurez", "platform_end_blocks"));

    public static void init() {
    }

}