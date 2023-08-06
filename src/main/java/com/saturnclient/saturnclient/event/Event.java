package com.saturnclient.saturnclient.event;

import com.saturnclient.saturnclient.eventbus.SaturnEvent;
import net.minecraft.client.MinecraftClient;

public class Event extends SaturnEvent {
    public MinecraftClient mc = MinecraftClient.getInstance();

    public Event() {}
}