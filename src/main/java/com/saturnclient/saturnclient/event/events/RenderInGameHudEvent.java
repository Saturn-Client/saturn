/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

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
