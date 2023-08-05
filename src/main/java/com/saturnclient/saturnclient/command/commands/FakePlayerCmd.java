package com.saturnclient.saturnclient.command.commands;

import com.saturnclient.saturnclient.command.Command;
import com.saturnclient.saturnclient.util.SaturnLogger;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class FakePlayerCmd extends Command {

    public FakePlayerCmd() {
        super("Fake-player", "Creates a fake player", "fake-player", "fp");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 1) {
            SaturnLogger.info("Still being worked on, come back later.");
        } else {
            SaturnLogger.error("Please provide 1 argument.");
        }
    }
}
