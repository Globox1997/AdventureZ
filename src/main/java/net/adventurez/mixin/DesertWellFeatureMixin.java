package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.entity.DesertRhinoEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.TemplateFeature;

@Mixin(TemplateFeature.class)
public class DesertWellFeatureMixin {

    @Unique
    private static final Identifier DESERT_WELL_TEMPLATE = Identifier.withDefaultNamespace("desert_well/well");

    @Shadow
    @Final
    private WeightedList<TemplateFeature.TemplateEntry> templates;

    @Inject(method = "place", at = @At(value = "RETURN"))
    private void generateMixin(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin, CallbackInfoReturnable<Boolean> info) {
        if (info.getReturnValue() && this.isDesertWell()) {
            int rhinoSpawnChance = ConfigInit.CONFIG.desert_rhino_well_spawn_chance;
            if (!level.isClientSide() && rhinoSpawnChance != 0) {
                int spawnChanceInt = random.nextInt(rhinoSpawnChance) + 1;
                BlockPos spawnPos = origin.above(2).south(3).west();
                if (spawnChanceInt == 1) {
                    for (int i = 0; i < 4; i++) {
                        if (i == 1) {
                            spawnPos = spawnPos.east(9);
                        }
                        if (i == 2) {
                            spawnPos = spawnPos.south(5).east(4);
                        }
                        if (i == 3) {
                            spawnPos = spawnPos.north(5).east(3);
                        }
                        if (level.isEmptyBlock(spawnPos) && SpawnPlacements.checkSpawnRules(EntityInit.DESERT_RHINO, level, EntitySpawnReason.CHUNK_GENERATION, spawnPos, random)) {
                            DesertRhinoEntity desertRhinoEntity = EntityInit.DESERT_RHINO.create(level.getLevel(), EntitySpawnReason.CHUNK_GENERATION);
                            desertRhinoEntity.snapTo(spawnPos, random.nextFloat() * 360F, 0.0F);
                            level.addFreshEntity(desertRhinoEntity);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Unique
    private boolean isDesertWell() {
        for (Weighted<TemplateFeature.TemplateEntry> entry : this.templates.unwrap()) {
            if (entry.value().template().equals(DESERT_WELL_TEMPLATE)) {
                return true;
            }
        }
        return false;
    }
}
