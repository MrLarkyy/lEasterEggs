package eastereggs.Managers.Runnables;

import eastereggs.Eastereggs;
import eastereggs.Managers.Egg;
import eastereggs.Managers.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.UUID;

public class CheckEggsRunnable extends BukkitRunnable {

    private Eastereggs main;
    private StorageManager storage;

    public CheckEggsRunnable (Eastereggs main, StorageManager storage) {
        this.main = main;
        this.storage = storage;
    }
    @Override
    public void run(){
        for (Integer i : storage.getEggs().keySet()) {
            Egg egg = storage.getEggs().get(i);
            if (egg.getLoc().getBlock().getType()== Material.AIR) {
                for (UUID uuid : storage.getPlayers().keySet()) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                    if (player!=null)
                        if (storage.hasEgg(player.getUniqueId(),egg))
                            storage.getPlayer(player.getUniqueId()).removeEgg(egg);
                }
                main.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&cThere was an issue with loaded EasterEgg!"));
                main.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&c(Easter Egg #"+storage.getEggID(egg)+" was removed!)"));

                try {
                    storage.removeEgg(egg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
