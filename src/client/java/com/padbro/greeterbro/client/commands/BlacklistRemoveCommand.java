package com.padbro.greeterbro.client.commands;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class BlacklistRemoveCommand implements Command<FabricClientCommandSource> {
  @Override
  public int run(CommandContext<FabricClientCommandSource> context) {
    GreeterBroConfig config = GreeterBroClient.getConfig();
    FabricClientCommandSource source = context.getSource();
    String player = getString(context, "player");

    if (!config.blacklistConfig.players.contains(player)) {
      source.sendError(Text.translatable("text.command.GreeterBro.blacklist.remove.error.exists"));
      return 0;
    }

    config.blacklistConfig.players.remove(player);
    GreeterBroClient.saveConfig();
    source.sendFeedback(
        Text.translatable("text.command.GreeterBro.blacklist.remove.success", player));
    return 0;
  }
}
