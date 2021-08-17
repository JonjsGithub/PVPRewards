package cn.jonjs.pvpr.mysql;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLHelper {

    public static void processAll() {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLInitializer sqlInitializer = new MySQLInitializer(conn);
            sqlInitializer.initTables(); //若表不存在则新建数据表
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
