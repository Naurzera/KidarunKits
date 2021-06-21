package com.kidarun.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryHelper {

    public static int quantoCabe(Player p){

        int itens = 0;

        for (int slot = 0; slot < 36; slot++) {
            if (p.getInventory().getItem(slot) == null) {
                itens += 1;
                continue;
            }
            if (p.getInventory().getItem(slot).getType().equals(Material.AIR)) {
                itens += 1;
            }
        }

        return itens;

    }

}
