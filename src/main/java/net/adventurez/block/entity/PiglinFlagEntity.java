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
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("Flagging", flagWave);
        return nbt;
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
        sendUpdate();
    }

    private void sendUpdate() {
        if (this.world != null) {
            BlockState state = this.world.getBlockState(this.pos);
            (this.world).updateListeners(this.pos, state, state, 3);
        }
    }

    public void update() {

        if (this.flagWave < 2400) {
            this.flagWave++;
        }
        if (flagWave >= 2400 && this.world.getClosestPlayer((double) this.getPos().getX(), (double) this.getPos().getY(), (double) this.getPos().getZ(), 3D, null) != null) {
            getPiglins();
            markDirty();
            if (world.isClient) {
                for (int i = 0; i < 20; i++) {
                    double d = (double) pos.getX() + (double) world.random.nextFloat();
                    double e = (double) pos.getY() + (double) world.random.nextFloat() + 1D;
                    double f = (double) pos.getZ() + (double) world.random.nextFloat();
                    world.addParticle(ParticleTypes.HAPPY_VILLAGER, d, e, f, 0.0D, 1.0D, 0.0D);
                }
            }
            this.flagWave = 0;
        }
    }

    public void getPiglins() {
        List<LivingEntity> list = this.world.getEntitiesByClass(LivingEntity.class, new Box(pos).expand(40D), EntityPredicates.EXCEPT_SPECTATOR);
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