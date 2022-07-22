package me.btelnyy.crackedaccauth.playerdata;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import me.btelnyy.crackedaccauth.CrackedAccAuth;
import me.btelnyy.crackedaccauth.constants.Globals;


public class DataHandler {
    static String path = Globals.Path;

    public static void GenerateFolder() {
        Bukkit.getScheduler().runTaskAsynchronously(CrackedAccAuth.getInstance(), new Runnable() {
            @Override
            public void run() {
                Path cur_config = Path.of(path);
                if (Files.notExists(cur_config, LinkOption.NOFOLLOW_LINKS)) {
                    try {
                        Files.createDirectory(cur_config);
                    } catch (Exception e) {
                        CrackedAccAuth.getInstance().log(Level.SEVERE, "Can't create data folder! Folder path: " + path + "Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static String generatePlayerFolder(String name){
        Path cur_config = Path.of(path + "/" + name + "/");
        if (Files.notExists(cur_config, LinkOption.NOFOLLOW_LINKS)) {
            try {
                Files.createDirectory(cur_config);
                return name;
            } catch (Exception e) {
                CrackedAccAuth.getInstance().log(Level.SEVERE, "Can't create data folder! Folder path: " + cur_config + "Error: " + e.getMessage());
                e.printStackTrace();
                return name;
            }
        }else{
            return name;
        }
    }
    public static String generatePlayerFolder(Player player){
        String name = player.getName().toString();
        Path cur_config = Path.of(path + "/" + name + "/");
        if (Files.notExists(cur_config, LinkOption.NOFOLLOW_LINKS)) {
            try {
                Files.createDirectory(cur_config);
                return name;
            } catch (Exception e) {
                CrackedAccAuth.getInstance().log(Level.SEVERE, "Can't create data folder! Folder path: " + cur_config + "Error: " + e.getMessage());
                e.printStackTrace();
                return name;
            }
        }else{
            return name;
        }
    }
    public static String generatePlayerFolder(PlayerData Data){
        String name = Data.getName();
        Path cur_config = Path.of(path + "/" + name + "/");
        if (Files.notExists(cur_config, LinkOption.NOFOLLOW_LINKS)) {
            try {
                Files.createDirectory(cur_config);
                return name;
            } catch (Exception e) {
                CrackedAccAuth.getInstance().log(Level.SEVERE, "Can't create data folder! Folder path: " + cur_config + "Error: " + e.getMessage());
                e.printStackTrace();
                return name;
            }
        }else{
            return name;
        }
    }

    public static PlayerData GetData(String name) {
        if (Globals.CachedPlayers.containsKey(name)) {
            return Globals.CachedPlayers.get(name);
        } //not cached name
        File player_data = new File(path + generatePlayerFolder(name) + "/" + name + ".yml");
        String yamldata = null;
        try {
            yamldata = Files.readString(Path.of(player_data.toString()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (yamldata == "null") {
            DeleteData(name);
            CreateNewDataFile(name);
            GetData(name);
            return new PlayerData();
        }
        Yaml yaml = new Yaml(new Constructor(PlayerData.class));
        if (player_data.exists()) {
            yamldata = null;
            try {
                yamldata = Files.readString(Path.of(player_data.toString()));
            } catch (IOException e) {
                CrackedAccAuth.getInstance().log(java.util.logging.Level.WARNING, "Can't open read data for name: " + name + " Message: " + e.getMessage());
            }
            PlayerData data = yaml.load(yamldata);
            if (data == null) {
                DeleteData(name);
                data = CreateNewDataFile(name);
            }
            Globals.CachedPlayers.put(name, data);
            return data;
        } else {
            PlayerData data = CreateNewDataFile(name);
            Globals.CachedPlayers.put(name, data);
            return data;
        }
    }
    public static PlayerData GetData(Player player) {
        String name = player.getName().toString();
        if (Globals.CachedPlayers.containsKey(name)) {
            return Globals.CachedPlayers.get(name);
        } //not cached name
        File player_data = new File(path + generatePlayerFolder(name) + "/" + name + ".yml");
        String yamldata = null;
        try {
            yamldata = Files.readString(Path.of(player_data.toString()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (yamldata == "null") {
            DeleteData(name);
            CreateNewDataFile(name);
            GetData(name);
            return new PlayerData();
        }
        Yaml yaml = new Yaml(new Constructor(PlayerData.class));
        if (player_data.exists()) {
            yamldata = null;
            try {
                yamldata = Files.readString(Path.of(player_data.toString()));
            } catch (IOException e) {
                CrackedAccAuth.getInstance().log(java.util.logging.Level.WARNING, "Can't open read data for name: " + name + " Message: " + e.getMessage());
            }
            PlayerData data = yaml.load(yamldata);
            if (data == null) {
                DeleteData(name);
                data = CreateNewDataFile(name);
            }
            Globals.CachedPlayers.put(name, data);
            return data;
        } else {
            PlayerData data = CreateNewDataFile(name);
            Globals.CachedPlayers.put(name, data);
            return data;
        }
    }

    public static PlayerData CreateNewDataFile(String name) {
        Yaml yaml = new Yaml();
        PlayerData pd = new PlayerData();
        Bukkit.getScheduler().runTaskAsynchronously(CrackedAccAuth.getInstance(), new Runnable() {
                @Override
                public void run() {
                try {
                    File player_data = new File(path + generatePlayerFolder(name) + "/" + name + ".yml");
                    player_data.createNewFile();
                    FileWriter writer = new FileWriter(player_data);
                    pd.playerName = name;
                    yaml.dump(pd, writer);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    PlayerData pd = new PlayerData();
                    pd.playerName = name;
                    CrackedAccAuth.getInstance().log(java.util.logging.Level.WARNING, "An error occured when trying to create Data for " + name + ": " + e.getMessage());
                }
            }
        });
        return pd;
    }
    public static PlayerData CreateNewDataFile(Player player) {
        String name = player.getName().toString();
        Yaml yaml = new Yaml();
        PlayerData pd = new PlayerData();
        Bukkit.getScheduler().runTaskAsynchronously(CrackedAccAuth.getInstance(), new Runnable() {
                @Override
                public void run() {
                try {
                    File player_data = new File(path + generatePlayerFolder(name) + "/" + name + ".yml");
                    player_data.createNewFile();
                    FileWriter writer = new FileWriter(player_data);
                    pd.playerName = name;
                    yaml.dump(pd, writer);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    PlayerData pd = new PlayerData();
                    pd.playerName = name;
                    CrackedAccAuth.getInstance().log(java.util.logging.Level.WARNING, "An error occured when trying to create Data for " + name + ": " + e.getMessage());
                }
            }
        });
        return pd;
    }

    public static void SaveAndRemoveData(String name) {
        SaveData(name);
        Globals.CachedPlayers.remove(name);
    }
    public static void SaveAndRemoveData(Player player) {
        String name = player.getName().toString();
        SaveData(player);
        Globals.CachedPlayers.remove(name);
    }
    public static void SaveAndRemoveData(PlayerData player) {
        String name = player.getName();
        SaveData(player);
        Globals.CachedPlayers.remove(name);
    }
    public static void SaveData(String name) {
        Yaml yaml = new Yaml();
        File player_data = new File(path + generatePlayerFolder(name) + "/" + name + ".yml");
        Bukkit.getScheduler().runTaskAsynchronously(CrackedAccAuth.getInstance(), new Runnable() {
            @Override
            public void run() {
                try (FileWriter writer = new FileWriter(player_data)) {
                    PlayerData pd = Globals.CachedPlayers.get(name);
                    yaml.dump(pd, writer);
                    writer.close();
                    CrackedAccAuth.getInstance().log(Level.INFO, "Saving " + name + "'s data");
                }catch(Exception e){
                    CrackedAccAuth.getInstance().log(java.util.logging.Level.WARNING, "An error occured when trying to save Data for " + name + ": " + e.getMessage());
                }
            }
        });
    }
    public static void SaveData(Player player) {
        String name = player.getName().toString();
        Yaml yaml = new Yaml();
        File player_data = new File(path + generatePlayerFolder(name) + "/" + name + ".yml");
        Bukkit.getScheduler().runTaskAsynchronously(CrackedAccAuth.getInstance(), new Runnable() {
            @Override
            public void run() {
                try (FileWriter writer = new FileWriter(player_data)) {
                    PlayerData pd = Globals.CachedPlayers.get(name);
                    yaml.dump(pd, writer);
                    writer.close();
                    CrackedAccAuth.getInstance().log(Level.INFO, "Saving " + name + "'s data");
                }catch(Exception e){
                    CrackedAccAuth.getInstance().log(java.util.logging.Level.WARNING, "An error occured when trying to save Data for " + name + ": " + e.getMessage());
                }
            }
        });
    }
    public static void SaveData(PlayerData player) {
        String name = player.getName();
        Yaml yaml = new Yaml();
        Bukkit.getScheduler().runTaskAsynchronously(CrackedAccAuth.getInstance(), new Runnable() {
            @Override
            public void run() {
                try{
                    File player_data = new File(path + generatePlayerFolder(name) + "/" + name + ".yml");
                    FileWriter writer = new FileWriter(player_data);
                    PlayerData pd = Globals.CachedPlayers.get(name);
                    yaml.dump(pd, writer);
                    writer.close();
                    CrackedAccAuth.getInstance().log(Level.INFO, "Saving " + name + "'s data");
                }catch(Exception e){
                    CrackedAccAuth.getInstance().log(java.util.logging.Level.WARNING, "An error occured when trying to save Data for " + name + ": " + e.getMessage());
                }
            }
        });
    }

    public static void ResetData(String name) {
        PlayerData data = Globals.CachedPlayers.get(name);
        Globals.CachedPlayers.remove(name);
        Globals.CachedPlayers.put(name, data);
        SaveData(name);
    }
    public static void ResetData(Player player) {
        String name = player.getName().toString();
        PlayerData data = Globals.CachedPlayers.get(name);
        Globals.CachedPlayers.remove(name);
        Globals.CachedPlayers.put(name, data);
        SaveData(name);
    }

    public static void DeleteData(String name) {
        File player_data = new File(path + generatePlayerFolder(name) + "/" + name + ".yml");
        player_data.delete();
    }
    public static void DeleteData(Player player) {
        String name = player.getName().toString();
        File player_data = new File(path + generatePlayerFolder(name) + "/" + name + ".yml");
        player_data.delete();
    }

    public static void SaveAll() {
        CrackedAccAuth.getInstance().log(Level.INFO, "Saving all cached players data....");
        for (String name : Globals.CachedPlayers.keySet()) {
            SaveData(name);
        }
    }

    public static void ServerShutdown() {
        for (String key : Globals.CachedPlayers.keySet()) {
            DataHandler.SaveAndRemoveData(key);
        }
    }
}
