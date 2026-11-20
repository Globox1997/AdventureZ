package net.adventurez.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import net.adventurez.AdventureMain;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class SoundInit {

    private static final List<SoundEvent> SOUND_EVENTS = new ArrayList<>();

    // Blackstone Golem
    public static SoundEvent BLACKSTONE_GOLEM_IDLE_EVENT = register("blackstone_golem_idle");
    public static SoundEvent BLACKSTONE_GOLEM_STEP_EVENT = register("blackstone_golem_step");
    public static SoundEvent BLACKSTONE_GOLEM_HURT_EVENT = register("blackstone_golem_hurt");
    public static SoundEvent BLACKSTONE_GOLEM_ROAR_EVENT = register("blackstone_golem_roar");
    public static SoundEvent BLACKSTONE_GOLEM_DEATH_EVENT = register("blackstone_golem_death");
    public static SoundEvent BLACKSTONE_GOLEM_SPAWN_EVENT = register("blackstone_golem_spawn");
    public static SoundEvent BLACKSTONE_GOLEM_AWAKENS_EVENT = register("blackstone_golem_awakens");
    // Mini Blackstone Golem
    public static SoundEvent MINI_BLACKSTONE_GOLEM_IDLE_EVENT = register("mini_blackstone_golem_idle");
    public static SoundEvent MINI_BLACKSTONE_GOLEM_STEP_EVENT = register("mini_blackstone_golem_step");
    public static SoundEvent MINI_BLACKSTONE_GOLEM_HURT_EVENT = register("mini_blackstone_golem_hurt");
    public static SoundEvent MINI_BLACKSTONE_GOLEM_DEATH_EVENT = register("mini_blackstone_golem_death");
    // Piglin Beast
    public static SoundEvent PIGLIN_BEAST_IDLE_EVENT = register("piglin_beast_idle");
    public static SoundEvent PIGLIN_BEAST_DEATH_EVENT = register("piglin_beast_death");
    public static SoundEvent PIGLIN_BEAST_HURT_EVENT = register("piglin_beast_hurt");
    public static SoundEvent PIGLIN_BEAST_STEP_EVENT = register("piglin_beast_step");
    public static SoundEvent PIGLIN_BEAST_SHOUT_EVENT = register("piglin_beast_shout");
    public static SoundEvent PIGLIN_BEAST_CLUBSWING_EVENT = register("piglin_beast_clubswing");
    // Nightmare
    public static SoundEvent NIGHTMARE_IDLE_EVENT = register("nightmare_idle");
    public static SoundEvent NIGHTMARE_DEATH_EVENT = register("nightmare_death");
    public static SoundEvent NIGHTMARE_HURT_EVENT = register("nightmare_hurt");
    public static SoundEvent NIGHTMARE_ANGRY_EVENT = register("nightmare_angry");
    // Soul Reaper
    public static SoundEvent SOUL_REAPER_IDLE_EVENT = register("soul_reaper_idle");
    public static SoundEvent SOUL_REAPER_HURT_EVENT = register("soul_reaper_hurt");
    public static SoundEvent SOUL_REAPER_DEATH_EVENT = register("soul_reaper_death");
    // Summoner Magic
    public static SoundEvent SPELL_CAST_SHIELD_EVENT = register("spellcast_shield");
    public static SoundEvent MAGIC_SHIELD_HIT_EVENT = register("magic_shield_hit");
    // The Eye
    public static SoundEvent THE_EYE_DEATH_EVENT = register("the_eye_death");
    public static SoundEvent THE_EYE_HURT_EVENT = register("the_eye_hurt");
    public static SoundEvent THE_EYE_IDLE_EVENT = register("the_eye_idle");
    public static SoundEvent THE_EYE_DEATH_PLATFORM_EVENT = register("the_eye_death_platform");
    // Fungus
    public static SoundEvent FUNGUS_IDLE_EVENT = register("fungus_idle");
    public static SoundEvent FUNGUS_HURT_EVENT = register("fungus_hurt");
    public static SoundEvent FUNGUS_DEATH_EVENT = register("fungus_death");
    // Ork
    public static SoundEvent ORC_DEATH_EVENT = register("orc_death");
    public static SoundEvent ORC_HURT_EVENT = register("orc_hurt");
    public static SoundEvent ORC_IDLE_EVENT = register("orc_idle");
    public static SoundEvent ORC_STEP_EVENT = register("orc_step");
    // Dragon
    public static SoundEvent DRAGON_DEATH_EVENT = register("dragon_death");
    public static SoundEvent DRAGON_HURT_EVENT = register("dragon_hurt");
    public static SoundEvent DRAGON_IDLE_EVENT = register("dragon_idle");
    public static SoundEvent DRAGON_STEP_EVENT = register("dragon_step");
    public static SoundEvent DRAGON_BREATH_EVENT = register("dragon_breath");
    // Mammoth
    public static SoundEvent MAMMOTH_HURT_EVENT = register("mammoth_hurt");
    public static SoundEvent MAMMOTH_IDLE_EVENT = register("mammoth_idle");
    public static SoundEvent MAMMOTH_DEATH_EVENT = register("mammoth_death");
    public static SoundEvent MAMMOTH_BABY_IDLE_EVENT = register("mammoth_baby_idle");
    // Void Shadow
    public static SoundEvent VOID_SHADOW_CAST_EVENT = register("void_shadow_cast");
    public static SoundEvent VOID_SHADOW_PREPARE_EVENT = register("void_shadow_prepare");
    public static SoundEvent VOID_SHADOW_IDLE_EVENT = register("void_shadow_idle");
    public static SoundEvent VOID_SHADOW_DEATH_EVENT = register("void_shadow_death");
    // Iguana
    public static SoundEvent IGUANA_DEATH_EVENT = register("iguana_death");
    public static SoundEvent IGUANA_HURT_EVENT = register("iguana_hurt");
    public static SoundEvent IGUANA_IDLE_EVENT = register("iguana_idle");
    public static SoundEvent IGUANA_STEP_EVENT = register("iguana_step");
    // Ender Whale
    public static SoundEvent ENDER_WHALE_DEATH_EVENT = register("ender_whale_death");
    public static SoundEvent ENDER_WHALE_HURT_EVENT = register("ender_whale_hurt");
    public static SoundEvent ENDER_WHALE_IDLE_EVENT = register("ender_whale_idle");
    // Amethyst Golem
    public static SoundEvent AMETHYST_GOLEM_DEATH_EVENT = register("amethyst_golem_death");
    public static SoundEvent AMETHYST_GOLEM_HURT_EVENT = register("amethyst_golem_hurt");
    public static SoundEvent AMETHYST_GOLEM_STEP_EVENT = register("amethyst_golem_step");
    public static SoundEvent AMETHYST_GOLEM_IDLE_EVENT = register("amethyst_golem_idle");
    public static SoundEvent AMETHYST_GOLEM_RAGE_EVENT = register("amethyst_golem_rage");
    // Desert Rhino
    public static SoundEvent DESERT_RHINO_DEATH_EVENT = register("desert_rhino_death");
    public static SoundEvent DESERT_RHINO_ATTACK_EVENT = register("desert_rhino_attack");
    public static SoundEvent DESERT_RHINO_STEP_EVENT = register("desert_rhino_step");
    public static SoundEvent DESERT_RHINO_IDLE_EVENT = register("desert_rhino_idle");
    public static SoundEvent DESERT_RHINO_HURT_EVENT = register("desert_rhino_hurt");
    // Shaman
    public static SoundEvent SHAMAN_DEATH_EVENT = register("shaman_death");
    public static SoundEvent SHAMAN_STEP_EVENT = register("shaman_step");
    public static SoundEvent SHAMAN_IDLE_EVENT = register("shaman_idle");
    public static SoundEvent SHAMAN_HURT_EVENT = register("shaman_hurt");
    // Deer
    public static SoundEvent DEER_DEATH_EVENT = register("deer_death");
    public static SoundEvent DEER_IDLE_EVENT = register("deer_idle");
    public static SoundEvent DEER_HURT_EVENT = register("deer_hurt");
    public static SoundEvent BABY_DEER_IDLE_EVENT = register("baby_deer_idle");
    public static SoundEvent BABY_DEER_HURT_EVENT = register("baby_deer_hurt");
    // Enderwarthog
    public static SoundEvent ENDERWARTHOG_DEATH_EVENT = register("enderwarthog_death");
    public static SoundEvent ENDERWARTHOG_STEP_EVENT = register("enderwarthog_step");
    public static SoundEvent ENDERWARTHOG_IDLE_EVENT = register("enderwarthog_idle");
    public static SoundEvent ENDERWARTHOG_HURT_EVENT = register("enderwarthog_hurt");
    public static SoundEvent ENDERWARTHOG_ATTACK_EVENT = register("enderwarthog_attack");
    // Skunk
    public static SoundEvent SKUNK_DEATH_EVENT = register("skunk_death");
    public static SoundEvent SKUNK_IDLE_EVENT = register("skunk_idle");
    public static SoundEvent SKUNK_HURT_EVENT = register("skunk_hurt");
    public static SoundEvent SKUNK_FART_EVENT = register("skunk_fart");
    // Dubious Pumpkin
    public static SoundEvent DUBIOUS_PUMPKIN_DEATH_EVENT = register("dubious_pumpkin_death");
    public static SoundEvent DUBIOUS_PUMPKIN_IDLE_EVENT = register("dubious_pumpkin_idle");
    public static SoundEvent DUBIOUS_PUMPKIN_HURT_EVENT = register("dubious_pumpkin_hurt");
    public static SoundEvent DUBIOUS_PUMPKIN_STEP_EVENT = register("dubious_pumpkin_step");
    // Crystal Beetle
    public static SoundEvent CRYSTAL_BEETLE_DEATH_EVENT = register("crystal_beetle_death");
    public static SoundEvent CRYSTAL_BEETLE_IDLE_EVENT = register("crystal_beetle_idle");
    public static SoundEvent CRYSTAL_BEETLE_HURT_EVENT = register("crystal_beetle_hurt");
    public static SoundEvent CRYSTAL_BEETLE_STEP_EVENT = register("crystal_beetle_step");
    // Terracotta Golem
    public static SoundEvent TERRACOTTA_GOLEM_DEATH_EVENT =register("terracotta_golem_death");
    public static SoundEvent TERRACOTTA_GOLEM_IDLE_EVENT = register("terracotta_golem_idle");
    public static SoundEvent TERRACOTTA_GOLEM_HURT_EVENT = register("terracotta_golem_hurt");
    public static SoundEvent TERRACOTTA_GOLEM_STEP_EVENT = register("terracotta_golem_step");
    public static SoundEvent TERRACOTTA_GOLEM_BUILD_UP_EVENT = register("terracotta_golem_build_up");
    public static SoundEvent TERRACOTTA_GOLEM_BUILD_DOWN_EVENT = register("terracotta_golem_build_down");
    // Item
    public static SoundEvent ROCK_IMPACT_EVENT = register("rock_impact");
    public static SoundEvent ROCK_THROW_EVENT = register("rock_throw");
    public static SoundEvent HEART_BEAT_EVENT = register("heart_beat");
    public static SoundEvent FLUTE_CALL_EVENT = register("flute_call");
    public static SoundEvent SHARD_DESTROY_EVENT = register("shard_destroy");
    // Item Related
    public static SoundEvent EQUIP_CHEST_EVENT = register("equip_chest");
    // Block
    public static SoundEvent OPEN_SHADOW_CHEST_EVENT = register("open_shadow_chest");
    public static SoundEvent CLOSE_SHADOW_CHEST_EVENT = register("close_shadow_chest");

    private static SoundEvent register(String id) {
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(AdventureMain.identifierOf(id));
        SOUND_EVENTS.add(soundEvent);
        return soundEvent;
    }

    public static void register(BiConsumer<Identifier, SoundEvent> registrar) {
        for (SoundEvent soundEvent : SOUND_EVENTS) {
            registrar.accept(soundEvent.location(), soundEvent);
        }
    }
}