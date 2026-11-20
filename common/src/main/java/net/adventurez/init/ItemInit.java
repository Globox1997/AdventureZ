package net.adventurez.init;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.mojang.serialization.Codec;

import net.adventurez.AdventureMain;
import net.adventurez.item.*;
import net.adventurez.item.component.GildedActivationComponent;
import net.adventurez.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;

public class ItemInit {

    // Item Group
    public static final ResourceKey<CreativeModeTab> ADVENTUREZ_ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath("adventurez", "item_group"));

    public static final List<Item> ITEM_GROUP_ITEMS = new ArrayList<>();
    private static final Map<Identifier, DataComponentType<?>> DATA_COMPONENTS = new LinkedHashMap<>();

    // Components
    public static final DataComponentType<Boolean> VOID_DROP = registerComponent("void_drop", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> LAVA_LIGHT = registerComponent("lava_light", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<GildedActivationComponent> GILDED_DATA = registerComponent("gilded_data",
            builder -> builder.persistent(GildedActivationComponent.CODEC).networkSynchronized(GildedActivationComponent.PACKET_CODEC));

    // Items
    public static Item GILDED_BLACKSTONE_SHARD;
    public static Item BLACKSTONE_GOLEM_HEART;
    public static Item BLACKSTONE_GOLEM_ARM;
    public static Item GILDED_NETHERITE_FRAGMENT;
    public static Item PRIME_EYE;
    public static Item ORC_SKIN;
    public static Item DRAGON_SADDLE;
    public static Item SOURCE_STONE;
    public static Item CHORUS_FRUIT_ON_A_STICK;
    public static Item ENDER_FLUTE;
    public static Item IGUANA_HIDE;
    public static Item MAMMOTH_LEATHER;
    public static Item ENDER_WHALE_SKIN;
    public static Item IVORY_ARROW;
    public static Item MAMMOTH_TUSK;
    public static Item RHINO_LEATHER;
    public static Item WARTHOG_SHELL_PIECE;
    public static Item HANDBOOK;
    public static Item GILDED_UPGRADE_SMITHING_TEMPLATE;

    // Food
    public static Item MAMMOTH_MEAT;
    public static Item COOKED_MAMMOTH_MEAT;
    public static Item IGUANA_MEAT;
    public static Item COOKED_IGUANA_MEAT;
    public static Item ENDER_WHALE_MEAT;
    public static Item COOKED_ENDER_WHALE_MEAT;
    public static Item RHINO_MEAT;
    public static Item COOKED_RHINO_MEAT;
    public static Item RAW_VENISON;
    public static Item COOKED_VENISON;
    public static Item WARTHOG_MEAT;
    public static Item COOKED_WARTHOG_MEAT;
    public static Item SKUNK_MEAT;
    public static Item COOKED_SKUNK_MEAT;

    // Armor
    public static Item GILDED_NETHERITE_HELMET;
    public static Item GILDED_NETHERITE_CHESTPLATE;
    public static Item GILDED_NETHERITE_LEGGINGS;
    public static Item GILDED_NETHERITE_BOOTS;

    // Tool
    public static Item GILDED_NETHERITE_SWORD;
    public static Item GILDED_NETHERITE_SHOVEL;
    public static Item GILDED_NETHERITE_PICKAXE;
    public static Item GILDED_NETHERITE_AXE;
    public static Item GILDED_NETHERITE_HOE;

    public static void registerDataComponents(BiConsumer<Identifier, DataComponentType<?>> registrar) {
        DATA_COMPONENTS.forEach(registrar);
    }

    public static void registerItems(BiConsumer<Identifier, Item> registrar) {
        GILDED_BLACKSTONE_SHARD = register(registrar, "gilded_blackstone_shard", properties -> new GildedBlackstoneShard(properties, () -> EntityInit.GILDED_BLACKSTONE_SHARD), new Item.Properties().fireResistant());
        BLACKSTONE_GOLEM_HEART = register(registrar, "blackstone_golem_heart", BlackstoneGolemHeart::new, new Item.Properties().fireResistant());
        BLACKSTONE_GOLEM_ARM = register(registrar, "blackstone_golem_arm", BlackstoneGolemArm::new, new Item.Properties().durability(2506).repairable(Items.NETHERITE_SCRAP).fireResistant());
        GILDED_NETHERITE_FRAGMENT = register(registrar, "gilded_netherite_fragment", GildedNetheriteFragment::new, new Item.Properties().fireResistant());
        PRIME_EYE = register(registrar, "prime_eye", PrimeEyeItem::new, new Item.Properties().durability(64));
        ORC_SKIN = register(registrar, "orc_skin", Item::new, new Item.Properties());
        DRAGON_SADDLE = register(registrar, "dragon_saddle", Item::new, new Item.Properties().stacksTo(1));
        SOURCE_STONE = register(registrar, "source_stone", SourceStone::new, new Item.Properties().stacksTo(1));
        EntityInit.registerSpawnEggs(registrar);
        CHORUS_FRUIT_ON_A_STICK = register(registrar, "chorus_fruit_on_a_stick", properties -> new FoodOnAStickItem<>(EntityInit.ENDER_WHALE, 1, properties), new Item.Properties().durability(100));
        ENDER_FLUTE = register(registrar, "ender_flute", EnderFlute::new, new Item.Properties().durability(32));
        IGUANA_HIDE = register(registrar, "iguana_hide", Item::new, new Item.Properties());
        MAMMOTH_LEATHER = register(registrar, "mammoth_fur", Item::new, new Item.Properties());
        ENDER_WHALE_SKIN = register(registrar, "ender_whale_skin", Item::new, new Item.Properties());
        IVORY_ARROW = register(registrar, "ivory_arrow", ArrowItem::new, new Item.Properties());
        MAMMOTH_TUSK = register(registrar, "mammoth_tusk", Item::new, new Item.Properties());
        RHINO_LEATHER = register(registrar, "rhino_leather", Item::new, new Item.Properties());
        WARTHOG_SHELL_PIECE = register(registrar, "warthog_shell_piece", Item::new, new Item.Properties());
        HANDBOOK = register(registrar, "handbook", Handbook::new, new Item.Properties().stacksTo(1));
        GILDED_UPGRADE_SMITHING_TEMPLATE = register(registrar, "gilded_upgrade_smithing_template",
                properties -> new SmithingTemplateItem(Component.translatable("item.minecraft.smithing_template.gilded_upgrade.applies_to").withStyle(ChatFormatting.BLUE),
                        Component.translatable("item.minecraft.smithing_template.gilded_upgrade.ingredients").withStyle(ChatFormatting.BLUE),
                        Component.translatable("item.smithing_template.gilded_upgrade.base_slot_description"),
                        Component.translatable("item.adventurez.smithing_template.gilded_upgrade.additions_slot_description"),
                        List.of(Identifier.withDefaultNamespace("container/slot/helmet"), Identifier.withDefaultNamespace("container/slot/chestplate"),
                                Identifier.withDefaultNamespace("container/slot/leggings"), Identifier.withDefaultNamespace("container/slot/boots")),
                        List.of(AdventureMain.identifierOf("container/slot/gilded_netherite_fragment")), properties),
                new Item.Properties());
        MAMMOTH_MEAT = register(registrar, "mammoth_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build()));
        COOKED_MAMMOTH_MEAT = register(registrar, "cooked_mammoth_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build()));
        IGUANA_MEAT = register(registrar, "iguana_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build(),
                Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F)).build()));
        COOKED_IGUANA_MEAT = register(registrar, "cooked_iguana_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build()));
        ENDER_WHALE_MEAT = register(registrar, "ender_whale_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build(),
                Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0), 0.5F)).build()));
        COOKED_ENDER_WHALE_MEAT = register(registrar, "cooked_ender_whale_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.9F).build(),
                Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1.0F)).build()));
        RHINO_MEAT = register(registrar, "rhino_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build()));
        COOKED_RHINO_MEAT = register(registrar, "cooked_rhino_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build()));
        RAW_VENISON = register(registrar, "raw_venison", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build()));
        COOKED_VENISON = register(registrar, "cooked_venison", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build()));
        WARTHOG_MEAT = register(registrar, "warthog_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build()));
        COOKED_WARTHOG_MEAT = register(registrar, "cooked_warthog_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build(),
                Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED, 600, 0), 1.0F)).build()));
        SKUNK_MEAT = register(registrar, "skunk_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build()));
        COOKED_SKUNK_MEAT = register(registrar, "cooked_skunk_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.8F).build()));
        GILDED_NETHERITE_HELMET = register(registrar, "gilded_netherite_helmet", GildedNetheriteArmor::new,
                new Item.Properties().humanoidArmor(AdventureArmorMaterials.GILDED_NETHERITE, ArmorType.HELMET).fireResistant());
        GILDED_NETHERITE_CHESTPLATE = register(registrar, "gilded_netherite_chestplate", GildedNetheriteArmor::new,
                new Item.Properties().humanoidArmor(AdventureArmorMaterials.GILDED_NETHERITE, ArmorType.CHESTPLATE).fireResistant());
        GILDED_NETHERITE_LEGGINGS = register(registrar, "gilded_netherite_leggings", GildedNetheriteArmor::new,
                new Item.Properties().humanoidArmor(AdventureArmorMaterials.GILDED_NETHERITE, ArmorType.LEGGINGS).fireResistant());
        GILDED_NETHERITE_BOOTS = register(registrar, "gilded_netherite_boots", GildedNetheriteArmor::new,
                new Item.Properties().humanoidArmor(AdventureArmorMaterials.GILDED_NETHERITE, ArmorType.BOOTS).fireResistant());
        GILDED_NETHERITE_SWORD = register(registrar, "gilded_netherite_sword", Item::new, new Item.Properties().sword(AdventureToolMaterials.GILDED_NETHERITE, 3, -2.4f).fireResistant());
        GILDED_NETHERITE_SHOVEL = register(registrar, "gilded_netherite_shovel", Item::new, new Item.Properties().shovel(AdventureToolMaterials.GILDED_NETHERITE, 1.5f, -3.0f).fireResistant());
        GILDED_NETHERITE_PICKAXE = register(registrar, "gilded_netherite_pickaxe", Item::new, new Item.Properties().pickaxe(AdventureToolMaterials.GILDED_NETHERITE, 1.0f, -2.8f).fireResistant());
        GILDED_NETHERITE_AXE = register(registrar, "gilded_netherite_axe", Item::new, new Item.Properties().axe(AdventureToolMaterials.GILDED_NETHERITE, 5.0f, -3.0f).fireResistant());
        GILDED_NETHERITE_HOE = register(registrar, "gilded_netherite_hoe", Item::new, new Item.Properties().hoe(AdventureToolMaterials.GILDED_NETHERITE, -4f, -2.0f).fireResistant());
    }

    public static void registerCreativeModeTabs(BiConsumer<Identifier, CreativeModeTab> registrar) {
        registrar.accept(ADVENTUREZ_ITEM_GROUP.identifier(),
                Services.PLATFORM.createCreativeModeTab(Component.translatable("item.adventurez.item_group"), () -> new ItemStack(ItemInit.HANDBOOK), () -> ITEM_GROUP_ITEMS));
    }

    public static Item register(BiConsumer<Identifier, Item> registrar, String id, Function<Item.Properties, Item> factory, Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, AdventureMain.identifierOf(id));
        Item item = factory.apply(properties.setId(key));
        registrar.accept(key.identifier(), item);
        ITEM_GROUP_ITEMS.add(item);
        return item;
    }

    private static <T> DataComponentType<T> registerComponent(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        DataComponentType<T> componentType = builderOperator.apply(DataComponentType.builder()).build();
        DATA_COMPONENTS.put(AdventureMain.identifierOf(id), componentType);
        return componentType;
    }

}
