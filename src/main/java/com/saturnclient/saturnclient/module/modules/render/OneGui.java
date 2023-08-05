/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.saturnclient.saturnclient.module.modules.render;

import com.saturnclient.saturnclient.Saturn;
import com.saturnclient.saturnclient.gui.SaturnOneGui;
import com.saturnclient.saturnclient.module.Module;
import org.lwjgl.glfw.GLFW;

public class OneGui extends Module {
    public OneGui() {
        super("OneGui", "One window, no tabs.", GLFW.GLFW_KEY_RIGHT_CONTROL, Category.RENDER);
    }

    @Override
    public void onEnable() {
        Saturn.getInstance().getModuleManager().getModule("Gui").setEnabled(false);
        mc.setScreen(new SaturnOneGui());
    }

    @Override
    public void onDisable() {
        mc.setScreen(null);
    }

}
