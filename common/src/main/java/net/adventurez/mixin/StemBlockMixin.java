package net.adventurez.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.adventurez.entity.DubiousPumpkinEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.world.entity.EntitySpawnReason;

@Mixin(StemBlock.class)
public class StemBlockMixin {

    @WrapOperation(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0))
    private boolean randomTickMixin(ServerLevel instance, BlockPos blockPos, BlockState blockState, Operation<Boolean> original) {
        if (blockState.is(Blocks.PUMPKIN) && instance.getRandom().nextFloat() < ((float) ConfigInit.CONFIG.dubious_pumpkin_spawn_chance / 100f)) {
            DubiousPumpkinEntity dubiousPumpkinEntity = EntityInit.DUBIOUS_PUMPKIN.create(instance, EntitySpawnReason.MOB_SUMMONED);
            dubiousPumpkinEntity.snapTo(blockPos, instance.getRandom().nextFloat() * 360F, 0.0F);
            instance.addFreshEntity(dubiousPumpkinEntity);
            return false;
        } else {
            return original.call(instance, blockPos, blockState);
        }
    }

}
