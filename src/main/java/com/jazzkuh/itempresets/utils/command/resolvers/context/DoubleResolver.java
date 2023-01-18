package com.jazzkuh.itempresets.utils.command.resolvers.context;

import com.jazzkuh.itempresets.utils.command.resolvers.ContextResolver;
import org.bukkit.command.CommandSender;

public final class DoubleResolver implements ContextResolver<Double> {
    @Override
    public Double resolve(CommandSender sender, String args) {
        try {
            return Double.parseDouble(args);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}