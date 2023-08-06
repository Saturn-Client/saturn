package com.saturnclient.saturnclient.interfaces;

import net.minecraft.client.util.Session;

public interface IMinecraftClient {
    public void rightClick();

    public void setItemUseCooldown(int itemUseCooldown);

    public int getItemUseCooldown();

    public void setSession(Session session);
}