package net.adventurez.init;

import net.minecraft.block.Block;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class TagInit {

    // Block
    public static final TagKey<Block> UNBREAKABLE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier("adventurez", "unbreakable_blocks"));
    public static final TagKey<Block> PLATFORM_NETHER_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier("adventurez", "platform_nether_blocks"));
    public static final TagKey<Block> PLATFORM_END_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier("adventurez", "platform_end_blocks"));
    // Item
    public static final TagKey<Item> LEATHER_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier("adventurez", "leather_items"));
    public static final TagKey<Item> HOLDER_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier("adventurez", "holder_items"));
    public static final TagKey<Item> PIGLIN_NOT_ATTACK_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier("adventurez", "piglin_not_attack_items"));
    // Biome
    public static final TagKey<Biome> IS_MUSHROOM = TagKey.of(RegistryKeys.BIOME, new Identifier("c", "mushroom"));
    // Damage Type
    public static final TagKey<DamageType> IS_WALL = TagKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("adventurez", "is_wall"));

    public static void init() {
    }

}