package eastereggs.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EggPlayer {
    private List<Egg> eggs;

    private UUID uuid;

    public EggPlayer(UUID uuid,List<Egg> eggs) {
        this.eggs = eggs;
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
    public List<Egg> getEggs() {
        return eggs;
    }

    public void addEgg (Egg egg) {
        eggs.add(egg);
    }

    public void removeEgg (Egg egg) {
        eggs.remove(egg);
    }
}
