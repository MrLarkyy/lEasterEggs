package eastereggs.Managers;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Egg {

    private Location loc;

    private List<String> commands;

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
    public void addCommand(String command) {
        if (this.commands==null) {
            this.commands = new ArrayList<>();
            this.commands.add(command);
        } else
            this.commands.add(command);
    }
    public void removeCommand(String command){
        this.commands.remove(command);
    }

}
