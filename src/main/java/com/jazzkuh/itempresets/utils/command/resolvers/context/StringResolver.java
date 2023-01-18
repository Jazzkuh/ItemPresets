package com.jazzkuh.itempresets.utils.command.resolvers.context;

import com.jazzkuh.itempresets.utils.command.resolvers.ContextResolver;
import org.bukkit.command.CommandSender;

public final class StringResolver implements ContextResolver<String> {
    @Override
    public String resolve(CommandSender sender, String arg) {
        return arg;
    }
}