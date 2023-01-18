package com.jazzkuh.itempresets.utils.command.resolvers.context;

import com.jazzkuh.itempresets.utils.command.resolvers.ContextResolver;
import org.bukkit.command.CommandSender;

public final class IntegerResolver implements ContextResolver<Integer> {
    @Override
    public Integer resolve(CommandSender sender, String args) {
        try {
            return Integer.parseInt(args);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}