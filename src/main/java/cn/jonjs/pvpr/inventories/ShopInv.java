package cn.jonjs.pvpr.inventories;

import cn.jonjs.jonapi.utils.MessageUtils;
import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.ShopData;
import cn.jonjs.pvpr.viaversion.ViaVersion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Set;

public class ShopInv {

    static FileConfiguration config = Main.getInst().getConfig();

    /**
     * {}：玻璃板；||：按钮；空白：展示；@@：页数信息
     * {00} {01} {02} {03} {04} {05} {06} {07} {08}
     * {09}  10   11   12   13   14   15   16  {17}
     * {18}  19   20   21   22   23   24   25  {26}
     * {27}  28   29   30   31   32   33   34  {35}
     * {36}  37   38   39   40   41   42   43  {44}
     * {45} {46} {47} |48| @49@ |50| {51} {52} {53}
     */

    public static final int[] glassSlots = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 51, 52, 53};
    // size=23
    public static final int[] btnSlots = new int[]{48, 50};
    // size=2
    public static final int[] infoSlots = new int[]{49};
    // size=1
    public static final int[] itemSlots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
    // size=28

    /**
     * 获取总页数
     * @return 总页数
     */
    public static Integer getPageCount() {
        Set set = ShopData.config.getKeys(false);
        int size = set.size();
        int pageCount = (int) Math.ceil(size / 28) + 1; //神奇的特性：向上取整没用，要+1
        return pageCount;
    }

    public static int getIndex(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1; //如果未找到返回-1
    }


    /**
     * 加载GUI界面
     * @param pageNow 指定页数
     * @return GUI对应的Inventory
     */
    public static Inventory generate(Integer pageNow) {
        String title_shop = MessageUtils.color(config.getString("Settings.GUI-Title.Shop"));
        Inventory inv = Bukkit.createInventory(null, 54, title_shop);
        /** glass **/
        for (int i = 0; i < glassSlots.length; i++) { //循环i=0~22
            inv.setItem(glassSlots[i], ViaVersion.getGlassPane(0));
        }
        /** glass **/
        /** btn **/
        ItemStack btn1 = new ItemStack(Material.ARROW);
        ItemMeta meta1 = btn1.getItemMeta();
        meta1.setDisplayName(MessageUtils.color("&f上一页"));
        btn1.setItemMeta(meta1);
        ItemStack btn2 = new ItemStack(Material.ARROW);
        ItemMeta meta2 = btn2.getItemMeta();
        meta2.setDisplayName(MessageUtils.color("&f下一页"));
        btn2.setItemMeta(meta2);
        inv.setItem(btnSlots[0], btn1);
        inv.setItem(btnSlots[1], btn2);
        /** btn **/
        /** info **/
        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta meta = info.getItemMeta();
        meta.setDisplayName(pageNow + "/" + getPageCount());
        info.setItemMeta(meta);
        inv.setItem(infoSlots[0], info);
        /** info **/
        /** item **/
        Set set = ShopData.config.getKeys(false);
        int size = set.size();
        if(size != 0) {
            ArrayList<Object> list = new ArrayList(set);
            int pageCount = (int) Math.ceil(size / 28) + 1; //神奇的特性：向上取整没用，要+1

            int startIndex, endIndex;
            if (pageNow == pageCount) { //最后一页
                startIndex = (pageCount - 1) * 28; //第2页开始处数字索引为28
                endIndex = size - 1;
            } else { //不是最后一页
                startIndex = (pageNow - 1) * 28; //开始的数组索引序号，0起
                endIndex = pageNow * 28 - 1;
            }
            for (int index = startIndex; index <= endIndex; index++) { // start~end (E.G. 0~27)
                String obj = (String) list.get(index);
                ItemStack item = Data.getItem(obj);
                inv.setItem(itemSlots[index - (pageNow - 1) * 28], item);
            }
        }

        /** item **/
        return inv;
    }

}
