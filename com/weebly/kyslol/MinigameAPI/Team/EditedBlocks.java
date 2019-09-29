package com.weebly.kyslol.MinigameAPI.Team;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class EditedBlocks {
	private Location location;
	private Material material;
	private byte data;
	
	@SuppressWarnings("deprecation")
	public EditedBlocks(Block block){
		location = block.getLocation();
		material = block.getType();
		data = block.getData();
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public byte getData() {
		return data;
	}

	public void setData(byte data) {
		this.data = data;
	}

}
