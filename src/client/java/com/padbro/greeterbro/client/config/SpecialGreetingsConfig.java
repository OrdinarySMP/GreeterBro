package com.padbro.greeterbro.client.config;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "specialGreetings")
public class SpecialGreetingsConfig implements ConfigData {
  @ConfigEntry.Gui.Tooltip public List<SpecialGreeting> specialGreetings = List.of();

  public static class SpecialGreeting {
    @ConfigEntry.Gui.Tooltip String player;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int greetingChance;

    @ConfigEntry.Gui.Tooltip public List<String> greetings;

    @ConfigEntry.Gui.Tooltip public List<String> returningGreetings;

    SpecialGreeting() {
      this.player = "";
      this.greetingChance = 100;
      this.greetings = List.of("Good to see you", "I was waiting for you");
      this.returningGreetings = List.of("You are back :D", "Where have you been?");
    }
  }

  public Optional<SpecialGreeting> getForPlayer(String player) {
    return this.specialGreetings.stream()
        .filter(specialGreeting -> player.equals(specialGreeting.player))
        .findFirst();
  }

  @Override
  public void validatePostLoad() {
    this.specialGreetings =
        this.specialGreetings.stream()
            .filter(
                specialGreeting -> {
                  if (Objects.equals(specialGreeting.player, "")) {
                    return false;
                  }
                  specialGreeting.greetings =
                      specialGreeting.greetings.stream().filter(s -> !s.trim().isEmpty()).toList();
                  specialGreeting.returningGreetings =
                      specialGreeting.returningGreetings.stream()
                          .filter(s -> !s.trim().isEmpty())
                          .toList();
                  return true;
                })
            .toList();
  }
}
