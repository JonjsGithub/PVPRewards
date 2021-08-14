package cn.jonjs.pvpr.data;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CountData {
    public static File configfile = new File("plugins/PVPRewards/count.yml");

    public static FileConfiguration config;

    public static void load() {
        config = (FileConfiguration) YamlConfiguration.loadConfiguration(configfile);
        config.options().copyDefaults(true);
    }

    public static void save() {
        try {
            config.save(configfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
