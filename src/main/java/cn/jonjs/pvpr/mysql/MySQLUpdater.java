package cn.jonjs.pvpr.mysql;

import cn.jonjs.pvpr.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;

public class MySQLUpdater {

    private Connection conn;
    FileConfiguration config = Main.getInst().getConfig();

    public MySQLUpdater(Connection connection) {
        conn = connection;
    }

    public void updateAll() {
        if () {

        }
    }

}
