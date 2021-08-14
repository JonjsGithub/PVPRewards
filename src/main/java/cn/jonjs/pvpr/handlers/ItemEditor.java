package cn.jonjs.pvpr.handlers;

import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.Maps;
import cn.jonjs.pvpr.admininventories.ItemEditInv;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemEditor {

    static FileConfiguration config = Main.getInst().getConfig();

    public static void changeEditName(Player editingPlayer, String oldEditName, String newEditName) {
        if (Data.getItem(oldEditName) == null) {
            MsgSender.sendNormally(editingPlayer, config.getString("Messages.Item-Not-Found")
                    .replace("{editname}", oldEditName));
        } else {
            if (Data.getItem(newEditName) != null) {
                MsgSender.sendNormally(editingPlayer, config.getString("Messages.Item-Exists")
                        .replace("{editname}", newEditName));
            } else {
                Maps.setEditing(editingPlayer.getName(), newEditName);
                Data.setEditName(oldEditName, newEditName);
                MsgSender.sendNormally(editingPlayer, config.getString("Messages.Item-EditName-Changed")
                        .replace("{old}", oldEditName)
                        .replace("{new}", newEditName));
                Inventory inv = ItemEditInv.generate(newEditName);
                editingPlayer.closeInventory();
                editingPlayer.openInventory(inv);
            }
        }
    }

    public static void changePrice(Player p, String editName, int price) {
        if(Data.getItem(editName) == null) {
            MsgSender.sendNormally(p, config.getString("Messages.Item-Not-Found")
                    .replace("{editname}", editName));
        } else {
            Maps.setEditing(p.getName(), editName);
            Data.setPrice(editName, price);
            MsgSender.sendNormally(p, config.getString("Messages.Item-Price-Changed")
                    .replace("{old}", editName)
                    .replace("{new}", "" + price));
            Inventory inv = ItemEditInv.generate(editName);
            p.closeInventory();
            p.openInventory(inv);
        }
    }

    public static void changeDiscount(Player p, String editName, double discount) {
        if(Data.getItem(editName) == null) {
            MsgSender.sendNormally(p, config.getString("Messages.Item-Not-Found")
                    .replace("{editname}", editName));
        } else {
            Maps.setEditing(p.getName(), editName);
            Data.setDiscount(editName, discount);
            MsgSender.sendNormally(p, config.getString("Messages.Item-Discount-Changed")
                    .replace("{old}", editName)
                    .replace("{new}", "" + discount));
            Inventory inv = ItemEditInv.generate(editName);
            p.closeInventory();
            p.openInventory(inv);
        }
    }

    public static void changeExp(Player p, String editName, int exp) {
        if(Data.getItem(editName) == null) {
            MsgSender.sendNormally(p, config.getString("Messages.Item-Not-Found")
                    .replace("{editname}", editName));
        } else {
            Maps.setEditing(p.getName(), editName);
            Data.setNeedExp(editName, exp);
            MsgSender.sendNormally(p, config.getString("Messages.Item-Exp-Changed")
                    .replace("{old}", editName)
                    .replace("{new}", "" + exp));
            Inventory inv = ItemEditInv.generate(editName);
            p.closeInventory();
            p.openInventory(inv);
        }
    }

    public static void changeCount(Player p, String editName, int count) {
        if(Data.getItem(editName) == null) {
            MsgSender.sendNormally(p, config.getString("Messages.Item-Not-Found")
                    .replace("{editname}", editName));
        } else {
            Maps.setEditing(p.getName(), editName);
            Data.setCount(editName, count);
            MsgSender.sendNormally(p, config.getString("Messages.Item-Count-Changed")
                    .replace("{old}", editName)
                    .replace("{new}", "" + count));
            Inventory inv = ItemEditInv.generate(editName);
            p.closeInventory();
            p.openInventory(inv);
        }
    }

    public static void changeItemStack(Player p, String editName, ItemStack item) {
        if(Data.getItem(editName) == null) {
            MsgSender.sendNormally(p, config.getString("Messages.Item-Not-Found")
                    .replace("{editname}", editName));
        } else {
            if(item.getType() == Material.AIR) {
                MsgSender.sendFromKey(p, "Messages.Item-Is-Air");
            } else {
                Maps.setEditing(p.getName(), editName);
                Data.setItem(item, editName, Data.getPrice(editName), Data.getDiscount(editName), Data.getExp(editName), Data.getCount(editName));
                MsgSender.sendNormally(p, config.getString("Messages.Item-Stack-Changed")
                        .replace("{new}", Data.getItem(editName).getType().toString()));
                Inventory inv = ItemEditInv.generate(editName);
                p.closeInventory();
                p.openInventory(inv);
            }
        }
    }

}
