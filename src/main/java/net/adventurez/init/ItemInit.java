package net.adventurez.init;

import net.adventurez.item.*;
import net.adventurez.item.armor.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.OnAStickItem;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemInit {

    // Item Group
    public static final RegistryKey<ItemGroup> ADVENTUREZ_ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("adventurez", "item_group"));

    // Items
    public static final Item GILDED_STONE = register("gilded_stone", new GildedStoneItem(new Item.Settings(), () -> EntityInit.GILDEDSTONE_ENTITY));
    public static final Item STONE_GOLEM_HEART = register("stone_golem_heart", new StoneGolemHeartItem(new Item.Settings()));
    public static final Item STONE_GOLEM_ARM = register("stone_golem_arm", new StoneGolemArm(new Item.Settings().maxDamage(2506)));
    public static final Item GILDED_NETHERITE_FRAGMENT = register("gilded_netherite_fragment", new GildedNetheriteFragmentItem(new Item.Settings().fireproof()));
    public static final Item PRIME_EYE = register("prime_eye", new PrimeEyeItem(new Item.Settings().maxDamage(64)));
    public static final Item ORC_SKIN = register("orc_skin", new Item(new Item.Settings()));
    public static final Item DRAGON_SADDLE = register("dragon_saddle", new Item(new Item.Settings().maxCount(1)));
    public static final Item SOURCE_STONE = register("source_stone", new SourceStoneItem(new Item.Settings().maxCount(1)));
    public static final Item CHORUS_FRUIT_ON_A_STICK = register("chorus_fruit_on_a_stick", new OnAStickItem<>((new Item.Settings()).maxDamage(100), EntityInit.ENDER_WHALE_ENTITY, 1));
    public static final Item ENDER_FLUTE = register("ender_flute", new EnderFluteItem(new Item.Settings().maxDamage(32)));
    public static final Item IGUANA_HIDE = register("iguana_hide", new Item(new Item.Settings()));
    public static final Item MAMMOTH_LEATHER = register("mammoth_fur", new Item(new Item.Settings()));
    public static final Item ENDER_WHALE_SKIN = register("ender_whale_skin", new Item(new Item.Settings()));
    public static final Item IVORY_ARROW = register("ivory_arrow", new ArrowItem(new Item.Settings()));
    public static final Item MAMMOTH_TUSK = register("mammoth_tusk", new Item(new Item.Settings()));
    public static final Item RHINO_LEATHER = register("rhino_leather", new Item(new Item.Settings()));
    public static final Item WARTHOG_SHELL_PIECE = register("warthog_shell_piece", new Item(new Item.Settings()));
    public static final Item HANDBOOK = register("handbook", new HandbookItem(new Item.Settings()));

    // Food
    public static final Item MAMMOTH_MEAT = register("mammoth_meat", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).meat().build())));
    public static final Item COOKED_MAMMOTH_MEAT = register("cooked_mammoth_meat", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).meat().build())));
    public static final Item IGUANA_MEAT = register("iguana_meat", new Item(
            new Item.Settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3F).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final Item COOKED_IGUANA_MEAT = register("cooked_iguana_meat", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.6F).meat().build())));
    public static final Item ENDER_WHALE_MEAT = register("ender_whale_meat", new Item(
            new Item.Settings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 0), 0.5F).meat().build())));
    public static final Item COOKED_ENDER_WHALE_MEAT = register("cooked_ender_whale_meat", new Item(
            new Item.Settings().food(new FoodComponent.Builder().hunger(8).saturationModifier(0.9F).statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 600, 0), 1.0F).meat().build())));
    public static final Item RHINO_MEAT = register("rhino_meat", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).meat().build())));
    public static final Item COOKED_RHINO_MEAT = register("cooked_rhino_meat", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).meat().build())));
    public static final Item RAW_VENISON = register("raw_venison", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).meat().build())));
    public static final Item COOKED_VENISON = register("cooked_venison", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.8F).meat().build())));
    public static final Item WARTHOG_MEAT = register("warthog_meat", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).meat().build())));
    public static final Item COOKED_WARTHOG_MEAT = register("cooked_warthog_meat", new Item(
            new Item.Settings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.8F).statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 0), 1.0F).meat().build())));
    // Armor
    public static final ArmorMaterial STONE_GOLEM_ARMOR_MATERIAL = new StoneGolemArmorMaterial();
    public static final Item STONE_GOLEM_HELMET = register("stone_golem_helmet", new StoneGolemArmor(STONE_GOLEM_ARMOR_MATERIAL, ArmorItem.Type.HELMET));
    public static final Item STONE_GOLEM_CHESTPLATE = register("stone_golem_chestplate", new StoneGolemArmor(STONE_GOLEM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE));
    public static final Item STONE_GOLEM_LEGGINGS = register("stone_golem_leggings", new StoneGolemArmor(STONE_GOLEM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS));
    public static final Item STONE_GOLEM_BOOTS = register("stone_golem_boots", new StoneGolemArmor(STONE_GOLEM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS));

    private static Item register(String id, Item item) {
        return register(new Identifier("adventurez", id), item);
    }

    private static Item register(Identifier id, Item item) {
        ItemGroupEvents.modifyEntriesEvent(ADVENTUREZ_ITEM_GROUP).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, id, item);
    }

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, ADVENTUREZ_ITEM_GROUP,
                FabricItemGroup.builder().icon(() -> new ItemStack(ItemInit.HANDBOOK)).displayName(Text.translatable("item.adventurez.item_group")).build());
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, ItemInit.ORC_SKIN, Potions.TURTLE_MASTER);
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, ItemInit.ENDER_WHALE_SKIN, Potions.SLOW_FALLING);
    }

}