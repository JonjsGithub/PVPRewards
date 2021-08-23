package cn.jonjs.pvpr.listeners;

import cn.jonjs.jonapi.utils.MessageUtils;
import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.DataFromSQL;
import cn.jonjs.pvpr.data.Maps;
import cn.jonjs.pvpr.data.PointData;
import cn.jonjs.pvpr.handlers.MsgSender;
import cn.jonjs.pvpr.handlers.UpdateChecker;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.zip.DataFormatException;

public class PVPRPlayerJoinEvent implements Listener {

    FileConfiguration config = Main.getInst().getConfig();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent e) {

        Player p = e.getPlayer();

        if( ! PointData.config.isSet(p.getName())) {
            MsgSender.sendFromKey(p, "Messages.Creating-Data");
            if(Main.useMySQL) {
                DataFromSQL.setPoint(p.getName(), 0);
                DataFromSQL.setToday(p.getName(), 0);
                DataFromSQL.setLastTime(p.getName());
                DataFromSQL.setExp(p.getName(), 0);
            } else {
                Data.setPoint(p.getName(), 0);
                Data.setToday(p.getName(), 0);
                Data.updateLastTime(p.getName());
                Data.setExp(p.getName(), 0);
            }
            Maps.setPage(p.getName(), 1);
            MsgSender.sendFromKey(p, "Messages.Created-Data");
        } else {
            MsgSender.sendFromKey(p, "Messages.Loading-Data");
            Maps.setPage(p.getName(), 1);
            MsgSender.sendFromKey(p, "Messages.Loaded-Data");
        }

        Maps.setPvpStatus(p, true);
        String status = Maps.getPVPStatus(p) ? "开启" : "关闭";
        MsgSender.sendNormally(p, config.getString("Messages.PVP-Toggled")
                .replace("{status}", status));

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
