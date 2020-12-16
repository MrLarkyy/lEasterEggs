package eastereggs.Managers.Inventories;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import eastereggs.Eastereggs;
import eastereggs.Managers.Egg;
import eastereggs.Managers.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.*;

public class GUI {

    public GUI(Player p, int page,Eastereggs main, StorageManager storage) {

        Inventory gui = Bukkit.createInventory(null,54,"Eggs Editor - "+page);

        List<ItemStack> allItems = new ArrayList<>();
        ItemStack eggitem;
        ItemMeta eggmeta;

        for (Map.Entry<Integer, Egg> pair : storage.getEggs().entrySet()) {
            //eggitem = GUIUtil.mkitem(Material.SKULL_ITEM,"&eEgg &6#"+String.valueOf(pair.getKey()),String.valueOf(pair.getKey()), GUIUtil.mkLore("",Arrays.asList(" ","&6Left-Click&e to edit","&6Right-Click&e to teleport"," ","&eLocation:","&eWorld: &6"+pair.getValue().getLoc().getWorld().getName(),"&ex: &6"+pair.getValue().getLoc().getX(),"&ey: &6"+pair.getValue().getLoc().getY(),"&ez: &6"+pair.getValue().getLoc().getZ())));
            //CREATING ITEMSTACK
            eggitem = new ItemStack(Material.SKULL_ITEM,1,(byte) SkullType.PLAYER.ordinal());
            eggmeta = eggitem.getItemMeta();

            // SETTING META
            eggmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&eEgg &6#"+pair.getKey()));
            eggmeta.setLocalizedName(String.valueOf(pair.getKey()));
            eggmeta.setLore(GUIUtil.mkLore("",Arrays.asList(" ","&6Left-Click&e to edit","&6Right-Click&e to teleport"," ","&eLocation:","&eWorld: &6"+pair.getValue().getLoc().getWorld().getName(),"&ex: &6"+pair.getValue().getLoc().getX(),"&ey: &6"+pair.getValue().getLoc().getY(),"&ez: &6"+pair.getValue().getLoc().getZ())));

            // CREATING GAMEPROFILE
            GameProfile profile = new GameProfile(UUID.randomUUID(),null);
            profile.getProperties().put("textures",new Property("textures","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjU2ZjdmM2YzNTM2NTA2NjI2ZDVmMzViNDVkN2ZkZjJkOGFhYjI2MDA4NDU2NjU5ZWZlYjkxZTRjM2E5YzUifX19"));
            Field field;
            try {
                field = eggmeta.getClass().getDeclaredField("profile");
                field.setAccessible(true);
                field.set(eggmeta,profile);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException x) {
                x.printStackTrace();
            }
            //FINAL
            eggitem.setItemMeta(eggmeta);

            allItems.add(eggitem);
        }
        if (allItems.size()==0) {
            eggitem = new ItemStack(Material.BARRIER);
            eggmeta = eggitem.getItemMeta();
            eggmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&cNo eggs found!"));
            eggitem.setItemMeta(eggmeta);
            gui.setItem(22,eggitem);
        }


        ItemStack left;
        ItemMeta leftmeta;
        if (GUIUtil.isPageValid(allItems,page-1,45)) {
            left = new ItemStack(Material.ARROW,1);
            leftmeta=left.getItemMeta();
            leftmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&aPrevious Page"));
            leftmeta.setLocalizedName(String.valueOf(page));
            left.setItemMeta(leftmeta);

            gui.setItem(45,left);
        }

        ItemStack right;
        ItemMeta rightmeta;

        if (GUIUtil.isPageValid(allItems,page+1,45)) {
            right = new ItemStack(Material.ARROW,1);
            rightmeta=right.getItemMeta();
            rightmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&aNext Page"));
            rightmeta.setLocalizedName(String.valueOf(page));
            right.setItemMeta(rightmeta);

            gui.setItem(53,right);
        }


        for (ItemStack item : GUIUtil.getPageItems(allItems,page,45))
            gui.setItem(gui.firstEmpty(),item);

        p.openInventory(gui);

    }
}
