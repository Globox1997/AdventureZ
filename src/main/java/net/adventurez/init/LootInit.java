package net.adventurez.init;

import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class LootInit {

    public static final Identifier[] ADDED_LOOT_TABLE = new Identifier[] { LootTables.PIGLIN_BARTERING_GAMEPLAY, LootTables.BASTION_BRIDGE_CHEST, LootTables.BASTION_HOGLIN_STABLE_CHEST,
            LootTables.BASTION_OTHER_CHEST, LootTables.BASTION_TREASURE_CHEST };

    private static boolean addedLootTable(Identifier lootTable) {
        for (Identifier id : ADDED_LOOT_TABLE) {
            if (id.equals(lootTable)) {
                return true;
            }
        }
        return false;
    }

    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (addedLootTable(id)) {
                LootPool pool = LootPool.builder().with(ItemEntry.builder(ItemInit.GILDED_BLACKSTONE_SHARD).build()).rolls(BinomialLootNumberProvider.create(1, 0.01F)).build();
                supplier.pool(pool);
                if (LootTables.BASTION_TREASURE_CHEST.equals(id)) {
                    pool = LootPool.builder().with(ItemEntry.builder(ItemInit.GILDED_UPGRADE_SMITHING_TEMPLATE).build()).rolls(BinomialLootNumberProvider.create(1, 0.5F)).build();
                    supplier.pool(pool);
                }
            } else if ("minecraft:entities/piglin_brute".equals(id.toString())) {
                LootPool pool = LootPool.builder().with(ItemEntry.builder(ItemInit.GILDED_BLACKSTONE_SHARD).build()).rolls(BinomialLootNumberProvider.create(1, 0.1F)).build();
                supplier.pool(pool);
            }
        });

    }

}