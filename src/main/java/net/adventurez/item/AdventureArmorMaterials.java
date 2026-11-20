package net.adventurez.item;

import net.adventurez.AdventureMain;
import net.adventurez.init.TagInit;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

public class AdventureArmorMaterials {

    public static final ResourceKey<EquipmentAsset> GILDED_NETHERITE_ASSET = ResourceKey.create(EquipmentAssets.ROOT_ID, AdventureMain.identifierOf("gilded_netherite"));

    public static final ArmorMaterial GILDED_NETHERITE = new ArmorMaterial(37, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
        map.put(ArmorType.BODY, 11);
    }), 25, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.5f, 0.1f, TagInit.REPAIRS_GILDED_NETHERITE, GILDED_NETHERITE_ASSET);

}
