package com.padbro.greeterbro.client.config;

import com.padbro.greeterbro.client.GreeterBroClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.List;
import java.util.stream.Collectors;

@Config(name = "general")
public class GeneralConfig implements ConfigData {
    @ConfigEntry.Gui.Excluded
    public int configVersion = 0;
    @ConfigEntry.Gui.Tooltip
    public boolean enableOwnJoin = true;
    @ConfigEntry.Gui.Tooltip
    public String customMessage = "";
    public boolean cancelOnLeave = true;
    @ConfigEntry.Gui.Tooltip
    public String customLeaveMessage = "";
    @ConfigEntry.Gui.Tooltip
    public List<String> greetings = List.of("Hello", "o/");
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public DelayRange delayRange = new DelayRange(3, 5);
    @ConfigEntry.Gui.Tooltip
    private boolean enable = true;
    @ConfigEntry.BoundedDiscrete(max = 100)
    @ConfigEntry.Gui.Tooltip
    private int greetingChance = 100;

    public boolean getEnabled() {
        if (GreeterBroClient.serverConfig != null && GreeterBroClient.serverConfig.generalConfig.enabled == false) {
            return false;
        }
        return this.enable;
    }

    public void setEnabled(boolean enabled) {
        this.enable = enabled;
    }

    public int getGreetingChance() {
        if (GreeterBroClient.serverConfig != null && GreeterBroClient.serverConfig.generalConfig.greetingChance != -1) {
            return GreeterBroClient.serverConfig.generalConfig.greetingChance;
        }
        return this.greetingChance;
    }

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
        private float min;
        @ConfigEntry.BoundedDiscrete(max = 10, min = 0)
        private float max;

        DelayRange(float min, float max) {
            this.min = min;
            this.max = max;
        }

        public float getMax() {
            return max;
        }

        public void setMax(float max) {
            this.max = max;
        }

        public float getMin() {
            return min;
        }

        public void setMin(float min) {
            this.min = min;
        }

        public int getRandomDelayInTicks() {
            com.padbro.greeterbro.config.GeneralConfig generalServerConfig = GreeterBroClient.serverConfig != null
                    ? GreeterBroClient.serverConfig.generalConfig
                    : null;

            float clientMin = Math.min(this.min, this.max);
            float clientMax = Math.max(this.min, this.max);

            float actualMin = clientMin;
            float actualMax = clientMax;

            if (generalServerConfig != null && generalServerConfig.minDelay != -1) {
                actualMin = Math.max(generalServerConfig.minDelay, clientMin);
            }

            if (generalServerConfig != null && generalServerConfig.maxDelay != -1) {
                actualMax = Math.max(generalServerConfig.maxDelay, clientMax);
            }

            float min = Math.max(actualMin, 0);
            float max = Math.max(actualMax, 0);

            float randomDelay = (float) (Math.random() * (max - min + 1)) + min;

            return Math.round(randomDelay * 20);
        }
    }
}
