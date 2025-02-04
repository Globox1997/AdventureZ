package net.adventurez.init;

import net.adventurez.AdventureMain;
import net.minecraft.block.Block;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class TagInit {

    // Block
    public static final TagKey<Block> UNBREAKABLE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, AdventureMain.identifierOf("unbreakable_blocks"));
    public static final TagKey<Block> PLATFORM_NETHER_BLOCKS = TagKey.of(RegistryKeys.BLOCK, AdventureMain.identifierOf("platform_nether_blocks"));
    public static final TagKey<Block> PLATFORM_END_BLOCKS = TagKey.of(RegistryKeys.BLOCK, AdventureMain.identifierOf("platform_end_blocks"));
    // Item
    public static final TagKey<Item> LEATHER_ITEMS = TagKey.of(RegistryKeys.ITEM, AdventureMain.identifierOf("leather_items"));
    public static final TagKey<Item> HOLDER_ITEMS = TagKey.of(RegistryKeys.ITEM, AdventureMain.identifierOf("holder_items"));
    public static final TagKey<Item> PIGLIN_NOT_ATTACK_ITEMS = TagKey.of(RegistryKeys.ITEM, AdventureMain.identifierOf("piglin_not_attack_items"));
    // Biome
    public static final TagKey<Biome> IS_MUSHROOM = TagKey.of(RegistryKeys.BIOME, Identifier.of("c", "mushroom"));
    // Damage Type
    public static final TagKey<DamageType> IS_WALL = TagKey.of(RegistryKeys.DAMAGE_TYPE, AdventureMain.identifierOf("is_wall"));

    public static void init() {
    }

}