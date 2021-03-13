package net.adventurez.init;

import org.lwjgl.glfw.GLFW;

import io.netty.buffer.Unpooled;
import net.adventurez.entity.DragonEntity;
import net.adventurez.network.KeybindPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;

public class KeybindInit {
  public static KeyBinding dragonFlyDownKeyBind;
  public static KeyBinding armorKeyBind;
  public static boolean armorKeyBoolean;

  public static void init() {
    // Keybinds
    armorKeyBind = new KeyBinding("key.adventurez.activatearmor", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V,
        "category.adventurez.keybind");
    dragonFlyDownKeyBind = new KeyBinding("key.adventurez.dragonflydown", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT,
        "category.adventurez.keybind");
    // Registering
    KeyBindingHelper.registerKeyBinding(armorKeyBind);
    KeyBindingHelper.registerKeyBinding(dragonFlyDownKeyBind);
    // Callback
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      if (armorKeyBind.wasPressed()) {
        if (!armorKeyBoolean) {
          activateArmor(client.player);
        }
        armorKeyBoolean = true;
      } else {
        armorKeyBoolean = false;
      }
    });
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      if (dragonFlyDownKeyBind.isPressed()) {
        flyDragonDown(client.player, dragonFlyDownKeyBind.getBoundKeyTranslationKey());

      }
    });
  }

  public static void flyDragonDown(ClientPlayerEntity player, String keyString) {
    if (player.getVehicle() != null && player.getVehicle() instanceof DragonEntity) {
      PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
      buf.writeString(keyString);
      CustomPayloadC2SPacket packet = new CustomPayloadC2SPacket(KeybindPacket.FLY_DOWN_PACKET, buf);
      MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
    }
  }

  public static void activateArmor(ClientPlayerEntity player) {
    if (fullNetheriteArmor(player)) {
      PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
      CustomPayloadC2SPacket packet = new CustomPayloadC2SPacket(KeybindPacket.ARMOR_PACKET, buf);
      MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
    }
  }

  private static boolean fullNetheriteArmor(PlayerEntity playerEntity) {
    if (playerEntity.getEquippedStack(EquipmentSlot.HEAD).getItem().equals(ItemInit.STONE_GOLEM_HELMET)
        && playerEntity.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(ItemInit.STONE_GOLEM_CHESTPLATE)
        && playerEntity.getEquippedStack(EquipmentSlot.LEGS).getItem().equals(ItemInit.STONE_GOLEM_LEGGINGS)
        && playerEntity.getEquippedStack(EquipmentSlot.FEET).getItem().equals(ItemInit.STONE_GOLEM_BOOTS)) {
      return true;
    } else
      return false;
  }

}