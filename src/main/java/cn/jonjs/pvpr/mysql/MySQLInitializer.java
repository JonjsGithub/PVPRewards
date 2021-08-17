package cn.jonjs.pvpr.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLInitializer {

    private Connection conn;
    private String tablePrefix;

    public MySQLInitializer(Connection connection) {
        conn = connection;
        System.out.println(conn);
        tablePrefix = MySQLConnector.PREFIX;
    }

    public void initTables() throws SQLException {

        /** 创建数据表(若不存在) **/
        ArrayList<String> sqlList = new ArrayList<>();
        sqlList.add("SET NAMES utf8;");
        sqlList.add("CREATE TABLE IF NOT EXISTS `"+tablePrefix+"_point` (" +
                "`player` varchar(128) NOT NULL, UNIQUE KEY (`player`)," +
                "`point` int(11) NOT NULL," +
                "`last_time` date NOT NULL COMMENT '玩家最后一次获得积分的时间'," +
                "`today_point` int(11) NOT NULL COMMENT '玩家今天获得的积分总数'" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        sqlList.add("CREATE TABLE IF NOT EXISTS `"+tablePrefix+"_exp` (" +
                "`player` varchar(128) NOT NULL, UNIQUE KEY (`player`)," +
                "`exp` int(11) NOT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        PreparedStatement ps = null;
        for(String sql : sqlList) {
            ps = conn.prepareStatement(sql);
            ps.execute();
        }
        ps.close();
    }

}
