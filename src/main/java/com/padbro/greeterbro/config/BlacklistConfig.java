package com.padbro.greeterbro.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlacklistConfig implements ConfigData {
    public static final Codec<BlacklistConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.listOf().fieldOf("players").forGetter((BlacklistConfig sc) -> sc.players)
    ).apply(instance, BlacklistConfig::new));

    @Comment("Players that should never be greeted.")
    public List<String> players;

    public BlacklistConfig(
            List<String> players
    ) {
        this.players = players;
    }

    public BlacklistConfig() {
        this(new ArrayList<>());
    }

    @Override
    public void validatePostLoad() {
        this.players = players.stream().filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
    }
}
