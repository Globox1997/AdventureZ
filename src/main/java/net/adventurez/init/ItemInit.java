package net.adventurez.init;

import net.adventurez.item.*;
import net.adventurez.item.armor.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemInit {

        public static final GildedStoneItem GILDED_STONE_ITEM = new GildedStoneItem(
                        new Item.Settings().group(ItemGroup.MISC), () -> EntityInit.GILDEDSTONE_ENTITY);
        public static final StoneGolemHeart STONE_GOLEM_HEART = new StoneGolemHeart(
                        new Item.Settings().group(ItemGroup.MISC));
        public static final StoneGolemArm STONE_GOLEM_ARM = new StoneGolemArm(
                        new Item.Settings().group(ItemGroup.COMBAT).maxDamage(2506));
        public static final GildedNetheriteFragment GILDED_NETHERITE_FRAGMENT = new GildedNetheriteFragment(
                        new Item.Settings().group(ItemGroup.MISC).fireproof());
        public static final PrimeEyeItem PRIME_EYE_ITEM = new PrimeEyeItem(new Item.Settings().group(ItemGroup.MISC));

        // Armor
        public static final ArmorMaterial STONE_GOLEM_ARMOR_MATERIAL = new StoneGolemArmorMaterial();
        public static final Item STONE_GOLEM_HELMET = register("stone_golem_helmet",
                        new StoneGolemArmor(STONE_GOLEM_ARMOR_MATERIAL, EquipmentSlot.HEAD));
        public static final Item STONE_GOLEM_CHESTPLATE = register("stone_golem_chestplate",
                        new StoneGolemArmor(STONE_GOLEM_ARMOR_MATERIAL, EquipmentSlot.CHEST));
        public static final Item STONE_GOLEM_LEGGINGS = register("stone_golem_leggings",
                        new StoneGolemArmor(STONE_GOLEM_ARMOR_MATERIAL, EquipmentSlot.LEGS));
        public static final Item STONE_GOLEM_BOOTS = register("stone_golem_boots",
                        new StoneGolemArmor(STONE_GOLEM_ARMOR_MATERIAL, EquipmentSlot.FEET));

        private static Item register(String id, Item item) {
                return register(new Identifier("adventurez", id), item);
        }

        private static Item register(Identifier id, Item item) {
                return Registry.register(Registry.ITEM, id, item);
        }

        public static void init() {
                Registry.register(Registry.ITEM, new Identifier("adventurez", "gilded_stone_item"), GILDED_STONE_ITEM);
                Registry.register(Registry.ITEM, new Identifier("adventurez", "stone_golem_heart"), STONE_GOLEM_HEART);
                Registry.register(Registry.ITEM, new Identifier("adventurez", "stone_golem_arm"), STONE_GOLEM_ARM);
                Registry.register(Registry.ITEM, new Identifier("adventurez", "gilded_netherite_fragment"),
                                GILDED_NETHERITE_FRAGMENT);
                Registry.register(Registry.ITEM, new Identifier("adventurez", "prime_eye_item"), PRIME_EYE_ITEM);
        }

}