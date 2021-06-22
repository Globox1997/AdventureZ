package net.adventurez.init;

import org.lwjgl.glfw.GLFW;

import io.netty.buffer.Unpooled;
import net.adventurez.entity.DragonEntity;
import net.adventurez.item.armor.StoneGolemArmor;
import net.adventurez.network.KeybindPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;

public class KeybindInit {
    public static KeyBinding dragonFlyDownKeyBind;
    public static KeyBinding armorKeyBind;
    public static KeyBinding dragonFireBreathBind;
    public static boolean keyBoolean;

    public static void init() {
        // Keybinds
        armorKeyBind = new KeyBinding("key.adventurez.activatearmor", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "category.adventurez.keybind");
        dragonFlyDownKeyBind = new KeyBinding("key.adventurez.dragonflydown", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "category.adventurez.keybind");
        dragonFireBreathBind = new KeyBinding("key.adventurez.dragonfirebreath", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "category.adventurez.keybind");
        // Registering
        KeyBindingHelper.registerKeyBinding(armorKeyBind);
        KeyBindingHelper.registerKeyBinding(dragonFlyDownKeyBind);
        KeyBindingHelper.registerKeyBinding(dragonFireBreathBind);
        // Callback
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (dragonFlyDownKeyBind.isPressed()) {
                flyDragonDown(client.player, dragonFlyDownKeyBind.getBoundKeyTranslationKey());
            } else if (armorKeyBind.wasPressed()) {
                if (!keyBoolean) {
                    activateArmor(client.player);
                }
                keyBoolean = true;
            } else if (dragonFireBreathBind.wasPressed()) {
                if (!keyBoolean) {
                    dragonFireBreath(client.player);
                }
                keyBoolean = true;
            } else {
                keyBoolean = false;
            }
        });
    }

    public static void flyDragonDown(ClientPlayerEntity player, String keyString) {
        if (player.getVehicle() != null && player.getVehicle() instanceof DragonEntity) {
            ((DragonEntity) player.getVehicle()).setKeyBind(keyString);
        }
    }

    public static void dragonFireBreath(ClientPlayerEntity player) {
        if (player.getVehicle() != null && player.getVehicle() instanceof DragonEntity && player.getVehicle().isAlive()) {
            DragonEntity dragonEntity = (DragonEntity) player.getVehicle();
            if (dragonEntity.getDataTracker().get(DragonEntity.DRAGON_SIZE) >= 3) {
                // Call on Server
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                buf.writeInt(player.getVehicle().getId());
                CustomPayloadC2SPacket packet = new CustomPayloadC2SPacket(KeybindPacket.FIRE_BREATH_PACKET, buf);
                MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
                // Call on client
                ((DragonEntity) player.getVehicle()).fireBreathActive = true;
            }
        }
    }

    public static void activateArmor(ClientPlayerEntity player) {
        if (StoneGolemArmor.fullGolemArmor(player)) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            CustomPayloadC2SPacket packet = new CustomPayloadC2SPacket(KeybindPacket.ARMOR_PACKET, buf);
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
        }
    }

}