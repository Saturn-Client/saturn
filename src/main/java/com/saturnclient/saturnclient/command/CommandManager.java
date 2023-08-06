package com.saturnclient.saturnclient.command;

import com.saturnclient.saturnclient.command.commands.FakePlayerCmd;
import com.saturnclient.saturnclient.command.commands.HelpCmd;
import com.saturnclient.saturnclient.util.SaturnLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    public String prefix = "-";
    public List < Command > commands;
    boolean commandFound;

    public CommandManager() {
        commands = new ArrayList < > ();

        /* Add commands here */
        commands.add(new HelpCmd());
        commands.add(new FakePlayerCmd());
    }

    /**
     * Executes a command
     *
     * @param command command to execute
     */
    public void execute(String command) {
        String message = command;

        if (!message.startsWith(prefix)) return;

        message = message.substring(prefix.length());
        if (message.split(" ").length > 0) {
            commandFound = false;
            String commandName = message.split(" ")[0];
            for (Command c: commands) {
                if (c.aliases.contains(commandName) || c.name.equalsIgnoreCase(commandName)) {
                    c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                    commandFound = true;
                    break;
                }
            }
            if (!commandFound) SaturnLogger.error("Command not found, use " + prefix + "help");
        }
    }

    /**
     * Gets a command by name.
     *
     * @param name name of command
     */
    public Command getCommand(String name) {
        for (Command command: commands) {
            if (command.name.equalsIgnoreCase(name)) return command;
        }
        return null;
    }
}