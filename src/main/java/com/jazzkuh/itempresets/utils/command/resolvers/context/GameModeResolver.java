package com.jazzkuh.itempresets.utils.command.resolvers.context;

import com.jazzkuh.itempresets.utils.command.resolvers.ContextResolver;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;

public final class GameModeResolver implements ContextResolver<GameMode> {
    @Override
    public GameMode resolve(CommandSender sender, String arg) {
        if (!EnumUtils.isValidEnum(GameMode.class, arg.toUpperCase())) {
            return null;
        }

        return GameMode.valueOf(arg.toUpperCase());
    }
}