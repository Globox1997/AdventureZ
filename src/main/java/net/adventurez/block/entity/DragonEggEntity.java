package net.adventurez.block.entity;

import java.util.List;

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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class DragonEggEntity extends BlockEntity implements Tickable {
    private int overallSummoningTick;
    private int overallHatchingTick;
    private int hatchTick;
    private int summoningTick;
    private boolean isHatchAble;

    public DragonEggEntity() {
        super(BlockInit.DRAGON_EGG_ENTITY);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        hatchTick = tag.getInt("Hatch_Tick");
        isHatchAble = tag.getBoolean("Hatch_Able");
        summoningTick = tag.getInt("Summoning_Tick");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putBoolean("Hatch_Able", isHatchAble);
        tag.putInt("Hatch_Tick", hatchTick);
        tag.putInt("Summoning_Tick", summoningTick);
        return tag;
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
        this.updateTheEyeSummoning();
        this.updateDragonHatching();
    }

    private void updateDragonHatching() {
        if (ConfigInit.CONFIG.allow_dragon_hatching && !this.world.isClient) {
            overallHatchingTick++;
            if (overallHatchingTick == 20) {
                if (!this.isHatchAble) {
                    Box box = new Box(this.getPos());
                    List<PlayerEntity> list = world.getEntitiesByClass(PlayerEntity.class, box.expand(2D),
                            EntityPredicates.EXCEPT_SPECTATOR);
                    for (int i = 0; i < list.size(); ++i) {
                        PlayerEntity playerEntity = (PlayerEntity) list.get(i);
                        if (playerEntity instanceof PlayerEntity && playerEntity.hasStatusEffect(EffectInit.FAME)) {
                            this.isHatchAble = true;
                        }
                    }
                } else {
                    this.hatchTick++;
                    if (this.hatchTick % 60 == 0) {
                        for (int i = 0; i < 20; i++) {
                            double d = (double) pos.getX() + (double) world.random.nextFloat();
                            double e = (double) pos.getY() + (double) world.random.nextFloat();
                            double f = (double) pos.getZ() + (double) world.random.nextFloat();
                            ((ServerWorld) this.world).spawnParticles(ParticleTypes.HAPPY_VILLAGER, d, e, f, 0, 0.0D,
                                    0.0D, 0.0D, 0.01D);
                        }
                    }
                    if (this.hatchTick >= 598) {
                        world.breakBlock(this.pos, false);
                        DragonEntity dragonEntity = (DragonEntity) EntityInit.DRAGON_ENTITY.create(world);
                        dragonEntity.refreshPositionAndAngles((double) this.getPos().getX() + 0.5D,
                                (double) this.getPos().getY() + 0.55D, (double) this.getPos().getZ() + 0.5D, 90F, 0.0F);
                        dragonEntity.initialize(((ServerWorld) this.world), this.world.getLocalDifficulty(pos),
                                SpawnReason.STRUCTURE, null, null);
                        dragonEntity.setSize(1);
                        world.spawnEntity(dragonEntity);
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
            if (this.world.getBlockState(pos.down()).equals(Blocks.CRYING_OBSIDIAN.getDefaultState())
                    && this.world.getRegistryKey() == World.END) {
                overallSummoningTick++;
                BlockState state = this.getCachedState();
                if (overallSummoningTick == 20 && this.isValid(world, pos, state)) {
                    summoningTick++;
                    if (!world.isClient && summoningTick >= 60) {
                        TheEyeEntity theEyeEntity = (TheEyeEntity) EntityInit.THE_EYE_ENTITY.create(world);
                        theEyeEntity.refreshPositionAndAngles((double) this.getPos().getX() + 0.5D,
                                (double) this.getPos().getY() + 0.55D, (double) this.getPos().getZ() + 0.5D, 90F, 0.0F);
                        theEyeEntity.initialize(((ServerWorld) this.world), this.world.getLocalDifficulty(pos),
                                SpawnReason.STRUCTURE, null, null);
                        theEyeEntity.setEyeInvulnerabletime();
                        world.spawnEntity(theEyeEntity);
                        this.world.breakBlock(pos, false);
                    }
                }
                if (overallSummoningTick >= 20) {
                    overallSummoningTick = 0;
                }
                if (world.isClient && summoningTick != 0) {
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

}