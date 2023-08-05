/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.saturnclient.saturnclient.gui;

import com.saturnclient.saturnclient.Saturn;
import com.saturnclient.saturnclient.setting.settings.*;
import com.saturnclient.saturnclient.util.SaturnLogger;
import com.saturnclient.saturnclient.module.Module;
import com.saturnclient.saturnclient.setting.Setting;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImString;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Saturn's other click gui.
 * One window, no tabs.
 */
public class SaturnOneGui extends Screen {
    private static final HashMap<Setting, Object> settingsMap = new HashMap<>();
    private static boolean binding;
    private final ImGuiImplGlfw implGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 implGl3 = new ImGuiImplGl3();
    private final HashMap<Module, ImBoolean> enabledMap = new HashMap<>();
    private Module activeModule;
    MinecraftClient mc = MinecraftClient.getInstance();

    public SaturnOneGui() {
        super(Text.literal("Saturn"));
        long windowHandle = mc.getWindow().getHandle();
        ImGui.createContext();
        implGlfw.init(windowHandle, true);
        implGl3.init();
    }

    /**
     * Should the game be paused when the gui is open?
     */
    @Override
    public boolean shouldPause() {
        return false;
    }


    /**
     * Renders the GUI.
     *
     * @param context The context to render in
     * @param mouseX  The mouse x position
     * @param mouseY  The mouse y position
     * @param delta   The delta time
     */
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Setup
        implGlfw.newFrame();
        ImGui.newFrame();

