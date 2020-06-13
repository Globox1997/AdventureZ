package net.adventurez;

import net.adventurez.init.EntityInit;
import net.adventurez.init.SoundInit;
import net.fabricmc.api.ModInitializer;

public class AdventureMain implements ModInitializer {

  @Override
  public void onInitialize() {

    EntityInit.init();
    SoundInit.init();

  }
}

// You are LOVED!!!
// Jesus loves you unconditional!