package net.adventurez.init;

import org.lwjgl.glfw.GLFW;

import net.adventurez.entity.DragonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

@Environment(EnvType.CLIENT)
public class KeybindInit {

    public static KeyBinding dragonFlyDownKeyBind;
    public static KeyBinding dragonFireBreathBind;

    public static void init() {
        // Keybinds
        dragonFlyDownKeyBind = new KeyBinding("key.adventurez.dragonflydown", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "category.adventurez.keybind");
        dragonFireBreathBind = new KeyBinding("key.adventurez.dragonfirebreath", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "category.adventurez.keybind");
        // Registering
        KeyBindingHelper.registerKeyBinding(dragonFlyDownKeyBind);
        KeyBindingHelper.registerKeyBinding(dragonFireBreathBind);
        // Callback
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (dragonFlyDownKeyBind.isPressed()) {
                DragonEntity.flyDragonDown(client.player, dragonFlyDownKeyBind.getBoundKeyTranslationKey());
                return;
            } else if (dragonFireBreathBind.wasPressed()) {
                DragonEntity.dragonFireBreath(client.player);
                return;
            }
        });
    }

}
