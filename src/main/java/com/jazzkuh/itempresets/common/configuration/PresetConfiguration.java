package com.jazzkuh.itempresets.common.configuration;

import com.jazzkuh.itempresets.ItemPresets;
import com.jazzkuh.itempresets.utils.configuration.ConfigurateConfig;

public class PresetConfiguration extends ConfigurateConfig {
    public PresetConfiguration() {
        super(ItemPresets.getInstance(), "presets.yml");
    }
}
