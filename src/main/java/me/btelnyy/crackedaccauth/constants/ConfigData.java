package me.btelnyy.crackedaccauth.constants;

import me.btelnyy.crackedaccauth.service.file_manager.Configuration;

public class ConfigData {
    private static ConfigData instance;
    //string
    public String langFile = "language.yml";
    public String defaultPassword = "password"; //its a default password.

    //bool
    public boolean enforcePasswordRequirement = false;

    //int
    public Integer maxPasswordAtemmpts = 3;
    public Integer pwEntryTimer = 15;

    public void load(Configuration config) {
        instance = this;
        enforcePasswordRequirement = config.getBoolean("enforce_password");
        defaultPassword = config.getString("default_password");
        langFile = config.getString("lang_file");
        maxPasswordAtemmpts = config.getInteger("max_pw_attempts");
        pwEntryTimer = config.getInteger("pw_entry_timer");
    }
    public static ConfigData getInstance(){
        return instance;
    }
}
