package net.adventurez.mixin;

import net.adventurez.entity.DragonEntity;
import net.adventurez.init.ConfigInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NetherPortalBlock.class)
public abstract class NetherPortalBlockMixin extends Block {

    public NetherPortalBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (player.isPassenger() && player.getVehicle() instanceof DragonEntity dragonEntity && player.canUsePortal(true) && ConfigInit.CONFIG.allow_ender_dragon_nether_portal) {
            player.stopRiding();
            dragonEntity.portalProcess.setAsInsidePortalThisTick(true);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;

    }

}