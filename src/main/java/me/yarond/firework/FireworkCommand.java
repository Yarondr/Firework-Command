package me.yarond.firework;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.Random;

public class FireworkCommand implements CommandExecutor {

    private Main plugin = Main.getPlugin(Main.class);
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("firework") || label.equalsIgnoreCase("fw")) {
            if (sender.hasPermission("firework.launch")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Console can't summon a firework!");
                    return true;
                }
                Player player = (Player) sender;
                if (!player.hasPermission("firework.bypass")) {
                    if (plugin.cooldown.get(player) != null) {
                        if (plugin.cooldown.get(player) >= 0) {
                            player.sendMessage(ChatColor.RED + "You have to wait " + plugin.cooldown.get(player) + " seconds before doing this again");
                            return true;
                        }
                    }
                }
                Color[] colors = new Color[] {Color.GREEN, Color.PURPLE, Color.RED, Color.AQUA, Color.BLACK, Color.PURPLE,
                        Color.BLUE, Color.GRAY, Color.FUCHSIA, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE,
                        Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW};
                Random r = new Random();
                Color color1 = colors[r.nextInt(colors.length)];
                Color color2 = colors[r.nextInt(colors.length)];
                Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
                FireworkMeta meta = firework.getFireworkMeta();
                meta.addEffects(FireworkEffect.builder().withColor(color1).withColor(color2).with(FireworkEffect.Type.BALL_LARGE).withFlicker().build());
                meta.setPower(1);
                firework.setFireworkMeta(meta);
                if (plugin.cooldown.get(player) != null) {
                    plugin.cooldown.replace(player, 15);
                } else plugin.cooldown.put(player, 15);
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "You don't have the permission to do that!");
            }
            return true;
        }
        return false;
    }

}
