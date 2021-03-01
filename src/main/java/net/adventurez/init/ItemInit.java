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

        public static final Item GILDED_STONE_ITEM = register("gilded_stone_item", new GildedStoneItem(
                        new Item.Settings().group(ItemGroup.MISC), () -> EntityInit.GILDEDSTONE_ENTITY));
        public static final Item STONE_GOLEM_HEART = register("stone_golem_heart",
                        new StoneGolemHeart(new Item.Settings().group(ItemGroup.MISC)));
        public static final Item STONE_GOLEM_ARM = register("stone_golem_arm",
                        new StoneGolemArm(new Item.Settings().group(ItemGroup.COMBAT).maxDamage(2506)));
        public static final Item GILDED_NETHERITE_FRAGMENT = register("gilded_netherite_fragment",
                        new GildedNetheriteFragment(new Item.Settings().group(ItemGroup.MISC).fireproof()));
        public static final Item PRIME_EYE_ITEM = register("prime_eye_item",
                        new PrimeEyeItem(new Item.Settings().group(ItemGroup.MISC)));
        public static final Item ORC_SKIN_ITEM = register("orc_skin_item",
                        new OrcSkinItem(new Item.Settings().group(ItemGroup.MISC)));

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
        }

}