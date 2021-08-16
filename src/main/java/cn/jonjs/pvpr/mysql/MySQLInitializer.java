package cn.jonjs.pvpr.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLInitializer {

    private Connection conn;
    private String tablePrefix = "";

    public MySQLInitializer(Connection connection) {
        conn = connection;
        tablePrefix = MySQLConnector.PREFIX;
    }

    public void initTables() throws SQLException {
        ArrayList<String> sqlList = new ArrayList<>();
        sqlList.add("SET NAMES utf-8;");
        sqlList.add("CREATE TABLE IF NOT EXISTS `"+tablePrefix+"_point` (" +
                " `player` varchar(128) NOT NULL," +
                " `point` int(11) NOT NULL," +
                " `last_time` datetime NOT NULL COMMENT '玩家最后一次获得积分的时间'," +
                " `today_point` int(11) NOT NULL COMMENT '玩家今天获得的积分总数'" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;");
        sqlList.add("CREATE TABLE IF NOT EXISTS `"+tablePrefix+"_exp` (" +
                " `player` varchar(128) NOT NULL," +
                " `exp` int(11) NOT NULL" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;");
        sqlList.add("CREATE TABLE IF NOT EXISTS `"+tablePrefix+"_rank` (" +
                " `key_name` varchar(128) NOT NULL COMMENT '配置文件中所对应的键名'," +
                " `exp` int(11) NOT NULL," +
                " `display_name` text NOT NULL COMMENT '色彩代码为&'" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;");
        sqlList.add("CREATE TABLE IF NOT EXISTS `"+tablePrefix+"_shop` (" +
                " `edit_name` varchar(128) NOT NULL COMMENT '商品编辑名'," +
                " `itemstack` blob NOT NULL COMMENT '商品ItemStack(序列化后)'," +
                " `price` int(11) NOT NULL COMMENT '消耗积分数'," +
                " `discount` double NOT NULL COMMENT '折扣小数'," +
                " `exp` int(11) NOT NULL COMMENT '所需经验值'," +
                " `count` int(11) NOT NULL COMMENT '每位玩家的购买次数上限'" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;");
        sqlList.add("CREATE TABLE `"+tablePrefix+"_count` (" +
                " `player` varchar(128) NOT NULL COMMENT '[不唯一]'," +
                " `edit_name` varchar(128) NOT NULL COMMENT '商品编辑名'," +
                " `count` int(11) NOT NULL COMMENT '对应次数'" +
                ") ENGINE=MyISAM DEFAULT CHARSET=utf8;");
        sqlList.add("ALTER TABLE `"+tablePrefix+"_point` ADD UNIQUE( `player`);"); //唯一. 设置后, insert = update
        StringBuilder sql = new StringBuilder();
        for(String sqlSingle : sqlList) {
            sql.append(sqlSingle);
        }
        PreparedStatement ps;
        ps = conn.prepareStatement(sql.toString());
        ps.execute();
        ps.close();
    }

}
