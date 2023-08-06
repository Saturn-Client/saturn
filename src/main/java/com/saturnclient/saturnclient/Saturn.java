package com.saturnclient.saturnclient;

import com.saturnclient.saturnclient.util.SaturnLogger;
import com.saturnclient.saturnclient.util.TPSUtil;
import com.saturnclient.saturnclient.command.CommandManager;
import com.saturnclient.saturnclient.config.ConfigManager;
import com.saturnclient.saturnclient.eventbus.EventBus;
import com.saturnclient.saturnclient.module.ModuleManager;
import com.saturnclient.saturnclient.setting.SettingManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;

public class Saturn implements ModInitializer {
    public static final String MOD_NAME = "Saturn";
    public static final String MOD_VERSION = "0.1-dev";
    public static final MinecraftClient mc = MinecraftClient.getInstance();
    private static Saturn INSTANCE;
    private final EventBus EVENT_BUS = new EventBus();
    private final ModuleManager MODULE_MANAGER = new ModuleManager();
    private final CommandManager COMMAND_MANAGER = new CommandManager();
    private final SettingManager SETTING_MANAGER = new SettingManager();
    private final ConfigManager CONFIG_MANAGER = new ConfigManager();

    public Saturn() {
        INSTANCE = this;
    }

    /**
     * Gets the instance of Saturn.
     */
    public static Saturn getInstance() {
        return INSTANCE;
    }

    @Override
    public void onInitialize() {
        SaturnLogger.logger.info(MOD_NAME + " v" + MOD_VERSION + " (phase 1) has initialized!");
        CONFIG_MANAGER.load();
        SaturnLogger.logger.info("Loaded config!");

        // Save configs on shutdown
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            CONFIG_MANAGER.save();
            SaturnLogger.logger.info("Saved config!");
        });
    }

    /**
     * Called when Minecraft has finished loading.
     */
    public void postInitialize() {
        EVENT_BUS.register(TPSUtil.INSTANCE);
        SaturnLogger.logger.info("Registered TickRateUtil!");
        SaturnLogger.logger.info(MOD_NAME + " v" + MOD_VERSION + " (phase 2) has initialized!");
    }

    /**
     * Gets the event bus.
     */
    public EventBus getEventBus() {
        return EVENT_BUS;
    }

    /**
     * Gets the module manager.
     */
    public ModuleManager getModuleManager() {
        return MODULE_MANAGER;
    }

    /**
     * Gets the command manager.
     */
    public CommandManager getCommandManager() {
        return COMMAND_MANAGER;
    }

    /**
     * Gets the setting manager.
     */
    public SettingManager getSettingManager() {
        return SETTING_MANAGER;
    }

    /**
     * Gets the config manager.
     */
    public ConfigManager getConfigManager() {
        return CONFIG_MANAGER;
    }
}