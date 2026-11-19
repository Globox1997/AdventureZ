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
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.phys.AABB;

@Mixin(BaseSpawner.class)
public class MobSpawnerLogicMixin {
    @Shadow
    private int spawnDelay = 20;
    @Shadow
    @Nullable
    private SpawnData nextSpawnData;

    private boolean spawnGuardian = false;

    @Inject(method = "setEntityId", at = @At("HEAD"))
    private void setEntityId(EntityType<?> type, @Nullable Level world, RandomSource random, BlockPos po, CallbackInfo info) {
        if (type != null && type.equals(EntityType.BLAZE)) {
            this.spawnGuardian = true;
        }
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void readNbtMixin(@Nullable Level world, BlockPos pos, CompoundTag nbt, CallbackInfo info) {
        this.spawnGuardian = nbt.getBoolean("SpawnGuardian");
    }

    @Inject(method = "save", at = @At("HEAD"))
    private void writeNbtMixin(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> info) {
        nbt.putBoolean("SpawnGuardian", this.spawnGuardian);
    }

    @Inject(method = "serverTick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/BaseSpawner;spawnDelay:I", ordinal = 1))
    private void serverTick(ServerLevel world, BlockPos pos, CallbackInfo info) {
        if (this.spawnDelay == 2 && ConfigInit.CONFIG.allow_guardian_spawner_spawn) {
            if (this.spawnGuardian) {
                BlazeGuardianEntity blazeGuardianEntity = EntityInit.BLAZE_GUARDIAN.create(world.getLevel());
                blazeGuardianEntity.finalizeSpawn(world.getLevel(), world.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null);
                int randomCheck = world.random.nextInt(3);
                blazeGuardianEntity.moveTo(pos.north(world.random.nextInt(3) - 1).east(randomCheck - 1 == 0 ? -1 : randomCheck), world.random.nextFloat() * 360F, 0.0F);
                world.addFreshEntity(blazeGuardianEntity);
                spawnDelay = 600;
                this.spawnGuardian = false;
            } else if (this.nextSpawnData != null && this.nextSpawnData.getEntityToSpawn() != null) {
                Optional<EntityType<?>> optionalEntityType = EntityType.by(this.nextSpawnData.getEntityToSpawn());
                if (optionalEntityType.isPresent() && optionalEntityType.get().equals(EntityType.BLAZE)
                        && !world.getEntitiesOfClass(BlazeGuardianEntity.class, new AABB(pos).inflate(16D), EntitySelector.NO_SPECTATORS).isEmpty()) {
                    spawnDelay = 600;
                }
            }
        }
    }
}
