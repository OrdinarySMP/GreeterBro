package com.padbro.greeterbro.client.commands.provider;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class PlayerSuggestionProvider implements SuggestionProvider<FabricClientCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(
            CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) {
        FabricClientCommandSource source = context.getSource();

        String partialQuery = builder.getRemainingLowerCase();

        Collection<String> playerNames = source.getOnlinePlayerNames();

        for (String playerName : playerNames) {
            if (playerName.toLowerCase().startsWith(partialQuery)) {
                builder.suggest(playerName);
            }
        }

        return builder.buildFuture();
    }
}
