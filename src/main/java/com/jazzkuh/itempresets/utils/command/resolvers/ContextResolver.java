package com.jazzkuh.itempresets.utils.command.resolvers;

import org.bukkit.command.CommandSender;

public interface ContextResolver<T> {
    T resolve(CommandSender sender, String arg);
}
