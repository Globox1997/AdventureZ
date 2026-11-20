package net.adventurez.block.entity;

import net.adventurez.entity.BlackstoneGolemEntity;
import net.adventurez.init.BlockInit;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.adventurez.init.ItemInit;
import net.adventurez.init.SoundInit;
import net.adventurez.init.TagInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class ChiseledPolishedBlackstoneHolderEntity extends BlockEntity implements Container {
    private NonNullList<ItemStack> inventory;
    private boolean startBuildingGolem = false;
    private int buildGolemCounter = 0;
    private int tickCounter = 0;

    public ChiseledPolishedBlackstoneHolderEntity(BlockPos pos, BlockState state) {
        super(BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY, pos, state);
        this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    }

    @Override
    protected void loadAdditional(ValueInput nbt) {
        super.loadAdditional(nbt);
        inventory.clear();
        ContainerHelper.loadAllItems(nbt, inventory);
        buildGolemCounter = nbt.getIntOr("buildcounter", 0);
        tickCounter = nbt.getIntOr("tickcounter", 0);
        startBuildingGolem = nbt.getBooleanOr("startbuilding", false);
    }

    @Override
    protected void saveAdditional(ValueOutput nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, inventory);
        nbt.putInt("buildcounter", buildGolemCounter);
        nbt.putInt("tickcounter", tickCounter);
        nbt.putBoolean("startbuilding", startBuildingGolem);
    }

    public static void clientTick(Level world, BlockPos pos, BlockState state, ChiseledPolishedBlackstoneHolderEntity blockEntity) {
        blockEntity.tick();
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, ChiseledPolishedBlackstoneHolderEntity blockEntity) {
        blockEntity.tick();
    }

    public boolean isValid(Level world, BlockPos pos, BlockState state) {
        int stoneCounter;
        int stoneCounter2;
        int stoneCounter3 = 0;
        for (stoneCounter = 1; stoneCounter < 10; stoneCounter++) {
            for (stoneCounter2 = -4; stoneCounter2 < 5; stoneCounter2++) {
                BlockState stoneState = this.getLevel().getBlockState(pos.north(stoneCounter).east(stoneCounter2));
                if (stoneState.is(TagInit.PLATFORM_NETHER_BLOCKS)) {
                    stoneCounter3++;
                }
            }
        }
        if (stoneCounter3 == 81) {
            return true;
        }
        return false;
    }

    public void tick() {
        if (!this.isEmpty()) {
            if (!this.getLevel().getBlockState(worldPosition.above()).isAir()) {
                if (!this.getLevel().isClientSide()) {
                    Containers.dropContents(level, worldPosition, inventory);
                    inventory.clear();
                }
                tickCounter = -1;
            } else if (ConfigInit.CONFIG.allow_blackstone_golem_summoning) {
                this.tickCounter++;
                if (tickCounter > 40 && this.getItem(0).is(ItemInit.GILDED_BLACKSTONE_SHARD) && this.getLevel().dimension() == Level.NETHER) {
                    this.update();
                    tickCounter = 0;
                }
            }
        }
        if (startBuildingGolem) {
            this.buildStructure();
        }
    }

    private void update() {
        BlockState state = this.getBlockState();
        BlockPos secondHolderPos = this.getBlockPos().north(10);
        BlockPos thirdHolderPos = this.getBlockPos().east(5).north(5);
        BlockPos fourthHolderPos = this.getBlockPos().west(5).north(5);
        BlockState north = this.getLevel().getBlockState(this.getBlockPos().north(10));
        BlockState east = this.getLevel().getBlockState(this.getBlockPos().east(5).north(5));
        BlockState west = this.getLevel().getBlockState(this.getBlockPos().west(5).north(5));
        if (north.getBlock() == BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER && east.getBlock() == BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER
                && west.getBlock() == BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER) {
            if (this.getLevel().isClientSide()) {
                // Visuals?
            }
            if (!this.isEmpty() && !BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY.getBlockEntity(level, secondHolderPos).isEmpty()
                    && !BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY.getBlockEntity(level, thirdHolderPos).isEmpty()
                    && !BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY.getBlockEntity(level, fourthHolderPos).isEmpty()) {
                if (this.isValid(this.getLevel(), this.getBlockPos(), state)) {
                    BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY.getBlockEntity(level, this.getBlockPos().north(10)).clearContent();
                    BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY.getBlockEntity(level, this.getBlockPos().east(5).north(5)).clearContent();
                    BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY.getBlockEntity(level, this.getBlockPos().west(5).north(5)).clearContent();
                    this.clearContent();
                    this.setChanged();
                    startBuildingGolem = true;
                    if (this.getLevel().isClientSide()) {
                        for (int counting = 0; counting < 20; counting++) {
                            double d = (double) this.getBlockPos().getX() + (double) this.getLevel().getRandom().nextFloat();
                            double e = (double) this.getBlockPos().getY() + (double) this.getLevel().getRandom().nextFloat() + 1D;
                            double f = (double) this.getBlockPos().getZ() + (double) this.getLevel().getRandom().nextFloat();

                            double d2 = (double) secondHolderPos.getX() + (double) this.getLevel().getRandom().nextFloat();
                            double e2 = (double) secondHolderPos.getY() + (double) this.getLevel().getRandom().nextFloat() + 1D;
                            double f2 = (double) secondHolderPos.getZ() + (double) this.getLevel().getRandom().nextFloat();

                            double d3 = (double) thirdHolderPos.getX() + (double) this.getLevel().getRandom().nextFloat();
                            double e3 = (double) thirdHolderPos.getY() + (double) this.getLevel().getRandom().nextFloat() + 1D;
                            double f3 = (double) thirdHolderPos.getZ() + (double) this.getLevel().getRandom().nextFloat();

                            double d4 = (double) fourthHolderPos.getX() + (double) this.getLevel().getRandom().nextFloat();
                            double e4 = (double) fourthHolderPos.getY() + (double) this.getLevel().getRandom().nextFloat() + 1D;
                            double f4 = (double) fourthHolderPos.getZ() + (double) this.getLevel().getRandom().nextFloat();

                            this.getLevel().addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
                            this.getLevel().addParticle(ParticleTypes.SMOKE, d2, e2, f2, 0.0D, 0.0D, 0.0D);
                            this.getLevel().addParticle(ParticleTypes.SMOKE, d3, e3, f3, 0.0D, 0.0D, 0.0D);
                            this.getLevel().addParticle(ParticleTypes.SMOKE, d4, e4, f4, 0.0D, 0.0D, 0.0D);

                        }
                    }
                }
            }
        }

    }

    private void buildStructure() {
        buildGolemCounter++;
        if (!this.getLevel().isClientSide()) {
            // First Layer
            if (buildGolemCounter == 30) {
                this.getLevel().setBlock(this.getBlockPos().above().north(2).east(2), Blocks.BLACKSTONE_STAIRS.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above().north(2).west(2), Blocks.BLACKSTONE_STAIRS.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above().north(3).east(2),
                        Blocks.BLACKSTONE_STAIRS.defaultBlockState().setValue(StairBlock.HALF, Half.TOP).setValue(StairBlock.FACING, Direction.SOUTH), 3);
                this.getLevel().setBlock(this.getBlockPos().above().north(3).west(2),
                        Blocks.BLACKSTONE_STAIRS.defaultBlockState().setValue(StairBlock.HALF, Half.TOP).setValue(StairBlock.FACING, Direction.SOUTH), 3);
                this.getLevel().setBlock(this.getBlockPos().above().north(7).east(), Blocks.BLACKSTONE.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above().north(7).west(), Blocks.BLACKSTONE.defaultBlockState(), 3);
                this.getLevel().playSound(null, this.getBlockPos(), SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1F, 1F);
            }
            // Second Layer
            if (buildGolemCounter == 60) {
                this.getLevel().setBlock(this.getBlockPos().above(2).north(3).east(2), Blocks.BLACKSTONE_STAIRS.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above(2).north(3).west(2), Blocks.BLACKSTONE_STAIRS.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above(2).north(4).east(2),
                        Blocks.BLACKSTONE_STAIRS.defaultBlockState().setValue(StairBlock.HALF, Half.TOP).setValue(StairBlock.FACING, Direction.SOUTH), 3);
                this.getLevel().setBlock(this.getBlockPos().above(2).north(4).west(2),
                        Blocks.BLACKSTONE_STAIRS.defaultBlockState().setValue(StairBlock.HALF, Half.TOP).setValue(StairBlock.FACING, Direction.SOUTH), 3);
                this.getLevel().setBlock(this.getBlockPos().above(2).north(6).east(), Blocks.BLACKSTONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP), 3);
                this.getLevel().setBlock(this.getBlockPos().above(2).north(6).west(), Blocks.BLACKSTONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP), 3);
                this.getLevel().setBlock(this.getBlockPos().above(2).north(7).east(), Blocks.BLACKSTONE.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above(2).north(7).west(), Blocks.BLACKSTONE.defaultBlockState(), 3);
                this.getLevel().playSound(null, this.getBlockPos(), SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1F, 1F);
            }
            // Third Layer
            if (buildGolemCounter == 90) {
                this.getLevel().setBlock(this.getBlockPos().above(3).north(4).east(2), Blocks.BLACKSTONE_STAIRS.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above(3).north(4).west(2), Blocks.BLACKSTONE_STAIRS.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above(3).north(5).east(2), Blocks.BLACKSTONE.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above(3).north(5).west(2), Blocks.BLACKSTONE.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above(3).north(6).east(2), Blocks.BLACKSTONE.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above(3).north(6).west(2), Blocks.BLACKSTONE.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above(3).north(7).east(), Blocks.BLACKSTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH), 3);
                this.getLevel().setBlock(this.getBlockPos().above(3).north(7).west(), Blocks.BLACKSTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH), 3);
                for (int i = -1; i < 2; i++) {
                    this.getLevel().setBlock(this.getBlockPos().above(3).north(5).east(i), Blocks.BLACKSTONE.defaultBlockState(), 3);
                    this.getLevel().setBlock(this.getBlockPos().above(3).north(6).east(i), Blocks.BLACKSTONE.defaultBlockState(), 3);
                }
                this.getLevel().playSound(null, this.getBlockPos(), SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1F, 1F);
            }
            // Fourth Layer
            if (buildGolemCounter == 120) {
                this.getLevel().setBlock(this.getBlockPos().above(4).north(5).east(2), Blocks.BLACKSTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST), 3);
                this.getLevel().setBlock(this.getBlockPos().above(4).north(5).west(2), Blocks.BLACKSTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST), 3);
                this.getLevel().setBlock(this.getBlockPos().above(4).north(6).east(2),
                        Blocks.BLACKSTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.SHAPE, StairsShape.OUTER_LEFT), 3);
                this.getLevel().setBlock(this.getBlockPos().above(4).north(6).west(2),
                        Blocks.BLACKSTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.SHAPE, StairsShape.OUTER_RIGHT), 3);
                for (int i = -1; i < 2; i++) {
                    this.getLevel().setBlock(this.getBlockPos().above(4).north(5).east(i), Blocks.BLACKSTONE.defaultBlockState(), 3);
                    this.getLevel().setBlock(this.getBlockPos().above(4).north(6).east(i), Blocks.BLACKSTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH), 3);
                }
                this.getLevel().setBlock(this.getBlockPos().above(4).north(4), Blocks.BLACKSTONE.defaultBlockState(), 3);
                this.getLevel().setBlock(this.getBlockPos().above(5).north(4), Blocks.BLACKSTONE_SLAB.defaultBlockState(), 3);
                this.getLevel().playSound(null, this.getBlockPos(), SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1F, 1F);
            }
            if (buildGolemCounter >= 150) {
                for (int i = 1; i < 6; i++) {
                    for (int o = -4; o < 6; o++) {
                        for (int u = 1; u < 10; u++) {
                            this.getLevel().destroyBlock(this.getBlockPos().above(i).east(o).north(u), false);
                        }
                    }
                }
                BlackstoneGolemEntity stoneGolemEntity = EntityInit.BLACKSTONE_GOLEM.create(level, EntitySpawnReason.MOB_SUMMONED);
                BlockPos spawnPos = new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY() + 1, this.getBlockPos().getZ() - 5);
                stoneGolemEntity.snapTo(spawnPos, 0.0F, 0.0F);
                stoneGolemEntity.finalizeSpawn(((ServerLevel) this.getLevel()), ((ServerLevel) this.getLevel()).getCurrentDifficultyAt(this.getBlockPos()), EntitySpawnReason.STRUCTURE, null);
                stoneGolemEntity.sendtoEntity();
                this.getLevel().addFreshEntity(stoneGolemEntity);
                this.getLevel().playSound(null, this.getBlockPos(), SoundInit.BLACKSTONE_GOLEM_SPAWN_EVENT, SoundSource.HOSTILE, 1F, 1F);
            }
        } else {
            double d = (double) this.getBlockPos().getX() + (double) this.getLevel().getRandom().nextFloat();
            double e = (double) this.getBlockPos().getY() + (double) this.getLevel().getRandom().nextFloat() + 1D;
            double f = (double) this.getBlockPos().getZ() + (double) this.getLevel().getRandom().nextFloat();
            if (buildGolemCounter > 0 && buildGolemCounter < 26) {
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 2, e, f - 2, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 2, e, f - 2, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 2, e, f - 3, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 2, e, f - 3, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 1, e, f - 7, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 1, e, f - 7, 0.0D, 0.0D, 0.0D);
            }
            if (buildGolemCounter > 30 && buildGolemCounter < 56) {
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 2, e + 1, f - 3, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 2, e + 1, f - 3, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 2, e + 1, f - 4, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 2, e + 1, f - 4, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 1, e + 1, f - 6, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 1, e + 1, f - 6, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 1, e + 1, f - 7, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 1, e + 1, f - 7, 0.0D, 0.0D, 0.0D);
            }
            if (buildGolemCounter > 60 && buildGolemCounter < 86) {
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 2, e + 2, f - 4, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 2, e + 2, f - 4, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 2, e + 2, f - 5, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 2, e + 2, f - 5, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 2, e + 2, f - 6, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 2, e + 2, f - 6, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 1, e + 2, f - 7, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 1, e + 2, f - 7, 0.0D, 0.0D, 0.0D);
                for (int i = -1; i < 2; i++) {
                    this.getLevel().addParticle(ParticleTypes.SMOKE, d - i, e + 2, f - 5, 0.0D, 0.0D, 0.0D);
                    this.getLevel().addParticle(ParticleTypes.SMOKE, d - i, e + 2, f - 6, 0.0D, 0.0D, 0.0D);
                }
            }
            if (buildGolemCounter > 90 && buildGolemCounter < 116) {
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 2, e + 3, f - 5, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 2, e + 3, f - 5, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d + 2, e + 3, f - 6, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d - 2, e + 3, f - 6, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d, e + 3, f - 4, 0.0D, 0.0D, 0.0D);
                this.getLevel().addParticle(ParticleTypes.SMOKE, d, e + 4, f - 4, 0.0D, 0.0D, 0.0D);
                for (int i = -1; i < 2; i++) {
                    this.getLevel().addParticle(ParticleTypes.SMOKE, d - i, e + 3, f - 5, 0.0D, 0.0D, 0.0D);
                    this.getLevel().addParticle(ParticleTypes.SMOKE, d - i, e + 3, f - 6, 0.0D, 0.0D, 0.0D);
                }
            }
        }

        if (buildGolemCounter >= 150) {
            startBuildingGolem = false;
            buildGolemCounter = 0;
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        sendUpdate();
    }

    private void sendUpdate() {
        if (this.getLevel() != null) {
            BlockState state = this.getLevel().getBlockState(this.worldPosition);
            this.getLevel().sendBlockUpdated(this.worldPosition, state, state, 3);
        }
    }

    @Override
    public void clearContent() {
        this.inventory.clear();
        this.setChanged();
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.getItem(0).isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.get(0);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(this.inventory, slot, 1);
        this.setChanged();
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        this.setChanged();
        return ContainerHelper.takeItem(this.inventory, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.inventory.set(0, stack);
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(Provider registryLookup) {
        return this.saveWithoutMetadata(registryLookup);
    }

}
