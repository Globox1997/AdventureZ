package net.adventurez.block.entity;

import java.util.List;

import net.adventurez.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class PiglinFlagEntity extends BlockEntity {
    private int flagWave;

    public PiglinFlagEntity(BlockPos pos, BlockState state) {
        super(BlockInit.PIGLIN_FLAG_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        flagWave = nbt.getInt("Flagging");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("Flagging", flagWave);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, PiglinFlagEntity blockEntity) {
        blockEntity.update();
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, PiglinFlagEntity blockEntity) {
        blockEntity.update();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        this.sendUpdate();
    }

    private void sendUpdate() {
        if (this.getWorld() != null) {
            BlockState state = this.getWorld().getBlockState(this.getPos());
            this.getWorld().updateListeners(this.getPos(), state, state, 3);
        }
    }

    private void update() {
        if (this.getWorld().getTime() % 20 == 0) {
            if (this.flagWave < 120) {
                this.flagWave++;
            }
            if (this.flagWave >= 120 && this.getWorld().getClosestPlayer((double) this.getPos().getX(), (double) this.getPos().getY(), (double) this.getPos().getZ(), 3D, null) != null) {
                this.getPiglins();
                this.markDirty();
                if (this.getWorld().isClient()) {
                    for (int i = 0; i < 20; i++) {
                        double d = (double) this.getPos().getX() + (double) this.getWorld().getRandom().nextFloat();
                        double e = (double) this.getPos().getY() + (double) this.getWorld().getRandom().nextFloat() * 2.0F;
                        double f = (double) this.getPos().getZ() + (double) this.getWorld().getRandom().nextFloat();
                        this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, d, e, f, 0.0D, 1.0D, 0.0D);
                    }
                }
                this.flagWave = 0;
            }
        }
    }

    public void getPiglins() {
        List<LivingEntity> list = this.getWorld().getEntitiesByClass(LivingEntity.class, new Box(this.getPos()).expand(40D), EntityPredicates.EXCEPT_SPECTATOR);
        for (int i = 0; i < list.size(); ++i) {
            LivingEntity entity = (LivingEntity) list.get(i);
            if (entity.getType() == EntityType.PIGLIN) {
                PiglinEntity piglin = (PiglinEntity) entity;
                if (piglin.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) {
                    piglin.getBrain().forget(MemoryModuleType.ANGRY_AT);
                    piglin.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
                    piglin.getBrain().forget(MemoryModuleType.WALK_TARGET);
                }
            }
        }
    }

}