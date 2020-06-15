package net.adventurez;

import net.adventurez.init.*;
import net.fabricmc.api.ModInitializer;

public class AdventureMain implements ModInitializer {

  @Override
  public void onInitialize() {

    BlockInit.init();
    EntityInit.init();
    ItemInit.init();
    SoundInit.init();

  }
}

// You are LOVED!!!
// Jesus loves you unconditional!