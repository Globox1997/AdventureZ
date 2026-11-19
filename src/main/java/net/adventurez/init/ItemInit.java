package net.adventurez.init;

import java.util.List;
import java.util.function.UnaryOperator;

import com.mojang.serialization.Codec;

import net.adventurez.AdventureMain;
import net.adventurez.item.*;
import net.adventurez.item.component.GildedActivationComponent;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.alchemy.Potions;

public class ItemInit {

    // Item Group
    public static final ResourceKey<CreativeModeTab> ADVENTUREZ_ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath("adventurez", "item_group"));

    // Components
    public static final DataComponentType<Boolean> VOID_DROP = registerComponent("void_drop", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> LAVA_LIGHT = registerComponent("lava_light", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<GildedActivationComponent> GILDED_DATA = registerComponent("gilded_data",
            builder -> builder.persistent(GildedActivationComponent.CODEC).networkSynchronized(GildedActivationComponent.PACKET_CODEC));

    // Items
    public static final Item GILDED_BLACKSTONE_SHARD = register("gilded_blackstone_shard", new GildedBlackstoneShard(new Item.Properties().fireResistant(), () -> EntityInit.GILDED_BLACKSTONE_SHARD));
    public static final Item BLACKSTONE_GOLEM_HEART = register("blackstone_golem_heart", new BlackstoneGolemHeart(new Item.Properties().fireResistant()));
    public static final Item BLACKSTONE_GOLEM_ARM = register("blackstone_golem_arm", new BlackstoneGolemArm(new Item.Properties().durability(2506).fireResistant()));
    public static final Item GILDED_NETHERITE_FRAGMENT = register("gilded_netherite_fragment", new GildedNetheriteFragment(new Item.Properties().fireResistant()));
    public static final Item PRIME_EYE = register("prime_eye", new PrimeEyeItem(new Item.Properties().durability(64)));
    public static final Item ORC_SKIN = register("orc_skin", new Item(new Item.Properties()));
    public static final Item DRAGON_SADDLE = register("dragon_saddle", new Item(new Item.Properties().stacksTo(1)));
    public static final Item SOURCE_STONE = register("source_stone", new SourceStone(new Item.Properties().stacksTo(1)));
    public static final Item CHORUS_FRUIT_ON_A_STICK = register("chorus_fruit_on_a_stick", new FoodOnAStickItem<>((new Item.Properties()).durability(100), EntityInit.ENDER_WHALE, 1));
    public static final Item ENDER_FLUTE = register("ender_flute", new EnderFlute(new Item.Properties().durability(32)));
    public static final Item IGUANA_HIDE = register("iguana_hide", new Item(new Item.Properties()));
    public static final Item MAMMOTH_LEATHER = register("mammoth_fur", new Item(new Item.Properties()));
    public static final Item ENDER_WHALE_SKIN = register("ender_whale_skin", new Item(new Item.Properties()));
    public static final Item IVORY_ARROW = register("ivory_arrow", new ArrowItem(new Item.Properties()));
    public static final Item MAMMOTH_TUSK = register("mammoth_tusk", new Item(new Item.Properties()));
    public static final Item RHINO_LEATHER = register("rhino_leather", new Item(new Item.Properties()));
    public static final Item WARTHOG_SHELL_PIECE = register("warthog_shell_piece", new Item(new Item.Properties()));
    public static final Item HANDBOOK = register("handbook", new Handbook(new Item.Properties().stacksTo(1)));
    public static final Item GILDED_UPGRADE_SMITHING_TEMPLATE = register("gilded_upgrade_smithing_template",
            new SmithingTemplateItem(Component.translatable("item.minecraft.smithing_template.gilded_upgrade.applies_to").withStyle(ChatFormatting.BLUE),
                    Component.translatable("item.minecraft.smithing_template.gilded_upgrade.ingredients").withStyle(ChatFormatting.BLUE),
                    Component.translatable("item.adventurez.gilded_upgrade").withStyle(ChatFormatting.GRAY), Component.translatable("item.smithing_template.gilded_upgrade.base_slot_description"),
                    Component.translatable("item.adventurez.smithing_template.gilded_upgrade.additions_slot_description"), List.of(ResourceLocation.parse("item/empty_armor_slot_helmet"),
                    ResourceLocation.parse("item/empty_armor_slot_chestplate"), ResourceLocation.parse("item/empty_armor_slot_leggings"), ResourceLocation.parse("item/empty_armor_slot_boots")),
                    List.of(ResourceLocation.parse("adventurez:item/empty_slot_gilded_netherite_fragment"))));

    // Food
    public static final Item MAMMOTH_MEAT = register("mammoth_meat", new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build())));
    public static final Item COOKED_MAMMOTH_MEAT = register("cooked_mammoth_meat", new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build())));
    public static final Item IGUANA_MEAT = register("iguana_meat",
            new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).build())));
    public static final Item COOKED_IGUANA_MEAT = register("cooked_iguana_meat", new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build())));
    public static final Item ENDER_WHALE_MEAT = register("ender_whale_meat", new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).effect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0), 0.5F).build())));
    public static final Item COOKED_ENDER_WHALE_MEAT = register("cooked_ender_whale_meat", new Item(
            new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.9F).effect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1.0F).build())));
    public static final Item RHINO_MEAT = register("rhino_meat", new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build())));
    public static final Item COOKED_RHINO_MEAT = register("cooked_rhino_meat", new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build())));
    public static final Item RAW_VENISON = register("raw_venison", new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build())));
    public static final Item COOKED_VENISON = register("cooked_venison", new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build())));
    public static final Item WARTHOG_MEAT = register("warthog_meat", new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build())));
    public static final Item COOKED_WARTHOG_MEAT = register("cooked_warthog_meat",
            new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0F).build())));
    public static final Item SKUNK_MEAT = register("skunk_meat", new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build())));
    public static final Item COOKED_SKUNK_MEAT = register("cooked_skunk_meat", new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.8F).build())));

    // Armor
    public static final Item GILDED_NETHERITE_HELMET = register("gilded_netherite_helmet",
            new GildedNetheriteArmor(AdventureArmorMaterials.GILDED_NETHERITE, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(37)).fireResistant()));
    public static final Item GILDED_NETHERITE_CHESTPLATE = register("gilded_netherite_chestplate",
            new GildedNetheriteArmor(AdventureArmorMaterials.GILDED_NETHERITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(37)).fireResistant()));
    public static final Item GILDED_NETHERITE_LEGGINGS = register("gilded_netherite_leggings",
            new GildedNetheriteArmor(AdventureArmorMaterials.GILDED_NETHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(37)).fireResistant()));
    public static final Item GILDED_NETHERITE_BOOTS = register("gilded_netherite_boots",
            new GildedNetheriteArmor(AdventureArmorMaterials.GILDED_NETHERITE, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(37)).fireResistant()));

    // Tool
    public static final Item GILDED_NETHERITE_SWORD = register("gilded_netherite_sword", new SwordItem(AdventureToolMaterials.GILDED_NETHERITE, new Item.Properties().attributes(SwordItem.createAttributes(AdventureToolMaterials.GILDED_NETHERITE, 3, -2.4f)).fireResistant()));
    public static final Item GILDED_NETHERITE_SHOVEL = register("gilded_netherite_shovel", new ShovelItem(AdventureToolMaterials.GILDED_NETHERITE, new Item.Properties().attributes(ShovelItem.createAttributes(AdventureToolMaterials.GILDED_NETHERITE, 1.5f, -3.0f)).fireResistant()));
    public static final Item GILDED_NETHERITE_PICKAXE = register("gilded_netherite_pickaxe", new PickaxeItem(AdventureToolMaterials.GILDED_NETHERITE, new Item.Properties().attributes(PickaxeItem.createAttributes(AdventureToolMaterials.GILDED_NETHERITE, 1.0f, -2.8f)).fireResistant()));
    public static final Item GILDED_NETHERITE_AXE = register("gilded_netherite_axe", new AxeItem(AdventureToolMaterials.GILDED_NETHERITE, new Item.Properties().attributes(AxeItem.createAttributes(AdventureToolMaterials.GILDED_NETHERITE, 5.0f, -3.0f)).fireResistant()));
    public static final Item GILDED_NETHERITE_HOE = register("gilded_netherite_hoe", new HoeItem(AdventureToolMaterials.GILDED_NETHERITE, new Item.Properties().attributes(HoeItem.createAttributes(AdventureToolMaterials.GILDED_NETHERITE, -4f, -2.0f)).fireResistant()));

    private static Item register(String id, Item item) {
        return register(AdventureMain.identifierOf(id), item);
    }

    private static Item register(ResourceLocation id, Item item) {
        ItemGroupEvents.modifyEntriesEvent(ADVENTUREZ_ITEM_GROUP).register(entries -> entries.accept(item));
        return Registry.register(BuiltInRegistries.ITEM, id, item);
    }

    private static <T> DataComponentType<T> registerComponent(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, AdventureMain.identifierOf(id), builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ADVENTUREZ_ITEM_GROUP,
                FabricItemGroup.builder().icon(() -> new ItemStack(ItemInit.HANDBOOK)).title(Component.translatable("item.adventurez.item_group")).build());

        FabricBrewingRecipeRegistryBuilder.BUILD.register((builder) -> {
            builder.addMix(Potions.AWKWARD, ItemInit.ORC_SKIN, Potions.TURTLE_MASTER);
            builder.addMix(Potions.AWKWARD, ItemInit.ENDER_WHALE_SKIN, Potions.SLOW_FALLING);
        });
    }

}