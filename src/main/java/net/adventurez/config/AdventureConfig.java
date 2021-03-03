package net.adventurez.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "adventurez")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class AdventureConfig implements ConfigData {
  @ConfigEntry.Gui.PrefixText
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  public int necromancer_spawn_weight = 1;
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  public int nightmare_spawn_weight = 2;
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  @Comment("Chance for spawning when mining gold ore in nether; 1/Value")
  public int piglin_beast_ore_spawn_chance = 70;
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  @Comment("Chance for spawning when killing too many piglins; 1/Value")
  public int piglin_beast_attack_piglin_spawn_chance = 2;
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  public int small_stone_golem_spawn_weight = 5;
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  @Comment("Chance for spawning when thunder occurs; 1/Value")
  public int summoner_thunder_spawn_chance = 4;
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  public int blaze_guardian_spawn_weight = 1;
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  public int fungus_spawn_weight = 10;
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  public int ork_spawn_weight = 10;
  public boolean allow_stone_golem_summoning = true;
  public boolean allow_the_eye_summoning = true;

}