package net.adventurez.init;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.adventurez.block.*;
import net.adventurez.block.entity.StoneHolderEntity;

public class BlockInit {
  public static final StoneHolderBlock STONE_HOLDER_BLOCK = new StoneHolderBlock(
      FabricBlockSettings.copy(Blocks.STONE));

  public static BlockEntityType<StoneHolderEntity> STONE_HOLDER_ENTITY;

  public static void init() {

    Registry.register(Registry.ITEM, new Identifier("adventurez", "stone_holder_block"),
        new BlockItem(STONE_HOLDER_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
    Registry.register(Registry.BLOCK, new Identifier("adventurez", "stone_holder_block"), STONE_HOLDER_BLOCK);
    STONE_HOLDER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "adventurez:stone_holder_entity",
        BlockEntityType.Builder.create(StoneHolderEntity::new, STONE_HOLDER_BLOCK).build(null));
  }
}