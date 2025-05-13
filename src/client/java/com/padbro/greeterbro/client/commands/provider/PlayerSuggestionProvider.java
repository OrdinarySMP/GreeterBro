package com.padbro.greeterbro.client.commands.provider;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class PlayerSuggestionProvider
    implements SuggestionProvider<FabricClientCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(
            CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) {
        FabricClientCommandSource source = context.getSource();

        // Thankfully, the ServerCommandSource has a method to get a list of player names.
        Collection<String> playerNames = source.getPlayerNames();

        // Add all player names to the builder.
        for (String playerName : playerNames) {
            builder.suggest(playerName);
        }

        // Lock the suggestions after we've modified them.
        return builder.buildFuture();
    }
}
