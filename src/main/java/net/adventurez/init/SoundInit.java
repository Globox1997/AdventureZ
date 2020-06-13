package net.adventurez.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundInit {
  public static final Identifier GOLEMIDLE = new Identifier("adventurez:golem_idle");
  public static SoundEvent GOLEMIDLEEVENT = new SoundEvent(GOLEMIDLE);

  public static void init() {
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEMIDLE, GOLEMIDLEEVENT);
  }
}