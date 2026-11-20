package net.adventurez.init;

import net.adventurez.AdventureMain;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.adventurez.block.*;
import net.adventurez.block.entity.*;

import java.util.Set;
import java.util.function.Function;
import java.util.List;
import net.adventurez.item.TooltipBlockItem;

public class BlockInit {
    public static final Block CHISELED_POLISHED_BLACKSTONE_HOLDER = register("chiseled_polished_blackstone_holder", ChiseledPolishedBlackstoneHolder::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE),
            List.of("block.adventurez.chiseled_polished_blackstone_holder.tooltip", "block.adventurez.chiseled_polished_blackstone_holder.tooltip2", "block.adventurez.chiseled_polished_blackstone_holder.tooltip3"));
    public static final Block PIGLIN_FLAG = register("piglin_flag", PiglinFlag::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS),
            List.of("block.adventurez.piglin_flag.tooltip", "block.adventurez.piglin_flag.tooltip2", "block.adventurez.piglin_flag.tooltip3"));
    public static final Block SHADOW_CHEST = register("shadow_chest", ShadowChest::new, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(100F, 1000.0F).lightLevel((state) -> {
        return 8;
    }), List.of());

    public static BlockEntityType<ChiseledPolishedBlackstoneHolderEntity> CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY;
    public static BlockEntityType<PiglinFlagEntity> PIGLIN_FLAG_ENTITY;
    public static BlockEntityType<DragonEggEntity> DRAGON_EGG_ENTITY;
    public static BlockEntityType<ShadowChestEntity> SHADOW_CHEST_ENTITY;

    private static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties, List<String> tooltipKeys) {
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, AdventureMain.identifierOf(id));
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, AdventureMain.identifierOf(id));
        Block block = Registry.register(BuiltInRegistries.BLOCK, blockKey, factory.apply(properties.setId(blockKey)));
        Item item = Registry.register(BuiltInRegistries.ITEM, itemKey, tooltipKeys.isEmpty() ? new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix()) : new TooltipBlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix(), tooltipKeys));
        CreativeModeTabEvents.modifyOutputEvent(ItemInit.ADVENTUREZ_ITEM_GROUP).register(entries -> entries.accept(item));
        return block;
    }

    public static void init() {
        // Entities
        CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, AdventureMain.identifierOf("chiseled_polished_blackstone_holder_entity"),
                new BlockEntityType<>(ChiseledPolishedBlackstoneHolderEntity::new, Set.of(CHISELED_POLISHED_BLACKSTONE_HOLDER)));
        PIGLIN_FLAG_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, AdventureMain.identifierOf("piglin_flag_entity"), new BlockEntityType<>(PiglinFlagEntity::new, Set.of(PIGLIN_FLAG)));
        DRAGON_EGG_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, AdventureMain.identifierOf("dragon_egg_entity"), new BlockEntityType<>(DragonEggEntity::new, Set.of(Blocks.DRAGON_EGG)));
        SHADOW_CHEST_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, AdventureMain.identifierOf("shadow_chest_entity"), new BlockEntityType<>(ShadowChestEntity::new, Set.of(SHADOW_CHEST)));
    }
}
