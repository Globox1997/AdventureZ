package net.adventurez.mixin.compat;

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
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerCompatMixin extends ForgingScreenHandler {

    @Unique
    private int enchantmentLevel = 0;
    @Unique
    private boolean isVoidShadowDrop = false;

    public AnvilScreenHandlerCompatMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @SuppressWarnings("rawtypes")
    @Inject(method = "updateResult", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void updateResultMixin(CallbackInfo info, ItemStack itemStack, int i, int j, int k, ItemStack itemStack2, ItemStack itemStack3, Map<Enchantment, Integer> map, boolean bl,
            Map<Enchantment, Integer> l, boolean m, boolean n, Iterator var12, Enchantment p, int q, int r) {
        if (itemStack3.isOf(Items.ENCHANTED_BOOK) && itemStack3.hasNbt() && itemStack3.getNbt().contains("void_drop") && itemStack3.getNbt().getBoolean("void_drop")) {
            enchantmentLevel = r;
            this.isVoidShadowDrop = true;
        } else {
            this.isVoidShadowDrop = false;
        }
    }

    @ModifyVariable(method = "updateResult", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I", ordinal = 1), ordinal = 4)
    private int updateResultModifyMixin(int original) {
        if (this.isVoidShadowDrop) {
            return enchantmentLevel;
        } else {
            return original;
        }
    }

}
