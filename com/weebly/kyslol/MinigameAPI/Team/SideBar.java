package com.weebly.kyslol.MinigameAPI.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class SideBar {

	public static final String PLAYER_COUNT = "playerCount";
	public static final String TIME_LEFT = "timeLeft";
	public static final String TIMER = "timer";
	public static final String PLAYER_KILLS = "playerKills";
	public static final String TEAM_KILLS = "teamKills";
	public static final String PLAYER_LIVES = "playerLives";
	public static final String TEAM_LIVES = "teamLives";
	public static final String TEAM_COUNT = "teamCount";
	public static final String TEAM_NAME = "teamName";
	public static final String CUSTOM = "custom";

	private final GameData gd;
	private final Player p;

	private ScoreboardManager manager = Bukkit.getScoreboardManager();
	private Scoreboard board = manager.getNewScoreboard();
	private Objective objective;

	public SideBar(Player p, GameData gd) {
		this.p = p;
		this.gd = gd;
		createSideBar();
	}

	public void createSideBar() {
		if (gd.isDisplayPlayerNameNotTitle()) {
			objective = board.registerNewObjective(p.getName(), "dummy");
		} else {
			objective = board.registerNewObjective(gd.getSidebarTitle(), "dummy");
		}
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		new SidebarUpdateEvent(gd.getGAME_NAME(), gd);
		update();
	}

	private String playerCount = "", timeLeft = "", timer = "";
	private String playerKills = "", teamKills = "", playerLives = "";
	private String teamLives = "", teamCount = "";

	// private String gap = "";

	public void update() {
		playerCount();
		playerKills();
		gameTime();
		timeLeft();
		playerLives();
		teamCount();
		teamKills();
		teamLives();
		teamName();
		
		p.setScoreboard(board);
	}

	public void playerKills() {
		if (gd.isDisplayPlayerKillsOnSidebar()) {

			PlayerData pd = gd.getPlayerData(p);
			String kills = ChatColor.DARK_AQUA + "" + pd.getKills() + "!";
			playerKills = add(ChatColor.GREEN + "Kills:", kills, playerKills, gd.getPlayerKillsScore());
			p.setScoreboard(board);
		}
	}

	public void teamName() {
		if (gd.isDisplayTeamName()) {
			for (Team team : gd.getTeams()) {
				if (team.contains(p)) {
					add(ChatColor.GREEN + "Team:", team.getCOLOUR() + team.getTEAM_NAME(), "",
							gd.getTeamNameScore());
				}
			}
			p.setScoreboard(board);
		}
	}

	public void teamKills() {
		if (gd.isDisplayTeamKillsOnSidebar()) {
			for (Team team : gd.getTeams()) {
				if (team.contains(p)) {
					String kills = ChatColor.AQUA + "" + team.getKills() + "!";
					teamKills = add(ChatColor.GREEN + "Team Kills:", kills, teamKills, gd.getTeamKillsScore());
				}
			}
			p.setScoreboard(board);
		}
	}

	public void gameTime() {
		if (gd.isDisplayGameTime()) {
			String time = ChatColor.DARK_AQUA + "" + gd.getGameTime();
			timer = add(ChatColor.GREEN + "Total Time:", time, timer, gd.getGameTimeScore());
			p.setScoreboard(board);
		}
	}

	public void timeLeft() {
		if (gd.isDisplayTimeLeft()) {
			String time = ChatColor.DARK_GREEN + "" + (gd.getMaxGameTime() - gd.getGameTime());
			timeLeft = add(ChatColor.GREEN + "Time Left:", time, timeLeft, gd.getTimeLeftScore());
			p.setScoreboard(board);
		}
	}

	public void playerCount() {
		if (gd.isDisplayGamePlayerCount()) {

			String msg = ChatColor.DARK_PURPLE + "" + gd.getPlayers().size() + "/" + gd.getMaxPlayers();
			playerCount = add(ChatColor.GREEN + "Players:", msg, playerCount, gd.getGamePlayerCountScore());
			p.setScoreboard(board);
		}
	}

	public void teamCount() {
		if (gd.isDisplayTeamPlayerCount()) {

			for (Team team : gd.getTeams()) {
				if (team.contains(p)) {
					String msg = ChatColor.DARK_PURPLE + "" + team.getMembers().size() + "/" + team.getMAX_PLAYERS();
					teamCount = add(ChatColor.GREEN + "Team Members:", msg, teamCount, gd.getTeamPlayerCountScore());
				}
			}
			p.setScoreboard(board);
		}
	}

	public void playerLives() {
		if (gd.isDisplayPlayerLives()) {
			PlayerData pd = gd.getPlayerData(p);
			String msg;
			if (pd.getLives() > 1) {
				msg = ChatColor.GREEN + "" + pd.getLives();
			} else {
				msg = ChatColor.RED + "" + pd.getLives();
			}
			playerLives = add(ChatColor.GREEN + "Lives:", msg, playerLives, gd.getPlayerLivesScore());
			p.setScoreboard(board);
		}
	}

	public void teamLives() {
		if (gd.isDisplayTeamLives()) {
			for (Team team : gd.getTeams()) {
				if (team.contains(p)) {
					String msg;
					if (team.getLives() > 1) {
						msg = ChatColor.GREEN + "" + team.getLives();
					} else {
						msg = ChatColor.RED + "" + team.getLives();
					}
					teamLives = add(ChatColor.GREEN + "Lives:", msg, teamLives, gd.getTeamLivesScore());
				}
			}
			p.setScoreboard(board);
		}
	}

	public String add(String title, String subTitle, String reset, int score) {
		Score players = objective.getScore(title);
		players.setScore(score);
		score--;
		board.resetScores(reset);
		Score ammountOfPlayers = objective.getScore(subTitle);
		ammountOfPlayers.setScore(score);
		score--;
		String gap = "";
		for (int i = 0; i < score; i += 3) {
			gap += " ";
		}
		Score blank = objective.getScore(gap);
		blank.setScore(score);
		score--;
		return subTitle;
	}
}
