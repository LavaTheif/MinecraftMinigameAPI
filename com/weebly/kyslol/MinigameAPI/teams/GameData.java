package com.weebly.kyslol.MinigameAPI.teams;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Deprecated
public class GameData {
	private ArrayList<Location> spawns = new ArrayList<Location>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Team> teamData = new ArrayList<Team>();
	private ArrayList<ScoreBoard> sb = new ArrayList<ScoreBoard>();
	private Team winner;
	private TeamMain tm;
	private int offsetx = 0, offsetz = 0;
	private int money, gameID;
	private String gameName;
	private int killsToWin = 10;
	
	private int teams = 2, maxTeamSize = 10, minTeamSize = 1, timer = 60, gameId, type;

	private World world; // = Bukkit.getWorld("teamPVP")
	private Location lobby;

	private boolean start = false;
	private boolean removeItemsOnDeath = false;
	
	public GameData() {
	}

	public boolean add(Player p) {
		if(players.contains(p)) return false;
		if (!start && players.size() < maxTeamSize * teams) {
			players.add(p);
			p.setInvulnerable(true);
			p.setGameMode(GameMode.SURVIVAL);
			p.teleport(lobby);
			if (!(players.size() >= minTeamSize * teams)) {
				p.sendMessage(ChatColor.RED + "More players required!");
			}
			return true;
		} else {
			return false;
		}
	}

	public void tick(boolean tick) {
		for (int e = 0; e < teamData.size(); e++) {
			Team t = teamData.get(e);
			if (t.kills() >= killsToWin || teamData.size() < 2) {
				winner = t;
				for (int i = 0; i < players.size(); i++) {
					Player p = players.get(i);
					p.sendMessage(t.getId() + ChatColor.GREEN + " Win");
					if (winner.getMembers().contains(p)) {
						for(Player pl : t.getMembers()){
							TeamMain.econ.depositPlayer(pl, money);
							pl.sendMessage(ChatColor.GREEN + "You get £"+money+"!");
						}
					}
					quit(p);
				}
				tm.getGames().remove(this);
			}
		}
		_tick();
		// check if kills == 10
	}

	public TeamMain getTm() {
		return tm;
	}

	public void setTm(TeamMain tm) {
		this.tm = tm;
	}

	public void tick() {
		Random random = new Random();
		if (players.size() >= minTeamSize * teams) {// Enough players
			if (timer > 0) {
				timer--;
				for (Player p : players) {
					p.setLevel(timer);

				}
			} else {
				if (start) {// fighting
					_sec();
				} else {// tp players to spawns
					start = true;
					for (int i = 0; i < teams; i++) {
						teamData.add(new Team(i, this));
					}
					int team = 0;
					for (Player p : players) {
						p.setInvulnerable(false);
						p.setFireTicks(0);
						p.teleport(spawns.get(random.nextInt(spawns.size())));
						setTeam(p, team % teams);
						team += 1;
						sb.add(new ScoreBoard(p, this));
					}
					
					for(Team d : teamData){
						for(Player p : d.getMembers()){
							String msg = ChatColor.GOLD + "Your team members are: ";
							for(Player pl : d.getMembers()){
								msg += d.getChatcolor() + pl.getName() +  " ";
							}
							p.sendMessage(msg);
						}
					}
					
					// Set teams
				}
			}
		} else {
			// players.get(0).sendMessage("MORE NEEDED");
		}
	}
	
	public void start(Player p) {
		if (players.size() >= minTeamSize * teams) {
			timer = 0;
			for (Player pp : players) {
				pp.sendMessage(ChatColor.GREEN + p.getDisplayName() + ChatColor.GREEN + " Has started the game!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "More players required");
		}
	}
	
	public void _tick(){
	}
	public void _sec(){
	}

	public void quit(Player p) {
		p.setInvulnerable(false);
		p.setFireTicks(0);
		p.setGameMode(GameMode.ADVENTURE);
		p.sendMessage(ChatColor.RED + "Quiting " + getGameName());
		p.teleport(getWorld().getSpawnLocation());
		players.remove(p);
		if(players.size() == 0){
			tm.getGames().remove(this);
		}
		p.teleport(world.getSpawnLocation());

		for (int i = 0; i < teamData.size(); i++) {
			Team t = teamData.get(i);
			if (t.getMembers().contains(p)) {
				t.remove(p);
			}
			if (t.getMembers().size() < 1) {
				teamData.remove(t);
			}
		}
	}

	public void setTeam(Player p, int team) {
		teamData.get(team).add(p);
	}

	public ArrayList<Team> getTeamData() {
		return teamData;
	}

	public ArrayList<Location> getSpawns() {
		return spawns;
	}

	public void setSpawns(ArrayList<Location> spawns) {
		this.spawns = spawns;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<ScoreBoard> getSb() {
		return sb;
	}

	public void setSb(ArrayList<ScoreBoard> sb) {
		this.sb = sb;
	}

	public Team getWinner() {
		return winner;
	}

	public void setWinner(Team winner) {
		this.winner = winner;
	}

	public int getOffsetx() {
		return offsetx;
	}

	public void setOffsetx(int offsetx) {
		this.offsetx = offsetx;
	}

	public int getOffsetz() {
		return offsetz;
	}

	public void setOffsetz(int offsetz) {
		this.offsetz = offsetz;
	}

	public int getTeams() {
		return teams;
	}

	public void setTeams(int teams) {
		this.teams = teams;
	}

	public int getMaxTeamSize() {
		return maxTeamSize;
	}

	public void setMaxTeamSize(int maxTeamSize) {
		this.maxTeamSize = maxTeamSize;
	}

	public int getMinTeamSize() {
		return minTeamSize;
	}

	public void setMinTeamSize(int minTeamSize) {
		this.minTeamSize = minTeamSize;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void setTeamData(ArrayList<Team> teamData) {
		this.teamData = teamData;
	}
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public int getKillsToWin() {
		return killsToWin;
	}

	public void setKillsToWin(int killsToWin) {
		this.killsToWin = killsToWin;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

}
