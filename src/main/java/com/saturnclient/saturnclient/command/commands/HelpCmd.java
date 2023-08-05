/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.saturnclient.saturnclient.command.commands;

import com.saturnclient.saturnclient.Saturn;
import com.saturnclient.saturnclient.command.Command;
import com.saturnclient.saturnclient.util.SaturnLogger;

import java.util.stream.Collectors;

public class HelpCmd extends Command {

    public HelpCmd() {
        super("Help", "Shows a list of commands", "help", "h");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 0) {
            SaturnLogger.info("Commands: " + Saturn.getInstance().getCommandManager().commands.stream()
                    .map(Command::getName).collect(Collectors.joining(", ")));
        } else {
            for (Command cmd : Saturn.getInstance().getCommandManager().commands) {
                if (cmd.getName().equalsIgnoreCase(args[0])) {
                    SaturnLogger.info(cmd.getSyntax());
                    return;
                }
            }
            SaturnLogger.error("Command not found.");
        }
    }
}
