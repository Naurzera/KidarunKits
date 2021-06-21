package com.kidarun.kits;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Cache {

    public static Inventory mainInventory = null;
    public static LinkedHashMap<String, ItemStack> categoryItem = new LinkedHashMap<>();
    public static LinkedHashMap<String, Inventory> kitPreview = new LinkedHashMap<>();


    public static List<Kit> kits = new ArrayList<>();
    public static List<Kit> ranksKits = new ArrayList<>();
    public static List<Kit> vipsKits = new ArrayList<>();

    public static void addKit(Kit kit){
        kits.add(kit);
    }
    public static List<Kit> getKits(){return kits;}
    public static Inventory getMainInventory(){return mainInventory;}
    public static LinkedHashMap<String, Inventory> getKitPreview(){return kitPreview;}

}
