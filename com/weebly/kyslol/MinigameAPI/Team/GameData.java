package com.weebly.kyslol.MinigameAPI.Team;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.weebly.kyslol.MinigameAPI.Main;

public class GameData {

	// Basic game data

	private ArrayList<EditedBlocks> editedBlocks = new ArrayList<EditedBlocks>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Player> toQuit = new ArrayList<Player>();
	private int minPlayers = 2;
	private int maxPlayers = 10;
	private int maxTeams = 2;
	private int maxPlayersPerTeam = maxPlayers / maxTeams;
	private int minPlayersPerTeam = 2;
	private boolean inLobby = true;
	private int lobbyCounter_START = 60;
	private int lobbyCounter = 60;
	private boolean inGame = false;
	private boolean prizeGiven = false;
	private int gameTime = 0;
	private int startCountDown = -1;
	private int maxGameTime = 300;
	private boolean inEnding = false;
	private int endTime = 0;
	private int endID = 0;
	private boolean gameOver = false;
	private Team winners;
	private boolean useTimer1 = false;
	private int timer1 = 0;
	private boolean randomSpawns = true;
	private boolean spawnInAsGroup = false;
	private ArrayList<Location> spawns = new ArrayList<Location>();
	private String startMessage = "Insert start message here!";
	private boolean useStartMsg = false;
	private String endMessage = "Insert end message here!";
	private boolean useEndMsg = false;
	private int winnerPrize = 0;
	private Inventory lobbyInventory = getLobbyInventory();
	// For multi maps at once
	private int xOffs = 0;
	private int zOffs = 0;
	private World world;
	private Location lobby;
	private Location minCorner;
	private Location maxCorner;
	private boolean inDebug = true;
	private boolean inAlpha = false;
	private boolean inBeta = false;
	private final String DEBUG_MESSAGE = ChatColor.DARK_RED + "WARNING! " + ChatColor.DARK_AQUA
			+ "This game is in [DEBUG] mode.  The game wont be functioning properly right now.";
	private final String ALPHA_MESSAGE = ChatColor.DARK_RED + "WARNING! " + ChatColor.DARK_AQUA
			+ "This game is in [ALPHA] mode.  There may be many bugs with the game and gameplay may change a lot depending on feedback.";
	private final String BETA_MESSAGE = ChatColor.DARK_RED + "WARNING! " + ChatColor.DARK_AQUA
			+ "This game is in [BETA] mode.  There may be many bugs with the game.  Please report them on the forum or discord.";
	private final String GAME_NAME;
	private final String MAP_NAME;
	// Game play logic
	// Get x kills to win
	private boolean winByTeamKills = false;
	private boolean winByPlayerKills = false;
	private int killsToWin = 0;
	
	private boolean disableItemDrop = false;

	private boolean winByLastTeamStanding = true;

	private boolean use1_9Combat = true;

	private boolean useTeams = true;
	private boolean soloGame = false;

	public boolean isSoloGame() {
		return soloGame;
	}

	public void setSoloGame(boolean soloGame) {
		this.soloGame = soloGame;
	}

	private Player winner;

	private boolean dropItemsOnDeath = false;
	private boolean keepInventoryOnDeath = true;

	private boolean disableHunger = true;
	private boolean disableFallDamage = true;

	private ArrayList<PlayerData> playerData = new ArrayList<PlayerData>();

	private boolean infinateLives = false;
	private boolean useTeamLives = false;
	private int livesPerTeam = 1;
	private int livesPerPlayer = 1;

	private boolean friendlyFire = false;

	private boolean useKits = false;
	private ArrayList<Kit> kits = new ArrayList<Kit>();

	private ArrayList<Team> teams = new ArrayList<Team>();

	private boolean useRandomChestLoot = false;
	private Loot randomChestLoot = new Loot(this);

	private Random random = new Random();

	// Sidebar/ scoreboard
	private int timeOfDay = 0;
	private ArrayList<SideBar> sideBars = new ArrayList<SideBar>();
	private boolean displayTeamKillsOnSidebar = true;
	private int teamKillsScore = 11;
	private boolean displayTeamName = true;
	private int teamNameScore = 20;
	private boolean displayPlayerKillsOnSidebar = true;
	private int playerKillsScore = 8;
	private boolean displayGamePlayerCount = true;
	private int gamePlayerCountScore = 17;
	private boolean displayTeamPlayerCount = true;
	private int teamPlayerCountScore = 14;
	private boolean displayCustomVariables = false;
	private boolean displayGameTime = false;
	private int gameTimeScore = 5;
	private boolean displayTimeLeft = false;
	private int timeLeftScore = 2;
	private boolean displayPlayerLives = false;
	private int playerLivesScore = -1;
	private boolean displayTeamLives = true;
	private int teamLivesScore = -4;
	private boolean displayPlayerNameNotTitle = false;
	private String sidebarTitle;

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
	
