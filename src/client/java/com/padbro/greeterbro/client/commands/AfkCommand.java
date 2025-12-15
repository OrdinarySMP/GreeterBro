package com.padbro.greeterbro.client.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.config.AfkNotifyType;
import com.padbro.greeterbro.client.managers.AfkManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class AfkCommand {
    public static void register(LiteralArgumentBuilder<FabricClientCommandSource> root) {
        root.then(literal("afk").executes(AfkCommand::afk));
    }

    public static int afk(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        if (AfkManager.isAfk) {
            source.sendError(Component.translatable("text.command.GreeterBro.afk.error.isAfk"));
            return 0;
        }
        AfkManager.goAfk();
        if (GreeterBroClient.getConfig().afkConfig.notifyType == AfkNotifyType.Disabled) {
            source.sendFeedback(
                    Component.translatable("text.message.GreeterBro.afk.enter_afk").withStyle(ChatFormatting.GRAY));
        }
        return 0;
    }
}
