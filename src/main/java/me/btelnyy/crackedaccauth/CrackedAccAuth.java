package me.btelnyy.crackedaccauth;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import me.btelnyy.crackedaccauth.constants.ConfigData;
import me.btelnyy.crackedaccauth.listener.EventListener;
import me.btelnyy.crackedaccauth.playerdata.DataHandler;
import me.btelnyy.crackedaccauth.service.file_manager.Configuration;
import me.btelnyy.crackedaccauth.service.file_manager.FileID;
import me.btelnyy.crackedaccauth.service.file_manager.FileManager;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class CrackedAccAuth extends JavaPlugin {

    // An instance of the plugin, so we don't need to make everything static
    private static CrackedAccAuth instance;

    private Configuration config;
    private ConfigData configData;
    private FileManager fileManager;

    private final LinkedList<UUID> systemMessagePlayers = new LinkedList<>();

    @Override
    public void onEnable() {
        // Self-explanatory
        instance = this;

        // Load config
        saveDefaultConfig();
        {
            // Load it into a more user-friendly object
            config = new Configuration(getConfig());

            // Cache it
            configData = new ConfigData();
            configData.load(config);
        }

        // Language
        fileManager = new FileManager(this);
        fileManager.addFile(FileID.LANGUAGE, fileManager.create(null, configData.langFile));
        // Register commands

        // SnakeYAML fix
        Thread.currentThread().setContextClassLoader(this.getClassLoader());

        // generate data folder (if needed)
        DataHandler.GenerateFolder();



        // Register events
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        // GitHub message
        getLogger().info("Check out the project on GitHub! https://github.com/BTELNYY/btelnyy-nochatreport");
    }
    @Override
    public void onDisable(){
        DataHandler.ServerShutdown();
    }

    public void log(Level level, Object message){
        getLogger().log(level, message.toString());
    }
    @SuppressWarnings("unused")
    private void registerCommandExecutor(String commandName, CommandExecutor commandExecutor) {
        PluginCommand command = getCommand(commandName);

        /*
        If the command is null java will trigger an error any ways, the goal with this is to
        not trigger the error, so calling an explicit NullPointerError does not make things any better
         */
        if (command == null) {
            getLogger().severe("The command " + commandName + " could not be registered, please contact the plugin authors " + getDescription().getAuthors());
            return;
        }
        command.setExecutor(commandExecutor);
    }
    @SuppressWarnings("unused")
    private void registerCommandExecutor(String commandName, CommandExecutor commandExecutor, TabCompleter completer) {
        PluginCommand command = getCommand(commandName);

        /*
        If the command is null java will trigger an error any ways, the goal with this is to
        not trigger the error, so calling an explicit NullPointerError does not make things any better
         */
        if (command == null) {
            getLogger().severe("The command " + commandName + " could not be registered, please contact the plugin authors " + getDescription().getAuthors());
            return;
        }
        command.setTabCompleter(completer);
        command.setExecutor(commandExecutor);
    }

    public static CrackedAccAuth getInstance() {
        return instance;
    }

    public ConfigData getConfigData() {
        return configData;
    }

    public List<UUID> getSystemMessagePlayers() {
        return systemMessagePlayers;
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
