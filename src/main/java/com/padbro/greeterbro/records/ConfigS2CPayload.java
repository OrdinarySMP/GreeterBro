package com.padbro.greeterbro.records;

import com.padbro.greeterbro.GreeterBro;
import com.padbro.greeterbro.config.GreeterBroServerConfig;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ConfigS2CPayload(GreeterBroServerConfig config) implements CustomPayload {

    public static final CustomPayload.Id<ConfigS2CPayload> ID =
            new CustomPayload.Id<>(Identifier.of(GreeterBro.MOD_ID.toLowerCase(), "config"));

    public static final PacketCodec<ByteBuf, ConfigS2CPayload> CODEC =
            PacketCodecs.codec(GreeterBroServerConfig.CODEC)
                    .xmap(ConfigS2CPayload::new, ConfigS2CPayload::config);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
