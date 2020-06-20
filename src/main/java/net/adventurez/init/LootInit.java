package net.adventurez.init;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.BinomialLootTableRange;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class LootInit {

  public static final Identifier[] ADDED_LOOT_TABLE = new Identifier[] { LootTables.PIGLIN_BARTERING_GAMEPLAY,
      LootTables.BASTION_BRIDGE_CHEST, LootTables.BASTION_HOGLIN_STABLE_CHEST, LootTables.BASTION_OTHER_CHEST,
      LootTables.BASTION_TREASURE_CHEST };

  private static boolean addedLootTable(Identifier lootTable) {
    for (Identifier id : ADDED_LOOT_TABLE) {
      if (id.equals(lootTable)) {
        return true;
      }
    }
    return false;
  }

  public static void init() {

    LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
      if (addedLootTable(id)) {
        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder().rolls(new BinomialLootTableRange(1, 0.05F))
            .with(ItemEntry.builder(ItemInit.GILDED_STONE_ITEM));
        supplier.pool(poolBuilder);
      }
    });

  }

}