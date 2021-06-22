package net.adventurez.mixin;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.util.collection.Pool;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.NetherFortressFeature;

@Mixin(NetherFortressFeature.class)
public class NetherFortressFeatureMixin {

    @Shadow
    @Final
    private static Pool<SpawnSettings.SpawnEntry> MONSTER_SPAWNS;
    private static final List<SpawnSettings.SpawnEntry> ADDED_SPAWNS;

    @Inject(method = "Lnet/minecraft/world/gen/feature/NetherFortressFeature;getMonsterSpawns()Ljava/util/List;", at = @At(value = "HEAD"), cancellable = true)
    public void getMonsterSpawnsMixin(CallbackInfoReturnable<List<SpawnSettings.SpawnEntry>> info) {
        List<SpawnSettings.SpawnEntry> spawnersList = new ArrayList<>(MONSTER_SPAWNS.getEntries());
        spawnersList.addAll(ADDED_SPAWNS);
        info.setReturnValue(spawnersList);
    }

    static {
        ADDED_SPAWNS = ImmutableList.of(new SpawnSettings.SpawnEntry(EntityInit.NECROMANCER_ENTITY, ConfigInit.CONFIG.necromancer_spawn_weight, 1, 1),
                new SpawnSettings.SpawnEntry(EntityInit.BLAZEGUARDIAN_ENTITY, ConfigInit.CONFIG.blaze_guardian_spawn_weight, 1, 1));
    }
}
