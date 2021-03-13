package net.adventurez.network;

import net.adventurez.entity.DragonEntity;
import net.adventurez.init.ItemInit;
import net.adventurez.item.armor.StoneGolemArmor;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class KeybindPacket {

  public static final Identifier ARMOR_PACKET = new Identifier("adventurez", "armor_activating");
  public static final Identifier FLY_DOWN_PACKET = new Identifier("adventurez", "fly_down_dragon");

  public static void init() {

    ServerPlayNetworking.registerGlobalReceiver(ARMOR_PACKET, (server, player, handler, buffer, sender) -> {
      ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
      if (!stack.isEmpty() && stack.getItem() == ItemInit.STONE_GOLEM_CHESTPLATE) {
        StoneGolemArmor.fireActive(player, stack);
      }
    });

    ServerPlayNetworking.registerGlobalReceiver(FLY_DOWN_PACKET, (server, player, handler, buffer, sender) -> {
      if (player.getVehicle() != null && player.getVehicle() instanceof DragonEntity) {
        DragonEntity dragonEntity = (DragonEntity) player.getVehicle();
        dragonEntity.setKeyBind(buffer.readString());
      }

    });

  }

}