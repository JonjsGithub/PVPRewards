package cn.jonjs.pvpr.handlers;

import cn.jonjs.jonapi.utils.MessageUtils;
import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.DataFromSQL;
import cn.jonjs.pvpr.data.ExpData;
import cn.jonjs.pvpr.data.Maps;
import cn.jonjs.pvpr.mysql.MySQLConnector;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RankTop {

    static FileConfiguration config = Main.getInst().getConfig();

    public static void sort() {
        Map<String, Integer> expMap = new HashMap<>();

        //存入PVP Exp数据
        if(Main.useMySQL) {
            try {
                MySQLConnector connector = new MySQLConnector();
                Connection conn = connector.getConn();
                String prefix = connector.getPrefix();
                PreparedStatement ps;
                ps = conn.prepareStatement("SELECT * FROM " + prefix + "_exp");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String player = rs.getString("player");
                    int exp = rs.getInt("exp");
                    expMap.put(player, exp);
                }
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Set<String> pnKeys = ExpData.config.getKeys(false);
            for (String pn : pnKeys) {
                int exp = Data.getExp(pn);
                expMap.put(pn, exp);
            }
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
        int myExp = Main.useMySQL ? DataFromSQL.getExp(p.getName()) : Data.getExp(p.getName());
        int myPlace = RankTop.getPlace(p.getName());
        String pn1 = RankTop.getPlayerName(1);
        String pn2 = RankTop.getPlayerName(2);
        String pn3 = RankTop.getPlayerName(3);
        String pn4 = RankTop.getPlayerName(4);
        String pn5 = RankTop.getPlayerName(5);
        String pn6 = RankTop.getPlayerName(6);
        String pn7 = RankTop.getPlayerName(7);
        String pn8 = RankTop.getPlayerName(8);
        String pn9 = RankTop.getPlayerName(9);
        String pn10 = RankTop.getPlayerName(10);
        int exp1 = Main.useMySQL ? DataFromSQL.getExp(pn1) : Data.getExp(pn1);
        int exp2 = Main.useMySQL ? DataFromSQL.getExp(pn2) : Data.getExp(pn2);
        int exp3 = Main.useMySQL ? DataFromSQL.getExp(pn3) : Data.getExp(pn3);
        int exp4 = Main.useMySQL ? DataFromSQL.getExp(pn4) : Data.getExp(pn4);
        int exp5 = Main.useMySQL ? DataFromSQL.getExp(pn5) : Data.getExp(pn5);
        int exp6 = Main.useMySQL ? DataFromSQL.getExp(pn6) : Data.getExp(pn6);
        int exp7 = Main.useMySQL ? DataFromSQL.getExp(pn7) : Data.getExp(pn7);
        int exp8 = Main.useMySQL ? DataFromSQL.getExp(pn8) : Data.getExp(pn8);
        int exp9 = Main.useMySQL ? DataFromSQL.getExp(pn9) : Data.getExp(pn9);
        int exp10 = Main.useMySQL ? DataFromSQL.getExp(pn10) : Data.getExp(pn10);
        MessageUtils.sendMessage(p, " ");
        MessageUtils.sendMessage(p, "&b&lPVP&3&lRewards &6&l排行榜");
        MessageUtils.sendMessage(p, "&3排名.玩家名 &7- &3 PVP经验");
        MessageUtils.sendMessage(p, "&c&l1." + pn1 + "&7 - &b" + exp1);
        MessageUtils.sendMessage(p, "&6&l2." + pn2 + "&7 - &b" + exp2);
        MessageUtils.sendMessage(p, "&e&l3." + pn3 + "&7 - &b" + exp3);
        MessageUtils.sendMessage(p, "&f&l4." + pn4 + "&7 - &b" + exp4);
        MessageUtils.sendMessage(p, "&f&l5." + pn5 + "&7 - &b" + exp5);
        MessageUtils.sendMessage(p, "&f6." + pn6 + "&7 - &b" + exp6);
        MessageUtils.sendMessage(p, "&f7." + pn7 + "&7 - &b" + exp7);
        MessageUtils.sendMessage(p, "&f8." + pn8 + "&7 - &b" + exp8);
        MessageUtils.sendMessage(p, "&f9." + pn9 + "&7 - &b" + exp9);
        MessageUtils.sendMessage(p, "&f10." + pn10 + "&7 - &b" + exp10);
        MessageUtils.sendMessage(p, "&3我的PVP经验值: &b" + myExp);
        MessageUtils.sendMessage(p, "&3我的排行榜排名: &a第" + myPlace + "位");
    }


}
