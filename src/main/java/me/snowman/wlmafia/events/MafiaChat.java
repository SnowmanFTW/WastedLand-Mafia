package me.snowman.wlmafia.events;

import me.snowman.wlmafia.mafiautils.MafiaManager;
import me.snowman.wlmafia.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class MafiaChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (MafiaManager.getManager().getMafia(player) == null) {
            return;
        }
        if (!MafiaManager.getManager().getMafia(player).getChat().contains(player.getUniqueId())) {
            return;
        }
        event.getRecipients().clear();
        for (UUID uuid : MafiaManager.getManager().getMafia(player).getPlayers()) {
            Player player1 = Bukkit.getPlayer(uuid);
            event.getRecipients().add(player1);
        }
        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            if (players.hasPermission("mafia.chat.see")) {
                event.getRecipients().add(players);
            }
        }
        event.setFormat(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().getMessage("MafiaChatPrefix") + event.getFormat()));
    }
}
