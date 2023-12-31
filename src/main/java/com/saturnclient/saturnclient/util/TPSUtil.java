package com.saturnclient.saturnclient.util;

import com.saturnclient.saturnclient.event.events.PacketEvent;
import com.saturnclient.saturnclient.eventbus.SaturnSubscribe;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.math.MathHelper;

public class TPSUtil {
    public static TPSUtil INSTANCE = new TPSUtil();
    private static double ticks = 0;
    private static long prevTime = 0;

    @SaturnSubscribe
    public void onPacket(PacketEvent event) {
        if (event.getType() == PacketEvent.Type.RECEIVE) {
            if (event.getPacket() instanceof WorldTimeUpdateS2CPacket) {
                long time = System.currentTimeMillis();
                long timeOffset = Math.abs(1000 - (time - prevTime)) + 1000;
                ticks = (MathHelper.clamp(20 / (timeOffset / 1000d), 0, 20) * 100d) / 100d;
                prevTime = time;
            }
        }
    }

    /**
     * Returns the ticks per-second.
     *
     * @return ticks
     */
    public double getTPS() {
        return ticks;
    }
}