package com.padbro.greeterbro.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "afk")
public class AfkConfig implements ConfigData {
  @ConfigEntry.Gui.Tooltip public boolean enable = true;

  @ConfigEntry.Gui.Tooltip public int afkTime = 5;

  @ConfigEntry.Gui.Tooltip public boolean notify = true;
}
