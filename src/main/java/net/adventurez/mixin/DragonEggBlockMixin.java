package net.adventurez.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.block.entity.DragonEggEntity;
import net.adventurez.init.BlockInit;
import net.adventurez.init.ConfigInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.block.entity.BlockEntity;

@Mixin(DragonEggBlock.class)
public class DragonEggBlockMixin implements BlockEntityProvider {

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void onUseMixin(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> info) {
        if (ConfigInit.CONFIG.allow_other_dragon_hatching && player.getStackInHand(hand).isOf(Items.DRAGON_BREATH) && player.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.DRAGON_HEAD)) {
            if (!world.isClient) {
                player.getStackInHand(hand).decrement(1);
                if (state.hasBlockEntity()) {
                    ((DragonEggEntity) world.getBlockEntity(pos)).enableEggHatching();
                }
            } else {
                for (int i = 0; i < 20; i++)
                    world.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.getX() + world.random.nextDouble(), pos.getY() + world.random.nextDouble(), pos.getZ() + world.random.nextDouble(), 0.0D, 0.0D,
                            0.0D);
            }
            info.setReturnValue(ActionResult.success(true));
        }
    }

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