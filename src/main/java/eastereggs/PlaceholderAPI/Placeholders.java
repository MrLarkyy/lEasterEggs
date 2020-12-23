package eastereggs.PlaceholderAPI;

import eastereggs.Eastereggs;
import eastereggs.Managers.StorageManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {

    private Eastereggs main;
    private StorageManager storage;

    public Placeholders (Eastereggs main, StorageManager storage) {
        this.main = main;
        this.storage = storage;
    }
    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor() {
        return main.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier(){
        return "eastereggs";
    }

    @Override
    public String getVersion(){
        return main.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier){

        if(p == null){
            return "";
        }

        if(identifier.equals("found")){
            return String.valueOf(storage.getPlayer(p.getUniqueId()).getEggs().size());
        }

        if(identifier.equals("total")){
            return String.valueOf(storage.getEggs().size());
        }

        if (identifier.equals("remain"))
            return String.valueOf(storage.getPlayer(p.getUniqueId()).getEggs().size() - storage.getEggs().size());

        return null;
    }
}
