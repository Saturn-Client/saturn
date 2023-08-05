

package com.saturnclient.saturnclient.setting.settings;

import com.saturnclient.saturnclient.setting.Setting;

public class StringSetting extends Setting {
    public String string;

    public StringSetting(String name, String description, String string) {
        super(name, description);
        this.string = string;
    }

    /**
     * Gets the value of the setting.
     */
    public String getString() {
        return string;
    }

    /**
     * Sets the value of the setting.
     *
     * @param string value to set
     */
    public void setString(String string) {
        this.string = string;
    }
}
