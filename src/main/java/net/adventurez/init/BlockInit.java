package net.adventurez.init;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.adventurez.block.*;
import net.adventurez.block.entity.*;

public class BlockInit {
    public static final StoneHolderBlock STONE_HOLDER_BLOCK = new StoneHolderBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final PiglinFlagBlock PIGLIN_FLAG_BLOCK = new PiglinFlagBlock(FabricBlockSettings.copy(Blocks.SPRUCE_PLANKS));
    public static final ShadowChestBlock SHADOW_CHEST_BLOCK = new ShadowChestBlock(FabricBlockSettings.of((Material.STONE)).requiresTool().strength(100F, 1000.0F).luminance((state) -> {
        return 8;
    }));

    public static BlockEntityType<StoneHolderEntity> STONE_HOLDER_ENTITY;
    public static BlockEntityType<PiglinFlagEntity> PIGLIN_FLAG_ENTITY;
    public static BlockEntityType<DragonEggEntity> DRAGON_EGG_ENTITY;
    public static BlockEntityType<ShadowChestEntity> SHADOW_CHEST_ENTITY;

    public static void init() {
        // Blocks
        Registry.register(Registry.ITEM, new Identifier("adventurez", "stone_holder_block"), new BlockItem(STONE_HOLDER_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.BLOCK, new Identifier("adventurez", "stone_holder_block"), STONE_HOLDER_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("adventurez", "piglin_flag_block"), new BlockItem(PIGLIN_FLAG_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.BLOCK, new Identifier("adventurez", "piglin_flag_block"), PIGLIN_FLAG_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("adventurez", "shadow_chest_block"), new BlockItem(SHADOW_CHEST_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.BLOCK, new Identifier("adventurez", "shadow_chest_block"), SHADOW_CHEST_BLOCK);
        // Entities
        STONE_HOLDER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "adventurez:stone_holder_entity",
                FabricBlockEntityTypeBuilder.create(StoneHolderEntity::new, STONE_HOLDER_BLOCK).build(null));
        PIGLIN_FLAG_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "adventurez:piglin_flag_entity", FabricBlockEntityTypeBuilder.create(PiglinFlagEntity::new, PIGLIN_FLAG_BLOCK).build(null));
        DRAGON_EGG_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "adventurez:dragon_egg_entity", FabricBlockEntityTypeBuilder.create(DragonEggEntity::new, Blocks.DRAGON_EGG).build(null));
        SHADOW_CHEST_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "adventurez:shadow_chest_entity",
                FabricBlockEntityTypeBuilder.create(ShadowChestEntity::new, SHADOW_CHEST_BLOCK).build(null));
    }
}