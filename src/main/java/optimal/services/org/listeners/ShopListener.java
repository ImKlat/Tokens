package optimal.services.org.listeners;

/*
 * Created by ImKlat
 * Project: Tokens
 * Date: 27/2/2022 @ 10:29
 */

import optimal.services.org.Main;
import optimal.services.org.utils.CC;
import optimal.services.org.utils.PlayerData;
import optimal.services.org.utils.config.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopListener implements Listener {
    private JoinListener plugin;
    public ShopListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }
    public static ItemStack material;
    ConfigFile shop = Main.getInstance().getShopConfig();
    ConfigFile lang = Main.getInstance().getLangConfig();
    ConfigurationSection section = shop.getConfiguration().getConfigurationSection("SHOP-MENU.ITEMS");

    public static Inventory getShop() {
        ConfigFile shop = Main.getInstance().getShopConfig();
        ConfigurationSection section = shop.getConfiguration().getConfigurationSection("SHOP-MENU.ITEMS");
        Inventory shopInv = Bukkit.createInventory(null, shop.getInt("SHOP-MENU.SIZE"), CC.translate(shop.getString("SHOP-MENU.TITLE")));

        for (String item : section.getKeys(false)) {
            ItemStack materialItem = new ItemStack(Material.valueOf(shop.getString("SHOP-MENU.ITEMS." + item + ".MATERIAL")));
            ItemMeta materialMeta = materialItem.getItemMeta();

            materialMeta.setDisplayName(CC.translate(shop.getString("SHOP-MENU.ITEMS." + item + ".DISPLAYNAME")));

            List<String> lore = new ArrayList<>();
            for (String string : shop.getStringList("SHOP-MENU.ITEMS."+ item +".LORE")) {
                lore.add(string);
                materialMeta.setLore(lore);
            }

            if (shop.getBoolean("SHOP-MENU.ITEMS." + item + ".GLOW")) {
                materialMeta.addEnchant(Enchantment.LUCK, 1, false);
                materialMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            materialItem.setItemMeta(materialMeta);
            material = materialItem;
            shopInv.setItem(shop.getInt("SHOP-MENU.ITEMS." + item + ".SLOT") - 1, material);
        }
        return shopInv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        UUID uuid = p.getUniqueId();
        int slot = e.getSlot();
        Inventory inventory = e.getInventory();
        int actualTokens = PlayerData.getTokens(Main.getInstance().getMySQl(), p.getUniqueId());

        if (inventory.getName().equals(CC.translate(shop.getString("SHOP-MENU.TITLE")))) {
            e.setCancelled(true);
            for (String item : section.getKeys(false)) {
                int price = shop.getInt("SHOP-MENU.ITEMS." + item + ".PRICE");
                if (slot == shop.getInt("SHOP-MENU.ITEMS." + item + ".SLOT") - 1) {
                    if (shop.getInt("SHOP-MENU.ITEMS." + item + ".PRICE") > PlayerData.getTokens(Main.getInstance().getMySQl(), uuid)) {
                        p.sendMessage(CC.translate(lang.getString("UTILS.NOT-BALANCE")));
                        return;
                    }

                    PlayerData.giveTokens(Main.getInstance().getMySQl(), p.getUniqueId(), actualTokens - price);

                    for (String string : shop.getStringList("SHOP-MENU.ITEMS." + item + ".COMMANDS")) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), string.replaceAll("%PLAYER%", p.getName()));
                    }
                    p.sendMessage(CC.translate(shop.getString("SHOP-MENU.SUCCESSFUL").replaceAll("%MATERIAL%", shop.getString("SHOP-MENU.ITEMS." + item + ".DISPLAYNAME")).replaceAll("%AMOUNT%", Integer.toString(shop.getInt("SHOP-MENU.ITEMS." + item + ".AMOUNT")))));
                }
            }
        }
    }
}
