

package com.saturnclient.saturnclient.gui;

import com.saturnclient.saturnclient.Saturn;
import com.saturnclient.saturnclient.gui.tabs.ModuleTabs;
import com.saturnclient.saturnclient.setting.settings.BooleanSetting;
import imgui.*;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Saturn's click gui.
 * Uses ImGui, because its based.
 */
public class SaturnGui extends Screen {
    public static final ImFloat guiHeight = new ImFloat(1.0f);
    public static final ImFloat guiWidth = new ImFloat(1.0f);
    public static final ImFloat fontSize = new ImFloat(1.0f);

    public static final float[] fontColor = new float[]{1.0f, 1.0f, 1.0f, 1.0f}; // default to white with full opacity

    public int toARGB(float[] color) {
        int a = 255; // default to full opacity
        int r = (int) (color[0] * 255) & 0xFF;
        int g = (int) (color[1] * 255) & 0xFF;
        int b = (int) (color[2] * 255) & 0xFF;
        return a << 24 | b << 16 | g << 8 | r;
    }






    public static final ImBoolean CustomFont = new ImBoolean(true);
    private final ImGuiImplGlfw implGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 implGl3 = new ImGuiImplGl3();
    MinecraftClient mc = MinecraftClient.getInstance();

    public SaturnGui() {
        super(Text.literal("Saturn"));
        long windowHandle = mc.getWindow().getHandle();
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();

        // Load the font
        ImFontAtlas fontAtlas = io.getFonts();
        ImFont font;

        // Extract the font file from the jar to a temporary file
        try {
            Path tempFile = Files.createTempFile("font", ".ttf");
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("SourceCodePro-Regular.ttf")) {
                Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            // Load the font from the temporary file
            font = fontAtlas.addFontFromFileTTF(tempFile.toString(), 16);

            // Delete the temporary file when the JVM exits
            tempFile.toFile().deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load font", e);
        }

        // Set the default font
        io.setFontDefault(font);

        implGlfw.init(windowHandle, true);
        implGl3.init();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        implGlfw.newFrame();
        ImGui.newFrame();

        ImGuiIO io = ImGui.getIO();
        io.setFontGlobalScale(fontSize.get());
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);

        ImGui.getIO().setConfigWindowsMoveFromTitleBarOnly(true);
        ImGui.getStyle().setColor(ImGuiCol.FrameBgActive, 50, 50, 50, 200);
        ImGui.getStyle().setColor(ImGuiCol.TitleBgActive, 0, 0, 0, 255);
        ImGui.getStyle().setColor(ImGuiCol.FrameBg, 50, 50, 50, 200);
        ImGui.getStyle().setColor(ImGuiCol.WindowBg, 0, 0, 0, 170);

        if (ImGui.begin(Saturn.MOD_NAME + " " + Saturn.MOD_VERSION + " | Info tab", ImGuiWindowFlags.NoResize)) {
            ImGui.setWindowSize(400, 500);
            ImGui.text("Welcome to Saturn!");
            ImGui.separator();
            ImGui.text("Saturn v" + Saturn.MOD_VERSION);
            ImGui.text("Minecraft " + SharedConstants.getGameVersion().getName());
            ImGui.text("Cmd prefix: " + Saturn.getInstance().getCommandManager().prefix);

            ImGui.sliderFloat("Gui Height", guiHeight.getData(), 0.5f, 3.0f);
            ImGui.sliderFloat("Gui Width", guiWidth.getData(), 0.5f, 2.0f);
            ImGui.sliderFloat("Font Size", fontSize.getData(), 0.5f, 2.0f);
            ImGui.checkbox("Custom Font", CustomFont);
            ImGui.colorEdit3("Font Color", fontColor);

            ImGuiStyle style = ImGui.getStyle();
            int fontColorInt = toARGB(fontColor);
            style.setColor(ImGuiCol.Text, fontColorInt);


            ImGui.setWindowSize(400 * guiWidth.get(), 500 * guiHeight.get());

            ImGui.separator();
            ImGui.text("saturnclient.com");

            ModuleTabs.render();
        }

        ImGui.end();

        ImGui.render();
        implGl3.renderDrawData(ImGui.getDrawData());
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        Saturn.getInstance().getModuleManager().getModule("Gui").setEnabled(false);
        return true;
    }

    @Override
    public void close() {
        mc.setScreen(null);
        Saturn.getInstance().getModuleManager().getModule("Gui").setEnabled(false);
        super.close();
    }
}

