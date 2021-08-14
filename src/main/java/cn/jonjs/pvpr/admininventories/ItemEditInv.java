package cn.jonjs.pvpr.admininventories;

import cn.jonjs.jonapi.utils.MessageUtils;
import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemEditInv {

    static FileConfiguration config = Main.getInst().getConfig();

    public static Inventory generate(String editName) {
        String title_itemeditor = MessageUtils.color(config.getString("Settings.GUI-Title.ItemEditor", "&4> &0&n商品设置"));
        Inventory inv = Bukkit.createInventory(null, 9, title_itemeditor);
        ItemStack item = Data.getItem(editName);
        inv.setItem(0, item);

        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta meta = info.getItemMeta();
        meta.setDisplayName(MessageUtils.color("&e&l商品信息"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(MessageUtils.color("&3所需PVP积分: &e" + Data.getPrice(editName)));
        lore.add(MessageUtils.color("&3折扣: &b"+Data.getDiscount(editName)+" &f(" + (Data.getDiscount(editName) * 10) + "折)"));
        lore.add(MessageUtils.color("&3折后所需PVP积分: &e&l" + Data.getNewPrice(editName)));
        lore.add(" ");
        lore.add(MessageUtils.color("&3所需PVP经验: &e&l" + Data.getNeedExp(editName)));
        lore.add(MessageUtils.color("&3购买次数上限: &b" + Data.getCount(editName)));
        meta.setLore(lore);
        info.setItemMeta(meta);
        inv.setItem(7, info);

        ItemStack edit = new ItemStack(Material.IRON_AXE);
        ItemMeta im2 = edit.getItemMeta();
        im2.setDisplayName(MessageUtils.color("&c&l管理"));
        edit.setItemMeta(im2);
        inv.setItem(8, edit);
        return inv;
    }

}
