package net.adventurez.block;

import net.adventurez.block.entity.StoneHolderEntity;
import net.adventurez.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class StoneHolderBlock extends Block implements BlockEntityProvider {

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
      if (!heldItem.isEmpty() && heldItem.isItemEqual(new ItemStack(ItemInit.RED_STONE_ITEM))) {
        blockEntity.setStack(0, heldItem.split(1));
        return ActionResult.SUCCESS;
      }
      return ActionResult.FAIL;
    }
  }

}
