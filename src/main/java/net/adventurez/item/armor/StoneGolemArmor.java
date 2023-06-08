package net.adventurez.item.armor;

import java.util.List;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.ItemInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class StoneGolemArmor extends ArmorItem {

    public StoneGolemArmor(ArmorMaterial material, Type type) {
        super(material, type, new Item.Settings().fireproof());
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(Text.translatable("item.adventurez.moreinfo.tooltip"));
            if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)) {
                tooltip.remove(Text.translatable("item.adventurez.moreinfo.tooltip"));
                tooltip.remove(Text.translatable("item.adventurez.stone_golem_armor.tooltip"));
                tooltip.add(Text.translatable("item.adventurez.stone_golem_armor.tooltip"));
                tooltip.add(Text.translatable("item.adventurez.stone_golem_armor.tooltip2"));
                tooltip.add(Text.translatable("item.adventurez.stone_golem_armor.tooltip3"));
                tooltip.add(Text.translatable("item.adventurez.stone_golem_armor.tooltip4"));
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.getItem() == ItemInit.STONE_GOLEM_CHESTPLATE) {
            NbtCompound tag = stack.getNbt();
            if (tag != null && tag.contains("armor_time")) {
                if (tag.getBoolean("activating_armor") && tag.getInt("armor_time") + (ConfigInit.CONFIG.stone_golem_armor_effect_duration * 2) < (int) world.getTime()) {
                    tag.putBoolean("activating_armor", false);
                }
                if (tag.getBoolean("activating_armor") && tag.getInt("armor_time") + ConfigInit.CONFIG.stone_golem_armor_effect_duration < (int) world.getTime()) {
                    entity.setFireTicks(0);
                    tag.putBoolean("activating_armor_visuals", false);
                }
            }
        }
    }

    public static void activateStoneGolemArmor(PlayerEntity player, ItemStack stack) {
        if (!stack.isOf(ItemInit.STONE_GOLEM_CHESTPLATE))
            return;

        StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffect.byRawId(12), ConfigInit.CONFIG.stone_golem_armor_effect_duration, 0, false, false);
        NbtCompound tag = stack.getNbt();
        if (tag != null && tag.contains("activating_armor")) {
            if (tag.getBoolean("activating_armor") == false) {
                tag.putBoolean("activating_armor", true);
                tag.putBoolean("activating_armor_visuals", true);
                tag.putInt("armor_time", (int) player.getWorld().getTime());
                if (!player.getWorld().isClient()) {
                    player.addStatusEffect(statusEffectInstance);
                }
                player.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        } else {
            tag.putBoolean("activating_armor", true);
            tag.putBoolean("activating_armor_visuals", true);
            tag.putInt("armor_time", (int) player.getWorld().getTime());
            stack.setNbt(tag);
            player.addStatusEffect(statusEffectInstance);
            player.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
    }

    public static boolean isStoneGolemArmorActive(ItemStack stack) {
        if (!stack.isOf(ItemInit.STONE_GOLEM_CHESTPLATE))
            return false;

        NbtCompound tag = stack.getNbt();
        if (tag != null && tag.contains("armor_time") && tag.contains("activating_armor")) {
            if (tag.getBoolean("activating_armor_visuals")) {
                return true;
            }
        }
        return false;
    }

    public static boolean fullGolemArmor(PlayerEntity playerEntity) {
        if (playerEntity.getEquippedStack(EquipmentSlot.HEAD).getItem().equals(ItemInit.STONE_GOLEM_HELMET)
                && playerEntity.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(ItemInit.STONE_GOLEM_CHESTPLATE)
                && playerEntity.getEquippedStack(EquipmentSlot.LEGS).getItem().equals(ItemInit.STONE_GOLEM_LEGGINGS)
                && playerEntity.getEquippedStack(EquipmentSlot.FEET).getItem().equals(ItemInit.STONE_GOLEM_BOOTS)) {
            return true;
        } else
            return false;
    }

}