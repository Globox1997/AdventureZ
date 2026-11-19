package net.adventurez.item;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.ItemInit;
import net.adventurez.item.component.GildedActivationComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import java.util.List;

public class GildedNetheriteArmor extends ArmorItem {

    public GildedNetheriteArmor(Holder<ArmorMaterial> material, Type type, Properties settings) {
        super(material, type, settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(Component.translatable("item.adventurez.moreinfo.tooltip"));
            if (Screen.hasShiftDown()) {
                tooltip.remove(Component.translatable("item.adventurez.moreinfo.tooltip"));
                tooltip.add(Component.translatable("item.adventurez.gilded_netherite_armor.tooltip"));
                tooltip.add(Component.translatable("item.adventurez.gilded_netherite_armor.tooltip2"));
                tooltip.add(Component.translatable("item.adventurez.gilded_netherite_armor.tooltip3"));
                tooltip.add(Component.translatable("item.adventurez.gilded_netherite_armor.tooltip4"));
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (stack.getItem() == ItemInit.GILDED_NETHERITE_CHESTPLATE) {
            GildedActivationComponent component = stack.getOrDefault(ItemInit.GILDED_DATA, GildedActivationComponent.DEFAULT);

            if (component.activated()) {
                int elapsed = (int) world.getGameTime() - component.time();
                if (elapsed > ConfigInit.CONFIG.gilded_netherite_armor_effect_duration * 2) {
                    stack.set(ItemInit.GILDED_DATA, new GildedActivationComponent(false, component.time(), false));
                } else if (elapsed > ConfigInit.CONFIG.gilded_netherite_armor_effect_duration && component.visuals()) {
                    entity.setRemainingFireTicks(0);
                    stack.set(ItemInit.GILDED_DATA, new GildedActivationComponent(true, component.time(), false));
                }
            }
        }
    }

    public static boolean activateStoneGolemArmor(Player player, ItemStack stack) {
        if (!stack.is(ItemInit.GILDED_NETHERITE_CHESTPLATE)) {
            return false;
        }

        GildedActivationComponent component = stack.getOrDefault(ItemInit.GILDED_DATA, GildedActivationComponent.DEFAULT);
        if (component.activated()) {
            return false;
        }
        stack.set(ItemInit.GILDED_DATA, new GildedActivationComponent(true, (int) player.level().getGameTime(), true));

        if (!player.level().isClientSide()) {
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, ConfigInit.CONFIG.gilded_netherite_armor_effect_duration, 0, false, false));
        }
        player.playSound(SoundEvents.FIRE_EXTINGUISH, 1.0F, 1.0F);
        return true;
    }

    public static boolean isStoneGolemArmorActive(ItemStack stack) {
        if (!stack.is(ItemInit.GILDED_NETHERITE_CHESTPLATE)) {
            return false;
        }

        GildedActivationComponent component = stack.getOrDefault(ItemInit.GILDED_DATA, GildedActivationComponent.DEFAULT);
        return component.activated() && component.visuals();
    }

    public static boolean fullGolemArmor(Player playerEntity) {
        if (playerEntity.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(ItemInit.GILDED_NETHERITE_HELMET)
                && playerEntity.getItemBySlot(EquipmentSlot.CHEST).getItem().equals(ItemInit.GILDED_NETHERITE_CHESTPLATE)
                && playerEntity.getItemBySlot(EquipmentSlot.LEGS).getItem().equals(ItemInit.GILDED_NETHERITE_LEGGINGS)
                && playerEntity.getItemBySlot(EquipmentSlot.FEET).getItem().equals(ItemInit.GILDED_NETHERITE_BOOTS)) {
            return true;
        } else {
            return false;
        }
    }

}
