package com.kidarun.kits.Database;

import com.kidarun.kits.Kit;
import org.bukkit.entity.Player;

import java.util.Locale;

public class Cooldown {

    public static boolean canTakeKit(Player player, Kit kit){
        if (player.hasPermission(kit.getPermission())){
            if (MySQL.isPlayerRegistered(player.getName(), kit.getNome())){
                long lastTimeUsed = MySQL.getLastTimeUsed(player.getName(), kit.getNome());
                long pastTime = System.currentTimeMillis()-lastTimeUsed;
                long seconds = pastTime/1000;
                long kitDelay = kit.getDelayInSeconds();
                if (seconds > kitDelay)
                {
                    return true;
                }
                else
                {
                    String tempo = "segundos";
                    double segundos = kitDelay-seconds;
                    if (segundos>60) {
                        tempo = "minutos";
                        segundos = segundos / 60;

                        if (segundos > 60) {
                            tempo = "horas";
                            segundos = segundos / 60;

                            if (segundos > 24) {
                                tempo = "dias";
                                segundos = segundos / 24;

                                if (segundos > 30) {
                                    tempo = "meses";
                                    segundos = segundos / 30;
                                }
                                else if (segundos > 7) {
                                    tempo = "semanas";
                                    segundos = segundos / 7;
                                }
                            }
                        }
                    }
                    tempo = formatNumber(segundos)+" "+tempo;

                    player.sendMessage("§cVocê só poderá pegar esse kit em "+tempo);
                    return false;
                }
            }else{
                return true;
            }
        }
        else
        {
            player.sendMessage("§cVocê não tem permissão para pegar esse kit!");
            return false;
        }
    }

    public static void updateCooldown(Player player, Kit kit){
        String kitname = kit.getNome();
        if (MySQL.isPlayerRegistered(player.getName(), kitname)) {
            MySQL.setLastTimeUsed(player.getName(), kitname);
        }
        else
        {
            MySQL.registerPlayer(player.getName(), kitname);
        }
    }

    private static String formatNumber(final double d) {
        final java.text.NumberFormat format = java.text.NumberFormat.getInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);
        return format.format(d);
    }

}
