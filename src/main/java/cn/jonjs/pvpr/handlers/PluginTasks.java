package cn.jonjs.pvpr.handlers;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.*;

public class PluginTasks {

    public static void reloadAll() {
        Main.getInst().getConfig().options().copyDefaults(true);
        Main.getInst().saveConfig();
        Main.getInst().reloadConfig();
        ShopData.load();
        ShopData.save();
        PointData.load();
        PointData.save();
        ExpData.load();
        ExpData.save();
        CountData.load();
        CountData.save();
        HologramData.load();
        HologramData.save();
    }

}
