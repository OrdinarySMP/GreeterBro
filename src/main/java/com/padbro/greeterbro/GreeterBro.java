package com.padbro.greeterbro;

import com.padbro.greeterbro.commands.CommandManager;
import com.padbro.greeterbro.config.GreeterBroServerConfig;
import com.padbro.greeterbro.records.ConfigS2CPayload;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreeterBro implements ModInitializer {
    public static final String MOD_ID = "GreeterBro";
    public static final Logger LOGGER = LoggerFactory.getLogger(GreeterBro.MOD_ID);
    public static ConfigHolder<GreeterBroServerConfig> config;

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playS2C().register(ConfigS2CPayload.ID, ConfigS2CPayload.CODEC);

        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER) {
            return;
        }

        CommandManager.register();
        config = AutoConfig.register(GreeterBroServerConfig.class, JanksonConfigSerializer::new);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            sendConfigToClient(handler.getPlayer());
        });
    }

    public static void loadConfig() {
        config.load();
    }

    public static void sendConfigToClient(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new ConfigS2CPayload(config.getConfig()));
    }
}
