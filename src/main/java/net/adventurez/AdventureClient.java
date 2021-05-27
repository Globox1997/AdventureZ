package net.adventurez;

import net.adventurez.init.KeybindInit;
import net.adventurez.init.ModelProviderInit;
import net.adventurez.init.RenderInit;
import net.adventurez.network.GeneralPacket;
import net.fabricmc.api.ClientModInitializer;

public class AdventureClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    GeneralPacket.init();
    KeybindInit.init();
    ModelProviderInit.init();
    RenderInit.init();
  }

}