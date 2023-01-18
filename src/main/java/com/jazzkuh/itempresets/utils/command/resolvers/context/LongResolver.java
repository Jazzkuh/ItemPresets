package com.jazzkuh.itempresets.utils.command.resolvers.context;

import com.jazzkuh.itempresets.utils.command.resolvers.ContextResolver;
import org.bukkit.command.CommandSender;

public final class LongResolver implements ContextResolver<Long> {
    @Override
    public Long resolve(CommandSender sender, String args) {
        try {
            return Long.parseLong(args);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}