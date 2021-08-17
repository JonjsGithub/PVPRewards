package cn.jonjs.pvpr.api;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.DataFromSQL;
import cn.jonjs.pvpr.handlers.Rank;
import cn.jonjs.pvpr.handlers.RankTop;
import cn.jonjs.pvpr.handlers.TimeHandler;

public class PVPRewardsAPI {

    final static int API_VERSION = 2;

    public static int getAPIVersion() {
        return API_VERSION;
    }

    public static int getPoint(String playerName) {
        return Main.useMySQL ? DataFromSQL.getPoint(playerName) : Data.getPoint(playerName);
    }
    public static void setPoint(String playerName, int value) {
        if(Main.useMySQL) {
            DataFromSQL.setPoint(playerName, value);
        } else {
            Data.setPoint(playerName, value);
        }
    }

    public static int getTodayPoint(String playerName) {
        return Main.useMySQL ? DataFromSQL.getToday(playerName) : Data.getToday(playerName);
    }
    public static void setTodayPoint(String playerName, int value) {
        if(Main.useMySQL) {
            DataFromSQL.setToday(playerName, value);
        } else {
            Data.setToday(playerName, value);
        }
    }

    public static String getLastTime(String playerName) {
        return Main.useMySQL ? DataFromSQL.getLastTime(playerName) : Data.getLastTime(playerName);
    }
    public static void setLastTime(String playerName) {
        if(Main.useMySQL) {
            DataFromSQL.setLastTime(playerName);
        } else {
            Data.updateLastTime(playerName);
        }
    }

    public static int getExp(String playerName) {
        return Main.useMySQL ? DataFromSQL.getExp(playerName) : Data.getExp(playerName);
    }
    public static void setExp(String playerName, int value) {
        if(Main.useMySQL) {
            DataFromSQL.setExp(playerName, value);
        } else {
            Data.setExp(playerName, value);
        }
    }

    public static String getRankDisplayName(String playerName) {
        return Rank.getRankDisplayName(playerName);
    }

    public static String getRankTopPlayerNameOf(int place) {
        RankTop.sort();
        return RankTop.getPlayerName(place);
    }

    public static int getRankTopPlaceOf(String playerName) {
        RankTop.sort();
        return RankTop.getPlace(playerName);
    }

}
