package com.padbro.greeterbro.client.config;

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

    public int ignoreForMin = 5;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.EnumHandler(option = EnumDisplayOption.BUTTON)
    public CacheClearType cacheClearType = CacheClearType.OnJoin;

    @ConfigEntry.BoundedDiscrete(max = 100)
    @ConfigEntry.Gui.Tooltip
    public int greetingChance = 100;

    @ConfigEntry.Gui.Tooltip
    public List<String> greetings = List.of("Welcome back", "wb", "o/");

    @Override
    public void validatePostLoad() {
        this.greetings =
                greetings.stream().filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
    }
}
