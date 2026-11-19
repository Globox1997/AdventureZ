package net.adventurez.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.block.entity.DragonEggEntity;
import net.adventurez.init.BlockInit;
import net.adventurez.init.ConfigInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DragonEggBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@Mixin(DragonEggBlock.class)
public class DragonEggBlockMixin implements EntityBlock {

    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void onUseMixin(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> info) {
        if (ConfigInit.CONFIG.allow_other_dragon_hatching && player.getMainHandItem().is(Items.DRAGON_BREATH) && player.getItemBySlot(EquipmentSlot.HEAD).is(Items.DRAGON_HEAD)) {
            if (!world.isClientSide) {
                player.getMainHandItem().shrink(1);
                if (state.hasBlockEntity()) {
                    ((DragonEggEntity) world.getBlockEntity(pos)).enableEggHatching();
                }
            } else {
                for (int i = 0; i < 20; i++)
                    world.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.getX() + world.random.nextDouble(), pos.getY() + world.random.nextDouble(), pos.getZ() + world.random.nextDouble(), 0.0D, 0.0D,
                            0.0D);
            }
            info.setReturnValue(InteractionResult.sidedSuccess(true));
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DragonEggEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockInit.DRAGON_EGG_ENTITY, world.isClientSide ? DragonEggEntity::clientTick : DragonEggEntity::serverTick);
    }

    @SuppressWarnings("unchecked")
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

}