package me.btelnyy.crackedaccauth.constants;

import java.util.HashMap;

import me.btelnyy.crackedaccauth.CrackedAccAuth;
import me.btelnyy.crackedaccauth.playerdata.PlayerData;

public class Globals {
    //to reduce disk operations
    public static HashMap<String, PlayerData> CachedPlayers = new HashMap<String, PlayerData>();
    //this one should not change, its effectively legacy code, but it works perfectly
    public final static String Path = CrackedAccAuth.getInstance().getDataFolder().toString() + "/PlayerData/";
    //for passwords
    public static HashMap<String, Integer> passwordPlayers = new HashMap<String, Integer>();
}
