package com.padbro.greeterbro.client.commands;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;

public class CommandManager {
  private static final LiteralArgumentBuilder<FabricClientCommandSource> commandRoot =
      literal("greeterBro");

  public static void register() {
    ClientCommandRegistrationCallback.EVENT.register(CommandManager::registerGreeterBro);
  }

  private static void registerGreeterBro(
      CommandDispatcher<FabricClientCommandSource> dispatcher,
      CommandRegistryAccess registryAccess) {

    BlacklistCommand.register(commandRoot);
    EnableCommand.register(commandRoot);
    DisableCommand.register(commandRoot);

    dispatcher.register(commandRoot);
  }
}