        // Settings
        // Add input typing
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);

        // Styling
        ImGui.getIO().setConfigWindowsMoveFromTitleBarOnly(true);
        ImGui.getStyle().setColor(ImGuiCol.TitleBgActive, 255, 255, 255, 125);

        // Window
        ImGui.begin(Saturn.MOD_NAME + " " + Saturn.MOD_VERSION, ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove);
        ImGui.setWindowSize(800, 600);
        ImGui.text("Welcome to Saturn!");
        ImGui.separator();
        ImGui.text("Saturn v" + Saturn.MOD_VERSION);
        ImGui.text("Minecraft " + SharedConstants.getGameVersion().getName());
        ImGui.text("Cmd prefix: " + Saturn.getInstance().getCommandManager().prefix);
        ImGui.separator();
        ImGui.text("vili.dev");

        // Categories
        for (Module.Category category : Module.Category.values()) {
            ImGui.separator();
            if (ImGui.collapsingHeader(category.name(), ImGuiTreeNodeFlags.DefaultOpen)) {
                ImGui.indent();
                renderCategoryModules(category);
                ImGui.unindent();
            }
        }

        ImGui.end();

        // Render
        ImGui.render();
        implGl3.renderDrawData(ImGui.getDrawData());
        super.render(context, mouseX, mouseY, delta);
    }

    /**
     * Renders the modules for a given category.
     *
     * @param category The category to render modules for
     */
    private void renderCategoryModules(Module.Category category) {
        for (Module module : Saturn.getInstance().getModuleManager().getModulesByCategory(category)) {
            ImGui.separator();
            if (ImGui.collapsingHeader(module.getName(), ImGuiTreeNodeFlags.DefaultOpen)) {
                ImGui.indent();
                renderModule(module);
                ImGui.unindent();
            }
        }
    }

    /**
     * Renders a module and its settings.
     *
     * @param module The module to render
     */
    private void renderModule(Module module) {
        ImBoolean enabled = enabledMap.getOrDefault(module, new ImBoolean(module.isEnabled()));
        enabledMap.put(module, enabled);

        ImGui.checkbox("Enabled", enabled);
        module.setEnabled(enabled.get());

        for (Setting setting : module.settings) {
            switch (setting.getClass().getSimpleName()) {
                case "BooleanSetting" -> {
                    BooleanSetting booleanSetting = (BooleanSetting) setting;
                    ImBoolean booleanValue = (ImBoolean) settingsMap.getOrDefault(setting, new ImBoolean(booleanSetting.isEnabled()));
                    settingsMap.put(setting, booleanValue);
                    ImGui.checkbox(setting.getName(), booleanValue);
                    if (booleanSetting.isEnabled() != booleanValue.get()) {
                        booleanSetting.setEnabled(booleanValue.get());
                    }
                }
                case "NumberSetting" -> {
                    NumberSetting numberSetting = (NumberSetting) setting;
                    float[] floatValue = (float[]) settingsMap.getOrDefault(setting, new float[]{(float) numberSetting.getValue()});
                    settingsMap.put(setting, floatValue);
                    ImGui.sliderFloat(setting.getName(), floatValue, (float) numberSetting.getMinimum(),
                            (float) numberSetting.getMaximum());
                    if (floatValue[0] != (float) numberSetting.getValue()) {
                        numberSetting.setValue(floatValue[0]);
                    }
                }
                case "ModeSetting" -> {
                    ModeSetting modeSetting = (ModeSetting) setting;
                    ImInt modeIndex = (ImInt) settingsMap.getOrDefault(setting, new ImInt(modeSetting.modes.indexOf(modeSetting.getMode())));
                    settingsMap.put(setting, modeIndex);
                    String[] modeNames = modeSetting.modes.toArray(new String[0]);
                    ImGui.combo(setting.getName(), modeIndex, modeNames);
                    if (modeIndex.get() != modeSetting.modes.indexOf(modeSetting.getMode())) {
                        modeSetting.setMode(modeSetting.modes.get(modeIndex.get()));
                    }
                }
                case "KeybindSetting" -> {
                    KeybindSetting keybindSetting = (KeybindSetting) setting;
                    if (binding && activeModule == module) { // Check if the module is active for keybind changes
                        ImGui.text("Press a key to bind");
                        for (int i = 0; i < 512; i++) {
                            if (ImGui.isKeyPressed(i)) {
                                if (i == GLFW.GLFW_KEY_ESCAPE || i == GLFW.GLFW_KEY_BACKSPACE || i == GLFW.GLFW_KEY_DELETE) {
                                    keybindSetting.setKeyCode(-1);
                                } else {
                                    keybindSetting.setKeyCode(i);
                                }
                                binding = false;
                            }
                        }
                    } else {
                        String name = keybindSetting.getKeyCode() < 0 ? "NONE"
                                : InputUtil.fromKeyCode(keybindSetting.getKeyCode(), -1).getLocalizedText().getString();
                        if (ImGui.button("Bind: " + name)) {
                            activeModule = module; // Set the active module for keybind changes
                            binding = true;
                        }
                    }
                }
                case "StringSetting" -> {
                    StringSetting stringSetting = (StringSetting) setting;
                    ImString stringValue = (ImString) settingsMap.getOrDefault(setting, new ImString(stringSetting.getString()));
                    settingsMap.put(setting, stringValue);
                    byte[] byteValue = stringValue.get().getBytes();
                    ImGui.inputText(setting.getName(), new ImString(Arrays.toString(byteValue)));
                    stringSetting.setString(new String(byteValue));
                }
                default -> SaturnLogger.logger.warn("Unknown setting type: " + setting.getClass().getSimpleName());
            }
            if (ImGui.isItemHovered()) {
                ImGui.setTooltip(setting.getDescription());
            }
        }
    }

    /**
     * Should the gui close when the escape key is pressed?
     */
    @Override
    public boolean shouldCloseOnEsc() {
        Saturn.getInstance().getModuleManager().getModule("OneGui").setEnabled(false);
        return true;
    }

    /**
     * Called when the gui is closed.
     */
    @Override
    public void close() {
        mc.setScreen(null);
        Saturn.getInstance().getModuleManager().getModule("OneGui").setEnabled(false);
        super.close();
    }
}
