package com.jazzkuh.itempresets.utils.command.resolvers.completion;

import com.jazzkuh.itempresets.utils.command.resolvers.CompletionResolver;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public final class GameModeCompletionResolver implements CompletionResolver {
    @Override
    public List<String> resolve(CommandSender sender, String arg) {
        return Arrays.stream(GameMode.values()).toList().stream().map(gameMode -> gameMode.name().toLowerCase()).toList();
    }
}