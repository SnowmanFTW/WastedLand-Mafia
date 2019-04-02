package me.snowman.wlmafia.mafiautils;

import me.snowman.wlmafia.utils.ConfigManager;
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

    public void addPlayer(Player player, String mafia) {
        Mafia m = this.getMafia(mafia);

        if (m == null) {
            player.sendMessage("nu");
            return;
        }

        m.getPlayers().add(player.getUniqueId());
        List<String> temp = new ArrayList<>();
        for (UUID uuid : m.getPlayers()) {
            temp.add(uuid.toString());
        }
        ConfigManager.getConfigManager().getMafias().set(mafia + ".players", temp);
        ConfigManager.getConfigManager().saveMafias();
        player.sendMessage("adaugat");
    }

    public void removePlayer(Player player) {
        Mafia m = null;

        for (Mafia mafia : this.mafias) {
            if (mafia.getPlayers().contains(player.getUniqueId())) m = mafia;
        }

        if (m == null) {
            player.sendMessage("nu este in mafie");
            return;
        }

        m.getPlayers().remove(player.getUniqueId());
        player.sendMessage("scos");
    }

    public void createMafia(String name, Player player) {
        if (this.mafias.contains(getMafia(name))) {
            player.sendMessage("exista");
            return;
        }
        for (Mafia mafias : mafias) {
            if (mafias.getPlayers().contains(player.getUniqueId())) {
                player.sendMessage("esti deja in mafie");
                return;
            }
        }

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
        player.sendMessage("creeat");
    }

    public void deleteMafia(Player player, String name) {
        Mafia m = this.getMafia(name);

        if (!this.mafias.contains(m)) {
            player.sendMessage("nu exista");
            return;
        }

        if (!player.getUniqueId().equals(m.getOwner())) {
            player.sendMessage("nu esti owner");
            return;
        }

        m.getPlayers().removeAll(m.getPlayers());
        this.mafias.remove(m);
        ConfigManager.getConfigManager().getMafias().set(name, null);
        ConfigManager.getConfigManager().saveMafias();
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
            player.sendMessage("nuuiu");
        }
        m.getWaiting().add(player.getUniqueId());
        player.sendMessage("ai fost invitat in " + m.getName() + " prostule");
    }

    public void acceptWaiting(Player player, Player owner) {
        Mafia m = null;
        for (Mafia mafias : getMafias()) {
            if (mafias.getOwner().equals(owner.getUniqueId())) {
                m = mafias;
            }
        }

        if (m == null) {
            player.sendMessage("nuuiu");
        }

        if (m.getWaiting().contains(player.getUniqueId())) {
            this.addPlayer(player, m.getName());
            m.getWaiting().remove(player.getUniqueId());
        } else {
            player.sendMessage("nu a fost invitat dobitocule");
        }
    }


}
