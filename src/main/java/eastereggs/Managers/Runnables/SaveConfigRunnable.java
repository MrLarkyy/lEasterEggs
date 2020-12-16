package eastereggs.Managers.Runnables;

import eastereggs.Eastereggs;
import eastereggs.Managers.StorageManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class SaveConfigRunnable extends BukkitRunnable {
    private Eastereggs main;
    private StorageManager storage;

    public SaveConfigRunnable (Eastereggs main, StorageManager storage) {
        this.main = main;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            storage.saveEggs();
            storage.saveEggPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
