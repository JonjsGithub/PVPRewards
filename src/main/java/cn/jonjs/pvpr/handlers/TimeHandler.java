package cn.jonjs.pvpr.handlers;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.DataFromSQL;
import org.bukkit.configuration.file.FileConfiguration;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeHandler {

    static FileConfiguration config = Main.getInst().getConfig();

    public static boolean canAdd(String player) {
        int limit = config.getInt("Settings.PVP.Daily-Limit", -1);
        if(limit == -1) {
            return true;
        }
        int today;
        String last_time;
        if(Main.useMySQL) {
            today = DataFromSQL.getToday(player);
            last_time = DataFromSQL.getLastTime(player);
            boolean isSameDay = isSameDay(last_time, now());
            if( ! isSameDay) {
                DataFromSQL.setToday(player, 0);
                return true;
            }
            if(isSameDay && today < limit) {
                return true;
            }
            if(today >= limit) {
                return false;
            }
        } else {
            today = Data.getToday(player);
            last_time = Data.getLastTime(player);
            boolean isSameDay = isSameDay(last_time, now());
            if( ! isSameDay) {
                Data.setToday(player, 0);
                return true;
            }
            if(isSameDay && today < limit) {
                return true;
            }
            if(today >= limit) {
                return false;
            }
        }

        return false;
    }

    public static String now() {
        LocalDateTime now = LocalDateTime.now();
        String offset = config.getString("Settings.PVP.Time-Zone", "+08:00");
        now.toInstant(ZoneOffset.of(offset));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowStr = now.format(formatter2);
        return nowStr;
    }

    public static boolean isSameDay(String dateStr1, String dateStr2) {
        return dateStr1.equals(dateStr2);
    }

}
