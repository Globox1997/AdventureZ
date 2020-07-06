package net.adventurez.item.armor;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import net.adventurez.init.ItemInit;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class StoneGolemArmor extends ArmorItem {
  private static final UUID[] MODIFIERS = new UUID[] { UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150") };
  private final int protection;
  private final float toughness;
  private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

  public StoneGolemArmor(ArmorMaterial material, EquipmentSlot slot) {
    super(material, slot, new Item.Settings().group(ItemGroup.COMBAT).fireproof());

    this.protection = material.getProtectionAmount(slot);
    this.toughness = material.getToughness();
    Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
    UUID uUID = MODIFIERS[slot.getEntitySlotId()];
    builder.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uUID, "Armor modifier",
        (double) this.protection, EntityAttributeModifier.Operation.ADDITION));
    builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(uUID, "Armor toughness",
        (double) this.toughness, EntityAttributeModifier.Operation.ADDITION));
    builder.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(uUID,
        "Armor knockback resistance", (double) this.knockbackResistance, EntityAttributeModifier.Operation.ADDITION));
    this.attributeModifiers = builder.build();
  }

  @Override
  public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
    return slot == this.slot ? this.attributeModifiers : super.getAttributeModifiers(slot);
  }

  @Override
  public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    tooltip.add(new TranslatableText("item.adventurez.stone_golem_armor.tooltip"));
  }

  @Override
  public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    LivingEntity player = (LivingEntity) entity;
    StatusEffectInstance spd = new StatusEffectInstance(StatusEffect.byRawId(1), 9, 0, false, false);
    StatusEffectInstance haste = new StatusEffectInstance(StatusEffect.byRawId(3), 9, 0, false, false);
    StatusEffectInstance fire = new StatusEffectInstance(StatusEffect.byRawId(12), 9, 0, false, false);

    if (player.getEquippedStack(EquipmentSlot.FEET).isItemEqual(new ItemStack(ItemInit.STONE_GOLEM_BOOTS))
        && player.getEquippedStack(EquipmentSlot.LEGS).isItemEqual(new ItemStack(ItemInit.STONE_GOLEM_LEGGINGS))
        && player.getEquippedStack(EquipmentSlot.CHEST).isItemEqual(new ItemStack(ItemInit.STONE_GOLEM_CHESTPLATE))
        && player.getEquippedStack(EquipmentSlot.HEAD).isItemEqual(new ItemStack(ItemInit.STONE_GOLEM_HELMET))
        && !world.isClient) {
      player.addStatusEffect(haste);
      player.addStatusEffect(fire);
      if (player.isSprinting()) {
        player.addStatusEffect(spd);
      }
    }
  }

}