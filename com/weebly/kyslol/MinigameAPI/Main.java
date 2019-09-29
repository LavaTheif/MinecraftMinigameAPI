package com.weebly.kyslol.MinigameAPI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	public static Economy econ = null;
	public static Main main;
	public static ArrayList<Plugin> hooked = new ArrayList<Plugin>();

	public static void hook(Plugin p) {
		if(!hooked.contains(p)){
			hooked.add(p);
		}
		if(!com.weebly.kyslol.reload.Main.hooked.contains(p)){
			com.weebly.kyslol.reload.Main.hook(p);
		}
	}
	public static void unhook(Plugin p) {
		if(hooked.contains(p)){
			hooked.remove(p);
		}
		if(com.weebly.kyslol.reload.Main.hooked.contains(p)){
			com.weebly.kyslol.reload.Main.unhook(p);
		}
	}

	public static Main getMain() {
		return main;
	}

	@Override
	public void onEnable() {
		setupEconomy(this);
		main = this;
	}

	private boolean setupEconomy(Main plugin) {
		RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			econ = economyProvider.getProvider();
		}

		return (econ != null);
	}

	@Override
	public void onDisable() {
		for (Plugin p : hooked) {
			Bukkit.getPluginManager().disablePlugin(p);
		}
	}
}
