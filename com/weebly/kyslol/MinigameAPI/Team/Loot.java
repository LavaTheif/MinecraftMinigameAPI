package com.weebly.kyslol.MinigameAPI.Team;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Loot {
	File dir = new File("plugins/Storage/API/").getAbsoluteFile();
	File common = new File(dir + "/Common.txt");
	File semiCommon = new File(dir + "/SemiCommon.txt");
	File medium = new File(dir + "/Medium.txt");
	File rare = new File(dir + "/Rare.txt");
	File ultraRare = new File(dir + "/UltraRare.txt");

	private ArrayList<Material> commonItems = new ArrayList<Material>();
	private ArrayList<Material> semiCommonItems = new ArrayList<Material>();
	private ArrayList<Material> mediumItems = new ArrayList<Material>();
	private ArrayList<Material> rareItems = new ArrayList<Material>();
	private ArrayList<Material> ultraRareItems = new ArrayList<Material>();

	private GameData gd;

	public Loot(GameData g) {
		gd = g;
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {
			if (!common.exists()) {
				common.createNewFile();
			}
			if (!semiCommon.exists()) {
				semiCommon.createNewFile();
			}
			if (!medium.exists()) {
				medium.createNewFile();
			}
			if (!rare.exists()) {
				rare.createNewFile();
			}
			if (!ultraRare.exists()) {
				ultraRare.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		setCommon();
		setSemiCommon();
		setMedium();
		setRare();
		setUltraRare();
	}

	public void setCommon() {
		try {
			Scanner scanner = new Scanner(common);
			while (scanner.hasNextLine()) {
				Material m = Material.getMaterial(scanner.nextLine().toUpperCase());
				if (m != null) {
					commonItems.add(m);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setSemiCommon() {
		try {
			Scanner scanner = new Scanner(semiCommon);
			while (scanner.hasNextLine()) {
				Material m = Material.getMaterial(scanner.nextLine().toUpperCase());
				if (m != null) {
					semiCommonItems.add(m);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setMedium() {
		try {
			Scanner scanner = new Scanner(medium);
			while (scanner.hasNextLine()) {
				Material m = Material.getMaterial(scanner.nextLine().toUpperCase());
				if (m != null) {
					mediumItems.add(m);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setRare() {
		try {
			Scanner scanner = new Scanner(rare);
			while (scanner.hasNextLine()) {
				Material m = Material.getMaterial(scanner.nextLine().toUpperCase());
				if (m != null) {
					rareItems.add(m);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setUltraRare() {
		try {
			Scanner scanner = new Scanner(ultraRare);
			while (scanner.hasNextLine()) {
				Material m = Material.getMaterial(scanner.nextLine().toUpperCase());
				if (m != null) {
					ultraRareItems.add(m);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void randomItems(Inventory chestInv) {
		chestInv.clear();

		for (int slot = 0; slot < gd.getRandom().nextInt(5) + 2; slot++) {
			ItemStack item;
			chestInv.addItem(new ItemStack(Material.ANVIL, gd.getRandom().nextInt(256)));
			if (gd.getRandom().nextInt(10) < 5) {
				item = new ItemStack(commonItems.get(gd.getRandom().nextInt(commonItems.size())),
						gd.getRandom().nextInt(5) + 1);
				add(chestInv, item);
			}else if (gd.getRandom().nextInt(10) < 5) {
				item = new ItemStack(semiCommonItems.get(gd.getRandom().nextInt(semiCommonItems.size())),
						gd.getRandom().nextInt(5) + 1);
				add(chestInv, item);
			}else if (gd.getRandom().nextInt(10) < 5) {
				item = new ItemStack(mediumItems.get(gd.getRandom().nextInt(mediumItems.size())),
						gd.getRandom().nextInt(5) + 1);
				add(chestInv, item);
			}else if (gd.getRandom().nextInt(10) < 8) {
				item = new ItemStack(rareItems.get(gd.getRandom().nextInt(rareItems.size())),
						gd.getRandom().nextInt(5) + 1);
				add(chestInv, item);
			}else{
				item = new ItemStack(ultraRareItems.get(gd.getRandom().nextInt(ultraRareItems.size())),
						gd.getRandom().nextInt(5) + 1);
				add(chestInv, item);
			}
//			chestInv.addItem(item);
		}
		chestInv.remove(Material.ANVIL);
//		chest.update(true);
	}

	private void add(Inventory chestInv, ItemStack item) {
		if (item.getAmount() > item.getMaxStackSize()) {
			item.setAmount(item.getMaxStackSize());
		}
		chestInv.addItem(item);
	}
}
