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
    public static final Block CHISELED_POLISHED_BLACKSTONE_HOLDER = register("chiseled_polished_blackstone_holder", new ChiseledPolishedBlackstoneHolder(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block PIGLIN_FLAG = register("piglin_flag", new PiglinFlag(FabricBlockSettings.copy(Blocks.SPRUCE_PLANKS)));
    public static final Block SHADOW_CHEST = register("shadow_chest", new ShadowChest(FabricBlockSettings.create().requiresTool().strength(100F, 1000.0F).luminance((state) -> {
        return 8;
    })));

    public static BlockEntityType<ChiseledPolishedBlackstoneHolderEntity> CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY;
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
        CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "adventurez:chiseled_polished_blackstone_holder_entity",
                FabricBlockEntityTypeBuilder.create(ChiseledPolishedBlackstoneHolderEntity::new, CHISELED_POLISHED_BLACKSTONE_HOLDER).build(null));
        PIGLIN_FLAG_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "adventurez:piglin_flag_entity", FabricBlockEntityTypeBuilder.create(PiglinFlagEntity::new, PIGLIN_FLAG).build(null));
        DRAGON_EGG_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "adventurez:dragon_egg_entity", FabricBlockEntityTypeBuilder.create(DragonEggEntity::new, Blocks.DRAGON_EGG).build(null));
        SHADOW_CHEST_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "adventurez:shadow_chest_entity", FabricBlockEntityTypeBuilder.create(ShadowChestEntity::new, SHADOW_CHEST).build(null));
    }
}