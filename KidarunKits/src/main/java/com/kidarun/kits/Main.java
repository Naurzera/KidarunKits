package com.kidarun.kits;

import com.kidarun.kits.Commands.KitCommand;
import com.kidarun.kits.Database.MySQL;
import com.kidarun.kits.Listeners.InventoryClick;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Main extends JavaPlugin {

    public static FileConfiguration config;
    public MySQL mysql;

    public Main() {
        this.config = getConfig();
        this.mysql = null;
    }

    private static Main instance = null;
    public static Main getInstance(){return instance;}

    @Override
    public void onEnable() {
        long started = System.currentTimeMillis();
        sendCsl(ChatColor.GREEN, "---------------------------------------");
        instance = this;
        sendCsl(ChatColor.GREEN, "Carregando config...");
        saveDefaultConfig();
        sendCsl(ChatColor.GREEN, "Conectando ao MySQL...");
        mysql = new MySQL();
        sendCsl(ChatColor.GREEN, "Carregando kits dos ranks...");
        DataLoader.registerRanksKits();
        sendCsl(ChatColor.GREEN, "Carregando kits vip...");
        DataLoader.registerVipsKits();
        sendCsl(ChatColor.GREEN, "Carregando inventario main...");
        DataLoader.registerMainInventory();
        sendCsl(ChatColor.GREEN, "Carregando previews dos kits...");
        DataLoader.registerKitsPrviews();
        sendCsl(ChatColor.GREEN, "Registrando comando...");
        registerCommand();
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        long time = System.currentTimeMillis()-started;
        sendCsl(ChatColor.GREEN, "---------------------------------------");
    }
    void sendCsl(ChatColor color, String msg){
        getServer().getConsoleSender().sendMessage(color+msg);
    }
    private void registerCommand() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register("kit", new KitCommand());
        } catch (Exception e) {
            setEnabled(false);
            throw new RuntimeException("Failed to register commands", e);
        }
    }
}
