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
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {

    public ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> registryEntry, Supplier<Profiler> profiler, boolean isClient,
            boolean debugWorld, long seed) {
        super(properties, registryRef, registryEntry, profiler, isClient, debugWorld, seed);
    }

    @Inject(method = "tickChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LightningEntity;setCosmetic(Z)V", shift = Shift.AFTER))
    public void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo info) {
        int summonerSpawnChance = ConfigInit.CONFIG.summoner_thunder_spawn_chance;
        if (summonerSpawnChance != 0) {
            int spawnChanceInt = this.getRandom().nextInt(summonerSpawnChance) + 1;
            if (spawnChanceInt == 1) {
                ChunkPos chunkPos = chunk.getPos();
                int i = chunkPos.getStartX();
                int j = chunkPos.getStartZ();
                BlockPos blockPos = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, this.getRandomPosInChunk(i, 0, j, 15));
                if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, chunk.getWorld(), blockPos, EntityInit.SUMMONER_ENTITY)) {
                    SummonerEntity summonerEntity = (SummonerEntity) EntityInit.SUMMONER_ENTITY.create(this);
                    summonerEntity.updatePosition((double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ());
                    summonerEntity.initialize((ServerWorld) (Object) this, this.getLocalDifficulty(blockPos), SpawnReason.EVENT, null, null);
                    this.spawnEntity(summonerEntity);
                    summonerEntity.playSpawnEffects();
                }
            }
        }
    }

}