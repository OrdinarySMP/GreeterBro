package com.padbro.greeterbro.client.config;

import java.util.List;
import java.util.stream.Collectors;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "nameChange")
public class NameChangeConfig implements ConfigData {
  public boolean enable = true;

  @ConfigEntry.Gui.Tooltip public String customMessage = "";

  @ConfigEntry.BoundedDiscrete(max = 100)
  @ConfigEntry.Gui.Tooltip public int greetingChance = 100;

  @ConfigEntry.Gui.Tooltip public List<String> greetings = List.of();

  @Override
  public void validatePostLoad() {
    this.greetings =
        greetings.stream().filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
  }
}
