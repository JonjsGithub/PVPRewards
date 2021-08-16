package cn.jonjs.pvpr.data;

import cn.jonjs.pvpr.mysql.MySQLConnector;
import cn.jonjs.pvpr.mysql.MySQLQuery;

import java.sql.Connection;
import java.sql.SQLException;

public class DataFromSQL {

    public static int getPoint(String playerName) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            int point = query.getPoint(playerName);
            conn.close();
            return point;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }

}
