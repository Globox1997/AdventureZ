package net.adventurez.init;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import java.util.List;

public class LootInit {

    private static final List<ResourceKey<LootTable>> ADDED_LOOT_TABLE = List.of(BuiltInLootTables.PIGLIN_BARTERING, BuiltInLootTables.BASTION_BRIDGE, BuiltInLootTables.BASTION_HOGLIN_STABLE,
            BuiltInLootTables.BASTION_OTHER, BuiltInLootTables.BASTION_TREASURE);

    private static boolean addedLootTable(ResourceKey<LootTable> lootTable) {
        for (ResourceKey<LootTable> id : ADDED_LOOT_TABLE) {
            if (id.equals(lootTable)) {
                return true;
            }
        }
        return false;
    }

    public static void init() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (addedLootTable(key)) {
                LootPool pool = LootPool.lootPool().with(LootItem.lootTableItem(ItemInit.GILDED_BLACKSTONE_SHARD).build()).setRolls(BinomialDistributionGenerator.binomial(1, 0.01F)).build();
                tableBuilder.pool(pool);
                if (BuiltInLootTables.BASTION_TREASURE.equals(key)) {
                    pool = LootPool.lootPool().with(LootItem.lootTableItem(ItemInit.GILDED_UPGRADE_SMITHING_TEMPLATE).build()).setRolls(BinomialDistributionGenerator.binomial(1, 0.5F)).build();
                    tableBuilder.pool(pool);
                }
            } else if ("minecraft:entities/piglin_brute".equals(key.location().toString())) {
                LootPool pool = LootPool.lootPool().with(LootItem.lootTableItem(ItemInit.GILDED_BLACKSTONE_SHARD).build()).setRolls(BinomialDistributionGenerator.binomial(1, 0.1F)).build();
                tableBuilder.pool(pool);
            }
        });

    }

}