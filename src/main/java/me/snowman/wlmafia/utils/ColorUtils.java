package me.snowman.wlmafia.utils;

import org.bukkit.ChatColor;

public class ColorUtils {

    private static ColorUtils cu;
    public String prefix = color("[prefix] ");

    public static ColorUtils getColorUtils() {
        if (cu == null)
            cu = new ColorUtils();
        return cu;
    }

    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
