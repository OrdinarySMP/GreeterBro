package com.padbro.greeterbro.client;

import com.padbro.greeterbro.client.config.GreeterBroConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

public class GreeterBroClient implements ClientModInitializer {

    public static ConfigHolder<GreeterBroConfig> config;

    @Override
    public void onInitializeClient() {
        config = AutoConfig.register(GreeterBroConfig.class, Toml4jConfigSerializer::new);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            TickManager.onTick();
        });
    }
}
