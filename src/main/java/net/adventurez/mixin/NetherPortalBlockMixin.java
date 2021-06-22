package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.entity.DragonEntity;
import net.adventurez.init.ConfigInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin {

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.hasVehicle() && player.getVehicle() instanceof DragonEntity && player.canUsePortals() && ConfigInit.CONFIG.allow_ender_dragon_nether_portal) {
            DragonEntity dragonEntity = (DragonEntity) player.getVehicle();
            player.stopRiding();
            dragonEntity.setInNetherPortal(pos);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;

    }

}