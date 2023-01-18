package com.jazzkuh.itempresets;

import com.jazzkuh.itempresets.common.commands.PresetCommand;
import com.jazzkuh.itempresets.common.configuration.DefaultConfiguration;
import com.jazzkuh.itempresets.common.configuration.PresetConfiguration;
import com.jazzkuh.itempresets.common.framework.ConfiguredPreset;
import com.jazzkuh.itempresets.common.framework.PresetEditType;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.*;

public final class ItemPresets extends JavaPlugin {
    private static @Getter @Setter ItemPresets instance;
    private static @Getter PresetConfiguration presetConfiguration;
    private static @Getter DefaultConfiguration defaultConfiguration;
    private @Getter @Setter List<ConfiguredPreset> presets = new ArrayList<>();

    @Override
    public void onEnable() {
        setInstance(this);

        // register configuration files
        defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.saveConfiguration();

        presetConfiguration = new PresetConfiguration();
        this.loadPresets();
        presetConfiguration.saveConfiguration();

        // register commands
        new PresetCommand().register(this);
    }

    @Override
    public void onDisable() {
    }

    public void loadPresets() {
        presets.clear();
        CommentedConfigurationNode presetsNode = getPresetConfiguration().getRootNode().node("presets");
        for (CommentedConfigurationNode node : presetsNode.childrenMap().values()) {
            presets.add(new ConfiguredPreset(node));
        }
    }

    @SneakyThrows
    public void createPreset(String identifier) {
        CommentedConfigurationNode presetsNode = getPresetConfiguration().getRootNode().node("presets");
        CommentedConfigurationNode rootNode = presetsNode.node(identifier);
        rootNode.node("identifier").set(identifier);

        getPresetConfiguration().saveConfiguration();
        this.loadPresets();
    }

    public void editPreset(String identifier, PresetEditType editType, Object value) throws SerializationException {
        CommentedConfigurationNode presetsNode = getPresetConfiguration().getRootNode().node("presets", identifier);

        if (presetsNode == null) return;
        presetsNode.node(editType.getKey()).set(editType.getTypeClass(), value);

        getPresetConfiguration().saveConfiguration();
        this.loadPresets();
    }

    @SneakyThrows
    public void deletePreset(String identifier) {
        CommentedConfigurationNode presetsNode = getPresetConfiguration().getRootNode().node("presets", identifier);

        if (presetsNode == null) return;
        presetsNode.set(null);

        getPresetConfiguration().saveConfiguration();
        this.loadPresets();
    }

    public Optional<ConfiguredPreset> findPreset(String identifier) {
        return presets.stream().filter(preset -> preset.getIdentifier().equals(identifier)).findFirst();
    }
}
