package com.kidarun.kits;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class KitPreviewInventory {

    public static Inventory createKitPreviewInventory(Kit kit) {

        int[] la = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
        int[] gp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};

        Inventory inv = Bukkit.createInventory(null, 54, kit.getInventoryTitle());

        List<ItemStack> itens = kit.getItens();
        short lojan = 0;
        for (int n : la) {
            if (itens.size() > lojan) {
                inv.setItem(n, itens.get(lojan));
                lojan++;
            } else {
                inv.setItem(n, addBlackStainedGlassPain(Material.STAINED_GLASS_PANE, 7));
            }
        }
        for (int n : gp) {
            inv.setItem(n, addBlackStainedGlassPain(Material.STAINED_GLASS_PANE, 7));
        }

        inv.setItem(49, addBack());

        return inv;
    }


    public static ItemStack addBlackStainedGlassPain(Material material, int durability) {
        ItemStack item = new ItemStack(material);
        item.setDurability((short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack addBack() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cVoltar");
        item.setItemMeta(meta);

        return item;
    }

}
