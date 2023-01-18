package com.jazzkuh.itempresets.utils.command.resolvers.completion;

import com.jazzkuh.itempresets.common.framework.PresetEditType;
import com.jazzkuh.itempresets.utils.command.resolvers.CompletionResolver;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public final class PresetEditTypeCompletionResolver implements CompletionResolver {
    @Override
    public List<String> resolve(CommandSender sender, String arg) {
        return Arrays.stream(PresetEditType.values()).toList().stream().map(presetEditType -> presetEditType.name().toLowerCase()).toList();
    }
}