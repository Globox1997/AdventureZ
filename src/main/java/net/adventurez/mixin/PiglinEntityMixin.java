package net.adventurez.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.entity.PiglinBeastEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;

@Mixin(PiglinEntity.class)
public abstract class PiglinEntityMixin extends AbstractPiglinEntity {

    public PiglinEntityMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onDeath(DamageSource source) {
        if (!world.isClient && ConfigInit.CONFIG.piglin_beast_attack_piglin_spawn_chance != 0) {
            if (this.getAttacker() != null && this.getAttacker() instanceof PlayerEntity && world.getRegistryKey() == World.NETHER) {
                int spawnChanceInt = world.random.nextInt(ConfigInit.CONFIG.piglin_beast_attack_piglin_spawn_chance) + 1;
                if (spawnChanceInt == 1 && isEntityNearby(EntityType.PIGLIN, 12D, 6) && !isEntityNearby(EntityInit.PIGLINBEAST_ENTITY, 40D, 1)) {
                    PiglinBeastEntity beastEntity = (PiglinBeastEntity) EntityInit.PIGLINBEAST_ENTITY.create(world);
                    int posYOfPlayer = this.getBlockPos().getY();
                    for (int counter = 0; counter < 100; counter++) {
                        float randomFloat = world.random.nextFloat() * 6.2831855F;
                        int posX = this.getBlockPos().getX() + MathHelper.floor(MathHelper.cos(randomFloat) * 26.0F + world.random.nextInt(5));
                        int posZ = this.getBlockPos().getZ() + MathHelper.floor(MathHelper.sin(randomFloat) * 26.0F + world.random.nextInt(5));
                        int posY = posYOfPlayer - 20 + world.getRandom().nextInt(40);
                        BlockPos spawnPos = new BlockPos(posX, posY, posZ);
                        if (this.world.isRegionLoaded(spawnPos.getX() - 4, spawnPos.getY() - 4, spawnPos.getZ() - 4, spawnPos.getX() + 4, spawnPos.getY() + 4, spawnPos.getZ() + 4)
                                && SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, this.world, spawnPos, EntityInit.PIGLINBEAST_ENTITY)) {
                            beastEntity.refreshPositionAndAngles(spawnPos, world.random.nextFloat() * 360.0F, 0.0F);
                            beastEntity.initialize(((ServerWorld) this.world), this.world.getLocalDifficulty(this.getBlockPos()), SpawnReason.EVENT, null, null);
                            world.spawnEntity(beastEntity);
                            beastEntity.playSpawnEffects();
                            break;
                        }
                    }
                }
            }
        }
        super.onDeath(source);
    }

    private boolean isEntityNearby(EntityType entityType, double distance, int count) {
        List<LivingEntity> list = this.world.getEntitiesByType(entityType, this.getBoundingBox().expand(distance), EntityPredicates.EXCEPT_SPECTATOR);
        if (list.size() >= count) {
            return true;
        } else
            return false;
    }

}
