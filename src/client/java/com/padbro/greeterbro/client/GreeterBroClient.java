package com.padbro.greeterbro.client;

import com.padbro.greeterbro.client.commands.CommandManager;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreeterBroClient implements ClientModInitializer {

  private static ConfigHolder<GreeterBroConfig> config;
  private static JoinCache joinCache;
  public static boolean isJoining = false;
  public static final String MOD_ID = "GreeterBro";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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

    joinCache = JoinCache.loadCache();

    ClientTickEvents.END_CLIENT_TICK.register(
        client -> {
          TickManager.onTick();
          AfkManager.onTick();
        });

    CommandManager.register();
  }
}
