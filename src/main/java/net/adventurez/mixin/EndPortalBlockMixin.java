package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.entity.DragonEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.block.Portal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(EndPortalBlock.class)
public abstract class EndPortalBlockMixin extends BlockWithEntity {

    public EndPortalBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.hasVehicle() && player.getVehicle() instanceof DragonEntity dragonEntity && player.canUsePortals(true)) {
            player.stopRiding();
            if (world instanceof ServerWorld) {
                RegistryKey<World> registryKey = world.getRegistryKey() == World.END ? World.OVERWORLD : World.END;
                ServerWorld serverWorld = ((ServerWorld) world).getServer().getWorld(registryKey);
                if (serverWorld == null) {
                    return ActionResult.PASS;
                }
                dragonEntity.tryUsePortal((Portal) (Object) this, pos);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;

    }

}
