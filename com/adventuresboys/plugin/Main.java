package com.adventuresboys.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	static FileCreator config;
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPlugin encendido."));
		config = new FileCreator(this, "config");
		this.getCommand("abu").setExecutor(new Commands());
		this.getCommand("abu add").setExecutor(new Commands());
	}
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlugin apagado."));
	}
}
