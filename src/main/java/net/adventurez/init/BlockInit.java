package net.adventurez.init;

import net.adventurez.AdventureMain;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.adventurez.block.*;
import net.adventurez.block.entity.*;

public class BlockInit {
    public static final Block CHISELED_POLISHED_BLACKSTONE_HOLDER = register("chiseled_polished_blackstone_holder", new ChiseledPolishedBlackstoneHolder(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final Block PIGLIN_FLAG = register("piglin_flag", new PiglinFlag(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final Block SHADOW_CHEST = register("shadow_chest", new ShadowChest(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(100F, 1000.0F).lightLevel((state) -> {
        return 8;
    })));

    public static BlockEntityType<ChiseledPolishedBlackstoneHolderEntity> CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY;
    public static BlockEntityType<PiglinFlagEntity> PIGLIN_FLAG_ENTITY;
    public static BlockEntityType<DragonEggEntity> DRAGON_EGG_ENTITY;
    public static BlockEntityType<ShadowChestEntity> SHADOW_CHEST_ENTITY;

    private static Block register(String id, Block block) {
        return register(AdventureMain.identifierOf(id), block);
    }

    private static Block register(ResourceLocation id, Block block) {
        Item item = Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, new Item.Properties()));
        ItemGroupEvents.modifyEntriesEvent(ItemInit.ADVENTUREZ_ITEM_GROUP).register(entries -> entries.accept(item));

        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static void init() {
        // Entities
        CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "adventurez:chiseled_polished_blackstone_holder_entity",
                BlockEntityType.Builder.of(ChiseledPolishedBlackstoneHolderEntity::new, CHISELED_POLISHED_BLACKSTONE_HOLDER).build(null));
        PIGLIN_FLAG_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "adventurez:piglin_flag_entity", BlockEntityType.Builder.of(PiglinFlagEntity::new, PIGLIN_FLAG).build(null));
        DRAGON_EGG_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "adventurez:dragon_egg_entity", BlockEntityType.Builder.of(DragonEggEntity::new, Blocks.DRAGON_EGG).build(null));
        SHADOW_CHEST_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "adventurez:shadow_chest_entity", BlockEntityType.Builder.of(ShadowChestEntity::new, SHADOW_CHEST).build(null));
    }
}