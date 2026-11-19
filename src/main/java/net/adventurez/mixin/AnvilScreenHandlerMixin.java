package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.adventurez.init.ItemInit;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Mixin(AnvilMenu.class)
public abstract class AnvilScreenHandlerMixin extends ItemCombinerMenu {

    @Unique
    private int repairedAmount;

    @Shadow
    @Mutable
    @Final
    private DataSlot cost = DataSlot.standalone();

    public AnvilScreenHandlerMixin(MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void updateResultMixin(CallbackInfo info) {
        ItemStack itemStack = this.inputSlots.getItem(0);
        if (itemStack.getItem() == ItemInit.PRIME_EYE && itemStack.getDamageValue() != 0) {
            ItemStack itemStack2 = this.inputSlots.getItem(1);
            if (itemStack2.getItem() == Items.ENDER_PEARL) {
                ItemStack itemStack3 = itemStack.copy();
                repairedAmount = Math.min(itemStack2.getCount(), itemStack.getDamageValue());
                itemStack3.setDamageValue(itemStack.getDamageValue() - repairedAmount);
                this.resultSlots.setItem(0, itemStack3);
                info.cancel();
            }
        }
    }

    @Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
    public void canTakeOutputMixin(Player player, boolean present, CallbackInfoReturnable<Boolean> info) {
        ItemStack itemStack = this.inputSlots.getItem(0);
        if (itemStack.getItem() == ItemInit.PRIME_EYE && itemStack.getDamageValue() != 0) {
            ItemStack itemStack2 = this.inputSlots.getItem(1);
            if (itemStack2.getItem() == Items.ENDER_PEARL) {
                info.setReturnValue(true);
            }
        }
    }

    @Inject(method = "onTake", at = @At("HEAD"))
    public void onTakeOutputMixin(Player player, ItemStack stack, CallbackInfo info) {
        ItemStack itemStack = this.inputSlots.getItem(0);
        if (itemStack.getItem() == ItemInit.PRIME_EYE) {
            ItemStack itemStack2 = this.inputSlots.getItem(1);
            if (itemStack2.getItem() == Items.ENDER_PEARL) {
                itemStack2.shrink(repairedAmount - 1);
                this.resultSlots.setItem(0, itemStack2);
                this.cost.set(0);
            }
        }
    }

}
