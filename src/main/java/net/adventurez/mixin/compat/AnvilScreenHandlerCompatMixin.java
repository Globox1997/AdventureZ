package net.adventurez.mixin.compat;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.adventurez.init.ItemInit;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ItemEnchantments;

@Mixin(AnvilMenu.class)
public abstract class AnvilScreenHandlerCompatMixin extends ItemCombinerMenu {

    @Unique
    private int enchantmentLevel = 0;
    @Unique
    private boolean isVoidShadowDrop = false;

    public AnvilScreenHandlerCompatMixin(MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess context) {
        super(type, syncId, playerInventory, context);
    }

    @SuppressWarnings("rawtypes")
    @Inject(method = "createResult", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void updateResultMixin(CallbackInfo info, ItemStack itemStack, int i, long l, int j, ItemStack itemStack2, ItemStack itemStack3, ItemEnchantments.Mutable builder, boolean bl,
            ItemEnchantments itemEnchantmentsComponent, boolean bl2, boolean bl3, Iterator var13, Object2IntMap.Entry entry, Holder registryEntry, int q, int r) {
        if (itemStack3.is(Items.ENCHANTED_BOOK) && itemStack3.get(ItemInit.VOID_DROP) != null && itemStack3.get(ItemInit.VOID_DROP)) {
            enchantmentLevel = r;
            this.isVoidShadowDrop = true;
        } else {
            this.isVoidShadowDrop = false;
        }
    }

    @ModifyVariable(method = "createResult", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I", ordinal = 1), ordinal = 3)
    private int updateResultModifyMixin(int original) {
        if (this.isVoidShadowDrop) {
            return enchantmentLevel;
        } else {
            return original;
        }
    }

}
