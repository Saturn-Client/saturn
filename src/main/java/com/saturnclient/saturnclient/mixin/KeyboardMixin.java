/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.saturnclient.saturnclient.mixin;

import com.saturnclient.saturnclient.event.events.KeyEvent;
import com.saturnclient.saturnclient.Saturn;
import com.saturnclient.saturnclient.module.Module;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At(value = "INVOKE", target = "net/minecraft/client/util/InputUtil.isKeyPressed(JI)Z", ordinal = 5), cancellable = true)
    private void onKey(long window, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (Saturn.mc.currentScreen != null) return;
        if (action == 2) action = 1;
        switch (action) {
            case 0 -> {
                KeyEvent event = new KeyEvent(key, scanCode, KeyEvent.Status.RELEASED);
                Saturn.getInstance().getEventBus().post(event);
                if (event.isCancelled()) ci.cancel();
            }
            case 1 -> {
                KeyEvent event = new KeyEvent(key, scanCode, KeyEvent.Status.PRESSED);
                Saturn.getInstance().getModuleManager().getModules().stream().filter(m -> m.getKey() == key).forEach(Module::toggle);
                Saturn.getInstance().getEventBus().post(event);
                if (event.isCancelled()) ci.cancel();
            }
        }
    }
}
