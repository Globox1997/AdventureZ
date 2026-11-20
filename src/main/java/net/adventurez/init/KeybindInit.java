package net.adventurez.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.adventurez.entity.DragonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.adventurez.AdventureMain;

@Environment(EnvType.CLIENT)
public class KeybindInit {

    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(AdventureMain.identifierOf("keybind"));

    public static KeyMapping dragonFlyDownKeyBind;
    public static KeyMapping dragonFireBreathBind;

    public static void init() {
        // Keybinds
        dragonFlyDownKeyBind = new KeyMapping("key.adventurez.dragonflydown", InputConstants.Type.KEYBOARD, InputConstants.KEY_LALT, CATEGORY);
        dragonFireBreathBind = new KeyMapping("key.adventurez.dragonfirebreath", InputConstants.Type.KEYBOARD, InputConstants.KEY_GRAVE, CATEGORY);
        // Registering
        KeyMappingHelper.registerKeyMapping(dragonFlyDownKeyBind);
        KeyMappingHelper.registerKeyMapping(dragonFireBreathBind);
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
