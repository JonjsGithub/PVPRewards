package cn.jonjs.pvpr.listeners;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.handlers.Rank;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PVPRPlayerChatEvent implements Listener {

    FileConfiguration config = Main.getInst().getConfig();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();

        String rankDisplayName = Rank.getRankDisplayName(Rank.getRank(p.getName()));
        e.setFormat(config.getString("Settings.Rank.Chat-Format")
                .replace("{rank}", rankDisplayName)
                .replace("{chat}", "§r" + e.getFormat()));



    }

}
