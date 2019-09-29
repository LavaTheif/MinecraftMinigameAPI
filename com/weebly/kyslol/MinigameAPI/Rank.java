package com.weebly.kyslol.MinigameAPI;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class Rank {
	public static final Permission OWNER = new Permission("rank.owner");
	public static final Permission HEAD_ADMIN = new Permission("rank.head-admin");
	public static final Permission BLUE = new Permission("rank.blue");
	public static final Permission ADMIN = new Permission("rank.admin");
	public static final Permission MOD = new Permission("rank.mod");
	public static final Permission HEAD_BUILDER = new Permission("rank.head-builder");
	public static final Permission BUILDER = new Permission("rank.builder");
	public static final Permission TRAINEE = new Permission("rank.trainee");
	public static final Permission TRAINEE_BUILDER = new Permission("rank.trainee-builder");
	public static final Permission TITAN = new Permission("rank.titan");
	public static final Permission PLATINUM = new Permission("rank.platinum");
	public static final Permission YOUTUBE = new Permission("rank.youtube");
	public static final Permission ULTRA = new Permission("rank.ultra");
	public static final Permission PREMUIM = new Permission("rank.premium");
	public static final Permission DEFAULT = new Permission("rank.default");
	
	private static ArrayList<Permission> perms = new ArrayList<Permission>();
	public static void _INIT_(){
		perms.add(OWNER);
		perms.add(BLUE);
		perms.add(HEAD_ADMIN);
		perms.add(ADMIN);
		perms.add(MOD);
		perms.add(HEAD_BUILDER);
		perms.add(BUILDER);
		perms.add(TRAINEE);
		perms.add(TRAINEE_BUILDER);
		perms.add(TITAN);
		perms.add(PLATINUM);
		perms.add(YOUTUBE);
		perms.add(ULTRA);
		perms.add(PREMUIM);
		perms.add(DEFAULT);
	}
	public static boolean min(Permission rank, Player p){ //Has permission rank or higher
		
		for(int i = 0; i < perms.size(); i++){
			if(p.hasPermission(perms.get(i)))return true;
			if(perms.get(i).equals(rank)) return false;
		}
		
		return false;
	}
}
