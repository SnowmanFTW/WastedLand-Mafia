package me.snowman.wlmafia.commands;

import me.snowman.wlmafia.mafiautils.Mafia;
import me.snowman.wlmafia.mafiautils.MafiaManager;
import me.snowman.wlmafia.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MafiaCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ColorUtils.getColorUtils().color("&eWastedLand Mafia Help\n&f- &e/clan create <nume> &f- &7Creeaza un clan\n&f- &e/clan delete &f- &7Sterge clanul tau\n&f- &e/clan invite <player> &f- &7Invita pe cineva in clan\n&f- &e/clan accept <player> &f- &7Accepta pe cineva in clan\n&f- &e/clan remove <player> &f &7Scoate pe cineva din clan\n&f- &e/clan list &f- &7Arata toate clanurile\n&f- &e/clan join <clan> &f- &7Intra in clanul cuiva\n&f- &e/clan leave &f- &7Iesi din clan-ul tau\n&f- &e/clan help &f- &7Arata asta"));
            return true;
        }
        switch (args[0]) {
            case "create":
                if (!(sender instanceof Player)) {
                    break;
                }
                if (args.length < 2) {
                    sender.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + "&eFoloseste: &f/clan create <nume>"));
                    break;
                }
                MafiaManager.getManager().createMafia(args[1], (Player) sender);
                break;
            case "list":
                List<Mafia> mafiaList = MafiaManager.getManager().getMafias();

                ArrayList<String> lines = new ArrayList<String>();

                final int pageheight = 4;
                int pagenumber;
                if (args.length < 2) {
                    pagenumber = 1;
                } else {
                    pagenumber = Integer.valueOf(args[1]);
                }
                int pagecount = (mafiaList.size() / pageheight) + 1;
                if (pagenumber > pagecount) {
                    pagenumber = pagecount;
                } else if (pagenumber < 1) {
                    pagenumber = 1;
                }
                int start = (pagenumber - 1) * pageheight;
                int end = start + pageheight;
                if (end > mafiaList.size()) {
                    end = mafiaList.size();
                }

                String header = ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().getMessage("List"));
                sender.sendMessage(header);

                for (Mafia mafia : mafiaList.subList(start, end)) {
                    sender.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().getMessage("ListMafie").replace("%mafia%", mafia.getName())));
                }
                sender.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().getMessage("PaginaUrmatoare").replace("%numar%", String.valueOf(pagenumber + 1))));
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
                if (args.length < 2) {
                    sender.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + "&eFoloseste: &f/clan invite <player>"));
                    break;
                }
                Player invited = Bukkit.getPlayer(args[1]);
                if (invited == null) {
                    sender.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("PlayerNuOnline")));
                    break;
                }
                MafiaManager.getManager().addWaiting(invited, (Player) sender);
                break;
            case "accept":
                if (!(sender instanceof Player)) {
                    break;
                }
                if (args.length < 2) {
                    sender.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + "&eFoloseste: &f/clan accept <player>"));
                    break;
                }
                if (MafiaManager.getManager().getMafia((Player) sender) == null) {
                    MafiaManager.getManager().acceptWaiting((Player) sender, Bukkit.getPlayer(args[1]));
                } else {
                    MafiaManager.getManager().acceptWaitingPeInvers(Bukkit.getPlayer(args[1]), (Player) sender);
                }
                break;
            case "remove":
                if (!(sender instanceof Player)) {
                    break;
                }
                if (args.length < 2) {
                    sender.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + "&eFoloseste: &f/clan remove <player>"));
                    break;
                }
                Player removed = Bukkit.getPlayer(args[1]);
                if (removed == null) {
                    sender.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("PlayerNuOnline")));
                    break;
                }
                MafiaManager.getManager().removePlayer(removed, (Player) sender);
                break;
            case "leave":
                if (!(sender instanceof Player)) {
                    break;
                }
                MafiaManager.getManager().leaveMafia((Player) sender);
                break;
            case "help":
                sender.sendMessage(ColorUtils.getColorUtils().color("&eWastedLand Mafia Help\n&f- &e/clan create <nume> &f- &7Creeaza un clan\n&f- &e/clan delete &f- &7Sterge clanul tau\n&f- &e/clan invite <player> &f- &7Invita pe cineva in clan\n&f- &e/clan accept <player> &f- &7Accepta pe cineva in clan\n&f- &e/clan remove <player> &f &7Scoate pe cineva din clan\n&f- &e/clan list &f- &7Arata toate clanurile\n&f- &e/clan join <clan> &f- &7Intra in clanul cuiva\n&f- &e/clan leave &f- &7Iesi din clan-ul tau\n&f- &e/clan help &f- &7Arata asta"));
                break;
            case "join":
                if (!(sender instanceof Player)) {
                    break;
                }
                if (args.length < 2) {
                    sender.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + "&eFoloseste: &f/clan join <clan>"));
                    break;
                }
                MafiaManager.getManager().addWaitingPeInvers((Player) sender, args[1]);
                break;
            default:
                sender.sendMessage(ColorUtils.getColorUtils().color("&eWastedLand Mafia Help\n&f- &e/clan create <nume> &f- &7Creeaza un clan\n&f- &e/clan delete &f- &7Sterge clanul tau\n&f- &e/clan invite <player> &f- &7Invita pe cineva in clan\n&f- &e/clan accept <player> &f- &7Accepta pe cineva in clan\n&f- &e/clan remove <player> &f &7Scoate pe cineva din clan\n&f- &e/clan list &f- &7Arata toate clanurile\n&f- &e/clan join <clan> &f- &7Intra in clanul cuiva\n&f- &e/clan leave &f- &7Iesi din clan-ul tau\n&f- &e/clan help &f- &7Arata asta"));
                break;
        }
        return true;
    }
}
