package com.padbro.greeterbro.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

public class NameChangeConfig {
    public static final Codec<NameChangeConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("greetingChance").forGetter((NameChangeConfig sc) -> sc.greetingChance)
    ).apply(instance, NameChangeConfig::new));

    @Comment("Forces a specific greeting chance from 0-100. (-1 will not enforce the chance)")
    public Integer greetingChance;

    public NameChangeConfig(
            int greetingChance
    ) {
        this.greetingChance = greetingChance;
    }

    public NameChangeConfig() {
        this(-1);
    }
}
