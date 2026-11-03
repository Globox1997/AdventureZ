package net.adventurez.mixin;

import net.adventurez.entity.DragonEntity;
import net.adventurez.init.ConfigInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NetherPortalBlock.class)
public abstract class NetherPortalBlockMixin extends Block {

    public NetherPortalBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.hasVehicle() && player.getVehicle() instanceof DragonEntity dragonEntity && player.canUsePortals(true) && ConfigInit.CONFIG.allow_ender_dragon_nether_portal) {
            player.stopRiding();
            dragonEntity.portalManager.setInPortal(true);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;

    }

}