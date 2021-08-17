package cn.jonjs.pvpr.handlers;

import cn.jonjs.jonapi.utils.MessageUtils;
import cn.jonjs.pvpr.Main;
import cn.jonjs.pvpr.data.Data;
import cn.jonjs.pvpr.data.DataFromSQL;
import cn.jonjs.pvpr.data.HologramData;
import cn.jonjs.pvpr.data.Maps;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.arasple.mc.trhologram.api.TrHologramAPI;
import me.arasple.mc.trhologram.api.hologram.HologramBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class RankHolo {

    static FileConfiguration config = Main.getInst().getConfig();
    static Plugin plugin = Main.getInst();

    public static boolean hasPlugin() {
        if (Bukkit.getServer().getPluginManager().getPlugin("HolographicDisplays") != null
                || Bukkit.getServer().getPluginManager().getPlugin("TrHologram") != null) {
            return true;
        } else {
            return false;
        }
    }
    public static int getPluginType() {
        String name = config.getString("Settings.Hologram.Plugin", " ");
        if (Bukkit.getServer().getPluginManager().getPlugin(name) != null) {
            if (name.equalsIgnoreCase("HolographicDisplays")) {
                return 1;
            }
            if (name.equalsIgnoreCase("TrHologram")) {
                return 2;
            }
        }
        return 0;
    }

    public static Hologram holoBuild(Location where) {
            Hologram hologram = HologramsAPI.createHologram(plugin, where);
            return hologram;
    }
    public static void holoAppend(Hologram holo) {
        RankTop.sort();
        holo.appendItemLine(new ItemStack(Material.DIAMOND_SWORD));
        holo.appendTextLine(MessageUtils.color("&6&lPVP 排行榜"));
        holo.appendTextLine(MessageUtils.color("&3位次 玩家名 &7- &3PVP经验值"));
        int exp1 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(1)) : Data.getExp(RankTop.getPlayerName(1));
        int exp2 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(2)) : Data.getExp(RankTop.getPlayerName(2));
        int exp3 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(3)) : Data.getExp(RankTop.getPlayerName(3));
        int exp4 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(4)) : Data.getExp(RankTop.getPlayerName(4));
        int exp5 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(5)) : Data.getExp(RankTop.getPlayerName(5));
        int exp6 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(6)) : Data.getExp(RankTop.getPlayerName(6));
        int exp7 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(7)) : Data.getExp(RankTop.getPlayerName(7));
        int exp8 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(8)) : Data.getExp(RankTop.getPlayerName(8));
        int exp9 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(9)) : Data.getExp(RankTop.getPlayerName(9));
        int exp10 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(10)) : Data.getExp(RankTop.getPlayerName(10));
        holo.appendTextLine(MessageUtils.color("&c&l① " + RankTop.getPlayerName(1) +
                "&7 - &b" + exp1));
        holo.appendTextLine(MessageUtils.color("&6&l② " + RankTop.getPlayerName(2) +
                "&7 - &b" + exp2));
        holo.appendTextLine(MessageUtils.color("&e&l③ " + RankTop.getPlayerName(3) +
                "&7 - &b" + exp3));
        holo.appendTextLine(MessageUtils.color("&f&l④ " + RankTop.getPlayerName(4) +
                "&7 - &b" + exp4));
        holo.appendTextLine(MessageUtils.color("&f&l⑤ " + RankTop.getPlayerName(5) +
                "&7 - &b" + exp5));
        holo.appendTextLine(MessageUtils.color("&f⑥ " + RankTop.getPlayerName(6) +
                "&7 - &b" + exp6));
        holo.appendTextLine(MessageUtils.color("&f⑦ " + RankTop.getPlayerName(7) +
                "&7 - &b" + exp7));
        holo.appendTextLine(MessageUtils.color("&f⑧ " + RankTop.getPlayerName(8) +
                "&7 - &b" + exp8));
        holo.appendTextLine(MessageUtils.color("&f⑨ " + RankTop.getPlayerName(9) +
                "&7 - &b" + exp9));
        holo.appendTextLine(MessageUtils.color("&f⑩ " + RankTop.getPlayerName(10) +
                "&7 - &b" + exp10));
    }
    public static void holoDelete(Hologram holo) {
        holo.delete();
    }

    public static HologramBuilder trHoloBuild(Location where) {
        return TrHologramAPI.builder(where);
    }
    public static void trHoloAppend(HologramBuilder holoBuilder, long rTime) {
        RankTop.sort();
        holoBuilder.append(MessageUtils.color("&6&lPVP 排行榜"));
        holoBuilder.append(MessageUtils.color("&3位次 玩家名 &7- &3PVP经验"));
        int exp1 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(1)) : Data.getExp(RankTop.getPlayerName(1));
        int exp2 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(2)) : Data.getExp(RankTop.getPlayerName(2));
        int exp3 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(3)) : Data.getExp(RankTop.getPlayerName(3));
        int exp4 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(4)) : Data.getExp(RankTop.getPlayerName(4));
        int exp5 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(5)) : Data.getExp(RankTop.getPlayerName(5));
        int exp6 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(6)) : Data.getExp(RankTop.getPlayerName(6));
        int exp7 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(7)) : Data.getExp(RankTop.getPlayerName(7));
        int exp8 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(8)) : Data.getExp(RankTop.getPlayerName(8));
        int exp9 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(9)) : Data.getExp(RankTop.getPlayerName(9));
        int exp10 = Main.useMySQL ? DataFromSQL.getExp(RankTop.getPlayerName(10)) : Data.getExp(RankTop.getPlayerName(10));
        holoBuilder.append(MessageUtils.color("&c&l① " + RankTop.getPlayerName(1) +
                "&7 - &b" + exp1), rTime * 20);
        holoBuilder.append(MessageUtils.color("&6&l② " + RankTop.getPlayerName(2) +
                "&7 - &b" + exp2), rTime * 20);
        holoBuilder.append(MessageUtils.color("&e&l③ " + RankTop.getPlayerName(3) +
                "&7 - &b" + exp3), rTime * 20);
        holoBuilder.append(MessageUtils.color("&f&l④ " + RankTop.getPlayerName(4) +
                "&7 - &b" + exp4), rTime * 20);
        holoBuilder.append(MessageUtils.color("&f&l⑤ " + RankTop.getPlayerName(5) +
                "&7 - &b" + exp5), rTime * 20);
        holoBuilder.append(MessageUtils.color("&f⑥ " + RankTop.getPlayerName(6) +
                "&7 - &b" + exp6), rTime * 20);
        holoBuilder.append(MessageUtils.color("&f⑦ " + RankTop.getPlayerName(7) +
                "&7 - &b" + exp7), rTime * 20);
        holoBuilder.append(MessageUtils.color("&f⑧ " + RankTop.getPlayerName(8) +
                "&7 - &b" + exp8), rTime * 20);
        holoBuilder.append(MessageUtils.color("&f⑨ " + RankTop.getPlayerName(9) +
                "&7 - &b" + exp9), rTime * 20);
        holoBuilder.append(MessageUtils.color("&f⑩ " + RankTop.getPlayerName(10) +
                "&7 - &b" + exp10), rTime * 20);
    }
    public static void trHoloDelete(me.arasple.mc.trhologram.module.display.Hologram holo) {
        holo.destroy();
    }

    public static void create(String name, Location where) {
        if( ! hasPlugin()) {
            return;
        }

        HologramData.config.set(name, where);
        HologramData.save();
        int rTime = config.getInt("Settings.Hologram.Refresh-Time", 30) * 20;

        // HD
        if(getPluginType() == 1) {
            Hologram holo = holoBuild(where);
            holoAppend(holo);
            Bukkit.getScheduler().cancelTasks(plugin);
            Bukkit.getScheduler().runTaskTimer(
                    plugin,
                    new Runnable() {
                        @Override
                        public void run() {
                            update();
                        }
                    },
                    rTime,
                    rTime
            );
        }

        // TR
        if(getPluginType() == 2) {
            HologramBuilder holoBuilder = trHoloBuild(where);
            trHoloAppend(holoBuilder, rTime);
            String id = holoBuilder.build().getId();
            Maps.setTrID(name, id);
//            Bukkit.getScheduler().cancelTasks(plugin);
//            Bukkit.getScheduler().runTaskTimer(
//                    plugin,
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            update();
//                        }
//                    },
//                    rTime,
//                    rTime
//            );
        }

    }

    public static void update() {
        Set<String> holoLocKeys = HologramData.config.getKeys(false);
        if( ! hasPlugin()) {
            return;
        }
        if(holoLocKeys.isEmpty()) {
            return;
        }

        // HD
        if(getPluginType() == 1) {
            destroy();
            //Create
            for(String holoLocKey : holoLocKeys) {
                Hologram newHolo = holoBuild((Location) HologramData.config.get(holoLocKey));
                holoAppend(newHolo);
            }
        }

//        // TR
//        if (getPluginType() == 2) {
//            destroy();
//            for(String holoLocKey : holoLocKeys) {
//                System.out.println(holoLocKey);
//                HologramBuilder holoBuilder = trHoloBuild((Location) HologramData.config.get(holoLocKey));
//                trHoloAppend(holoBuilder);
//                me.arasple.mc.trhologram.module.display.Hologram holo = holoBuilder.build();
//                String id = holo.getId();
//                Maps.setTrID(holoLocKey, id);
//            }
//        }

    }

    public static void destroy() {
        if( ! hasPlugin()) {
            return;
        }

        Set<String> holoLocKeys = HologramData.config.getKeys(false);

        // HD
        if(getPluginType() == 1) {
            if(HologramsAPI.getHolograms(plugin).isEmpty()) {
                return;
            }
            for(Hologram holo : HologramsAPI.getHolograms(plugin)) {
                holoDelete(holo);
            }
        }

//        // TR
//        if(getPluginType() == 2) {
//            if(Maps.trIDMap.isEmpty()) {
//                return;
//            }
//            for(String holoLocKey : holoLocKeys) {
//                String id = Maps.getTrID(holoLocKey);
//                System.out.println(id);
//                if(TrHologramAPI.getHologramById(id) != null) {
//                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//                    TrHologramAPI.getHologramById(id).destroy();
//                }
//            }
//            Maps.trIDMap.clear();
//        }

    }


}

