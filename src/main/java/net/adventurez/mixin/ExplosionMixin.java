package net.adventurez.mixin;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.adventurez.entity.BlazeGuardianEntity;
import net.adventurez.entity.VoidFragmentEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerExplosion;

@Mixin(ServerExplosion.class)
public class ExplosionMixin {
    @Shadow
    @Final
    private @Nullable Entity source;

    @ModifyExpressionValue(method = "hurtEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"))
    private List<Entity> collectBlocksAndDamageEntitiesMixin(List<Entity> list) {
        if (this.source != null) {
            if (this.source instanceof BlazeGuardianEntity || this.source instanceof VoidFragmentEntity) {
                List<Entity> filtered = new ArrayList<Entity>(list);
                filtered.removeIf(entity -> !(entity instanceof Player));
                return filtered;
            }
        }
        return list;
    }
}
