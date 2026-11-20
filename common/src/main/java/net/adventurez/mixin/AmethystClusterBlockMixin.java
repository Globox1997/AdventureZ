package net.adventurez.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.entity.AmethystGolemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

@Mixin(AmethystClusterBlock.class)
public abstract class AmethystClusterBlockMixin extends AmethystBlock {

    public AmethystClusterBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide()) {
            List<AmethystGolemEntity> list = world.getEntitiesOfClass(AmethystGolemEntity.class, new AABB(pos).inflate(16.0D), EntitySelector.NO_SPECTATORS);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).amethystGolemRageMode();
                }
            }
        }
        return super.playerWillDestroy(world, pos, state, player);
    }

}
