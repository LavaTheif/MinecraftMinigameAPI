package com.weebly.kyslol.MinigameAPI.teams;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.weebly.kyslol.MinigameAPI.Main;

import net.milkbowl.vault.economy.Economy;

@Deprecated
public class TeamMain{
	private ArrayList<GameData> games = new ArrayList<GameData>();
	private boolean beta = false;
	private boolean debug = false;
	private String name;
	private World world;
	
	private Listeners listeners;
	
	public TeamMain(){
		listeners = new Listeners(this);
		
		//EACH SECOND
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), new Runnable() {
			public void run() {
				for (GameData d : games) {
					d.tick();
				}
			}
		}, 0, 20);

		//EACH TICK
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), new Runnable() {
			public void run() {
				for (GameData d : games) {
					d.tick(true);
				}
			}
		}, 0, 1);
	}
	
	public static Economy econ = Main.econ;
	
	public void signClicked(String line2, String line3, Player p){
	}
	
	public boolean join(int game, Player p) {
		for (GameData g : games) {
			if(g.getPlayers().contains(p)){
				p.sendMessage(ChatColor.RED + "You are already in a game");
				return false;
			}
		}
		for (GameData g : games) {
			if (g.getGameId() == game) {
				if (g.add(p)) {
					p.sendMessage(ChatColor.GREEN + "Joining the game!");
				} else {
					if (g.isStart()) {
						p.sendMessage(ChatColor.RED + "Game has already started!");
					} else {
						p.sendMessage(ChatColor.RED + "Game is full!");
					}
				}
				return true;
			}else{
			}
		}
		createGame(game).add(p);
		return false;
//		if (game == 0) {
//			p.sendMessage(ChatColor.RED + "Disabled");
//			new TestMap(p);
//   	}
	}
	
	public void createDisplay(Inventory inv, Player p, int id) {
	}

	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public String _getName() {
		return name;
	}

	public GameData createGame(int id){
		return null;
	}

	public ArrayList<GameData> getGames() {
		return games;
	}

	public void setGames(ArrayList<GameData> games) {
		this.games = games;
	}

	public boolean isBeta() {
		return beta;
	}

	public void setBeta(boolean beta) {
		this.beta = beta;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getGameName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Listeners getListeners() {
		return listeners;
	}

	public void setListeners(Listeners listeners) {
		this.listeners = listeners;
	}

	public static Economy getEcon() {
		return econ;
	}

	public static void setEcon(Economy econ) {
		TeamMain.econ = econ;
	}
}
