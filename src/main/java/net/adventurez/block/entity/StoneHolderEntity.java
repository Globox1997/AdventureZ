package net.adventurez.block.entity;

import net.adventurez.entity.StoneGolemEntity;
import net.adventurez.init.BlockInit;
import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.adventurez.init.TagInit;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.block.enums.StairShape;
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
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.util.Tickable;

public class StoneHolderEntity extends BlockEntity implements Tickable, Inventory, BlockEntityClientSerializable {
  private DefaultedList<ItemStack> inventory;
  private boolean startBuildingGolem = false;
  private int buildGolemCounter = 0;
  private int tickCounter = 0;

  public StoneHolderEntity() {
    super(BlockInit.STONE_HOLDER_ENTITY);
    this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
  }

  @Override
  public void fromTag(BlockState state, CompoundTag tag) {
    super.fromTag(state, tag);
    inventory.clear();
    Inventories.fromTag(tag, inventory);
    buildGolemCounter = tag.getInt("buildcounter");
    tickCounter = tag.getInt("tickcounter");
    startBuildingGolem = tag.getBoolean("startbuilding");
  }

  @Override
  public CompoundTag toTag(CompoundTag tag) {
    super.toTag(tag);
    Inventories.toTag(tag, inventory);
    tag.putInt("buildcounter", buildGolemCounter);
    tag.putInt("tickcounter", tickCounter);
    tag.putBoolean("startbuilding", startBuildingGolem);
    return tag;
  }

  public boolean isValid(World world, BlockPos pos, BlockState state) {
    int stoneCounter;
    int stoneCounter2;
    int stoneCounter3 = 0;
    for (stoneCounter = 1; stoneCounter < 10; stoneCounter++) {
      for (stoneCounter2 = -4; stoneCounter2 < 5; stoneCounter2++) {
        BlockState stoneState = world.getBlockState(pos.north(stoneCounter).east(stoneCounter2));
        if (stoneState.getBlock().isIn(TagInit.PLATFORM_NETHER_BLOCKS)) {
          stoneCounter3++;
        }
      }
    }
    if (stoneCounter3 == 81) {
      return true;
    }
    return false;
  }

  @Override
  public void tick() {
    if (!this.isEmpty()) {
      this.tickCounter++;
      if (tickCounter > 40) {
        this.update();
        tickCounter = 0;
      }
    }
    if (startBuildingGolem == true) {
      this.buildStructure();
    }
  }

