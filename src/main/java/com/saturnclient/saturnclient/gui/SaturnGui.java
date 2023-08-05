

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

    public static final float[] fontColor = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    public static final float[] bgColor = new float[]{0.0f, 0.0f, 0.0f, 0.66f};
    public static final float[] frameColor = new float[]{0.2f, 0.2f, 0.2f, 0.8f};
    public static final float[] titleColor = new float[]{0.0f, 0.0f, 0.0f, 1.0f};

    public static final float[] sliderColor = new float[]{1.0f, 1.0f, 1.0f, 1.0f};

    public static final float[] checkboxColor = new float[]{1.0f, 1.0f, 1.0f, 1.0f};

    public static final float[] buttonColor = new float[]{1.0f, 1.0f, 1.0f, 0.7f};
    public int toARGB(float[] color) {
        int a = 255; // default to full opacity
        int r = (int) (color[0] * 255) & 0xFF;
        int g = (int) (color[1] * 255) & 0xFF;
        int b = (int) (color[2] * 255) & 0xFF;
        return a << 24 | b << 16 | g << 8 | r;
    }

    public int toARGB2(float[] color) {
        int a = (int) (color[3] * 255) & 0xFF; // default to full opacity
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

        ImFontAtlas fontAtlas = io.getFonts();
        ImFont font;

        try {
            Path tempFile = Files.createTempFile("font", ".ttf");
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf")) {
                Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            font = fontAtlas.addFontFromFileTTF(tempFile.toString(), 16);

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
        if (ImGui.begin(Saturn.MOD_NAME + " " + Saturn.MOD_VERSION + " | Info tab", ImGuiWindowFlags.NoResize)) {
            ImGui.setWindowSize(400, 500);
            ImGui.text("Welcome to Saturn!");
            ImGui.separator();
            ImGui.text("Saturn v" + Saturn.MOD_VERSION);
            ImGui.text("Minecraft " + SharedConstants.getGameVersion().getName());
            ImGui.text("Cmd prefix: " + Saturn.getInstance().getCommandManager().prefix);

            ImGui.separator();
            ImGui.text("Sizes:");
            ImGui.sliderFloat("Height", guiHeight.getData(), 0.5f, 3.0f);
            ImGui.sliderFloat("Width", guiWidth.getData(), 0.5f, 2.0f);
            ImGui.sliderFloat("Font", fontSize.getData(), 0.5f, 2.0f);
            ImGui.separator();
            ImGui.text("Colors:");
            ImGui.colorEdit3("Font", fontColor);
            ImGui.colorEdit4("BG", bgColor);
            ImGui.colorEdit4("Frame", frameColor);
            ImGui.colorEdit4("Title", titleColor);
            ImGui.colorEdit4("Slider", sliderColor);
            ImGui.colorEdit4("Checkbox", checkboxColor);
            ImGui.colorEdit4("Button", buttonColor);

            ImGuiStyle style = ImGui.getStyle();
            int fontColorInt = toARGB(fontColor);
            style.setColor(ImGuiCol.Text, fontColorInt);

            int bgColorInt = toARGB2(bgColor);
            style.setColor(ImGuiCol.WindowBg, bgColorInt);

            int checkboxColorInt = toARGB2(checkboxColor);
            style.setColor(ImGuiCol.CheckMark, checkboxColorInt);

            int frameColorInt = toARGB2(frameColor);
            style.setColor(ImGuiCol.FrameBg, frameColorInt);
            style.setColor(ImGuiCol.FrameBgActive, frameColorInt);
            style.setColor(ImGuiCol.FrameBgHovered, frameColorInt);

            int titleColorInt = toARGB2(titleColor);
            style.setColor(ImGuiCol.TitleBgActive, titleColorInt);
            style.setColor(ImGuiCol.TitleBg, titleColorInt);
            style.setColor(ImGuiCol.TitleBgCollapsed, titleColorInt);

            int sliderColorInt = toARGB2(sliderColor);
            style.setColor(ImGuiCol.SliderGrab, sliderColorInt);
            style.setColor(ImGuiCol.SliderGrabActive, sliderColorInt);

            int buttonColorInt = toARGB2(buttonColor);
            int buttonColorInt2 = toARGB2(buttonColor);
            style.setColor(ImGuiCol.Button, buttonColorInt);
            style.setColor(ImGuiCol.ButtonActive, buttonColorInt2);
            style.setColor(ImGuiCol.ButtonHovered, buttonColorInt2);


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

