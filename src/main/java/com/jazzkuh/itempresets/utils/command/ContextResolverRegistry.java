package com.jazzkuh.itempresets.utils.command;

import com.jazzkuh.itempresets.common.framework.PresetEditType;
import com.jazzkuh.itempresets.utils.command.resolvers.ContextResolver;
import com.jazzkuh.itempresets.utils.command.resolvers.context.*;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContextResolverRegistry {
    private static final Map<Class<?>, ContextResolver<?>> resolvers = new HashMap<>();

    static {
        resolvers.put(String.class, new StringResolver());
        resolvers.put(Boolean.class, new BooleanResolver());
        resolvers.put(Integer.class, new IntegerResolver());
        resolvers.put(Double.class, new DoubleResolver());
        resolvers.put(Long.class, new LongResolver());
        resolvers.put(Float.class, new FloatResolver());
        resolvers.put(UUID.class, new UUIDResolver());
        resolvers.put(Player.class, new PlayerResolver());
        resolvers.put(GameMode.class, new GameModeResolver());
        resolvers.put(World.class, new WorldResolver());
        resolvers.put(PresetEditType.class, new PresetEditTypeResolver());
    }

    public static void registerResolver(Class<?> typeClass, ContextResolver<?> resolver) {
        resolvers.put(typeClass, resolver);
    }

    public static ContextResolver<?> getResolver(Class<?> typeClass) {
        if (typeClass.isArray()) {
            return resolvers.get(typeClass.getComponentType());
        }
        return resolvers.get(typeClass);
    }
}