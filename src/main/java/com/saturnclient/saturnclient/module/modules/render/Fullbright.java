

package com.saturnclient.saturnclient.module.modules.render;



import com.saturnclient.saturnclient.interfaces.ISimpleOption;
import com.saturnclient.saturnclient.module.Module;
import org.lwjgl.glfw.GLFW;

public class Fullbright extends Module {

    float before;


    public Fullbright() {
        super("Fullbright", "Lights up your world.", GLFW.GLFW_KEY_UNKNOWN, Category.RENDER);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ((ISimpleOption<Double>)(Object)mc.options.getGamma()).setValueUnrestricted(1.0);
    }




    @Override
    public void onEnable() {
        super.onEnable();
        ((ISimpleOption<Double>)(Object)mc.options.getGamma()).setValueUnrestricted(100.0);
    }

}
