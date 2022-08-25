package com.stinkymonkey.rankup;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class guiManager {
	private main Main;
	public guiManager(main Main) {
		this.Main = Main;
	}
	
	public Inventory gui;
	public Inventory preGui;
	public void openGui(Player p, String nextGroup, double price) {
		gui = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("guiNames.title")).replace("{RANK}", nextGroup));
		ItemStack item = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("guiNames.greenAccept")));
		List<String> lore = new ArrayList<String>();
		for (String s : Main.getConfigManager().getStringList("guiNames.greenLore")) {
			lore.add(ChatColor.translateAlternateColorCodes('&', s).replace("{RANK}", nextGroup).replace("{PRICE}", String.format("%,.2f", price)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		gui.setItem(0, item);
		gui.setItem(1, item);
		gui.setItem(2, item);
		gui.setItem(3, item);
		List<String> empty = new ArrayList<String>();
		empty.add(" ");
		meta.setLore(empty);
		item.setType(Material.BLACK_STAINED_GLASS_PANE);
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		gui.setItem(4, item);
		item.setType(Material.REDSTONE_BLOCK);
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("guiNames.redCancel")));
		item.setItemMeta(meta);
		gui.setItem(5, item);
		gui.setItem(6, item);
		gui.setItem(7, item);
		gui.setItem(8, item);
		
		p.openInventory(gui);
	}
	
	public void openPreGui(Player p, String nextGroup, Double price) {
		preGui = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("guiNames.preTitle")).replace("{RANK}", nextGroup));
		ItemStack item = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("guiNames.greenAccept")));
		List<String> lore = new ArrayList<String>();
		for (String s : Main.getConfigManager().getStringList("guiNames.greenLore")) {
			lore.add(ChatColor.translateAlternateColorCodes('&', s).replace("{RANK}", nextGroup).replace("{PRICE}", String.format("%,.2f", price)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		preGui.setItem(0, item);
		preGui.setItem(1, item);
		preGui.setItem(2, item);
		preGui.setItem(3, item);
		List<String> empty = new ArrayList<String>();
		empty.add(" ");
		meta.setLore(empty);
		item.setType(Material.BLACK_STAINED_GLASS_PANE);
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		preGui.setItem(4, item);
		item.setType(Material.REDSTONE_BLOCK);
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("guiNames.redCancel")));
		item.setItemMeta(meta);
		preGui.setItem(5, item);
		preGui.setItem(6, item);
		preGui.setItem(7, item);
		preGui.setItem(8, item);
		
		p.openInventory(preGui);
	}
}
