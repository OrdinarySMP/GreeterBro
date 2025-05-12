package com.padbro.greeterbro.client.config;

import java.util.List;
import java.util.stream.Collectors;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "general")
public class GeneralConfig implements ConfigData {
  @ConfigEntry.Gui.Tooltip public boolean enable = true;

  public String customMessage = "";

  @ConfigEntry.Gui.Tooltip public List<String> greetings = List.of("Hello", "o/");

  @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
  public DelayRange delayRange = new DelayRange(20, 60);

  public static class DelayRange {
    @ConfigEntry.BoundedDiscrete(max = 100)
    int min;

    @ConfigEntry.BoundedDiscrete(max = 100)
    int max;

    DelayRange(int min, int max) {
      this.min = min;
      this.max = max;
    }

    public int getRandomDelay() {
      return (int) (Math.random() * (this.max - this.min + 1)) + this.min;
    }
  }

  @Override
  public void validatePostLoad() {
    int actualMin = Math.min(this.delayRange.min, this.delayRange.max);
    int actualMax = Math.max(this.delayRange.min, this.delayRange.max);
    this.delayRange.min = actualMin;
    this.delayRange.max = actualMax;

    this.greetings =
        greetings.stream().filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
  }
}
