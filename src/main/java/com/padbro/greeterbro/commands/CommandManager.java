package com.padbro.greeterbro.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class CommandManager {
    private static final LiteralArgumentBuilder<ServerCommandSource> commandRoot =
            literal("greeterBroServer");

    public static void register() {
        CommandRegistrationCallback.EVENT.register(CommandManager::registerGreeterBro);
    }

    private static void registerGreeterBro(
            CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess commandRegistryAccess,
            RegistrationEnvironment registrationEnvironment
    ) {
        ReloadCommand.register(commandRoot);
        dispatcher.register(commandRoot);
    }
}
