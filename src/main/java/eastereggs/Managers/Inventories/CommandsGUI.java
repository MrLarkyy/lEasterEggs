package eastereggs.Managers.Inventories;

import eastereggs.Eastereggs;
import eastereggs.Managers.Egg;
import eastereggs.Managers.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandsGUI {

    public CommandsGUI(Player p, Egg egg, int page, Eastereggs main, StorageManager storage) {
        Inventory gui = Bukkit.createInventory(null,54,"Commands Editor");

        List<ItemStack> commandItems = new ArrayList<>();

        int number=0;
        ItemStack commandItem;
        if (storage.getEggs().get(storage.getEggID(egg)).getCommands()!=null)
        for (String str : storage.getEggs().get(storage.getEggID(egg)).getCommands()){

            commandItem = GUIUtil.mkitem(Material.PAPER,"&eCommand &6#"+number,str, GUIUtil.mkLore("",Arrays.asList("&6-&e "+str," ","&6Right-Click &fto remove")));
            commandItems.add(commandItem);
            number++;
        } else {
            commandItem = GUIUtil.mkitem(Material.BARRIER,"&cNo Commands Found!","nocommands",null);
            gui.setItem(22, commandItem);

        }

        ItemStack left;
        if (GUIUtil.isPageValid(commandItems,page-1,45)) {
            left = GUIUtil.mkitem(Material.ARROW,"&aPrevious Page",String.valueOf(page),null);
            gui.setItem(45,left);
        }

        ItemStack right;
        if (GUIUtil.isPageValid(commandItems,page+1,45)) {
            right = GUIUtil.mkitem(Material.ARROW,"&aNext Page",String.valueOf(page),null);
            gui.setItem(53,right);
        }

        for (ItemStack item : GUIUtil.getPageItems(commandItems,page,45))
            gui.setItem(gui.firstEmpty(),item);

        ItemStack back = GUIUtil.mkitem(Material.WOOD_DOOR,"&cGo Back","back",null);
        ItemStack add = GUIUtil.mkitem(Material.EMERALD,"&aAdd a command","add",null);

        gui.setItem(49,back);
        gui.setItem(48,add);
        gui.setItem(50,add);

        p.openInventory(gui);
    }
}
