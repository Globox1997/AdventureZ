package net.adventurez.init;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.adventurez.config.AdventureConfig;

public class ConfigInit {
  public static AdventureConfig CONFIG = new AdventureConfig();

  public static void init() {
    AutoConfig.register(AdventureConfig.class, JanksonConfigSerializer::new);
    CONFIG = AutoConfig.getConfigHolder(AdventureConfig.class).getConfig();
  }

}
