

package com.saturnclient.saturnclient.module.modules.render;

import com.saturnclient.saturnclient.gui.SaturnGui;
import com.saturnclient.saturnclient.module.Module;
import org.lwjgl.glfw.GLFW;

public class Gui extends Module {

    public Gui() {
        super("Gui", "Saturn gui.", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.RENDER);
    }

    @Override
    public void onEnable() {
        mc.setScreen(new SaturnGui());
    }

    @Override
    public void onDisable() {
        mc.setScreen(null);
    }
}
