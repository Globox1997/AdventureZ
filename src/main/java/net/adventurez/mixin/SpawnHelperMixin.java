package net.adventurez.mixin;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.NetherFortressStructure;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {
    private static final Pool<SpawnSettings.SpawnEntry> ADDED_SPAWNS;

    @Inject(method = "getSpawnEntries", at = @At(value = "FIELD", target = "Lnet/minecraft/world/gen/structure/NetherFortressStructure;MONSTER_SPAWNS:Lnet/minecraft/util/collection/Pool;", ordinal = 0), cancellable = true)
    private static void getSpawnEntriesMixin(ServerWorld world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, SpawnGroup spawnGroup, BlockPos pos,
            @Nullable RegistryEntry<Biome> biomeEntry, CallbackInfoReturnable<Pool<SpawnSettings.SpawnEntry>> info) {
        List<SpawnSettings.SpawnEntry> spawnersList = new ArrayList<>(NetherFortressStructure.MONSTER_SPAWNS.getEntries());
        spawnersList.addAll(ADDED_SPAWNS.getEntries());
        info.setReturnValue(Pool.of(spawnersList));
    }

    static {
        ADDED_SPAWNS = Pool.of(new SpawnSettings.SpawnEntry(EntityInit.NECROMANCER_ENTITY, ConfigInit.CONFIG.necromancer_spawn_weight, 1, 1),
                new SpawnSettings.SpawnEntry(EntityInit.BLAZEGUARDIAN_ENTITY, ConfigInit.CONFIG.blaze_guardian_spawn_weight, 1, 1));
    }
}
