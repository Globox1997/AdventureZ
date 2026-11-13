package net.adventurez.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.util.collection.Pool;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {

    @Unique
    private static Pool<SpawnSettings.SpawnEntry> ADDED_SPAWNS;

    @ModifyExpressionValue(method = "getSpawnEntries", at = @At(value = "FIELD", target = "Lnet/minecraft/world/gen/structure/NetherFortressStructure;MONSTER_SPAWNS:Lnet/minecraft/util/collection/Pool;"))
    private static Pool<SpawnSettings.SpawnEntry> getSpawnEntriesMixin(Pool<SpawnSettings.SpawnEntry> original) {
        if (ADDED_SPAWNS == null) {
            List<SpawnSettings.SpawnEntry> spawnersList = new ArrayList<>(original.getEntries());
            if (ConfigInit.CONFIG.necromancer_spawn_weight > 0) {
                spawnersList.add(new SpawnSettings.SpawnEntry(EntityInit.NECROMANCER, ConfigInit.CONFIG.necromancer_spawn_weight, 1, 1));
            }
            if (ConfigInit.CONFIG.blaze_guardian_spawn_weight > 0) {
                spawnersList.add(new SpawnSettings.SpawnEntry(EntityInit.BLAZE_GUARDIAN, ConfigInit.CONFIG.blaze_guardian_spawn_weight, 1, 1));
            }
            ADDED_SPAWNS = Pool.of(spawnersList);
        }
        return ADDED_SPAWNS;
    }
}
