package me.snowman.wlmafia.mafiautils;

import me.snowman.wlmafia.utils.ColorUtils;
import me.snowman.wlmafia.utils.ConfigManager;
import me.snowman.wlmafia.utils.EconomyUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MafiaManager {
    private static MafiaManager mm;
    private List<Mafia> mafias = new ArrayList<>();
    private ColorUtils colorUtils = ColorUtils.getColorUtils();

    public static MafiaManager getManager() {
        if (mm == null)
            mm = new MafiaManager();
        return mm;
    }

    public Mafia getMafia(String name) {
        for (Mafia m : this.mafias) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public Mafia getMafia(Player player) {
        for (Mafia m : this.mafias) {
            if (m.getPlayers().contains(player.getUniqueId())) {
                return m;
            }
        }
        return null;
    }

    public void addPlayer(Player player, String mafia) {
        Mafia m = this.getMafia(mafia);

        if (m == null) {
            player.sendMessage(colorUtils.color(colorUtils.prefix + colorUtils.getMessage("MafieOwner")));
            return;
        }

        if (m.getPlayers().size() == 5) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieMaxim")));
        }
        m.getPlayers().add(player.getUniqueId());
        List<String> temp = new ArrayList<>();
        for (UUID uuid : m.getPlayers()) {
            temp.add(uuid.toString());
        }
        ConfigManager.getConfigManager().getMafias().set(mafia + ".players", temp);
        ConfigManager.getConfigManager().saveMafias();
    }

    public void removePlayer(Player player) {
        Mafia m = null;

        for (Mafia mafia : this.mafias) {
            if (mafia.getPlayers().contains(player.getUniqueId())) m = mafia;
        }

        if (m == null) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieNuE")));
            return;
        }
        Player owner = Bukkit.getPlayer(m.getOwner());
        m.getPlayers().remove(player.getUniqueId());
        List<String> temp = new ArrayList<>();
        for (UUID uuid : m.getPlayers()) {
            temp.add(uuid.toString());
        }
        ConfigManager.getConfigManager().getMafias().set(m.getName() + ".players", temp);
        ConfigManager.getConfigManager().saveMafias();
        player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieScos").replace("%mafia%", m.getName())));
        owner.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieAiScos").replace("%player%", player.getName())));
    }

    public void createMafia(String name, Player player) {
        if (this.mafias.contains(getMafia(name))) {
            player.sendMessage(colorUtils.color(colorUtils.prefix + colorUtils.getMessage("MafieExista").replace("%mafia%", name)));
            return;
        }
        for (Mafia mafias : mafias) {
            if (mafias.getPlayers().contains(player.getUniqueId())) {
                player.sendMessage(colorUtils.color(colorUtils.prefix + colorUtils.getMessage("MafieEstiDeja").replace("%mafia%", name)));
                return;
            }
        }

        if (!EconomyUtils.getEconomy().getEcon().has(player, ConfigManager.getConfigManager().getConfig().getInt("BaniMafie"))) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieNuAiBani").replace("%bani%", String.valueOf(ConfigManager.getConfigManager().getConfig().getInt("BaniMafie")))));
            return;
        }
        EconomyUtils.getEconomy().getEcon().withdrawPlayer(player, ConfigManager.getConfigManager().getConfig().getInt("BaniMafie"));
        Mafia m = new Mafia(name, player.getUniqueId());
        this.mafias.add(m);
        m.getPlayers().add(player.getUniqueId());
        List<String> temp = new ArrayList<>();
        for (UUID uuid : m.getPlayers()) {
            temp.add(uuid.toString());
        }
        ConfigManager.getConfigManager().getMafias().set(name + ".players", temp);
        ConfigManager.getConfigManager().getMafias().set(name + ".deposit", " ");
        ConfigManager.getConfigManager().getMafias().set(name + ".owner", player.getUniqueId().toString());
        ConfigManager.getConfigManager().saveMafias();
        player.sendMessage(colorUtils.color(colorUtils.prefix + colorUtils.getMessage("MafieCreeata").replace("%mafia%", name)));
    }

    public void deleteMafia(Player player) {
        Mafia m = null;
        for (Mafia mafias : getMafias()) {
            if (mafias.getOwner().equals(player.getUniqueId())) {
                m = mafias;
            }
        }

        if (m == null) {
            player.sendMessage(colorUtils.color(colorUtils.prefix + colorUtils.getMessage("MafieOwner")));
            return;
        }

        m.getPlayers().removeAll(m.getPlayers());
        this.mafias.remove(m);
        ConfigManager.getConfigManager().getMafias().set(m.getName(), null);
        ConfigManager.getConfigManager().saveMafias();
        player.sendMessage(colorUtils.color(colorUtils.prefix + colorUtils.getMessage("MafieStearsa").replace("%mafia%", m.getName())));
    }

    public List<Mafia> getMafias() {
        return mafias;
    }

    public void addWaiting(Player player, Player owner) {
        Mafia m = null;
        for (Mafia mafias : getMafias()) {
            if (mafias.getOwner().equals(owner.getUniqueId())) {
                m = mafias;
            }
        }

        if (m == null) {
            player.sendMessage(colorUtils.color(colorUtils.prefix + colorUtils.getMessage("MafieOwner")));
            return;
        }
        m.getWaiting().add(player.getUniqueId());
        player.sendMessage(colorUtils.color(colorUtils.prefix + colorUtils.getMessage("MafieInvitat").replace("%mafia%", m.getName())));
    }

    public void acceptWaiting(Player player, Player owner) {
        Mafia m = null;
        for (Mafia mafias : getMafias()) {
            if (mafias.getOwner().equals(owner.getUniqueId())) {
                m = mafias;
            }
        }

        if (m == null) {
            player.sendMessage(colorUtils.color(colorUtils.prefix + colorUtils.getMessage("MafieNeinvitat").replace("%player%", owner.getName())));
            return;
        }

        if (m.getWaiting().contains(player.getUniqueId())) {
            this.addPlayer(player, m.getName());
            m.getWaiting().remove(player.getUniqueId());
            player.sendMessage(colorUtils.color(colorUtils.prefix + colorUtils.getMessage("MafieAcceptat").replace("%mafia%", m.getName())));
        } else {
            player.sendMessage(colorUtils.color(colorUtils.prefix + colorUtils.getMessage("MafieNeinvitat").replace("%player%", owner.getName())));
        }
    }


}
