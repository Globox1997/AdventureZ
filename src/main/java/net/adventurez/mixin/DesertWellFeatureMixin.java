package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.entity.DesertRhinoEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.gen.feature.DesertWellFeature;
import net.minecraft.world.gen.feature.GeodeFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

@Mixin(DesertWellFeature.class)
public class DesertWellFeatureMixin {

    @Inject(method = "generate", at = @At(value = "RETURN"))
    private void generateMixin(FeatureContext<GeodeFeatureConfig> context, CallbackInfoReturnable<Boolean> info) {
        if (info.getReturnValue()) {
            int rhinoSpawnChance = ConfigInit.CONFIG.desert_rhino_well_spawn_chance;
            if (!context.getWorld().isClient() && rhinoSpawnChance != 0) {
                int spawnChanceInt = context.getRandom().nextInt(rhinoSpawnChance) + 1;
                BlockPos spawnPos = context.getOrigin().south(3).west();
                if (spawnChanceInt == 1) {
                    for (int i = 0; i < 4; i++) {
                        if (i == 1)
                            spawnPos = spawnPos.east(9);
                        if (i == 2)
                            spawnPos = spawnPos.south(5).east(4);
                        if (i == 3)
                            spawnPos = spawnPos.north(5).east(3);
                        if (context.getWorld().isAir(spawnPos) && SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, context.getWorld(), spawnPos, EntityInit.PIGLINBEAST_ENTITY)) {
                            DesertRhinoEntity desertRhinoEntity = (DesertRhinoEntity) EntityInit.DESERT_RHINO_ENTITY.create(context.getWorld().toServerWorld());
                            desertRhinoEntity.refreshPositionAndAngles(spawnPos, context.getRandom().nextFloat() * 360F, 0.0F);
                            context.getWorld().spawnEntity(desertRhinoEntity);
                            break;
                        }
                    }
                }
            }
        }
    }
}
