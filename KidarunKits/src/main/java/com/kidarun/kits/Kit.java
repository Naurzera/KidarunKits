package com.kidarun.kits;

import com.kidarun.kits.Database.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kit {
    private final String nome;
    private final String inventoryTitle;
    private final String permission;
    private final List<ItemStack> itens;
    private final List<String> comandos;
    private final int slots;
    private final long delayInSeconds;

    public Kit (String nome, String inventoryTitle, String permission
            , List<ItemStack> itens, List<String> comandos, int slots, long delayInSeconds){
        this.nome = nome;
        this.inventoryTitle = inventoryTitle;
        this.permission = permission;
        this.itens = itens;
        this.comandos = comandos;
        this.slots = slots;
        this.delayInSeconds = delayInSeconds;
    }

    public String getNome() {
        return nome;
    }
    public String getPermission() {return permission;}
    public List<ItemStack> getItens() {
        return itens;
    }
    public int getSlots() {
        return slots;
    }
    public long getDelayInSeconds() {
        return delayInSeconds;
    }
    public String getInventoryTitle() {
        return inventoryTitle;
    }
    public void give(Player player){
        List<ItemStack> itens = new ArrayList<>(this.itens);
        if (InventoryHelper.quantoCabe(player)<this.slots){
            player.sendMessage("§cOs itens desse kit não cabem no seu inventário!");
        }
        else {
            for (ItemStack item : itens) {
                player.getInventory().addItem(item);
            }
            for (String comando : comandos){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), comando.replaceAll("&","§").replaceAll("%player%",player.getName()));
            }
            Cooldown.updateCooldown(player, this);
        }
    }
}
