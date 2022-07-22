package me.btelnyy.crackedaccauth.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.scheduler.BukkitScheduler;

import me.btelnyy.crackedaccauth.CrackedAccAuth;
import me.btelnyy.crackedaccauth.constants.ConfigData;
import me.btelnyy.crackedaccauth.constants.Globals;
import me.btelnyy.crackedaccauth.playerdata.DataHandler;
import me.btelnyy.crackedaccauth.playerdata.PlayerData;
import me.btelnyy.crackedaccauth.service.Utils;
import me.btelnyy.crackedaccauth.service.file_manager.Configuration;
import me.btelnyy.crackedaccauth.service.file_manager.FileID;

public class EventListener implements Listener {
    private static final Configuration language = CrackedAccAuth.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String name = player.getName();
        String playerIp = player.getAddress().getHostName(); //only get the IP, e.g. 74.56.32.37 and no port
        PlayerData pd = new PlayerData();
        if(DataHandler.checkExistence(name)){
            pd = DataHandler.GetData(name);
            //if the ip is different, kick the player
            if(!(pd.lastIP.equals(playerIp)) && pd.strictIPRules){
                player.kickPlayer(Utils.colored(language.getString("kick_msg_wrong_ip")));
                return;
            }
            //now for passwords
            if(ConfigData.getInstance().enforcePasswordRequirement){
                if(pd.password.isEmpty() || pd.password.isBlank()){
                    player.sendMessage(Utils.colored(language.getString("password_reset_message").replace("{password}", ConfigData.getInstance().defaultPassword)));
                    pd.password = ConfigData.getInstance().defaultPassword;
                    DataHandler.SaveData(player);
                }
                player.sendMessage(Utils.colored(language.getString("pw_timer_started_message").replace("{seconds}", ConfigData.getInstance().pwEntryTimer.toString())));
                Globals.passwordPlayers.put(name, ConfigData.getInstance().maxPasswordAtemmpts);
                playerKickTimer(player);
            }else{
                if(!pd.password.isEmpty() || !pd.password.isBlank()){
                    player.sendMessage(Utils.colored(language.getString("pw_timer_started_message").replace("{seconds}", ConfigData.getInstance().pwEntryTimer.toString())));
                    Globals.passwordPlayers.put(name, ConfigData.getInstance().maxPasswordAtemmpts);
                    DataHandler.SaveData(player);
                    playerKickTimer(player);
                }
            }
        }else{
            pd = DataHandler.CreateNewDataFile(name);
            pd.lastIP = playerIp;
            if(ConfigData.getInstance().enforcePasswordRequirement){
                if(pd.password.isEmpty() || pd.password.isBlank()){
                    player.sendMessage(Utils.colored(language.getString("password_reset_message").replace("{password}", ConfigData.getInstance().defaultPassword)));
                    pd.password = ConfigData.getInstance().defaultPassword;
                    DataHandler.SaveData(player);
                }
            }
        }
    }
    @EventHandler
    public void onLeave(PlayerKickEvent event){
        Player player = event.getPlayer();
        String name = player.getName();
        DataHandler.SaveAndRemoveData(name);
    }
    public void playerKickTimer(Player p){
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(CrackedAccAuth.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(Globals.passwordPlayers.containsKey(p.getName())){
                        p.kickPlayer(Utils.colored(language.getString("kick_msg_time_ran_out")));
                    }
                }
            }, ConfigData.getInstance().pwEntryTimer);
    }
}
