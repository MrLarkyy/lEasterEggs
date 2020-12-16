package eastereggs.Managers.Inventories;

import eastereggs.Eastereggs;
import eastereggs.Managers.Egg;
import eastereggs.Managers.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EditorGUI {

    public EditorGUI(Player p, Egg egg, Eastereggs main, StorageManager storage) {

        Inventory inv = Bukkit.createInventory(null,36,"Egg Editor #"+storage.getEggID(egg));
        ItemStack commands;
        if (egg.getCommands()!=null)
            commands = GUIUtil.mkitem(Material.COMMAND,"&eCommands &7(Click)","commands", GUIUtil.mkLore("&6- &e",egg.getCommands()));
        else
            commands = GUIUtil.mkitem(Material.COMMAND,"&eCommands &7(Click)","commands", null);
        ItemStack delete = GUIUtil.mkitem(Material.BARRIER,"&cDelete this Egg","delete",null);
        ItemStack back = GUIUtil.mkitem(Material.WOOD_DOOR,"&cGo Back","back",null);

        inv.setItem(11,commands);
        inv.setItem(15,delete);
        inv.setItem(31,back);

        p.openInventory(inv);
    }
}
