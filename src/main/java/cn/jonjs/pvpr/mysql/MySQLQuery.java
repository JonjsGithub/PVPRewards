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
        int point = rs.getInt("point");
        ps.close();
        rs.close();
        return point;
    }

    public int getExp(String playerName) throws SQLException {
        String sql = "SELECT * FROM "+tablePrefix+"_exp WHERE player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int exp = rs.getInt("exp");
        ps.close();
        rs.close();
        return exp;
    }

    public ItemStack getShopItemStack(String editName) throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT * FROM "+tablePrefix+"_shop WHERE edit_name='"+editName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ItemStack item = new ItemStack(Material.AIR);
        Blob blob = rs.getBlob(0);
        Object obj = Serializer.unserialize(blob);
        if(obj instanceof ItemStack) {
            item = (ItemStack) obj;
        }

        rs.close();
        ps.close();
        return item;
    }

    public int getPlayerCount(String playerName, String editName) throws SQLException {
        String sql = "SELECT * FROM "+tablePrefix+"_count WHERE edit_name='"+editName+"' AND player='"+playerName+"';";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int count = rs.getInt("count");
        ps.close();
        rs.close();
        return count;
    }

}
