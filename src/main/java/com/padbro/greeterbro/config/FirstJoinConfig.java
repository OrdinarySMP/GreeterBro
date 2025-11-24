package com.padbro.greeterbro.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

public class FirstJoinConfig {
    public static final Codec<FirstJoinConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("greetingChance").forGetter((FirstJoinConfig sc) -> sc.greetingChance)
    ).apply(instance, FirstJoinConfig::new));

    @Comment("Forces a specific greeting chance from 0-100. (-1 will not enforce the chance)")
    public int greetingChance;

    public FirstJoinConfig(
            int greetingChance
    ) {
        this.greetingChance = greetingChance;
    }

    public FirstJoinConfig() {
        this(-1);
    }
}
