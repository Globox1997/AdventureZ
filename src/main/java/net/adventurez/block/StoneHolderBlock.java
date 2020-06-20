package net.adventurez.block;

import net.adventurez.block.entity.StoneHolderEntity;
import net.adventurez.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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
  public BlockEntity createBlockEntity(BlockView view) {

    return new StoneHolderEntity();
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
      BlockHitResult hit) {
    Inventory blockEntity = (Inventory) world.getBlockEntity(pos);
    ItemStack stack = blockEntity.getStack(0);

    if (!stack.isEmpty()) {
      player.giveItemStack(stack);
      blockEntity.clear();
      return ActionResult.SUCCESS;
    } else {
      ItemStack heldItem = player.getMainHandStack();
      if (heldItem.isItemEqual(new ItemStack(ItemInit.GILDED_STONE_ITEM))) {
        if (!world.isClient) {
          blockEntity.setStack(0, heldItem.split(1));
          return ActionResult.SUCCESS;
        } else {
          return ActionResult.SUCCESS;
        }

      }
    }
    return ActionResult.PASS;
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPE;
  }

  static {
    SHAPE = VoxelShapes.union(createCuboidShape(0D, 0D, 0D, 16D, 14D, 16D),
        createCuboidShape(0D, 14D, 0D, 16D, 16D, 3D), createCuboidShape(0D, 14D, 13D, 16D, 16D, 16D),
        createCuboidShape(13D, 14D, 3D, 16D, 16D, 13D), createCuboidShape(0D, 14D, 3D, 3D, 16D, 13D));
  }

}
