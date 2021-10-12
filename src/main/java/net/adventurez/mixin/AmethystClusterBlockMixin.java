package net.adventurez.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.entity.AmethystGolemEntity;
import net.minecraft.block.AmethystBlock;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

@Mixin(AmethystClusterBlock.class)
public abstract class AmethystClusterBlockMixin extends AmethystBlock {

    public AmethystClusterBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (!world.isClient) {
            List<AmethystGolemEntity> list = world.getEntitiesByClass(AmethystGolemEntity.class, new Box(pos).expand(16.0D), EntityPredicates.EXCEPT_SPECTATOR);
            if (!list.isEmpty())
                for (int i = 0; i < list.size(); i++)
                    list.get(i).amethystGolemRageMode();
        }
    }

}
