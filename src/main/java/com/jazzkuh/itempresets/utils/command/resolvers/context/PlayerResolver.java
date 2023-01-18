package com.jazzkuh.itempresets.utils.command.resolvers.context;

import com.jazzkuh.itempresets.utils.command.resolvers.ContextResolver;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class PlayerResolver implements ContextResolver<Player> {
    @Override
    public Player resolve(CommandSender sender, String args) {
        return Bukkit.getPlayer(args);
    }
}