package cn.jonjs.pvpr.api;

import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.handlers.Rank;
import cn.jonjs.pvpr.handlers.RankTop;

public class PVPRewardsAPI {

    final static int API_VERSION = 1;

    public static int getAPIVersion() {
        return API_VERSION;
    }

    public static int getPoint(String playerName) {
        return Data.getPoint(playerName);
    }
    public static void setPoint(String playerName, int value) {
        Data.setPoint(playerName, value);
    }

    public static int getExp(String playerName) {
        return Data.getExp(playerName);
    }
    public static void setExp(String playerName, int value) {
        Data.setExp(playerName, value);
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
