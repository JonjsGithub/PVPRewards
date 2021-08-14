package cn.jonjs.pvpr.inventories;

import cn.jonjs.jonapi.utils.MessageUtils;
import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.CountData;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.Maps;
import cn.jonjs.pvpr.viaversion.ViaVersion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class InfoInv {

    static FileConfiguration config = Main.getInst().getConfig();

    public static Inventory generate(Player p, String editName) {

        Maps.setBuying(p.getName(), editName);

        if( ! CountData.config.isSet(p.getName() + "." + editName)) {
            Data.setUserCount(p.getName(), editName, Data.getCount(editName));
        }

        String title_itemeditor = MessageUtils.color(config.getString("Settings.GUI-Title.Info", "&2> &0&n物品购买"));
        Inventory inv = Bukkit.createInventory(null, 18, title_itemeditor);
        ItemStack item = Data.getItem(editName);
        inv.setItem(4, item);

        int originalPrice = Data.getPrice(editName);
        String discount_str = String.valueOf(Data.getDiscount(editName) * 10); //8折、8.8折
        int afterPrice = Data.getNewPrice(editName);
        int needExp = Data.getNeedExp(editName);
        int userCount = Data.getUserCount(p.getName(), editName);

        ItemStack btn1 = ViaVersion.getGlassPane(13); //GREEN
        ItemMeta meta1 = btn1.getItemMeta();
        meta1.setDisplayName(MessageUtils.color("&a&l购买"));
        btn1.setItemMeta(meta1);
        inv.setItem(12, btn1);

        ItemStack btn2 = ViaVersion.getGlassPane(14); //RED
        ItemMeta meta2 = btn2.getItemMeta();
        meta2.setDisplayName(MessageUtils.color("&c&l返回"));
        btn2.setItemMeta(meta2);
        inv.setItem(14, btn2);

        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta meta3 = info.getItemMeta();
        meta3.setDisplayName(MessageUtils.color("&e&l商品信息"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(MessageUtils.color("&7------------------------"));
        lore.add(MessageUtils.color("&6所需PVP积分: &e&l" + afterPrice + "&7   (&m"+originalPrice+"&7, "+discount_str+"折)"));
        lore.add(MessageUtils.color("&3所需PVP经验: &b&l" + needExp));
        lore.add(MessageUtils.color(("&3剩余购买次数: &c" + userCount + "次")));
        lore.add(MessageUtils.color("&7------------------------"));
        meta3.setLore(lore);
        info.setItemMeta(meta3);
        inv.setItem(13, info);

        return inv;

    }

}
