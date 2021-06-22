package net.adventurez.network;

import net.adventurez.entity.DragonEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.ItemInit;
import net.adventurez.item.armor.StoneGolemArmor;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class KeybindPacket {

    public static final Identifier ARMOR_PACKET = new Identifier("adventurez", "armor_activating");
    public static final Identifier FIRE_BREATH_PACKET = new Identifier("adventurez", "fire_breath");

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(ARMOR_PACKET, (server, player, handler, buffer, sender) -> {
            ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
            if (!stack.isEmpty() && stack.getItem() == ItemInit.STONE_GOLEM_CHESTPLATE && !ConfigInit.CONFIG.disable_armor_bonus) {
                StoneGolemArmor.fireActive(player, stack);
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(FIRE_BREATH_PACKET, (server, player, handler, buffer, sender) -> {
            ((DragonEntity) player.getVehicle()).fireBreathActive = true;
        });

    }

}