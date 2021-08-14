package cn.jonjs.pvpr.listeners;

import cn.jonjs.jonapi.utils.GameVersionUtils;
import cn.jonjs.jonapi.utils.MessageUtils;
import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.admininventories.AdminShopInv;
import cn.jonjs.pvpr.admininventories.ItemEditInv;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.Maps;
import cn.jonjs.pvpr.data.ShopData;
import cn.jonjs.pvpr.handlers.MsgSender;
import cn.jonjs.pvpr.inventories.InfoInv;
import cn.jonjs.pvpr.inventories.ShopInv;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Set;

public class PVPRInventoryClickEvent implements Listener {

    static FileConfiguration config = Main.getInst().getConfig();

    @EventHandler
    public void onInvClick(org.bukkit.event.inventory.InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if(e.getClickedInventory().getItem(e.getSlot()) == null) {
            return;
        }

        String title = "";
        if(GameVersionUtils.is_1_12_or_older()) {
            title = e.getClickedInventory().getTitle();
        } else {
            title = p.getOpenInventory().getTitle();
        }

        String title_shop = MessageUtils.color(config.getString("Settings.GUI-Title.Shop", "&2> &0&n积分商城"));
        String title_info = MessageUtils.color(config.getString("Settings.GUI-Title.Info", "&2> &0&n物品购买"));
        String title_adminshop = MessageUtils.color(config.getString("Settings.GUI-Title.AdminShop", "&4> &0&n积分商城: 管理员模式"));
        String title_itemeditor = MessageUtils.color(config.getString("Settings.GUI-Title.ItemEditor", "&4> &0&n商品设置"));

        /** 编辑菜单 **/
        if (title.equals(title_itemeditor)) {
            e.setCancelled(true);
            if (e.getSlot() == 8) {
                p.closeInventory();
                String editName = Maps.getEditing(p.getName());
                MessageUtils.sendMessage(p, " ");
                MessageUtils.sendMessage(p, "&b&lPVP&3&lRewards   &c&o商城物品编辑器");
                MessageUtils.sendJSONMessage(
                        p,
                        "&e&l[修改]   &3编辑名: &b" + editName,
                        ClickEvent.Action.SUGGEST_COMMAND,
                        "/pvpr admin shop setEditName " + editName + " 新编辑名"
                );
                MessageUtils.sendJSONMessage(
                        p,
                        "&e&l[修改]   &3原所需PVP积分: &b" + Data.getPrice(editName),
                        ClickEvent.Action.SUGGEST_COMMAND,
                        "/pvpr admin shop setPrice " + editName + " 新价格"
                );
                MessageUtils.sendJSONMessage(
                        p,
                        "&e&l[修改]   &3折扣: &b" + Data.getDiscount(editName) + " &7("+(Data.getDiscount(editName)*10)+"折)",
                        ClickEvent.Action.SUGGEST_COMMAND,
                        "/pvpr admin shop setDiscount " + editName + " 新折扣"
                );
                MessageUtils.sendMessage(
                        p,
                        "&e&l&m[修改]&3   现所需PVP积分: &b" + Data.getNewPrice(editName)
                );
                MessageUtils.sendJSONMessage(
                        p,
                        "&e&l[修改]   &3所需PVP经验: &b" + Data.getNeedExp(editName),
                        ClickEvent.Action.SUGGEST_COMMAND,
                        "/pvpr admin shop setExp " + editName + " 新经验值"
                );
                MessageUtils.sendJSONMessage(
                        p,
                        "&e&l[修改]   &3购买次数上限: &b" + Data.getCount(editName),
                        ClickEvent.Action.SUGGEST_COMMAND,
                        "/pvpr admin shop setCount " + editName + " 新次数"
                );
                MessageUtils.sendJSONMessage(
                        p,
                        "&d&l[修改商品为手中物品]",
                        ClickEvent.Action.RUN_COMMAND,
                        "/pvpr admin shop setItem " + editName
                );
                MessageUtils.sendJSONMessage(
                        p,
                        "&c&l[删除该商品]",
                        ClickEvent.Action.SUGGEST_COMMAND,
                        "/pvpr admin shop remove " + editName
                );
            }
        }

        /** 商城首页 **/
        if (title.equals(title_shop)) {
            e.setCancelled(true);
            /** 按钮 **/
            if (e.getSlot() == ShopInv.btnSlots[0]) { //上一页
                int 总页数 = ShopInv.getPageCount();
                int 现在页数 = Maps.getPage(p.getName());
                if (现在页数 == 1) {
                    p.sendMessage("已经是第一页了");
                } else {
                    现在页数--;
                    Maps.setPage(p.getName(), 现在页数);
                    p.closeInventory();
                    p.openInventory(ShopInv.generate(现在页数));
                    p.sendMessage("已经帮您切换到: " + 现在页数);
                }
            }
            if (e.getSlot() == ShopInv.btnSlots[1]) { //下一页
                int 总页数 = ShopInv.getPageCount();
                int 现在页数 = Maps.getPage(p.getName());
                if (现在页数 == 总页数) {
                    p.sendMessage("已经是最后一页了");
                } else {
                    现在页数++;
                    Maps.setPage(p.getName(), 现在页数);
                    p.closeInventory();
                    p.openInventory(ShopInv.generate(现在页数));
                    p.sendMessage("已帮您切换到: " + 现在页数);
                }
            }

            /** 商品列表 **/
            int index = ShopInv.getIndex(ShopInv.itemSlots, e.getSlot());
            if (index != -1) {
                Set set = ShopData.config.getKeys(false);
                ArrayList<Object> list = new ArrayList(set);
                int pageNow = Maps.getPage(p.getName());
                int indexInKeysList = (pageNow - 1) * 28 + index;
                if (list.get(indexInKeysList) instanceof String) {
                    String editName = (String) list.get(indexInKeysList);
                    Inventory infoInv = InfoInv.generate(p, editName);
                    p.closeInventory();
                    p.openInventory(infoInv);
                }
            }

        }

        /** 商品购买 **/
        if (title.equals(title_info)) {
            e.setCancelled(true);
            if (e.getSlot() == 12) { //购买
                String buying = Maps.getBuying(p.getName());
                int playerPoint = Data.getPoint(p.getName());
                int playerExp = Data.getExp(p.getName());
                int price = Data.getNewPrice(buying);
                int exp = Data.getNeedExp(buying);
                int userCount = Data.getUserCount(p.getName(), buying);

                if (playerExp < exp) { //经验值不足
                    MsgSender.sendNormally(p, config.getString("Messages.Player-Not-Enough-Exp")
                            .replace("{need}", "" + exp)
                            .replace("{have}", "" + playerExp));
                } else if (userCount == 0) { //次数不足 (count == 0)
                    MsgSender.sendFromKey(p, "Messages.Player-Not-Enough-Count");
                } else if (playerPoint < price) { //积分不足
                    MsgSender.sendNormally(p, config.getString("Messages.Player-Not-Enough-Point")
                            .replace("{need}", "" + price)
                            .replace("{have}", "" + playerPoint));
                } else { //正常购买
                    if(Data.getCount(buying) == -1) { //无限次数
                        Data.removePoint(p.getName(), price);
                        p.getInventory().addItem(Data.getItem(buying));
                        MsgSender.sendNormally(p, config.getString("Messages.Player-Bought-Item")
                                .replace("{cost}", "" + price)
                                .replace("{left}", "" + Data.getPoint(p.getName())));
                    } else { //次数有限
                        Data.removePoint(p.getName(), price);
                        //次数减少
                        Data.setUserCount(p.getName(), buying, Data.getUserCount(p.getName(), buying) - 1);
                        p.getInventory().addItem(Data.getItem(buying));
                        p.closeInventory();
                        p.openInventory(InfoInv.generate(p, buying)); //更新inv
                        MsgSender.sendNormally(p, config.getString("Messages.Player-Bought-Item")
                                .replace("{cost}", "" + price)
                                .replace("{left}", "" + Data.getPoint(p.getName())));
                    }
                }
            }

            if(e.getSlot() == 14) { //返回
                p.closeInventory();
                p.chat("/pvpr shop");
            }

        }

        /** 管理员商城首页 **/
        if (title.equals(title_adminshop)) {
            e.setCancelled(true);
            /** 按钮 **/
            if (e.getSlot() == AdminShopInv.btnSlots[0]) { //上一页
                int 总页数 = AdminShopInv.getPageCount();
                int 现在页数 = Maps.getPage(p.getName());
                if (现在页数 == 1) {
                    p.sendMessage("已经是第一页了");
                } else {
                    现在页数--;
                    Maps.setPage(p.getName(), 现在页数);
                    p.closeInventory();
                    p.openInventory(AdminShopInv.generate(现在页数));
                    p.sendMessage("已经帮您切换到: " + 现在页数);
                }
            }
            if (e.getSlot() == ShopInv.btnSlots[1]) { //下一页
                int 总页数 = AdminShopInv.getPageCount();
                int 现在页数 = Maps.getPage(p.getName());
                if (现在页数 == 总页数) {
                    p.sendMessage("已经是最后一页了");
                } else {
                    现在页数++;
                    Maps.setPage(p.getName(), 现在页数);
                    p.closeInventory();
                    p.openInventory(AdminShopInv.generate(现在页数));
                    p.sendMessage("已帮您切换到: " + 现在页数);
                }
            }

            /** 商品列表 **/
            int index = ShopInv.getIndex(ShopInv.itemSlots, e.getSlot());
            if (index != -1) {
                Set set = ShopData.config.getKeys(false);
                ArrayList<Object> list = new ArrayList(set);
                int pageNow = Maps.getPage(p.getName());
                int indexInKeysList = (pageNow - 1) * 28 + index;
                if (list.get(indexInKeysList) instanceof String) {
                    String editName = (String) list.get(indexInKeysList);
                    Inventory infoInv = ItemEditInv.generate(editName);
                    p.closeInventory();
                    p.openInventory(infoInv);
                }
            }

        }

    }

}
