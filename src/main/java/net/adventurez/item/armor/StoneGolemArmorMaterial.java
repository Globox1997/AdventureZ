package net.adventurez.item.armor;

import net.minecraft.item.ArmorItem.Type;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class StoneGolemArmorMaterial implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = new int[] { 12, 16, 15, 14 };
    private static final int[] PROTECTION_AMOUNTS = new int[] { 3, 8, 6, 3 };

    @Override
    public int getDurability(Type type) {
        return BASE_DURABILITY[type.ordinal()] * 40;
    }

    @Override
    public int getProtection(Type type) {
        return PROTECTION_AMOUNTS[type.ordinal()];
    }

    @Override
    public int getEnchantability() {
        return 20;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.NETHERITE_INGOT);
    }

    @Override
    public String getName() {
        return "stone_golem";
    }

    @Override
    public float getToughness() {
        return 3F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.1F;
    }

}