package cn.jonjs.pvpr.handlers;

import cn.jonjs.jonapi.utils.MessageUtils;
import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.DataFromSQL;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Set;

public class Rank {

    static FileConfiguration config = Main.getInst().getConfig();

    public static ArrayList<String> getRankListKeys() {
        ConfigurationSection section = config.getConfigurationSection("Settings.Rank.List");

        if (section == null) {
            ArrayList<String> keys = new ArrayList<>();
            return keys;
        }
        Set<String> set = section.getKeys(false);
        ArrayList<String> keys = new ArrayList<>(set);
        return keys;
    }

    public static String getRank(String playerName) {
        int playerExp = Main.useMySQL ? DataFromSQL.getExp(playerName) : Data.getExp(playerName);
        String rankKey = "";
        ArrayList<String> ranks = getRankListKeys();
        for (String key : ranks) {
            int needExp = getRankNeedExp(key);
            if (playerExp >= needExp) {
                rankKey = key;
            }
        }
        return rankKey;
    }

    public static int getRankNeedExp(String keyName) {
        return config.getInt("Settings.Rank.List." + keyName + ".exp", -1);
    }

    public static String getRankDisplayName(String keyName) {
        String displayName = config.getString("Settings.Rank.List." + keyName + ".display-name", "[]");
        return MessageUtils.color(displayName);
    }

    public static void sendRankList(Player p) {
        p.sendMessage(" ");
        MessageUtils.sendMessage(p, "&b&lPVP&3&lRewards &6&l段位系统");
        MessageUtils.sendMessage(p, "&3段位称号 &7- &3所需PVP经验");
        ArrayList<String> ranks = getRankListKeys();
        for(String rank : ranks) {
            MessageUtils.sendMessage(p, "&r" + getRankDisplayName(rank) + " &7- &b" + getRankNeedExp(rank));
        }
    }

}
