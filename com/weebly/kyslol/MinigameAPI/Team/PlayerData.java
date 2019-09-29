package com.weebly.kyslol.MinigameAPI.Team;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.bukkit.entity.Player;

public class PlayerData {
	private File dir;
	private Player p;
	private GameData gd;
	private int lives = 0;
	private int kitID = 0;

	// Scores
	private int kills = 0;
	private File totalKills;
	private int blocksBroken = 0;
	private File totalBlocksBroken;
	private int blocksPlaced = 0;
	private File totalBlocksPlaced;
	private double distanceFallen = 0;// TODO
	private File totalDistanceFallen;
	private double distanceTravel = 0;
	private File totalDistanceTravel;
	private int deaths = 0;
	private File totalDeaths;
	// TODO most used potion
	private File mostUsedPotion;
	private File gamesPlayed;
	private File gamesWon;
	private File longestGame;
	private File shortestGame;

	// Storage vars for games

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

	public PlayerData(Player p, GameData g) {
		this.p = p;
		gd = g;
		lives = gd.getLivesPerPlayer();
		dir = new File("plugins/Storage/GameScores/" + gd.getGAME_NAME() + "/" + p.getUniqueId().toString() + "/")
				.getAbsoluteFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		setFiles();
	}

	public void saveScores() {
		addToScore(totalKills, kills);
		addToScore(totalBlocksBroken, blocksBroken);
		addToScore(totalBlocksPlaced, blocksPlaced);
		addToScore(totalDistanceFallen, (int) distanceFallen);
		addToScore(totalDistanceTravel, (int) distanceTravel);
		addToScore(totalDeaths, deaths);
		// addToScore(mostUsedPotion, kills);//TODO
	}

	public void saveScoreEndOfGame() {
		addToScore(gamesPlayed, 1);
		if (gd.isInEnding()) {
			if (gd.isUseTeams()) {
				if (gd.getWinners().contains(p)) {
					addToScore(gamesWon, 1);
				}
			} else {
				if (gd.getWinner().equals(p)) {
					addToScore(gamesWon, 1);
				}
			}
		}

		if (getScore(longestGame) <= gd.getGameTime()) {
			setScore(longestGame, gd.getGameTime());
		}
		if (getScore(shortestGame) >= gd.getGameTime() || getScore(shortestGame) == 0) {
			setScore(shortestGame, gd.getGameTime());
		}
	}

	private void setFiles() {

		File[] files = { totalKills = new File(dir + "/totalKills.txt"),
				totalBlocksBroken = new File(dir + "/totalBlocksBroken.txt"),
				totalBlocksPlaced = new File(dir + "/totalBlocksPlaced.txt"),
				totalDistanceFallen = new File(dir + "/totalDistanceFallen.txt"),
				totalDistanceTravel = new File(dir + "/totalDistanceTravel.txt"),
				totalDeaths = new File(dir + "/totalDeaths.txt"),
				mostUsedPotion = new File(dir + "/mostUsedPotion.txt"),
				gamesPlayed = new File(dir + "/gamesPlayed.txt"), gamesWon = new File(dir + "/gamesWon.txt"),
				longestGame = new File(dir + "/longestGame.txt"), shortestGame = new File(dir + "/shortestGame.txt") };

		for (int i = 0; i < files.length; i++) {
			if (!files[i].exists()) {
				setScore(files[i], 0);
			}
		}
	}

