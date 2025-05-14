package com.padbro.greeterbro.client.commands.provider;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.padbro.greeterbro.client.GreeterBroClient;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class BlacklistSuggestionProvider implements SuggestionProvider<FabricClientCommandSource> {
  @Override
  public CompletableFuture<Suggestions> getSuggestions(
      CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) {
    for (String playerName : GreeterBroClient.getConfig().blacklistConfig.players) {
      builder.suggest(playerName);
    }

    return builder.buildFuture();
  }
}
