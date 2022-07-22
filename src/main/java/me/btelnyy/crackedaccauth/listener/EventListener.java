package me.btelnyy.crackedaccauth.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;

import me.btelnyy.crackedaccauth.CrackedAccAuth;
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
        
    }
    @EventHandler
    public void onLeave(PlayerKickEvent event){

    }
}
