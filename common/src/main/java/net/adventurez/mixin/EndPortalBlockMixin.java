package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.entity.DragonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EndPortalBlock;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@Mixin(EndPortalBlock.class)
public abstract class EndPortalBlockMixin extends BaseEntityBlock {

    public EndPortalBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (player.isPassenger() && player.getVehicle() instanceof DragonEntity dragonEntity && player.canUsePortal(true)) {
            player.stopRiding();
            if (world instanceof ServerLevel) {
                ResourceKey<Level> registryKey = world.dimension() == Level.END ? Level.OVERWORLD : Level.END;
                ServerLevel serverWorld = ((ServerLevel) world).getServer().getLevel(registryKey);
                if (serverWorld == null) {
                    return InteractionResult.PASS;
                }
                dragonEntity.setAsInsidePortal((Portal) (Object) this, pos);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;

    }

}
