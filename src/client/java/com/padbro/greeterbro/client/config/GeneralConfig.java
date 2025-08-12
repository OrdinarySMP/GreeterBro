package com.padbro.greeterbro.client.config;

import java.util.List;
import java.util.stream.Collectors;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import javax.xml.transform.Source;

@Config(name = "general")
public class GeneralConfig implements ConfigData {
  @ConfigEntry.Gui.Tooltip public boolean enable = true;

  @ConfigEntry.Gui.Tooltip public boolean enableOwnJoin = true;

  @ConfigEntry.Gui.Tooltip public String customMessage = "";

  @ConfigEntry.BoundedDiscrete(max = 100)
  @ConfigEntry.Gui.Tooltip public int greetingChance = 100;

  public boolean cancelOnLeave = true;
  @ConfigEntry.Gui.Tooltip public String customLeaveMessage = "";

  @ConfigEntry.Gui.Tooltip public List<String> greetings = List.of("Hello", "o/");

  @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
  public DelayRange delayRange = new DelayRange(20, 60);

  @Override
  public void validatePostLoad() {
    int actualMin = Math.min(this.delayRange.min, this.delayRange.max);
    int actualMax = Math.max(this.delayRange.min, this.delayRange.max);
    this.delayRange.min = Math.max(actualMin, 0);
    this.delayRange.max = Math.max(actualMax, 0);

    this.greetings =
        greetings.stream().filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
  }

  public static class DelayRange {
    int min;

    int max;

    DelayRange(int min, int max) {
      this.min = min;
      this.max = max;
    }

    public int getRandomDelay() {
      int actualMin = Math.min(this.min, this.max);
      int actualMax = Math.max(this.min, this.max);
      int min = Math.max(actualMin, 0);
      int max = Math.max(actualMax, 0);
      return (int) (Math.random() * (max - min + 1)) + min;
    }
  }
}
