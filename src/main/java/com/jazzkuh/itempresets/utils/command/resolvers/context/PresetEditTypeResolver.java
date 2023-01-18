package com.jazzkuh.itempresets.utils.command.resolvers.context;

import com.jazzkuh.itempresets.common.framework.PresetEditType;
import com.jazzkuh.itempresets.utils.command.resolvers.ContextResolver;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.command.CommandSender;

public final class PresetEditTypeResolver implements ContextResolver<PresetEditType> {
    @Override
    public PresetEditType resolve(CommandSender sender, String args) {
        if (!EnumUtils.isValidEnum(PresetEditType.class, args.toUpperCase())) return null;
        return PresetEditType.valueOf(args.toUpperCase());
    }
}