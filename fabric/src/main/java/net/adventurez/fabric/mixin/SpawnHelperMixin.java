package net.adventurez.fabric.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.adventurez.init.SpawnInit;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NaturalSpawner.class)
public class SpawnHelperMixin {

    @Unique
    private static WeightedList<MobSpawnSettings.SpawnerData> ADDED_SPAWNS;

    @ModifyExpressionValue(method = "mobsAt", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/structure/structures/NetherFortressStructure;FORTRESS_ENEMIES:Lnet/minecraft/util/random/WeightedList;"))
    private static WeightedList<MobSpawnSettings.SpawnerData> getSpawnEntriesMixin(WeightedList<MobSpawnSettings.SpawnerData> original) {
        if (ADDED_SPAWNS == null) {
            WeightedList.Builder<MobSpawnSettings.SpawnerData> builder = WeightedList.builder();
            for (Weighted<MobSpawnSettings.SpawnerData> weighted : original.unwrap()) {
                builder.add(weighted.value(), weighted.weight());
            }
            SpawnInit.addFortressSpawns(builder::add);
            ADDED_SPAWNS = builder.build();
        }
        return ADDED_SPAWNS;
    }
}
