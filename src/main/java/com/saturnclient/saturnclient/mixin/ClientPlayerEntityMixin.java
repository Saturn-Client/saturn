/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

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
