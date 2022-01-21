package net.adventurez.mixin;

import java.util.Iterator;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {

    @Unique
    private int maxLevel = 0;
    @Unique
    private int enchantmentLevel = 0;

    @Inject(method = "updateResult", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void updateResultMixin(CallbackInfo info, ItemStack itemStack, int i, int j, int k, ItemStack itemStack2, ItemStack itemStack3, Map<Enchantment, Integer> map, boolean bl,
            Map<Enchantment, Integer> l, boolean m, boolean n, Iterator var12, Enchantment p, int q, int r) {
        maxLevel = p.getMaxLevel();
        enchantmentLevel = r;
    }

    @ModifyVariable(method = "updateResult", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I", ordinal = 1), ordinal = 4)
    private int updateResultModifyMixin(int original) {
        if (maxLevel < enchantmentLevel && maxLevel == enchantmentLevel - 1)
            return enchantmentLevel;
        else
            return original;
    }

}
