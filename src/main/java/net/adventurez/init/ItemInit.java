package net.adventurez.init;

import net.adventurez.item.*;
import net.adventurez.item.armor.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.OnAStickItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemInit {
    // Items
    public static final Item GILDED_STONE = register("gilded_stone", new GildedStoneItem(new Item.Settings().group(ItemGroup.MISC), () -> EntityInit.GILDEDSTONE_ENTITY));
    public static final Item STONE_GOLEM_HEART = register("stone_golem_heart", new StoneGolemHeartItem(new Item.Settings().group(ItemGroup.MISC)));
    public static final Item STONE_GOLEM_ARM = register("stone_golem_arm", new StoneGolemArm(new Item.Settings().group(ItemGroup.COMBAT).maxDamage(2506)));
    public static final Item GILDED_NETHERITE_FRAGMENT = register("gilded_netherite_fragment", new GildedNetheriteFragmentItem(new Item.Settings().group(ItemGroup.MISC).fireproof()));
    public static final Item PRIME_EYE = register("prime_eye", new PrimeEyeItem(new Item.Settings().group(ItemGroup.MISC).maxDamage(64)));
    public static final Item ORC_SKIN = register("orc_skin", new Item(new Item.Settings().group(ItemGroup.MISC)));
    public static final Item DRAGON_SADDLE = register("dragon_saddle", new Item(new Item.Settings().group(ItemGroup.TRANSPORTATION).maxCount(1)));
    public static final Item SOURCE_STONE = register("source_stone", new SourceStoneItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1)));
    public static final Item CHORUS_FRUIT_ON_A_STICK = register("chorus_fruit_on_a_stick",
            new OnAStickItem<>((new Item.Settings()).maxDamage(100).group(ItemGroup.TRANSPORTATION), EntityInit.ENDER_WHALE_ENTITY, 1));
    public static final Item ENDER_FLUTE = register("ender_flute", new EnderFluteItem(new Item.Settings().group(ItemGroup.TRANSPORTATION).maxDamage(32)));
    public static final Item IGUANA_HIDE = register("iguana_hide", new Item(new Item.Settings().group(ItemGroup.MATERIALS)));
    public static final Item MAMMOTH_LEATHER = register("mammoth_fur", new Item(new Item.Settings().group(ItemGroup.MATERIALS)));
    public static final Item ENDER_WHALE_LEATHER = register("ender_whale_skin", new Item(new Item.Settings().group(ItemGroup.MATERIALS)));
    public static final Item IVORY_ARROW = register("ivory_arrow", new ArrowItem(new Item.Settings().group(ItemGroup.COMBAT)));
    public static final Item MAMMOTH_TUSK = register("mammoth_tusk", new Item(new Item.Settings().group(ItemGroup.MATERIALS)));
    public static final Item RHINO_LEATHER = register("rhino_leather", new Item(new Item.Settings().group(ItemGroup.MATERIALS)));
    public static final Item WARTHOG_SHELL_PIECE = register("warthog_shell_piece", new Item(new Item.Settings().group(ItemGroup.MATERIALS)));

    // Food
    public static final Item MAMMOTH_MEAT = register("mammoth_meat",
            new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).meat().build())));
    public static final Item COOKED_MAMMOTH_MEAT = register("cooked_mammoth_meat",
            new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).meat().build())));
    public static final Item IGUANA_MEAT = register("iguana_meat", new Item(new Item.Settings().group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3F).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final Item COOKED_IGUANA_MEAT = register("cooked_iguana_meat",
            new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.6F).meat().build())));
    public static final Item ENDER_WHALE_MEAT = register("ender_whale_meat", new Item(new Item.Settings().group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 0), 0.5F).meat().build())));
    public static final Item COOKED_ENDER_WHALE = register("cooked_ender_whale_meat", new Item(new Item.Settings().group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.9F).statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 600, 0), 1.0F).meat().build())));
    public static final Item RHINO_MEAT = register("rhino_meat",
            new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).meat().build())));
    public static final Item COOKED_RHINO_MEAT = register("cooked_rhino_meat",
            new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).meat().build())));
    public static final Item RAW_VENISON = register("raw_venison",
            new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).meat().build())));
    public static final Item COOKED_VENISON = register("cooked_venison",
            new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.8F).meat().build())));
    public static final Item WARTHOG_MEAT = register("warthog_meat",
            new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).meat().build())));
    public static final Item COOKED_WARTHOG_MEAT = register("cooked_warthog_meat", new Item(new Item.Settings().group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.8F).statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 0), 1.0F).meat().build())));
    // Armor
    public static final ArmorMaterial STONE_GOLEM_ARMOR_MATERIAL = new StoneGolemArmorMaterial();
    public static final Item STONE_GOLEM_HELMET = register("stone_golem_helmet", new StoneGolemArmor(STONE_GOLEM_ARMOR_MATERIAL, EquipmentSlot.HEAD));
    public static final Item STONE_GOLEM_CHESTPLATE = register("stone_golem_chestplate", new StoneGolemArmor(STONE_GOLEM_ARMOR_MATERIAL, EquipmentSlot.CHEST));
    public static final Item STONE_GOLEM_LEGGINGS = register("stone_golem_leggings", new StoneGolemArmor(STONE_GOLEM_ARMOR_MATERIAL, EquipmentSlot.LEGS));
    public static final Item STONE_GOLEM_BOOTS = register("stone_golem_boots", new StoneGolemArmor(STONE_GOLEM_ARMOR_MATERIAL, EquipmentSlot.FEET));

    private static Item register(String id, Item item) {
        return register(new Identifier("adventurez", id), item);
    }

    private static Item register(Identifier id, Item item) {
        return Registry.register(Registry.ITEM, id, item);
    }

    public static void init() {
    }

}