  public void update() {
    BlockState state = this.getCachedState();
    BlockPos secondHolderPos = pos.north(10);
    BlockPos thirdHolderPos = pos.east(5).north(5);
    BlockPos fourthHolderPos = pos.west(5).north(5);
    BlockState north = world.getBlockState(pos.north(10));
    BlockState east = world.getBlockState(pos.east(5).north(5));
    BlockState west = world.getBlockState(pos.west(5).north(5));
    if (north.getBlock() == BlockInit.STONE_HOLDER_BLOCK && east.getBlock() == BlockInit.STONE_HOLDER_BLOCK
        && west.getBlock() == BlockInit.STONE_HOLDER_BLOCK) {
      if (world.isClient) {
        // Visuals?
      }
      if (!this.isEmpty() && !BlockInit.STONE_HOLDER_ENTITY.get(world, secondHolderPos).isEmpty()
          && !BlockInit.STONE_HOLDER_ENTITY.get(world, thirdHolderPos).isEmpty()
          && !BlockInit.STONE_HOLDER_ENTITY.get(world, fourthHolderPos).isEmpty()) {
        if (this.isValid(world, pos, state)) {
          BlockInit.STONE_HOLDER_ENTITY.get(world, pos.north(10)).clear();
          BlockInit.STONE_HOLDER_ENTITY.get(world, pos.east(5).north(5)).clear();
          BlockInit.STONE_HOLDER_ENTITY.get(world, pos.west(5).north(5)).clear();
          this.clear();
          this.markDirty();
          startBuildingGolem = true;
          if (world.isClient) {
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

  }

  private void buildStructure() {
    buildGolemCounter++;
    if (!world.isClient) {
      // First Layer
      if (buildGolemCounter == 30) {
        world.setBlockState(pos.up().north(2).east(2), Blocks.BLACKSTONE_STAIRS.getDefaultState(), 3);
        world.setBlockState(pos.up().north(2).west(2), Blocks.BLACKSTONE_STAIRS.getDefaultState(), 3);
        world.setBlockState(pos.up().north(3).east(2), Blocks.BLACKSTONE_STAIRS.getDefaultState()
            .with(StairsBlock.HALF, BlockHalf.TOP).with(StairsBlock.FACING, Direction.SOUTH), 3);
        world.setBlockState(pos.up().north(3).west(2), Blocks.BLACKSTONE_STAIRS.getDefaultState()
            .with(StairsBlock.HALF, BlockHalf.TOP).with(StairsBlock.FACING, Direction.SOUTH), 3);
        world.setBlockState(pos.up().north(7).east(), Blocks.BLACKSTONE.getDefaultState(), 3);
        world.setBlockState(pos.up().north(7).west(), Blocks.BLACKSTONE.getDefaultState(), 3);
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1F, 1F);
      }
      // Second Layer
      if (buildGolemCounter == 60) {
        world.setBlockState(pos.up(2).north(3).east(2), Blocks.BLACKSTONE_STAIRS.getDefaultState(), 3);
        world.setBlockState(pos.up(2).north(3).west(2), Blocks.BLACKSTONE_STAIRS.getDefaultState(), 3);
        world.setBlockState(pos.up(2).north(4).east(2), Blocks.BLACKSTONE_STAIRS.getDefaultState()
            .with(StairsBlock.HALF, BlockHalf.TOP).with(StairsBlock.FACING, Direction.SOUTH), 3);
        world.setBlockState(pos.up(2).north(4).west(2), Blocks.BLACKSTONE_STAIRS.getDefaultState()
            .with(StairsBlock.HALF, BlockHalf.TOP).with(StairsBlock.FACING, Direction.SOUTH), 3);
        world.setBlockState(pos.up(2).north(6).east(),
            Blocks.BLACKSTONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 3);
        world.setBlockState(pos.up(2).north(6).west(),
            Blocks.BLACKSTONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 3);
        world.setBlockState(pos.up(2).north(7).east(), Blocks.BLACKSTONE.getDefaultState(), 3);
        world.setBlockState(pos.up(2).north(7).west(), Blocks.BLACKSTONE.getDefaultState(), 3);
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1F, 1F);
      }
      // Third Layer
      if (buildGolemCounter == 90) {
        world.setBlockState(pos.up(3).north(4).east(2), Blocks.BLACKSTONE_STAIRS.getDefaultState(), 3);
        world.setBlockState(pos.up(3).north(4).west(2), Blocks.BLACKSTONE_STAIRS.getDefaultState(), 3);
        world.setBlockState(pos.up(3).north(5).east(2), Blocks.BLACKSTONE.getDefaultState(), 3);
        world.setBlockState(pos.up(3).north(5).west(2), Blocks.BLACKSTONE.getDefaultState(), 3);
        world.setBlockState(pos.up(3).north(6).east(2), Blocks.BLACKSTONE.getDefaultState(), 3);
        world.setBlockState(pos.up(3).north(6).west(2), Blocks.BLACKSTONE.getDefaultState(), 3);
        world.setBlockState(pos.up(3).north(7).east(),
            Blocks.BLACKSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH), 3);
        world.setBlockState(pos.up(3).north(7).west(),
            Blocks.BLACKSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH), 3);
        for (int i = -1; i < 2; i++) {
          world.setBlockState(pos.up(3).north(5).east(i), Blocks.BLACKSTONE.getDefaultState(), 3);
          world.setBlockState(pos.up(3).north(6).east(i), Blocks.BLACKSTONE.getDefaultState(), 3);
        }
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1F, 1F);
      }
      // Fourth Layer
      if (buildGolemCounter == 120) {
        world.setBlockState(pos.up(4).north(5).east(2),
            Blocks.BLACKSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST), 3);
        world.setBlockState(pos.up(4).north(5).west(2),
            Blocks.BLACKSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST), 3);
        world.setBlockState(pos.up(4).north(6).east(2), Blocks.BLACKSTONE_STAIRS.getDefaultState()
            .with(StairsBlock.FACING, Direction.WEST).with(StairsBlock.SHAPE, StairShape.OUTER_LEFT), 3);
        world.setBlockState(pos.up(4).north(6).west(2), Blocks.BLACKSTONE_STAIRS.getDefaultState()
            .with(StairsBlock.FACING, Direction.EAST).with(StairsBlock.SHAPE, StairShape.OUTER_RIGHT), 3);
        for (int i = -1; i < 2; i++) {
          world.setBlockState(pos.up(4).north(5).east(i), Blocks.BLACKSTONE.getDefaultState(), 3);
          world.setBlockState(pos.up(4).north(6).east(i),
              Blocks.BLACKSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH), 3);
        }
        world.setBlockState(pos.up(4).north(4), Blocks.BLACKSTONE.getDefaultState(), 3);
        world.setBlockState(pos.up(5).north(4), Blocks.BLACKSTONE_SLAB.getDefaultState(), 3);
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1F, 1F);
      }
      if (buildGolemCounter >= 150) {
        for (int i = 1; i < 6; i++) {
          for (int o = -4; o < 6; o++) {
            for (int u = 1; u < 10; u++) {
              world.breakBlock(pos.up(i).east(o).north(u), false);
            }
          }
        }
        StoneGolemEntity stoneGolemEntity = (StoneGolemEntity) EntityInit.STONEGOLEM_ENTITY.create(world);
        BlockPos spawnPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ() - 5);
        stoneGolemEntity.refreshPositionAndAngles(spawnPos, 0.0F, 0.0F);
        stoneGolemEntity.sendtoEntity();
        world.spawnEntity(stoneGolemEntity);
        world.playSound(null, pos, SoundInit.GOLEM_SPAWN_EVENT, SoundCategory.HOSTILE, 1F, 1F);
      }
    } else {
      double d = (double) pos.getX() + (double) world.random.nextFloat();
      double e = (double) pos.getY() + (double) world.random.nextFloat() + 1D;
      double f = (double) pos.getZ() + (double) world.random.nextFloat();
      if (buildGolemCounter > 0 && buildGolemCounter < 26) {
        world.addParticle(ParticleTypes.SMOKE, d + 2, e, f - 2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 2, e, f - 2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d + 2, e, f - 3, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 2, e, f - 3, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d + 1, e, f - 7, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 1, e, f - 7, 0.0D, 0.0D, 0.0D);
      }
      if (buildGolemCounter > 30 && buildGolemCounter < 56) {
        world.addParticle(ParticleTypes.SMOKE, d + 2, e + 1, f - 3, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 2, e + 1, f - 3, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d + 2, e + 1, f - 4, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 2, e + 1, f - 4, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d + 1, e + 1, f - 6, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 1, e + 1, f - 6, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d + 1, e + 1, f - 7, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 1, e + 1, f - 7, 0.0D, 0.0D, 0.0D);
      }
      if (buildGolemCounter > 60 && buildGolemCounter < 86) {
        world.addParticle(ParticleTypes.SMOKE, d + 2, e + 2, f - 4, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 2, e + 2, f - 4, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d + 2, e + 2, f - 5, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 2, e + 2, f - 5, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d + 2, e + 2, f - 6, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 2, e + 2, f - 6, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d + 1, e + 2, f - 7, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 1, e + 2, f - 7, 0.0D, 0.0D, 0.0D);
        for (int i = -1; i < 2; i++) {
          world.addParticle(ParticleTypes.SMOKE, d - i, e + 2, f - 5, 0.0D, 0.0D, 0.0D);
          world.addParticle(ParticleTypes.SMOKE, d - i, e + 2, f - 6, 0.0D, 0.0D, 0.0D);
        }
      }
      if (buildGolemCounter > 90 && buildGolemCounter < 116) {
        world.addParticle(ParticleTypes.SMOKE, d + 2, e + 3, f - 5, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 2, e + 3, f - 5, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d + 2, e + 3, f - 6, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d - 2, e + 3, f - 6, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d, e + 3, f - 4, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d, e + 4, f - 4, 0.0D, 0.0D, 0.0D);
        for (int i = -1; i < 2; i++) {
          world.addParticle(ParticleTypes.SMOKE, d - i, e + 3, f - 5, 0.0D, 0.0D, 0.0D);
          world.addParticle(ParticleTypes.SMOKE, d - i, e + 3, f - 6, 0.0D, 0.0D, 0.0D);
        }
      }
    }

    if (buildGolemCounter >= 150) {
      startBuildingGolem = false;
      buildGolemCounter = 0;
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
    this.markDirty();
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
    this.markDirty();
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
    buildGolemCounter = tag.getInt("buildcounter");
    startBuildingGolem = tag.getBoolean("startbuilding");
  }

  @Override
  public CompoundTag toClientTag(CompoundTag tag) {
    super.toTag(tag);
    Inventories.toTag(tag, inventory);
    tag.putInt("buildcounter", buildGolemCounter);
    tag.putBoolean("startbuilding", startBuildingGolem);
    return tag;
  }
}