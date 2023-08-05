

package com.saturnclient.saturnclient.module.modules.movement;

import com.saturnclient.saturnclient.event.events.TickEvent;
import com.saturnclient.saturnclient.eventbus.SaturnSubscribe;
import com.saturnclient.saturnclient.setting.settings.NumberSetting;
import com.saturnclient.saturnclient.module.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

public class Fly extends Module {
    public final NumberSetting speed = new NumberSetting("Speed", "How fast to fly.", 3, 0.1, 10, 0.1);
    private int antiKickTimer = 0;

    public Fly() {
        super("Fly", "Allows you to fly.", GLFW.GLFW_KEY_F, Category.MOVEMENT);
        this.addSettings(speed);
    }

    @Override
    public void onDisable() {
        if (mc.world == null || mc.player == null) return;
        mc.player.getAbilities().flying = false;
        mc.player.getAbilities().allowFlying = false;
        mc.player.getAbilities().setFlySpeed(0.05f);

        super.onDisable();
    }

    @SaturnSubscribe
    public void onTick(TickEvent event) {
        if (mc.world == null || mc.player == null) return;
        float flySpeed = (int) speed.getValue();
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().allowFlying = true;
        mc.player.getAbilities().setFlySpeed(flySpeed / 10f);

        antiKickTimer++;
        if (antiKickTimer > 20 && mc.player.getWorld().getBlockState(BlockPos.ofFloored(mc.player.getPos().subtract(0, 0.0433D, 0))).isAir()) {
            antiKickTimer = 0;
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + 0.0433D, mc.player.getZ(), false));
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() - 0.0433D, mc.player.getZ(), true));
        }
    }
}
