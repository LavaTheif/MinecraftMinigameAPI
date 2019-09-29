package com.weebly.kyslol.MinigameAPI.teams;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Deprecated
public class Team{
	private ArrayList<Player> teamMembers = new ArrayList<Player>();
	private Color colour;
	private int kills = 0, intId;
	private String id = "";
	private GameData g;
	private ChatColor chatcolor;
	
	public void add(Player p){
		teamMembers.add(p);
		g.getTm().createDisplay(p.getInventory(), p, g.getType());
		//set inventory
	}
	
	public Team(int c, GameData gd){
		g = gd;
		intId = c;
		if(c == 0){
			colour = Color.BLUE;//new Color(0x0000FF);
			id = ChatColor.BLUE + "Blue team";
			chatcolor = ChatColor.BLUE;
		}else if(c == 1){
			colour = Color.GREEN;//new Color(0x00FF00);
			id = ChatColor.GREEN + "Green team";
			chatcolor = ChatColor.GREEN;

		}else if(c == 2){
			colour = Color.RED;//new Color(0xFF0000);
			id = ChatColor.RED + "Red team";
			chatcolor = ChatColor.RED;

		}else if(c == 3){
			colour = Color.BLACK;
			id = ChatColor.BLACK + "Black team";
			chatcolor = ChatColor.BLACK;

		}else if(c == 4){
			colour = Color.YELLOW;
			id = ChatColor.YELLOW + "Yellow team";
			chatcolor = ChatColor.YELLOW;

		}else if(c == 5){
			colour = Color.SILVER;
			id = ChatColor.GRAY + "Grey team";
			chatcolor = ChatColor.GRAY;

		}else if(c == 6){
			colour = Color.PURPLE;
			id = ChatColor.DARK_PURPLE + "Purple team";
			chatcolor = ChatColor.DARK_PURPLE;

		}
	}
	
	public void createDisplay(Player p) {
		Inventory inv = p.getInventory();
		
		inv.clear();
		
		ItemStack item = new ItemStack(Material.COMPASS);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Server Selector");
		item.setItemMeta(meta);
		inv.setItem(0, item);//Add Sword
	}
	
	public void remove(Player p){
		teamMembers.remove(p);
		createDisplay(p);
	}
	
	public void kill(){
		kills++;
		for(int i = 0; i < g.getSb().size(); i++){
			g.getSb().get(i).kill();
		}
		
	}
	public int kills(){
		return kills;
	}
	public Color getColour(){
		return colour;
	}
	public ArrayList<Player> getMembers(){
		return teamMembers;
	}
	public String getId(){
		return id;
	}
	public int getIntId() {
		return intId;
	}
	public GameData getG() {
		return g;
	}

	public ChatColor getChatcolor() {
		return chatcolor;
	}

	public void setChatcolor(ChatColor chatcolor) {
		this.chatcolor = chatcolor;
	}

}
