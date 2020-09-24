package net.adventurez.init;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.adventurez.config.AdventureConfig;

public class ConfigInit {
  public static AdventureConfig CONFIG = new AdventureConfig();

  public static void init() {
    AutoConfig.register(AdventureConfig.class, JanksonConfigSerializer::new);
    CONFIG = AutoConfig.getConfigHolder(AdventureConfig.class).getConfig();
  }

}
