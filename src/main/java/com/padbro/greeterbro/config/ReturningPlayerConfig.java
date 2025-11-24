package com.padbro.greeterbro.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

public class ReturningPlayerConfig {
    public static final Codec<ReturningPlayerConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("greetingChance").forGetter((ReturningPlayerConfig sc) -> sc.greetingChance)
    ).apply(instance, ReturningPlayerConfig::new));

    @Comment("Forces a specific greeting chance from 0-100. (-1 will not enforce the chance)")
    public int greetingChance = -1;

    public ReturningPlayerConfig(
            int greetingChance
    ) {
        this.greetingChance = greetingChance;
    }

    public ReturningPlayerConfig() {
        this(-1);
    }
}
