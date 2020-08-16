package io.enderle.buddylocator;

import org.bukkit.entity.Player;

public class PermissionChecker extends Main{

    public static boolean CheckPermission(Player player, String permission) {
        if (Main.plugin.usePermissions) {
            return player.hasPermission(permission) || player.isOp();
        }
        return true;
    }
}
