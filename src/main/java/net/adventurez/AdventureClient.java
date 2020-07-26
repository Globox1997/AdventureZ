package net.adventurez;

import net.adventurez.init.KeybindInit;
import net.adventurez.init.ModelProviderInit;
import net.adventurez.init.RenderInit;

import net.fabricmc.api.ClientModInitializer;

public class AdventureClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    KeybindInit.init();
    RenderInit.init();
    ModelProviderInit.init();
  }

}