package net.adventurez.mixin;

import java.util.Iterator;
import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.adventurez.init.ItemInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @Unique
    private int enchantmentLevel = 0;
    @Unique
    private boolean isVoidShadowDrop = false;
    @Unique
    private int repairedAmount;

    @Shadow
    @Mutable
    @Final
    private Property levelCost = Property.create();

    public AnvilScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
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

    @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
    private void updateResultMixin(CallbackInfo info) {
        ItemStack itemStack = this.input.getStack(0);
        if (itemStack.getItem() == ItemInit.PRIME_EYE && itemStack.getDamage() != 0) {
            ItemStack itemStack2 = this.input.getStack(1);
            if (itemStack2.getItem() == Items.ENDER_PEARL) {
                ItemStack itemStack3 = new ItemStack(ItemInit.PRIME_EYE);
                repairedAmount = itemStack2.getCount() > itemStack.getDamage() ? itemStack.getDamage() : itemStack2.getCount();
                itemStack3.setDamage(itemStack.getDamage() - repairedAmount);
                this.output.setStack(0, itemStack3);
                info.cancel();
            }
        }
    }

    @Inject(method = "canTakeOutput", at = @At("HEAD"), cancellable = true)
    public void canTakeOutputMixin(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> info) {
        ItemStack itemStack = this.input.getStack(0);
        if (itemStack.getItem() == ItemInit.PRIME_EYE && itemStack.getDamage() != 0) {
            ItemStack itemStack2 = this.input.getStack(1);
            if (itemStack2.getItem() == Items.ENDER_PEARL) {
                info.setReturnValue(true);
            }
        }
    }

    @Inject(method = "onTakeOutput", at = @At("HEAD"))
    public void onTakeOutputMixin(PlayerEntity player, ItemStack stack, CallbackInfo info) {
        ItemStack itemStack = this.input.getStack(0);
        if (itemStack.getItem() == ItemInit.PRIME_EYE) {
            ItemStack itemStack2 = this.input.getStack(1);
            if (itemStack2.getItem() == Items.ENDER_PEARL) {
                itemStack2.decrement(repairedAmount - 1);
                this.output.setStack(0, itemStack2);
                this.levelCost.set(0);
            }
        }
    }

}
