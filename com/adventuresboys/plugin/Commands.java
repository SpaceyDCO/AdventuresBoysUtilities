package com.adventuresboys.plugin;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{
	FileCreator config = Main.config;
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.isOp() || !label.equalsIgnoreCase("abu") || !(sender instanceof Player)) {
			return true;
		}
		if(args.length < 1) {
			sender.sendMessage("ayuda");
			return true;
		}
		if(args[0].equalsIgnoreCase("add")) {
			if(args.length < 2) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "Debes especificar un nombre!"));
				return true;
			}
			if(((Player) sender).getTargetBlock(null, 5) == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDebes mirar a un bloque."));
				return true;
			}
			Block block = ((Player) sender).getTargetBlock(null, 5);
			if(block.getType().name().equals("AIR")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo puedes agregar aire!"));
				return true;
			}
			if(config.contains("Eventos.Bloques." + args[1])) {
				sender.sendMessage("bloque ya existe");
				return true;
			}
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			short dur = block.getType().getMaxDurability();
			String[] arrayBlock = {block.getType().name(), String.valueOf(x), String.valueOf(y), String.valueOf(z), String.valueOf(dur)};
			config.set("Eventos." + "Bloques." + args[1], block.getType().name());
			config.save();
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAgregado bloque " + block + " con éxito."));
			return true;
		}
		return false;
	}
}
