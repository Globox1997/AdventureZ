package net.adventurez.item;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.ItemInit;
import net.adventurez.item.component.GildedActivationComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;
import java.util.function.Consumer;

public class GildedNetheriteArmor extends Item {

    public GildedNetheriteArmor(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, display, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            if (Minecraft.getInstance().hasShiftDown()) {
                tooltip.accept(Component.translatable("item.adventurez.gilded_netherite_armor.tooltip"));
                tooltip.accept(Component.translatable("item.adventurez.gilded_netherite_armor.tooltip2"));
                tooltip.accept(Component.translatable("item.adventurez.gilded_netherite_armor.tooltip3"));
                tooltip.accept(Component.translatable("item.adventurez.gilded_netherite_armor.tooltip4"));
            } else {
                tooltip.accept(Component.translatable("item.adventurez.moreinfo.tooltip"));
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
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
