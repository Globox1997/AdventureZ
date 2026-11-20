package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.ConfigInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;

@Mixin(EnderDragonFight.class)
public class EnderDragonFightMixin {

    @Shadow
    private boolean hasPreviouslyKilledDragon;
    @Shadow
    private ServerLevel level;
    @Shadow
    private BlockPos origin;

    @Inject(method = "setDragonKilled", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/dimension/end/EnderDragonFight;spawnNewGateway()V"))
    public void dragonKilledMixin(EnderDragon dragon, CallbackInfo info) {
        if (!this.level.isClientSide() && ConfigInit.CONFIG.resummoned_ender_dragon_drops_egg && this.hasPreviouslyKilledDragon
                && this.level.getBlockState(this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EnderDragonFight.getPodiumLocation(this.origin))).getBlock() != Blocks.DRAGON_EGG) {
            this.level.setBlockAndUpdate(this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EnderDragonFight.getPodiumLocation(this.origin)), Blocks.DRAGON_EGG.defaultBlockState());
        }
    }

}
