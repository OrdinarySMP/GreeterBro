package com.padbro.greeterbro.client.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.commands.provider.BlacklistSuggestionProvider;
import com.padbro.greeterbro.client.commands.provider.PlayerSuggestionProvider;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class BlacklistCommand {

    public static void register(LiteralArgumentBuilder<FabricClientCommandSource> root) {
        root.then(
                literal("blacklist")
                        .executes(BlacklistCommand::list)
                        .then(
                                literal("add")
                                        .then(
                                                argument("player", string())
                                                        .suggests(new PlayerSuggestionProvider())
                                                        .executes(BlacklistCommand::add)))
                        .then(
                                literal("remove")
                                        .then(
                                                argument("player", string())
                                                        .suggests(new BlacklistSuggestionProvider())
                                                        .executes(BlacklistCommand::remove))));
    }

    public static int list(CommandContext<FabricClientCommandSource> context) {
        GreeterBroConfig config = GreeterBroClient.getConfig();
        FabricClientCommandSource source = context.getSource();
        String players = String.join(", ", config.blacklistConfig.players);

        source.sendFeedback(
                Component.translatable(
                                "text.command.GreeterBro.blacklist.get.success",
                                config.blacklistConfig.players.size(),
                                players)
                        .withStyle(ChatFormatting.GRAY));
        return 0;
    }

    public static int add(CommandContext<FabricClientCommandSource> context) {
        GreeterBroConfig config = GreeterBroClient.getConfig();
        FabricClientCommandSource source = context.getSource();
        String player = getString(context, "player");
        if (config.blacklistConfig.players.contains(player)) {

            source.sendError(Component.translatable("text.command.GreeterBro.blacklist.add.error.exists"));
            return 0;
        }

        config.blacklistConfig.players.add(player);
        GreeterBroClient.saveConfig();
        source.sendFeedback(
                Component.translatable("text.command.GreeterBro.blacklist.add.success", player)
                        .withStyle(ChatFormatting.GRAY));
        return 0;
    }

    public static int remove(CommandContext<FabricClientCommandSource> context) {
        GreeterBroConfig config = GreeterBroClient.getConfig();
        FabricClientCommandSource source = context.getSource();
        String player = getString(context, "player");

        if (!config.blacklistConfig.players.contains(player)) {
            source.sendError(Component.translatable("text.command.GreeterBro.blacklist.remove.error.exists"));
            return 0;
        }

        config.blacklistConfig.players.remove(player);
        GreeterBroClient.saveConfig();
        source.sendFeedback(
                Component.translatable("text.command.GreeterBro.blacklist.remove.success", player)
                        .withStyle(ChatFormatting.GRAY));
        return 0;
    }
}
