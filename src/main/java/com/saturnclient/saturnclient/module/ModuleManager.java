

package com.saturnclient.saturnclient.module;

import com.saturnclient.saturnclient.Saturn;
import com.saturnclient.saturnclient.event.events.KeyEvent;
import com.saturnclient.saturnclient.eventbus.SaturnSubscribe;
import com.saturnclient.saturnclient.module.modules.misc.DiscordRPC;
import com.saturnclient.saturnclient.module.modules.movement.Fly;
import com.saturnclient.saturnclient.module.modules.movement.Sprint;
import com.saturnclient.saturnclient.module.modules.player.Dummy;
import com.saturnclient.saturnclient.module.modules.render.Fullbright;
import com.saturnclient.saturnclient.module.modules.render.Gui;
import com.saturnclient.saturnclient.module.modules.render.Hud;
import com.saturnclient.saturnclient.module.modules.render.OneGui;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public final ArrayList<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<>();

        /* Add modules here */
        modules.add(new Dummy());
        modules.add(new Fullbright());
        modules.add(new Fly());
        modules.add(new Gui());
        modules.add(new OneGui());
        modules.add(new Hud());
        modules.add(new Sprint());
        modules.add(new DiscordRPC());
    }

    /**
     * Gets the modules.
     */
    public ArrayList<Module> getModules() {
        return modules;
    }

    /**
     * Gets enabled modules.
     */
    public ArrayList<Module> getEnabledModules() {
        ArrayList<Module> enabledModules = new ArrayList<>();
        for (Module module : modules) {
            if (module.isEnabled())
                enabledModules.add(module);
        }
        return enabledModules;
    }

    /**
     * Gets the module by name.
     *
     * @param name name of the module
     */
    public Module getModule(String name) {
        return modules.stream().filter(mm -> mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Gets the modules state
     *
     * @param name name of the module
     */
    public boolean isModuleEnabled(String name) {
        Module mod = modules.stream().filter(mm -> mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return mod.isEnabled();
    }

    /**
     * Gets the modules by category.
     *
     * @param category category of the module
     */
    public List<Module> getModulesByCategory(Module.Category category) {
        List<Module> cats = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory() == category) cats.add(m);
        }
        return cats;
    }

    @SaturnSubscribe
    public void onKeyPress(KeyEvent event) {
        if (InputUtil.isKeyPressed(Saturn.mc.getWindow().getHandle(), GLFW.GLFW_KEY_F3)) return;
        modules.stream().filter(m -> m.getKey() == event.getKey()).forEach(Module::toggle);
    }
}
