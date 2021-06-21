package com.kidarun.kits.Commands;

import com.kidarun.kits.Cache;
import com.kidarun.kits.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KitCommand extends BukkitCommand {
    public KitCommand()
    {
        super("kit");
    }

    @Override
    public boolean execute(final CommandSender sender, String alias, final String[] args)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (sender instanceof Player)
                {
                    ((Player)sender).openInventory(Cache.getMainInventory());
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
        return false;
    }
}
