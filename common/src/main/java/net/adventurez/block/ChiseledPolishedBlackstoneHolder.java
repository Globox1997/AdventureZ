package net.adventurez.block;

import java.util.List;

import net.adventurez.init.BlockInit;
import net.adventurez.init.ConfigInit;
import org.jetbrains.annotations.Nullable;

import net.adventurez.block.entity.ChiseledPolishedBlackstoneHolderEntity;
import net.adventurez.init.TagInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Prediction;

public class ChiseledPolishedBlackstoneHolder extends Block implements EntityBlock {
    private static final VoxelShape SHAPE;

    public ChiseledPolishedBlackstoneHolder(Properties settings) {
        super(settings);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChiseledPolishedBlackstoneHolderEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY,
                world.isClientSide() ? ChiseledPolishedBlackstoneHolderEntity::clientTick : ChiseledPolishedBlackstoneHolderEntity::serverTick);
    }


    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack itemStack = player.getMainHandItem();
        Container blockEntity = (Container) world.getBlockEntity(pos);
        ItemStack blockStack = blockEntity.getItem(0);
        if (blockStack.isEmpty()) {
            if ((itemStack.is(TagInit.HOLDER_ITEMS) || ConfigInit.CONFIG.allow_all_items_on_holder) && world.getBlockState(pos.above()).isAir()) {
                if (!world.isClientSide()) {
                    blockEntity.setItem(0, itemStack);
                    itemStack.consume(1, player);
                }
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        } else {
            if (!world.isClientSide() && !player.getInventory().add(blockStack)) {
                player.drop(blockStack, false, Prediction.SERVER_ONLY);
            }

            blockEntity.clearContent();
            return InteractionResult.SUCCESS;
        }

    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean moved) {
        Containers.updateNeighboursAfterDestroy(state, world, pos);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    static {
        SHAPE = Shapes.or(box(0D, 0D, 0D, 16D, 14D, 16D), box(0D, 14D, 0D, 16D, 16D, 3D), box(0D, 14D, 13D, 16D, 16D, 16D),
                box(13D, 14D, 3D, 16D, 16D, 13D), box(0D, 14D, 3D, 3D, 16D, 13D));
    }

}
