package cn.jonjs.pvpr.listeners;

import cn.jonjs.jonapi.utils.MessageUtils;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.Maps;
import cn.jonjs.pvpr.data.PointData;
import cn.jonjs.pvpr.handlers.MsgSender;
import cn.jonjs.pvpr.handlers.UpdateChecker;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PVPRPlayerJoinEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent e) {

        Player p = e.getPlayer();

        if( ! PointData.config.isSet(p.getName())) {
            MsgSender.sendFromKey(p, "Messages.Creating-Data");
            Data.setPoint(p.getName(), 0);
            Data.setExp(p.getName(), 0);
            Maps.setPage(p.getName(), 1);
            MsgSender.sendFromKey(p, "Messages.Created-Data");
        } else {
            MsgSender.sendFromKey(p, "Messages.Loading-Data");
            Maps.setPage(p.getName(), 1);
            MsgSender.sendFromKey(p, "Messages.Loaded-Data");
        }

        if(p.isOp()) {
            if(UpdateChecker.hasNewerVersion()) {
                MessageUtils.sendMessage(p, " ");
                MessageUtils.sendMessage(p, "  &2&l[!]  &b&lPVP&3&lRewards &c&l发现新版本!  &2&l[!]");
                MessageUtils.sendMessage(p, "  &6当前版本: &e" + UpdateChecker.getThisVersion());
                MessageUtils.sendMessage(p, "  &6最新版本: &b&l" + UpdateChecker.getWebVersion());
                MessageUtils.sendJSONMessage(
                        p,
                        "  &d&l[MCBBS]",
                        ClickEvent.Action.OPEN_URL,
                        "https://www.mcbbs.net/thread-810285-1-1.html"
                );
                MessageUtils.sendMessage(p, " ");
            }
        }

    }

}
