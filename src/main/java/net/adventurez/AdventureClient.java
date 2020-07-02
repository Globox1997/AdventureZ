package net.adventurez;

import net.adventurez.init.ModelProviderInit;
import net.adventurez.init.RenderInit;

import net.fabricmc.api.ClientModInitializer;

public class AdventureClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    RenderInit.init();
    ModelProviderInit.init();
  }

}