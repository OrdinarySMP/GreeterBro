package com.padbro.greeterbro.client.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class BlacklistGetCommand implements Command<FabricClientCommandSource> {
  @Override
  public int run(CommandContext<FabricClientCommandSource> context) {
    GreeterBroConfig config = GreeterBroClient.getConfig();
    FabricClientCommandSource source = context.getSource();
    String players = String.join(", ", config.blacklistConfig.players);

    source.sendFeedback(
        Text.translatable(
            "text.command.GreeterBro.blacklist.get.success",
            config.blacklistConfig.players.size(),
            players));
    return 0;
  }
}
