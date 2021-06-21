package com.kidarun.kits;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MainInventory {

    public static Inventory getMainInventory(){
        byte rows = (byte) Main.getInstance().getConfig().getInt("main-menu.rows");
        String title = Main.config.getString("main-menu.titulo");
        Inventory inv = Bukkit.createInventory(null, rows*9, title);
        ConfigurationSection configurationSection = Main.getInstance().getConfig().getConfigurationSection("main-menu.itens");
        FileConfiguration config = Main.getInstance().getConfig();
        for (String item : configurationSection.getKeys(false)){
            String path = "main-menu.itens."+item+".";
            short slot = (short) config.getInt(path+"slot");
            Material material = Material.getMaterial(Main.config.getString(path+"material"));
            short durabilidade = (short) config.getInt(path+"durabilidade");
            String nome = config.getString(path+"nome");
            nome = Format.color(nome);
            List<String> lore = config.getStringList(path+"lore");
            lore = Format.color(lore);
            ItemStack itemStack = new ItemStack(material);
            itemStack.setDurability(durabilidade);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(nome);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inv.setItem(slot, itemStack);
            Cache.categoryItem.put(item, itemStack);
        }
        return inv;
    }

}
