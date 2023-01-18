package com.jazzkuh.itempresets.common.configuration;

import com.jazzkuh.itempresets.ItemPresets;
import com.jazzkuh.itempresets.utils.configuration.ConfigurateConfig;
import lombok.Getter;

public class DefaultConfiguration extends ConfigurateConfig {
    private final @Getter String version;

    public DefaultConfiguration() {
        super(ItemPresets.getInstance(), "config.yml");

        this.version = rootNode.node("_version").getString("1");
    }
}
