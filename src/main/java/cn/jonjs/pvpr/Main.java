package cn.jonjs.pvpr;

import cn.jonjs.jonapi.utils.ArrayUtils;
import cn.jonjs.jonapi.utils.GameVersionUtils;
import cn.jonjs.pvpr.admininventories.AdminShopInv;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.DataFromSQL;
import cn.jonjs.pvpr.data.HologramData;
import cn.jonjs.pvpr.data.Maps;
import cn.jonjs.pvpr.handlers.*;
import cn.jonjs.pvpr.admininventories.ItemEditInv;
import cn.jonjs.pvpr.hookers.PAPIHooker;
import cn.jonjs.pvpr.inventories.ShopInv;
import cn.jonjs.pvpr.listeners.*;
import cn.jonjs.pvpr.metrics.Metrics;
import cn.jonjs.pvpr.mysql.*;
import cn.jonjs.pvpr.viaversion.ViaVersion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {

    public static final int CONFIG_VERSION = 5;
    public static final String[] ALLOW_MC_VERSION = {"19", "18", "17", "16", "15", "14", "13", "12", "11", "10", "9", "8"};
    FileConfiguration config;
    private static Plugin inst;
    public static boolean useMySQL = false;

    public static Plugin getInst() {
        return inst;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        inst = this;
        String ver_second = GameVersionUtils.getBukkitVersion_Second();
        if( ! ArrayUtils.isInArray(ver_second, ALLOW_MC_VERSION)) {
            getLogger().warning("[X] 此插件不支持 " + GameVersionUtils.getBukkitVersion() + " 版本的服务器!");
        }
        if( ! getServer().getPluginManager().getPlugin("JonAPI").getDescription().getVersion().contains("1.0")) {
            getLogger().warning("[X] 前置插件 JonAPI 版本太低! (需要 >= 1.1)");
        }
        PluginTasks.reloadAll();
        config = getConfig();
        getLogger().info("数据文件加载完毕!");

        if(config.getBoolean("Settings.MySQL.Enable", false)) {
            MySQLHelper.processAll();
            getLogger().info("MySQL 数据库处理完成.");
        }

        useMySQL = config.getBoolean("Settings.MySQL.Enable", false);

        getServer().getPluginManager().registerEvents(new PVPRPlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PVPRInventoryClickEvent(), this);
        getServer().getPluginManager().registerEvents(new PVPRPlayerDeathEvent(), this);
        getServer().getPluginManager().registerEvents(new PVPRPlayerChatEvent(), this);
        getLogger().info("事件监听器注册成功!");
        if(config.getInt("Config-Version", -1) != CONFIG_VERSION) {
            ConfigUpdater.update();
            getLogger().info("所有数据文件升级成功! 4 -> 5");
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PAPIHooker papiHooker = new PAPIHooker(this);
            papiHooker.register();
            getLogger().info("PlaceholderAPI 变量注册成功!");
        } else {
            getLogger().warning("PlaceholderAPI 插件未找到, 变量功能无法使用!");
        }
        if (getServer().getPluginManager().getPlugin(config.getString("Settings.Hologram.Plugin")) != null) {
            long rTime = config.getInt("Settings.Hologram.Refresh-Time", 30 ) * 20;
            Bukkit.getScheduler().runTaskTimer(
                    this,
                    new Runnable() {
                        @Override
                        public void run() {
                            RankHolo.update();
                        }
                    },
                    0,
                    rTime
            );
            getLogger().info("全息图加载完毕!");
        } else {
            getLogger().warning("全息图插件未找到, 全息图功能无法使用!");
        }
        int pluginId = 12411; // bStats Plugin ID
        Metrics metrics = new Metrics(this, pluginId);
        getLogger().info("PVPRewards 插件已开启!   BY JONJS2333");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getServer().getScheduler().cancelTasks(this);
        RankHolo.destroy();
        getLogger().info("PVPRewards 插件已关闭!   Goodbye! BY JONJS2333");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //  Command::PVPRS、PVPRSHOP
        if(label.equalsIgnoreCase("pvprs")
                || label.equalsIgnoreCase("pvprshop")) {
            //不为玩家
            if( ! (sender instanceof Player)) {
                sender.sendMessage("PVPR >> 你不能在控制台执行PVPR指令!");
                return true;
            }
            Player p = (Player) sender;

            Inventory inv = ShopInv.generate(Maps.getPage(p.getName()));
            p.closeInventory();
            p.openInventory(inv);

            return true;
        }

        //  Command::PVPRAS、PVPRADMINSHOP
        if(label.equalsIgnoreCase("pvpradminshop")
                || label.equalsIgnoreCase("pvpras")) {
            //不为玩家
            if( ! (sender instanceof Player)) {
                sender.sendMessage("PVPR >> 你不能在控制台执行PVPR指令!");
                return true;
            }

            Player p = (Player) sender;

            //无权限
            if( ! p.hasPermission("pvpr.admin")) {
                MsgSender.sendNormally(p, config.getString("Messages.No-Permission")
                        .replace("{permission}", "pvpr.reload"));
                return true;
            }

            Inventory inv = AdminShopInv.generate(Maps.getPage(p.getName()));
            p.closeInventory();
            p.openInventory(inv);
            return true;
        }

        //  Command::PVPR
        if(label.equalsIgnoreCase("pvpr")) {
            //不为玩家
            if( ! (sender instanceof Player)) {
                sender.sendMessage("PVPR >> 你不能在控制台执行PVPR指令!");
                return true;
            }
            Player p = (Player) sender;

            //PVPR
            if(args.length == 0) {
                return false;
            }

            //PVPR Reload
            if(args[0].equalsIgnoreCase("reload")) {
                if (p.hasPermission("pvpr.reload")) {
                    PluginTasks.reloadAll();
                    MsgSender.sendFromKey(p, "Messages.Reloaded");
                } else {
                    MsgSender.sendNormally(p, config.getString("Messages.No-Permission")
                            .replace("{permission}", "pvpr.reload"));
                }
            }

            //PVPR Help
            if(args[0].equalsIgnoreCase("help")) {
                MsgSender.sendHelpList(p);
                return true;
            }

            //PVPR Point
            if(args[0].equalsIgnoreCase("point")) { //PVPR Point 1:View 2:玩家
                if(args.length != 2 && args.length != 3) {
                    p.sendMessage("/PVPR Point View [玩家]");
                    return true;
                }
                if(args[1].equalsIgnoreCase("view")) {
                    if (args.length == 2) {
                        int point = useMySQL ? DataFromSQL.getPoint(p.getName()) : Data.getPoint(p.getName());
                        MsgSender.sendNormally(p, config.getString("Messages.Point-View")
                                .replace("{player}", p.getName())
                                .replace("{point}", "" + point));
                    } else {
                        String pn = args[2];
                        int point = useMySQL ? DataFromSQL.getPoint(pn) : Data.getPoint(pn);
                        MsgSender.sendNormally(p, config.getString("Messages.Point-View")
                                .replace("{player}", pn)
                                .replace("{point}", "" + point));
                    }
                }
            }

            //PVPR Exp
            if(args[0].equalsIgnoreCase("exp")) { //PVPR Exp 1:View 2:玩家
                if(args.length != 2 && args.length != 3) {
                    p.sendMessage("/PVPR Exp View [玩家]");
                    return true;
                }
                if(args[1].equalsIgnoreCase("view")) {
                    if (args.length == 2) {
                        MsgSender.sendNormally(p, config.getString("Messages.Exp-View")
                                .replace("{player}", p.getName())
                                .replace("{exp}", "" + Data.getExp(p.getName())));
                    } else {
                        String pn = args[2];
                        MsgSender.sendNormally(p, config.getString("Messages.Exp-View")
                                .replace("{player}", pn)
                                .replace("{exp}", "" + Data.getExp(pn)));
                    }
                }
            }

            //PVPR Shop
            if(args[0].equalsIgnoreCase("shop")) {
                Inventory inv = ShopInv.generate(Maps.getPage(p.getName()));
                p.closeInventory();
                p.openInventory(inv);
            }

            //PVPR RankTop
            if(args[0].equalsIgnoreCase("ranktop")) {
                RankTop.sort();
                RankTop.sendRankTop(p);
            }

            //PVPR Rank
            if(args[0].equalsIgnoreCase("rank")) { //PVPR Rank 1:玩家
                if(args.length >= 2) { //别人
                    String pn = args[1];
                    MsgSender.sendNormally(p, config.getString("Messages.Rank-View")
                            .replace("{player}", pn)
                            .replace("{rank}", Rank.getRankDisplayName(Rank.getRank(pn)))
                            .replace("{exp}", "" + Data.getExp(pn)));
                } else {
                    Rank.sendRankList(p);
                    String pn = p.getName();
                    MsgSender.sendNormally(p, config.getString("Messages.Rank-View")
                            .replace("{player}", pn)
                            .replace("{rank}", Rank.getRankDisplayName(Rank.getRank(pn)))
                            .replace("{exp}", "" + Data.getExp(p.getName())));
                }
            }

            //PVPR Admin
            if(args[0].equalsIgnoreCase("admin")) {
                if( ! p.hasPermission("pvpr.admin")) {
                    MsgSender.sendNormally(p, config.getString("Messages.No-Permission")
                            .replace("{permission}", "pvpr.admin"));
                    return true;
                }

                if(args.length == 1) {
                    MsgSender.sendAdminHelpList(p, "main");
                    return true;
                }

                ////PVPR Admin Point
                if (args[1].equalsIgnoreCase("point")) {
                    if(args.length == 2) {
                        MsgSender.sendAdminHelpList(p, "point");
                        return true;
                    } else {
                        String sub = args[2];
                        if(sub.equalsIgnoreCase("add")) {
                            if(args.length != 5) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String name = args[3];
                                int amount = Integer.parseInt(args[4]);
                                Data.addPoint(name, amount);
                                MsgSender.sendNormally(p, config.getString("Messages.Point-Added")
                                        .replace("{amount}", "" + amount)
                                        .replace("{player}", name));
                            }
                        }
                        if(sub.equalsIgnoreCase("remove")) {
                            if(args.length != 5) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String name = args[3];
                                int amount = Integer.parseInt(args[4]);
                                Data.removePoint(name, amount);
                                MsgSender.sendNormally(p, config.getString("Messages.Point-Removed")
                                        .replace("{amount}", "" + amount)
                                        .replace("{player}", name));
                            }
                        }
                        if(sub.equalsIgnoreCase("set")) {
                            if(args.length != 5) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String name = args[3];
                                int amount = Integer.parseInt(args[4]);
                                Data.setPoint(name, amount);
                                MsgSender.sendNormally(p, config.getString("Messages.Point-Set")
                                        .replace("{amount}", "" + amount)
                                        .replace("{player}", name));
                            }
                        }
                    }
                }

                ////PVPR Admin Exp
                if (args[1].equalsIgnoreCase("exp")) {
                    if(args.length == 2) {
                        MsgSender.sendAdminHelpList(p, "exp");
                        return true;
                    } else {
                        String sub = args[2];
                        if(sub.equalsIgnoreCase("add")) {
                            if(args.length != 5) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String name = args[3];
                                int amount = Integer.parseInt(args[4]);
                                Data.addExp(name, amount);
                                MsgSender.sendNormally(p, config.getString("Messages.Exp-Added")
                                        .replace("{amount}", "" + amount)
                                        .replace("{player}", name));
                            }
                        }
                        if(sub.equalsIgnoreCase("remove")) {
                            if(args.length != 5) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String name = args[3];
                                int amount = Integer.parseInt(args[4]);
                                Data.removeExp(name, amount);
                                MsgSender.sendNormally(p, config.getString("Messages.Exp-Removed")
                                        .replace("{amount}", "" + amount)
                                        .replace("{player}", name));
                            }
                        }
                        if(sub.equalsIgnoreCase("set")) {
                            if(args.length != 5) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String name = args[3];
                                int amount = Integer.parseInt(args[4]);
                                Data.setExp(name, amount);
                                MsgSender.sendNormally(p, config.getString("Messages.Exp-Set")
                                        .replace("{amount}", "" + amount)
                                        .replace("{player}", name));
                            }
                        }
                    }
                }

                ////PVPR Admin Holo
                if (args[1].equalsIgnoreCase("holo")) { //PVPR Admin Holo 2:Create 3:编辑名
                    if(args.length != 3 && args.length != 4) {
                        MsgSender.sendAdminHelpList(p, "holo");
                        return true;
                    }

                    if(args.length == 3) {
                        MsgSender.sendFromKey(p, ("Messages.Hologram-Updated"));
                        RankHolo.update();
                    }

                    if(args.length == 4) {
                        if (args[2].equalsIgnoreCase("create")) {
                            String name = args[3];
                            if(HologramData.config.get(name) != null) {
                                MsgSender.sendNormally(p, config.getString("Messages.Hologram-Exists")
                                        .replace("{holo}", name));
                                return true;
                            }
                            org.bukkit.Location loc = p.getLocation();
                            loc.setY(loc.getY() + 4.0);
                            RankHolo.create(name, loc);
                            MsgSender.sendNormally(p, config.getString("Messages.Hologram-Created")
                                    .replace("{holo}", name));
                        }
                        if (args[2].equalsIgnoreCase("remove")) {
                            String name = args[3];
                            if(HologramData.config.get(name) == null) {
                                MsgSender.sendNormally(p, config.getString("Messages.Hologram-Not-Found")
                                        .replace("{holo}", name));
                                return true;
                            }
                            HologramData.config.set(name, null);
                            HologramData.save();
                            MsgSender.sendNormally(p, config.getString("Messages.Hologram-Removed")
                                    .replace("{holo}", name));
                            RankHolo.destroy();
                            RankHolo.update();
                        }
                    }

                }

                ////PVPR Admin Shop
                if (args[1].equalsIgnoreCase("shop")) {
                    if(args.length == 2) {
                        MsgSender.sendAdminHelpList(p, "shop");
                        return true;
                    } else {
                        String sub = args[2];

                        if(sub.equalsIgnoreCase("add")) { //add 3:编辑名 4:价格 5:折扣 6:所需经验 7:次数(-1无限)
                            if(args.length != 8) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {

                                String editName = args[3];
                                int price = Integer.parseInt(args[4]);
                                double discount = Double.parseDouble(args[5]);
                                int exp = Integer.parseInt(args[6]);
                                int count = Integer.parseInt(args[7]);

                                if(Data.getItem(editName) != null) { //编辑名下的物品已存在
                                    MsgSender.sendNormally(p, config.getString("Messages.Item-Exists")
                                            .replace("{editname}", editName));
                                } else {
                                    if (ViaVersion.getItemInMainHand(p).getType() == Material.AIR) {
                                        //手中为空气
                                        MsgSender.sendFromKey(p, "Messages.Item-Is-Air");
                                    } else {
                                        //正常新增
                                        Data.setItem(ViaVersion.getItemInMainHand(p), editName, price, discount, exp, count);
                                        Maps.setEditing(p.getName(), editName);
                                        Inventory inv = ItemEditInv.generate(editName);
                                        p.closeInventory();
                                        p.openInventory(inv);

                                        MsgSender.sendNormally(p, config.getString("Messages.Item-Added")
                                                .replace("{editname}", editName)
                                                .replace("{price}", "" + price)
                                                .replace("{discount}", "" + discount)
                                                .replace("{exp}", "" + exp)
                                                .replace("{count}", "" + count));
                                    }
                                }


                            }
                        }

                        if(sub.equalsIgnoreCase("remove")) { //remove 3:编辑名
                            if(args.length != 4) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String editName = args[3];
                                if(Data.getItem(editName) == null) {
                                    MsgSender.sendNormally(p, config.getString("Messages.Item-Not-Found")
                                            .replace("{editname}", editName));
                                } else {
                                    Data.removeItem(editName);
                                    MsgSender.sendNormally(p, config.getString("Messages.Item-Removed")
                                            .replace("{editname}", editName));
                                }
                            }
                        }

                        if(sub.equalsIgnoreCase("edit")) { //edit 3:编辑名
                            if(args.length != 4) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String editName = args[3];
                                if(Data.getItem(editName) == null) {
                                    MsgSender.sendNormally(p, config.getString("Messages.Item-Not-Found")
                                            .replace("{editname}", editName));
                                } else {
                                    Maps.setEditing(p.getName(), editName);
                                    Inventory inv = ItemEditInv.generate(editName);
                                    p.closeInventory();
                                    p.openInventory(inv);
                                }
                            }
                        }

                        if(sub.equalsIgnoreCase("setEditName")) { //setEditName 3:编辑名 4:新编辑名
                            if(args.length != 5) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String editName = args[3];
                                String newEditName = args[4];
                                ItemEditor.changeEditName(p, editName, newEditName);
                            }
                        }

                        if(sub.equalsIgnoreCase("setPrice")) { //setPrice 3:编辑名 4:新价格
                            if(args.length != 5) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String editName = args[3];
                                int price = Integer.parseInt(args[4]);
                                ItemEditor.changePrice(p, editName, price);
                            }
                        }

                        if(sub.equalsIgnoreCase("setDiscount")) { //setDiscount 3:编辑名 4:新折扣
                            if(args.length != 5) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String editName = args[3];
                                double discount = Double.parseDouble(args[4]);
                                ItemEditor.changeDiscount(p, editName, discount);
                            }
                        }

                        if(sub.equalsIgnoreCase("setExp")) { //setExp 3:编辑名 4:新经验值
                            if(args.length != 5) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String editName = args[3];
                                int exp = Integer.parseInt(args[4]);
                                ItemEditor.changeExp(p, editName, exp);
                            }
                        }

                        if(sub.equalsIgnoreCase("setCount")) { //setCount 3:编辑名 4:新可用次数
                            if(args.length != 5) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String editName = args[3];
                                int count = Integer.parseInt(args[4]);
                                ItemEditor.changeCount(p, editName, count);
                            }
                        }

                        if(sub.equalsIgnoreCase("setItem")) { //setItem 3:编辑名
                            if (args.length != 4) {
                                MsgSender.sendFromKey(p, "Messages.Command-Error");
                            } else {
                                String editName = args[3];
                                ItemEditor.changeItemStack(p, editName, ViaVersion.getItemInMainHand(p));
                            }
                        }

                    }
                }


            }

            return true;
        }
        return false;
    }
}
