package com.jazzkuh.itempresets.common.framework;

import lombok.Getter;
import org.spongepowered.configurate.ConfigurationNode;

import java.util.ArrayList;
import java.util.List;

public class ConfiguredPreset {
    private final @Getter ConfigurationNode rootNode;
    private final @Getter String identifier;
    private final @Getter String name;
    private final @Getter String lore;
    private final @Getter boolean unbreakable;

    public ConfiguredPreset(ConfigurationNode rootNode) {
        this.rootNode = rootNode;

        this.identifier = rootNode.node("identifier").getString();
        this.name = rootNode.node("name").getString();
        this.lore = rootNode.node("lore").getString();
        this.unbreakable = rootNode.node("unbreakable").getBoolean(false);
    }
}
