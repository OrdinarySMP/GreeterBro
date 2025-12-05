package com.padbro.greeterbro.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

public class AfkConfig {
    public static final Codec<AfkConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("enforceAfkMode").forGetter((AfkConfig sc) -> sc.enforceAfkMode),
            Codec.INT.fieldOf("afkTime").forGetter((AfkConfig sc) -> sc.afkTime)
    ).apply(instance, AfkConfig::new));

    @Comment("If afk mode should be enforced")
    public Boolean enforceAfkMode;
    @Comment("The time till a player counts as afk. (-1 will not enforce the time)")
    public int afkTime;

    public AfkConfig(
            Boolean enforceAfkMode,
            int afkTime
    ) {
        this.enforceAfkMode = enforceAfkMode;
        this.afkTime = afkTime;
    }

    public AfkConfig() {
        this(false, -1);
    }
}
