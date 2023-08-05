

package com.saturnclient.saturnclient.setting.settings;

import com.saturnclient.saturnclient.setting.Setting;

public class KeybindSetting extends Setting {
    public int code;

    public KeybindSetting(int keyCode) {
        super("KeyBind", "Sets a keybind for the module.");
        this.code = keyCode;
    }

    /**
     * Gets the key code.
     */
    public int getKeyCode() {
        return code;
    }

    /**
     * Sets the key code.
     *
     * @param keyCode key code to set
     */
    public void setKeyCode(int keyCode) {
        this.code = keyCode;
    }
}
