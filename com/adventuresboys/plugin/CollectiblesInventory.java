package com.adventuresboys.plugin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CollectiblesInventory implements Listener {
	private final Inventory INVENTORY;
	private final Player PLAYER;
	private final FileCreator config = Main.config;
	public CollectiblesInventory() {
		this.PLAYER = null;
		this.INVENTORY = null;
	}
	public CollectiblesInventory(Player player) {
		this.INVENTORY = Bukkit.createInventory(new CollectiblesHolder(), 27, "AdventuresBoys");
		this.PLAYER = player;
		putItems();
	}
	private void putItems() {
		for(int i = 0; i < 27; i++) {
			if(i == 11 || i == 13 || i == 15) i++;
			INVENTORY.setItem(i, createGuiItem(Material.STAINED_GLASS_PANE, " ", (short) 0));
		}
		final int blocksFound = config.getInt("Eventos.Jugadores." + PLAYER.getUniqueId() + ".TotalBloques");
		final int totalBlocks = config.getInt("Eventos.Bloques.AddedBlocks");
		final int leftBlocks = (totalBlocks - blocksFound);
		INVENTORY.setItem(11, createGuiItem(Material.SIGN, "§lProximamente...", (short) 0));
		INVENTORY.setItem(13, createGuiItem(Material.COMPASS, "§7Coleccionables encontrados", (short) 0, "§a" + blocksFound + "§f/§a" + totalBlocks, "§a" + leftBlocks + " §7coleccionables faltantes por encontrar!"));
		INVENTORY.setItem(15, createGuiItem(Material.GOLD_ORE, "§lProximamente...", (short) 0));
	}
	private ItemStack createGuiItem(final Material material, final String name, final short dur, final String... lore) {
		final ItemStack item = new ItemStack(material, 1, dur);
		final ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}
	protected void openInventory() {
		PLAYER.openInventory(INVENTORY);
	}
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event) {
		if(!(event.getInventory().getHolder() instanceof CollectiblesHolder)) return;
		event.setCancelled(true);
		final ItemStack clickedItem = event.getCurrentItem();
		if(clickedItem == null || clickedItem.getType().name().equalsIgnoreCase("air")) return;
	}
	@EventHandler
	public void onInventoryDrag(final InventoryDragEvent event) {
		if(event.getInventory().getHolder() instanceof CollectiblesHolder) {
			event.setCancelled(true);
		}
	}
}
