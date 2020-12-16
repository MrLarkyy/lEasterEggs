package eastereggs.Managers;

import eastereggs.Eastereggs;
import eastereggs.Managers.Inventories.GUI;
import eastereggs.Managers.Inventories.GUIUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class EasterEggsCommand implements CommandExecutor {

    private Eastereggs main;
    private StorageManager storage;

    public EasterEggsCommand(Eastereggs main, StorageManager storage) {
        this.main = main;
        this.storage = storage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (!storage.isTyping(p)) {
            if (args.length > 0) {

                // MENU COMMAND
                if (args[0].equals("menu") && p.hasPermission(main.getConfigString("permissions.menu","leggs.menu"))) {
                    new GUI(p, 1, main, storage);
                } else if (!p.hasPermission(main.getConfigString("permissions.menu","leggs.menu"))) {
                    main.sendMessage(p,main.getConfigString("messages.nopermission","&cYou have no permission to do that!"));
                }

                // TP COMMAND
                if (args[0].equals("tp") && p.hasPermission(main.getConfigString("permissions.tp","leggs.tp"))) {
                    if (args.length > 1) {
                        try {
                            int id = Integer.parseInt(args[1]);
                            storage.getEggs().containsKey(id);
                            Egg egg = storage.getEggs().get(id);
                            p.teleport(egg.getLoc());
                            main.sendMessage(p,main.getConfigString("messages.teleported","&fYou have been teleported to &eEater Egg &6#%id%").replace("%id%",String.valueOf(storage.getEggID(egg))));
                        } catch (NumberFormatException e) {
                            main.sendMessage(p,main.getConfigString("messages.unknownid","&cThis ID is unknown! Check IDs in /ee menu"));
                        }
                    } else {
                        main.sendMessage(p,main.getConfigString("messages.usages.tp","&cUsage: /ee tp <id>"));
                    }
                } else if (!p.hasPermission(main.getConfigString("permissions.tp","leggs.tp"))) {
                    main.sendMessage(p,main.getConfigString("messages.nopermission","&cYou have no permission to do that!"));
                }

                // CREATE COMMAND
                if (args[0].equals("create") && p.hasPermission(main.getConfigString("permissions.create","leggs.create"))) {
                    if (p.getInventory().getItemInMainHand()!=null && p.getInventory().getItemInMainHand().getType()!=null && p.getInventory().getItemInMainHand().getType()!=Material.AIR) {
                        if (p.getInventory().getItemInMainHand().getType().isBlock() || p.getInventory().getItemInMainHand().getType() == Material.SKULL_ITEM) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&dEaster Egg &7(Place)"));
                            meta.setLore(GUIUtil.mkLore("", Arrays.asList(" ", "&dPlace&f to make a new egg!")));
                            meta.setLocalizedName("easteregg");
                            item.setItemMeta(meta);

                            main.sendMessage(p,main.getConfigString("messages.eastereggitemmade","You have made a new &eEaster Egg&f!\n&7To register it to database, you have to place it!"));
                        } else {
                            main.sendMessage(p, main.getConfigString("messages.mustholdblock","&cYou must hold a block to create an Easter Egg!"));
                        }
                    } else {
                        main.sendMessage(p, main.getConfigString("messages.mustholdblock","&cYou must hold a block to create an Easter Egg!"));
                    }
                } else if (!p.hasPermission(main.getConfigString("permissions.create","leggs.create"))) {
                    main.sendMessage(p,main.getConfigString("messages.nopermission","&cYou have no permission to do that!"));
                }
                // RANDOM SKULL COMMAND
                if (args[0].equals("createrandom") && p.hasPermission(main.getConfigString("permissions.createrandom","leggs.createrandom"))) {
                    p.getInventory().addItem(GUIUtil.mkskull("&dEaster Egg &7(Place)","randomeasteregg",Arrays.asList(" ","&dPlace&f to make a new egg!"),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjU2ZjdmM2YzNTM2NTA2NjI2ZDVmMzViNDVkN2ZkZjJkOGFhYjI2MDA4NDU2NjU5ZWZlYjkxZTRjM2E5YzUifX19"));
                    main.sendMessage(p,main.getConfigString("messages.randomegggive","&aRandom Egg has been given!"));




                } else {
                    main.sendMessage(p,"&6EasterEggs Help Page:\n&e/ee menu &6- &fOpens a menu with all eggs\n&e/ee tp <id> &6- &fTeleports you to an egg\n&e/ee create &6- &fMakes a new Easter Egg");
                }

            } else {
                main.sendMessage(p,"&6EasterEggs Help Page:\n&e/ee menu &6- &fOpens a menu with all eggs\n&e/ee tp <id> &6- &fTeleports you to an egg\n&e/ee create &6- &fMakes a new Easter Egg");
            }

        }
        else
            main.sendMessage(p,main.getConfigString("messages.alreadyeditting","&cYou are already editting an egg!\n&7&oType 'cancel' to cancel the action!"));

        return false;
    }
}
