package net.adventurez.init;

import net.adventurez.AdventureMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.adventurez.block.*;
import net.adventurez.block.entity.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.List;
import net.adventurez.item.TooltipBlockItem;

public class BlockInit {

    private static final Map<String, Block> BLOCK_ITEMS = new LinkedHashMap<>();
    private static final Map<Block, List<String>> BLOCK_ITEM_TOOLTIPS = new LinkedHashMap<>();

    public static Block CHISELED_POLISHED_BLACKSTONE_HOLDER;
    public static Block PIGLIN_FLAG;
    public static Block SHADOW_CHEST;

    public static BlockEntityType<ChiseledPolishedBlackstoneHolderEntity> CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY;
    public static BlockEntityType<PiglinFlagEntity> PIGLIN_FLAG_ENTITY;
    public static BlockEntityType<DragonEggEntity> DRAGON_EGG_ENTITY;
    public static BlockEntityType<ShadowChestEntity> SHADOW_CHEST_ENTITY;

    public static void registerBlocks(BiConsumer<Identifier, Block> registrar) {
        CHISELED_POLISHED_BLACKSTONE_HOLDER = register(registrar, "chiseled_polished_blackstone_holder", ChiseledPolishedBlackstoneHolder::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE),
                List.of("block.adventurez.chiseled_polished_blackstone_holder.tooltip", "block.adventurez.chiseled_polished_blackstone_holder.tooltip2", "block.adventurez.chiseled_polished_blackstone_holder.tooltip3"));
        PIGLIN_FLAG = register(registrar, "piglin_flag", PiglinFlag::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS),
                List.of("block.adventurez.piglin_flag.tooltip", "block.adventurez.piglin_flag.tooltip2", "block.adventurez.piglin_flag.tooltip3"));
        SHADOW_CHEST = register(registrar, "shadow_chest", ShadowChest::new, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(100F, 1000.0F).lightLevel((state) -> {
            return 8;
        }), List.of());
    }

    public static void registerItems(BiConsumer<Identifier, Item> registrar) {
        for (Map.Entry<String, Block> entry : BLOCK_ITEMS.entrySet()) {
            Block block = entry.getValue();
            List<String> tooltipKeys = BLOCK_ITEM_TOOLTIPS.get(block);
            ItemInit.register(registrar, entry.getKey(),
                    properties -> tooltipKeys.isEmpty() ? new BlockItem(block, properties.useBlockDescriptionPrefix()) : new TooltipBlockItem(block, properties.useBlockDescriptionPrefix(), tooltipKeys),
                    new Item.Properties());
        }
    }

    public static void registerBlockEntityTypes(BiConsumer<Identifier, BlockEntityType<?>> registrar) {
        CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY = new BlockEntityType<>(ChiseledPolishedBlackstoneHolderEntity::new, Set.of(CHISELED_POLISHED_BLACKSTONE_HOLDER));
        registrar.accept(AdventureMain.identifierOf("chiseled_polished_blackstone_holder_entity"), CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY);
        PIGLIN_FLAG_ENTITY = new BlockEntityType<>(PiglinFlagEntity::new, Set.of(PIGLIN_FLAG));
        registrar.accept(AdventureMain.identifierOf("piglin_flag_entity"), PIGLIN_FLAG_ENTITY);
        DRAGON_EGG_ENTITY = new BlockEntityType<>(DragonEggEntity::new, Set.of(Blocks.DRAGON_EGG));
        registrar.accept(AdventureMain.identifierOf("dragon_egg_entity"), DRAGON_EGG_ENTITY);
        SHADOW_CHEST_ENTITY = new BlockEntityType<>(ShadowChestEntity::new, Set.of(SHADOW_CHEST));
        registrar.accept(AdventureMain.identifierOf("shadow_chest_entity"), SHADOW_CHEST_ENTITY);
    }

    private static Block register(BiConsumer<Identifier, Block> registrar, String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties, List<String> tooltipKeys) {
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, AdventureMain.identifierOf(id));
        Block block = factory.apply(properties.setId(blockKey));
        registrar.accept(blockKey.identifier(), block);
        BLOCK_ITEMS.put(id, block);
        BLOCK_ITEM_TOOLTIPS.put(block, tooltipKeys);
        return block;
    }
}
