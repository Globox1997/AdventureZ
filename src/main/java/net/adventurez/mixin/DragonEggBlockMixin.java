package net.adventurez.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.block.entity.DragonEggEntity;
import net.adventurez.init.BlockInit;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.voidz.init.DimensionInit;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.block.entity.BlockEntity;

@Mixin(DragonEggBlock.class)
public class DragonEggBlockMixin implements BlockEntityProvider {

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DragonEggEntity(pos, state);
    }

    @Inject(method = "onUse", at = @At(value = "HEAD"), cancellable = true)
    private void onUseMixin(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> info) {
        if (!world.isClient && FabricLoader.getInstance().isModLoaded("voidz") && world.getRegistryKey() == DimensionInit.VOID_WORLD) {
            world.removeBlock(pos, false);
            ItemStack itemStack = new ItemStack(Items.DRAGON_EGG);
            if (!player.getInventory().insertStack(itemStack)) {
                player.dropItem(itemStack, false);
            }
            info.setReturnValue(ActionResult.success(world.isClient));
        }
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