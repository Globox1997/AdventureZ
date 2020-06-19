package net.adventurez.block.entity;

import net.adventurez.entity.StoneGolemEntity;
import net.adventurez.init.BlockInit;
import net.adventurez.init.EntityInit;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.Tickable;

public class StoneHolderEntity extends BlockEntity implements Tickable, Inventory, BlockEntityClientSerializable {
  private DefaultedList<ItemStack> inventory;
  private int countBlocks = 0;

  public StoneHolderEntity() {
    super(BlockInit.STONE_HOLDER_ENTITY);
    this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
  }

  @Override
  public void fromTag(BlockState state, CompoundTag tag) {
    super.fromTag(state, tag);
    inventory.clear();
    Inventories.fromTag(tag, inventory);
  }

  @Override
  public CompoundTag toTag(CompoundTag tag) {
    super.toTag(tag);
    Inventories.toTag(tag, inventory);
    return tag;
  }

  public boolean isValid(World world, BlockPos pos, BlockState state) {
    BlockState north = world.getBlockState(pos.north(10));
    BlockState east = world.getBlockState(pos.east(5).north(5));
    BlockState west = world.getBlockState(pos.west(5).north(5));

    if (north.getBlock() == BlockInit.STONE_HOLDER_BLOCK) {
      if (east.getBlock() == BlockInit.STONE_HOLDER_BLOCK) {
        if (west.getBlock() == BlockInit.STONE_HOLDER_BLOCK) {
          int stoneCounter;
          int stoneCounter2;
          for (stoneCounter = 1; stoneCounter < 10; stoneCounter++) {
            for (stoneCounter2 = -5; stoneCounter2 < 4; stoneCounter2++) {
              BlockState stoneState = world.getBlockState(pos.north(stoneCounter).east(stoneCounter2));
              if (stoneState.getBlock() == Blocks.BLACKSTONE || stoneState.getBlock() == Blocks.BLACKSTONE_SLAB
                  || stoneState.getBlock() == Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
                  || stoneState.getBlock() == Blocks.POLISHED_BLACKSTONE_BRICKS
                  || stoneState.getBlock() == Blocks.POLISHED_BLACKSTONE_BRICK_SLAB
                  || stoneState.getBlock() == Blocks.POLISHED_BLACKSTONE_SLAB
                  || stoneState.getBlock() == Blocks.GILDED_BLACKSTONE) {
                countBlocks++;
                // System.out.println(countBlocks);
                return true;

              }
            }
          }
        }
      }
    }
    // if(countBlocks >= 100){
    // return true;
    // }else
    return false;
  }

  @Override
  public void tick() {
    this.update();
  }

  public void update() {
    if (!isEmpty()) {
      BlockState state = this.getCachedState();
      if (this.isValid(world, pos, state)) {
        this.clear();
        if (!world.isClient) {
          StoneGolemEntity stoneGolemEntity = (StoneGolemEntity) EntityInit.STONEGOLEM_ENTITY.create(world);
          BlockPos spawnPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ() - 5);
          stoneGolemEntity.refreshPositionAndAngles(spawnPos, 0.0F, 0.0F);
          // world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH,
          // SoundCategory.HOSTILE, 1F, 1F);
          world.spawnEntity(stoneGolemEntity);
          // this.items.clear();
          // this.clear();
        } else {
          double d = (double) pos.getX() + (double) world.random.nextFloat();
          double e = (double) pos.getY() + (double) world.random.nextFloat();
          double f = (double) pos.getZ() + (double) world.random.nextFloat();
          double o = (double) pos.getY() + ((double) world.random.nextFloat() + 1);
          double r = (double) pos.getY() - ((double) world.random.nextFloat());
          world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
          world.addParticle(ParticleTypes.SMOKE, d, o, f, 0.0D, 0.0D, 0.0D);
          world.addParticle(ParticleTypes.SMOKE, d, r, f, 0.0D, 0.0D, 0.0D);
        }

      }
      // ++processTime;
      // if (processTime >= dryingTime) {
      // this.items.clear();
      // setStack(0, new ItemStack(Items.LEATHER));
      // processTime = 0;
      // }
    }
  }

  @Override
  public void markDirty() {
    super.markDirty();
    sendUpdate();
  }

  private void sendUpdate() {
    if (this.world != null) {
      BlockState state = this.world.getBlockState(this.pos);
      (this.world).updateListeners(this.pos, state, state, 3);
    }
  }

  @Override
  public void clear() {
    this.inventory.clear();
  }

  @Override
  public int size() {
    return 1;
  }

  @Override
  public boolean isEmpty() {
    return this.getStack(0).isEmpty();
  }

  @Override
  public ItemStack getStack(int slot) {
    return this.inventory.get(0);
  }

  @Override
  public ItemStack removeStack(int slot, int amount) {
    ItemStack result = Inventories.splitStack(this.inventory, slot, 1);
    if (!result.isEmpty()) {
      markDirty();
    }
    return result;
  }

  @Override
  public ItemStack removeStack(int slot) {
    this.markDirty();
    return Inventories.removeStack(this.inventory, slot);
  }

  @Override
  public void setStack(int slot, ItemStack stack) {
    this.inventory.set(0, stack);
    this.markDirty();
  }

  @Override
  public boolean canPlayerUse(PlayerEntity player) {
    return true;
  }

  @Override
  public void fromClientTag(CompoundTag tag) {
    inventory.clear();
    Inventories.fromTag(tag, inventory);

  }

  @Override
  public CompoundTag toClientTag(CompoundTag tag) {
    super.toTag(tag);
    Inventories.toTag(tag, inventory);
    return tag;
  }
}