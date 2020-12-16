package eastereggs.Managers;

import eastereggs.Eastereggs;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.*;

public class StorageManager {

    private Eastereggs main;
    private HashMap<Integer,Egg> eggs;
    private HashMap<UUID,EggPlayer> players;
    private HashMap<Player,Egg> editingegg = new HashMap<>();
    private List<Player> isTyping = new ArrayList<>();

    public StorageManager(Eastereggs main) {this.main = main;}

    public void loadEggs() {
        HashMap<Integer,Egg> loadedEggs = new HashMap<>();
        if (main.getDataFile().getConfigurationSection("eggs")!=null)
            for (String str : main.getDataFile().getConfigurationSection("eggs").getKeys(false)) {

                double x =main.getDataFile().getDouble("eggs."+str+".x");
                double y =main.getDataFile().getDouble("eggs."+str+".y");
                double z =main.getDataFile().getDouble("eggs."+str+".z");
                World w = Bukkit.getWorld(main.getDataFile().getString("eggs."+str+".world"));
                Location loc =new Location(w,x,y,z);
                ArrayList<String> commands = (ArrayList<String>) main.getDataFile().get("eggs."+str+".commands");
                if (commands == null){
                    commands = new ArrayList<String>();
                }

                Egg egg = new Egg(loc,commands);

                loadedEggs.put(Integer.parseInt(str), egg);
        }
            this.eggs =loadedEggs;
    }

    public void loadEggPlayers() {
        HashMap<UUID,EggPlayer> loadedPlayers = new HashMap<>();
        if (main.getDataFile().getConfigurationSection("players")!=null)
            for (String str : main.getDataFile().getConfigurationSection("players").getKeys(false)) {

                UUID uuid = UUID.fromString(str);
                List<Integer> list = (ArrayList<Integer>) main.getDataFile().get("players."+str);

                List<Egg> eggList = new ArrayList<>();
                for (int i : list) {
                    if (eggs.containsKey(i)) {
                        eggList.add(eggs.get(i));
                    }
                }
                EggPlayer eggplayer = new EggPlayer(uuid, eggList);

                loadedPlayers.put(uuid,eggplayer);
            }
        this.players = loadedPlayers;
    }

    public void saveEggs() throws IOException {
        for(Map.Entry<Integer, Egg> pair : eggs.entrySet()) {
            Egg egg = pair.getValue();
            main.getDataFile().set("eggs."+pair.getKey()+".commands",egg.getCommands());

            main.getDataFile().set("eggs."+pair.getKey()+".x",egg.getLoc().getX());
            main.getDataFile().set("eggs."+pair.getKey()+".y",egg.getLoc().getY());
            main.getDataFile().set("eggs."+pair.getKey()+".z",egg.getLoc().getZ());
            main.getDataFile().set("eggs."+pair.getKey()+".world",egg.getLoc().getWorld().getName());
        }
        main.getDataFile().save(main.getFile());
    }

    public void saveEggPlayers() throws IOException {
        List<Integer> eggs = new ArrayList<>();
        for (Map.Entry<UUID,EggPlayer> pair : players.entrySet()) {
            EggPlayer player = pair.getValue();

            for (Egg egg : player.getEggs()) {
                eggs.add(getEggID(egg));

            }
            main.getDataFile().set("players."+pair.getKey(),eggs);
        }
        main.getDataFile().save(main.getFile());
    }

    public void addEgg(Egg egg) {
        for (int i = 0;i < Integer.MAX_VALUE;i++) {
            if (!eggs.containsKey(i)) {
                eggs.put(i, egg);
                break;
            }
        }
    }
    public HashMap<Integer,Egg> getEggs() {
        return (HashMap<Integer, Egg>) eggs.clone();
    }
    public HashMap<Integer,Egg> getEggsForParticles() {
        return (HashMap<Integer, Egg>) eggs.clone();
    }


    public void removeEgg(Egg egg) throws IOException {
        if (egg!=null && eggs!=null)
            for (Map.Entry<Integer, Egg> pair : getEggs().entrySet()) {
                if (pair.getValue().equals(egg))
                    eggs.remove(pair.getKey());
            }
        main.getDataFile().set("eggs",null);
        saveEggs();
        saveEggPlayers();
    }

    public void addPlayer (EggPlayer eggPlayer) {
        players.put(eggPlayer.getUuid(),eggPlayer);
    }

    public void addEditingEgg (Player p, Egg egg) {
        editingegg.remove(p);
        editingegg.put(p,egg);
    }
    public Egg getEditingEgg (Player p) {
        return editingegg.get(p);
    }
    public Boolean isTyping (Player p) {
        return isTyping.contains(p);
    }
    public void addTyping (Player p) {
        isTyping.add(p);
    }
    public void removeTyping (Player p) {
        isTyping.remove(p);
    }

    public Egg getEgg(Location loc) {
        for (Map.Entry<Integer, Egg> pair : eggs.entrySet()) {
            if (pair.getValue().getLoc().equals(loc)) {
                return pair.getValue();
            }
        }
        return null;
    }
    public int getEggID(Egg egg) {
        for (Map.Entry<Integer,Egg> pair2 : eggs.entrySet()) {
            if (egg.equals(pair2.getValue())) {
                return pair2.getKey();
            }
        }
        return 0;
    }

    public EggPlayer getPlayer(UUID uuid) {
        return this.players.get(uuid);
    }
    public HashMap<UUID,EggPlayer> getPlayers() {
        return this.players;
    }

    public boolean hasEgg (UUID uuid, Egg egg) {
        if (getPlayer(uuid)==null) Bukkit.broadcastMessage("Player is null");
        if (getPlayer(uuid).getEggs()==null) Bukkit.broadcastMessage("Eggs are null");
        return (getPlayer(uuid).getEggs().contains(egg));
    }

}
