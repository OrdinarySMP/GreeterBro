package com.padbro.greeterbro.client.config;

import com.padbro.greeterbro.client.GreeterBroClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler.EnumDisplayOption;

import java.util.List;
import java.util.stream.Collectors;

@Config(name = "returningPlayer")
public class ReturningPlayerConfig implements ConfigData {
    public boolean enable = true;

    @ConfigEntry.Gui.Tooltip
    public boolean cacheOnJoin = true;

    @ConfigEntry.BoundedDiscrete(max = 10)
    public int ignoreForMin = 5;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.EnumHandler(option = EnumDisplayOption.BUTTON)
    public CacheClearType cacheClearType = CacheClearType.OnJoin;
    @ConfigEntry.Gui.Tooltip
    public List<String> greetings = List.of("Welcome back", "wb", "o/");
    @ConfigEntry.BoundedDiscrete(max = 100)
    @ConfigEntry.Gui.Tooltip
    private int greetingChance = 100;

    @Override
    public void validatePostLoad() {
        this.greetings =
                greetings.stream().filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
    }

    public int getGreetingChance() {
        if (GreeterBroClient.serverConfig != null && GreeterBroClient.serverConfig.generalConfig.greetingChance != -1) {
            return GreeterBroClient.serverConfig.generalConfig.greetingChance;
        }
        return this.greetingChance;
    }
}
