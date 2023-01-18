package com.jazzkuh.itempresets.common.framework;

import lombok.Getter;

public enum PresetEditType {
    NAME(String.class, "name"),
    LORE(String.class, "lore"),
    UNBREAKABLE(Boolean.class, "unbreakable");

    private final @Getter Class<?> typeClass;
    private final @Getter String key;

    PresetEditType(Class<?> typeClass, String key) {
        this.typeClass = typeClass;
        this.key = key;
    }
}
