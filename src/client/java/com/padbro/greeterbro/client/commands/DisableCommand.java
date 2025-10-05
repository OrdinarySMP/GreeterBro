package com.padbro.greeterbro.client.commands;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class DisableCommand {
  public static void register(LiteralArgumentBuilder<FabricClientCommandSource> root) {
    root.then(literal("disable").executes(DisableCommand::disable));
  }

  public static int disable(CommandContext<FabricClientCommandSource> context) {
    GreeterBroConfig config = GreeterBroClient.getConfig();
    FabricClientCommandSource source = context.getSource();

    if (!config.generalConfig.enable) {
      source.sendError(Text.translatable("text.command.GreeterBro.disable.error.disabled"));
      return 0;
    }

    config.generalConfig.enable = false;
    GreeterBroClient.saveConfig();
    source.sendFeedback(
        Text.translatable("text.command.GreeterBro.disable.success").formatted(Formatting.GRAY));

    return 0;
  }
}
