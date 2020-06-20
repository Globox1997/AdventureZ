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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.Tickable;

public class StoneHolderEntity extends BlockEntity implements Tickable, Inventory, BlockEntityClientSerializable {
  private DefaultedList<ItemStack> inventory;

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
                // System.out.println(countBlocks);
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  @Override
  public void tick() {
    this.update();
  }

  public void update() {
    BlockState state = this.getCachedState();
    BlockPos secondHolderPos = pos.north(10);
    BlockPos thirdHolderPos = pos.east(5).north(5);
    BlockPos fourthHolderPos = pos.west(5).north(5);

    if (this.isValid(world, pos, state)) {
      if (world.isClient) {
        // double d = (double) pos.getX() + (double) world.random.nextFloat();
        // double e = (double) pos.getY() + (double) world.random.nextFloat();
        // double f = (double) pos.getZ() + (double) world.random.nextFloat();
        // double o = (double) pos.getY() + ((double) world.random.nextFloat() + 1);
        // double r = (double) pos.getY() - ((double) world.random.nextFloat());
        // world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
        // world.addParticle(ParticleTypes.SMOKE, d, o, f, 0.0D, 0.0D, 0.0D);
        // world.addParticle(ParticleTypes.SMOKE, d, r, f, 0.0D, 0.0D, 0.0D);
      }
      if (!isEmpty() && !BlockInit.STONE_HOLDER_ENTITY.get(world, secondHolderPos).isEmpty()
          && !BlockInit.STONE_HOLDER_ENTITY.get(world, thirdHolderPos).isEmpty()
          && !BlockInit.STONE_HOLDER_ENTITY.get(world, fourthHolderPos).isEmpty()) {
        BlockInit.STONE_HOLDER_ENTITY.get(world, pos.north(10)).clear();
        BlockInit.STONE_HOLDER_ENTITY.get(world, pos.east(5).north(5)).clear();
        BlockInit.STONE_HOLDER_ENTITY.get(world, pos.west(5).north(5)).clear();
        this.clear();
        if (!world.isClient) {
          StoneGolemEntity stoneGolemEntity = (StoneGolemEntity) EntityInit.STONEGOLEM_ENTITY.create(world);
          BlockPos spawnPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ() - 5);
          stoneGolemEntity.refreshPositionAndAngles(spawnPos, 0.0F, 0.0F);
          world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.HOSTILE, 1F, 1F);
          world.spawnEntity(stoneGolemEntity);
        } else {
          for (int counting = 0; counting < 20; counting++) {
            double d = (double) pos.getX() + (double) world.random.nextFloat();
            double e = (double) pos.getY() + (double) world.random.nextFloat() + 1D;
            double f = (double) pos.getZ() + (double) world.random.nextFloat();

            double d2 = (double) secondHolderPos.getX() + (double) world.random.nextFloat();
            double e2 = (double) secondHolderPos.getY() + (double) world.random.nextFloat() + 1D;
            double f2 = (double) secondHolderPos.getZ() + (double) world.random.nextFloat();

            double d3 = (double) thirdHolderPos.getX() + (double) world.random.nextFloat();
            double e3 = (double) thirdHolderPos.getY() + (double) world.random.nextFloat() + 1D;
            double f3 = (double) thirdHolderPos.getZ() + (double) world.random.nextFloat();

            double d4 = (double) fourthHolderPos.getX() + (double) world.random.nextFloat();
            double e4 = (double) fourthHolderPos.getY() + (double) world.random.nextFloat() + 1D;
            double f4 = (double) fourthHolderPos.getZ() + (double) world.random.nextFloat();

            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.SMOKE, d2, e2, f2, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.SMOKE, d3, e3, f3, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.SMOKE, d4, e4, f4, 0.0D, 0.0D, 0.0D);

          }
        }
      }
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