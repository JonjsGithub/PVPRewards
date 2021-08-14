package cn.jonjs.pvpr.handlers;

import cn.jonjs.jonapi.utils.MessageUtils;
import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.ExpData;
import cn.jonjs.pvpr.data.Maps;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class RankTop {

    static FileConfiguration config = Main.getInst().getConfig();

    public static void sort() {
        Map<String, Integer> expMap = new HashMap<>();

        //存入PVP Exp数据
        Set<String> pnKeys = ExpData.config.getKeys(false);
        for(String pn : pnKeys) {
            expMap.put(pn, Data.getExp(pn));
        }

        //排序
        Set<String> playerNames = expMap.keySet();
        String[] ranking = new String[playerNames.size()];
        int n = 0;
        for (String playerName : playerNames) {
            ranking[n] = playerName;
            n++;
        }

        for (int x = 1; x < ranking.length; x++) {
            for (int y = x; y > 0; y--) {
                String playerNameCompared = ranking[y - 1];
                int pointCompared = expMap.get(playerNameCompared);
                String playerName = ranking[y];
                int point = expMap.get(playerName);
                if (point > pointCompared) {
                    ranking[y] = playerNameCompared;
                    ranking[y - 1] = playerName;
                } else {
                    break;
                }
            }
        }

        for (int i = 0; i < ranking.length; i++) {
            int realRank = i + 1;
            String playerName = ranking[i];
            Maps.setRankTopOf(realRank, playerName);
        }
    }

    public static int getPlace(String pn) {
        List<Integer> keyList = new ArrayList<>();
        for(Object key : Maps.rankTopMap.keySet()){
            if(Maps.rankTopMap.get(key).equals(pn) && (key instanceof Integer)){
                keyList.add((int) key);
            }
        }
        return keyList.get(0);
    }

    public static String getPlayerName(int place) {
        return Maps.rankTopMap.getOrDefault(place, "虚位以待");
    }

    public static void sendRankTop(Player p) {
        MessageUtils.sendMessage(p, " ");
        MessageUtils.sendMessage(p, "&b&lPVP&3&lRewards &6&l排行榜");
        MessageUtils.sendMessage(p, "&3排名.玩家名 &7- &3 PVP经验");
        MessageUtils.sendMessage(p, "&c&l1." + RankTop.getPlayerName(1) + "&7 - &b" + Data.getExp(RankTop.getPlayerName(1)));
        MessageUtils.sendMessage(p, "&6&l2." + RankTop.getPlayerName(2) + "&7 - &b" + Data.getExp(RankTop.getPlayerName(2)));
        MessageUtils.sendMessage(p, "&e&l3." + RankTop.getPlayerName(3) + "&7 - &b" + Data.getExp(RankTop.getPlayerName(3)));
        MessageUtils.sendMessage(p, "&f&l4." + RankTop.getPlayerName(4) + "&7 - &b" + Data.getExp(RankTop.getPlayerName(4)));
        MessageUtils.sendMessage(p, "&f&l5." + RankTop.getPlayerName(5) + "&7 - &b" + Data.getExp(RankTop.getPlayerName(5)));
        MessageUtils.sendMessage(p, "&f6." + RankTop.getPlayerName(6) + "&7 - &b" + Data.getExp(RankTop.getPlayerName(6)));
        MessageUtils.sendMessage(p, "&f7." + RankTop.getPlayerName(7) + "&7 - &b" + Data.getExp(RankTop.getPlayerName(7)));
        MessageUtils.sendMessage(p, "&f8." + RankTop.getPlayerName(8) + "&7 - &b" + Data.getExp(RankTop.getPlayerName(8)));
        MessageUtils.sendMessage(p, "&f9." + RankTop.getPlayerName(9) + "&7 - &b" + Data.getExp(RankTop.getPlayerName(9)));
        MessageUtils.sendMessage(p, "&f10." + RankTop.getPlayerName(10) + "&7 - &b" + Data.getExp(RankTop.getPlayerName(10)));
        MessageUtils.sendMessage(p, "&3我的PVP经验值: &b" + Data.getExp(p.getName()));
        MessageUtils.sendMessage(p, "&3我的排行榜排名: &a第" + RankTop.getPlace(p.getName()) + "位");
    }


}
