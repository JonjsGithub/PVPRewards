package cn.jonjs.pvpr.mysql;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.PointData;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class MySQLUpdater {

    private Connection conn;
    private String tablePrefix;
    FileConfiguration config = Main.getInst().getConfig();

    public MySQLUpdater(Connection connection) {
        conn = connection;
        tablePrefix = MySQLConnector.PREFIX;
    }

    public void updatePoint(String player, int point) throws SQLException {
        String sql = "UPDATE "+tablePrefix+"_point SET point=" + point + " WHERE player='" + player + "';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ps.execute();
    }

}
