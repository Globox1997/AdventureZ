package net.adventurez.mixin;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.BlazeGuardianEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

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
        if (type != null && type.equals(EntityTypes.BLAZE)) {
            this.spawnGuardian = true;
        }
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void readNbtMixin(@Nullable Level world, BlockPos pos, ValueInput nbt, CallbackInfo info) {
        this.spawnGuardian = nbt.getBooleanOr("SpawnGuardian", false);
    }

    @Inject(method = "save", at = @At("HEAD"))
    private void writeNbtMixin(ValueOutput nbt, CallbackInfo info) {
        nbt.putBoolean("SpawnGuardian", this.spawnGuardian);
    }

    @Inject(method = "serverTick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/BaseSpawner;spawnDelay:I", ordinal = 1))
    private void serverTick(ServerLevel world, BlockPos pos, CallbackInfo info) {
        if (this.spawnDelay == 2 && ConfigInit.CONFIG.allow_guardian_spawner_spawn) {
            if (this.spawnGuardian) {
                BlazeGuardianEntity blazeGuardianEntity = EntityInit.BLAZE_GUARDIAN.create(world.getLevel(), EntitySpawnReason.MOB_SUMMONED);
                blazeGuardianEntity.finalizeSpawn(world.getLevel(), world.getCurrentDifficultyAt(pos), EntitySpawnReason.STRUCTURE, null);
                int randomCheck = world.getRandom().nextInt(3);
                blazeGuardianEntity.snapTo(pos.north(world.getRandom().nextInt(3) - 1).east(randomCheck - 1 == 0 ? -1 : randomCheck), world.getRandom().nextFloat() * 360F, 0.0F);
                world.addFreshEntity(blazeGuardianEntity);
                spawnDelay = 600;
                this.spawnGuardian = false;
            } else if (this.nextSpawnData != null && this.nextSpawnData.getEntityToSpawn() != null) {
                Optional<EntityType<?>> optionalEntityType = this.nextSpawnData.getEntityToSpawn().getString("id").flatMap(id -> BuiltInRegistries.ENTITY_TYPE.getOptional(Identifier.tryParse(id)));
                if (optionalEntityType.isPresent() && optionalEntityType.get().equals(EntityTypes.BLAZE)
                        && !world.getEntitiesOfClass(BlazeGuardianEntity.class, new AABB(pos).inflate(16D), EntitySelector.NO_SPECTATORS).isEmpty()) {
                    spawnDelay = 600;
                }
            }
        }
    }
}
