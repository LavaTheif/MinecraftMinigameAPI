package com.weebly.kyslol.MinigameAPI.Team;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class SidebarUpdateEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private String toUpdate;
	private GameData gd;
	
	public SidebarUpdateEvent(String toUpdate, GameData gd){
		this.toUpdate = toUpdate;
		this.gd = gd;
		Bukkit.getServer().getPluginManager().callEvent(this);
	}
	
	public String gettoUpdate() {
		return toUpdate;
	}

	public void setGameName(String toUpdate) {
		this.toUpdate = toUpdate;
	}

	public GameData getGd() {
		return gd;
	}

	public void setGd(GameData gd) {
		this.gd = gd;
	}

	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
