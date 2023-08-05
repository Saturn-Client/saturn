

package com.saturnclient.saturnclient.event.events;

import com.saturnclient.saturnclient.event.Event;
import net.minecraft.client.gui.DrawContext;

public class RenderInGameHudEvent extends Event {
    private final DrawContext context;

    public RenderInGameHudEvent(DrawContext context) {
        this.context = context;
    }

    /**
     * Gets the draw context.
     *
     * @return context
     */
    public DrawContext getContext() {
        return context;
    }
}
