

package com.saturnclient.saturnclient.mixin;

import com.saturnclient.saturnclient.Saturn;
import com.saturnclient.saturnclient.event.events.TickEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (Saturn.mc.player != null && Saturn.mc.world != null) {
            TickEvent event = new TickEvent();
            Saturn.getInstance().getEventBus().post(event);
        }
    }
}
