package com.stinkymonkey.rankup;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.stinkymonkey.rankup.commandManager;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class main extends JavaPlugin{
	// Utility
	public static Permission perms = null;
	public static Economy econ = null;
	public static FileConfiguration rankF;
	private static configManager cM;
	private static commandManager coM;
	private static guiManager gM;
	
	public static HashMap<String, String> rankHash = new HashMap<String, String>();
	public static HashMap<String, String> preHash = new HashMap<String, String>();
	@Override
	public void onEnable() {
		cM = new configManager(this);
		if (!setupEconomy()) {
			this.getConfigManager().sendConsole("Warning! Vault Is Not Found!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		// CHANGE TO FALSE LATER!
		this.saveResource("ranks.yml", false);
		setupPermissions();
		loadRanks();
		
		// Registery
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new listenerManager(this), this);
		coM = new commandManager(this);
		getCommand("rankup").setExecutor(coM);
		getCommand("prestige").setExecutor(coM);
		gM = new guiManager(this);
		
		// constructors
		
		// Command Constructors
	}
	
	@Override
	public void onDisable() {
		
	}
	
	// SET UP VAULT
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null)
			return false;
		econ = rsp.getProvider();
		this.getConfigManager().sendConsole("Using Economy System: " + rsp.getProvider().getName());
		return econ != null;
	}
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		this.getConfigManager().sendConsole("Using Permission System: " + rsp.getProvider().getName());
		return perms != null;
	}
	
	public void addGroup(UUID uid, String groupName) {
		Objects.requireNonNull(uid);
	    Objects.requireNonNull(groupName);
	    perms.playerAddGroup(null, Bukkit.getOfflinePlayer(uid), groupName);
	}
	
	public void removeGroup(UUID uid, String groupName) {
		Objects.requireNonNull(uid);
	    Objects.requireNonNull(groupName);
	    perms.playerRemoveGroup(null, Bukkit.getOfflinePlayer(uid), groupName);
	}
	
	public boolean inGroup(UUID uid, String groupName) {
		Objects.requireNonNull(uid);
		Objects.requireNonNull(groupName);
		String[] playerGroups = perms.getPlayerGroups(null, Bukkit.getOfflinePlayer(uid));
		for (String playerGroup : playerGroups) {
			if (groupName.equalsIgnoreCase(playerGroup))
				return true;
		}
		return false;
	}
	
	public String[] getRank(UUID uid) {
		Objects.requireNonNull(uid);
		String[] playerGroups = perms.getPlayerGroups(null, Bukkit.getOfflinePlayer(uid));
		return playerGroups;
	}
	
	public void loadRanks() {
		File rankFile = new File("plugins" + System.getProperty("file.separator") + this.getDescription().getName() + System.getProperty("file.separator") + "ranks.yml");
		if (!rankFile.exists()) {
			this.getConfigManager().sendConsole("ranks.yml COULD NOT BE FOUND! RESTART OR CREATE A NEW ONE!");
			return;
		}
		try {
			rankF = YamlConfiguration.loadConfiguration(rankFile);
			int index = 0;
			while (rankF.contains("ranks.rank" + Integer.toString(index))) {
				rankHash.put(rankF.getString("ranks.rank" + Integer.toString(index) + ".group"), "rank" + Integer.toString(index));
				index++;
			}
			index = 0;
			while (rankF.contains("prestige.pre" + Integer.toString(index))) {
				preHash.put(rankF.getString("prestige.pre" + Integer.toString(index) + ".preGroup"), "pre" + Integer.toString(index));
				index++;
			}
		} catch (Exception e) {
			this.getConfigManager().sendConsole("NOT ALL RANKS OR PRESETS ARE FILLED OUT PLEASE FOLLOW THE FORMAT!");
		}
	}
	
	// GETTERS
	public configManager getConfigManager() {
		return cM;
	}
	public guiManager getGuiManager() {
		return gM;
	}
	public commandManager getCommandManager() {
		return coM;
	}
}
