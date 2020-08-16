package io.enderle.buddylocator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BLCommandExecutor implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("buddylocator") && sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length > 0 && args[0].equalsIgnoreCase("create")) {
                if (!PermissionChecker.CheckPermission(p, "buddylocator.use")) {
                    p.sendMessage(ChatColor.RED + "BuddyLocator " + ChatColor.GRAY + "Sorry, you don't have permission to do that.");
                    return false;
                }
                if (args.length > 1) {
                    if (!Bukkit.getOnlinePlayers().stream().map(Player::getName).anyMatch(args[1]::equalsIgnoreCase)) {
                        p.sendMessage("The player " + args[1] + " is not online");
                    } else Main.plugin.CreateLocatorBoard(p, args[1]);
                } else p.sendMessage(ChatColor.RED + "BuddyLocator " + ChatColor.GRAY + "You must specify a player");

            } else if (args.length > 0 && args[0].equalsIgnoreCase("use")) {
                Player target = p;
                if (args.length > 1) {
                    if (PermissionChecker.CheckPermission(p, "buddylocator.remove")) {
                        if (!Bukkit.getOnlinePlayers().stream().map(Player::getName).anyMatch(args[1]::equalsIgnoreCase)) {
                            p.sendMessage("The player " + args[1] + " is not online");
                        } else target = Bukkit.getPlayer(args[1]);
                    } else p.sendMessage(ChatColor.RED + "BuddyLocator " + ChatColor.GRAY + "Sorry, you don't have permission to do that.");
                }
                Main.plugin.RemoveLocatorBoard(target);
            } else if (args.length > 0 && args[0].equalsIgnoreCase("clear")) {
                if (PermissionChecker.CheckPermission(p, "buddylocator.clear")) {
                    Main.plugin.RemoveAllLocatorBoards();
                } else p.sendMessage(ChatColor.RED + "BuddyLocator " + ChatColor.GRAY + "Sorry, you don't have permission to do that.");
            } else {
                p.sendMessage(ChatColor.RED + "BuddyLocator " + ChatColor.GOLD + "[HELP] " + ChatColor.GRAY + " Main Menu and Commands");
                if (PermissionChecker.CheckPermission(p, "buddylocator.use")) {
                    p.sendMessage(ChatColor.GRAY + "/bl " + ChatColor.GOLD + "create " + ChatColor.RED + "<player_name> " + ChatColor.RESET + "Creates a Locator Board");
                    p.sendMessage(ChatColor.GRAY + "/bl " + ChatColor.GOLD + "remove " + ChatColor.RESET + "Remove your Locator Board");
                }
                if (PermissionChecker.CheckPermission(p, "buddylocator.remove")) {
                    p.sendMessage(ChatColor.GRAY + "/bl " + ChatColor.GOLD + "remove " + ChatColor.RED + "<player_name> " + ChatColor.RESET + "Remove a board from a player");
                }
                if (PermissionChecker.CheckPermission(p, "buddylocator.clear")) {
                    p.sendMessage(ChatColor.GRAY + "/bl " + ChatColor.GOLD + "clear " + ChatColor.RESET + "Remove all Locator Boards");
                }
            }
        }
        return false;
    }
}
