package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.entity.AmethystGolemEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.GeodeFeature;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;

@Mixin(GeodeFeature.class)
public class GeodeFeatureMixin {

    @Inject(method = "place", at = @At(value = "RETURN"))
    private void generateMixin(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin, CallbackInfoReturnable<Boolean> info) {
        if (!level.isClientSide() && info.getReturnValue()) {
            int amethystSpawnChance = ConfigInit.CONFIG.amethyst_golem_spawn_chance;
            if (amethystSpawnChance != 0) {
                int spawnChanceInt = random.nextInt(amethystSpawnChance) + 1;
                BlockPos spawnPos = origin.south(4).east(4);
                if (spawnChanceInt == 1 && (level.isEmptyBlock(spawnPos) || level.isEmptyBlock(spawnPos.above()))) {
                    AmethystGolemEntity amethystGolemEntity = (AmethystGolemEntity) EntityInit.AMETHYST_GOLEM.create(level.getLevel(), EntitySpawnReason.CHUNK_GENERATION);
                    amethystGolemEntity.getEntityData().set(AmethystGolemEntity.DEEPSLATE_VARIANT, spawnPos.getY() <= 0);
                    if (!level.isEmptyBlock(spawnPos)) {
                        spawnPos = spawnPos.above();
                    }
                    amethystGolemEntity.snapTo(spawnPos, random.nextFloat() * 360F, 0.0F);
                    level.addFreshEntity(amethystGolemEntity);
                }
            }
        }
    }
}
