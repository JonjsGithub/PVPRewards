package cn.jonjs.pvpr.handlers;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.DataFromSQL;
import org.bukkit.configuration.file.FileConfiguration;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeHandler {

    static FileConfiguration config = Main.getInst().getConfig();

    public static boolean canAdd(String player) {
        int limit = config.getInt("Settings.PVP.Daily-Limit", -1);
        if(limit == -1) {
            return true;
        }
        if(Main.useMySQL) {
            int today = DataFromSQL.getToday(player);
            //int add = config.getInt("Settings.PVP.Point-Per-Player", 0);
            String last_time = DataFromSQL.getLastTime(player);
            boolean isSameDay = isSameDay(last_time, now());
            if( ! isSameDay && today < limit) {
                DataFromSQL.setToday(player, 0);
                return true;
            }
            if(isSameDay && today < limit) {
                return true;
            }
            if(today > limit) {
                return false;
            }
        }
        return false;
    }

    public static String now() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh:mm:ss");
        String nowStr = now.format(formatter2);
        return nowStr;
    }

    public static boolean isSameDay(String timeStr1, String timeStr2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return fmt.format(timeStr1).equals(fmt.format(timeStr2));
    }

}
