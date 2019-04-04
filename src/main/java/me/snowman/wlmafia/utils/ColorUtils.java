package me.snowman.wlmafia.utils;

import org.bukkit.ChatColor;

public class ColorUtils {

    private static ColorUtils cu;
    public String prefix = color(getMessage("Prefix"));

    public static ColorUtils getColorUtils() {
        if (cu == null)
            cu = new ColorUtils();
        return cu;
    }

    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String getMessage(String s) {
        return ConfigManager.getConfigManager().getMessages().getString(s);
    }
}
