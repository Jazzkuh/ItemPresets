package com.jazzkuh.itempresets.utils.command.resolvers.context;

import com.jazzkuh.itempresets.utils.command.resolvers.ContextResolver;
import org.bukkit.command.CommandSender;

public final class BooleanResolver implements ContextResolver<Boolean> {
    @Override
    public Boolean resolve(CommandSender sender, String args) {
        try {
            return Boolean.parseBoolean(args);
        } catch (Exception e) {
            return null;
        }
    }
}