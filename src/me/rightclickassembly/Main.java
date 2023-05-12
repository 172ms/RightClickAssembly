package me.rightclickassembly;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
        String version = getServer().getClass().getPackage().getName().split("\\.")[3];
        
        try {
            Class<?> playerInteractClass = Class.forName("me.rightclickassembly.version." + version);
            
            PluginManager pluginManager = getServer().getPluginManager();
            Listener playerInteractInstance = (Listener)playerInteractClass.newInstance();
            
            pluginManager.registerEvents(playerInteractInstance, this);
        }
        catch (Exception e) {
        	getServer().getConsoleSender().sendMessage("§e[!] §cYOUR VERSION " + Bukkit.getVersion() + " NOT SUPPORTED!");
        }
    }
	
	@Override
	public void onDisable() {}
}