package com.padbro.greeterbro.client.config;

import com.padbro.greeterbro.client.GreeterBroClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.List;
import java.util.stream.Collectors;

@Config(name = "firstJoin")
public class FirstJoinConfig implements ConfigData {
    public boolean enable = true;

    @ConfigEntry.Gui.Tooltip
    public String customMessage = "";
    @ConfigEntry.Gui.Tooltip
    public List<String> greetings = List.of("Welcome");
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
