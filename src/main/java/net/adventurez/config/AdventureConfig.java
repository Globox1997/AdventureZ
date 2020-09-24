package net.adventurez.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "adventurez")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class AdventureConfig implements ConfigData {
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  public int necromancer_spawn_weight = 1;
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  public int nightmare_spawn_weight = 2;
  @ConfigEntry.BoundedDiscrete(min = 1, max = 200)
  @Comment("Chance for spawning when mining gold ore in nether")
  public int piglin_beast_ore_chance = 200;
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  public int small_stone_golem_spawn_weight = 5;
  @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
  public int summoner_spawn_weight = 1;

}