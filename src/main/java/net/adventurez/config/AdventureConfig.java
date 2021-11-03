package net.adventurez.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "adventurez")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class AdventureConfig implements ConfigData {
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int necromancer_spawn_weight = 1;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int nightmare_spawn_weight = 5;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    @Comment("Chance for spawning when mining gold ore in nether; 1/Value")
    public int piglin_beast_ore_spawn_chance = 30;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    @Comment("Chance for spawning when killing too many piglins; 1/Value")
    public int piglin_beast_attack_piglin_spawn_chance = 7;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int small_stone_golem_spawn_weight = 5;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    @Comment("Chance for spawning when thunder occurs; 1/Value")
    public int summoner_thunder_spawn_chance = 4;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int blaze_guardian_spawn_weight = 1;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int fungus_spawn_weight = 8;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int orc_spawn_weight = 30;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int mammoth_spawn_weight = 1;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int ender_whale_spawn_weight = 5;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int iguana_spawn_weight = 10;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    @Comment("Chance for spawning when geode generates; 1/Value")
    public int amethyst_golem_spawn_chance = 5;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int desert_rhino_spawn_weight = 1;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    @Comment("Chance for spawning when desert well generates; 1/Value")
    public int desert_rhino_well_spawn_chance = 1;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int shaman_spawn_weight = 1;
    @ConfigEntry.Category("spawn_setting")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int deer_spawn_weight = 6;

    // Allow settings
    @ConfigEntry.Category("spawn_setting")
    public boolean allow_stone_golem_summoning = true;
    @ConfigEntry.Category("spawn_setting")
    public boolean allow_the_eye_summoning = true;
    @ConfigEntry.Category("spawn_setting")
    public boolean allow_dragon_hatching = true;
    @ConfigEntry.Category("spawn_setting")
    public boolean allow_guardian_spawner_spawn = true;
    // Misc settings
    @ConfigEntry.Category("misc")
    public boolean allow_source_stone_tp = true;
    @ConfigEntry.Category("misc")
    @ConfigEntry.Gui.RequiresRestart
    public boolean disable_armor_bonus = false;
    @ConfigEntry.Category("misc")
    public boolean resummoned_ender_dragon_drops_egg = false;
    @ConfigEntry.Category("misc")
    public boolean allow_ender_dragon_nether_portal = false;
    @ConfigEntry.Category("misc")
    public boolean heavy_dragon_flight = true;
    @ConfigEntry.Category("misc")
    public boolean allow_special_enchant_loot = true;
    @ConfigEntry.Category("misc")
    public boolean allow_extra_tooltips = true;
    @ConfigEntry.Category("misc")
    public boolean allow_all_items_on_holder = false;

}