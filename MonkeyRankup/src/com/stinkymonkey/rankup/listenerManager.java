package com.stinkymonkey.rankup;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class listenerManager implements Listener {
	private main Main;
	public listenerManager (main Main) {
		this.Main = Main;
	}
	
	@EventHandler
	public void guiEvent(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		if (event.getCurrentItem() == null) return;
		if (event.getCurrentItem().getItemMeta() == null) return;
		if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		if (event.getInventory().equals(Main.getGuiManager().gui)) {
			event.setCancelled(true);
			if (event.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
				Main.getCommandManager().rankUP(p);
			}
			if (event.getCurrentItem().getType().equals(Material.REDSTONE_BLOCK)) {
				p.closeInventory();
			}
		} else if (event.getInventory().equals(Main.getGuiManager().preGui)) {
			event.setCancelled(true);
			if (event.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
				Main.getCommandManager().prestigeUP(p);
			}
			if (event.getCurrentItem().getType().equals(Material.REDSTONE_BLOCK)) {
				p.closeInventory();
			}
		}
	}
}
