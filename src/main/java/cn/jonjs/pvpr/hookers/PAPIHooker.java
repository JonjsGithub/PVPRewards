package cn.jonjs.pvpr.hookers;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.DataFromSQL;
import cn.jonjs.pvpr.handlers.Rank;
import cn.jonjs.pvpr.handlers.RankTop;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class PAPIHooker extends PlaceholderExpansion {

    private Plugin plugin;

    public PAPIHooker(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "Jonjs2333";
    }

    @Override
    public String getIdentifier() {
        return "pvprewards";
    }

    @Override
    public String getVersion() {
        return "2.1";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        if (params.equalsIgnoreCase("point")) {
            return Main.useMySQL ? "" + DataFromSQL.getPoint(player.getName()) : "" + Data.getPoint(player.getName());
        }

        if (params.equalsIgnoreCase("exp")) {
            return Main.useMySQL ? "" + DataFromSQL.getExp(player.getName()) : "" + Data.getExp(player.getName());
        }

        if (params.equalsIgnoreCase("rank")) {
            return "" + Rank.getRankDisplayName(Rank.getRank(player.getName()));
        }

        if (params.contains("ranktop")) {
            RankTop.sort();
            // ranktop_place
            // ranktop_#_sth
            String[] arr = params.split("_");
            if (arr.length == 2) {
                // 玩家排行榜位次
                if (arr[1].equalsIgnoreCase("place")) {
                    return "" + RankTop.getPlace(player.getName());
                }
            }
            if (arr.length == 3) {
                // 1~10
                int place = Integer.parseInt(arr[1]);
                    if (arr[2].equalsIgnoreCase("player")) {
                        return RankTop.getPlayerName(place);
                    }
                    if (arr[2].equalsIgnoreCase("point")) {
                        return Main.useMySQL ? "" + DataFromSQL.getPoint(RankTop.getPlayerName(place)) : "" + Data.getPoint(RankTop.getPlayerName(place));
                    }
                    if (arr[2].equalsIgnoreCase("exp")) {
                        return Main.useMySQL ? "" + DataFromSQL.getExp(RankTop.getPlayerName(place)) : "" + Data.getExp(RankTop.getPlayerName(place));
                    }
                    if (arr[2].equalsIgnoreCase("rank")) {
                        return Rank.getRankDisplayName(Rank.getRank(RankTop.getPlayerName(place)));
                    }
                return "N";
            }

            return "E";
        }

        return null; // Placeholder is unknown by the Expansion

    }
}

