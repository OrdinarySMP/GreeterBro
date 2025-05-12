package com.padbro.greeterbro.client.config;

import java.util.List;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "firstJoin")
public class FirstJoinConfig implements ConfigData {
    public boolean enable = true;

    public String customMessage = "";

    public List<String> greetings = List.of("Welcome");
}
