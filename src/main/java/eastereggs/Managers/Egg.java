package eastereggs.Managers;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Egg {

    private Location loc;

    private List<String> commands;

    private int ID;

    public Egg(Location loc, List<String> commands) {
        this.loc = loc;
        this.commands = commands;
    }

// GETTERS
    public Location getLoc() {
        return loc;
    }
    public List<String> getCommands() {
        return commands;
    }
// SETTERS
    public void setLoc(Location loc) {
        this.loc = loc;
    }
    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

}
