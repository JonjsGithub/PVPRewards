package cn.jonjs.pvpr.listeners;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Maps;
import cn.jonjs.pvpr.handlers.MsgSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PVPRPlayerDamagePlayerEvent implements Listener {

    FileConfiguration config = Main.getInst().getConfig();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity entity_damager = e.getDamager();
        Entity entity = e.getEntity();
        if( ! (entity_damager instanceof Player)
                || ! (entity instanceof Player)) {
            return;
        }
        Player damager = (Player) entity_damager;
        Player victim = (Player) entity;
        if( ! Maps.getPVPStatus(damager)) {
            MsgSender.sendNormally(damager, config.getString("Messages.PVP-Damager-Disabled"));
            e.setCancelled(true);
        }
        if( ! Maps.getPVPStatus(victim)) {
            MsgSender.sendNormally(damager, config.getString("Messages.PVP-Victim-Disabled"));
            e.setCancelled(true);
        }
    }

}
