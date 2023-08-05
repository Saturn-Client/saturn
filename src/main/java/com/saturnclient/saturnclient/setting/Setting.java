

package com.saturnclient.saturnclient.setting;

public class Setting {
    public String name;
    public String description;

    public Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the name of the setting.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the setting.
     */
    public String getDescription() {
        return description;
    }
}
