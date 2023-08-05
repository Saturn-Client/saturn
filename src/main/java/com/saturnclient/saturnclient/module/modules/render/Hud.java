

package com.saturnclient.saturnclient.module.modules.render;

import com.saturnclient.saturnclient.Saturn;
import com.saturnclient.saturnclient.event.events.RenderInGameHudEvent;
import com.saturnclient.saturnclient.eventbus.SaturnSubscribe;
import com.saturnclient.saturnclient.setting.settings.BooleanSetting;
import com.saturnclient.saturnclient.util.TPSUtil;
import com.saturnclient.saturnclient.module.Module;
import org.lwjgl.glfw.GLFW;

public class Hud extends Module {
    public final BooleanSetting watermark = new BooleanSetting("Watermark", "Renders the Saturn watermark.", true);
    //public final StringSetting watermarkText = new StringSetting("Watermark Text", "The text of the watermark.", "Saturn");
    public final BooleanSetting arraylist = new BooleanSetting("Arraylist", "Renders the Saturn arraylist.", true);
    public final BooleanSetting ticks = new BooleanSetting("TPS", "Renders the ticks per second.", true);

    public Hud() {
        super("Hud", "Renders the Saturn hud.", GLFW.GLFW_KEY_UNKNOWN, Category.RENDER);
        this.addSettings(watermark, arraylist, ticks);
    }

    @SaturnSubscribe
    public void onRender(RenderInGameHudEvent event) {
        if (mc.world == null || mc.player == null) return;

        if (watermark.isEnabled()) {
            event.getContext().drawTextWithShadow(mc.textRenderer, Saturn.MOD_NAME + " v" + Saturn.MOD_VERSION,
                    2, 2, 0xFFFFFF);
        }

        if (ticks.isEnabled()) {
            event.getContext().drawTextWithShadow(mc.textRenderer, "Ticks per sec: " +
                    TPSUtil.INSTANCE.getTPS(), watermark.isEnabled() ? 70 : 2, 2, 0xFFFFFF);
        }

        int y = 5;

        if (arraylist.isEnabled()) {
            for (Module module : Saturn.getInstance().getModuleManager().getEnabledModules()) {
                event.getContext().drawTextWithShadow(mc.textRenderer, ">" + module.name, 2, y += 10, 0xFFFFFF);
            }
        }
    }
}
