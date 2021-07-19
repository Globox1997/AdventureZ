package net.adventurez.mixin;

import java.util.List;

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
        if (this.spawnEntry.getEntityNbt() != null && EntityType.BLAZE.equals(EntityType.fromNbt(this.spawnEntry.getEntityNbt()).get())) {
            Box box = new Box(pos);
            List<BlazeGuardianEntity> list = world.getEntitiesByClass(BlazeGuardianEntity.class, box.expand(16D), EntityPredicates.EXCEPT_SPECTATOR);
            if (!list.isEmpty()) {
                spawnDelay = 600;
                info.cancel();
            }
        }
    }
}
