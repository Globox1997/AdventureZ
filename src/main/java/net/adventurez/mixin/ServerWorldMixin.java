package net.adventurez.mixin;

import java.util.List;
import java.util.concurrent.Executor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.SummonerEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {

  public ServerWorldMixin(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session,
      ServerWorldProperties properties, RegistryKey<World> registryKey, DimensionType dimensionType,
      WorldGenerationProgressListener worldGenerationProgressListener, ChunkGenerator chunkGenerator, boolean bl,
      long l, List<Spawner> list, boolean bl2) {
    super(properties, registryKey, dimensionType, server::getProfiler, false, bl, l);

  }

  @Inject(method = "Lnet/minecraft/server/world/ServerWorld;tickChunk(Lnet/minecraft/world/chunk/WorldChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LightningEntity;setCosmetic(Z)V", shift = Shift.AFTER))
  public void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo info) {
    int spawnChanceInt = this.getRandom().nextInt(ConfigInit.CONFIG.summoner_thunder_spawn_chance);
    if (spawnChanceInt == 1) {
      ChunkPos chunkPos = chunk.getPos();
      int i = chunkPos.getStartX();
      int j = chunkPos.getStartZ();
      BlockPos blockPos = this.getSurface(this.getRandomPosInChunk(i, 0, j, 15));
      if (this.getBlockState(blockPos).isSolidBlock(this, blockPos)) {
        SummonerEntity summonerEntity = (SummonerEntity) EntityInit.SUMMONER_ENTITY.create(this);
        summonerEntity.updatePosition((double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ());
        summonerEntity.initialize((ServerWorld) (Object) this, this.getLocalDifficulty(blockPos), SpawnReason.EVENT,
            null, null);
        this.spawnEntity(summonerEntity);
        summonerEntity.playSpawnEffects();
      }
    }
  }

  @Shadow
  protected BlockPos getSurface(BlockPos pos) {
    return pos;
  }

}