	public void addToScore(File file, int add) {
		try {
			int score = getScore(file);
			score += add;
			file.createNewFile();
			FileWriter w = new FileWriter(file);
			PrintWriter pw = new PrintWriter(w);
			pw.println("" + score);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getScore(File file) {
		int score = 0;
		try {
			Scanner scanner = new Scanner(file);
			score = Integer.parseInt(scanner.nextLine());
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return score;
	}

	public void setScore(File file, int set) {
		try {
			file.createNewFile();
			FileWriter w = new FileWriter(file);
			PrintWriter pw = new PrintWriter(w);
			pw.println("" + set);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public GameData getGd() {
		return gd;
	}

	public void setGd(GameData gd) {
		this.gd = gd;
	}

	public int getLives() {
		return lives;
	}

	public void addLives(int add) {
		new SidebarUpdateEvent(SideBar.PLAYER_LIVES, gd);
		lives += add;
	}

	public void setLives(int lives) {
		new SidebarUpdateEvent(SideBar.PLAYER_LIVES, gd);
		this.lives = lives;
	}

	public int getKills() {
		return kills;
	}

	public void addKills(int add) {
		new SidebarUpdateEvent(SideBar.PLAYER_KILLS, gd);
		kills += add;
	}

	public void setKills(int kills) {
		new SidebarUpdateEvent(SideBar.PLAYER_KILLS, gd);
		this.kills = kills;
	}

	public int getBlocksBroken() {
		return blocksBroken;
	}

	public void addBlocksBroken(int add) {
		blocksBroken += add;
	}

	public void setBlocksBroken(int blocksBroken) {
		this.blocksBroken = blocksBroken;
	}

	public int getBlocksPlaced() {
		return blocksPlaced;
	}

	public void addBlocksPlaced(int add) {
		blocksPlaced += add;
	}

	public void setBlocksPlaced(int blocksPlaced) {
		this.blocksPlaced = blocksPlaced;
	}

	public double getDistanceFallen() {
		return distanceFallen;
	}

	public void addDistanceFallen(double add) {
		distanceFallen += add;
	}

	public void setDistanceFallen(double distanceFallen) {
		this.distanceFallen = distanceFallen;
	}

	public int getKitID() {
		return kitID;
	}

	public void setKitID(int kitID) {
		this.kitID = kitID;
	}

	public void addDistanceTravel(double distance) {
		distanceTravel += distance;
	}

	public double getDistanceTravel() {
		return distanceTravel;
	}

	public void setDistanceTravel(double distanceTravel) {
		this.distanceTravel = distanceTravel;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public void addDeaths(int deaths) {
		this.deaths += deaths;
	}

	public int getDeaths() {
		return deaths;
	}

	public File getDir() {
		return dir;
	}

	public File getTotalKills() {
		return totalKills;
	}

	public File getTotalBlocksBroken() {
		return totalBlocksBroken;
	}

	public File getTotalBlocksPlaced() {
		return totalBlocksPlaced;
	}

	public File getTotalDistanceFallen() {
		return totalDistanceFallen;
	}

	public File getTotalDistanceTravel() {
		return totalDistanceTravel;
	}

	public File getTotalDeaths() {
		return totalDeaths;
	}

	public File getMostUsedPotion() {
		return mostUsedPotion;
	}

	public File getGamesPlayed() {
		return gamesPlayed;
	}

	public File getGamesWon() {
		return gamesWon;
	}

	public File getLongestGame() {
		return longestGame;
	}

	public File getShortestGame() {
		return shortestGame;
	}

	public void setDir(File dir) {
		this.dir = dir;
	}

	public void setTotalKills(File totalKills) {
		this.totalKills = totalKills;
	}

	public void setTotalBlocksBroken(File totalBlocksBroken) {
		this.totalBlocksBroken = totalBlocksBroken;
	}

	public void setTotalBlocksPlaced(File totalBlocksPlaced) {
		this.totalBlocksPlaced = totalBlocksPlaced;
	}

	public void setTotalDistanceFallen(File totalDistanceFallen) {
		this.totalDistanceFallen = totalDistanceFallen;
	}

	public void setTotalDistanceTravel(File totalDistanceTravel) {
		this.totalDistanceTravel = totalDistanceTravel;
	}

	public void setTotalDeaths(File totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	public void setMostUsedPotion(File mostUsedPotion) {
		this.mostUsedPotion = mostUsedPotion;
	}

	public void setGamesPlayed(File gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public void setGamesWon(File gamesWon) {
		this.gamesWon = gamesWon;
	}

	public void setLongestGame(File longestGame) {
		this.longestGame = longestGame;
	}

	public void setShortestGame(File shortestGame) {
		this.shortestGame = shortestGame;
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

}
