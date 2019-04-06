package me.snowman.wlmafia.utils;

import me.snowman.wlmafia.mafiautils.Mafia;
import me.snowman.wlmafia.mafiautils.MafiaManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SaveUtils {
    private static SaveUtils su;

    public static SaveUtils getUtils() {
        if (su == null)
            su = new SaveUtils();
        return su;
    }

    public void shutDownSave() {
        for (Mafia mafia : MafiaManager.getManager().getMafias()) {
            ConfigManager.getConfigManager().getMafias().set(mafia.getName() + ".owner", mafia.getOwner().toString());
            List<String> temp = new ArrayList<>();
            for (UUID uuid : mafia.getPlayers()) {
                temp.add(uuid.toString());
            }
            ConfigManager.getConfigManager().getMafias().set(mafia.getName() + ".players", temp);
            ConfigManager.getConfigManager().getMafias().set(mafia.getName() + ".deposit", " ");
            ConfigManager.getConfigManager().saveMafias();
        }
    }

    public void loadMafias() {
        for (String m : ConfigManager.getConfigManager().getMafias().getKeys(false)) {
            Mafia mafia = new Mafia(m, UUID.fromString(ConfigManager.getConfigManager().getMafias().getString(m + ".owner")));
            MafiaManager.getManager().getMafias().add(mafia);
            for (String s : ConfigManager.getConfigManager().getMafias().getStringList(m + ".players")) {
                UUID uuid = UUID.fromString(s);
                mafia.getPlayers().add(uuid);
            }
        }
    }
}
