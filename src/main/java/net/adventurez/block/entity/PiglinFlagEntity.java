package net.adventurez.block.entity;

import java.util.List;

import net.adventurez.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class PiglinFlagEntity extends BlockEntity {
    private int flagWave;

    public PiglinFlagEntity(BlockPos pos, BlockState state) {
        super(BlockInit.PIGLIN_FLAG_ENTITY, pos, state);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);
        this.flagWave = nbt.getInt("Flagging");
    }

    @Override
    public void saveAdditional(CompoundTag nbt, Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);
        nbt.putInt("Flagging", flagWave);
    }

    public static void clientTick(Level world, BlockPos pos, BlockState state, PiglinFlagEntity blockEntity) {
        blockEntity.update();
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, PiglinFlagEntity blockEntity) {
        blockEntity.update();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.sendUpdate();
    }

    private void sendUpdate() {
        if (this.getLevel() != null) {
            BlockState state = this.getLevel().getBlockState(this.getBlockPos());
            this.getLevel().sendBlockUpdated(this.getBlockPos(), state, state, 3);
        }
    }

    private void update() {
        if (this.getLevel().getGameTime() % 20 == 0) {
            if (this.flagWave < 120) {
                this.flagWave++;
            }
            if (this.flagWave >= 120 && this.getLevel().getNearestPlayer((double) this.getBlockPos().getX(), (double) this.getBlockPos().getY(), (double) this.getBlockPos().getZ(), 3D, null) != null) {
                this.getPiglins();
                this.setChanged();
                if (this.getLevel().isClientSide()) {
                    for (int i = 0; i < 20; i++) {
                        double d = (double) this.getBlockPos().getX() + (double) this.getLevel().getRandom().nextFloat();
                        double e = (double) this.getBlockPos().getY() + (double) this.getLevel().getRandom().nextFloat() * 2.0F;
                        double f = (double) this.getBlockPos().getZ() + (double) this.getLevel().getRandom().nextFloat();
                        this.getLevel().addParticle(ParticleTypes.HAPPY_VILLAGER, d, e, f, 0.0D, 1.0D, 0.0D);
                    }
                }
                this.flagWave = 0;
            }
        }
    }

    public void getPiglins() {
        List<LivingEntity> list = this.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(this.getBlockPos()).inflate(40D), EntitySelector.NO_SPECTATORS);
        for (int i = 0; i < list.size(); ++i) {
            LivingEntity entity = list.get(i);
            if (entity.getType() == EntityType.PIGLIN) {
                Piglin piglin = (Piglin) entity;
                if (piglin.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
                    piglin.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
                    piglin.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
                    piglin.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
                }
            }
        }
    }

}