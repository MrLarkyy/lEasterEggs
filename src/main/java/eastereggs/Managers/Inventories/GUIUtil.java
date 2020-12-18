package eastereggs.Managers.Inventories;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GUIUtil {

    public static List<ItemStack> getPageItems(List<ItemStack> items, int page, int spaces){
        int upperBound = page * spaces;
        int lowerBound = upperBound - spaces;

        List<ItemStack> newItems = new ArrayList<>();
        for (int i = lowerBound;i < upperBound;i++) {
            try {
                newItems.add(items.get(i));
            } catch(IndexOutOfBoundsException e) {
                continue;
            }


        }
        return newItems;

    }
    public static boolean isPageValid(List<ItemStack> items,int page,int spaces) {
        if (page <= 0) {
            return false;
        }

        int upperBound = page * spaces;
        int lowerBound = upperBound - spaces;

        return items.size() > lowerBound;

    }

    public static ItemStack mkitem (Material material, String displayname, String localizedname, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',displayname));
        meta.setLocalizedName(localizedname);
        if (lore!=null)
            meta.setLore(mkLore("",lore));
        item.setItemMeta(meta);
        return item;
    }
    public static List<String>mkLore(String prefix,List<String> lore) {
        List<String> colLore = new ArrayList<>();
        for (String str : lore) {
            colLore.add(ChatColor.translateAlternateColorCodes('&',prefix+str));
        }
        return colLore;

    }

    public static ItemStack mkskull (String displayname, String localizedname, List <String> colLore, String texture) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM,1,(byte) SkullType.PLAYER.ordinal());
        ItemMeta meta = item.getItemMeta();

        // SETTING META
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',displayname));
        meta.setLocalizedName(localizedname);
        meta.setLore(mkLore("",colLore));

        // CREATING GAMEPROFILE
        GameProfile profile = new GameProfile(UUID.randomUUID(),null);
        profile.getProperties().put("textures",new Property("textures",texture));
        Field field;
        try {
            field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta,profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException x) {
            x.printStackTrace();
        }
        //FINAL
        item.setItemMeta(meta);
        return item;
    }

    public void setSkullItemSkin (ItemStack item, String texture) {
        ItemMeta meta = item.getItemMeta();

        // CREATING GAMEPROFILE
        GameProfile profile = new GameProfile(UUID.randomUUID(),null);
        profile.getProperties().put("textures",new Property("textures",texture));
        Field field;
        try {
            field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta,profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException x) {
            x.printStackTrace();
        }
        //FINAL
        item.setItemMeta(meta);

    }


}
