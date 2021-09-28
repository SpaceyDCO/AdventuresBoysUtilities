package com.adventuresboys.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
		if(args[0].equalsIgnoreCase("addBlock")) {
			Block block = ((Player) sender).getTargetBlock(null, 5);
			if(block.getType().name().equals("AIR")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo puedes agregar aire!"));
				return true;
			}
			String arrayBlock = blockConfig(block);
			int addb = config.getInt("Eventos.Bloques.AddedBlocks");
			for(int i = 0; i < addb; i++) {
				String[] arrayBlockCheck = config.getString("Eventos.Bloques." + i).split(":");;
				if(checkEventBlock(arrayBlock.split(":"), arrayBlockCheck)) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEste bloque ya se encuentra agregado."));
					return true;
				}
			}
			if(addb < 1) {
				config.set("Eventos.Bloques.AddedBlocks", 1);
			}else {
				config.set("Eventos.Bloques.AddedBlocks", addb+1);
			}
			config.set("Eventos.Bloques." + addb, arrayBlock);
			config.save();
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAgregado bloque " + block + " con éxito."));
			return true;
		}
		if(args[0].equalsIgnoreCase("removeBlock")) {
			Block block = ((Player) sender).getTargetBlock(null, 5);
			int addb = config.getInt("Eventos.Bloques.AddedBlocks");
			String arrayBlock = blockConfig(block);
			for(int i = 0; i < addb; i++) {
				String[] arrayBlockCheck = config.getString("Eventos.Bloques." + i).split(":");
				if(checkEventBlock(arrayBlock.split(":"), arrayBlockCheck)) {
					int rmvb = config.getInt("Eventos.Bloques.AddedBlocks")-1;
					config.set("Eventos.Bloques.AddedBlocks", rmvb);
					if(i < rmvb) {
						String arrayBlock2 = config.getString("Eventos.Bloques." + rmvb);
						config.set("Eventos.Bloques." + i, arrayBlock2);
						config.set("Eventos.Bloques." + rmvb, null);
					}else {
						config.set("Eventos.Bloques." + i, null);
					}
					config.save();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cBloque removido con éxito!"));
					return true;
				}
			}
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEste bloque no se encuentra agregado en la config!"));
			return true;
		}
		if(args[0].equalsIgnoreCase("totalBlocks")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "-----------------------------------------------------"));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aHay &2" + config.getInt("Eventos.Bloques.AddedBlocks") + " &abloques registrados!"));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPuedes usar el comando &2/abu tpBlock id &a para teletransportarte a la localización de un bloque agregado (el 0 cuenta como bloque)"));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "-----------------------------------------------------"));
			return true;
		}
		if(args[0].equalsIgnoreCase("tpBlock")) {
			if(args.length < 2) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDebes especificar una ID (recuerda que el 0 va incluido)"));
				return true;
			}
			try {
				int id = Integer.valueOf(args[1]);
				if(config.contains("Eventos.Bloques." + id)) {
					String[] block = config.getString("Eventos.Bloques." + id).split(":");
					Double y = Double.valueOf(block[2]);
					y++;
					Location loc = new Location(Bukkit.getWorld(block[0]), Double.valueOf(block[1]), y, Double.valueOf(block[3]));
					((Player) sender).teleport(loc);
					return true;
				}else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo hay un bloque con ese id."));
					return true;
				}
			}catch(NumberFormatException error) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "La id debe ser un número entero."));
				return true;
			}
		}
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEse comando no existe!"));
		return false;
	}
	public static boolean checkEventBlock(String[] arrayBlock, String[] arrayBlockCheck) {
		if(arrayBlock[0].equals(arrayBlockCheck[0])) {
			int o = 0;
			for(int i = 1; i < arrayBlock.length-1; i++) {
				if(arrayBlock[i].equals(arrayBlockCheck[i])) {
					o++;
				}
			}
			if(o >= 3 && arrayBlock[4].equals(arrayBlockCheck[4])) {
				return true;
			}
		}
		return false;
	}
	public String blockConfig(Block block) {
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		short dur = block.getType().getMaxDurability();
		return block.getWorld().getName() + ":" + String.valueOf(x) + ":" + String.valueOf(y) + ":" + String.valueOf(z) + ":" + String.valueOf(dur);
	}
}
