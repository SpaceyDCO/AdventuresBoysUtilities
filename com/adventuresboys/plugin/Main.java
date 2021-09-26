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
	/*public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		/*if(command.getName().equalsIgnoreCase("test")) {
			sender.sendMessage(label);
			sender.sendMessage("hola");
		}
		if(!label.equalsIgnoreCase("abu")) {
			return false;
		}
		Commands.abuCommands(args, (Player) sender);
		return true;*/
        /*if (command.getName().equalsIgnoreCase("mycommand")) {
            sender.sendMessage("You ran /mycommand!");
            return true;
        }
        return false;
	}*/
}
