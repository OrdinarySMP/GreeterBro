package com.padbro.greeterbro.client;

import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.padbro.greeterbro.client.commands.BlacklistAddCommand;
import com.padbro.greeterbro.client.commands.BlacklistGetCommand;
import com.padbro.greeterbro.client.commands.BlacklistRemoveCommand;
import com.padbro.greeterbro.client.commands.provider.BlacklistSuggestionProvider;
import com.padbro.greeterbro.client.commands.provider.PlayerSuggestionProvider;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class GreeterBroClient implements ClientModInitializer {

  public static ConfigHolder<GreeterBroConfig> config;

  @Override
  public void onInitializeClient() {
    config = AutoConfig.register(GreeterBroConfig.class, Toml4jConfigSerializer::new);
    ClientTickEvents.END_CLIENT_TICK.register(
        client -> {
          TickManager.onTick();
        });

    SuggestionProvider<FabricClientCommandSource> playerSuggestionProvider =
        new PlayerSuggestionProvider();
    SuggestionProvider<FabricClientCommandSource> blacklistSuggestionProvider =
        new BlacklistSuggestionProvider();
    ClientCommandRegistrationCallback.EVENT.register(
        ((dispatcher, registryAccess) ->
            dispatcher.register(
                literal("greeterBro")
                    .then(
                        literal("blacklist")
                            .then(
                                literal("add")
                                    .then(
                                        argument("player", string())
                                            .suggests(playerSuggestionProvider)
                                            .executes(new BlacklistAddCommand())))
                            .then(
                                literal("remove")
                                    .then(
                                        argument("player", string())
                                            .suggests(blacklistSuggestionProvider)
                                            .executes(new BlacklistRemoveCommand())))
                            .then(literal("get").executes(new BlacklistGetCommand()))))));
  }
}
