package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.SummonerEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

@Mixin(ServerLevel.class)
public abstract class ServerWorldMixin {

    @Inject(method = "tickThunder", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LightningBolt;setVisualOnly(Z)V", shift = Shift.AFTER))
    public void tickChunk(LevelChunk chunk, CallbackInfo info) {
        ServerLevel level = (ServerLevel) (Object) this;
        int summonerSpawnChance = ConfigInit.CONFIG.summoner_thunder_spawn_chance;
        if (summonerSpawnChance != 0) {
            int spawnChanceInt = level.getRandom().nextInt(summonerSpawnChance) + 1;
            if (spawnChanceInt == 1) {
                ChunkPos chunkPos = chunk.getPos();
                int i = chunkPos.getMinBlockX();
                int j = chunkPos.getMinBlockZ();
                BlockPos blockPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, level.getBlockRandomPos(i, 0, j, 15));
                if (SpawnPlacements.checkSpawnRules(EntityInit.SUMMONER, level, EntitySpawnReason.EVENT, blockPos, level.getRandom())) {
                    SummonerEntity summonerEntity = EntityInit.SUMMONER.create(level, EntitySpawnReason.MOB_SUMMONED);
                    summonerEntity.absSnapTo((double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ());
                    summonerEntity.finalizeSpawn(level, level.getCurrentDifficultyAt(blockPos), EntitySpawnReason.EVENT, null);
                    level.addFreshEntity(summonerEntity);
                    summonerEntity.spawnAnim();
                }
            }
        }
    }
}
