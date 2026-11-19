package net.adventurez.mixin;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.SummonerEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.WritableLevelData;

@Mixin(ServerLevel.class)
public abstract class ServerWorldMixin extends Level {

    public ServerWorldMixin(WritableLevelData properties, ResourceKey<Level> registryRef, RegistryAccess registryManager, Holder<DimensionType> dimensionEntry,
            Supplier<ProfilerFiller> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess, maxChainedNeighborUpdates);
    }

    @Inject(method = "tickChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LightningBolt;setVisualOnly(Z)V", shift = Shift.AFTER))
    public void tickChunk(LevelChunk chunk, int randomTickSpeed, CallbackInfo info) {
        int summonerSpawnChance = ConfigInit.CONFIG.summoner_thunder_spawn_chance;
        if (summonerSpawnChance != 0) {
            int spawnChanceInt = this.getRandom().nextInt(summonerSpawnChance) + 1;
            if (spawnChanceInt == 1) {
                ChunkPos chunkPos = chunk.getPos();
                int i = chunkPos.getMinBlockX();
                int j = chunkPos.getMinBlockZ();

                BlockPos blockPos = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, this.getBlockRandomPos(i, 0, j, 15));
                if (SpawnPlacements.checkSpawnRules(EntityInit.SUMMONER, (ServerLevel) (Object) this, MobSpawnType.EVENT, blockPos, this.getRandom())) {
                    SummonerEntity summonerEntity = EntityInit.SUMMONER.create(this);
                    summonerEntity.absMoveTo((double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ());
                    summonerEntity.finalizeSpawn((ServerLevel) (Object) this, this.getCurrentDifficultyAt(blockPos), MobSpawnType.EVENT, null);
                    this.addFreshEntity(summonerEntity);
                    summonerEntity.spawnAnim();
                }
            }
        }
    }

}