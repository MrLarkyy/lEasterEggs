package eastereggs;

import eastereggs.Managers.Egg;
import eastereggs.Managers.EggPlayer;
import eastereggs.Managers.Inventories.CommandsGUI;
import eastereggs.Managers.Inventories.EditorGUI;
import eastereggs.Managers.Inventories.GUI;
import eastereggs.Managers.Inventories.GUIUtil;
import eastereggs.Managers.StorageManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.*;

public class EggsListener implements Listener {

    private Eastereggs main;

    private StorageManager storage;

    public EggsListener (Eastereggs main) {
        this.main = main;
        this.storage = main.storage;
        this.textures = main.getConfigList("eggtextures",Arrays.asList("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjU2ZjdmM2YzNTM2NTA2NjI2ZDVmMzViNDVkN2ZkZjJkOGFhYjI2MDA4NDU2NjU5ZWZlYjkxZTRjM2E5YzUifX19","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThiOWUyOWFiMWE3OTVlMmI4ODdmYWYxYjFhMzEwMjVlN2NjMzA3MzMzMGFmZWMzNzUzOTNiNDVmYTMzNWQxIn19fQ==", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjJjZDVkZjlkN2YxZmE4MzQxZmNjZTJmM2MxMThlMmY1MTdlNGQyZDk5ZGYyYzUxZDYxZDkzZWQ3ZjgzZTEzIn19fQ==", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc2NTk1ZWZmY2M1NjI3ZTg1YjE0YzljODgyNDY3MWI1ZWMyOTY1NjU5YzhjNDE3ODQ5YTY2Nzg3OGZhNDkwIn19fQ==", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNkNjliMjNhZTU5MmM2NDdlYjhkY2ViOWRhYWNlNDQxMzlmNzQ4ZTczNGRjODQ5NjI2MTNjMzY2YTA4YiJ9fX0=", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RjM2VlNDYxNDdmMzM3ZGE0ZGY5MTRiZDUyODg3MTI4N2Y5ZTY3MmM5NjQ1YmY1MWQ0MzhjYTU1NDM4ZjM5NyJ9fX0="));
    }
    public GUIUtil util = new GUIUtil();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        EggPlayer eggPlayer = new EggPlayer(p.getUniqueId(),new ArrayList<>());
        storage.addPlayer(eggPlayer);
    }
    @EventHandler (ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) throws IOException {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (storage.getEgg(b.getLocation()) != null) {
            if (!p.isSneaking()) {
                e.setCancelled(true);
            } else {

                new BukkitRunnable() {
                    @Override
                    public void run() {

                        Egg egg = storage.getEgg(b.getLocation());

                        for (UUID uuid : storage.getPlayers().keySet()) {
                            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                            if (player != null)
                                if (storage.hasEgg(player.getUniqueId(), egg))
                                    storage.getPlayer(player.getUniqueId()).removeEgg(egg);
                        }
                        try {
                            storage.removeEgg(egg);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        main.sendMessage(p, main.getConfigString("messages.eggremoved", "&cEgg was successfully removed!"));

                    }
                }.runTaskAsynchronously(main);
            }
        }

    }

    private List<String> textures;
    @EventHandler (ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e) throws IOException {
        Player p = e.getPlayer();
        Block b = e.getBlockPlaced();

        if (p.getInventory().getItemInMainHand().getItemMeta()!=null && p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName()!=null) {
            if (p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("easteregg")) {
                main.saveEgg(b.getLocation(), main.getConfigFile().getStringList("predefinedcommands"), p);
                main.sendMessage(p, main.getConfigString("messages.eggcreated","&fYou have created an &eEaster Egg&f!"));
                main.getDataFile().save(main.getFile());
            }
            if (p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().equals("randomeasteregg")) {
                Collections.shuffle(textures);

                ItemStack item = p.getInventory().getItemInMainHand();

                util.setSkullItemSkin(item,textures.get(1));
            }
        }
    }
    ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.AQUA,Color.FUCHSIA,Color.PURPLE,Color.BLUE,Color.GREEN,Color.LIME,Color.MAROON,Color.NAVY,Color.ORANGE,Color.ORANGE,Color.WHITE,Color.YELLOW));

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getHand().equals(EquipmentSlot.HAND)) {
            Block b = e.getClickedBlock();
            if (storage.getEgg(b.getLocation())!=null) {
                Egg egg = storage.getEgg(b.getLocation());
                if (!storage.hasEgg(p.getUniqueId(),egg)) {
                    storage.getPlayer(p.getUniqueId()).addEgg(egg);
                    main.sendMessage(p,main.getConfigString("messages.eggfound","&fYou have found an&e Easter Egg &6#%id%&f!").replace("%id%",String.valueOf(storage.getEggID(egg))));
                    if (main.getConfigBoolean("sounds.eggfound.enabled",true))
                        p.playSound(p.getLocation(),Sound.valueOf(main.getConfigString("sounds.eggfound.sound","ENTITY_PLAYER_LEVELUP")),1,main.getConfigInt("sounds.eggfound.pitch",1));

                    //Title
                    if (main.getConfigBoolean("title.enabled",true))
                        p.sendTitle(ChatColor.translateAlternateColorCodes('&',main.getConfigString("title.title","&dCongratulation!")),ChatColor.translateAlternateColorCodes('&',main.getConfigString("title.subtitle","&fYou have found an egg.")),main.getConfigInt("title.fadein",10),main.getConfigInt("title.stay",10),main.getConfigInt("title.fadeout",10));

                    //Firework
                    if (main.getConfigBoolean("firework.enabled",true)) {
                        Collections.shuffle(colors);
                        Firework firework = (Firework) egg.getLoc().getWorld().spawnEntity(egg.getLoc(), EntityType.FIREWORK);
                        FireworkMeta fireworkMeta = firework.getFireworkMeta();
                        FireworkEffect effect;
                        if (main.getConfigBoolean("firework.random",true)) {
                            effect = FireworkEffect.builder().flicker(true).withColor(colors.get(1)).withFade(colors.get(2)).build();
                        } else
                            effect = FireworkEffect.builder().flicker(true).withColor(main.getConfigColor("firework.colors.color1",Color.YELLOW)).withFade(main.getConfigColor("firework.colors.color2",Color.AQUA)).build();
                        fireworkMeta.addEffect(effect);
                        fireworkMeta.setPower(0);

                        firework.setFireworkMeta(fireworkMeta);
                    }

                    // Sending Commands
                    if (egg.getCommands()!=null)
                    for (String str : egg.getCommands()) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),str.replace("%player%",p.getName()));

                    }
                } else {
                    main.sendMessage(p,main.getConfigString("messages.alreadyfound","&cYou have already found this Easter Egg!"));
                    if (main.getConfigBoolean("sounds.alreadyfound.enabled",true)) p.playSound(p.getLocation(),Sound.valueOf(main.getConfigString("sounds.alreadyfound.sound","ENTITY_VILLAGER_NO")),1,main.getConfigInt("sounds.eggfound.pitch",1));
                }
            }
        }
    }
    @EventHandler
    public void onInvClick (InventoryClickEvent e) {
        if (e.getCurrentItem()!=null && !e.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) {
            Player p = (Player) e.getWhoClicked();
            ItemStack i = e.getCurrentItem();
            Inventory inv =e.getInventory();


            //EGGS LIST
            if (i!=null && i.getType()!=null && e.getView().getTitle().contains("Eggs Editor")) {
                int page;
                if (inv.getItem(45)!=null)
                    page = Integer.parseInt(inv.getItem(45).getItemMeta().getLocalizedName());
                else
                    page = 1;
                // OPEN EDITOR
                if (i.getType()==Material.SKULL_ITEM && e.getClick().isLeftClick()) {
                    storage.addEditingEgg(p,storage.getEggs().get(Integer.parseInt(i.getItemMeta().getLocalizedName())));
                    new EditorGUI(p,storage.getEggs().get(Integer.parseInt(i.getItemMeta().getLocalizedName())),main,storage);
                }
                // TELEPORT TO EGG
                if (i.getType()==Material.SKULL_ITEM && e.getClick().isRightClick()) {
                    main.sendMessage(p,main.getConfigString("messages.teleported","&fYou have been teleported to &eEaster Egg &6#%id%&f!").replace("%id%",i.getItemMeta().getLocalizedName()));
                    Location loc =  storage.getEggs().get(Integer.parseInt(i.getItemMeta().getLocalizedName())).getLoc();
                    loc.add(0.5,0.5,0.5);
                    p.teleport(loc);
                }
                //Switching pages
                if (e.getSlot()==45 && i.getType().equals(Material.ARROW)) {
                    new GUI(p,page-1,main,storage);
                } else if (e.getSlot()==53 && i.getType().equals(Material.ARROW)){
                    new GUI(p,page+1,main,storage);
                }

                e.setCancelled(true);
            }

            //EGG EDITOR
            if (i!=null && i.getType()!=null && e.getView().getTitle().contains("Egg Editor")) {

                //BACK BUTTON
                if (e.getCurrentItem()!=null && e.getCurrentItem().getItemMeta()!=null) {
                    if (i.getItemMeta().getLocalizedName().equals("back")) {
                        new GUI(p, 1, main, storage);
                    }

                    //COMMAND LIST
                    if (i.getItemMeta().getLocalizedName().contains("command")) {
                        new CommandsGUI(p, storage.getEditingEgg(p), 1, main, storage);
                    }

                    //DELETE EGG
                    if (i.getItemMeta().getLocalizedName().contains("delete")) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (storage.getEgg(storage.getEditingEgg(p).getLoc()) != null) {
                                    Egg egg = storage.getEditingEgg(p);

                                    for (UUID uuid : storage.getPlayers().keySet()) {
                                        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                                        if (player != null)
                                            if (storage.hasEgg(player.getUniqueId(), egg))
                                                storage.getPlayer(player.getUniqueId()).removeEgg(egg);
                                    }
                                    try {
                                        storage.removeEgg(egg);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                    main.sendMessage(p, main.getConfigString("messages.eggremoved", "&cEgg was successfully removed!"));
                                }
                            }
                        }.runTaskAsynchronously(main);
                        p.closeInventory();
                    }
                }

                e.setCancelled(true);

            }

            //EGG COMMANDS EDITOR
            if (i!=null && i.getType()!=null && e.getView().getTitle().contains("Commands Editor")) {

                //SETTING PAGE
                int page;
                if (inv.getItem(45)!=null)
                    page = Integer.parseInt(inv.getItem(45).getItemMeta().getLocalizedName());
                else
                    page = 1;

                //SWITCHING PAGES
                if (e.getSlot()==45 && i.getType().equals(Material.ARROW)) {
                    new GUI(p,page-1,main,storage);
                } else if (e.getSlot()==53 && i.getType().equals(Material.ARROW)){
                    new GUI(p,page+1,main,storage);
                }

                if (e.getCurrentItem()!=null && e.getCurrentItem().getItemMeta()!=null) {
                    //BACK BUTTON
                    if (e.getCurrentItem().getItemMeta().getLocalizedName().equals("back")) {
                        new EditorGUI(p,storage.getEditingEgg(p),main,storage);
                    }

                    //ADDING ITEM
                    if (e.getCurrentItem().getItemMeta().getLocalizedName().equals("add")) {
                        main.sendMessage(p,main.getConfigString("messages.typecommand","&e[!] &fType command into the chat! &c(Don't use slashes!)\n&7&oType 'cancel' to cancel the action"));
                        p.closeInventory();
                        storage.addTyping(p);
                    }

                    //COMMAND ITEM - Removing
                    if (e.getCurrentItem().getType().equals(Material.PAPER) && e.getClick().isRightClick()) {
                        String cmd = e.getCurrentItem().getItemMeta().getLocalizedName();
                        Egg egg = storage.getEditingEgg(p);

                        main.sendMessage(p, main.getConfigString("messages.commandremoved","Command &c%cmd%&f has been &cremoved&f!").replace("%cmd%",cmd));
                        egg.removeCommand(cmd);
                        new CommandsGUI(p, storage.getEditingEgg(p),1,main,storage);
                    }
                }

                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (storage.isTyping(p)) {
            String cmd = e.getMessage();
            Egg egg = storage.getEditingEgg(p);

            //ADDING
            if (!cmd.equals("cancel")) {
                egg.addCommand(cmd);
                main.sendMessage(p,main.getConfigString("messages.commandadded","Command &e%cmd%&f has been added!").replace("%cmd%",cmd));
            }

            //Cancel ADDING
            else {
                main.sendMessage(p,main.getConfigString("messages.actioncancelled","&cAction has been cancelled!"));
            }
            storage.removeTyping(p);
            if (Bukkit.getVersion().contains("1.12"))
            new CommandsGUI(p, storage.getEditingEgg(p),1,main,storage);
            e.setCancelled(true);
        }

    }

}
