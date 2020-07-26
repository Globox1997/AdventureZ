package net.adventurez.network;

import net.adventurez.init.ItemInit;
import net.adventurez.item.armor.StoneGolemArmor;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class KeybindPacket {

  public static final Identifier ARMOR_PACKET = new Identifier("adventurez", "armor_activating");

  public static void init() {

    ServerSidePacketRegistry.INSTANCE.register(ARMOR_PACKET, (context, buffer) -> {
      PlayerEntity player = context.getPlayer();
      ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
      if (!stack.isEmpty() && stack.getItem() == ItemInit.STONE_GOLEM_CHESTPLATE) {
        StoneGolemArmor.fireActive(player, stack);
      }
    });

  }

}