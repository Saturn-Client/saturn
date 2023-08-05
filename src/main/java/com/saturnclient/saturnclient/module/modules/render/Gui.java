/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

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
