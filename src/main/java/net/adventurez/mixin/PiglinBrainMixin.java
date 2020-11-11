package net.adventurez.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.TagInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {

  @Inject(method = "wearsGoldArmor(Lnet/minecraft/entity/LivingEntity;)Z", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
  private static void wearsGoldArmorMixin(LivingEntity entity, CallbackInfoReturnable<Boolean> info,
      Iterable<ItemStack> iterable, Iterator<ItemStack> iterator, ItemStack stack, Item item) {
    if (item.isIn(TagInit.PIGLIN_NOT_ATTACK))
      info.setReturnValue(true);
  }

}