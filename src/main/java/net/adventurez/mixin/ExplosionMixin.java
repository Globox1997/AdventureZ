package net.adventurez.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.BlazeGuardianEntity;
import net.adventurez.entity.VoidFragmentEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;

@Mixin(Explosion.class)
public class ExplosionMixin {
    @Shadow
    @Mutable
    @Final
    private Entity source;

    public ExplosionMixin(@Nullable Entity entity) {
        this.source = entity;
    }

    @Inject(method = "explode", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;", shift = Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void collectBlocksAndDamageEntitiesMixin(CallbackInfo info, Set<BlockPos> set, int i, float q, int k, int l, int r, int s, int t, int u, List<Entity> list) {
        if (this.source != null) {
            if (this.source instanceof BlazeGuardianEntity || this.source instanceof VoidFragmentEntity) {
                List<Entity> removeList = new ArrayList<Entity>();
                for (int o = 0; o < list.size(); ++o) {
                    Entity entityFromList = (Entity) list.get(o);
                    if (!(entityFromList instanceof Player)) {
                        removeList.add(entityFromList);
                    }
                }
                list.removeAll(removeList);
            }
        }
    }
}
