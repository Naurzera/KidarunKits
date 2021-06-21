package com.kidarun.kits;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public static void registerMainInventory(){
        Cache.mainInventory = MainInventory.getMainInventory();
    }

    public static void registerKitsPrviews(){
        for (Kit kit : Cache.getKits()) {
            Cache.kitPreview.put(kit.getNome(), KitPreviewInventory.createKitPreviewInventory(kit));
        }
    }

    public static void registerRanksKits() {
        FileConfiguration config = Main.getInstance().getConfig();
        ConfigurationSection configurationSection = config.getConfigurationSection("kits-rank");
        int slots = 0;
        for (String rank : configurationSection.getKeys(false)) {
            long delayInSeconds = config.getLong("kits-rank."+rank+".delay-em-segundos");
            String title = Format.color(config.getString("kits-rank."+rank+".titulo"));
            String permission = config.getString("kits-rank."+rank+".permission");
            List<ItemStack> itemStacks = new ArrayList<>();
            for (String id : config.getConfigurationSection("kits-rank."+rank+".itens").getKeys(false)) {
                try {
                    Material material = Material.getMaterial(config.getString("kits-rank."
                            +rank+".itens."+id+".material"));
                    short durability = (short) config.getInt("kits-rank."
                            +rank+".itens."+id+".durabilidade");
                    int amount = config.getInt("kits-rank."
                            +rank+".itens."+id+".quantidade");
                    slots += Math.ceil(amount/64.0);
                    String itemName = config.getString("kits-rank."
                            +rank+".itens."+id+".nome");
                    itemName = Format.color(itemName);
                    List<String> lore = config.getStringList("kits-rank."
                            +rank+".itens."+id+".lore");
                    lore = Format.color(lore);
                    List<String> enchantmentsSplit = config.getStringList("kits-rank."
                            +rank+".itens."+id+".encantamentos");
                    ItemStack itemStack = new ItemStack(material);
                    itemStack.setDurability(durability);
                    itemStack.setAmount(amount);
                    for (String enchantment : enchantmentsSplit) {
                        String[] enchantments = enchantment.split(":");
                        Enchantment enchant = Enchantment.getByName(enchantments[0]);
                        short level = Short.parseShort(enchantments[1]);
                        itemStack.addUnsafeEnchantment(enchant, level);
                    }
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(itemName);
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                    itemStacks.add(itemStack);
                } catch (Exception e) {
                    System.out.println("Houve um erro ao carregar o kit do rank "+rank+":");
                    e.printStackTrace();
                }
            }
            List<String> comandos = new ArrayList<>();
            for (String id : config.getStringList("kits-rank."+rank+".comandos")) {
                comandos.add(id);;
            }
            Kit kit = new Kit(rank, title, permission, itemStacks, comandos, slots, delayInSeconds);
            Cache.addKit(kit);
            Cache.ranksKits.add(kit);
        }
    }

    public static void registerVipsKits() {
        FileConfiguration config = Main.getInstance().getConfig();
        ConfigurationSection configurationSection = config.getConfigurationSection("kits-vip");
        int slots = 0;
        for (String rank : configurationSection.getKeys(false)) {
            long delayInSeconds = config.getLong("kits-vip."+rank+".delay-em-segundos");
            String title = Format.color(config.getString("kits-vip."+rank+".titulo"));
            String permission = config.getString("kits-vip."+rank+".permission");
            List<ItemStack> itemStacks = new ArrayList<>();
            for (String id : config.getConfigurationSection("kits-vip."+rank+".itens").getKeys(false)) {
                try {
                    Material material = Material.getMaterial(config.getString("kits-vip."
                            +rank+".itens."+id+".material"));
                    short durability = (short) config.getInt("kits-vip."
                            +rank+".itens."+id+".durabilidade");
                    int amount = config.getInt("kits-vip."
                            +rank+".itens."+id+".quantidade");
                    slots += Math.ceil(amount/64.0);
                    String itemName = config.getString("kits-vip."
                            +rank+".itens."+id+".nome");
                    itemName = Format.color(itemName);
                    List<String> lore = config.getStringList("kits-vip."
                            +rank+".itens."+id+".lore");
                    lore = Format.color(lore);
                    List<String> enchantmentsSplit = config.getStringList("kits-vip."
                            +rank+".itens."+id+".encantamentos");
                    ItemStack itemStack = new ItemStack(material);
                    itemStack.setDurability(durability);
                    itemStack.setAmount(amount);
                    for (String enchantment : enchantmentsSplit) {
                        String[] enchantments = enchantment.split(":");
                        Enchantment enchant = Enchantment.getByName(enchantments[0]);
                        short level = Short.parseShort(enchantments[1]);
                        itemStack.addUnsafeEnchantment(enchant, level);
                    }
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(itemName);
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                    itemStacks.add(itemStack);
                } catch (Exception e) {
                    System.out.println("Houve um erro ao carregar o kit do rank "+rank+":");
                    e.printStackTrace();
                }
            }
            List<String> comandos = new ArrayList<>();
            for (String id : config.getStringList("kits-rank."+rank+".comandos")) {
                comandos.add(id);;
            }
            Kit kit = new Kit(rank, title, permission, itemStacks, comandos, slots, delayInSeconds);
            Cache.addKit(kit);
            Cache.vipsKits.add(kit);
        }
    }

}
