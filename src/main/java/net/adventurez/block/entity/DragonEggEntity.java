package net.adventurez.block.entity;

import net.adventurez.entity.DragonEntity;
import net.adventurez.entity.TheEyeEntity;
import net.adventurez.init.BlockInit;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EffectInit;
import net.adventurez.init.EntityInit;
import net.adventurez.init.TagInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DragonEggEntity extends BlockEntity {
    private int overallSummoningTick;
    private int overallHatchingTick;
    private int hatchTick;
    private int summoningTick;
    private boolean isHatchAble;

    public DragonEggEntity(BlockPos pos, BlockState state) {
        super(BlockInit.DRAGON_EGG_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        hatchTick = nbt.getInt("Hatch_Tick");
        isHatchAble = nbt.getBoolean("Hatch_Able");
        summoningTick = nbt.getInt("Summoning_Tick");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("Hatch_Able", isHatchAble);
        nbt.putInt("Hatch_Tick", hatchTick);
        nbt.putInt("Summoning_Tick", summoningTick);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, DragonEggEntity blockEntity) {
        blockEntity.tick();
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, DragonEggEntity blockEntity) {
        blockEntity.tick();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        sendUpdate();
    }

    private void sendUpdate() {
        if (this.getWorld() != null) {
            BlockState state = this.getWorld().getBlockState(this.pos);
            (this.getWorld()).updateListeners(this.pos, state, state, 3);
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
        if (rod_1.equals(Blocks.END_ROD.getDefaultState()) && rod_2.equals(Blocks.END_ROD.getDefaultState()) && rod_3.equals(Blocks.END_ROD.getDefaultState())
                && rod_4.equals(Blocks.END_ROD.getDefaultState())) {
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
                    if (baseState.isIn(TagInit.PLATFORM_END_BLOCKS)) {
                        stoneCounter3++;
                    }
                }
            }
        }
        if (stoneCounter3 == 34) {
            return true;
        } else {
            return false;
        }
    }

    private void tick() {
        this.updateTheEyeSummoning();
        this.updateDragonHatching();
    }

    private void updateDragonHatching() {
        if (ConfigInit.CONFIG.allow_dragon_hatching && !this.getWorld().isClient()) {
            overallHatchingTick++;
            if (overallHatchingTick == 20) {
                if (!this.isHatchAble) {
                    if (((ServerWorld) this.getWorld()).getEnderDragonFight() != null && !((ServerWorld) this.getWorld()).getEnderDragonFight().hasPreviouslyKilled())
                        return;
                    PlayerEntity playerEntity = this.getWorld().getClosestPlayer(this.getPos().getX() + 0.5D, this.getPos().getY(), this.getPos().getZ() + 0.5D, 2.5D, true);
                    if (playerEntity != null && playerEntity.hasStatusEffect(EffectInit.FAME))
                        enableEggHatching();
                } else {
                    this.hatchTick++;
                    if (this.hatchTick % 60 == 0) {
                        for (int i = 0; i < 20; i++) {
                            double d = (double) pos.getX() + (double) this.getWorld().getRandom().nextFloat();
                            double e = (double) pos.getY() + (double) this.getWorld().getRandom().nextFloat();
                            double f = (double) pos.getZ() + (double) this.getWorld().getRandom().nextFloat();
                            ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.HAPPY_VILLAGER, d, e, f, 0, 0.0D, 0.0D, 0.0D, 0.01D);
                        }
                    }
                    if (this.hatchTick >= 598) {
                        this.getWorld().breakBlock(this.pos, false);
                        DragonEntity dragonEntity = (DragonEntity) EntityInit.DRAGON.create(this.getWorld());
                        dragonEntity.refreshPositionAndAngles((double) this.getPos().getX() + 0.5D, (double) this.getPos().getY() + 0.55D, (double) this.getPos().getZ() + 0.5D, 90F, 0.0F);
                        dragonEntity.initialize(((ServerWorld) this.getWorld()), this.getWorld().getLocalDifficulty(pos), SpawnReason.STRUCTURE, null, null);
                        dragonEntity.setSize(1);
                        this.getWorld().spawnEntity(dragonEntity);
                    }
                }

            }
            if (overallHatchingTick >= 20) {
                overallHatchingTick = 0;
            }
        }
    }

    private void updateTheEyeSummoning() {
        if (ConfigInit.CONFIG.allow_the_eye_summoning) {
            if (this.getWorld().getBlockState(pos.down()).equals(Blocks.CRYING_OBSIDIAN.getDefaultState()) && this.getWorld().getRegistryKey() == World.END) {
                overallSummoningTick++;
                BlockState state = this.getCachedState();
                if (overallSummoningTick == 20 && this.isValid(this.getWorld(), pos, state)) {
                    summoningTick++;
                    if (!this.getWorld().isClient() && summoningTick >= 60) {
                        TheEyeEntity theEyeEntity = (TheEyeEntity) EntityInit.THE_EYE.create(this.getWorld());
                        theEyeEntity.refreshPositionAndAngles((double) this.getPos().getX() + 0.5D, (double) this.getPos().getY() + 0.55D, (double) this.getPos().getZ() + 0.5D, 90F, 0.0F);
                        theEyeEntity.initialize(((ServerWorld) this.getWorld()), this.getWorld().getLocalDifficulty(pos), SpawnReason.STRUCTURE, null, null);
                        theEyeEntity.setEyeInvulnerabletime();
                        this.getWorld().spawnEntity(theEyeEntity);
                        this.getWorld().breakBlock(pos, false);
                    }
                }
                if (overallSummoningTick >= 20) {
                    overallSummoningTick = 0;
                }
                if (this.getWorld().isClient() && summoningTick != 0) {
                    if (summoningTick % 10 == 0) {
                        this.dragonAltartParticle(1);
                    }
                    if (summoningTick % 5 == 0) {
                        this.dragonAltartParticle(2);
                    }
                }

            }
        }
    }

    private void dragonAltartParticle(int distance) {
        double d = (double) pos.east(distance).north(distance).getX() + (double) world.random.nextFloat();
        double e = (double) pos.up(1 - distance).getY() + (double) world.random.nextFloat() * 2.0D;
        double f = (double) pos.east(distance).north(distance).getZ() + (double) world.random.nextFloat();
        double d1 = (double) pos.west(distance).north(distance).getX() + (double) world.random.nextFloat();
        double e1 = (double) pos.up(1 - distance).getY() + (double) world.random.nextFloat() * 2.0D;
        double f1 = (double) pos.west(distance).north(distance).getZ() + (double) world.random.nextFloat();
        double d2 = (double) pos.south(distance).east(distance).getX() + (double) world.random.nextFloat();
        double e2 = (double) pos.up(1 - distance).getY() + (double) world.random.nextFloat() * 2.0D;
        double f2 = (double) pos.south(distance).east(distance).getZ() + (double) world.random.nextFloat();
        double d3 = (double) pos.south(distance).west(distance).getX() + (double) world.random.nextFloat();
        double e3 = (double) pos.up(1 - distance).getY() + (double) world.random.nextFloat() * 2.0D;
        double f3 = (double) pos.south(distance).west(distance).getZ() + (double) world.random.nextFloat();
        world.addParticle(ParticleTypes.END_ROD, d, e, f, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.END_ROD, d1, e1, f1, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.END_ROD, d2, e2, f2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.END_ROD, d3, e3, f3, 0.0D, 0.0D, 0.0D);
    }

    public void enableEggHatching() {
        this.isHatchAble = true;
    }

}