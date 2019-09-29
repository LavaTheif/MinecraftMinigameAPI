package com.weebly.kyslol.MinigameAPI.teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

@Deprecated
public class ScoreBoard {
	private ScoreboardManager manager = Bukkit.getScoreboardManager();
	private Scoreboard board = manager.getNewScoreboard();
//	private static Team team;
	private Objective objective;
	private Player p;
	private GameData g;

	public ScoreBoard(Player player, GameData gd) {
		objective = board.registerNewObjective(player.getName(), "dummy");
		p = player;
		g = gd;

		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(player.getName());

		Score score = objective.getScore(ChatColor.GREEN + "Team:");
		score.setScore(60);

		for (int i = 0; i < g.getTeams(); i++) {
			if (g.getTeamData().get(i).getMembers().contains(p)) {
				Score score1 = objective.getScore(g.getTeamData().get(i).getId() + " ");
				score1.setScore(50);
				p.sendMessage(g.getTeamData().get(i).getId());
			}
		}
		Score score2 = objective.getScore("");
		score2.setScore(40);

		Score score3 = objective.getScore(ChatColor.GREEN + "Kills:");
		score3.setScore(30);

		for (int i = 0; i < g.getTeams(); i++) {
			Score score4 = objective.getScore(g.getTeamData().get(i).getId());
			score4.setScore(g.getTeamData().get(i).kills());
		}

		player.setScoreboard(board);
	}
	
	public void kill(){
		for (int i = 0; i < g.getTeams(); i++) {
			Score score4 = objective.getScore(g.getTeamData().get(i).getId());
			score4.setScore(g.getTeamData().get(i).kills());
		}
		p.setScoreboard(board);
	}
}
