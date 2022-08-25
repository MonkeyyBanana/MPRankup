package com.stinkymonkey.rankup;

import java.io.File;
import java.util.List;

import com.stinkymonkey.rankup.main;

import net.md_5.bungee.api.ChatColor;

public class configManager {
	private main Main;
	public configManager(main Main) {
		this.Main = Main;
		loadConfig();
	}
	
	public void loadConfig() {
		File pluginFolder = new File("plugins" + System.getProperty("file.separator") + Main.getDescription().getName());
		if (pluginFolder.exists() == false) {
	    	pluginFolder.mkdir();
	    	System.out.println("");
	    }
			
		File configFile = new File("plugins" + System.getProperty("file.separator") + Main.getDescription().getName() + System.getProperty("file.separator") + "config.yml");
		if (configFile.exists() == false) {
			Main.saveDefaultConfig();
	    	System.out.println("");
		}
	    	
	    try {
	    	Main.getConfig().load(configFile);
	    	System.out.println("[MonkeyRankup] Successfully Loaded Config!");
	    } catch (Exception e) {
			e.printStackTrace();
			System.out.println(ChatColor.RED + "[MonkeyRankup] Bruh What You Do?");
	    }
	}
	
	public String getString(String key) {
		if (!Main.getConfig().contains(key)) {
			System.out.println("[MonkeyRankup] Could Not Locate " + key + " In The 'config.yml' Inside Of The " + Main.getDescription().getName() + " Folder! (DELETE THE CURRENT ONE TO RESET TO DEFAULT!)");
			return "errorCoultNotLocateInConfigYml: " + key;
		} else {
			return Main.getConfig().getString(key).replaceAll("&", "§");
		}
	}
	
	public List<String> getStringList(String key) {
		if (!Main.getConfig().contains(key)) {
			System.out.println("[MonkeyRankup] Could Not Locate \" + key + \" In The 'config.yml' Inside Of The \" + Main.getDescription().getName() + \" Folder! (DELETE THE CURRENT ONE TO RESET TO DEFAULT!)");
			return null;
		} else {
			return Main.getConfig().getStringList(key);
		}
	}
	
	public void sendConsole(String str) {
		if (!Main.getConfig().contains("basic.consolePrefix")) {
			System.out.println("[MonkeyRankup] Could Not Locate \" + key + \" In The 'config.yml' Inside Of The \" + Main.getDescription().getName() + \" Folder! (DELETE THE CURRENT ONE TO RESET TO DEFAULT!)");
			return;
		} else {
			System.out.println(ChatColor.translateAlternateColorCodes('&', Main.getConfig().getString("basic.consolePrefix") + str));
		}
	}
}
