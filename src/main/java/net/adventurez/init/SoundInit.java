package net.adventurez.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundInit {
    // Stone Golem
    public static SoundEvent GOLEM_IDLE_EVENT = register("adventurez:golem_idle");
    public static SoundEvent GOLEM_WALK_EVENT = register("adventurez:golem_walk");
    public static SoundEvent GOLEM_HIT_EVENT = register("adventurez:golem_hit");
    public static SoundEvent GOLEM_ROAR_EVENT = register("adventurez:golem_roar");
    public static SoundEvent GOLEM_DEATH_EVENT = register("adventurez:golem_death");
    public static SoundEvent GOLEM_SPAWN_EVENT = register("adventurez:golem_spawn");
    // Small Golem
    public static SoundEvent SMALL_GOLEM_IDLE_EVENT = register("adventurez:small_golem_idle");
    public static SoundEvent SMALL_GOLEM_WALK_EVENT = register("adventurez:small_golem_walk");
    public static SoundEvent SMALL_GOLEM_HIT_EVENT = register("adventurez:small_golem_hit");
    public static SoundEvent SMALL_GOLEM_DEATH_EVENT = register("adventurez:small_golem_death");
    // Piglin Beast
    public static SoundEvent PIGLINBEAST_IDLE_EVENT = register("adventurez:piglinbeast_idle");
    public static SoundEvent PIGLINBEAST_DEATH_EVENT = register("adventurez:piglinbeast_death");
    public static SoundEvent PIGLINBEAST_HURT_EVENT = register("adventurez:piglinbeast_hurt");
    public static SoundEvent PIGLINBEAST_WALK_EVENT = register("adventurez:piglinbeast_walk");
    public static SoundEvent PIGLINBEAST_SHOUT_EVENT = register("adventurez:piglinbeast_shout");
    public static SoundEvent PIGLINBEAST_CLUBSWING_EVENT = register("adventurez:piglinbeast_clubswing");
    // Nightmare
    public static SoundEvent NIGHTMARE_IDLE_EVENT = register("adventurez:nightmare_idle");
    public static SoundEvent NIGHTMARE_DEATH_EVENT = register("adventurez:nightmare_death");
    public static SoundEvent NIGHTMARE_HURT_EVENT = register("adventurez:nightmare_hurt");
    public static SoundEvent NIGHTMARE_ANGRY_EVENT = register("adventurez:nightmare_angry");
    // Soul Reaper
    public static SoundEvent SOULREAPER_IDLE_EVENT = register("adventurez:soulreaper_idle");
    public static SoundEvent SOULREAPER_HURT_EVENT = register("adventurez:soulreaper_hurt");
    public static SoundEvent SOULREAPER_DEATH_EVENT = register("adventurez:soulreaper_death");
    // Magic
    public static SoundEvent SPELL_CAST_SHIELD_EVENT = register("adventurez:spellcast_shield");
    public static SoundEvent MAGIC_SHIELD_HIT_EVENT = register("adventurez:magic_shield_hit");
    // The Eye
    public static SoundEvent EYE_DEATH_EVENT = register("adventurez:eye_death");
    public static SoundEvent EYE_HURT_EVENT = register("adventurez:eye_hurt");
    public static SoundEvent EYE_IDLE_EVENT = register("adventurez:eye_idle");
    public static SoundEvent EYE_DEATH_PLATFORM_EVENT = register("adventurez:eye_death_platform");
    // Fungus
    public static SoundEvent FUNGUS_IDLE_EVENT = register("adventurez:fungus_idle");
    public static SoundEvent FUNGUS_HURT_EVENT = register("adventurez:fungus_hurt");
    public static SoundEvent FUNGUS_DEATH_EVENT = register("adventurez:fungus_death");
    // Ork
    public static SoundEvent ORC_DEATH_EVENT = register("adventurez:orc_death");
    public static SoundEvent ORC_HURT_EVENT = register("adventurez:orc_hurt");
    public static SoundEvent ORC_IDLE_EVENT = register("adventurez:orc_idle");
    public static SoundEvent ORC_STEP_EVENT = register("adventurez:orc_step");
    // Dragon
    public static SoundEvent DRAGON_DEATH_EVENT = register("adventurez:dragon_death");
    public static SoundEvent DRAGON_HIT_EVENT = register("adventurez:dragon_hit");
    public static SoundEvent DRAGON_IDLE_EVENT = register("adventurez:dragon_idle");
    public static SoundEvent DRAGON_STEP_EVENT = register("adventurez:dragon_step");
    public static SoundEvent DRAGON_BREATH_EVENT = register("adventurez:dragon_breath");
    // Mammoth
    public static SoundEvent MAMMOTH_HIT_EVENT = register("adventurez:mammoth_hit");
    public static SoundEvent MAMMOTH_IDLE_EVENT = register("adventurez:mammoth_idle");
    public static SoundEvent MAMMOTH_DEATH_EVENT = register("adventurez:mammoth_death");
    public static SoundEvent MAMMOTH_BABY_IDLE_EVENT = register("adventurez:mammoth_baby_idle");
    // Void Shadow
    public static SoundEvent SHADOW_CAST_EVENT = register("adventurez:shadow_cast");
    public static SoundEvent SHADOW_PREPARE_EVENT = register("adventurez:shadow_prepare");
    public static SoundEvent SHADOW_IDLE_EVENT = register("adventurez:shadow_idle");
    public static SoundEvent SHADOW_DEATH_EVENT = register("adventurez:shadow_death");
    // Iguana
    public static SoundEvent IGUANA_DEATH_EVENT = register("adventurez:iguana_death");
    public static SoundEvent IGUANA_HURT_EVENT = register("adventurez:iguana_hurt");
    public static SoundEvent IGUANA_IDLE_EVENT = register("adventurez:iguana_idle");
    public static SoundEvent IGUANA_STEP_EVENT = register("adventurez:iguana_step");
    // Ender Whale
    public static SoundEvent WHALE_DEATH_EVENT = register("adventurez:whale_death");
    public static SoundEvent WHALE_HURT_EVENT = register("adventurez:whale_hurt");
    public static SoundEvent WHALE_IDLE_EVENT = register("adventurez:whale_idle");
    // Item
    public static SoundEvent ROCK_IMPACT_EVENT = register("adventurez:rock_impact");
    public static SoundEvent ROCK_THROW_EVENT = register("adventurez:rock_throw");
    public static SoundEvent HEART_BEAT_EVENT = register("adventurez:heart_beat");
    public static SoundEvent FLUTE_CALL_EVENT = register("adventurez:flute_call");
    // Item Related
    public static SoundEvent EQUIP_CHEST_EVENT = register("adventurez:equip_chest");
    // Block
    public static SoundEvent OPEN_SHADOW_CHEST_EVENT = register("adventurez:open_shadow_chest");
    public static SoundEvent CLOSE_SHADOW_CHEST_EVENT = register("adventurez:close_shadow_chest");

    private static SoundEvent register(String id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(new Identifier(id)));
    }

    public static void init() {
    }
}