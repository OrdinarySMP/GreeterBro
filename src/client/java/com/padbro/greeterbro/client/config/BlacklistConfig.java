package com.padbro.greeterbro.client.config;

import com.padbro.greeterbro.client.GreeterBroClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Config(name = "blacklist")
public class BlacklistConfig implements ConfigData {
    private List<String> players = new ArrayList<>();

    public List<String> getAllPlayers() {
        return Stream.concat(getPlayers().stream(), getServerPlayers().stream()).toList();
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public void removePlayer(String name) {
        this.players.remove(name);
    }

    public void addPlayer(String name) {
        this.players.add(name);
    }

    public List<String> getServerPlayers() {
        if (GreeterBroClient.serverConfig != null) {
            return GreeterBroClient.serverConfig.blacklistConfig.players;
        }

        return List.of();
    }

    @Override
    public void validatePostLoad() {
        this.players = players.stream().filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
    }
}
