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
    // Nightmare
    public static final Identifier NIGHTMARE_IDLE = new Identifier("adventurez:nightmare_idle");
    public static final Identifier NIGHTMARE_DEATH = new Identifier("adventurez:nightmare_death");
    public static final Identifier NIGHTMARE_HURT = new Identifier("adventurez:nightmare_hurt");
    public static final Identifier NIGHTMARE_ANGRY = new Identifier("adventurez:nightmare_angry");
    public static SoundEvent NIGHTMARE_IDLE_EVENT = new SoundEvent(NIGHTMARE_IDLE);
    public static SoundEvent NIGHTMARE_DEATH_EVENT = new SoundEvent(NIGHTMARE_DEATH);
    public static SoundEvent NIGHTMARE_HURT_EVENT = new SoundEvent(NIGHTMARE_HURT);
    public static SoundEvent NIGHTMARE_ANGRY_EVENT = new SoundEvent(NIGHTMARE_ANGRY);
    // Soul Reaper
    public static final Identifier SOULREAPER_IDLE = new Identifier("adventurez:soulreaper_idle");
    public static final Identifier SOULREAPER_HURT = new Identifier("adventurez:soulreaper_hurt");
    public static final Identifier SOULREAPER_DEATH = new Identifier("adventurez:soulreaper_death");
    public static SoundEvent SOULREAPER_IDLE_EVENT = new SoundEvent(SOULREAPER_IDLE);
    public static SoundEvent SOULREAPER_HURT_EVENT = new SoundEvent(SOULREAPER_HURT);
    public static SoundEvent SOULREAPER_DEATH_EVENT = new SoundEvent(SOULREAPER_DEATH);
    // Magic
    public static final Identifier SPELL_CAST_SHIELD = new Identifier("adventurez:spellcast_shield");
    public static final Identifier MAGIC_SHIELD_HIT = new Identifier("adventurez:magic_shield_hit");
    public static SoundEvent SPELL_CAST_SHIELD_EVENT = new SoundEvent(SPELL_CAST_SHIELD);
    public static SoundEvent MAGIC_SHIELD_HIT_EVENT = new SoundEvent(MAGIC_SHIELD_HIT);
    // The Eye
    public static final Identifier EYE_DEATH = new Identifier("adventurez:eye_death");
    public static final Identifier EYE_HURT = new Identifier("adventurez:eye_hurt");
    public static final Identifier EYE_IDLE = new Identifier("adventurez:eye_idle");
    public static final Identifier EYE_DEATH_PLATFORM = new Identifier("adventurez:eye_death_platform");
    public static SoundEvent EYE_DEATH_EVENT = new SoundEvent(EYE_DEATH);
    public static SoundEvent EYE_HURT_EVENT = new SoundEvent(EYE_HURT);
    public static SoundEvent EYE_IDLE_EVENT = new SoundEvent(EYE_IDLE);
    public static SoundEvent EYE_DEATH_PLATFORM_EVENT = new SoundEvent(EYE_DEATH_PLATFORM);
    // Fungus
    public static final Identifier FUNGUS_IDLE = new Identifier("adventurez:fungus_idle");
    public static final Identifier FUNGUS_HURT = new Identifier("adventurez:fungus_hurt");
    public static final Identifier FUNGUS_DEATH = new Identifier("adventurez:fungus_death");
    public static SoundEvent FUNGUS_IDLE_EVENT = new SoundEvent(FUNGUS_IDLE);
    public static SoundEvent FUNGUS_HURT_EVENT = new SoundEvent(FUNGUS_HURT);
    public static SoundEvent FUNGUS_DEATH_EVENT = new SoundEvent(FUNGUS_DEATH);
    // Ork
    public static final Identifier ORC_DEATH = new Identifier("adventurez:orc_death");
    public static final Identifier ORC_HURT = new Identifier("adventurez:orc_hurt");
    public static final Identifier ORC_IDLE = new Identifier("adventurez:orc_idle");
    public static final Identifier ORC_STEP = new Identifier("adventurez:orc_step");
    public static SoundEvent ORC_DEATH_EVENT = new SoundEvent(ORC_DEATH);
    public static SoundEvent ORC_HURT_EVENT = new SoundEvent(ORC_HURT);
    public static SoundEvent ORC_IDLE_EVENT = new SoundEvent(ORC_IDLE);
    public static SoundEvent ORC_STEP_EVENT = new SoundEvent(ORC_STEP);
    // Dragon
    public static final Identifier DRAGON_DEATH = new Identifier("adventurez:dragon_death");
    public static final Identifier DRAGON_HIT = new Identifier("adventurez:dragon_hit");
    public static final Identifier DRAGON_IDLE = new Identifier("adventurez:dragon_idle");
    public static final Identifier DRAGON_STEP = new Identifier("adventurez:dragon_step");
    public static final Identifier DRAGON_BREATH = new Identifier("adventurez:dragon_breath");
    public static SoundEvent DRAGON_DEATH_EVENT = new SoundEvent(DRAGON_DEATH);
    public static SoundEvent DRAGON_HIT_EVENT = new SoundEvent(DRAGON_HIT);
    public static SoundEvent DRAGON_IDLE_EVENT = new SoundEvent(DRAGON_IDLE);
    public static SoundEvent DRAGON_STEP_EVENT = new SoundEvent(DRAGON_STEP);
    public static SoundEvent DRAGON_BREATH_EVENT = new SoundEvent(DRAGON_BREATH);
    // Mammoth
    public static final Identifier MAMMOTH_HIT = new Identifier("adventurez:mammoth_hit");
    public static final Identifier MAMMOTH_IDLE = new Identifier("adventurez:mammoth_idle");
    public static final Identifier MAMMOTH_DEATH = new Identifier("adventurez:mammoth_death");
    public static final Identifier MAMMOTH_BABY_IDLE = new Identifier("adventurez:mammoth_baby_idle");
    public static SoundEvent MAMMOTH_HIT_EVENT = new SoundEvent(MAMMOTH_HIT);
    public static SoundEvent MAMMOTH_IDLE_EVENT = new SoundEvent(MAMMOTH_IDLE);
    public static SoundEvent MAMMOTH_DEATH_EVENT = new SoundEvent(MAMMOTH_DEATH);
    public static SoundEvent MAMMOTH_BABY_IDLE_EVENT = new SoundEvent(MAMMOTH_BABY_IDLE);
    // Void Shadow
    public static final Identifier SHADOW_CAST = new Identifier("adventurez:shadow_cast");
    public static final Identifier SHADOW_PREPARE = new Identifier("adventurez:shadow_prepare");
    public static final Identifier SHADOW_IDLE = new Identifier("adventurez:shadow_idle");
    public static final Identifier SHADOW_DEATH = new Identifier("adventurez:shadow_death");
    public static SoundEvent SHADOW_CAST_EVENT = new SoundEvent(SHADOW_CAST);
    public static SoundEvent SHADOW_PREPARE_EVENT = new SoundEvent(SHADOW_PREPARE);
    public static SoundEvent SHADOW_IDLE_EVENT = new SoundEvent(SHADOW_IDLE);
    public static SoundEvent SHADOW_DEATH_EVENT = new SoundEvent(SHADOW_DEATH);
    // Iguana
    public static final Identifier IGUANA_DEATH = new Identifier("adventurez:iguana_death");
    public static final Identifier IGUANA_HURT = new Identifier("adventurez:iguana_hurt");
    public static final Identifier IGUANA_IDLE = new Identifier("adventurez:iguana_idle");
    public static final Identifier IGUANA_STEP = new Identifier("adventurez:iguana_step");
    public static SoundEvent IGUANA_DEATH_EVENT = new SoundEvent(IGUANA_DEATH);
    public static SoundEvent IGUANA_HURT_EVENT = new SoundEvent(IGUANA_HURT);
    public static SoundEvent IGUANA_IDLE_EVENT = new SoundEvent(IGUANA_IDLE);
    public static SoundEvent IGUANA_STEP_EVENT = new SoundEvent(IGUANA_STEP);
    // Ender Whale
    public static final Identifier WHALE_DEATH = new Identifier("adventurez:whale_death");
    public static final Identifier WHALE_HURT = new Identifier("adventurez:whale_hurt");
    public static final Identifier WHALE_IDLE = new Identifier("adventurez:whale_idle");
    public static SoundEvent WHALE_DEATH_EVENT = new SoundEvent(WHALE_DEATH);
    public static SoundEvent WHALE_HURT_EVENT = new SoundEvent(WHALE_HURT);
    public static SoundEvent WHALE_IDLE_EVENT = new SoundEvent(WHALE_IDLE);
    // Item
    public static final Identifier ROCK_IMPACT = new Identifier("adventurez:rock_impact");
    public static final Identifier ROCK_THROW = new Identifier("adventurez:rock_throw");
    public static final Identifier HEART_BEAT = new Identifier("adventurez:heart_beat");
    public static SoundEvent ROCK_IMPACT_EVENT = new SoundEvent(ROCK_IMPACT);
    public static SoundEvent ROCK_THROW_EVENT = new SoundEvent(ROCK_THROW);
    public static SoundEvent HEART_BEAT_EVENT = new SoundEvent(HEART_BEAT);
    // Item Related
    public static final Identifier EQUIP_CHEST = new Identifier("adventurez:equip_chest");
    public static SoundEvent EQUIP_CHEST_EVENT = new SoundEvent(EQUIP_CHEST);
    // Block
    public static final Identifier OPEN_SHADOW_CHEST = new Identifier("adventurez:open_shadow_chest");
    public static final Identifier CLOSE_SHADOW_CHEST = new Identifier("adventurez:close_shadow_chest");
    public static SoundEvent OPEN_SHADOW_CHEST_EVENT = new SoundEvent(OPEN_SHADOW_CHEST);
    public static SoundEvent CLOSE_SHADOW_CHEST_EVENT = new SoundEvent(CLOSE_SHADOW_CHEST);

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
        Registry.register(Registry.SOUND_EVENT, SoundInit.NIGHTMARE_IDLE, NIGHTMARE_IDLE_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.NIGHTMARE_DEATH, NIGHTMARE_DEATH_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.NIGHTMARE_HURT, NIGHTMARE_HURT_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.NIGHTMARE_ANGRY, NIGHTMARE_ANGRY_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.SOULREAPER_IDLE, SOULREAPER_IDLE_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.SOULREAPER_HURT, SOULREAPER_HURT_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.SOULREAPER_DEATH, SOULREAPER_DEATH_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.SPELL_CAST_SHIELD, SPELL_CAST_SHIELD_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.MAGIC_SHIELD_HIT, MAGIC_SHIELD_HIT_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.EYE_DEATH, EYE_DEATH_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.EYE_HURT, EYE_HURT_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.EYE_IDLE, EYE_IDLE_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.EYE_DEATH_PLATFORM, EYE_DEATH_PLATFORM_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.FUNGUS_IDLE, FUNGUS_IDLE_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.FUNGUS_HURT, FUNGUS_HURT_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.FUNGUS_DEATH, FUNGUS_DEATH_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.ORC_DEATH, ORC_DEATH_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.ORC_HURT, ORC_HURT_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.ORC_IDLE, ORC_IDLE_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.ORC_STEP, ORC_STEP_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.DRAGON_DEATH, DRAGON_DEATH_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.DRAGON_HIT, DRAGON_HIT_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.DRAGON_IDLE, DRAGON_IDLE_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.DRAGON_STEP, DRAGON_STEP_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.EQUIP_CHEST, EQUIP_CHEST_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.MAMMOTH_HIT, MAMMOTH_HIT_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.MAMMOTH_IDLE, MAMMOTH_IDLE_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.MAMMOTH_DEATH, MAMMOTH_DEATH_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.MAMMOTH_BABY_IDLE, MAMMOTH_BABY_IDLE_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.DRAGON_BREATH, DRAGON_BREATH_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.CLOSE_SHADOW_CHEST, CLOSE_SHADOW_CHEST_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.OPEN_SHADOW_CHEST, OPEN_SHADOW_CHEST_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.IGUANA_DEATH, IGUANA_DEATH_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.IGUANA_HURT, IGUANA_HURT_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.IGUANA_IDLE, IGUANA_IDLE_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.IGUANA_STEP, IGUANA_STEP_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.WHALE_DEATH, WHALE_DEATH_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.WHALE_HURT, WHALE_HURT_EVENT);
        Registry.register(Registry.SOUND_EVENT, SoundInit.WHALE_IDLE, WHALE_IDLE_EVENT);
    }
}