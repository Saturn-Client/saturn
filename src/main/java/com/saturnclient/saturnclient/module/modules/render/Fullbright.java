package com.saturnclient.saturnclient.module.modules.render;

import com.saturnclient.saturnclient.interfaces.ISimpleOption;
import com.saturnclient.saturnclient.module.Module;
import org.lwjgl.glfw.GLFW;

public class Fullbright extends Module {
    private double previousValue = 0.0;

    public Fullbright() {
        super("Fullbright", "Lights up your world", GLFW.GLFW_KEY_UNKNOWN, Category.RENDER);
    }

    @Override
    public void onDisable() {
        @SuppressWarnings("unchecked")
        ISimpleOption < Double > gamma =
                (ISimpleOption < Double > )(Object) mc.options.getGamma();
        gamma.forceSetValue(previousValue);
    }

    @Override
    public void onEnable() {
        this.previousValue = mc.options.getGamma().getValue();
        @SuppressWarnings("unchecked")
        ISimpleOption < Double > gamma =
                (ISimpleOption < Double > )(Object) mc.options.getGamma();
        gamma.forceSetValue(10000.0);
    }

}