package net.adventurez.init;

import org.lwjgl.glfw.GLFW;

import io.netty.buffer.Unpooled;
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
  public static KeyBinding armorKeyBind;
  public static boolean armorKeyBoolean;

  public static void init() {
    armorKeyBind = new KeyBinding("key.adventurez.activatearmor", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X,
        "category.adventurez.test");
    KeyBindingHelper.registerKeyBinding(armorKeyBind);
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      if (armorKeyBind.wasPressed()) {
        if (!armorKeyBoolean) {
          activateArmor();
        }
        armorKeyBoolean = true;
      } else {
        armorKeyBoolean = false;
      }

    });
  }

  public static void activateArmor() {
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
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