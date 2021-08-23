package cn.jonjs.pvpr.handlers;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.PointData;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class ConfigUpdater {

    static FileConfiguration config = Main.getInst().getConfig();

    public static void update() {

        // 4 -> 6
        if(config.getInt("Config-Version") == 4) {
            config.set("Config-Version", 6);
            Main.getInst().saveConfig();
            ArrayList<String> players = new ArrayList<>(PointData.config.getKeys(false));
            for(String p : players) {
                int original = PointData.config.getInt(p);
                PointData.config.set(p + ".point", original);
                PointData.config.set(p + ".time", TimeHandler.now());
                PointData.config.set(p + ".today", 0);
            }
            PointData.save();
        }

        // 5 -> 6
        if(config.getInt("Config-Version") == 5) {
            config.set("Config-Version", 6);
            Main.getInst().saveConfig();
        }

    }

}
