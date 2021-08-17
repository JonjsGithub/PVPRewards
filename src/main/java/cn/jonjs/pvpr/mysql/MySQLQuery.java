package cn.jonjs.pvpr.mysql;

import cn.jonjs.jonapi.serialize.Serializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
            sql = "INSERT INTO "+tablePrefix+"_point VALUES ('"+playerName+"', 0, now(), 0);";
            ps = conn.prepareStatement(sql);
            ps.execute();
        }
        ps.close();
        rs.close();
        return point;
    }
    public void setPoint(String playerName, int point) throws SQLException {
        int original = getPoint(playerName);
        String sql = "UPDATE "+tablePrefix+"_point SET point="+point+" WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }
    public void addPoint(String playerName, int point) throws SQLException {
        int value = getPoint(playerName) + point;
        String sql = "UPDATE "+tablePrefix+"_point SET point="+value+" WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }
    public void takePoint(String playerName, int point) throws SQLException {
        int value = getPoint(playerName) - point;
        String sql = "UPDATE "+tablePrefix+"_point SET point="+ (value<0 ? 0 : value) +" WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
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
            sql = "INSERT INTO "+tablePrefix+"_point VALUES ('"+playerName+"', 0, now(), 0);";
            ps = conn.prepareStatement(sql);
            ps.execute();
        }
        ps.close();
        rs.close();
        return point;
    }
    public void setToday(String playerName, int point) throws SQLException {
        int original = getToday(playerName);
        String sql = "UPDATE "+tablePrefix+"_point SET today_point="+point+" WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }
    public void addToday(String playerName, int point) throws SQLException {
        int value = getToday(playerName) + point;
        String sql = "UPDATE "+tablePrefix+"_point SET today_point="+value+" WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }
    public void takeToday(String playerName, int point) throws SQLException {
        int value = getToday(playerName) - point;
        String sql = "UPDATE "+tablePrefix+"_point SET today_point="+ (value<0 ? 0 : value) +" WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }

    public String getLastTime(String playerName) throws SQLException {
        String sql = "SELECT * FROM "+tablePrefix+"_point WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String time = "";
        if(rs.next()) {
            time = rs.getString("last_time");
        } else {
            sql = "INSERT INTO "+tablePrefix+"_point VALUES ('"+playerName+"', 0, now(), 0);";
            ps = conn.prepareStatement(sql);
            ps.execute();
        }
        ps.close();
        rs.close();
        return time;
    }
    public void setLastTime(String playerName) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String nowStr = now.format(formatter);
        String sql = "UPDATE "+tablePrefix+"_point SET last_time='"+nowStr+"' WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }

    public int getExp(String playerName) throws SQLException {
        String sql = "SELECT * FROM "+tablePrefix+"_exp WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int point = 0;
        if(rs.next()) {
            point = rs.getInt("exp");
        } else {
            sql = "INSERT INTO "+tablePrefix+"_exp VALUES ('"+playerName+"', 0);";
            ps = conn.prepareStatement(sql);
            ps.execute();
        }
        ps.close();
        rs.close();
        return point;
    }
    public void setExp(String playerName, int exp) throws SQLException {
        int original = getExp(playerName);
        String sql = "UPDATE "+tablePrefix+"_exp SET exp="+exp+" WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }
    public void addExp(String playerName, int exp) throws SQLException {
        int value = getExp(playerName) + exp;
        String sql = "UPDATE "+tablePrefix+"_exp SET exp="+value+" WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }
    public void takeExp(String playerName, int exp) throws SQLException {
        int value = getExp(playerName) - exp;
        String sql = "UPDATE "+tablePrefix+"_exp SET exp="+ (value<0 ? 0 : value) +" WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }


}
