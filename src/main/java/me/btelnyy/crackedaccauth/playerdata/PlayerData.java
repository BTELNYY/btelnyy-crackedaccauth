package me.btelnyy.crackedaccauth.playerdata;

public class PlayerData {
    //yes, its in plain text. I dont care since its not going to ba an issue if the YML is on a secure server
    public String playerName = "";
    public String lastIP = "";
    public String password = "";

    //bool
    public boolean strictIPRules = false;


    public String getName(){
        return playerName;
    }
}
