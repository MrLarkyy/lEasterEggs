package eastereggs;

import eastereggs.Managers.Egg;
import eastereggs.Managers.StorageManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class Eastereggs extends JavaPlugin {

    private File dataFile;
    private YamlConfiguration modifyDataFile;
    StorageManager storage = new StorageManager(this);

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&d[lEasterEggs]&f Plugin was &aEnabled&f!"));

        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        try {
            loadFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.loadEggs();
        storage.loadEggPlayers();

        getServer().getPluginManager().registerEvents(new EggsListener(this),this);

    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&d[lEasterEggs]&f Plugin was &cDisabled&f!"));
        try {
            storage.saveEggs();
            storage.saveEggPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public YamlConfiguration getDataFile() {
        return modifyDataFile;
    }
    public File getFile() {
        return dataFile;
    }

    public void loadFiles() throws IOException {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.createNewFile();
        }
        modifyDataFile = YamlConfiguration.loadConfiguration(dataFile);

    }

    public void saveEgg(Location loc, List<String> commands, Player p) {
        Egg egg = new Egg(loc,commands);
        storage.addEgg(egg);

    }
}
