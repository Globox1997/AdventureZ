package net.adventurez.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundInit {
  public static final Identifier GOLEM_IDLE = new Identifier("adventurez:golem_idle");
  public static final Identifier GOLEM_WALK = new Identifier("adventurez:golem_walk");
  public static final Identifier GOLEM_HIT = new Identifier("adventurez:golem_hit");
  public static final Identifier GOLEM_ROAR = new Identifier("adventurez:golem_roar");
  public static final Identifier GOLEM_DEATH = new Identifier("adventurez:golem_death");
  public static final Identifier ROCK_IMPACT = new Identifier("adventurez:rock_impact");
  public static SoundEvent GOLEM_IDLE_EVENT = new SoundEvent(GOLEM_IDLE);
  public static SoundEvent GOLEM_WALK_EVENT = new SoundEvent(GOLEM_WALK);
  public static SoundEvent GOLEM_HIT_EVENT = new SoundEvent(GOLEM_HIT);
  public static SoundEvent GOLEM_ROAR_EVENT = new SoundEvent(GOLEM_ROAR);
  public static SoundEvent GOLEM_DEATH_EVENT = new SoundEvent(GOLEM_DEATH);
  public static SoundEvent ROCK_IMPACT_EVENT = new SoundEvent(ROCK_IMPACT);

  public static void init() {
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEM_IDLE, GOLEM_IDLE_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEM_WALK, GOLEM_WALK_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEM_HIT, GOLEM_HIT_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEM_ROAR, GOLEM_ROAR_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEM_DEATH, GOLEM_DEATH_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.ROCK_IMPACT, ROCK_IMPACT_EVENT);
  }
}