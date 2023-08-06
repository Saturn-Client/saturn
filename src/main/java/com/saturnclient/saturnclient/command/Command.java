package com.saturnclient.saturnclient.command;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Arrays;
import java.util.List;

public abstract class Command {
    public static MinecraftClient mc = MinecraftClient.getInstance();
    public String name, description, syntax;
    public List < String > aliases;

    public Command(String name, String description, String syntax, String...aliases) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = Arrays.asList(aliases);
    }

    /**
     * Called when the command is executed
     *
     * @param args    arguments passed
     * @param command command name
     */
    public void onCommand(String[] args, String command) {}

    /**
     * Gets the command name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the command name
     *
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the command description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the command description
     *
     * @param description description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the command syntax
     */
    public String getSyntax() {
        return syntax;
    }

    /**
     * Sets the command syntax
     *
     * @param syntax syntax to set
     */
    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    /**
     * Gets the command aliases
     */
    public List < String > getAliases() {
        return aliases;
    }

    /**
     * Sets the command aliases
     *
     * @param aliases aliases to set
     */
    public void setAliases(List < String > aliases) {
        this.aliases = aliases;
    }

}