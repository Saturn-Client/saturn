

package com.saturnclient.saturnclient.module.modules.misc;

import com.saturnclient.saturnclient.Saturn;
import com.saturnclient.saturnclient.util.SaturnLogger;
import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;
import com.saturnclient.saturnclient.module.Module;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class DiscordRPC extends Module {

    private static final RichPresence rpc = new RichPresence();

    public DiscordRPC() {
        super("Discord RPC", "Discord presence.", GLFW.GLFW_KEY_UNKNOWN, Category.RENDER);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        DiscordIPC.stop();

    }


    @Override
    public void onEnable() {
        super.onEnable();

        if(!DiscordIPC.start(1137364667925286953L, null)) {
            SaturnLogger.warn("Discord RPC didn't work...");
        }
        rpc.setStart(System.currentTimeMillis() / 1000L);
        String largeText = Saturn.MOD_NAME + " " + Saturn.MOD_VERSION;
        rpc.setLargeImage("big", largeText);



    }

}
