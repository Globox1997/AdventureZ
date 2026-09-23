package net.adventurez.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.adventurez.entity.DubiousPumpkinEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.StemBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StemBlock.class)
public class StemBlockMixin {

    @WrapOperation(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z", ordinal = 0))
    private boolean randomTickMixin(ServerWorld instance, BlockPos blockPos, BlockState blockState, Operation<Boolean> original) {
        if (instance.getRandom().nextFloat() < ((float) ConfigInit.CONFIG.dubious_pumpkin_spawn_chance / 100f)) {
            DubiousPumpkinEntity dubiousPumpkinEntity = EntityInit.DUBIOUS_PUMPKIN.create(instance);
            dubiousPumpkinEntity.refreshPositionAndAngles(blockPos, instance.getRandom().nextFloat() * 360F, 0.0F);
            instance.spawnEntity(dubiousPumpkinEntity);
            return false;
        } else {
            return original.call(instance, blockPos, blockState);
        }
    }

}
