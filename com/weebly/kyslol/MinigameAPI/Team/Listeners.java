package com.weebly.kyslol.MinigameAPI.Team;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.TippedArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.weebly.kyslol.MinigameAPI.Main;

public class Listeners implements Listener {
	Main main;
	GameData gd;

	public Listeners(Main plugin, GameData g) {
		gd = g;
		Main.getMain().getServer().getPluginManager().registerEvents(this, Main.getMain());
		main = plugin;
	}

	// @EventHandler
	// public void blockBreak(BlockBreakEvent e) {
	// if (e.getBlock().getType().equals(Material.CROPS)) {
	// if (gd.inMap(e.getBlock().getLocation())) {
	// e.setCancelled(true);
	// }
	// }
	// }

	@EventHandler(priority = EventPriority.HIGHEST)
	public void interact(PlayerInteractEvent e) {
		// Player p = e.getPlayer();
		// if (p.getWorld().equals(main.getWorld())) {

		if (e.getClickedBlock() == null)
			return;
		if (gd.contains(e.getPlayer())) {
			if (e.getAction() == Action.PHYSICAL) {// &&
													// e.getClickedBlock().getType()
													// == Material.SOIL)
				Material block = e.getClickedBlock().getType();
				if (!block.equals(Material.GOLD_PLATE) && !block.equals(Material.IRON_PLATE)
						&& !block.equals(Material.STONE_PLATE) && !block.equals(Material.WOOD_PLATE))
					e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void interactM(PlayerInteractEvent e) {
		// Player p = e.getPlayer();
		// if (p.getWorld().equals(main.getWorld())) {

		if (e.getClickedBlock() == null)
			return;
		if (gd.contains(e.getPlayer())) {
			addEditBlock(e.getClickedBlock());
		}
	}

	//
	// if (e.getClickedBlock().getType() == Material.WALL_SIGN) {
	// Block block = e.getClickedBlock();
	// BlockState state = block.getState();
	// Sign sign = (Sign) state;
	// String sign0 = sign.getLine(0);
	// String sign1 = sign.getLine(1);
	// String sign2 = sign.getLine(2);
	// String sign3 = sign.getLine(3);
	//
	// // To get
	// if (sign0.toLowerCase().contains("[" + main.getGameName().toLowerCase() +
	// "]")) {
	// if (sign1.toLowerCase().contains("quit")) {
	// for (int i = 0; i < main.getGames().size(); i++) {
	// if (main.getGames().get(i).getPlayers().contains(p)) {
	// main.getGames().get(i).quit(p);
	// i--;
	// }
	// }
	// }
	// if (sign1.toLowerCase().contains("join")) {
	// main.signClicked(sign2, sign3, p);
	// }
	// }
	//
	// }
	// }
	// }
//	@EventHandler(priority = EventPriority.MONITOR)
//	public void breakBlock(BlockBreakEvent e) {
//		if (!e.isCancelled()) {
//			Player p = e.getPlayer();
//			for (PlayerData pd : gd.getPlayerData()) {
//				if (pd.getP().equals(p)) {
//					pd.addBlocksBroken(1);
//					addEditBlock(e.getBlock());
//				}
//			}
//		}
//	}

	public void addEditBlock(Block block) {
		for (EditedBlocks edit : gd.getEditedBlocks()) {
			if (edit.getLocation().equals(block.getLocation())) {
				return;// Already been changed
			}
		}
		gd.getEditedBlocks().add(new EditedBlocks(block));
	}

//	@EventHandler(priority = EventPriority.MONITOR)
//	public void breakBlock(BlockPlaceEvent e) {
//		if (!e.isCancelled()) {
//			Player p = e.getPlayer();
//			for (PlayerData pd : gd.getPlayerData()) {
//				if (pd.getP().equals(p)) {
//					pd.addBlocksPlaced(1);
//					addEditBlock(e.getBlock());
//				}
//			}
//		}
//	}

	@EventHandler
	public void kill(PlayerDeathEvent event) {
		Player p = event.getEntity();
		if (p.isDead()) {
			if (p.getKiller() instanceof Player) {
				Player killer = (Player) p.getKiller();
				if (gd.contains(killer)) {
					for (Team t : gd.getTeams()) {
						if (t.contains(killer)) {
							t.killRecieved();
							for (Player players : gd.getPlayers()) {
								if (gd.isUseTeams()) {
									players.sendMessage(ChatColor.RED + killer.getName() + " from " + t.getCOLOUR()
											+ t.getTEAM_NAME() + " Team" + ChatColor.RED + " killed " + p.getName());
								} else {
									players.sendMessage(ChatColor.RED + killer.getName() + " killed " + p.getName());
								}
							}
						}
					}
					gd.getPlayerData(killer).addKills(1);
				}
			}
			if (gd.contains(p)) {
				gd.getPlayerData(p).addDeaths(1);
				for (Team t : gd.getTeams()) {
					if (t.contains(p)) {
						if (gd.isInfinateLives()) {
						} else if (gd.isUseTeamLives()) {
							t.removeLife();
							if (t.getLives() <= 0) {
								gd.playerQuitGame(p, false);
								gd.getToQuit().add(p);
							}
							if (t.getLives() == 0) {
								for (Player player : gd.getPlayers()) {
									player.sendMessage(t.getCOLOUR() + t.getTEAM_NAME() + " team" + ChatColor.RED
											+ " is out of lives! Players from that team can no longer respawn!");
								}
							}
						} else {
							for (PlayerData pd : gd.getPlayerData()) {
								if (pd.getP().equals(p)) {
									pd.addLives(-1);
									if (pd.getLives() <= 0) {
										gd.playerQuitGame(p, false);
										gd.getToQuit().add(p);
										for (Player player : gd.getPlayers()) {
											player.sendMessage(ChatColor.RED + p.getName() + " is out of lives");
										}
									}
								}
							}
						}
					}
				}
			}
		}
		new SidebarUpdateEvent(SideBar.PLAYER_KILLS, gd);
		new SidebarUpdateEvent(SideBar.TEAM_KILLS, gd);
		new SidebarUpdateEvent(SideBar.PLAYER_LIVES, gd);
		new SidebarUpdateEvent(SideBar.TEAM_LIVES, gd);
	}

	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		if (gd.isDisableItemDrop() && gd.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void sideBarUpdate(SidebarUpdateEvent e) {
		// System.out.println("SideBarUpdateEvent " + e.gettoUpdate());
		if (e.getGd().equals(gd)) {
			// System.out.println("TRUE " + gd.getSideBars().size());
			for (SideBar sb : gd.getSideBars()) {
				if (e.gettoUpdate().equalsIgnoreCase(SideBar.PLAYER_LIVES)) {
					sb.playerLives();
				}
				if (e.gettoUpdate().equalsIgnoreCase(SideBar.PLAYER_COUNT)) {
					sb.playerCount();
				}
				if (e.gettoUpdate().equalsIgnoreCase(SideBar.PLAYER_KILLS)) {
					sb.playerKills();
				}
				if (e.gettoUpdate().equalsIgnoreCase(SideBar.TEAM_COUNT)) {
					sb.teamCount();
				}
				if (e.gettoUpdate().equalsIgnoreCase(SideBar.TEAM_KILLS)) {
					sb.teamKills();
				}
				if (e.gettoUpdate().equalsIgnoreCase(SideBar.TEAM_NAME)) {
					sb.teamName();
				}
				if (e.gettoUpdate().equalsIgnoreCase(SideBar.TEAM_LIVES)) {
					sb.teamLives();
				}
				if (e.gettoUpdate().equalsIgnoreCase(SideBar.TIME_LEFT)) {
					sb.timeLeft();
				}
				if (e.gettoUpdate().equalsIgnoreCase(SideBar.TIMER)) {
					sb.gameTime();
				}
			}
		}
	}

	@EventHandler
	public void hurt(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (gd.contains(p)) {
				// System.out.println(e.isCancelled());
				if (gd.isInLobby() || gd.isInEnding()) {
					e.setCancelled(true);
					p.setFireTicks(0);
					// System.out.println("" + gd.isInEnding() + " " +
					// gd.isInLobby());
				}
				if (gd.isDisableFallDamage()) {
					if (e.getCause().equals(DamageCause.FALL)) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void hurt(EntityDamageByEntityEvent edbe) {
		if (edbe.getEntity() instanceof Player) {
			Player p = (Player) edbe.getEntity();
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
			if (gd.contains(p)) {
				if (gd.isFriendlyFire())
					return;
				for (Team t : gd.getTeams()) {
					if (t.contains(p)) {
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

	@EventHandler
	public void death(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (gd.contains(p)) {
			if (gd.isKeepInventoryOnDeath()) {
				e.setKeepInventory(true);
			}
			if (!gd.isDropItemsOnDeath()) {
				e.getDrops().removeAll(e.getDrops());
			}
		}
	}

	@EventHandler
	public void Quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (gd.contains(p)) {
			gd.playerQuitGame(p, false);
		}
	}

	@EventHandler
	public void changeworld(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		if (gd.contains(p)) {
			gd.playerQuitGame(p, false);
		}
	}

	@EventHandler
	public void gamemode(PlayerGameModeChangeEvent e) {
		Player p = e.getPlayer();
		if (gd.contains(p) && !gd.isInLobby()) {
			if (!p.hasPermission("rank.admin")) {
				p.sendMessage(ChatColor.RED + "Cant change game mode in game!");
				e.setCancelled(true);
			}
		}
	}

	// @EventHandler
	// public void breakBlock(BlockBreakEvent e) {
	// Player p = e.getPlayer();
	// if (p.getWorld().equals(main.getWorld())) {
	// for (int i = 0; i < main.getGames().size(); i++) {
	// GameData g = main.getGames().get(i);
	// if (g.getPlayers().contains(p)) {
	// e.setCancelled(true);
	// }
	// }
	// }
	// }
	//
	// @EventHandler
	// public void placeBlock(BlockPlaceEvent e) {
	// Player p = e.getPlayer();
	// if (p.getWorld().equals(main.getWorld())) {
	// for (int i = 0; i < main.getGames().size(); i++) {
	// GameData g = main.getGames().get(i);
	// if (g.getPlayers().contains(p)) {
	// e.setCancelled(true);
	// }
	// }
	// }
	// }
	@EventHandler
	public void playerMoveEvent(PlayerMoveEvent e) {
		if (gd.contains(e.getPlayer())) {
			if (gd.isInGame() && gd.getGameTime() < 0) {
				e.setCancelled(true);
			} else {
				if (gd.isInGame()) {
					if (!e.isCancelled()) {
						Location from = e.getFrom();
						Location to = e.getTo();
						double distance = getTotalDistance(from, to);
						gd.getPlayerData(e.getPlayer()).addDistanceTravel(distance);
						double fall = getFallDistance(from, to);
						gd.getPlayerData(e.getPlayer()).addDistanceFallen(fall);
					}
				}
			}
		}
	}

	private double getFallDistance(Location from, Location to) {
		double starty = from.getY();
		double endy = to.getY();
		double diff = starty - endy;
		if (diff > 0) {
			return diff;
		} else {
			return 0;
		}
	}

	private double getTotalDistance(Location from, Location to) {
		double startx = from.getX();
		double starty = from.getY();
		double startz = from.getZ();

		double endx = to.getX();
		double endy = to.getY();
		double endz = to.getZ();

		double x = Math.abs(startx - endx);
		double y = Math.abs(starty - endy);
		double z = Math.abs(startz - endz);

		double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double distance = Math.sqrt(Math.pow(z, 2) + Math.pow(d, 2));

		return distance;
	}

	@EventHandler
	public void respawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (gd.getToQuit().contains(p)) {
			gd.playerQuitGameAfterRespawn(e, p, true);
		}
		if (gd.contains(p)) {
			if (gd.isInfinateLives()) {
				e.setRespawnLocation(gd.getSpawns().get(gd.getRandom().nextInt(gd.getSpawns().size())));
			} else if (gd.isUseTeamLives()) {
				for (Team t : gd.getTeams()) {
					if (t.contains(p)) {
						if (t.getLives() > 0) {
							e.setRespawnLocation(gd.getSpawns().get(gd.getRandom().nextInt(gd.getSpawns().size())));
						}
					}
				}
			} else {
				e.setRespawnLocation(gd.getSpawns().get(gd.getRandom().nextInt(gd.getSpawns().size())));
			}
		}
	}
}
