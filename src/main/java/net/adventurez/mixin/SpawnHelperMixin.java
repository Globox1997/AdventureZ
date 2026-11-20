package net.adventurez.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
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
            if (ConfigInit.CONFIG.necromancer_spawn_weight > 0) {
                builder.add(new MobSpawnSettings.SpawnerData(EntityInit.NECROMANCER, ConstantInt.of(1)), ConfigInit.CONFIG.necromancer_spawn_weight);
            }
            if (ConfigInit.CONFIG.blaze_guardian_spawn_weight > 0) {
                builder.add(new MobSpawnSettings.SpawnerData(EntityInit.BLAZE_GUARDIAN, ConstantInt.of(1)), ConfigInit.CONFIG.blaze_guardian_spawn_weight);
            }
            ADDED_SPAWNS = builder.build();
        }
        return ADDED_SPAWNS;
    }
}
