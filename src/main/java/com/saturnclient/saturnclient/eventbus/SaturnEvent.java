

package com.saturnclient.saturnclient.eventbus;

public abstract class SaturnEvent {
    private boolean cancelled;
    private Era era;

    /**
     * Gets whether the event is cancelled.
     */
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Sets whether the event is cancelled.
     *
     * @param cancelled whether the event is cancelled
     */
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Cancels the event.
     */
    public void cancel() {
        setCancelled(true);
    }

    /**
     * Gets the era of the event.
     */
    public Era getEra() {
        return era;
    }

    /**
     * Sets the era of the event.
     *
     * @param era era of the event
     */
    public void setEra(Era era) {
        this.era = era;
    }

    /**
     * Event eras.
     */
    public enum Era {
        PRE,
        POST
    }
}
