package cn.jonjs.pvpr.handlers;

import cn.jonjs.jonapi.utils.MessageUtils;
import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Maps;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Anti {

    static FileConfiguration config = Main.getInst().getConfig();

    public static void startAntiCamera(Player p) {
        if (Maps.getAntiTaskID(p) == -1) { //无刷分监视任务
            int coolDown = Maps.coolDownMap.getOrDefault("Settings.Anti.Time", 15);
            int id = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInst(), new Runnable() {
                @Override
                public void run() {

                    if( ! Maps.coolDownMap.containsKey(p)) {
                        Maps.coolDownMap.put(p, coolDown);
                    } else {
                        Maps.coolDownMap.replace(p, Maps.coolDownMap.get(p) - 1);
                    }

                    MsgSender.sendNormally(p, config.getString("Messages.PVP-Anti-Timer")
                            .replace("{left}", "" + Maps.coolDownMap.get(p)));

                    if(Maps.coolDownMap.get(p) == 0) {
                        clearAnti(p);
                    }

                }
            }, 0, 20).getTaskId();
            Maps.setAntiTaskID(p, id);
        } else { //在刷分监视中仍击杀了玩家
            clearAnti(p);
            punish(p);
        }
    }

    public static void clearAnti(Player p) {
        Bukkit.getScheduler().cancelTask(Maps.getAntiTaskID(p));
        Maps.coolDownMap.remove(p);
        Maps.taskIDMap.remove(p);
    }

    public static void punish(Player p) {
        String type = config.getString("Settings.Anti.Punishment.Type", "NONE");
        String value = config.getString("Settings.Anti.Punishment.Value", "NONE");
        if(type.equalsIgnoreCase("NONE")) {
            return;
        }
        if(type.equalsIgnoreCase("KICK")) {
            p.kickPlayer(MessageUtils.color(value));
        }
        if(type.equalsIgnoreCase("MESSAGE")) {
            MsgSender.sendNormally(p, value);
        }
    }


}
