package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.entity.PiglinBeastEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
@Mixin(ExperienceDroppingBlock.class)
public abstract class ExperienceDroppingBlockMixin extends Block {

    public ExperienceDroppingBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool) {
        if (!world.isClient() && state.isOf(Blocks.NETHER_GOLD_ORE) && ConfigInit.CONFIG.piglin_beast_ore_spawn_chance != 0) {
            if (!player.isCreative() && world.getRegistryKey() == World.NETHER && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, player.getMainHandStack()) == 0) {
                if (world.getEntitiesByType(EntityInit.PIGLINBEAST_ENTITY, player.getBoundingBox().expand(40D), EntityPredicates.EXCEPT_SPECTATOR).isEmpty()) {
                    int spawnChanceInt = world.getRandom().nextInt(ConfigInit.CONFIG.piglin_beast_ore_spawn_chance) + 1;
                    if (spawnChanceInt == 1) {
                        PiglinBeastEntity beastEntity = EntityInit.PIGLINBEAST_ENTITY.create((World) world);
                        int posYOfPlayer = player.getBlockPos().getY();
                        for (int counter = 0; counter < 100; counter++) {
                            float randomFloat = world.getRandom().nextFloat() * 6.2831855F;
                            int posX = pos.getX() + MathHelper.floor(MathHelper.cos(randomFloat) * 18.0F + world.getRandom().nextInt(16));
                            int posZ = pos.getZ() + MathHelper.floor(MathHelper.sin(randomFloat) * 18.0F + world.getRandom().nextInt(16));
                            // int posY = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, posX, posZ); doesnt work in nether
                            int posY = posYOfPlayer - 20 + world.getRandom().nextInt(40);
                            BlockPos spawnPos = new BlockPos(posX, posY, posZ);
                            // isRegionLoaded foun in Raid class at getRavagerSpawnLocation
                            if (world.isRegionLoaded(spawnPos.getX() - 4, spawnPos.getY() - 4, spawnPos.getZ() - 4, spawnPos.getX() + 4, spawnPos.getY() + 4, spawnPos.getZ() + 4)
                                    && SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, spawnPos, EntityInit.PIGLINBEAST_ENTITY)) {
                                beastEntity.refreshPositionAndAngles(spawnPos, 0.0F, 0.0F);
                                beastEntity.initialize(((ServerWorld) world), world.getLocalDifficulty(spawnPos), SpawnReason.EVENT, null, null);
                                world.spawnEntity(beastEntity);
                                beastEntity.playSpawnEffects();
                                break;
                            }
                        }
                    }
                }
            }
        }
        super.afterBreak(world, player, pos, state, blockEntity, tool);
    }

}
