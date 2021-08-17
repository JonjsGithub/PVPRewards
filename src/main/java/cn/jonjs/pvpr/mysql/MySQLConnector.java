package cn.jonjs.pvpr.mysql;

import cn.jonjs.pvpr.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {

    FileConfiguration config = Main.getInst().getConfig();
    private Connection conn;
    private String URL;
    private String USER;
    private String PASSWORD;
    public static String PREFIX;

    public MySQLConnector() throws SQLException {
        String DB_IP = config.getString("Settings.MySQL.Config.DB_IP", "localhost");
        int DB_PORT = config.getInt("Settings.MySQL.Config.DB_PORT", 3306);
        String DB_NAME = config.getString("Settings.MySQL.Config.DB_NAME", "pvprewards");
        USER = config.getString("Settings.MySQL.Config.DB_USER", "root");
        PASSWORD = config.getString("Settings.MySQL.Config.DB_PASSWORD", "root");
        String DB_TABLE_PREFIX = config.getString("Settings.MySQL.Config.DB_TABLE_PREFIX", "pvpr");
        URL = "jdbc:mysql://"+DB_IP+":"+DB_PORT+"/"+DB_NAME+"?useSSL=false";
        PREFIX = DB_TABLE_PREFIX;
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Connection getConn() {
        return conn;
    }

    public String getPrefix() {
        return PREFIX;
    }

}
