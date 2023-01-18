package com.jazzkuh.itempresets.utils.command.resolvers.completion;

import com.jazzkuh.itempresets.ItemPresets;
import com.jazzkuh.itempresets.utils.command.resolvers.CompletionResolver;
import org.bukkit.command.CommandSender;

import java.util.List;

public final class PresetCompletionResolver implements CompletionResolver {
    @Override
    public List<String> resolve(CommandSender sender, String arg) {
        return ItemPresets.getInstance().getPresets().stream().map(preset -> preset.getIdentifier()).toList();
    }
}