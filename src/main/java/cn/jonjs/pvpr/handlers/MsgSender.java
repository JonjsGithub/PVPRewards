package cn.jonjs.pvpr.handlers;

import cn.jonjs.jonapi.utils.GameVersionUtils;
import cn.jonjs.jonapi.utils.MessageUtils;
import cn.jonjs.pvpr.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MsgSender {

    static FileConfiguration config = Main.getInst().getConfig();

    public static void sendFromKey(Player p, String configKey) {
        if( ! configKey.contains(".")) {
            configKey = "Messages." + configKey; //自动纠错
        }
        String msg = config.getString(configKey, configKey);
        if( ! msg.equalsIgnoreCase("none")) { //消息提示为开

            if (msg.equals(configKey)) {
                p.sendMessage("<" + configKey + ">");
            } else {

                String[] temp;
                String delimeter = "\\@";  // 指定分割字符
                temp = msg.split(delimeter); // 分割字符串

                if (temp.length != 2 && temp.length != 6) {
                    p.sendMessage("配置项 " + configKey + " 格式错误");
                } else {
                    if (temp.length == 2) {
                        if (temp[0].equalsIgnoreCase("message")) {
                            MessageUtils.sendMessage(p, temp[1]);
                        }
                        if (temp[0].equalsIgnoreCase("actionbar")) {
                            MessageUtils.sendActionBar(p, temp[1]);
                        }
                    }
                    if (temp.length == 6) {
                        if (temp[0].equalsIgnoreCase("title")) {
                            String title = temp[4];
                            String subtitle = temp[5];
                            int fadeIn = Integer.parseInt(temp[1]);
                            int stay = Integer.parseInt(temp[2]);
                            int fadeOut = Integer.parseInt(temp[3]);
                            MessageUtils.sendTitle(p, title, subtitle, fadeIn, stay, fadeOut);
                        }
                    }
                }

            }
        }
    }

    public static void sendNormally(Player p, String msg) {
        if( ! msg.equalsIgnoreCase("none")) { //消息提示为开
                String[] temp;
                String delimeter = "\\@";  // 指定分割字符
                temp = msg.split(delimeter); // 分割字符串
                if (temp.length != 2 && temp.length != 6) {
                    p.sendMessage("配置格式错误");
                } else {
                    if (temp.length == 2) {
                        if (temp[0].equalsIgnoreCase("message")) {
                            MessageUtils.sendMessage(p, temp[1]);
                        }
                        if (temp[0].equalsIgnoreCase("actionbar")) {
                            MessageUtils.sendActionBar(p, temp[1]);
                        }
                    }
                    if (temp.length == 6) {
                        if (temp[0].equalsIgnoreCase("title")) {
                            String title = temp[4];
                            String subtitle = temp[5];
                            int fadeIn = Integer.parseInt(temp[1]);
                            int stay = Integer.parseInt(temp[2]);
                            int fadeOut = Integer.parseInt(temp[3]);
                            MessageUtils.sendTitle(p, title, subtitle, fadeIn, stay, fadeOut);
                        }
                    }
                }
        }
    }

    public static void sendAdminHelpList(Player p, String type) {

        p.sendMessage(" ");
        MessageUtils.sendMessage(p, "&b&lPVP&3&lRewards   &c&o管理员帮助   &7- &eBy Jonjs2333");
        MessageUtils.sendMessage(p, "&6插件版本: &e" + Main.getInst().getDescription().getVersion());
        MessageUtils.sendMessage(p, "&6服务器版本: &e" + GameVersionUtils.getBukkitVersion());
        MessageUtils.sendJSONMessage(
                p,
                "&d[前往MCBBS]",
                ClickEvent.Action.OPEN_URL,
                "https://www.mcbbs.net/thread-810285-1-1.html"
        );
        MessageUtils.sendJSONMessage(
                p,
                "&d&l[使用文档]",
                ClickEvent.Action.OPEN_URL,
                "https://pvprdoc.jonjs.cn/"
        );
        p.sendMessage(" ");

        if(type.equalsIgnoreCase("main")) {
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Point &f- PVP积分相关指令",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpr admin point"
            );
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Exp &f- PVP经验相关指令",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpr admin exp"
            );
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Shop &f- PVP积分商城相关指令",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpr admin shop"
            );
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Holo &f- PVP排行榜全息图相关指令",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpr admin holo"
            );
            MessageUtils.sendJSONMessage(
                    p,
                    "&e/PVPR Admin Transform &f- MySQL-YAML 数据互转相关指令",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpr admin transform"
            );
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Reload &f- 重载所有数据文件(推荐重启服务器)",
                    ClickEvent.Action.SUGGEST_COMMAND,
                    "/pvpr reload"
            );
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPRAdminShop &f- 以管理员身份打开PVP积分商城",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpradminshop"
            );
        }

        if(type.equalsIgnoreCase("transform")) {
            MessageUtils.sendJSONMessage(
                    p,
                    "&e/PVPR Admin Transform File2MySQL\n&f - 将YAML数据转换为MySQL数据",
                    ClickEvent.Action.SUGGEST_COMMAND,
                    "/pvpr admin transform file2mysql"
            );
            MessageUtils.sendJSONMessage(
                    p,
                    "&e/PVPR Admin Transform MySQL2File\n&f - 将MySQL数据转换为YAML数据",
                    ClickEvent.Action.SUGGEST_COMMAND,
                    "/pvpr admin transform mysql2file"
            );
            MessageUtils.sendJSONMessage(
                    p,
                    "&e&l[返回上一级]",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpr admin"
            );
        }

        if(type.equalsIgnoreCase("holo")) {
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Holo Create <编辑名>\n&f - 在你的位置新建一个排行榜全息图",
                    ClickEvent.Action.SUGGEST_COMMAND,
                    "/pvpr admin holo create 编辑名"
            );
            MessageUtils.sendMessage(p, "   &7&o例如: /PVPR Admin Holo Create 一号排行榜");
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Holo Remove <编辑名>\n&f - 删除编辑名对应的排行榜全息图",
                    ClickEvent.Action.SUGGEST_COMMAND,
                    "/pvpr admin holo remove 编辑名"
            );
            MessageUtils.sendMessage(p, "   &7&o例如: /PVPR Admin Holo Remove 一号排行榜");
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Holo Update\n&f - 更新所有排行榜全息图",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpr admin holo update"
            );
            MessageUtils.sendJSONMessage(
                    p,
                    "&e&l[返回上一级]",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpr admin"
            );
        }

        if(type.equalsIgnoreCase("point")) {
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Point Add <玩家> <积分(整数)>\n&f - 增加某玩家的PVP积分",
                    ClickEvent.Action.SUGGEST_COMMAND,
                    "/pvpr admin point add 玩家 积分(整数)"
            );
            MessageUtils.sendMessage(p, "   &7&o例如: /PVPR Admin Point Add Jonjs 52");
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Point Remove <玩家> <积分(整数)>\n&f - 减少某玩家的PVP积分",
                    ClickEvent.Action.SUGGEST_COMMAND,
                    "/pvpr admin point remove 玩家 积分(整数)"
            );
            MessageUtils.sendMessage(p, "   &7&o例如: /PVPR Admin Point Remove Jonjs 17");
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Point Set <玩家> <积分(整数)>\n&f - 设置某玩家的PVP积分",
                    ClickEvent.Action.SUGGEST_COMMAND,
                    "/pvpr admin point set 玩家 积分(整数)"
            );
            MessageUtils.sendMessage(p, "   &7&o例如: /PVPR Admin Point Set Jonjs 200");
            MessageUtils.sendJSONMessage(
                    p,
                    "&e&l[返回上一级]",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpr admin"
            );
        }

        if(type.equalsIgnoreCase("exp")) {
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Exp Add <玩家> <积分(整数)>\n&f - 增加某玩家的PVP经验",
                    ClickEvent.Action.SUGGEST_COMMAND,
                    "/pvpr admin exp add 玩家 积分(整数)"
            );
            MessageUtils.sendMessage(p, "   &7&o例如: /PVPR Admin Exp Add Jonjs 999");
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Exp Remove <玩家> <积分(整数)>\n&f - 减少某玩家的PVP经验",
                    ClickEvent.Action.SUGGEST_COMMAND,
                    "/pvpr admin exp remove 玩家 积分(整数)"
            );
            MessageUtils.sendMessage(p, "   &7&o例如: /PVPR Admin Exp Remove Jonjs 10");
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Exp Set <玩家> <积分(整数)>\n&f - 设置某玩家的PVP经验",
                    ClickEvent.Action.SUGGEST_COMMAND,
                    "/pvpr admin exp set 玩家 积分(整数)"
            );
            MessageUtils.sendMessage(p, "   &7&o例如: /PVPR Admin Exp Set Jonjs 1000");
            MessageUtils.sendJSONMessage(
                    p,
                    "&e&l[返回上一级]",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpr admin"
            );
        }

        if(type.equalsIgnoreCase("shop")) {
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPR Admin Shop Add <编辑名> &c<所需积分(整数)> &c<折扣(小数)> &c<所需PVP经验(整数)> &c<购买次数上限(整数)>" +
                            "\n&f - 把手中物品上架到积分商城 &7(次数-1为无限)",
                    ClickEvent.Action.SUGGEST_COMMAND,
                    "/pvpr admin shop add 编辑名 积分(整数) 折扣(小数) 经验(整数) 次数(整数)"
            );
            MessageUtils.sendMessage(p, "   &7&o例如: /PVPR Admin Shop Add 物品A 50 0.8 0 -1");
            MessageUtils.sendJSONMessage(
                    p,
                    "&c/PVPRAdminShop\n&f - 打开管理商品的界面",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpradminshop"
            );
            MessageUtils.sendJSONMessage(
                    p,
                    "&e&l[返回上一级]",
                    ClickEvent.Action.RUN_COMMAND,
                    "/pvpr admin"
            );
        }

    }

    public static void sendHelpList(Player p) {
        p.sendMessage(" ");
        MessageUtils.sendMessage(p, "&b&lPVP&3&lRewards &7- &eBy Jonjs2333");
        MessageUtils.sendMessage(p, "&6插件版本: &e" + Main.getInst().getDescription().getVersion());
        MessageUtils.sendMessage(p, "&6服务器版本: &e" + GameVersionUtils.getBukkitVersion());
        MessageUtils.sendJSONMessage(
                p,
                "&d[前往MCBBS]",
                ClickEvent.Action.OPEN_URL,
                "https://www.mcbbs.net/thread-810285-1-1.html"
        );
        MessageUtils.sendJSONMessage(
                p,
                "&d&l[使用文档]",
                ClickEvent.Action.OPEN_URL,
                "https://pvprdoc.jonjs.cn/"
        );
        p.sendMessage(" ");
        MessageUtils.sendJSONMessage(
                p,
                "&b/PVPR Point View [玩家]&f- 查看PVP积分",
                ClickEvent.Action.SUGGEST_COMMAND,
                "/pvpr point view 玩家(不填为自己)"
        );
        MessageUtils.sendJSONMessage(
                p,
                "&b/PVPR Exp View [玩家] &f- 查看PVP经验",
                ClickEvent.Action.SUGGEST_COMMAND,
                "/pvpr exp view 玩家(不填为自己)"
        );
        MessageUtils.sendJSONMessage(
                p,
                "&b/PVPR Rank &f- 查看PVP段位信息",
                ClickEvent.Action.SUGGEST_COMMAND,
                "/pvpr rank 玩家(不填为自己)"
        );
        MessageUtils.sendJSONMessage(
                p,
                "&b/PVPR RankTop &f- 查看PVP段位排行榜",
                ClickEvent.Action.RUN_COMMAND,
                "/pvpr ranktop"
        );
        MessageUtils.sendJSONMessage(
                p,
                "&b/PVPR Shop &f- 打开PVP积分商城",
                ClickEvent.Action.RUN_COMMAND,
                "/pvpr shop"
        );
        MessageUtils.sendJSONMessage(
                p,
                "&c/PVPR Admin &f- 管理员指令帮助",
                ClickEvent.Action.RUN_COMMAND,
                "/pvpr admin"
        );
    }

}
