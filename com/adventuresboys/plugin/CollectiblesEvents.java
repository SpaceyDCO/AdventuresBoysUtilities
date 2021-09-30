package com.adventuresboys.plugin;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class CollectiblesEvents implements Listener{
	FileCreator config = Main.config;
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getAction().toString().equalsIgnoreCase("RIGHT_CLICK_BLOCK") && event.getHand().toString().equalsIgnoreCase("HAND")) {
			Block block = event.getClickedBlock();
			String arrayBlock = Commands.blockConfig(block);
			for(int i = 0; i < config.getInt("Eventos.Bloques.AddedBlocks"); i++) {
				String[] arrayBlockCheck = config.getString("Eventos.Bloques." + i).split(":");;
				if(Commands.checkEventBlock(arrayBlock.split(":"), arrayBlockCheck)) {
					//event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "Encontraste bloque " + i));
					int addb = config.getInt("Eventos.Jugadores." + event.getPlayer().getUniqueId() + ".TotalBloques");
					if(addb < 1) {
						config.set("Eventos.Jugadores." + event.getPlayer().getUniqueId() + ".TotalBloques", 1);
						addb = 1;
						config.set("Eventos.Jugadores." + event.getPlayer().getUniqueId() + ".Bloques." + (addb-1), arrayBlock);
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aEncontrado bloque."));
						config.save();
					}else if(checkPlayerFoundBlock(arrayBlock, (Player) event.getPlayer(), addb)) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYa encontraste este bloque!"));
					}else {
						config.set("Eventos.Jugadores." + event.getPlayer().getUniqueId() + ".TotalBloques", addb+1);
						config.set("Eventos.Jugadores." + event.getPlayer().getUniqueId() + ".Bloques." + addb, arrayBlock);
						config.save();
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aEncontrado bloque."));
					}
				}
			}
			//Is block registered?
		}
	}
	public boolean checkPlayerFoundBlock(String arrayBlock, Player player, int addb) {
		for(int i = 0; i < addb; i++) {
			String[] checkBlock = config.getString("Eventos.Jugadores." + player.getUniqueId() + ".Bloques." + i).split(":");
			if(Commands.checkEventBlock(arrayBlock.split(":"), checkBlock)) {
				return true;
			}
		}
		return false;
	}
	//Has the block been found?
}
