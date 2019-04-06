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
        Player player = event.getPlayer();
        String format = event.getFormat();
        format = format.replace("{MAFIA}", MafiaManager.getManager().getMafia(player).getName());
        event.setFormat(format);
    }
}
