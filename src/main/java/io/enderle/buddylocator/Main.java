package io.enderle.buddylocator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.HashMap;

public class Main extends JavaPlugin {
    public static Main plugin = null;

    public HashMap<Player, Player> players = null;

    public boolean usePermissions = true;
    public boolean showhearts;
    public boolean showlevel;
    public boolean showworld;
    private long pollingperiod;

    public void onEnable() {
        plugin = this;
        this.getCommand("buddylocator").setExecutor(new BLCommandExecutor());
        this.players = new HashMap<>();
        this.showhearts = getConfig().getBoolean("Config.showhearts");
        this.showlevel = getConfig().getBoolean("Config.showlevel");
        this.showworld = getConfig().getBoolean("Config.showworld");


        this.usePermissions = getConfig().getBoolean("Config.use_permissions");
        this.pollingperiod = getConfig().getLong("Config.pollingperiod");

        Bukkit.getScheduler().runTaskTimer(this, new Runnable(){
            @Override
            public void run() {
                UpdateLocatorBoards();
            }
        }, 0, pollingperiod);

        RemoveAllLocatorBoards();
    }


    public void onDisable() {
        plugin = null;
    }

    public String Name() {
        return getDescription().getName();
    }
    public String Header() {
        return ChatColor.RED + "---------------------- " + ChatColor.GRAY + Name() + ChatColor.RED + " ----------------------";
    }
    public String preHeader() {
        return ChatColor.RED + "---------------------- " + ChatColor.GRAY + Name();
    }

    public void CreateLocatorBoard(Player p, String b) {
        Player buddy = Bukkit.getPlayer(b);
        this.players.put(p,buddy);

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("Locations", "dummy", buddy.getDisplayName());
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        p.setScoreboard(board);

        SetLocatorBoard(p, buddy);

    }

    public void SetLocatorBoard(Player p, Player b) {
        Scoreboard board = p.getScoreboard();
        Objective obj = board.getObjective(DisplaySlot.SIDEBAR);
        Score x = obj.getScore("X: ");
        x.setScore(Integer.valueOf(b.getLocation().getBlockX()));
        Score y = obj.getScore("Y: ");
        y.setScore(Integer.valueOf(b.getLocation().getBlockY()));
        Score z = obj.getScore("Z: ");
        z.setScore(Integer.valueOf(b.getLocation().getBlockZ()));

        if (this.showworld) {
            Score world = obj.getScore(b.getWorld().getName());
            world.setScore(0);
        }
        if (this.showhearts) {
            Score hearts = obj.getScore("Hearts: ");
            hearts.setScore(Integer.valueOf((int) b.getHealth()));
        }
        if (this.showlevel) {
            Score xp = obj.getScore("Level: ");
            xp.setScore(Integer.valueOf((int) b.getLevel()));
        }

        p.setScoreboard(board);
    }

    public void RemoveLocatorBoard(Player p) {
        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
    }

    public void UpdateLocatorBoards(){
        if (!this.players.equals(null) || !this.players.isEmpty()) {
            for (Player p : this.players.keySet()) {
                if (this.players.get(p).isOnline()) {
                    SetLocatorBoard(p, this.players.get(p));
                } else RemoveLocatorBoard(p);
            }
        }

    }

    public void RemoveAllLocatorBoards() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
        }
    }



}