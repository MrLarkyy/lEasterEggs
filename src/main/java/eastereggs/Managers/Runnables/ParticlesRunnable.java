package eastereggs.Managers.Runnables;

import eastereggs.Eastereggs;
import eastereggs.Managers.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticlesRunnable extends BukkitRunnable {

    private Eastereggs main;
    private StorageManager storage;

    public ParticlesRunnable (Eastereggs main, StorageManager storage) {
        this.main = main;
        this.storage = storage;
    }

    Location loc;
    @Override
    public void run() {
        if (main.getConfigBoolean("particles.enabled",true))
        for (Integer i : storage.getEggsForParticles().keySet()) {
            for (Player p : Bukkit.getOnlinePlayers())
                if (!storage.hasEgg(p.getUniqueId(),storage.getEggs().get(i))) {
                    loc = storage.getEggs().get(i).getLoc().clone();
                    loc.add(0.5, 0.5, 0.5);
                    p.spawnParticle(Particle.valueOf(main.getConfigString("particles.particle","VILLAGER_HAPPY")), loc, main.getConfigInt("particles.count",7), 0.325, 0.325, 0.325);
                }
        }
    }
}
