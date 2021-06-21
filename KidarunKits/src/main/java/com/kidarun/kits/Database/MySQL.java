package com.kidarun.kits.Database;

import com.kidarun.kits.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;

public class MySQL {

    private String url, user, pass, host, database;

    public static Connection connection;

    public MySQL() {
        this.database = Main.config.getString("mysql.database");
        this.host = Main.config.getString("mysql.host");
        this.pass = Main.config.getString("mysql.pass");
        this.user = Main.config.getString("mysql.user");

        this.url = "jdbc:mysql://" + this.host + ":3306/" + database;
        setConnection();
        createTable();
    }

    public void setConnection() {
        try {
            MySQL.connection = DriverManager.getConnection(this.url, this.user, this.pass);

        } catch (SQLException err) {
            Bukkit.getConsoleSender().sendMessage("§cNão foi possível se conectar ao banco de dados!");
            err.printStackTrace();
        }
    }

    private void createTable() {
        if(MySQL.connection != null) {
            PreparedStatement stm = null;

            try {
                stm = MySQL.connection.prepareStatement("create table if not exists kits_cooldowns(  \n" +
                        "    id int(10) NOT NULL AUTO_INCREMENT,  \n" +
                        "    nick varchar(16) NOT NULL,  \n" +
                        "    kitname varchar(50) NOT NULL,\n" +
                        "    lasttimeused bigint(100) NOT NULL,\n" +
                        "    PRIMARY KEY (id)  \n" +
                        ");");
                stm.executeUpdate();
                Bukkit.getConsoleSender().sendMessage("§aCriando tabela de cooldown");
            } catch (SQLException err) {}
        }
    }


    public static void registerPlayer(String player, String kitname) {
        PreparedStatement stm = null;
        long time = System.currentTimeMillis();
        try {
            stm = MySQL.connection.prepareStatement("INSERT INTO `kits_cooldowns` (nick, kitname, lasttimeused) VALUES (?, ?, ?)");
            stm.setString(1, player);
            stm.setString(2, kitname);
            stm.setLong(3, time);

            stm.executeUpdate();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public static boolean isPlayerRegistered(String player, String kitname) {
        PreparedStatement stm = null;

        try {
            stm = MySQL.connection.prepareStatement("SELECT `kitname` FROM `kits_cooldowns` WHERE `nick` = ?");
            stm.setString(1, player);

            ResultSet result = stm.executeQuery();
            while(result.next()) {
                if (result.getString("kitname").equalsIgnoreCase(kitname)){
                    return true;
                }
            }
            return false;
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public static long getLastTimeUsed(String player, String kitname) {
        PreparedStatement stm = null;

        try {
            stm = MySQL.connection.prepareStatement("SELECT * FROM `kits_cooldowns` WHERE `nick` = '"+player+"' AND `kitname` = '"+kitname+"'");

            ResultSet result = stm.executeQuery();

            while(result.next()) {
                return result.getLong("lasttimeused");
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }

        return 0;
    }

    public static void setLastTimeUsed(String player, String kitname) {
        PreparedStatement stm = null;

        try {
            stm = MySQL.connection.prepareStatement("UPDATE `kits_cooldowns` SET `lasttimeused` = ? WHERE `kits_cooldowns`.`nick` = ? AND `kits_cooldowns`.`kitname` = ?");
            stm.setLong(1, System.currentTimeMillis());
            stm.setString(2, player);
            stm.setString(3, kitname);

            stm.executeUpdate();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

}
