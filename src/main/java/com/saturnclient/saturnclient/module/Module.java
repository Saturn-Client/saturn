/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.saturnclient.saturnclient.module;

import com.saturnclient.saturnclient.Saturn;
import com.saturnclient.saturnclient.setting.settings.KeybindSetting;
import com.saturnclient.saturnclient.util.SaturnLogger;
import com.saturnclient.saturnclient.setting.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class Module {
    public static final MinecraftClient mc = MinecraftClient.getInstance();
    public String name, description;
    public KeybindSetting keyCode = new KeybindSetting(0);
    public Category category;
    public boolean enabled;
    public List<Setting> settings = new ArrayList<>();

    public Module(String name, String description, int key, Category category) {
        super();
        this.name = name;
        this.description = description;
        keyCode.code = key;
        this.category = category;

        /* Add default settings */
        addSettings(keyCode);
    }


    /**
     * Adds settings to the module.
     * Must be called in the constructor.
     *
     * @param settings settings to add
     */
    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
        this.settings.sort(Comparator.comparingInt(s -> s == keyCode ? 1 : 0));
    }

    /**
     * Called when the module is enabled.
     */
    public void onEnable() {
        Saturn.getInstance().getEventBus().register(this);
        Saturn.getInstance().getConfigManager().save();

        SaturnLogger.info(Formatting.GRAY + "Toggled "+ Formatting.WHITE + this.getName() + Formatting.GREEN + " on");
    }

    /**
     * Called when the module is disabled.
     */
    public void onDisable() {
        Saturn.getInstance().getEventBus().unregister(this);
        Saturn.getInstance().getConfigManager().save();

        SaturnLogger.info(Formatting.GRAY + "Toggled "+ Formatting.WHITE + this.getName() + Formatting.RED+ " off");
    }

    /**
     * Toggles a module.
     */
    public void toggle() {
        this.enabled = !this.enabled;

        if (this.enabled) onEnable();
        else onDisable();
    }

    /**
     * Gets the enabled state of the module.
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Sets the enabled state of the module.
     *
     * @param enabled enabled state to set
     */
    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            toggle();
        }
    }

    /* -------- Getters -------- */

    /**
     * Gets the name of the module.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the description of the module.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the category of the module.
     */
    public Category getCategory() {
        return this.category;
    }

    /**
     * Gets the key of the module.
     */
    public int getKey() {
        return this.keyCode.code;
    }

    /* -------- Setters -------- */

    /**
     * Sets the key of the module.
     *
     * @param key key to set
     */
    public void setKey(int key) {
        this.keyCode.code = key;
    }

    /**
     * Module categories.
     */
    public enum Category {
        COMBAT,
        MOVEMENT,
        RENDER,
        PLAYER,
        MISC
    }
}
