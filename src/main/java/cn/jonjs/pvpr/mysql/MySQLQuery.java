package cn.jonjs.pvpr.mysql;

import cn.jonjs.jonapi.serialize.Serializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.*;

public class MySQLQuery {

    private Connection conn;
    private String tablePrefix;

    public MySQLQuery(Connection connection) {
        conn = connection;
        tablePrefix = MySQLConnector.PREFIX;
    }

    public int getPoint(String playerName) throws SQLException {
        String sql = "SELECT * FROM "+tablePrefix+"_point WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int point = 0;
        if(rs.next()) {
            point = rs.getInt("point");
        } else {
            sql = "INSERT INTO "+tablePrefix+"_point ('"+playerName+"', 0, now(), 0)";
            ps = conn.prepareStatement(sql);
            ps.execute();
        }
        ps.close();
        rs.close();
        return point;
    }

    public int getToday(String playerName) throws SQLException {
        String sql = "SELECT * FROM "+tablePrefix+"_point WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int point = 0;
        if(rs.next()) {
            point = rs.getInt("today_point");
        } else {
            sql = "INSERT INTO "+tablePrefix+"_point ('"+playerName+"', 0, now(), 0)";
            ps = conn.prepareStatement(sql);
            ps.execute();
        }
        ps.close();
        rs.close();
        return point;
    }


}
