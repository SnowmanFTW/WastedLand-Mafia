package me.snowman.wlmafia;

import me.snowman.wlmafia.commands.MafiaCommand;
import me.snowman.wlmafia.events.ChatPlaceholders;
import me.snowman.wlmafia.utils.ConfigManager;
import me.snowman.wlmafia.utils.EconomyUtils;
import me.snowman.wlmafia.utils.SaveUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class WLMafia extends JavaPlugin {

    public void onEnable() {
        ConfigManager.getConfigManager().setupMessages();
        ConfigManager.getConfigManager().setupConfig();
        EconomyUtils.getEconomy().setupEconomy();
        getCommand("clan").setExecutor(new MafiaCommand());
        getServer().getPluginManager().registerEvents(new ChatPlaceholders(), this);
        SaveUtils.getUtils().loadMafias();
    }

    public void onDisable() {
        SaveUtils.getUtils().shutDownSave();
    }
}
