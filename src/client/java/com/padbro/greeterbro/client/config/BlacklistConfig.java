package com.padbro.greeterbro.client.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "blacklist")
public class BlacklistConfig implements ConfigData {
  public List<String> players = new ArrayList<>();

  @Override
  public void validatePostLoad() {
    this.players = players.stream().filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
  }
}
