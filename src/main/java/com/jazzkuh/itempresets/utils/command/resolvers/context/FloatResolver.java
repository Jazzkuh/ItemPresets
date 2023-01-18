package com.jazzkuh.itempresets.utils.command.resolvers.context;

import com.jazzkuh.itempresets.utils.command.resolvers.ContextResolver;
import org.bukkit.command.CommandSender;

public final class FloatResolver implements ContextResolver<Float> {
    @Override
    public Float resolve(CommandSender sender, String args) {
        try {
            return Float.parseFloat(args);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}