package cn.jonjs.pvpr.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLQuery {

    private Connection conn;

    public MySQLQuery(Connection connection) {
        conn = connection;
    }

    public int getPoint(String playerName) throws SQLException {
        String sql = "SELECT * FROM ";
        PreparedStatement ps;
        ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.getInt("point");
        ps.close();
    }

}
