package cn.jonjs.pvpr.data;

import cn.jonjs.pvpr.handlers.TimeHandler;
import cn.jonjs.pvpr.mysql.MySQLConnector;
import cn.jonjs.pvpr.mysql.MySQLQuery;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
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
    public static void setPoint(String playerName, int value) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            query.setPoint(playerName, value);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void addPoint(String playerName, int value) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            query.addPoint(playerName, value);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void takePoint(String playerName, int value) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            query.takePoint(playerName, value);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static int getToday(String playerName) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            int point = query.getToday(playerName);
            conn.close();
            return point;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }
    public static void setToday(String playerName, int value) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            query.setToday(playerName, value);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void addToday(String playerName, int value) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            query.addToday(playerName, value);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void takeToday(String playerName, int value) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            query.takeToday(playerName, value);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void setLastTime(String playerName) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            query.setLastTime(playerName);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static String getLastTime(String playerName) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            String time = query.getLastTime(playerName);
            conn.close();
            return time;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return TimeHandler.now();
        }
    }

    public static int getExp(String playerName) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            int point = query.getExp(playerName);
            conn.close();
            return point;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }
    public static void setExp(String playerName, int value) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            query.setExp(playerName, value);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void addExp(String playerName, int value) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            query.addExp(playerName, value);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void takeExp(String playerName, int value) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            query.takeExp(playerName, value);
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ItemStack getItem(String editName) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            ItemStack item = query.getItemStack(editName);
            conn.close();
            return item;
        } catch (SQLException | IOException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    public static void setItem(String editName, ItemStack item, int price, double discount, int exp, int count) {
        try {
            MySQLConnector connector = new MySQLConnector();
            Connection conn = connector.getConn();
            MySQLQuery query = new MySQLQuery(conn);
            query.setItemStack(editName, item);
            conn.close();
        } catch (SQLException | IOException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


}
