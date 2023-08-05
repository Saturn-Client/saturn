

package com.saturnclient.saturnclient.mixin;

import com.saturnclient.saturnclient.event.events.RenderInGameHudEvent;
import com.saturnclient.saturnclient.Saturn;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    private void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        RenderInGameHudEvent event = new RenderInGameHudEvent(context);
        Saturn.getInstance().getEventBus().post(event);

        if (event.isCancelled()) ci.cancel();
    }
}
