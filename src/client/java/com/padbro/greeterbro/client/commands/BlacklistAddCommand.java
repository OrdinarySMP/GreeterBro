package com.padbro.greeterbro.client.commands;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class BlacklistAddCommand implements Command<FabricClientCommandSource> {
  @Override
  public int run(CommandContext<FabricClientCommandSource> context) {
    GreeterBroConfig config = GreeterBroClient.config.get();
    FabricClientCommandSource source = context.getSource();
    String player = getString(context, "player");
    if (config.blacklistConfig.players.contains(player)) {
      source.sendError(Text.literal("The player is already in the blacklist."));
      return 0;
    }

    config.blacklistConfig.players.add(player);
    source.sendFeedback(
        Text.literal("The player \"" + player + "\" has been added to the blacklist."));
    return 0;
  }
}
