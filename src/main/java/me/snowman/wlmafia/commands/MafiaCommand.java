package me.snowman.wlmafia.commands;

import me.snowman.wlmafia.mafiautils.Mafia;
import me.snowman.wlmafia.mafiautils.MafiaManager;
import me.snowman.wlmafia.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MafiaCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ColorUtils.getColorUtils().color("&eWastedLand Mafia Help\n&f- &e/mafia create <nume> &f- &7Creeaza o mafie\n&f- &e/mafia delete &f- &7Sterge mafia ta\n&f- &e/mafia invite <player> &f- &7Invita pe cineva in mafie\n&f- &e/mafia accept <player> &f- &7Accepta pe cineva in mafie\n&f- &e/mafia list &f- &7Arata toate mafiile"));
            return true;
        }
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
                MafiaManager.getManager().deleteMafia((Player) sender);
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
            case "help":
                sender.sendMessage(ColorUtils.getColorUtils().color("&eWastedLand Mafia Help\n&f- &e/mafia create <nume> &f- &7Creeaza o mafie\n&f- &e/mafia delete &f- &7Sterge mafia ta\n&f- &e/mafia invite <player> &f- &7Invita pe cineva in mafie\n&f- &e/mafia accept <player> &f- &7Accepta pe cineva in mafie\n&f- &e/mafia list &f- &7Arata toate mafiile"));
                break;
            default:
                sender.sendMessage(ColorUtils.getColorUtils().color("&eWastedLand Mafia Help\n&f- &e/mafia create <nume> &f- &7Creeaza o mafie\n&f- &e/mafia delete &f- &7Sterge mafia ta\n&f- &e/mafia invite <player> &f- &7Invita pe cineva in mafie\n&f- &e/mafia accept <player> &f- &7Accepta pe cineva in mafie\n&f- &e/mafia list &f- &7Arata toate mafiile"));
                break;

        }
        return true;
    }
}
