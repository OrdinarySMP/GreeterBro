package com.padbro.greeterbro.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.List;

@Config(name = "GreeterBro")
public class GreeterBroConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean enableGreeterBro = true;

    @ConfigEntry.Gui.Tooltip
    public List<String> greetings = List.of("Hello", "o/");

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public DelayRange delayRange = new DelayRange(20, 60);

    public static class DelayRange {
        @ConfigEntry.BoundedDiscrete(max = 100)
        private int min;
        @ConfigEntry.BoundedDiscrete(max = 100)
        private int max;

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
    }
}
