package me.yarond.firework;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public final class Main extends JavaPlugin {

    public HashMap<Player, Integer> cooldown = new HashMap<Player, Integer>();

    @Override
    public void onEnable() {
        this.getCommand("firework").setExecutor(new FireworkCommand());
        new BukkitRunnable() {

            @Override
            public void run() {
                for(Player player : cooldown.keySet()) {
                    cooldown.replace(player, cooldown.get(player)-1);
                    if (cooldown.get(player) <= 0) {
                        cooldown.remove(player);
                    }
                }
            }

        }.runTaskTimer(this, 0, 20);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
