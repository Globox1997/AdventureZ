package net.adventurez.item;

import java.util.List;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.ItemInit;
import net.adventurez.item.component.GildedActivationComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class GildedNetheriteArmor extends ArmorItem {

    public GildedNetheriteArmor(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(Text.translatable("item.adventurez.moreinfo.tooltip"));
            if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)) {
                tooltip.remove(Text.translatable("item.adventurez.moreinfo.tooltip"));
                tooltip.add(Text.translatable("item.adventurez.gilded_netherite_armor.tooltip"));
                tooltip.add(Text.translatable("item.adventurez.gilded_netherite_armor.tooltip2"));
                tooltip.add(Text.translatable("item.adventurez.gilded_netherite_armor.tooltip3"));
                tooltip.add(Text.translatable("item.adventurez.gilded_netherite_armor.tooltip4"));
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.getItem() == ItemInit.GILDED_NETHERITE_CHESTPLATE) {
            GildedActivationComponent component = stack.getOrDefault(ItemInit.GILDED_DATA, GildedActivationComponent.DEFAULT);

            if (component.activated() && component.time() + (ConfigInit.CONFIG.gilded_netherite_armor_effect_duration * 2) < (int) world.getTime()) {
                stack.set(ItemInit.GILDED_DATA, new GildedActivationComponent(false, component.time(), component.visuals()));
            }
            if (component.activated() && component.time() + ConfigInit.CONFIG.gilded_netherite_armor_effect_duration < (int) world.getTime()) {
                entity.setFireTicks(0);
                stack.set(ItemInit.GILDED_DATA, new GildedActivationComponent(component.activated(), component.time(), false));
            }
        }
    }

    public static void activateStoneGolemArmor(PlayerEntity player, ItemStack stack) {
        if (!stack.isOf(ItemInit.GILDED_NETHERITE_CHESTPLATE)) {
            return;
        }

        GildedActivationComponent component = stack.getOrDefault(ItemInit.GILDED_DATA, GildedActivationComponent.DEFAULT);
        if (!component.activated()) {
            stack.set(ItemInit.GILDED_DATA, new GildedActivationComponent(true, (int) player.getWorld().getTime(), true));
            if (!player.getWorld().isClient()) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, ConfigInit.CONFIG.gilded_netherite_armor_effect_duration, 0, false, false));
            }
            player.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.0F);
        }
    }

    public static boolean isStoneGolemArmorActive(ItemStack stack) {
        if (!stack.isOf(ItemInit.GILDED_NETHERITE_CHESTPLATE)) {
            return false;
        }

        GildedActivationComponent component = stack.getOrDefault(ItemInit.GILDED_DATA, GildedActivationComponent.DEFAULT);
        return component.activated() && component.visuals();
    }

    public static boolean fullGolemArmor(PlayerEntity playerEntity) {
        if (playerEntity.getEquippedStack(EquipmentSlot.HEAD).getItem().equals(ItemInit.GILDED_NETHERITE_HELMET)
                && playerEntity.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(ItemInit.GILDED_NETHERITE_CHESTPLATE)
                && playerEntity.getEquippedStack(EquipmentSlot.LEGS).getItem().equals(ItemInit.GILDED_NETHERITE_LEGGINGS)
                && playerEntity.getEquippedStack(EquipmentSlot.FEET).getItem().equals(ItemInit.GILDED_NETHERITE_BOOTS)) {
            return true;
        } else {
            return false;
        }
    }

}