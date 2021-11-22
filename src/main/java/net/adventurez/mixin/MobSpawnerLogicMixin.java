package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.BlazeGuardianEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.MobSpawnerLogic;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin {
    @Shadow
    private int spawnDelay = 20;
    @Shadow
    private MobSpawnerEntry spawnEntry;

    @Inject(method = "serverTick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/MobSpawnerLogic;spawnCount:I", ordinal = 0, shift = Shift.BEFORE), cancellable = true)
    private void serverTickMixin(ServerWorld world, BlockPos pos, CallbackInfo info) {
        if (this.spawnEntry.getNbt() != null && EntityType.BLAZE.equals(EntityType.fromNbt(this.spawnEntry.getNbt()).get())) {
            if (!world.getEntitiesByClass(BlazeGuardianEntity.class, new Box(pos).expand(16D), EntityPredicates.EXCEPT_SPECTATOR).isEmpty()) {
                spawnDelay = 600;
                info.cancel();
            }
        }
    }
}
