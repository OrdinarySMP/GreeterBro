package com.padbro.greeterbro.client.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal;

public class CommandManager {
    private static final LiteralArgumentBuilder<FabricClientCommandSource> commandRoot =
            literal("greeterBro");

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register(CommandManager::registerGreeterBro);
    }

    private static void registerGreeterBro(
            CommandDispatcher<FabricClientCommandSource> dispatcher,
            CommandBuildContext registryAccess) {

        BlacklistCommand.register(commandRoot);
        EnableCommand.register(commandRoot);
        DisableCommand.register(commandRoot);
        AfkCommand.register(commandRoot);

        dispatcher.register(commandRoot);
    }
}
