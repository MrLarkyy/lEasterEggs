package eastereggs.Managers.Runnables;

import eastereggs.Eastereggs;
import eastereggs.Managers.Inventories.GUIUtil;
import eastereggs.Managers.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChangerandomSkinRunnable extends BukkitRunnable {

    private Eastereggs main;
    private StorageManager storage;

    public ChangerandomSkinRunnable (Eastereggs main, StorageManager storage) {
        this.main = main;
        this.storage = storage;
    }
    public GUIUtil util = new GUIUtil();
    public List<String> textures = main.getConfigList("eggtextures",Arrays.asList("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjU2ZjdmM2YzNTM2NTA2NjI2ZDVmMzViNDVkN2ZkZjJkOGFhYjI2MDA4NDU2NjU5ZWZlYjkxZTRjM2E5YzUifX19","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThiOWUyOWFiMWE3OTVlMmI4ODdmYWYxYjFhMzEwMjVlN2NjMzA3MzMzMGFmZWMzNzUzOTNiNDVmYTMzNWQxIn19fQ==", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjJjZDVkZjlkN2YxZmE4MzQxZmNjZTJmM2MxMThlMmY1MTdlNGQyZDk5ZGYyYzUxZDYxZDkzZWQ3ZjgzZTEzIn19fQ==", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc2NTk1ZWZmY2M1NjI3ZTg1YjE0YzljODgyNDY3MWI1ZWMyOTY1NjU5YzhjNDE3ODQ5YTY2Nzg3OGZhNDkwIn19fQ==", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNkNjliMjNhZTU5MmM2NDdlYjhkY2ViOWRhYWNlNDQxMzlmNzQ4ZTczNGRjODQ5NjI2MTNjMzY2YTA4YiJ9fX0=", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RjM2VlNDYxNDdmMzM3ZGE0ZGY5MTRiZDUyODg3MTI4N2Y5ZTY3MmM5NjQ1YmY1MWQ0MzhjYTU1NDM4ZjM5NyJ9fX0="));

    @Override
    public void run() {
        Collections.shuffle(textures);
        if (Bukkit.getOnlinePlayers() !=null)
            for (Player p : Bukkit.getOnlinePlayers()) {
                for (int i = 0; i < 36; i++){
                    if (p.getInventory().getItem(i)!=null && p.getInventory().getItem(i).getItemMeta()!=null && p.getInventory().getItem(i).getItemMeta().getLocalizedName()!=null)
                        if (p.getInventory().getItem(i).getItemMeta().getLocalizedName().equals("randomeasteregg")) {
                            ItemStack item = p.getInventory().getItem(i);
                            util.setSkullItemSkin(item,textures.get(1));
                    }
                }
            }
    }
}
