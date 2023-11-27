package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.entity.AmethystGolemEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.GeodeFeature;
import net.minecraft.world.gen.feature.GeodeFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

@Mixin(GeodeFeature.class)
public class GeodeFeatureMixin {

    @Inject(method = "generate", at = @At(value = "RETURN"))
    private void generateMixin(FeatureContext<GeodeFeatureConfig> context, CallbackInfoReturnable<Boolean> info) {
        if (!context.getWorld().isClient() && info.getReturnValue()) {
            int amethystSpawnChance = ConfigInit.CONFIG.amethyst_golem_spawn_chance;
            if (amethystSpawnChance != 0) {
                int spawnChanceInt = context.getRandom().nextInt(amethystSpawnChance) + 1;
                BlockPos spawnPos = context.getOrigin().south(4).east(4);
                if (spawnChanceInt == 1 && (context.getWorld().isAir(spawnPos) || context.getWorld().isAir(spawnPos.up()))) {
                    AmethystGolemEntity amethystGolemEntity = (AmethystGolemEntity) EntityInit.AMETHYST_GOLEM.create(context.getWorld().toServerWorld());
                    amethystGolemEntity.getDataTracker().set(AmethystGolemEntity.DEEPSLATE_VARIANT, spawnPos.getY() <= 0);
                    if (!context.getWorld().isAir(spawnPos)) {
                        spawnPos = spawnPos.up();
                    }
                    amethystGolemEntity.refreshPositionAndAngles(spawnPos, context.getRandom().nextFloat() * 360F, 0.0F);
                    context.getWorld().spawnEntity(amethystGolemEntity);
                }
            }
        }
    }
}
