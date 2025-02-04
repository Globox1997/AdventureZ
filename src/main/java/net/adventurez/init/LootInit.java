package net.adventurez.init;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.registry.RegistryKey;

import java.util.List;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;

public class LootInit {

    private static final List<RegistryKey<LootTable>> ADDED_LOOT_TABLE = List.of(LootTables.PIGLIN_BARTERING_GAMEPLAY, LootTables.BASTION_BRIDGE_CHEST, LootTables.BASTION_HOGLIN_STABLE_CHEST,
            LootTables.BASTION_OTHER_CHEST, LootTables.BASTION_TREASURE_CHEST);

    private static boolean addedLootTable(RegistryKey<LootTable> lootTable) {
        for (RegistryKey<LootTable> id : ADDED_LOOT_TABLE) {
            if (id.equals(lootTable)) {
                return true;
            }
        }
        return false;
    }

    public static void init() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (addedLootTable(key)) {
                LootPool pool = LootPool.builder().with(ItemEntry.builder(ItemInit.GILDED_BLACKSTONE_SHARD).build()).rolls(BinomialLootNumberProvider.create(1, 0.01F)).build();
                tableBuilder.pool(pool);
                if (LootTables.BASTION_TREASURE_CHEST.equals(key)) {
                    pool = LootPool.builder().with(ItemEntry.builder(ItemInit.GILDED_UPGRADE_SMITHING_TEMPLATE).build()).rolls(BinomialLootNumberProvider.create(1, 0.5F)).build();
                    tableBuilder.pool(pool);
                }
            } else if ("minecraft:entities/piglin_brute".equals(key.getValue().toString())) {
                LootPool pool = LootPool.builder().with(ItemEntry.builder(ItemInit.GILDED_BLACKSTONE_SHARD).build()).rolls(BinomialLootNumberProvider.create(1, 0.1F)).build();
                tableBuilder.pool(pool);
            }
        });

    }

}