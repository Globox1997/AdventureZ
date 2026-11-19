package net.adventurez.init;

import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;
import net.adventurez.entity.DragonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

@Environment(EnvType.CLIENT)
public class KeybindInit {

    public static KeyMapping dragonFlyDownKeyBind;
    public static KeyMapping dragonFireBreathBind;

    public static void init() {
        // Keybinds
        dragonFlyDownKeyBind = new KeyMapping("key.adventurez.dragonflydown", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "category.adventurez.keybind");
        dragonFireBreathBind = new KeyMapping("key.adventurez.dragonfirebreath", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "category.adventurez.keybind");
        // Registering
        KeyBindingHelper.registerKeyBinding(dragonFlyDownKeyBind);
        KeyBindingHelper.registerKeyBinding(dragonFireBreathBind);
        // Callback
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (dragonFlyDownKeyBind.isDown()) {
                DragonEntity.flyDragonDown(client.player, dragonFlyDownKeyBind.saveString());
                return;
            } else if (dragonFireBreathBind.consumeClick()) {
                DragonEntity.dragonFireBreath(client.player);
                return;
            }
        });
    }

}
