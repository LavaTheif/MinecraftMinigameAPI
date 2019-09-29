package com.weebly.kyslol.MinigameAPI.Team;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Kit {
	private Inventory inventory = Bukkit.createInventory(null, 4*9);
	private ItemStack head = new ItemStack(Material.LEATHER_HELMET);
	private ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
	private ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
	private ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
	

	
	public Kit(ItemStack[] items){
		inventory.setContents(items);
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public ItemStack getHead() {
		return head;
	}

	public void setHead(ItemStack head) {
		this.head = head;
	}

	public ItemStack getChest() {
		return chest;
	}

	public void setChest(ItemStack chest) {
		this.chest = chest;
	}

	public ItemStack getLegs() {
		return legs;
	}

	public void setLegs(ItemStack legs) {
		this.legs = legs;
	}

	public ItemStack getBoots() {
		return boots;
	}

	public void setBoots(ItemStack boots) {
		this.boots = boots;
	}
	
}
