package com.kidarun.kits.Listeners;

import com.kidarun.kits.Cache;
import com.kidarun.kits.Database.Cooldown;
import com.kidarun.kits.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class InventoryClick implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInvClick(InventoryClickEvent e) {
        try {
            for (Kit kit : Cache.getKits()) {
                if (e.getClickedInventory().getTitle().equals(kit.getInventoryTitle())) {
                    e.setResult(Event.Result.DENY);
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Voltar")) {
                        e.getWhoClicked().openInventory(Cache.getMainInventory());
                    }
                }
            }
            if (e.getInventory().equals(Cache.mainInventory)) {
                for (String item : Cache.categoryItem.keySet()) {
                    e.setResult(Event.Result.DENY);
                    if (!(Cache.categoryItem.get(item).isSimilar(e.getCurrentItem()))) continue;
                    if (e.getClick().isLeftClick()) {
                        switch (item) {
                            case "rank":
                                for (Kit rkit : Cache.ranksKits) {
                                    if (Cooldown.canTakeKit((Player) e.getWhoClicked(), rkit)) {
                                        rkit.give(((Player) e.getWhoClicked()).getPlayer());
                                    }
                                }
                                break;
                            case "vip":
                                for (Kit vkit : Cache.vipsKits) {
                                    if (Cooldown.canTakeKit((Player) e.getWhoClicked(), vkit)) {
                                        vkit.give(((Player) e.getWhoClicked()).getPlayer());
                                    }
                                }
                                break;
                            default:
                                List<Kit> kits = new ArrayList<>(Cache.getKits());
                                kits.removeAll(Cache.vipsKits);
                                kits.removeAll(Cache.ranksKits);
                                for (Kit ekit : kits) {
                                    if (Cooldown.canTakeKit((Player) e.getWhoClicked(), ekit)) {
                                        ekit.give(((Player) e.getWhoClicked()).getPlayer());
                                    }
                                }
                                break;
                        }
                    }
                    if (e.getClick().isRightClick()) {
                        if (e.getCurrentItem().isSimilar(Cache.categoryItem.get(item))) {
                            switch (item) {
                                case "rank":
                                    for (Kit rkit : Cache.ranksKits) {
                                        if (e.getWhoClicked().hasPermission(rkit.getPermission())) {
                                            e.getWhoClicked().openInventory(Cache.kitPreview.get(rkit.getNome()));
                                        }
                                    }
                                    break;
                                case "vip":
                                    for (Kit vkit : Cache.vipsKits) {
                                        if (e.getWhoClicked().hasPermission(vkit.getPermission())) {
                                            e.getWhoClicked().openInventory(Cache.kitPreview.get(vkit.getNome()));
                                        }
                                    }
                                    break;
                                default:
                                    List<Kit> kits = new ArrayList<>(Cache.getKits());
                                    kits.removeAll(Cache.vipsKits);
                                    kits.removeAll(Cache.ranksKits);
                                    for (Kit vkit : kits) {
                                        if (e.getWhoClicked().hasPermission(vkit.getPermission())) {
                                            e.getWhoClicked().openInventory(Cache.kitPreview.get(vkit.getNome()));
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException ignore){}
    }

}