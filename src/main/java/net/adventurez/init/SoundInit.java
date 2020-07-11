package net.adventurez.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundInit {
  // Stone Golem
  public static final Identifier GOLEM_IDLE = new Identifier("adventurez:golem_idle");
  public static final Identifier GOLEM_WALK = new Identifier("adventurez:golem_walk");
  public static final Identifier GOLEM_HIT = new Identifier("adventurez:golem_hit");
  public static final Identifier GOLEM_ROAR = new Identifier("adventurez:golem_roar");
  public static final Identifier GOLEM_DEATH = new Identifier("adventurez:golem_death");
  public static final Identifier GOLEM_SPAWN = new Identifier("adventurez:golem_spawn");
  public static SoundEvent GOLEM_IDLE_EVENT = new SoundEvent(GOLEM_IDLE);
  public static SoundEvent GOLEM_WALK_EVENT = new SoundEvent(GOLEM_WALK);
  public static SoundEvent GOLEM_HIT_EVENT = new SoundEvent(GOLEM_HIT);
  public static SoundEvent GOLEM_ROAR_EVENT = new SoundEvent(GOLEM_ROAR);
  public static SoundEvent GOLEM_DEATH_EVENT = new SoundEvent(GOLEM_DEATH);
  public static SoundEvent GOLEM_SPAWN_EVENT = new SoundEvent(GOLEM_SPAWN);
  // Item
  public static final Identifier ROCK_IMPACT = new Identifier("adventurez:rock_impact");
  public static final Identifier ROCK_THROW = new Identifier("adventurez:rock_throw");
  public static final Identifier HEART_BEAT = new Identifier("adventurez:heart_beat");
  public static SoundEvent ROCK_IMPACT_EVENT = new SoundEvent(ROCK_IMPACT);
  public static SoundEvent ROCK_THROW_EVENT = new SoundEvent(ROCK_THROW);
  public static SoundEvent HEART_BEAT_EVENT = new SoundEvent(HEART_BEAT);
  // Small Golem
  public static final Identifier SMALL_GOLEM_IDLE = new Identifier("adventurez:small_golem_idle");
  public static final Identifier SMALL_GOLEM_WALK = new Identifier("adventurez:small_golem_walk");
  public static final Identifier SMALL_GOLEM_HIT = new Identifier("adventurez:small_golem_hit");
  public static final Identifier SMALL_GOLEM_DEATH = new Identifier("adventurez:small_golem_death");
  public static SoundEvent SMALL_GOLEM_IDLE_EVENT = new SoundEvent(SMALL_GOLEM_IDLE);
  public static SoundEvent SMALL_GOLEM_WALK_EVENT = new SoundEvent(SMALL_GOLEM_WALK);
  public static SoundEvent SMALL_GOLEM_HIT_EVENT = new SoundEvent(SMALL_GOLEM_HIT);
  public static SoundEvent SMALL_GOLEM_DEATH_EVENT = new SoundEvent(SMALL_GOLEM_DEATH);
  // Piglin Beast
  public static final Identifier PIGLINBEAST_IDLE = new Identifier("adventurez:piglinbeast_idle");
  public static final Identifier PIGLINBEAST_DEATH = new Identifier("adventurez:piglinbeast_death");
  public static final Identifier PIGLINBEAST_HURT = new Identifier("adventurez:piglinbeast_hurt");
  public static final Identifier PIGLINBEAST_WALK = new Identifier("adventurez:piglinbeast_walk");
  public static final Identifier PIGLINBEAST_SHOUT = new Identifier("adventurez:piglinbeast_shout");
  public static final Identifier PIGLINBEAST_CLUBSWING = new Identifier("adventurez:piglinbeast_clubswing");
  public static SoundEvent PIGLINBEAST_IDLE_EVENT = new SoundEvent(PIGLINBEAST_IDLE);
  public static SoundEvent PIGLINBEAST_DEATH_EVENT = new SoundEvent(PIGLINBEAST_DEATH);
  public static SoundEvent PIGLINBEAST_HURT_EVENT = new SoundEvent(PIGLINBEAST_HURT);
  public static SoundEvent PIGLINBEAST_WALK_EVENT = new SoundEvent(PIGLINBEAST_WALK);
  public static SoundEvent PIGLINBEAST_SHOUT_EVENT = new SoundEvent(PIGLINBEAST_SHOUT);
  public static SoundEvent PIGLINBEAST_CLUBSWING_EVENT = new SoundEvent(PIGLINBEAST_CLUBSWING);

  public static void init() {
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEM_IDLE, GOLEM_IDLE_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEM_WALK, GOLEM_WALK_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEM_HIT, GOLEM_HIT_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEM_ROAR, GOLEM_ROAR_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEM_DEATH, GOLEM_DEATH_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.GOLEM_SPAWN, GOLEM_SPAWN_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.ROCK_IMPACT, ROCK_IMPACT_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.ROCK_THROW, ROCK_THROW_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.SMALL_GOLEM_IDLE, SMALL_GOLEM_IDLE_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.SMALL_GOLEM_WALK, SMALL_GOLEM_WALK_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.SMALL_GOLEM_HIT, SMALL_GOLEM_HIT_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.SMALL_GOLEM_DEATH, SMALL_GOLEM_DEATH_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.HEART_BEAT, HEART_BEAT_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.PIGLINBEAST_IDLE, PIGLINBEAST_IDLE_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.PIGLINBEAST_DEATH, PIGLINBEAST_DEATH_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.PIGLINBEAST_HURT, PIGLINBEAST_HURT_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.PIGLINBEAST_WALK, PIGLINBEAST_WALK_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.PIGLINBEAST_SHOUT, PIGLINBEAST_SHOUT_EVENT);
    Registry.register(Registry.SOUND_EVENT, SoundInit.PIGLINBEAST_CLUBSWING, PIGLINBEAST_CLUBSWING_EVENT);
  }
}