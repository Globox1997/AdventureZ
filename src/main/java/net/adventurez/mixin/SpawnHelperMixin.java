package net.adventurez.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(NaturalSpawner.class)
public class SpawnHelperMixin {

    @Unique
    private static WeightedRandomList<MobSpawnSettings.SpawnerData> ADDED_SPAWNS;

    @ModifyExpressionValue(method = "mobsAt", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/structure/structures/NetherFortressStructure;FORTRESS_ENEMIES:Lnet/minecraft/util/random/WeightedRandomList;"))
    private static WeightedRandomList<MobSpawnSettings.SpawnerData> getSpawnEntriesMixin(WeightedRandomList<MobSpawnSettings.SpawnerData> original) {
        if (ADDED_SPAWNS == null) {
            List<MobSpawnSettings.SpawnerData> spawnersList = new ArrayList<>(original.unwrap());
            if (ConfigInit.CONFIG.necromancer_spawn_weight > 0) {
                spawnersList.add(new MobSpawnSettings.SpawnerData(EntityInit.NECROMANCER, ConfigInit.CONFIG.necromancer_spawn_weight, 1, 1));
            }
            if (ConfigInit.CONFIG.blaze_guardian_spawn_weight > 0) {
                spawnersList.add(new MobSpawnSettings.SpawnerData(EntityInit.BLAZE_GUARDIAN, ConfigInit.CONFIG.blaze_guardian_spawn_weight, 1, 1));
            }
            ADDED_SPAWNS = WeightedRandomList.create(spawnersList);
        }
        return ADDED_SPAWNS;
    }
}
