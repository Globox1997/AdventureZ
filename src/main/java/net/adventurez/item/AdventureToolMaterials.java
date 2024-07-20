package net.adventurez.item;

import net.adventurez.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public class AdventureToolMaterials implements ToolMaterial {

    public static final ToolMaterial GILDED_NETHERITE = new AdventureToolMaterials(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 2331, 9.0F, 4.0F, 15, Ingredient.ofItems(ItemInit.GILDED_NETHERITE_FRAGMENT),
            "gilded_netherite");

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Ingredient repairIngredient;
    private final String name;

    private AdventureToolMaterials(TagKey<Block> inverseTag, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Ingredient repairIngredient, String name) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
        this.name = name;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
