package me.snowman.wlmafia.commands;

import me.snowman.wlmafia.mafiautils.Mafia;
import me.snowman.wlmafia.mafiautils.MafiaManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MafiaCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        switch (args[0]) {
            case "create":
                if (!(sender instanceof Player)) {
                    break;
                }
                MafiaManager.getManager().createMafia(args[1], (Player) sender);
                break;
            case "list":
                for (Mafia m : MafiaManager.getManager().getMafias()) {
                    sender.sendMessage(m.getName());
                }
                break;
            case "delete":
                if (!(sender instanceof Player)) {
                    break;
                }
                MafiaManager.getManager().deleteMafia((Player) sender, args[1]);
                break;
            case "invite":
                if (!(sender instanceof Player)) {
                    break;
                }
                MafiaManager.getManager().addWaiting(Bukkit.getPlayer(args[1]), (Player) sender);
                break;
            case "accept":
                if (!(sender instanceof Player)) {
                    break;
                }
                MafiaManager.getManager().acceptWaiting((Player) sender, Bukkit.getPlayer(args[1]));
                break;
        }
        return true;
    }
}
