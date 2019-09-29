package com.weebly.kyslol.MinigameAPI.teams;

import java.util.Random;

import javax.security.auth.login.AccountException;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.TippedArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.weebly.kyslol.MinigameAPI.Main;

import net.milkbowl.vault.VaultEco.VaultBankAccount;

@Deprecated
public class Listeners implements Listener {
	TeamMain main;

	public Listeners(TeamMain plugin) {
		Main.getMain().getServer().getPluginManager().registerEvents(this, Main.getMain());
		main = plugin;
	}

	Random random = new Random();

	@EventHandler
	public void clickingSigns(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().equals(main.getWorld())) {
			if (e.getClickedBlock() == null)
				return;

			if (e.getClickedBlock().getType() == Material.WALL_SIGN) {
				Block block = e.getClickedBlock();
				BlockState state = block.getState();
				Sign sign = (Sign) state;
				String sign0 = sign.getLine(0);
				String sign1 = sign.getLine(1);
				String sign2 = sign.getLine(2);
				String sign3 = sign.getLine(3);

				// To get
				if (sign0.toLowerCase().contains("[" + main.getGameName().toLowerCase() + "]")) {
					if (sign1.toLowerCase().contains("quit")) {
						for (int i = 0; i < main.getGames().size(); i++) {
							if (main.getGames().get(i).getPlayers().contains(p)) {
								main.getGames().get(i).quit(p);
								i--;
							}
						}
					}
					if (sign1.toLowerCase().contains("join")) {
						main.signClicked(sign2, sign3, p);
					}
				}

			}
		}
	}

	@EventHandler
	public void kill(PlayerDeathEvent event) {
		Player p = event.getEntity();
		if (p.isDead()) {
			if (p.getKiller() instanceof Player) {
				Player killer = (Player) p.getKiller();
				if (killer.getWorld().equals(main.getWorld())) {
					for (int i = 0; i < main.getGames().size(); i++) {
						GameData g = main.getGames().get(i);
						if (g.getPlayers().contains(killer)) {
							for (Team t : g.getTeamData()) {
								if (t.getMembers().contains(killer)) {
									t.kill();
									for(Player players : g.getPlayers()){
										players.sendMessage(ChatColor.RED + killer.getName() + " from " + t.getId() + ChatColor.RED + " just killed " + p.getName());
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void hurt(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (p.getWorld().equals(main.getWorld())) {
				for (int i = 0; i < main.getGames().size(); i++) {
					GameData g = main.getGames().get(i);
					if (g.getPlayers().contains(p)) {
						if (!g.isStart()) {
							e.setCancelled(true);
							p.setFireTicks(0);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void hurt(EntityDamageByEntityEvent edbe) {
		if (edbe.getEntity() instanceof Player) {
			Player p = (Player) edbe.getEntity();
			if (p.getWorld().equals(main.getWorld())) {
				Player d;
				if (edbe.getDamager() instanceof Player) {
					d = (Player) edbe.getDamager();
				} else if (edbe.getDamager() instanceof Arrow) {
					Arrow a = (Arrow) edbe.getDamager();
					if (!(a.getShooter() instanceof Player))
						return;
					d = (Player) a.getShooter();
				} else if (edbe.getDamager() instanceof TippedArrow) {
					TippedArrow a = (TippedArrow) edbe.getDamager();
					d = (Player) a.getShooter();
				} else {
					return;
				}
				for (int i = 0; i < main.getGames().size(); i++) {
					GameData g = main.getGames().get(i);
					if (g.getPlayers().contains(p)) {
						for (Team t : g.getTeamData()) {
							if (t.getMembers().contains(p)) {
								for (Player pl : t.getMembers()) {
									if (pl.equals(d)) {
										edbe.setCancelled(true);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void death(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (p.getWorld().equals(main.getWorld())) {
			for (int i = 0; i < main.getGames().size(); i++) {
				GameData g = main.getGames().get(i);
				if (g.getPlayers().contains(p)) {
					e.getDrops().removeAll(e.getDrops());//TODO Customisable wether they get removed?
				}
			}
		}
	}

	@EventHandler
	public void Quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().equals(main.getWorld())) {
			for (int i = 0; i < main.getGames().size(); i++) {
				GameData g = main.getGames().get(i);
				if (g.getPlayers().contains(p)) {
					g.quit(p);
				}
			}
		}
	}

	@EventHandler
	public void changeworld(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().equals(main.getWorld())) {
			for (int i = 0; i < main.getGames().size(); i++) {
				GameData g = main.getGames().get(i);
				if (g.getPlayers().contains(p)) {
					g.quit(p);
				}
			}
		}
	}

	@EventHandler
	public void gamemode(PlayerGameModeChangeEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().equals(main.getWorld())) {
			for (int i = 0; i < main.getGames().size(); i++) {
				GameData g = main.getGames().get(i);
				if (g.getPlayers().contains(p)) {
					if (!p.hasPermission("rank.admin")) {
						p.sendMessage(ChatColor.RED + "Cant change game mode in game!");
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void breakBlock(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().equals(main.getWorld())) {
			for (int i = 0; i < main.getGames().size(); i++) {
				GameData g = main.getGames().get(i);
				if (g.getPlayers().contains(p)) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void placeBlock(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().equals(main.getWorld())) {
			for (int i = 0; i < main.getGames().size(); i++) {
				GameData g = main.getGames().get(i);
				if (g.getPlayers().contains(p)) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void respawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().equals(main.getWorld())) {

		for (int i = 0; i < main.getGames().size(); i++) {
			GameData g = main.getGames().get(i);

				if (g.getPlayers().contains(p)) {
					e.setRespawnLocation(g.getSpawns().get(random.nextInt(g.getSpawns().size())));
					for (Team t : g.getTeamData()) {
						if (t.getMembers().contains(p)) {
							g.getTm().createDisplay(p.getInventory(), p, g.getType());
						}
					}
					return;
				}

			}
		p.setGameMode(GameMode.ADVENTURE);
		p.sendMessage(ChatColor.RED + "Quiting " + main.getGameName());
		p.teleport(main.getWorld().getSpawnLocation());
		e.setRespawnLocation(main.getWorld().getSpawnLocation());

		}

	}

}
