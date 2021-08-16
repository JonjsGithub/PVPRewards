package cn.jonjs.pvpr.handlers;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.PointData;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class ConfigUpdater {

    static FileConfiguration config = Main.getInst().getConfig();

    public static void update() {
        if(config.getInt("Config-Version") == 4) {
            config.set("Settings.MySQL.Enable", false);
            config.set("Settings.MySQL.Config.DB_IP", "localhost");
            config.set("Settings.MySQL.Config.DB_PORT", 3306);
            config.set("Settings.MySQL.Config.DB_NAME", "pvprewards");
            config.set("Settings.MySQL.Config.DB_USER", "root");
            config.set("Settings.MySQL.Config.DB_PASSWORD", "root");
            config.set("Settings.MySQL.Config.DB_TABLE_PREFIX", "pvpr");
            config.set("Settings.PVP.Point-Take-On-Death", 0);
            config.set("Settings.PVP.Exp-Take-On-Death", 0);
            config.set("Messages.PVP-Player-Died", "message@&b&lPVPR &8>> &c你因在PVP中死亡而被扣除 &b{point} &3PVP积分、&b{exp} &3PVP经验值");
            config.set("Config-Version", 5);
            Main.getInst().saveConfig();
            ArrayList<String> players = new ArrayList<>(PointData.config.getKeys(false));
            for(String p : players) {
                int original = PointData.config.getInt(p);
                PointData.config.set(p + ".point", original);
                PointData.config.set(p + ".today", 0);
            }
            PointData.save();
        }
    }

}
