

package com.saturnclient.saturnclient.event.events;

import com.saturnclient.saturnclient.event.Event;

public class KeyEvent extends Event {
    private final int key;
    private final int code;
    private final Status status;

    public KeyEvent(int key, int code, Status status) {
        this.key = key;
        this.code = code;
        this.status = status;
    }

    /**
     * Gets the key.
     *
     * @return key
     */
    public int getKey() {
        return key;
    }

    /**
     * Gets the key code.
     *
     * @return key code
     */
    public int getCode() {
        return code;
    }

    /**
     * Event enums.
     */
    public enum Status {
        PRESSED,
        RELEASED
    }
}
