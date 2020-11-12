package net.adventurez.block.entity;

import net.adventurez.entity.TheEyeEntity;
import net.adventurez.init.BlockInit;
import net.adventurez.init.EntityInit;
import net.adventurez.init.TagInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DragonEggEntity extends BlockEntity implements Tickable {
    private int overallTick;

    public DragonEggEntity() {
        super(BlockInit.DRAGON_EGG_ENTITY);
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

    private boolean isValid(World world, BlockPos pos, BlockState state) {
        int stoneCounter;
        int stoneCounter2;
        int stoneCounter3 = 0;
        int stoneCounter4;
        int stoneCounter5;
        BlockState rod_1 = world.getBlockState(pos.north().east());
        BlockState rod_2 = world.getBlockState(pos.north().west());
        BlockState rod_3 = world.getBlockState(pos.south().east());
        BlockState rod_4 = world.getBlockState(pos.south().west());
        if (rod_1.equals(Blocks.END_ROD.getDefaultState()) && rod_2.equals(Blocks.END_ROD.getDefaultState())
                && rod_3.equals(Blocks.END_ROD.getDefaultState()) && rod_4.equals(Blocks.END_ROD.getDefaultState())) {
            for (stoneCounter4 = -1; stoneCounter4 < 2; stoneCounter4++) {
                for (stoneCounter5 = -1; stoneCounter5 < 2; stoneCounter5++) {
                    BlockState middleState = world.getBlockState(pos.north(stoneCounter4).east(stoneCounter5).down());
                    if (middleState.getBlock().equals(Blocks.CRYING_OBSIDIAN)) {
                        stoneCounter3++;
                    }
                }
            }
            for (stoneCounter = -2; stoneCounter < 3; stoneCounter++) {
                for (stoneCounter2 = -2; stoneCounter2 < 3; stoneCounter2++) {
                    BlockState baseState = world.getBlockState(pos.north(stoneCounter).east(stoneCounter2).down(2));
                    if (baseState.getBlock().isIn(TagInit.PLATFORM_END_BLOCKS)) {
                        stoneCounter3++;
                    }
                }
            }
        }
        if (stoneCounter3 == 34) {
            return true;
        } else
            return false;
    }

    @Override
    public void tick() {
        this.update();
    }

    private void update() {
        if (this.world.getRegistryKey() == World.END
                && this.world.getBlockState(pos.down()).equals(Blocks.CRYING_OBSIDIAN.getDefaultState())) {
            overallTick++;
            BlockState state = this.getCachedState();
            // if (world.isClient) {
            // for (int i = 0; i < 20; i++) {
            // world.addParticle(ParticleTypes.ENCHANT, (double) this.getPos().getX() +
            // 0.5D,
            // (double) this.getPos().getY() + 3.0D, (double) this.getPos().getZ() + 0.5D,
            // (double) ((float) world.random.nextFloat()) - 0.5D,
            // (double) ((float) world.random.nextFloat() - 2.0F),
            // (double) ((float) world.random.nextFloat()) - 0.5D);
            // }

            // }
            if (overallTick == 100 && this.isValid(world, pos, state)) {
                // if (world.isClient) {
                // for (int i = 0; i < 20; i++) {
                // double d = (double) pos.getX() + (double) world.random.nextFloat();
                // double e = (double) pos.getY() + (double) world.random.nextFloat() + 1D;
                // double f = (double) pos.getZ() + (double) world.random.nextFloat();
                // world.addParticle(ParticleTypes.HAPPY_VILLAGER, d, e, f, 0.0D, 1.0D, 0.0D);
                // }

                // }
                if (!world.isClient) {// && this.world.isReceivingRedstonePower(pos)
                    TheEyeEntity theEyeEntity = (TheEyeEntity) EntityInit.THEEYE_ENTITY.create(world);
                    theEyeEntity.refreshPositionAndAngles((double) this.getPos().getX() + 0.5D,
                            (double) this.getPos().getY() + 0.55D, (double) this.getPos().getZ() + 0.5D, 90F, 0.0F);
                    theEyeEntity.method_6885();
                    world.spawnEntity(theEyeEntity);
                    this.world.breakBlock(pos, false);
                }
            }
            if (overallTick >= 100) {
                overallTick = 0;
            }

        }

    }

}