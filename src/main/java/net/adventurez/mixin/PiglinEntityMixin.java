package net.adventurez.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.entity.PiglinBeastEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.entity.EntityType;
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

@SuppressWarnings("deprecation")
@Mixin(PiglinEntity.class)
public abstract class PiglinEntityMixin extends AbstractPiglinEntity {

    public PiglinEntityMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onDeath(DamageSource source) {
        if (!this.getWorld().isClient() && ConfigInit.CONFIG.piglin_beast_attack_piglin_spawn_chance != 0) {
            if (this.getAttacker() != null && this.getAttacker() instanceof PlayerEntity && this.getWorld().getRegistryKey() == World.NETHER) {
                int spawnChanceInt = this.getWorld().getRandom().nextInt(ConfigInit.CONFIG.piglin_beast_attack_piglin_spawn_chance) + 1;
                if (spawnChanceInt == 1 && isEntityNearby(EntityType.PIGLIN, 12D, 2) && !isEntityNearby(EntityInit.PIGLIN_BEAST, 40D, 1)) {
                    PiglinBeastEntity beastEntity = (PiglinBeastEntity) EntityInit.PIGLIN_BEAST.create(this.getWorld());
                    int posYOfPlayer = this.getBlockPos().getY();
                    for (int counter = 0; counter < 100; counter++) {
                        float randomFloat = this.getWorld().getRandom().nextFloat() * 6.2831855F;
                        int posX = this.getBlockPos().getX() + MathHelper.floor(MathHelper.cos(randomFloat) * 26.0F + this.getWorld().getRandom().nextInt(5));
                        int posZ = this.getBlockPos().getZ() + MathHelper.floor(MathHelper.sin(randomFloat) * 26.0F + this.getWorld().getRandom().nextInt(5));
                        int posY = posYOfPlayer - 20 + this.getWorld().getRandom().nextInt(40);
                        BlockPos spawnPos = new BlockPos(posX, posY, posZ);
                        if (this.getWorld().isRegionLoaded(spawnPos.getX() - 4, spawnPos.getY() - 4, spawnPos.getZ() - 4, spawnPos.getX() + 4, spawnPos.getY() + 4, spawnPos.getZ() + 4)
                                && SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, this.getWorld(), spawnPos, EntityInit.PIGLIN_BEAST)) {
                            beastEntity.refreshPositionAndAngles(spawnPos, this.getWorld().getRandom().nextFloat() * 360.0F, 0.0F);
                            beastEntity.initialize(((ServerWorld) this.getWorld()), this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.EVENT, null, null);
                            this.getWorld().spawnEntity(beastEntity);
                            beastEntity.playSpawnEffects();
                            break;
                        }
                    }
                }
            }
        }
        super.onDeath(source);
    }

    private boolean isEntityNearby(EntityType<?> entityType, double distance, int count) {
        List<?> list = this.getWorld().getEntitiesByType(entityType, this.getBoundingBox().expand(distance), EntityPredicates.EXCEPT_SPECTATOR);
        if (list.size() >= count) {
            return true;
        } else {
            return false;
        }
    }

}
