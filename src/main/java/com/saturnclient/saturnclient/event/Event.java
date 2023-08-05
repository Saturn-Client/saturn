/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.saturnclient.saturnclient.event;

import com.saturnclient.saturnclient.eventbus.SaturnEvent;
import net.minecraft.client.MinecraftClient;

public class Event extends SaturnEvent {
    public MinecraftClient mc = MinecraftClient.getInstance();

    public Event() {
    }
}