	private ArrayList<Object> storageArr0 = new ArrayList<Object>();
	private ArrayList<Object> storageArr1 = new ArrayList<Object>();
	private ArrayList<Object> storageArr2 = new ArrayList<Object>();
	private ArrayList<Object> storageArr3 = new ArrayList<Object>();
	private ArrayList<Object> storageArr4 = new ArrayList<Object>();

	public GameData(String mapName, String gameName) {
		MAP_NAME = mapName;
		GAME_NAME = gameName;
		new Listeners(Main.getMain(), this);
	}

	public void _tick() {
		for (Player p : players) {
			if (disableHunger) {
				p.setFoodLevel(20);
			}
			if (!use1_9Combat) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, 100));
			}
		}
		if (inLobby || inEnding)
			return;

		if (useTeams) {
			if (teams.size() == 1) {
				inEnding = true;
				inGame = false;
				winners = teams.get(0);
				for (Player p : players) {
					getPlayerData(p).saveScoreEndOfGame();
				}
			}
		} else {
			if (players.size() == 1 && !soloGame) {
				inEnding = true;
				inGame = false;
				winner = players.get(0);
				for (Player p : players) {
					getPlayerData(p).saveScoreEndOfGame();
				}
			}
		}

		if (winByPlayerKills) {
			for (PlayerData pd : playerData) {
				if (pd.getKills() >= killsToWin) {
					for (Team team : teams) {
						if (team.contains(pd.getP())) {
							inEnding = true;
							inGame = false;
							for (Player p : players) {
								if (useTeams) {
									winners = team;
									p.sendMessage(ChatColor.GREEN + p.getName() + " got " + killsToWin + " kills!  "
											+ team.getCOLOUR() + team.getTEAM_NAME() + " team " + ChatColor.GREEN
											+ "wins!");
								} else {
									winner = p;
									p.sendMessage(ChatColor.GREEN + p.getName() + " got " + killsToWin
											+ " kills!  Well done " + p.getName());
								}
							}
						}
					}
				}
			}
		} else if (winByTeamKills) {
			for (Team team : teams) {
				if (team.getKills() >= killsToWin) {
					winners = team;
					inGame = false;
					for (Player p : players) {
						p.sendMessage(team.getCOLOUR() + team.getTEAM_NAME() + " team" + ChatColor.RED + " has won!");
					}
					inEnding = true;
					inGame = false;
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void _sec() {
		if (inLobby) {
			for (Player p : players) {
				p.setLevel(lobbyCounter);
			}
			if (players.size() >= minPlayers) {
				lobbyCounter--;
				if (lobbyCounter <= 0) {
					inLobby = false;
					inGame = true;
					gameTime = 0 - startCountDown;
					int teamID = 0;
					for (int i = 0; i < maxTeams; i++) {
						teams.add(new Team(this, i));
					}
					for (Player p : players) {
						p.setPlayerTime(timeOfDay, false);
						if (useStartMsg) {
							p.sendMessage(startMessage);
						}
						if (useKits) {
							for (PlayerData pd : playerData) {
								if (pd.getP().equals(p)) {
									if (pd.getKitID() > kits.size()) {
										error("Invalid Kit ID.  Setting kit to 0");
										pd.setKitID(0);
									}
									Kit kit = kits.get(pd.getKitID());
									p.getInventory().setContents(kit.getInventory().getContents());
									p.getInventory().setHelmet(kit.getHead());
									p.getInventory().setBoots(kit.getBoots());
									p.getInventory().setChestplate(kit.getChest());
									p.getInventory().setLeggings(kit.getLegs());
								}
							}
						}
						addPlayerToTeam(p, teamID);
						teamID++;

						if (!spawnInAsGroup) {
							tpToSpawn(p, teamID);
						}
					}
					for(int i = 0; i < teams.size(); i++){
						if(teams.get(i).getMembers().size() == 0){
							teams.remove(i);
							i--;
						}
					}
					if (spawnInAsGroup) {
						for (Team team : teams) {
							tpToSpawn(team, teamID);
							teamID++;
						}
					}
					if (useRandomChestLoot) {
						setRandomChestLoot();
					}
					killAllItems();
					
					new SidebarUpdateEvent(SideBar.TEAM_NAME, this);
					new SidebarUpdateEvent(SideBar.PLAYER_COUNT, this);
					new SidebarUpdateEvent(SideBar.PLAYER_KILLS, this);
					new SidebarUpdateEvent(SideBar.PLAYER_LIVES, this);
					new SidebarUpdateEvent(SideBar.TEAM_COUNT, this);
					new SidebarUpdateEvent(SideBar.TEAM_KILLS, this);
					new SidebarUpdateEvent(SideBar.TEAM_LIVES, this);
					new SidebarUpdateEvent(SideBar.TIME_LEFT, this);
					new SidebarUpdateEvent(SideBar.TIMER, this);
					
					if (useTeams) {
						for (Team team : teams) {
							String msg = ChatColor.GOLD + "Team Members: " + ChatColor.DARK_AQUA;
							for (Player p : team.getMembers()) {
								msg += p.getName();
							}
							for (Player p : team.getMembers()) {
								p.sendMessage(msg);
							}
						}
					}
				}
			} else {
				lobbyCounter = lobbyCounter_START;
			}
		} else if (inGame) {
			if (gameTime < 0) {
				for (Player p : players) {
					p.setLevel(Math.abs(gameTime));
				}
			} else {
				if (!soloGame) {
					for (Player p : players) {
						p.setLevel(Math.abs(maxGameTime - gameTime));
					}

					if (maxGameTime - gameTime <= 0) {
						inEnding = true;
						inGame = false;
						endID = -10;
						endTime = 0;
						for (Player p : players) {
							p.sendMessage(ChatColor.RED + "Time is up");
						}
					}
				}
			}

			if (winByLastTeamStanding) {
				if (useTeams) {
					if (teams.size() == 1) {
						winners = teams.get(0);
						inEnding = true;
						inGame = false;
					}
				} else {
					if (players.size() == 1 && !soloGame) {
						winner = players.get(0);
						inEnding = true;
						inGame = false;
					}
				}
			}

			gameTime += 1;
			new SidebarUpdateEvent(SideBar.TIME_LEFT, this);
			new SidebarUpdateEvent(SideBar.TIMER, this);
			if (useTimer1) {
				timer1++;
			}
		} else if (inEnding) {
			if (endTime > 0) {
				endTime--;

				if (!prizeGiven) {
					prizeGiven = true;
					if (endID != -10) {
						if (useTeams) {
							for (Player p : winners.getMembers()) {
								p.sendMessage(ChatColor.GREEN + "You win!  You get £" + winnerPrize);
								Main.econ.depositPlayer(p, winnerPrize);
							}
						} else {
							winner.sendMessage(ChatColor.GREEN + "You win!  You get £" + winnerPrize);
							Main.econ.depositPlayer(winner, winnerPrize);
						}
						for (Player p : players) {
							if (useEndMsg) {
								p.sendMessage(endMessage);
							} else if (useTeams) {
								p.sendMessage(winners.getCOLOUR() + winners.getTEAM_NAME() + " team " + ChatColor.GREEN
										+ "won!");
							} else {
								p.sendMessage(ChatColor.GREEN + winner.getName() + " won!");
							}
						}
					}
				}
			} else {
				for (EditedBlocks edit : editedBlocks) {
					Block block = world.getBlockAt(edit.getLocation());
					block.setType(edit.getMaterial());
					block.setData(edit.getData());
				}
				killAllItems();
				for (int i = 0; i < players.size(); i++) {
					Player p = players.get(i);
					playerQuitGame(p, true);
					i--;
				}
				gameOver = true;
			}
		}
	}

	public void tpToSpawn(Player p, int id) {
		if (randomSpawns) {
			p.teleport(spawns.get(random.nextInt(spawns.size())));
		} else {
			p.teleport(spawns.get(id % spawns.size()));
		}
	}

	public void tpToSpawn(Team team, int id) {
		for (Player p : team.getMembers()) {
			if (randomSpawns) {
				p.teleport(spawns.get(random.nextInt(spawns.size())));
			} else {
				p.teleport(spawns.get(id % spawns.size()));
			}
		}
	}

	public PlayerData getPlayerData(Player p) {
		for (PlayerData pd : playerData) {
			if (pd.getP().equals(p)) {
				return pd;
			}
		}
		return null;
	}

	public boolean inRegion(Location center, Location location, int x, int y, int z) {
		int px = location.getBlockX(), py = location.getBlockY(), pz = location.getBlockZ();
		int sx = center.getBlockX(), sy = center.getBlockY(), sz = center.getBlockZ();
		return (sx + x >= px && sx - x <= px && sy + y >= py && sy - y <= py && sz + z >= pz && sz - z <= pz);
	}

	public void killAllItems() {
		for (Entity e : world.getEntities()) {
			if (e instanceof Item) {
				// System.out.println("Item");
				if (inMap(e.getLocation())) {
					// System.out.println("is in the map");
					e.remove();
				}
			}
			if (e instanceof Arrow) {
				if (inMap(e.getLocation())) {
					e.remove();
				}
			}
			if (e instanceof LivingEntity) {
				if (!(e instanceof Player)) {
					e.remove();
				}
			}
		}
	}

	public boolean inMap(Location test) {
		int minX = maxCorner.getBlockX();
		int minY = maxCorner.getBlockY();
		int minZ = maxCorner.getBlockZ();

		int maxX = minCorner.getBlockX();
		int maxY = minCorner.getBlockY();
		int maxZ = minCorner.getBlockZ();

		int xp = test.getBlockX();
		int yp = test.getBlockY();
		int zp = test.getBlockZ();
		return (xp <= maxX && xp >= minX && yp <= maxY && yp >= minY && zp <= maxZ && zp >= minZ);
	}

	public void setRandomChestLoot() {
		int minX = minCorner.getBlockX();
		int minY = minCorner.getBlockY();
		int minZ = minCorner.getBlockZ();

		int maxX = maxCorner.getBlockX();
		int maxY = maxCorner.getBlockY();
		int maxZ = maxCorner.getBlockZ();

		for (int x = minX; x < maxX; x++) {
			for (int y = minY; y < maxY; y++) {
				for (int z = minZ; z < maxZ; z++) {
					Block block = world.getBlockAt(x, y, z);
					if (block.getType().equals(Material.CHEST)) {
						Chest chest = (Chest) block.getState();
						Inventory chestInv = chest.getInventory();
						randomChestLoot.randomItems(chestInv);
					}
				}
			}
		}
	}

	public void addPlayerToTeam(Player p, int teamID) {
		if (!teams.get(teamID % maxTeams).addPlayer(p)) {
			p.sendMessage(ChatColor.RED + "An error has occoured while adding you to a team,");
			p.sendMessage(ChatColor.RED + "Please try again in the next game");
			playerQuitGame(p, true);
		}
	}

	public void win(Team team) {
		winners = team;
	}

	public void error(String message) {
		System.out.println(ChatColor.DARK_RED + "WARNING!  MAP " + getMAP_NAME() + " IN GAME " + getGAME_NAME()
				+ " HAS AN ERROR, ENTERING DEBUG MODE.");
		System.out.println(ChatColor.DARK_RED + message);
		inDebug = true;
	}

	public boolean contains(Player p) {
		return players.contains(p);
	}

	public boolean playerJoinGame(Player p) {
		if (contains(p))
			return false;
		if (inLobby && players.size() < maxPlayers) {
			if (soloGame) {
				maxPlayers = 1;
				minPlayers = 1;
				lobbyCounter_START = 0;
				maxGameTime = -1;
			}
			players.add(p);
			p.getInventory().clear();
			playerData.add(new PlayerData(p, this));
			sideBars.add(new SideBar(p, this));
			p.setInvulnerable(true);
			p.setGameMode(GameMode.SURVIVAL);
			p.teleport(lobby);
			for (Player player : players) {
				player.sendMessage(ChatColor.GREEN + p.getName() + " has joined");
			}
			if (inDebug) {
				p.sendMessage(DEBUG_MESSAGE);
			}
			if (inAlpha) {
				p.sendMessage(ALPHA_MESSAGE);
			}
			if (inBeta) {
				p.sendMessage(BETA_MESSAGE);
			}

			if (!(players.size() >= minPlayers)) {
				p.sendMessage(ChatColor.RED + "More players required!");
			}
			new SidebarUpdateEvent(SideBar.PLAYER_COUNT, this);

			return true;
		} else {
			return false;
		}
	}

	public void playerQuitGameAfterRespawn(PlayerRespawnEvent e, Player p, boolean silentLeave) {
		Location tp = world.getSpawnLocation().add(new Vector(0.5, 0, 0.5));
		tp.setYaw(180);
		e.setRespawnLocation(tp);
		playerQuitGame(p, silentLeave);
	}

	public void playerQuitGame(Player p, boolean silentLeave) {
		if (toQuit.contains(p) || soloGame) {
			getPlayerData(p).saveScoreEndOfGame();
		}
		p.setFoodLevel(20);
		p.setHealth(20);
		new SidebarUpdateEvent(SideBar.PLAYER_COUNT, this);
		p.setInvulnerable(false);
		Location tp = world.getSpawnLocation().add(new Vector(0.5, 0, 0.5));
		tp.setYaw(180);
		p.teleport(tp);
		p.getInventory().clear();
		p.getInventory().setContents(lobbyInventory.getContents());
		p.setLevel(0);
		// for (PotionEffectType pet : PotionEffectType.values()) {
		// p.removePotionEffect(pet);
		// }
		if (!players.contains(p)) {
			toQuit.remove(p);
			return;
		}

		getPlayerData(p).saveScores();

		p.setPlayerTime(0, true);
		p.sendMessage(ChatColor.RED + "You have quit " + GAME_NAME);
		players.remove(p);
		for (int i = 0; i < teams.size(); i++) {
			Team team = teams.get(i);
			if (team.contains(p)) {
				team.removePlayer(p);
			}
		}
		if (!silentLeave) {
			for (Player player : players) {
				player.sendMessage(ChatColor.RED + p.getName() + " Has left this minigame.");
			}
		}
		if (players.size() <= 0) {
			gameOver = true;
			inGame = false;
		}
	}

	private Inventory getLobbyInventory() {
		Inventory i = Bukkit.createInventory(null, 4 * 9);
		createDisplay(i, Material.COMPASS, 0, ChatColor.GOLD + "Server Selector", new ArrayList<String>(), (byte) 0);
		Kit inventory = new Kit(i.getContents());
		return inventory.getInventory();
	}

	public void createKit(Inventory i) {
		kits.add(new Kit(i.getContents()));
	}

	public static void createDisplay(Inventory inv, Material material, int Slot, String name, ArrayList<String> lore,
			byte dmg) {
		ItemStack item = new ItemStack(material, 1, dmg);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		if (!meta.hasLore()) {
			meta.setLore(lore);
		}
		item.setItemMeta(meta);
		inv.setItem(Slot, item);
	}

	// Setters and Getters
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		new SidebarUpdateEvent(SideBar.PLAYER_COUNT, this);
		this.players = players;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
		setMaxPlayersPerTeam();
	}

	public int getMaxTeams() {
		return maxTeams;
	}

	public void setMaxTeams(int maxTeams) {
		this.maxTeams = maxTeams;
		setMaxPlayersPerTeam();
	}

	public int getMaxPlayersPerTeam() {
		return maxPlayersPerTeam;
	}

	public void setMaxPlayersPerTeam() {
		maxPlayersPerTeam = maxPlayers / maxTeams;
		double calc = maxPlayers / maxTeams;
		if (calc - maxPlayersPerTeam != 0) {
			error("maxPlayers not divisible by maxTeams.  " + calc + " players per team is not a valid ammount.");
		}
	}

	public boolean isInLobby() {
		return inLobby;
	}

	public void setInLobby(boolean inLobby) {
		this.inLobby = inLobby;
	}

	public int getLobbyCounter() {
		return lobbyCounter;
	}

	public void setLobbyCounter(int lobbyCounter) {
		this.lobbyCounter = lobbyCounter;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public int getGameTime() {
		return gameTime;
	}

	public void setGameTime(int gameTime) {
		this.gameTime = gameTime;
	}

	public int getMaxGameTime() {
		return maxGameTime;
	}

	public void setMaxGameTime(int maxGameTime) {
		this.maxGameTime = maxGameTime;
	}

	public boolean isInEnding() {
		return inEnding;
	}

	public void setInEnding(boolean inEnding) {
		this.inEnding = inEnding;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public int getEndID() {
		return endID;
	}

	public void setEndID(int endID) {
		this.endID = endID;
	}

	public Team getWinners() {
		return winners;
	}

	public void setWinners(Team winners) {
		this.winners = winners;
	}

	public boolean isUseTimer1() {
		return useTimer1;
	}

	public void setUseTimer1(boolean useTimer1) {
		this.useTimer1 = useTimer1;
	}

	public int getTimer1() {
		return timer1;
	}

	public void setTimer1(int timer1) {
		this.timer1 = timer1;
	}

	public boolean isRandomSpawns() {
		return randomSpawns;
	}

	public void setRandomSpawns(boolean randomSpawns) {
		this.randomSpawns = randomSpawns;
	}

	public boolean isSpawnInAsGroup() {
		return spawnInAsGroup;
	}

	public void setSpawnInAsGroup(boolean spawnInAsGroup) {
		this.spawnInAsGroup = spawnInAsGroup;
	}

	public ArrayList<Location> getSpawns() {
		return spawns;
	}

	public void setSpawns(ArrayList<Location> spawns) {
		this.spawns = spawns;
	}

	public String getStartMessage() {
		return startMessage;
	}

	public void setStartMessage(String startMessage) {
		this.startMessage = startMessage;
	}

	public String getEndMessage() {
		return endMessage;
	}

	public void setEndMessage(String endMessage) {
		this.endMessage = endMessage;
	}

	public int getWinnerPrize() {
		return winnerPrize;
	}

	public void setWinnerPrize(int winnerPrize) {
		this.winnerPrize = winnerPrize;
	}

	public int getxOffs() {
		return xOffs;
	}

	public void setxOffs(int xOffs) {
		this.xOffs = xOffs;
	}

	public int getzOffs() {
		return zOffs;
	}

	public void setzOffs(int zOffs) {
		this.zOffs = zOffs;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Location getLobby() {
		return lobby;
	}

	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	public boolean isInDebug() {
		return inDebug;
	}

	public void setInDebug(boolean inDebug) {
		this.inDebug = inDebug;
	}

	public boolean isInAlpha() {
		return inAlpha;
	}

	public void setInAlpha(boolean inAlpha) {
		this.inAlpha = inAlpha;
	}

	public boolean isInBeta() {
		return inBeta;
	}

	public void setInBeta(boolean inBeta) {
		this.inBeta = inBeta;
	}

	public boolean isWinByTeamKills() {
		return winByTeamKills;
	}

	public void setWinByTeamKills(boolean winByKills) {
		this.winByTeamKills = winByKills;
	}

	public boolean isWinByPlayerKills() {
		return winByPlayerKills;
	}

	public void setWinByPlayerKills(boolean winByKills) {
		this.winByPlayerKills = winByKills;
	}

	public int getKillsToWin() {
		return killsToWin;
	}

	public void setKillsToWin(int killsToWin) {
		this.killsToWin = killsToWin;
	}

	public boolean isInfinateLives() {
		return infinateLives;
	}

	public void setInfinateLives(boolean infinateLives) {
		this.infinateLives = infinateLives;
	}

	public boolean isUseTeamLives() {
		return useTeamLives;
	}

	public void setUseTeamLives(boolean useTeamLives) {
		this.useTeamLives = useTeamLives;
	}

	public int getLivesPerTeam() {
		return livesPerTeam;
	}

	public void setLivesPerTeam(int livesPerTeam) {
		this.livesPerTeam = livesPerTeam;
	}

	public int getLivesPerPlayer() {
		return livesPerPlayer;
	}

	public void setLivesPerPlayer(int livesPerPlayer) {
		this.livesPerPlayer = livesPerPlayer;
	}

	public boolean isFriendlyFire() {
		return friendlyFire;
	}

	public void setFriendlyFire(boolean friendlyFire) {
		this.friendlyFire = friendlyFire;
	}

	public boolean isUseKits() {
		return useKits;
	}

	public void setUseKits(boolean useKits) {
		this.useKits = useKits;
	}

	public ArrayList<Kit> getKits() {
		return kits;
	}

	public void setKits(ArrayList<Kit> kits) {
		this.kits = kits;
	}

	public boolean isDisplayTeamKillsOnSidebar() {
		return displayTeamKillsOnSidebar;
	}

	public void setDisplayTeamKillsOnSidebar(boolean displayTeamKillsOnSidebar) {
		this.displayTeamKillsOnSidebar = displayTeamKillsOnSidebar;
	}

	public boolean isDisplayPlayerKillsOnSidebar() {
		return displayPlayerKillsOnSidebar;
	}

	public void setDisplayPlayerKillsOnSidebar(boolean displayPlayerKillsOnSidebar) {
		this.displayPlayerKillsOnSidebar = displayPlayerKillsOnSidebar;
	}

	public boolean isDisplayGamePlayerCount() {
		return displayGamePlayerCount;
	}

	public void setDisplayGamePlayerCount(boolean displayGamePlayerCount) {
		this.displayGamePlayerCount = displayGamePlayerCount;
	}

	public boolean isDisplayTeamPlayerCount() {
		return displayTeamPlayerCount;
	}

	public void setDisplayTeamPlayerCount(boolean displayTeamPlayerCount) {
		this.displayTeamPlayerCount = displayTeamPlayerCount;
	}

	public boolean isDisplayCustomVariables() {
		return displayCustomVariables;
	}

	public void setDisplayCustomVariables(boolean displayCustomVariables) {
		this.displayCustomVariables = displayCustomVariables;
	}

	public boolean isDisplayGameTime() {
		return displayGameTime;
	}

	public void setDisplayGameTime(boolean displayGameTime) {
		this.displayGameTime = displayGameTime;
	}

	public boolean isDisplayTimeLeft() {
		return displayTimeLeft;
	}

	public void setDisplayTimeLeft(boolean displayTimeLeft) {
		this.displayTimeLeft = displayTimeLeft;
	}

	public boolean isDisplayPlayerNameNotTitle() {
		return displayPlayerNameNotTitle;
	}

	public void setDisplayPlayerNameNotTitle(boolean displayPlayerNameNotTitle) {
		this.displayPlayerNameNotTitle = displayPlayerNameNotTitle;
	}

	public ArrayList<Team> getTeams() {
		return teams;
	}

	public void setTeams(ArrayList<Team> teams) {
		this.teams = teams;
	}

	public Location getMinCorner() {
		return minCorner;
	}

	public void setMinCorner(Location minCorner) {
		this.minCorner = minCorner;
	}

	public Location getMaxCorner() {
		return maxCorner;
	}

	public void setMaxCorner(Location maxCorner) {
		this.maxCorner = maxCorner;
	}

	public boolean isUseRandomChestLoot() {
		return useRandomChestLoot;
	}

	public void setUseRandomChestLoot(boolean useRandomChestLoot) {
		this.useRandomChestLoot = useRandomChestLoot;
	}

	public Loot getRandomChestLoot() {
		return randomChestLoot;
	}

	public void setRandomChestLoot(Loot randomChestLoot) {
		this.randomChestLoot = randomChestLoot;
	}

	public int getMinPlayersPerTeam() {
		return minPlayersPerTeam;
	}

	public void setMinPlayersPerTeam(int minPlayersPerTeam) {
		this.minPlayersPerTeam = minPlayersPerTeam;
	}

	public boolean isDropItemsOnDeath() {
		return dropItemsOnDeath;
	}

	public void setDropItemsOnDeath(boolean dropItemsOnDeath) {
		this.dropItemsOnDeath = dropItemsOnDeath;
	}

	public boolean isKeepInventoryOnDeath() {
		return keepInventoryOnDeath;
	}

	public void setKeepInventoryOnDeath(boolean keepInventoryOnDeath) {
		this.keepInventoryOnDeath = keepInventoryOnDeath;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public String getSidebarTitle() {
		return sidebarTitle;
	}

	public void setSidebarTitle(String sidebarTitle) {
		this.sidebarTitle = sidebarTitle;
	}

	public int getLobbyCounter_START() {
		return lobbyCounter_START;
	}

	public void setLobbyCounter_START(int lobbyCounter_START) {
		this.lobbyCounter_START = lobbyCounter_START;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void setLobbyInventory(Inventory lobbyInventory) {
		this.lobbyInventory = lobbyInventory;
	}

	public int getStartCountDown() {
		return startCountDown;
	}

	public void setStartCountDown(int startCountDown) {
		this.startCountDown = startCountDown;
	}

	public ArrayList<SideBar> getSideBars() {
		return sideBars;
	}

	public void setSideBars(ArrayList<SideBar> sideBars) {
		this.sideBars = sideBars;
	}

	public int getTeamKillsScore() {
		return teamKillsScore;
	}

	public void setTeamKillsScore(int teamKillsScore) {
		this.teamKillsScore = teamKillsScore;
	}

	public int getPlayerKillsScore() {
		return playerKillsScore;
	}

	public void setPlayerKillsScore(int playerKillsScore) {
		this.playerKillsScore = playerKillsScore;
	}

	public int getGamePlayerCountScore() {
		return gamePlayerCountScore;
	}

	public void setGamePlayerCountScore(int gamePlayerCountScore) {
		this.gamePlayerCountScore = gamePlayerCountScore;
	}

	public int getTeamPlayerCountScore() {
		return teamPlayerCountScore;
	}

	public void setTeamPlayerCountScore(int teamPlayerCountScore) {
		this.teamPlayerCountScore = teamPlayerCountScore;
	}

	public int getGameTimeScore() {
		return gameTimeScore;
	}

	public void setGameTimeScore(int gameTimeScore) {
		this.gameTimeScore = gameTimeScore;
	}

	public int getTimeLeftScore() {
		return timeLeftScore;
	}

	public void setTimeLeftScore(int timeLeftScore) {
		this.timeLeftScore = timeLeftScore;
	}

	public boolean isDisplayPlayerLives() {
		return displayPlayerLives;
	}

	public void setDisplayPlayerLives(boolean displayPlayerLives) {
		this.displayPlayerLives = displayPlayerLives;
	}

	public int getPlayerLivesScore() {
		return playerLivesScore;
	}

	public void setPlayerLivesScore(int playerLivesScore) {
		this.playerLivesScore = playerLivesScore;
	}

	public boolean isDisplayTeamLives() {
		return displayTeamLives;
	}

	public void setDisplayTeamLives(boolean displayTeamLives) {
		this.displayTeamLives = displayTeamLives;
	}

	public int getTeamLivesScore() {
		return teamLivesScore;
	}

	public void setTeamLivesScore(int teamLivesScore) {
		this.teamLivesScore = teamLivesScore;
	}

	public boolean isDisableHunger() {
		return disableHunger;
	}

	public void setDisableHunger(boolean disableHunger) {
		this.disableHunger = disableHunger;
	}

	public boolean isDisableFallDamage() {
		return disableFallDamage;
	}

	public void setDisableFallDamage(boolean disableFallDamage) {
		this.disableFallDamage = disableFallDamage;
	}

	public ArrayList<PlayerData> getPlayerData() {
		return playerData;
	}

	public void setPlayerData(ArrayList<PlayerData> playerData) {
		this.playerData = playerData;
	}

	public ArrayList<Player> getToQuit() {
		return toQuit;
	}

	public void setToQuit(ArrayList<Player> toQuit) {
		this.toQuit = toQuit;
	}

	public boolean isDisplayTeamName() {
		return displayTeamName;
	}

	public void setDisplayTeamName(boolean displayTeamName) {
		this.displayTeamName = displayTeamName;
	}

	public int getTeamNameScore() {
		return teamNameScore;
	}

	public void setTeamNameScore(int teamNameScore) {
		this.teamNameScore = teamNameScore;
	}

	public boolean isWinByLastTeamStanding() {
		return winByLastTeamStanding;
	}

	public boolean isUse1_9Combat() {
		return use1_9Combat;
	}

	public boolean isUseTeams() {
		return useTeams;
	}

	public void setWinByLastTeamStanding(boolean winByLastTeamStanding) {
		this.winByLastTeamStanding = winByLastTeamStanding;
	}

	public void setUse1_9Combat(boolean use1_9Combat) {
		this.use1_9Combat = use1_9Combat;
	}

	public void setUseTeams(boolean useTeams) {
		this.useTeams = useTeams;
	}

	public boolean isUseStartMsg() {
		return useStartMsg;
	}

	public boolean isUseEndMsg() {
		return useEndMsg;
	}

	public Player getWinner() {
		return winner;
	}

	public void setUseStartMsg(boolean useStartMsg) {
		this.useStartMsg = useStartMsg;
	}

	public void setUseEndMsg(boolean useEndMsg) {
		this.useEndMsg = useEndMsg;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public int getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(int timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	public boolean isPrizeGiven() {
		return prizeGiven;
	}

	public void setPrizeGiven(boolean prizeGiven) {
		this.prizeGiven = prizeGiven;
	}

	public ArrayList<EditedBlocks> getEditedBlocks() {
		return editedBlocks;
	}

	public void setEditedBlocks(ArrayList<EditedBlocks> editedBlocks) {
		this.editedBlocks = editedBlocks;
	}

	public boolean isDisableItemDrop() {
		return disableItemDrop;
	}

	public void setDisableItemDrop(boolean disableItemDrop) {
		this.disableItemDrop = disableItemDrop;
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

	public ArrayList<Object> getStorageArr0() {
		return storageArr0;
	}

	public ArrayList<Object> getStorageArr1() {
		return storageArr1;
	}

	public ArrayList<Object> getStorageArr2() {
		return storageArr2;
	}

	public ArrayList<Object> getStorageArr3() {
		return storageArr3;
	}

	public ArrayList<Object> getStorageArr4() {
		return storageArr4;
	}

	public void setStorageArr0(ArrayList<Object> storageArr0) {
		this.storageArr0 = storageArr0;
	}

	public void setStorageArr1(ArrayList<Object> storageArr1) {
		this.storageArr1 = storageArr1;
	}

	public void setStorageArr2(ArrayList<Object> storageArr2) {
		this.storageArr2 = storageArr2;
	}

	public void setStorageArr3(ArrayList<Object> storageArr3) {
		this.storageArr3 = storageArr3;
	}

	public void setStorageArr4(ArrayList<Object> storageArr4) {
		this.storageArr4 = storageArr4;
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

	public String getDEBUG_MESSAGE() {
		return DEBUG_MESSAGE;
	}

	public String getALPHA_MESSAGE() {
		return ALPHA_MESSAGE;
	}

	public String getBETA_MESSAGE() {
		return BETA_MESSAGE;
	}

	public String getGAME_NAME() {
		return GAME_NAME;
	}

	public String getMAP_NAME() {
		return MAP_NAME;
	}

}
