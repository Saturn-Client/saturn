/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.saturnclient.saturnclient.mixin;

import com.saturnclient.saturnclient.event.events.PacketEvent;
import com.saturnclient.saturnclient.Saturn;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(method = "channelRead0*", at = @At("HEAD"), cancellable = true)
    private void packetReceive(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        PacketEvent event = new PacketEvent(packet, PacketEvent.Type.RECEIVE);
        Saturn.getInstance().getEventBus().post(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void packetSend(Packet<?> packet, CallbackInfo ci) {
        /* This is for the client commands */
        if (packet instanceof ChatMessageC2SPacket pack) {
            if (pack.chatMessage().startsWith(Saturn.getInstance().getCommandManager().prefix)) {
                Saturn.getInstance().getCommandManager().execute(pack.chatMessage());
                ci.cancel();
            }
        }

        PacketEvent event = new PacketEvent(packet, PacketEvent.Type.SEND);
        Saturn.getInstance().getEventBus().post(event);
        if (event.isCancelled()) ci.cancel();
    }

}
