package com.padbro.greeterbro.client.config;

import java.util.List;
import java.util.stream.Collectors;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "general")
public class GeneralConfig implements ConfigData {
  @ConfigEntry.Gui.Excluded public int configVersion = 0;

  @ConfigEntry.Gui.Tooltip public boolean enable = true;

  @ConfigEntry.Gui.Tooltip public boolean enableOwnJoin = true;

  @ConfigEntry.Gui.Tooltip public String customMessage = "";

  @ConfigEntry.BoundedDiscrete(max = 100)
  @ConfigEntry.Gui.Tooltip
  public int greetingChance = 100;

  public boolean cancelOnLeave = true;
  @ConfigEntry.Gui.Tooltip public String customLeaveMessage = "";

  @ConfigEntry.Gui.Tooltip public List<String> greetings = List.of("Hello", "o/");

  @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
  public DelayRange delayRange = new DelayRange(3, 5);

  @Override
  public void validatePostLoad() {

    float actualMin = Math.min(this.delayRange.min, this.delayRange.max);
    float actualMax = Math.max(this.delayRange.min, this.delayRange.max);
    this.delayRange.min = Math.max(actualMin, 0);
    this.delayRange.max = Math.max(actualMax, 0);

    this.greetings =
        greetings.stream().filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
  }

  public static class DelayRange {
    @ConfigEntry.BoundedDiscrete(max = 10, min = 0)
    public float min;

    @ConfigEntry.BoundedDiscrete(max = 10, min = 0)
    public float max;

    DelayRange(float min, float max) {
      this.min = min;
      this.max = max;
    }

    public int getRandomDelayInTicks() {
      float actualMin = Math.min(this.min, this.max);
      float actualMax = Math.max(this.min, this.max);
      float min = Math.max(actualMin, 0);
      float max = Math.max(actualMax, 0);
      float randomDelay = (float) (Math.random() * (max - min + 1)) + min;
      return Math.round(randomDelay * 20);
    }
  }
}
