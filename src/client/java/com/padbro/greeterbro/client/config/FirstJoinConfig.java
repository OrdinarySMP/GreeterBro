package com.padbro.greeterbro.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.List;

@Config(name = "firstJoin")
public class FirstJoinConfig implements ConfigData {
    public boolean enable = true;

    public String customMessage = "";

    public List<String> greetings = List.of("Welcome");
}
