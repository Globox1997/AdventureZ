package net.adventurez.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.adventurez.block.*;
import net.adventurez.block.entity.*;

public class BlockInit {
    public static final Block STONE_HOLDER_BLOCK = register("stone_holder_block", new StoneHolderBlock(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block PIGLIN_FLAG_BLOCK = register("piglin_flag_block", new PiglinFlagBlock(FabricBlockSettings.copy(Blocks.SPRUCE_PLANKS)));
    public static final Block SHADOW_CHEST_BLOCK = register("shadow_chest_block", new ShadowChestBlock(FabricBlockSettings.create().requiresTool().strength(100F, 1000.0F).luminance((state) -> {
        return 8;
    })));

    public static BlockEntityType<StoneHolderEntity> STONE_HOLDER_ENTITY;
    public static BlockEntityType<PiglinFlagEntity> PIGLIN_FLAG_ENTITY;
    public static BlockEntityType<DragonEggEntity> DRAGON_EGG_ENTITY;
    public static BlockEntityType<ShadowChestEntity> SHADOW_CHEST_ENTITY;

    private static Block register(String id, Block block) {
        return register(new Identifier("adventurez", id), block);
    }

    private static Block register(Identifier id, Block block) {
        Item item = Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(ItemInit.ADVENTUREZ_ITEM_GROUP).register(entries -> entries.add(item));

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void init() {
        // Entities
        STONE_HOLDER_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "adventurez:stone_holder_entity",
                FabricBlockEntityTypeBuilder.create(StoneHolderEntity::new, STONE_HOLDER_BLOCK).build(null));
        PIGLIN_FLAG_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "adventurez:piglin_flag_entity",
                FabricBlockEntityTypeBuilder.create(PiglinFlagEntity::new, PIGLIN_FLAG_BLOCK).build(null));
        DRAGON_EGG_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "adventurez:dragon_egg_entity", FabricBlockEntityTypeBuilder.create(DragonEggEntity::new, Blocks.DRAGON_EGG).build(null));
        SHADOW_CHEST_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "adventurez:shadow_chest_entity",
                FabricBlockEntityTypeBuilder.create(ShadowChestEntity::new, SHADOW_CHEST_BLOCK).build(null));
    }
}