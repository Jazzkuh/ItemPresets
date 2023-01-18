package com.jazzkuh.itempresets.utils.configuration;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;

public abstract class ConfigurateConfig {
    protected final @Getter YamlConfigurationLoader loader;
    protected @Getter CommentedConfigurationNode rootNode;

    public ConfigurateConfig(Plugin plugin, String name) {
        loader = YamlConfigurationLoader.builder()
                .path(plugin.getDataFolder().toPath().resolve(name))
                .indent(2)
                .nodeStyle(NodeStyle.BLOCK)
                .headerMode(HeaderMode.PRESET)
                .build();

        try {
            rootNode = loader.load();
        } catch (IOException e) {
            Bukkit.getLogger().warning("An error occurred while loading this configuration: " + e.getMessage());
        }
    }

    public void saveConfiguration() {
        try {
            loader.save(rootNode);
        } catch (final ConfigurateException e) {
            Bukkit.getLogger().warning("Unable to save your messages configuration! Sorry! " + e.getMessage());
        }
    }
}
