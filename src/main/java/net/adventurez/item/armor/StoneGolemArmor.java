package net.adventurez.item.armor;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.ItemInit;
import net.adventurez.init.KeybindInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class StoneGolemArmor extends ArmorItem {
    private static final UUID[] MODIFIERS = new UUID[] { UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
            UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150") };
    private final int protection;
    private final float toughness;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public StoneGolemArmor(ArmorMaterial material, EquipmentSlot slot) {
        super(material, slot, new Item.Settings().group(ItemGroup.COMBAT).fireproof());

        this.protection = material.getProtectionAmount(slot);
        this.toughness = material.getToughness();
        Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        UUID uUID = MODIFIERS[slot.getEntitySlotId()];
        builder.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uUID, "Armor modifier", (double) this.protection, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(uUID, "Armor toughness", (double) this.toughness, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                new EntityAttributeModifier(uUID, "Armor knockback resistance", (double) this.knockbackResistance, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == this.slot ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(new TranslatableText("item.adventurez.moreinfo.tooltip"));
            if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)) {
                tooltip.remove(new TranslatableText("item.adventurez.moreinfo.tooltip"));
                tooltip.remove(new TranslatableText("item.adventurez.stone_golem_armor.tooltip"));
                tooltip.add(new TranslatableText("item.adventurez.stone_golem_armor.tooltip"));
                tooltip.add(new TranslatableText("item.adventurez.stone_golem_armor.tooltip2", KeybindInit.armorKeyBind.getBoundKeyLocalizedText()));
                tooltip.add(new TranslatableText("item.adventurez.stone_golem_armor.tooltip3"));
                tooltip.add(new TranslatableText("item.adventurez.stone_golem_armor.tooltip4"));
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.getItem() == ItemInit.STONE_GOLEM_CHESTPLATE) {
            NbtCompound tag = stack.getNbt();
            if (tag != null && tag.contains("armor_time")) {
                if (tag.getBoolean("activating_armor") && tag.getInt("armor_time") + 2400 < (int) world.getTime()) {
                    tag.putBoolean("activating_armor", false);
                }
                if (tag.getBoolean("activating_armor") && tag.getInt("armor_time") + 1200 < (int) world.getTime()) {
                    entity.setFireTicks(0);
                    tag.putBoolean("activating_armor_visuals", false);
                }
            }
        }
    }

    public static void fireActive(PlayerEntity player, ItemStack stack) {
        StatusEffectInstance fire = new StatusEffectInstance(StatusEffect.byRawId(12), 1200, 0, false, false);
        NbtCompound tag = stack.getNbt();
        if (tag != null && tag.contains("activating_armor")) {
            if (tag.getBoolean("activating_armor") == false) {
                tag.putBoolean("activating_armor", true);
                tag.putBoolean("activating_armor_visuals", true);
                tag.putInt("armor_time", (int) player.world.getTime());
                if (!player.world.isClient) {
                    player.addStatusEffect(fire);
                }
                player.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        } else {
            tag.putBoolean("activating_armor", true);
            tag.putBoolean("activating_armor_visuals", true);
            tag.putInt("armor_time", (int) player.world.getTime());
            stack.setNbt(tag);
            player.addStatusEffect(fire);
            player.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
    }

    public static boolean fireTime(ItemStack stack) {
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