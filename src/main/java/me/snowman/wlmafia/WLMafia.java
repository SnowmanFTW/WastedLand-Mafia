package me.snowman.wlmafia;

import me.snowman.wlmafia.commands.MafiaCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class WLMafia extends JavaPlugin {

    public void onEnable() {
        getCommand("mafia").setExecutor(new MafiaCommand());
    }

    public void onDisable() {

    }
}
