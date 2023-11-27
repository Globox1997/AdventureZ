package net.adventurez;

import net.adventurez.init.KeybindInit;
import net.adventurez.init.ModelInit;
import net.adventurez.init.RenderInit;
import net.adventurez.network.AdventureClientPacket;
import net.fabricmc.api.ClientModInitializer;

public class AdventureClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AdventureClientPacket.init();
        KeybindInit.init();
        ModelInit.init();
        RenderInit.init();
    }

}