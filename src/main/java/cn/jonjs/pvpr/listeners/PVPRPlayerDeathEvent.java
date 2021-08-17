package cn.jonjs.pvpr.listeners;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.DataFromSQL;
import cn.jonjs.pvpr.data.Maps;
import cn.jonjs.pvpr.handlers.Anti;
import cn.jonjs.pvpr.handlers.MsgSender;
import cn.jonjs.pvpr.handlers.TimeHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class PVPRPlayerDeathEvent implements Listener {

    FileConfiguration config = Main.getInst().getConfig();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        Player died = e.getEntity();
        List<String> worlds = config.getStringList("Settings.PVP.Enable-World");

        if (died.getLastDamageCause() != null
                && died.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) died.getLastDamageCause();
            Entity damager = event.getDamager();
            if (damager.getType().equals(EntityType.PLAYER)) {
                Player killer = (Player) damager;

                //killer 不在开启的世界
                if( ! worlds.contains(killer.getWorld().getName())) { return; }

                int point;
                List<Integer> pointList = config.getIntegerList("Settings.PVP.Point-On-Kill");
                /** 重复击杀判断 **/
                if( ! Maps.killSameMap.containsKey(killer)) {
                    ArrayList<String> killed = new ArrayList<>();
                    killed.add(died.getName());
                    Maps.killSameMap.put(killer, killed);
                    point = pointList.get(0);
                } else {
                    ArrayList<String> killed = Maps.killSameMap.get(killer);
                    int size = killed.size();
                    String last = killed.get(size - 1);
                    if(last.equals(died.getName())) { //重复击杀
                        killed.add(died.getName());
                        Maps.killSameMap.replace(killer, killed);
                        point = pointList.size() < killed.size() ? pointList.get(pointList.size() - 1) : pointList.get(killed.size() - 1) ;
                    } else { //不重复击杀
                        ArrayList<String> newKilled = new ArrayList<>();
                        newKilled.add(died.getName());
                        Maps.killSameMap.replace(killer, newKilled);
                        point = pointList.get(0);
                    }
                }

                int exp = config.getInt("Settings.PVP.Exp-Per-Player", 0);
                int pointTake = config.getInt("Settings.PVP.Point-Take-On-Death", 0);
                int expTake = config.getInt("Settings.PVP.Exp-Take-On-Death", 0);
                int limit = config.getInt("Settings.PVP.Daily-Limit", -1);
                // 积分、经验结算
                if(TimeHandler.canAdd(killer.getName())) {
                    if (Main.useMySQL) {
                        /** 增加killer PVP积分、经验 **/
                        DataFromSQL.addPoint(killer.getName(), point);
                        DataFromSQL.addToday(killer.getName(), point);
                        DataFromSQL.setLastTime(killer.getName());
                        DataFromSQL.addExp(killer.getName(), exp);
                        /** 扣除died PVP积分 **/
                        if ((DataFromSQL.getPoint(died.getName()) - pointTake < 0)) {
                            DataFromSQL.setPoint(died.getName(), 0);
                        } else {
                            DataFromSQL.takePoint(died.getName(), pointTake);
                        }
                        if ((DataFromSQL.getExp(died.getName()) - expTake < 0)) {
                            DataFromSQL.setExp(died.getName(), 0);
                        } else {
                            DataFromSQL.takeExp(died.getName(), expTake);
                        }
                    } else {
                        /** 增加killer PVP积分、经验 **/
                        Data.addPoint(killer.getName(), point);
                        Data.addToday(killer.getName(), point);
                        Data.updateLastTime(killer.getName());
                        Data.addExp(killer.getName(), exp);
                        /** 扣除died PVP积分 **/
                        if ((Data.getPoint(died.getName()) - pointTake < 0)) {
                            Data.setPoint(died.getName(), 0);
                        } else {
                            Data.removePoint(died.getName(), pointTake);
                        }
                        if ((Data.getExp(died.getName()) - expTake < 0)) {
                            Data.setExp(died.getName(), 0);
                        } else {
                            Data.removeExp(died.getName(), expTake);
                        }
                    }
                    // 消息发送
                    MsgSender.sendNormally(killer, config.getString("Messages.PVP-Killed-Player")
                            .replace("{point}", "" + point)
                            .replace("{exp}", "" + exp)
                            .replace("{dead}", died.getName())
                            .replace("{killer}", killer.getName()));
                    MsgSender.sendNormally(died, config.getString("Messages.PVP-Player-Died")
                            .replace("{point}", "" + pointTake)
                            .replace("{exp}", "" + expTake)
                            .replace("{killer}", killer.getName()));
                } else {
                    MsgSender.sendNormally(killer, config.getString("Messages.PVP-Point-Daily-Limited")
                            .replace("{limit}", "" + limit));
                }

                //防刷分机制
                boolean antiEnabled = config.getBoolean("Settings.Anti.Enable", false);
                if(antiEnabled) { Anti.startAntiCamera(killer); }

            }
        }
    }

}
