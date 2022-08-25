package com.stinkymonkey.rankup;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class commandManager implements CommandExecutor {
	private main Main;
	public commandManager(main Main) {
		this.Main = Main;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("rankup")) {
			Player p = (Player) sender;
			rankUp(p);
		} else if (label.equalsIgnoreCase("prestige")) {
			Player p = (Player) sender;
			prestigeUp(p);
		}
		return false;
	}
	
	public void rankUp(Player p) {
		String[] rank = Main.getRank(p.getUniqueId());
		String position = null;
		String prePosition = null;
		int max = 0;
		int index = 0;
		while (main.rankF.contains("prestige.pre" + Integer.toString(index))) {
			max = index;
			index++;
		}
		for (String s : rank) {
			if (main.rankHash.containsKey(s))
				position = main.rankHash.get(s);
			if (main.preHash.containsKey(s)) {
				prePosition = main.preHash.get(s);
			}
			if (s.equals(main.rankF.getString("prestige.pre" + Integer.toString(max) + ".nextPreGroup"))) {
				prePosition = "break";
			}
		}
		if (prePosition == "break") {
			prePosition = "pre" + Integer.toString(max + 1);
		}
		
		if (position == null) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("rankup.must-prestige")));
			return;
		}
		//if (main.rankHash.containsValue("rank" + Integer.toString(Integer.parseInt(position.replace("rank", "")) + 1))) {
			double balance = main.econ.getBalance(p);
			double price = main.rankF.getDouble("ranks." + position + ".price");
			if (prePosition != null && prePosition != " ")
				price = main.rankF.getDouble("ranks." + position + ".price") * (1 + (main.rankF.getDouble("prestige.pre" + Integer.toString(Integer.parseInt(prePosition.substring(3)) - 1) + ".multiplier") / 100));
			if (balance >= price) {
				Main.getGuiManager().openGui(p, main.rankF.getString("ranks." + position + ".nextGroup"), price);
			} else {
				String need = String.format("%,.2f", (price - balance));
				String needMsg = ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("rankup.requirements-not-met")).replace("{MONEY}", need);
				p.sendMessage(needMsg);
			}
		//}
	}
	
	public void rankUP(Player p) {
		String[] rank = Main.getRank(p.getUniqueId());
		String position = null;
		String prePosition = null;
		int max = 0;
		int index = 0;
		while (main.rankF.contains("prestige.pre" + Integer.toString(index))) {
			max = index;
			index++;
		}
		for (String s : rank) {
			if (main.rankHash.containsKey(s)) {
				position = main.rankHash.get(s);
			}
			if (main.preHash.containsKey(s)) {
				prePosition = main.preHash.get(s);
			}
			if (s.equals(main.rankF.getString("prestige.pre" + Integer.toString(max) + ".nextPreGroup"))) {
				prePosition = "break";
			}
		}
		if (prePosition == "break") {
			prePosition = "pre" + Integer.toString(max + 1);
		}
		if (prePosition != null && prePosition != " ") {
			double price = main.rankF.getDouble("ranks." + position + ".price") * (1 + (main.rankF.getDouble("prestige.pre" +  Integer.toString(Integer.parseInt(prePosition.substring(3)) - 1) + ".multiplier") / 100));
			main.econ.withdrawPlayer(p, price);
		} else {
			main.econ.withdrawPlayer(p, main.rankF.getDouble("ranks." + position + ".price"));
		}
		Main.removeGroup(p.getUniqueId(), main.rankF.getString("ranks." + position + ".group"));
		Main.addGroup(p.getUniqueId(), main.rankF.getString("ranks." + position + ".nextGroup"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("rankup.success-private")).replace("{RANK}", main.rankF.getString("ranks." + position + ".nextGroup")));
		for (Player players : Bukkit.getOnlinePlayers()) {
			players.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("rankup.success-public")).replace("{PLAYER}", p.getName()).replace("{RANK}", main.rankF.getString("ranks." + position + ".nextGroup")));
		}
		p.closeInventory();
	}
	
	public void prestigeUp(Player p) {
		String[] rank = Main.getRank(p.getUniqueId());
		String position = null;
		String prePosition = null;
		int max = 0;
		int index = 0;
		while (main.rankF.contains("prestige.pre" + Integer.toString(index))) {
			max = index;
			index++;
		}
		for (String s : rank) {
			if (main.rankHash.containsKey(s)) {
				position = main.rankHash.get(s);
			}
			if (main.preHash.containsKey(s)) {
				prePosition = main.preHash.get(s);
			}
			if (s.equals(main.rankF.getString("prestige.pre" + Integer.toString(max) + ".nextPreGroup"))) {
				prePosition = "break";
				break;
			}
		}
		if (prePosition == null)
			prePosition = "pre0";
		if (position != null) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("prestige.not-high-enough")));
			return;
		}
		if (prePosition.equals("break")) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("prestige.no-prestige")));
			return;
		}
		double balance = main.econ.getBalance(p);
		double price = main.rankF.getDouble("prestige." + prePosition + ".price");
		if (balance < price) {
			String need = String.format("%,.2f", (price - balance));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("prestige.requirements-not-met")).replace("{MONEY}", need));
		} else {
			Main.getGuiManager().openPreGui(p, main.rankF.getString("prestige." + prePosition + ".nextPreGroup"), price);
		}
	}
	
	public void prestigeUP(Player p) {
		String[] rank = Main.getRank(p.getUniqueId());
		String position = null;
		String prePosition = null;
		int max = 0;
		int index = 0;
		while (main.rankF.contains("ranks.rank" + Integer.toString(index))) {
			max = index;
			index++;
		}
		for (String s : rank) {
			if (main.preHash.containsKey(s)) {
				prePosition = main.preHash.get(s);
			}
		}
		position = main.rankF.getString("ranks.rank" + Integer.toString(max) + ".nextGroup");
		if (prePosition == null)
			prePosition = "pre0";
		main.econ.withdrawPlayer(p, main.rankF.getDouble("prestige." + prePosition + ".price"));
		Main.removeGroup(p.getUniqueId(), main.rankF.getString("prestige." + prePosition + ".preGroup"));
		Main.addGroup(p.getUniqueId(), main.rankF.getString("prestige." + prePosition + ".group"));
		Main.addGroup(p.getUniqueId(), main.rankF.getString("prestige." + prePosition + ".nextPreGroup"));
		Main.removeGroup(p.getUniqueId(), position);
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("prestige.success-private")).replace("{PRESTIGE}", main.rankF.getString("prestige." + prePosition + ".nextPreGroup")));
		for (Player players : Bukkit.getOnlinePlayers()) {
			players.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getString("prestige.success-public")).replace("{PRESTIGE}", main.rankF.getString("prestige." + prePosition + ".nextPreGroup")).replace("{PLAYER}", p.getName()));
		}
		p.closeInventory();
	}
}
