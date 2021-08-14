package cn.jonjs.pvpr.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Maps {

    static HashMap<String, String> editingMap = new HashMap<>();
    static HashMap<String, Integer> pageMap = new HashMap<>();
    static HashMap<String, String> buyingMap = new HashMap<>();
    public static HashMap<Player, Integer> taskIDMap = new HashMap<>();
    public static HashMap<Player, Integer> coolDownMap = new HashMap<>();
    public static HashMap<Integer, String> rankTopMap = new HashMap<>();
    public static HashMap<String, String> trIDMap = new HashMap<>();

    public static String getTrID(String name) {
        return trIDMap.get(name);
    }
    public static void setTrID(String name, String id) {
        if(trIDMap.containsKey(name)) {
            trIDMap.replace(name, id);
        } else {
            trIDMap.put(name, id);
        }
    }

    public static String getRankTopOf(int number) { //从1起
        return rankTopMap.getOrDefault(number, "虚位以待");
    }
    public static void setRankTopOf(int number, String pn) {
        if(rankTopMap.containsKey(number)) {
            rankTopMap.replace(number, pn);
        } else {
            rankTopMap.put(number, pn);
        }
    }
    public static int getRankTopPlaceOfPlayer(String pn) {
        Set<Integer> mapSet = rankTopMap.keySet(); //获取所有的key值 为set的集合
        Iterator<Integer> itor = mapSet.iterator(); //获取key的Iterator
        while (itor.hasNext()) { //存在下一个值
            int key = itor.next(); //当前key值
            if (rankTopMap.get(key).equals(pn)) { //获取value 与 所知道的value比较
                return key;
            }
        }
        return -1;
    }

    public static int getAntiTaskID(Player p) {
        return taskIDMap.getOrDefault(p, -1);
    }
    public static void setAntiTaskID(Player p, int id) {
        if(taskIDMap.containsKey(p)) {
            taskIDMap.replace(p, id);
        } else {
            taskIDMap.put(p, id);
        }
    }

    public static String getEditing(String playerName) {
        return editingMap.getOrDefault(playerName, null);
    }
    public static void setEditing(String playerName, String editingName) {
        if(editingMap.containsKey(playerName)) {
            editingMap.replace(playerName, editingName);
        } else {
            editingMap.put(playerName, editingName);
        }
    }

    public static String getBuying(String playerName) {
        return buyingMap.getOrDefault(playerName, null);
    }
    public static void setBuying(String playerName, String editingName) {
        if(buyingMap.containsKey(playerName)) {
            buyingMap.replace(playerName, editingName);
        } else {
            buyingMap.put(playerName, editingName);
        }
    }

    public static int getPage(String playerName) {
        return pageMap.getOrDefault(playerName, 1);
    }
    public static void setPage(String playerName, int pageNow) {
        if(editingMap.containsKey(playerName)) {
            pageMap.replace(playerName, pageNow);
        } else {
            pageMap.put(playerName, pageNow);
        }
    }

}
