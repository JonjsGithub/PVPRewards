package cn.jonjs.pvpr.data;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;

public class Data {

    public static int getTodayPoint(String name) {
        return PointData.config.getInt(name + ".today", 0);
    }

    public static int getPoint(String name) {
        return PointData.config.getInt(name + ".point", 0);
    }
    public static void addPoint(String name, int amount) {
        int a = PointData.config.getInt(name + ".point", 0);
        PointData.config.set(name, a + amount);
        PointData.save();
    }
    public static void removePoint(String name, int amount) {
        int a = PointData.config.getInt(name + ".point", 0);
        PointData.config.set(name, a - amount);
        PointData.save();
    }
    public static void setPoint(String name, int amount) {
        PointData.config.set(name + ".point", amount);
        PointData.save();
    }

    public static int getExp(String name) {
        return ExpData.config.getInt(name, 0);
    }
    public static void addExp(String name, int amount) {
        int a = ExpData.config.getInt(name, 0);
        ExpData.config.set(name, a + amount);
        ExpData.save();
    }
    public static void removeExp(String name, int amount) {
        int a = ExpData.config.getInt(name, 0);
        ExpData.config.set(name, a - amount);
        ExpData.save();
    }
    public static void setExp(String name, int amount) {
        ExpData.config.set(name, amount);
        ExpData.save();
    }

    public static ItemStack getItem(String editName) {
        return ShopData.config.getItemStack(editName + ".itemStack", null);
    }
    public static void setItem(ItemStack itemStack, String editName, int price, double discount, int exp, int count) {
        ShopData.config.set(editName + ".itemStack", itemStack);
        ShopData.config.set(editName + ".price", price);
        discount = Double.parseDouble(String.format("%.2f", discount));
        ShopData.config.set(editName + ".discount", discount);
        ShopData.config.set(editName + ".exp", exp);
        ShopData.config.set(editName + ".count", count);
        ShopData.save();
    }
    public static void removeItem(String editName) {
        ShopData.config.set(editName, null);
        ShopData.save();
    }

    public static int getPrice(String editName) {
        return ShopData.config.getInt(editName + ".price", 99999999);
    }
    public static void setPrice(String editName, int price) {
        ShopData.config.set(editName + ".price", price);
        ShopData.save();
    }

    public static double getDiscount(String editName) {
        return ShopData.config.getDouble(editName + ".discount", 1.0);
    }
    public static void setDiscount(String editName, double discount) {
        discount = Double.parseDouble(String.format("%.2f", discount));
        ShopData.config.set(editName + ".discount", discount);
        ShopData.save();
    }

    public static int getNewPrice(String editName) {
        int price = getPrice(editName);
        double discount = getDiscount(editName);
        return (int) Math.round(price * discount);
    }

    public static int getCount(String editName) {
        return ShopData.config.getInt(editName + ".count", 0);
    }
    public static void setCount(String editName, int count) {
        ShopData.config.set(editName + ".count", count);
        ShopData.save();
    }

    public static int getUserCount(String pn, String editName) {
        return CountData.config.getInt(pn + "." + editName, 0);
    }
    public static void setUserCount(String pn, String editName, int newCount) {
        CountData.config.set(pn + "." + editName, newCount);
        CountData.save();
    }

    public static void setEditName(String older, String newer) {
        ItemStack olderData_1 = ShopData.config.getItemStack(older + ".itemStack");
        ShopData.config.set(newer + ".itemStack", olderData_1);
        int olderData_2 = ShopData.config.getInt(older + ".price");
        ShopData.config.set(newer + ".price", olderData_2);
        double olderData_3 = ShopData.config.getDouble(older + ".discount");
        ShopData.config.set(newer + ".discount", olderData_3);
        int olderData_4 = ShopData.config.getInt(older + ".exp");
        ShopData.config.set(newer + ".exp", olderData_4);
        int olderData_5 = ShopData.config.getInt(older + ".count");
        ShopData.config.set(newer + ".count", olderData_5);
        ShopData.config.set(older, null);
        ShopData.save();

        Set set = CountData.config.getKeys(false);
        ArrayList<Object> list = new ArrayList<>(set);
        for (Object playerName : list) {
            if(playerName instanceof String) {
                String pn = (String) playerName;
                int oldCount = Data.getUserCount(pn, older);
                CountData.config.set(pn + "." + newer, oldCount);
                CountData.config.set(pn + "." + older, null);
                CountData.save();
            }
        }

    }

    public static int getNeedExp(String editName) {
        return ShopData.config.getInt(editName + ".exp", 0);
    }
    public static void setNeedExp(String editName, int exp) {
        ShopData.config.set(editName + ".exp", exp);
    }


}
