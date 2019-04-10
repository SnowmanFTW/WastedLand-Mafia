package me.snowman.wlmafia.events;

import me.snowman.wlmafia.mafiautils.MafiaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatPlaceholders implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        String prefix;
        Player player = event.getPlayer();
        if (MafiaManager.getManager().getMafia(player) == null) {
            prefix = "";
        } else {
            prefix = MafiaManager.getManager().getMafia(player).getName();
        }
        String format = event.getFormat();
        format = format.replace("{MAFIA}", prefix);
        event.setFormat(format);
    }
}
