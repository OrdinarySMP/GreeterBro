package com.padbro.greeterbro.client.commands;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.padbro.greeterbro.client.managers.AfkManager;
import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.config.AfkNotifyType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class AfkCommand {
  public static void register(LiteralArgumentBuilder<FabricClientCommandSource> root) {
    root.then(literal("afk").executes(AfkCommand::afk));
  }

  public static int afk(CommandContext<FabricClientCommandSource> context) {
    FabricClientCommandSource source = context.getSource();
    if (AfkManager.isAfk) {
      source.sendError(Text.translatable("text.command.GreeterBro.afk.error.isAfk"));
      return 0;
    }
    AfkManager.goAfk();
    if (GreeterBroClient.getConfig().afkConfig.notifyType == AfkNotifyType.Disabled) {
      source.sendFeedback(
          Text.translatable("text.message.GreeterBro.afk.enter_afk").formatted(Formatting.GRAY));
    }
    return 0;
  }
}
