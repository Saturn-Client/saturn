package com.saturnclient.saturnclient.module.modules.player;

import com.saturnclient.saturnclient.module.Module;
import org.lwjgl.glfw.GLFW;

public class Dummy extends Module {

    public Dummy() {
        super("Dummy", "this is a test.", GLFW.GLFW_KEY_UNKNOWN, Category.PLAYER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}