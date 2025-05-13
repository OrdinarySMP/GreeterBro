package com.padbro.greeterbro.client.config;

import java.util.List;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "returningPlayer")
public class ReturningPlayerConfig implements ConfigData {
  public boolean enable = true;

  @ConfigEntry.BoundedDiscrete(max = 10)
  public int ignoreForMin = 5;

  @ConfigEntry.Gui.Tooltip public List<String> greetings = List.of("Welcome back", "wb", "o/");
}
