package com.padbro.greeterbro.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "GreeterBro")
public class GreeterBroServerConfig implements ConfigData {
    public static final Codec<GreeterBroServerConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GeneralConfig.CODEC.fieldOf("generalConfig").forGetter((GreeterBroServerConfig gbsc) -> gbsc.generalConfig),
            AfkConfig.CODEC.fieldOf("afkConfig").forGetter((GreeterBroServerConfig gbsc) -> gbsc.afkConfig),
            ReturningPlayerConfig.CODEC.fieldOf("returningPlayerConfig").forGetter((GreeterBroServerConfig gbsc) -> gbsc.returningPlayerConfig),
            FirstJoinConfig.CODEC.fieldOf("firstJoinConfig").forGetter((GreeterBroServerConfig gbsc) -> gbsc.firstJoinConfig),
            NameChangeConfig.CODEC.fieldOf("nameChangeConfig").forGetter((GreeterBroServerConfig gbsc) -> gbsc.nameChangeConfig),
            BlacklistConfig.CODEC.fieldOf("blacklistConfig").forGetter((GreeterBroServerConfig gbsc) -> gbsc.blacklistConfig)
    ).apply(instance, GreeterBroServerConfig::new));

    @ConfigEntry.Category("general")
    public GeneralConfig generalConfig;
    @ConfigEntry.Category("afk")
    public AfkConfig afkConfig;
    @ConfigEntry.Category("returningPlayer")
    public ReturningPlayerConfig returningPlayerConfig;
    @ConfigEntry.Category("firstJoin")
    public FirstJoinConfig firstJoinConfig;
    @ConfigEntry.Category("nameChange")
    public NameChangeConfig nameChangeConfig;
    @ConfigEntry.Category("blacklist")
    public BlacklistConfig blacklistConfig;

    public GreeterBroServerConfig(GeneralConfig generalConfig, AfkConfig afkConfig, ReturningPlayerConfig returningPlayerConfig, FirstJoinConfig firstJoinConfig, NameChangeConfig nameChangeConfig, BlacklistConfig blacklistConfig) {
        this.generalConfig = generalConfig;
        this.afkConfig = afkConfig;
        this.returningPlayerConfig = returningPlayerConfig;
        this.firstJoinConfig = firstJoinConfig;
        this.nameChangeConfig = nameChangeConfig;
        this.blacklistConfig = blacklistConfig;
    }

    public GreeterBroServerConfig() {
        this.generalConfig = new GeneralConfig();
        this.afkConfig = new AfkConfig();
        this.returningPlayerConfig = new ReturningPlayerConfig();
        this.firstJoinConfig = new FirstJoinConfig();
        this.nameChangeConfig = new NameChangeConfig();
        this.blacklistConfig = new BlacklistConfig();
    }
}