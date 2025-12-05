package com.padbro.greeterbro.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.padbro.greeterbro.GreeterBro;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class ReloadCommand {
    public static void register(LiteralArgumentBuilder<ServerCommandSource> root) {
        root.then(LiteralArgumentBuilder.<ServerCommandSource>literal("reload")
                .executes(ReloadCommand::reload));
    }

    private static <ServerLevel> int reload(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        GreeterBro.loadConfig();
        for (ServerPlayerEntity player : PlayerLookup.world(source.getWorld())) {
            GreeterBro.sendConfigToClient(player);
        }
        source.sendFeedback(() -> Text.literal("GreeterBro config reloaded"), false);
        return 1;
    }
}