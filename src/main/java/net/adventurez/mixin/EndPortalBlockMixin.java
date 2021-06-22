package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.entity.DragonEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

@Mixin(EndPortalBlock.class)
public abstract class EndPortalBlockMixin {

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.hasVehicle() && player.getVehicle() instanceof DragonEntity && player.canUsePortals()) {
            DragonEntity dragonEntity = (DragonEntity) player.getVehicle();
            player.stopRiding();
            if (world instanceof ServerWorld) {
                RegistryKey<World> registryKey = world.getRegistryKey() == World.END ? World.OVERWORLD : World.END;
                ServerWorld serverWorld = ((ServerWorld) world).getServer().getWorld(registryKey);
                if (serverWorld == null) {
                    return ActionResult.PASS;
                }
                dragonEntity.moveToWorld(serverWorld);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;

    }

}