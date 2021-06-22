package net.adventurez.mixin;

import net.adventurez.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.block.entity.DragonEggEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.block.entity.BlockEntity;

@Mixin(DragonEggBlock.class)
public class DragonEggBlockMixin implements BlockEntityProvider {

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DragonEggEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockInit.DRAGON_EGG_ENTITY, world.isClient ? DragonEggEntity::clientTick : DragonEggEntity::serverTick);
    }

    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

}