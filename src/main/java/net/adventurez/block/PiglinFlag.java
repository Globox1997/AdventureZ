package net.adventurez.block;

import net.adventurez.block.entity.PiglinFlagEntity;
import net.adventurez.init.BlockInit;
import net.adventurez.init.ConfigInit;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PiglinFlag extends Block implements EntityBlock {
    public static final DirectionProperty FACING;
    private static final VoxelShape BASE;
    private static final VoxelShape POLE;
    private static final VoxelShape BAR_1;
    private static final VoxelShape BAR_2;
    private static final VoxelShape BAR_3;
    private static final VoxelShape BAR_4;
    private static final VoxelShape NORTH_FLAG;
    private static final VoxelShape EAST_FLAG;
    private static final VoxelShape SOUTH_FLAG;
    private static final VoxelShape WEST_FLAG;

    public PiglinFlag(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PiglinFlagEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockInit.PIGLIN_FLAG_ENTITY, world.isClientSide() ? PiglinFlagEntity::clientTick : PiglinFlagEntity::serverTick);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, context, tooltip, options);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(Component.translatable("item.adventurez.moreinfo.tooltip"));
            if (Screen.hasShiftDown()) {
                tooltip.remove(Component.translatable("item.adventurez.moreinfo.tooltip"));
                tooltip.add(Component.translatable("block.adventurez.piglin_flag.tooltip"));
                tooltip.add(Component.translatable("block.adventurez.piglin_flag.tooltip2"));
                tooltip.add(Component.translatable("block.adventurez.piglin_flag.tooltip3"));
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getClockWise());
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return BASE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    private VoxelShape getShape(BlockState state) {
        Direction direction = state.getValue(FACING);
        if (direction == Direction.NORTH) {
            return NORTH_FLAG;
        } else if (direction == Direction.EAST) {
            return EAST_FLAG;
        } else if (direction == Direction.SOUTH) {
            return SOUTH_FLAG;
        } else if (direction == Direction.WEST) {
            return WEST_FLAG;
        } else {
            return NORTH_FLAG;
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        BASE = box(6, 0, 6, 10, 10, 10);
        POLE = Shapes.or(BASE, box(7, 10, 7, 9, 27, 9));
        BAR_1 = Block.box(6, 27, -10, 10, 32, 10);
        BAR_2 = Block.box(6, 27, 6, 26, 32, 10);
        BAR_3 = Block.box(6, 27, 6, 10, 32, 26);
        BAR_4 = Block.box(-10, 27, 6, 10, 32, 10);
        NORTH_FLAG = Shapes.or(POLE, BAR_1);
        EAST_FLAG = Shapes.or(POLE, BAR_2);
        SOUTH_FLAG = Shapes.or(POLE, BAR_3);
        WEST_FLAG = Shapes.or(POLE, BAR_4);
    }

}