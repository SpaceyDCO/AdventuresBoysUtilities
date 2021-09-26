package com.adventuresboys.plugin;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!label.equalsIgnoreCase("abu")) {
			return true;
		}
		if(args.length < 1) {
			sender.sendMessage("ayuda");
			return true;
		}
		if(args[0].equalsIgnoreCase("add")) {
			sender.sendMessage("agrega pibe");
			if(((Player) sender).getTargetBlock(null, 5) == null) {
				sender.sendMessage("deber mirar");
				return true;
			}
			Block block = ((Player) sender).getTargetBlock(null, 5);
			sender.sendMessage("miras a " + block.getType().name());
			return true;
		}
		return false;
	}
}
