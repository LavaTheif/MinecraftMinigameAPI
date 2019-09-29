package com.weebly.kyslol.MinigameAPI.Team;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Team {
	public Team(GameData g, int number) {
		gd = g;
		if (number == 0) {
			COLOUR = ChatColor.DARK_RED;
			TEAM_NAME = "Red";
			ARMOUR_COLOUR = Color.RED;
		} else if (number == 1) {
			COLOUR = ChatColor.DARK_GREEN;
			TEAM_NAME = "Green";
			ARMOUR_COLOUR = Color.GREEN;
		} else if (number == 2) {
			COLOUR = ChatColor.DARK_BLUE;
			TEAM_NAME = "Blue";
			ARMOUR_COLOUR = Color.BLUE;
		} else if (number == 3) {
			COLOUR = ChatColor.YELLOW;
			TEAM_NAME = "Yellow";
			ARMOUR_COLOUR = Color.YELLOW;
		} else if (number == 4) {
			COLOUR = ChatColor.DARK_AQUA;
			TEAM_NAME = "Cyan";
			ARMOUR_COLOUR = Color.AQUA;
		} else if (number == 5) {
			COLOUR = ChatColor.BLACK;
			TEAM_NAME = "Black";
			ARMOUR_COLOUR = Color.BLACK;
		} else if (number == 6) {
			COLOUR = ChatColor.WHITE;
			TEAM_NAME = "White";
			ARMOUR_COLOUR = Color.WHITE;
		} else {
			COLOUR = null;
			TEAM_NAME = null;
			ARMOUR_COLOUR = null;
			gd.error("Max Teams Exceeds Colour Limit.  Automaticaly Decreasing Value");
			gd.setMaxTeams(6);
		}
		lives = gd.getLivesPerTeam();
		KILLS_TO_WIN = gd.getKillsToWin();
		MAX_PLAYERS = gd.getMaxPlayersPerTeam();
		MIN_PLAYERS = gd.getMinPlayersPerTeam();
	}

	private ArrayList<Player> members = new ArrayList<Player>();
	private GameData gd;
	private int kills = 0;
	private int lives;

	private final int KILLS_TO_WIN;
	private final int MAX_PLAYERS;
	private final int MIN_PLAYERS;

	private final ChatColor COLOUR;
	private final String TEAM_NAME;
	private final Color ARMOUR_COLOUR;

	//Storage vars for games
	
	private boolean storageBool0 = false;
	private boolean storageBool1 = false;
	private boolean storageBool2 = false;
	private boolean storageBool3 = false;
	private boolean storageBool4 = false;
	
	private int storageInt0 = 0;
	private int storageInt1 = 0;
	private int storageInt2 = 0;
	private int storageInt3 = 0;
	private int storageInt4 = 0;

	private double storageDouble0 = 0;
	private double storageDouble1 = 0;
	private double storageDouble2 = 0;
	private double storageDouble3 = 0;
	private double storageDouble4 = 0;

	private String storageString0 = "";
	private String storageString1 = "";
	private String storageString2 = "";
	private String storageString3 = "";
	private String storageString4 = "";
	
	public boolean addPlayer(Player p) {
		if (contains(p))
			return false;
		if (members.size() >= MAX_PLAYERS)
			return false;
		members.add(p);

		if (p.getInventory().getHelmet() != null) {
			if (p.getInventory().getHelmet().getType().equals(Material.LEATHER_HELMET)) {
				LeatherArmorMeta meta = (LeatherArmorMeta) p.getInventory().getHelmet().getItemMeta();
				meta.setColor(ARMOUR_COLOUR);
				p.getInventory().getHelmet().setItemMeta(meta);
			}
		}
		if (p.getInventory().getChestplate() != null) {

			if (p.getInventory().getChestplate().getType().equals(Material.LEATHER_CHESTPLATE)) {
				LeatherArmorMeta meta = (LeatherArmorMeta) p.getInventory().getChestplate().getItemMeta();
				meta.setColor(ARMOUR_COLOUR);
				p.getInventory().getChestplate().setItemMeta(meta);
			}
		}
		if (p.getInventory().getLeggings() != null) {

			if (p.getInventory().getLeggings().getType().equals(Material.LEATHER_LEGGINGS)) {
				LeatherArmorMeta meta = (LeatherArmorMeta) p.getInventory().getLeggings().getItemMeta();
				meta.setColor(ARMOUR_COLOUR);
				p.getInventory().getLeggings().setItemMeta(meta);
			}
		}
		if (p.getInventory().getBoots() != null) {

			if (p.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
				LeatherArmorMeta meta = (LeatherArmorMeta) p.getInventory().getBoots().getItemMeta();
				meta.setColor(ARMOUR_COLOUR);
				p.getInventory().getBoots().setItemMeta(meta);
			}
		}

		new SidebarUpdateEvent(SideBar.TEAM_COUNT, gd);
		return true;
	}

	public void removePlayer(Player p) {
		if (!contains(p))
			return;
		members.remove(p);
		new SidebarUpdateEvent(SideBar.TEAM_COUNT, gd);
		if (members.size() <= 0) {
			gd.getTeams().remove(this);
		}
	}

	public boolean contains(Player p) {
		return members.contains(p);
	}

	public void killRecieved() {
		new SidebarUpdateEvent(SideBar.TEAM_KILLS, gd);
		kills++;
	}
	// public void _tick(){//TO DO Make tick
	// if(kills >= KILLS_TO_WIN){
	// //TO DO WIN
	// }
	// }
	// public void _sec(){
	//
	// }

	public void removeLife() {
		new SidebarUpdateEvent(SideBar.TEAM_LIVES, gd);
		lives--;
	}

	// GETTERS AND SETTERS
	public ArrayList<Player> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<Player> members) {
		this.members = members;
	}

	public GameData getGd() {
		return gd;
	}

	public void setGd(GameData gd) {
		this.gd = gd;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		new SidebarUpdateEvent(SideBar.TEAM_KILLS, gd);
		this.kills = kills;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		new SidebarUpdateEvent(SideBar.TEAM_LIVES, gd);
		this.lives = lives;
	}

	public boolean isStorageBool0() {
		return storageBool0;
	}

	public boolean isStorageBool1() {
		return storageBool1;
	}

	public boolean isStorageBool2() {
		return storageBool2;
	}

	public boolean isStorageBool3() {
		return storageBool3;
	}

	public boolean isStorageBool4() {
		return storageBool4;
	}

	public int getStorageInt0() {
		return storageInt0;
	}

	public int getStorageInt1() {
		return storageInt1;
	}

	public int getStorageInt2() {
		return storageInt2;
	}

	public int getStorageInt3() {
		return storageInt3;
	}

	public int getStorageInt4() {
		return storageInt4;
	}

	public double getStorageDouble0() {
		return storageDouble0;
	}

	public double getStorageDouble1() {
		return storageDouble1;
	}

	public double getStorageDouble2() {
		return storageDouble2;
	}

	public double getStorageDouble3() {
		return storageDouble3;
	}

	public double getStorageDouble4() {
		return storageDouble4;
	}

	public String getStorageString0() {
		return storageString0;
	}

	public String getStorageString1() {
		return storageString1;
	}

	public String getStorageString2() {
		return storageString2;
	}

	public String getStorageString3() {
		return storageString3;
	}

	public String getStorageString4() {
		return storageString4;
	}

	public void setStorageBool0(boolean storageBool0) {
		this.storageBool0 = storageBool0;
	}

	public void setStorageBool1(boolean storageBool1) {
		this.storageBool1 = storageBool1;
	}

	public void setStorageBool2(boolean storageBool2) {
		this.storageBool2 = storageBool2;
	}

	public void setStorageBool3(boolean storageBool3) {
		this.storageBool3 = storageBool3;
	}

	public void setStorageBool4(boolean storageBool4) {
		this.storageBool4 = storageBool4;
	}

	public void setStorageInt0(int storageInt0) {
		this.storageInt0 = storageInt0;
	}

	public void setStorageInt1(int storageInt1) {
		this.storageInt1 = storageInt1;
	}

	public void setStorageInt2(int storageInt2) {
		this.storageInt2 = storageInt2;
	}

	public void setStorageInt3(int storageInt3) {
		this.storageInt3 = storageInt3;
	}

	public void setStorageInt4(int storageInt4) {
		this.storageInt4 = storageInt4;
	}

	public void setStorageDouble0(double storageDouble0) {
		this.storageDouble0 = storageDouble0;
	}

	public void setStorageDouble1(double storageDouble1) {
		this.storageDouble1 = storageDouble1;
	}

	public void setStorageDouble2(double storageDouble2) {
		this.storageDouble2 = storageDouble2;
	}

	public void setStorageDouble3(double storageDouble3) {
		this.storageDouble3 = storageDouble3;
	}

	public void setStorageDouble4(double storageDouble4) {
		this.storageDouble4 = storageDouble4;
	}

	public void setStorageString0(String storageString0) {
		this.storageString0 = storageString0;
	}

	public void setStorageString1(String storageString1) {
		this.storageString1 = storageString1;
	}

	public void setStorageString2(String storageString2) {
		this.storageString2 = storageString2;
	}

	public void setStorageString3(String storageString3) {
		this.storageString3 = storageString3;
	}

	public void setStorageString4(String storageString4) {
		this.storageString4 = storageString4;
	}

	public int getKILLS_TO_WIN() {
		return KILLS_TO_WIN;
	}

	public int getMAX_PLAYERS() {
		return MAX_PLAYERS;
	}

	public int getMIN_PLAYERS() {
		return MIN_PLAYERS;
	}

	public ChatColor getCOLOUR() {
		return COLOUR;
	}

	public String getTEAM_NAME() {
		return TEAM_NAME;
	}

	public Color getARMOUR_COLOUR() {
		return ARMOUR_COLOUR;
	}

}
