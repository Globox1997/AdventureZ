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
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
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
    private void updateResultMixin(CallbackInfo info, ItemStack itemStack, int i, long l, int j, ItemStack itemStack2, ItemStack itemStack3, ItemEnchantmentsComponent.Builder builder, boolean bl,
            ItemEnchantmentsComponent itemEnchantmentsComponent, boolean bl2, boolean bl3, Iterator var13, Object2IntMap.Entry entry, RegistryEntry registryEntry, int q, int r) {
        if (itemStack3.isOf(Items.ENCHANTED_BOOK) && itemStack3.get(ItemInit.VOID_DROP) != null && itemStack3.get(ItemInit.VOID_DROP)) {
            enchantmentLevel = r;
            this.isVoidShadowDrop = true;
        } else {
            this.isVoidShadowDrop = false;
        }
    }

    @ModifyVariable(method = "updateResult", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I", ordinal = 1), ordinal = 3)
    private int updateResultModifyMixin(int original) {
        if (this.isVoidShadowDrop) {
            return enchantmentLevel;
        } else {
            return original;
        }
    }

}
