package net.adventurez.block;

import java.util.List;

import net.adventurez.init.BlockInit;
import net.adventurez.init.ConfigInit;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

import net.adventurez.block.entity.StoneHolderEntity;
import net.adventurez.init.TagInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class StoneHolderBlock extends Block implements BlockEntityProvider {
    private static final VoxelShape SHAPE;

    public StoneHolderBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StoneHolderEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockInit.STONE_HOLDER_ENTITY, world.isClient ? StoneHolderEntity::clientTick : StoneHolderEntity::serverTick);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(new TranslatableText("item.adventurez.moreinfo.tooltip"));
            if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)) {
                tooltip.remove(new TranslatableText("item.adventurez.moreinfo.tooltip"));
                tooltip.add(new TranslatableText("block.adventurez.stone_holder_block.tooltip"));
                tooltip.add(new TranslatableText("block.adventurez.stone_holder_block.tooltip2"));
                tooltip.add(new TranslatableText("block.adventurez.stone_holder_block.tooltip3"));
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);
        ItemStack blockStack = blockEntity.getStack(0);
        if (blockStack.isEmpty()) {
            if ((itemStack.isIn(TagInit.HOLDER_ITEMS) || ConfigInit.CONFIG.allow_all_items_on_holder) && world.getBlockState(pos.up()).isAir()) {
                if (!world.isClient) {
                    blockEntity.setStack(0, new ItemStack(itemStack.getItem(), 1));
                    if (!player.isCreative())
                        itemStack.decrement(1);
                }
                return ActionResult.success(world.isClient);
            } else
                return ActionResult.CONSUME;
        } else {
            if (!world.isClient)
                if (!player.getInventory().insertStack(blockStack))
                    player.dropItem(blockStack, false);

            blockEntity.clear();
            return ActionResult.success(world.isClient);
        }

    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    static {
        SHAPE = VoxelShapes.union(createCuboidShape(0D, 0D, 0D, 16D, 14D, 16D), createCuboidShape(0D, 14D, 0D, 16D, 16D, 3D), createCuboidShape(0D, 14D, 13D, 16D, 16D, 16D),
                createCuboidShape(13D, 14D, 3D, 16D, 16D, 13D), createCuboidShape(0D, 14D, 3D, 3D, 16D, 13D));
    }

}
