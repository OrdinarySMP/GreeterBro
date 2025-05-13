package com.padbro.greeterbro.client.config;

import java.util.List;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "blacklist")
public class BlacklistConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public List<String> players = List.of();
}
