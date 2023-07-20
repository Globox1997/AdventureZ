package net.adventurez.mixin;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.BlazeGuardianEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin {
    @Shadow
    private int spawnDelay = 20;
    @Shadow
    private MobSpawnerEntry spawnEntry;

    private boolean spawnGuardian = false;

    @Inject(method = "setEntityId", at = @At("HEAD"))
    private void setEntityId(EntityType<?> type, @Nullable World world, Random random, BlockPos po, CallbackInfo info) {
        if (type != null && type.equals(EntityType.BLAZE)) {
            this.spawnGuardian = true;
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    private void readNbtMixin(@Nullable World world, BlockPos pos, NbtCompound nbt, CallbackInfo info) {
        this.spawnGuardian = nbt.getBoolean("SpawnGuardian");
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    private void writeNbtMixin(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> info) {
        nbt.putBoolean("SpawnGuardian", this.spawnGuardian);
    }

    @Inject(method = "serverTick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/MobSpawnerLogic;spawnDelay:I", ordinal = 1))
    private void serverTick(ServerWorld world, BlockPos pos, CallbackInfo info) {
        if (this.spawnDelay == 2 && ConfigInit.CONFIG.allow_guardian_spawner_spawn) {
            if (this.spawnGuardian) {
                BlazeGuardianEntity blazeGuardianEntity = (BlazeGuardianEntity) EntityInit.BLAZEGUARDIAN_ENTITY.create(world.toServerWorld());
                blazeGuardianEntity.initialize(world.toServerWorld(), world.getLocalDifficulty(pos), SpawnReason.STRUCTURE, null, null);
                int randomCheck = world.random.nextInt(3);
                blazeGuardianEntity.refreshPositionAndAngles(pos.north(world.random.nextInt(3) - 1).east(randomCheck - 1 == 0 ? -1 : randomCheck), world.random.nextFloat() * 360F, 0.0F);
                world.spawnEntity(blazeGuardianEntity);
                spawnDelay = 600;
                this.spawnGuardian = false;
            } else if (this.spawnEntry != null && this.spawnEntry.getNbt() != null) {
                Optional<EntityType<?>> optionalEntityType = EntityType.fromNbt(this.spawnEntry.getNbt());
                if (optionalEntityType.isPresent() && optionalEntityType.get().equals(EntityType.BLAZE)
                        && !world.getEntitiesByClass(BlazeGuardianEntity.class, new Box(pos).expand(16D), EntityPredicates.EXCEPT_SPECTATOR).isEmpty()) {
                    spawnDelay = 600;
                }
            }
        }
    }
}
