package cn.jonjs.pvpr.listeners;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.handlers.Anti;
import cn.jonjs.pvpr.handlers.MsgSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class PVPRPlayerDeathEvent implements Listener {

    FileConfiguration config = Main.getInst().getConfig();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        Player playerWhoDied = e.getEntity();
        List<String> worlds = config.getStringList("Settings.PVP.Enable-World");

        if (playerWhoDied.getLastDamageCause() != null
                && playerWhoDied.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) playerWhoDied.getLastDamageCause();
            Entity damager = event.getDamager();
            if (damager.getType().equals(EntityType.PLAYER)) {
                Player killer = (Player) damager;

                // killer 不在开启的世界
                if( ! worlds.contains(killer.getWorld().getName())) {
                    return;
                }

                int point = config.getInt("Settings.PVP.Point-Per-Player", 0);
                int exp = config.getInt("Settings.PVP.Exp-Per-Player", 0);
                Data.addPoint(killer.getName(), point);
                Data.addExp(killer.getName(), exp);
                MsgSender.sendNormally(killer, config.getString("Messages.PVP-Killed-Player")
                        .replace("{point}", "" + point)
                        .replace("{exp}", "" + exp)
                        .replace("{dead}", playerWhoDied.getName())
                        .replace("{killer}", killer.getName()));

                boolean antiEnabled = config.getBoolean("Settings.Anti.Enable", false);
                if(antiEnabled) {
                    Anti.startAntiCamera(killer);
                }

            }
        }
    }

}
