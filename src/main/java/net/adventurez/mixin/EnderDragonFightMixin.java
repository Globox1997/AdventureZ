package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.ConfigInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;

@Mixin(EndDragonFight.class)
public class EnderDragonFightMixin {

    @Shadow
    private boolean previouslyKilled;
    @Shadow
    @Mutable
    @Final
    private ServerLevel level;
    @Shadow
    @Mutable
    @Final
    private BlockPos origin;

    @Inject(method = "setDragonKilled", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/dimension/end/EndDragonFight;spawnNewGateway()V"))
    public void dragonKilledMixin(EnderDragon dragon, CallbackInfo info) {
        if (!this.level.isClientSide && ConfigInit.CONFIG.resummoned_ender_dragon_drops_egg && this.previouslyKilled
                && this.level.getBlockState(this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.getLocation(this.origin))).getBlock() != Blocks.DRAGON_EGG) {
            this.level.setBlockAndUpdate(this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.getLocation(this.origin)), Blocks.DRAGON_EGG.defaultBlockState());
        }
    }

}
