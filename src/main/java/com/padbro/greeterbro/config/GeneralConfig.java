package com.padbro.greeterbro.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.stream.Collectors;

public class GeneralConfig implements ConfigData {
    public static final Codec<GeneralConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("configVersion").forGetter((GeneralConfig sc) -> sc.configVersion),
            Codec.BOOL.fieldOf("enabled").forGetter((GeneralConfig sc) -> sc.enabled),
            Codec.INT.fieldOf("greetingChance").forGetter((GeneralConfig sc) -> sc.greetingChance),
            Codec.FLOAT.fieldOf("minDelay").forGetter((GeneralConfig sc) -> sc.minDelay),
            Codec.FLOAT.fieldOf("maxDelay").forGetter((GeneralConfig sc) -> sc.maxDelay)
    ).apply(instance, GeneralConfig::new));

    @Comment("Do not edit the version manually.")
    public int configVersion;

    @Comment("Enables/Disables GreeterBro completely on the server.")
    public Boolean enabled;
    @Comment("Forces a specific greeting chance from 0-100. (-1 will not enforce the chance)")
    public int greetingChance;

    @Comment("The min delay before sending a greeting in seconds. (-1 will not enforce the min delay)")
    public float minDelay;
    @Comment("The max delay before sending a greeting in seconds. (-1 will not enforce the max delay)")
    public float maxDelay;

    public GeneralConfig(
            int configVersion,
            Boolean enabled,
            int greetingChance,
            float minDelay,
            float maxDelay
    ) {
        this.configVersion = configVersion;
        this.enabled = enabled;
        this.greetingChance = greetingChance;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
    }

    public GeneralConfig() {
        this(1, true, -1, 3, 5);
    }

    @Override
    public void validatePostLoad() {
        float actualMin = Math.min(this.minDelay, this.maxDelay);
        float actualMax = Math.max(this.minDelay, this.maxDelay);
        this.minDelay = Math.max(actualMin, 0);
        this.maxDelay = Math.max(actualMax, 0);
    }
}
