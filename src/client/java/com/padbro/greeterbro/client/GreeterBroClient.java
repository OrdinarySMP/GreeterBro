package com.padbro.greeterbro.client;

import com.padbro.greeterbro.client.commands.CommandManager;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import com.padbro.greeterbro.client.managers.AfkManager;
import com.padbro.greeterbro.client.managers.MigrationManager;
import com.padbro.greeterbro.client.managers.TickManager;
import com.padbro.greeterbro.config.GreeterBroServerConfig;
import com.padbro.greeterbro.records.ConfigS2CPayload;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class GreeterBroClient implements ClientModInitializer {
    public static boolean isJoining = false;
    public static GreeterBroServerConfig serverConfig;
    private static ConfigHolder<GreeterBroConfig> config;
    private static JoinCache joinCache;

    public static GreeterBroConfig getConfig() {
        config.save();
        return config.get();
    }

    public static JoinCache getJoinCache() {
        return joinCache;
    }

    public static void saveConfig() {
        config.save();
    }

    @Override
    public void onInitializeClient() {
        config = AutoConfig.register(GreeterBroConfig.class, Toml4jConfigSerializer::new);
        MigrationManager.migrate();

        joinCache = JoinCache.loadCache();

        ClientTickEvents.END_CLIENT_TICK.register(
                client -> {
                    TickManager.onTick();
                    AfkManager.onTick();
                });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            serverConfig = null;
        });

        CommandManager.register();

        ClientPlayNetworking.registerGlobalReceiver(
                ConfigS2CPayload.ID,
                (payload, context) -> {
                    serverConfig = payload.config();
                });
    }
}
