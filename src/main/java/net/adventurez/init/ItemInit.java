package net.adventurez.init;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.mojang.serialization.Codec;

import net.adventurez.AdventureMain;
import net.adventurez.item.*;
import net.adventurez.item.component.GildedActivationComponent;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
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

    // Components
    public static final DataComponentType<Boolean> VOID_DROP = registerComponent("void_drop", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> LAVA_LIGHT = registerComponent("lava_light", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<GildedActivationComponent> GILDED_DATA = registerComponent("gilded_data",
            builder -> builder.persistent(GildedActivationComponent.CODEC).networkSynchronized(GildedActivationComponent.PACKET_CODEC));

    // Items
    public static final Item GILDED_BLACKSTONE_SHARD = register("gilded_blackstone_shard", properties -> new GildedBlackstoneShard(properties, () -> EntityInit.GILDED_BLACKSTONE_SHARD), new Item.Properties().fireResistant());
    public static final Item BLACKSTONE_GOLEM_HEART = register("blackstone_golem_heart", BlackstoneGolemHeart::new, new Item.Properties().fireResistant());
    public static final Item BLACKSTONE_GOLEM_ARM = register("blackstone_golem_arm", BlackstoneGolemArm::new, new Item.Properties().durability(2506).repairable(Items.NETHERITE_SCRAP).fireResistant());
    public static final Item GILDED_NETHERITE_FRAGMENT = register("gilded_netherite_fragment", GildedNetheriteFragment::new, new Item.Properties().fireResistant());
    public static final Item PRIME_EYE = register("prime_eye", PrimeEyeItem::new, new Item.Properties().durability(64));
    public static final Item ORC_SKIN = register("orc_skin", Item::new, new Item.Properties());
    public static final Item DRAGON_SADDLE = register("dragon_saddle", Item::new, new Item.Properties().stacksTo(1));
    public static final Item SOURCE_STONE = register("source_stone", SourceStone::new, new Item.Properties().stacksTo(1));
    public static final Item CHORUS_FRUIT_ON_A_STICK = register("chorus_fruit_on_a_stick", properties -> new FoodOnAStickItem<>(EntityInit.ENDER_WHALE, 1, properties), new Item.Properties().durability(100));
    public static final Item ENDER_FLUTE = register("ender_flute", EnderFlute::new, new Item.Properties().durability(32));
    public static final Item IGUANA_HIDE = register("iguana_hide", Item::new, new Item.Properties());
    public static final Item MAMMOTH_LEATHER = register("mammoth_fur", Item::new, new Item.Properties());
    public static final Item ENDER_WHALE_SKIN = register("ender_whale_skin", Item::new, new Item.Properties());
    public static final Item IVORY_ARROW = register("ivory_arrow", ArrowItem::new, new Item.Properties());
    public static final Item MAMMOTH_TUSK = register("mammoth_tusk", Item::new, new Item.Properties());
    public static final Item RHINO_LEATHER = register("rhino_leather", Item::new, new Item.Properties());
    public static final Item WARTHOG_SHELL_PIECE = register("warthog_shell_piece", Item::new, new Item.Properties());
    public static final Item HANDBOOK = register("handbook", Handbook::new, new Item.Properties().stacksTo(1));
    public static final Item GILDED_UPGRADE_SMITHING_TEMPLATE = register("gilded_upgrade_smithing_template",
            properties -> new SmithingTemplateItem(Component.translatable("item.minecraft.smithing_template.gilded_upgrade.applies_to").withStyle(ChatFormatting.BLUE),
                    Component.translatable("item.minecraft.smithing_template.gilded_upgrade.ingredients").withStyle(ChatFormatting.BLUE),
                    Component.translatable("item.smithing_template.gilded_upgrade.base_slot_description"),
                    Component.translatable("item.adventurez.smithing_template.gilded_upgrade.additions_slot_description"),
                    List.of(Identifier.withDefaultNamespace("container/slot/helmet"), Identifier.withDefaultNamespace("container/slot/chestplate"),
                            Identifier.withDefaultNamespace("container/slot/leggings"), Identifier.withDefaultNamespace("container/slot/boots")),
                    List.of(AdventureMain.identifierOf("container/slot/gilded_netherite_fragment")), properties),
            new Item.Properties());

    // Food
    public static final Item MAMMOTH_MEAT = register("mammoth_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build()));
    public static final Item COOKED_MAMMOTH_MEAT = register("cooked_mammoth_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build()));
    public static final Item IGUANA_MEAT = register("iguana_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build(),
            Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F)).build()));
    public static final Item COOKED_IGUANA_MEAT = register("cooked_iguana_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build()));
    public static final Item ENDER_WHALE_MEAT = register("ender_whale_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build(),
            Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0), 0.5F)).build()));
    public static final Item COOKED_ENDER_WHALE_MEAT = register("cooked_ender_whale_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.9F).build(),
            Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1.0F)).build()));
    public static final Item RHINO_MEAT = register("rhino_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build()));
    public static final Item COOKED_RHINO_MEAT = register("cooked_rhino_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build()));
    public static final Item RAW_VENISON = register("raw_venison", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build()));
    public static final Item COOKED_VENISON = register("cooked_venison", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build()));
    public static final Item WARTHOG_MEAT = register("warthog_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build()));
    public static final Item COOKED_WARTHOG_MEAT = register("cooked_warthog_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build(),
            Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED, 600, 0), 1.0F)).build()));
    public static final Item SKUNK_MEAT = register("skunk_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build()));
    public static final Item COOKED_SKUNK_MEAT = register("cooked_skunk_meat", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.8F).build()));

    // Armor
    public static final Item GILDED_NETHERITE_HELMET = register("gilded_netherite_helmet", GildedNetheriteArmor::new,
            new Item.Properties().humanoidArmor(AdventureArmorMaterials.GILDED_NETHERITE, ArmorType.HELMET).fireResistant());
    public static final Item GILDED_NETHERITE_CHESTPLATE = register("gilded_netherite_chestplate", GildedNetheriteArmor::new,
            new Item.Properties().humanoidArmor(AdventureArmorMaterials.GILDED_NETHERITE, ArmorType.CHESTPLATE).fireResistant());
    public static final Item GILDED_NETHERITE_LEGGINGS = register("gilded_netherite_leggings", GildedNetheriteArmor::new,
            new Item.Properties().humanoidArmor(AdventureArmorMaterials.GILDED_NETHERITE, ArmorType.LEGGINGS).fireResistant());
    public static final Item GILDED_NETHERITE_BOOTS = register("gilded_netherite_boots", GildedNetheriteArmor::new,
            new Item.Properties().humanoidArmor(AdventureArmorMaterials.GILDED_NETHERITE, ArmorType.BOOTS).fireResistant());

    // Tool
    public static final Item GILDED_NETHERITE_SWORD = register("gilded_netherite_sword", Item::new, new Item.Properties().sword(AdventureToolMaterials.GILDED_NETHERITE, 3, -2.4f).fireResistant());
    public static final Item GILDED_NETHERITE_SHOVEL = register("gilded_netherite_shovel", Item::new, new Item.Properties().shovel(AdventureToolMaterials.GILDED_NETHERITE, 1.5f, -3.0f).fireResistant());
    public static final Item GILDED_NETHERITE_PICKAXE = register("gilded_netherite_pickaxe", Item::new, new Item.Properties().pickaxe(AdventureToolMaterials.GILDED_NETHERITE, 1.0f, -2.8f).fireResistant());
    public static final Item GILDED_NETHERITE_AXE = register("gilded_netherite_axe", Item::new, new Item.Properties().axe(AdventureToolMaterials.GILDED_NETHERITE, 5.0f, -3.0f).fireResistant());
    public static final Item GILDED_NETHERITE_HOE = register("gilded_netherite_hoe", Item::new, new Item.Properties().hoe(AdventureToolMaterials.GILDED_NETHERITE, -4f, -2.0f).fireResistant());

    private static Item register(String id, Function<Item.Properties, Item> factory, Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, AdventureMain.identifierOf(id));
        Item item = factory.apply(properties.setId(key));
        CreativeModeTabEvents.modifyOutputEvent(ADVENTUREZ_ITEM_GROUP).register(entries -> entries.accept(item));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    private static <T> DataComponentType<T> registerComponent(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, AdventureMain.identifierOf(id), builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ADVENTUREZ_ITEM_GROUP,
                FabricCreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.HANDBOOK)).title(Component.translatable("item.adventurez.item_group")).build());
    }

}
