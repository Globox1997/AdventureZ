package net.adventurez.init;

import java.util.function.Consumer;

import com.mojang.blaze3d.platform.InputConstants;
import net.adventurez.entity.DragonEntity;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.adventurez.AdventureMain;

public class KeybindInit {

    public static final KeyMapping.Category CATEGORY = new KeyMapping.Category(AdventureMain.identifierOf("keybind"));

    public static KeyMapping dragonFlyDownKeyBind;
    public static KeyMapping dragonFireBreathBind;

    public static void init(Consumer<KeyMapping> registrar) {
        dragonFlyDownKeyBind = new KeyMapping("key.adventurez.dragonflydown", InputConstants.Type.KEYBOARD, InputConstants.KEY_LALT, CATEGORY);
        dragonFireBreathBind = new KeyMapping("key.adventurez.dragonfirebreath", InputConstants.Type.KEYBOARD, InputConstants.KEY_GRAVE, CATEGORY);
        registrar.accept(dragonFlyDownKeyBind);
        registrar.accept(dragonFireBreathBind);
    }

    public static void onClientTick(Minecraft client) {
        if (dragonFlyDownKeyBind.isDown()) {
            DragonEntity.flyDragonDown(client.player, dragonFlyDownKeyBind.saveString());
            return;
        } else if (dragonFireBreathBind.consumeClick()) {
            DragonEntity.dragonFireBreath(client.player);
            return;
        }
    }

}
