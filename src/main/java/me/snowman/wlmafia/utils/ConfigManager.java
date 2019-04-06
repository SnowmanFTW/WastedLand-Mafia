package me.snowman.wlmafia.utils;

import me.snowman.wlmafia.WLMafia;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static ConfigManager cm;
    private WLMafia plugin = WLMafia.getPlugin(WLMafia.class);
    private File mafiasfile, messagesfile;
    private FileConfiguration mafiascfg, messagescfg;

    public static ConfigManager getConfigManager() {
        if (cm == null)
            cm = new ConfigManager();
        return cm;
    }

    public void setupMafias() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        mafiasfile = new File(plugin.getDataFolder(), "mafias.yml");
        if (!mafiasfile.exists()) {
            try {
                mafiasfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mafiascfg = YamlConfiguration.loadConfiguration(mafiasfile);
    }

    public FileConfiguration getMafias() {
        if (mafiasfile == null) {
            mafiasfile = new File(plugin.getDataFolder(), "mafias.yml");
            mafiascfg = YamlConfiguration.loadConfiguration(mafiasfile);
        }
        return mafiascfg;
    }

    public void saveMafias() {
        try {
            mafiascfg.save(mafiasfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupMessages() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        messagesfile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesfile.exists()) {
            plugin.saveResource("messages.yml", true);
        }
        messagescfg = YamlConfiguration.loadConfiguration(messagesfile);
    }

    public FileConfiguration getMessages() {
        if (messagesfile == null) {
            messagesfile = new File(plugin.getDataFolder(), "messages.yml");
            messagescfg = YamlConfiguration.loadConfiguration(messagesfile);
        }
        return messagescfg;
    }

    public void setupConfig() {
        plugin.saveDefaultConfig();
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }
}
