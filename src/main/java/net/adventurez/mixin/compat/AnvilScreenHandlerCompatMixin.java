package net.adventurez.mixin.compat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.adventurez.init.ItemInit;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilMenu.class)
public abstract class AnvilScreenHandlerCompatMixin extends ItemCombinerMenu {

    public AnvilScreenHandlerCompatMixin(MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess context, ItemCombinerMenuSlotDefinition slotDefinition) {
        super(type, syncId, playerInventory, context, slotDefinition);
    }

    @ModifyExpressionValue(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I"))
    private int updateResultMaxLevelMixin(int original) {
        ItemStack addition = this.inputSlots.getItem(1);
        if (addition.is(Items.ENCHANTED_BOOK) && Boolean.TRUE.equals(addition.get(ItemInit.VOID_DROP))) {
            return Integer.MAX_VALUE;
        }
        return original;
    }
}
