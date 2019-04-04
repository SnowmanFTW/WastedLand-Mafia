package me.snowman.wlmafia;

import me.snowman.wlmafia.commands.MafiaCommand;
import me.snowman.wlmafia.utils.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WLMafia extends JavaPlugin {

    public void onEnable() {
        ConfigManager.getConfigManager().setupMessages();
        getCommand("mafia").setExecutor(new MafiaCommand());
    }

    public void onDisable() {

    }
}
