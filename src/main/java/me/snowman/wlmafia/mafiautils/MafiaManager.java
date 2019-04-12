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
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieOwner")));
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
        Bukkit.broadcastMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieAIntrat").replace("%mafia%", m.getName()).replace("%player%", player.getName())));
    }

    public void removePlayer(Player player, Player owner) {
        Mafia m = null;

        for (Mafia mafia : this.mafias) {
            if (mafia.getPlayers().contains(player.getUniqueId())) m = mafia;
        }

        if (m == null) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieNuE")));
            return;
        }
        if (!m.getOwner().equals(owner.getUniqueId())) {
            owner.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieOwner")));
            return;
        }
        if (m.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieNuPotiIesi")));
            return;
        }
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

    public void leaveMafia(Player player) {
        Mafia m = null;

        for (Mafia mafia : this.mafias) {
            if (mafia.getPlayers().contains(player.getUniqueId())) m = mafia;
        }

        if (m == null) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieNuE")));
            return;
        }
        if (m.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieNuPotiIesi")));
            return;
        }
        m.getPlayers().remove(player.getUniqueId());
        List<String> temp = new ArrayList<>();
        for (UUID uuid : m.getPlayers()) {
            temp.add(uuid.toString());
        }
        ConfigManager.getConfigManager().getMafias().set(m.getName() + ".players", temp);
        ConfigManager.getConfigManager().saveMafias();
        player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieIesit").replace("%mafia%", m.getName())));
    }

    public void createMafia(String name, Player player) {
        if (this.mafias.contains(getMafia(name))) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieExista").replace("%mafia%", name)));
            return;
        }
        for (Mafia mafias : mafias) {
            if (mafias.getPlayers().contains(player.getUniqueId())) {
                player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieEstiDeja").replace("%mafia%", name)));
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
        player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieCreeata").replace("%mafia%", name)));
    }

    public void deleteMafia(Player player) {
        Mafia m = null;
        for (Mafia mafias : getMafias()) {
            if (mafias.getOwner().equals(player.getUniqueId())) {
                m = mafias;
            }
        }

        if (m == null) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieOwner")));
            return;
        }

        m.getPlayers().removeAll(m.getPlayers());
        this.mafias.remove(m);
        ConfigManager.getConfigManager().getMafias().set(m.getName(), null);
        ConfigManager.getConfigManager().saveMafias();
        player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieStearsa").replace("%mafia%", m.getName())));
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
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieOwner")));
            return;
        }
        if (m.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieInviteSingur")));
            return;
        }
        m.getWaiting().add(player.getUniqueId());
        player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieInvitat").replace("%mafia%", m.getName())));
        owner.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieScrie").replace("%player%", owner.getName())));
    }

    public void addWaitingPeInvers(Player player, String name) {
        Mafia m = null;
        for (Mafia mafias : getMafias()) {
            if (mafias.getName().equalsIgnoreCase(name)) {
                m = mafias;
            }
        }
        if (m == null) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieNuExista").replace("%mafia%", name)));
            return;
        }
        if (m.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieInviteSingur")));
            return;
        }
        m.getWaiting().add(player.getUniqueId());
        Bukkit.getPlayer(m.getOwner()).sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieVreaSaIntre").replace("%player%", player.getName())));
        Bukkit.getPlayer(m.getOwner()).sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieScrie").replace("%player%", player.getName())));
    }

    public void acceptWaiting(Player player, Player owner) {
        Mafia m = null;
        for (Mafia mafias : getMafias()) {
            if (mafias.getOwner().equals(owner.getUniqueId())) {
                m = mafias;
            }
        }

        if (m == null) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieNeinvitat").replace("%player%", owner.getName())));
            return;
        }

        if (m.getWaiting().contains(player.getUniqueId())) {
            this.addPlayer(player, m.getName());
            m.getWaiting().remove(player.getUniqueId());
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieAcceptat").replace("%mafia%", m.getName())));
        } else {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieNeinvitat").replace("%player%", owner.getName())));
        }
    }

    public void acceptWaitingPeInvers(Player player, Player owner) {
        Mafia m = null;
        for (Mafia mafias : getMafias()) {
            if (mafias.getWaiting().contains(player.getUniqueId())) {
                m = mafias;
            }
        }

        if (m == null) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieNuADatJoin").replace("%player%", player.getName())));
            return;
        }
        if (!m.getOwner().equals(owner.getUniqueId())) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieOwner")));
            return;
        }
        if (m.getWaiting().contains(player.getUniqueId())) {
            this.addPlayer(player, m.getName());
            m.getWaiting().remove(player.getUniqueId());
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieAcceptat").replace("%mafia%", m.getName())));
        } else {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieNeinvitat").replace("%player%", owner.getName())));
        }
    }

    public void addRemoveChat(Player player) {
        Mafia m = null;
        for (Mafia mafias : getMafias()) {
            if (mafias.getPlayers().contains(player.getUniqueId())) {
                m = mafias;
            }
        }

        if (m == null) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieNuEsti")));
            return;
        }

        if (m.getChat().contains(player.getUniqueId())) {
            player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieChatIesit")));
            m.getChat().remove(player.getUniqueId());
            return;
        }
        m.getChat().add(player.getUniqueId());
        player.sendMessage(ColorUtils.getColorUtils().color(ColorUtils.getColorUtils().prefix + ColorUtils.getColorUtils().getMessage("MafieChatIntrat")));
    }



}
