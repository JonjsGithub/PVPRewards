package cn.jonjs.pvpr.mysql;

import cn.jonjs.pvpr.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;

public class MySQLConnector {

    FileConfiguration config = Main.getInst().getConfig();
    private Connection conn;
    private String URL;
    private String USER;
    private String PASSWORD;
    public static String PREFIX;

    public MySQLConnector() {
        String DB_IP = config.getString("Settings.MySQL.Config.DB_IP", "localhost");
        int DB_PORT = config.getInt("Settings.MySQL.Config.DB_PORT", 3306);
        String DB_NAME = config.getString("Settings.MySQL.Config.DB_NAME", "pvprewards");
        String DB_USER = config.getString("Settings.MySQL.Config.DB_USER", "root");
        String DB_PASSWORD = config.getString("Settings.MySQL.Config.DB_PASSWORD", "root");
        String DB_TABLE_PREFIX = config.getString("Settings.MySQL.Config.DB_TABLE_PREFIX", "pvpr");
        URL = "jdbc:mysql://"+DB_IP+":"+DB_PORT+"/"+DB_NAME+"?useSSL=false";
        USER = DB_USER;
        PASSWORD = DB_PASSWORD;
        PREFIX = DB_TABLE_PREFIX;
        cn.jonjs.jonapi.mysql.MySQLConnector connector = new cn.jonjs.jonapi.mysql.MySQLConnector(
                URL,
                USER,
                PASSWORD
        );
        conn = connector.getConn();
    }

    public Connection getConn() {
        return conn;
    }

    public String getPrefix() {
        return PREFIX;
    }

}
