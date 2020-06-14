package net.adventurez.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundInit {
  public static final Identifier GOLEMIDLE = new Identifier("adventurez:golem_idle");
  public static final Identifier ROCK_IMPACT = new Identifier("adventurez:rock_impact");
  public static SoundEvent GOLEMIDLEEVENT = new SoundEvent(GOLEMIDLE);
  public static SoundEvent ROCK_IMPACT_EVENT = new SoundEvent(ROCK_IMPACT);

  public static void init() {
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEMIDLE, GOLEMIDLEEVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.ROCK_IMPACT, ROCK_IMPACT_EVENT);
  }
}