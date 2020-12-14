package eastereggs;

import eastereggs.Managers.Egg;
import eastereggs.Managers.EggPlayer;
import eastereggs.Managers.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class EggsListener implements Listener {

    private Eastereggs main;

    private StorageManager storage;

    public EggsListener (Eastereggs main) {
        this.main = main;
        this.storage = main.storage;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        EggPlayer eggPlayer = new EggPlayer(e.getPlayer().getUniqueId(),new ArrayList<>());
        storage.addPlayer(eggPlayer);
    }
    @EventHandler (ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) throws IOException {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (p.isSneaking()) {
            if (storage.getEgg(b.getLocation())!=null) {
                Egg egg = storage.getEgg(b.getLocation());

                for (String str : main.getDataFile().getConfigurationSection("players").getKeys(false)) {
                    Player player = Bukkit.getPlayer(str);
                    if (storage.hasEgg(player.getUniqueId(), egg))
                        storage.getPlayer(player.getUniqueId()).removeEgg(egg);
                }
                storage.removeEgg(egg);
                p.sendMessage("Vejce odebráno!");
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) throws IOException {
        Player p = e.getPlayer();
        Block b = e.getBlockPlaced();

        main.saveEgg(b.getLocation(),Arrays.asList("s","c"),p);
        p.sendMessage("You have saved an easter egg!");
        main.getDataFile().save(main.getFile());
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getHand().equals(EquipmentSlot.HAND)) {
            Block b = e.getClickedBlock();
            if (storage.getEgg(b.getLocation())!=null) {
                Egg egg = storage.getEgg(b.getLocation());
                if (!storage.hasEgg(p.getUniqueId(),egg)) {
                    storage.getPlayer(p.getUniqueId()).addEgg(egg);
                    p.sendMessage("našel jsi varle");

                }
            }




            /*for (Integer i = 0 ;i < main.getDataFile().getKeys(false).size();i++)
            if (e.getClickedBlock().getLocation().equals(main.getDataFile().get()));

            for(String str : main.getDataFile().getConfigurationSection("data").getKeys(false)) {
                if (e.getClickedBlock().getLocation().equals(main.getDataFile().get(str))) {*/


        }
    }
}
