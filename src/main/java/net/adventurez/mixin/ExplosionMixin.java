package net.adventurez.mixin;

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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;

@Mixin(Explosion.class)
public class ExplosionMixin {
  @Shadow
  @Mutable
  @Final
  private Entity entity;

  public ExplosionMixin(@Nullable Entity entity) {
    this.entity = entity;
  }

  @Inject(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;<init>(DDD)V", shift = Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
  public void collectBlocksAndDamageEntitiesMixin(CallbackInfo info, Set<BlockPos> set, float q, int r, int s, int t,
      int u, int v, int w, List<Entity> list) {
    if (this.entity != null && this.entity instanceof BlazeGuardianEntity) {
      for (int i = 0; i < list.size(); ++i) {
        Entity entityFromList = (Entity) list.get(i);
        if (!(entityFromList instanceof PlayerEntity)) {
          list.remove(i);
        }
      }
    }
  }
}
