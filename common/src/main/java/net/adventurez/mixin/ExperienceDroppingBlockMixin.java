package net.adventurez.mixin;

import net.adventurez.entity.PiglinBeastEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.server.level.ServerPlayer;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("deprecation")
@Mixin(DropExperienceBlock.class)
public abstract class ExperienceDroppingBlockMixin extends Block {

    public ExperienceDroppingBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public void playerDestroy(ServerLevel world, ServerPlayer player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (!world.isClientSide() && world instanceof ServerLevel serverWorld && state.is(Blocks.NETHER_GOLD_ORE) && ConfigInit.CONFIG.piglin_beast_ore_spawn_chance != 0) {
            if (!player.isCreative() && world.dimension() == Level.NETHER
                    && player.getMainHandItem().getEnchantments().keySet().stream().noneMatch(entry -> entry.is(Enchantments.SILK_TOUCH.identifier()))) {
                if (world.getEntities(EntityInit.PIGLIN_BEAST, player.getBoundingBox().inflate(40D), EntitySelector.NO_SPECTATORS).isEmpty()) {
                    int spawnChanceInt = world.getRandom().nextInt(ConfigInit.CONFIG.piglin_beast_ore_spawn_chance) + 1;
                    if (spawnChanceInt == 1) {

                        PiglinBeastEntity beastEntity = EntityInit.PIGLIN_BEAST.create(world, EntitySpawnReason.MOB_SUMMONED);
                        int posYOfPlayer = player.blockPosition().getY();
                        for (int counter = 0; counter < 100; counter++) {
                            float randomFloat = world.getRandom().nextFloat() * 6.2831855F;
                            int posX = pos.getX() + Mth.floor(Mth.cos(randomFloat) * 18.0F + world.getRandom().nextInt(16));
                            int posZ = pos.getZ() + Mth.floor(Mth.sin(randomFloat) * 18.0F + world.getRandom().nextInt(16));
                            // int posY = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, posX, posZ); doesnt work in nether
                            int posY = posYOfPlayer - 20 + world.getRandom().nextInt(40);
                            BlockPos spawnPos = new BlockPos(posX, posY, posZ);

                            if (world.hasChunksAt(spawnPos.getX() - 4, spawnPos.getY() - 4, spawnPos.getZ() - 4, spawnPos.getX() + 4, spawnPos.getY() + 4, spawnPos.getZ() + 4)
                                    && SpawnPlacements.checkSpawnRules(EntityInit.PIGLIN_BEAST, serverWorld, EntitySpawnReason.EVENT, spawnPos, world.getRandom())) {
                                beastEntity.snapTo(spawnPos, 0.0F, 0.0F);
                                beastEntity.finalizeSpawn(serverWorld, world.getCurrentDifficultyAt(spawnPos), EntitySpawnReason.EVENT, null);
                                world.addFreshEntity(beastEntity);
                                beastEntity.spawnAnim();
                                break;
                            }
                        }
                    }
                }
            }
        }
        super.playerDestroy(world, player, pos, state, blockEntity, tool);
    }

}
