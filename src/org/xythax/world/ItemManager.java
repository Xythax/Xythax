package org.xythax.world;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.xythax.model.Client;
import org.xythax.model.FloorItem;
import org.xythax.model.Player;
import org.xythax.model.combat.content.ItemProtect;
import org.xythax.model.definition.entity.ItemDefinition;

/**
 * ItemManager
 * 
 * @author Graham
 * @author Ultimate
 */

public class ItemManager {

	private static final int HIDE_FOR = 60000;
	private static final int STAY_FOR = (HIDE_FOR * 2);
	private List<FloorItem> list;

	public List<FloorItem> getList() {
		return list;
	}

	private Map<Integer, ItemDefinition> itemDefinitions;

	/**
	 * Loads the items, drops, etc.
	 * 
	 * @throws IOException
	 */
	public ItemManager() throws IOException {
		list = new ArrayList<FloorItem>();
		itemDefinitions = new HashMap<Integer, ItemDefinition>();
		loadDefinitions("./data/item/definitions.cfg");
		loadSpawns("./data/item/spawns.cfg");
	}

	// TODO cache this!
	/**
	 * Gets the unnoted item for an item.
	 * 
	 * @param normalId
	 * @return
	 */
	public int getUnnotedItem(int normalId) {
		int newId = -1;
		String notedName = itemDefinitions.get(normalId).getName();
		for (Map.Entry<Integer, ItemDefinition> entry : itemDefinitions
				.entrySet()) {
			if (entry.getValue().getName().equals(notedName)) {
				if (!entry.getValue().getDescription()
						.startsWith("Swap this note at any bank for a")) {
					newId = entry.getKey();
				}
			}
		}
		return newId;
	}

	public int getNotedItem(int normalId) {
		int newId = -1;
		String notedName = itemDefinitions.get(normalId).getName();
		for (Map.Entry<Integer, ItemDefinition> entry : itemDefinitions
				.entrySet()) {
			if (entry.getValue().getName().equals(notedName)) {
				if (entry.getValue().getDescription()
						.startsWith("Swap this note at any bank for a")) {
					newId = entry.getKey();
				}
			}
		}
		return newId;
	}

	/**
	 * Loads definitions.
	 * 
	 * @param string
	 * @throws IOException
	 */
	private void loadDefinitions(String name) throws IOException {
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(name));
			while (true) {
				String line = file.readLine();
				if (line == null)
					break;
				int spot = line.indexOf('=');
				if (spot > -1) {
					String values = line.substring(spot + 1);
					values = values.replace("\t\t", "\t");
					values = values.replace("\t\t", "\t");
					values = values.trim();
					String[] valuesArray = values.split("\t");
					int id = Integer.valueOf(valuesArray[0]);
					String iname = valuesArray[1].replaceAll("_", " ");
					String examine = valuesArray[2].replaceAll("_", " ");
					double shopValue = Double.valueOf(valuesArray[3]);
					double lowAlch = Double.valueOf(valuesArray[4]);
					double highAlch = Double.valueOf(valuesArray[5]);
					int[] bonuses = new int[12];
					int ptr = 6;
					for (int i = 0; i < bonuses.length; i++) {
						bonuses[i] = Integer.valueOf(valuesArray[ptr]);
						ptr++;
					}
					ItemDefinition def = new ItemDefinition(id, iname, examine,
							shopValue, lowAlch, highAlch, bonuses);
					itemDefinitions.put(id, def);
				}
			}
			System.out.println("Loaded " + itemDefinitions.size()
					+ " item definitions.");
		} finally {
			if (file != null)
				file.close();
		}
	}

	/**
	 * Gets an item definition.
	 * 
	 * @param id
	 * @return
	 */
	public ItemDefinition getItemDefinition(int id) {
		return itemDefinitions.get(id);
	}

	/**
	 * Loads drops.
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public void loadSpawns(String name) throws IOException {
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(name));
			while (true) {
				String line = file.readLine();
				if (line == null)
					break;
				int spot = line.indexOf('=');
				if (spot > -1) {
					String values = line.substring(spot + 1);
					values = values.replace("\t\t", "\t");
					values = values.replace("\t\t", "\t");
					values = values.trim();
					String[] valuesArray = values.split("\t");
					int id = Integer.valueOf(valuesArray[0]);
					int x = Integer.parseInt(valuesArray[1]);
					int y = Integer.parseInt(valuesArray[2]);
					int amount = Integer.parseInt(valuesArray[3]);
					int height = Integer.parseInt(valuesArray[4]);
					FloorItem i = new FloorItem(id, amount, null, x, y, height);
					i.setSpawn(true);
					list.add(i);
				}
			}
			System.out.println("Loaded " + list.size() + " item spawns.");
		} finally {
			if (file != null)
				file.close();
		}
	}

	/**
	 * Periodically remove old items.
	 */
	public void process() {
		Queue<FloorItem> remove = new LinkedList<FloorItem>();
		for (FloorItem item : list) {
			if (item.isSpawn())
				continue;
			if (System.currentTimeMillis() > (item.getDroppedAt() + HIDE_FOR)
					&& item.getDroppedBy() != null) {
				item.resetOwner();
				hideDrop(item);
				showDrop(item);
			} else if (System.currentTimeMillis() > (item.getDroppedAt() + STAY_FOR)) {
				remove.add(item);
				hideDrop(item);
			}
		}
		for (FloorItem r : remove) {
			list.remove(r);
		}
	}

	public void reloadDrops(Client client) {
		if (client == null)
			return;
		for (FloorItem item : list) {
			if (System.currentTimeMillis() > (item.getDroppedAt() + HIDE_FOR)) {

				if (ItemProtect.onProtectedList(item.getId())) {
					if (item.getUsername() != client.getUsername())
						continue;
				}
				showDrop(item, client);
			}
			if (client.getUsername().equalsIgnoreCase(item.getUsername())) {

				if (item.getHeight() != client.getHeightLevel())
					continue;

				int tmpX = item.getX() - client.getAbsX();
				int tmpY = item.getY() - client.getAbsY();

				if (tmpX >= -96 && tmpX <= 96 && tmpY >= -96 && tmpX <= 96) {
					client.outStream.createFrame(85);
					client.outStream
							.writeByteC((item.getY() - 8 * client.mapRegionY));
					client.outStream
							.writeByteC((item.getX() - 8 * client.mapRegionX));
					client.outStream.createFrame(44);
					client.outStream.writeWordBigEndianA(item.getId());
					client.outStream.writeWord(item.getAmount());
					client.outStream.writeByte(0);
					client.flushOutStream();
				}
			}
		}
	}

	/**
	 * Shows items when the player loads a new region.
	 * 
	 * @param p
	 *            The player.
	 */
	public void reload(Client p) {
		if (p == null)
			return;
		for (FloorItem item : list) {
			if (System.currentTimeMillis() > (item.getDroppedAt() + HIDE_FOR)) {
				if (ItemProtect.onProtectedList(item.getId())) {
					if (item.getUsername() != p.getUsername())
						continue;
				}
				showDrop(item, p);
			}
			if (item.getHeight() != p.getHeightLevel())
				continue;
			int tmpX = item.getX() - p.getAbsX();
			int tmpY = item.getY() - p.getAbsY();
			if (tmpX >= -96 && tmpX <= 96 && tmpY >= -96 && tmpY <= 96) {
				if (item.getDroppedBy() != null || !item.isSpawn()) {
					if (item.getUsername() == null) {
						continue;
					}
					if (!item.getUsername().equalsIgnoreCase(p.getUsername())) {
						continue;
					}
				}
				p.outStream.createFrame(85);
				p.outStream.writeByteC((item.getY() - 8 * p.mapRegionY));
				p.outStream.writeByteC((item.getX() - 8 * p.mapRegionX));
				// hide existing drop
				p.outStream.createFrame(156);
				p.outStream.writeByteS(0);
				p.outStream.writeWord(item.getId());
				// show new drop
				p.outStream.createFrame(44);
				p.outStream.writeWordBigEndianA(item.getId());
				p.outStream.writeWord(item.getAmount());
				p.outStream.writeByte(0);
				p.flushOutStream();
			}
		}
	}

	public void destruct2(Client p) {
		for (FloorItem item : list) {
			if (item.getDroppedBy() != null) {
				if (item.getUsername().equalsIgnoreCase(p.getUsername())) {
					item.resetOwner();
					hideDrop(item);
					showDrop(item);
				}
			}
		}
	}

	public void newDrop(FloorItem i, String player) {
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (p.getUsername().equalsIgnoreCase(player)) {
				newDrop(i, (Client) p);
			}
		}
	}

	public void newDrop(FloorItem i, Client p) {
		list.add(i);
		if (p == null)
			return;
		p.outStream.createFrame(85);
		p.outStream.writeByteC((i.getY() - 8 * p.mapRegionY));
		p.outStream.writeByteC((i.getX() - 8 * p.mapRegionX));
		p.outStream.createFrame(44);
		p.outStream.writeWordBigEndianA(i.getId());
		p.outStream.writeWord(i.getAmount());
		p.outStream.writeByte(0);
		p.flushOutStream();
	}

	public void newDropFromNPC(FloorItem l, String attacker) {

		Client attacking = null;
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (!p.isActive)
				continue;
			if (p.getUsername() == attacker) {
				attacking = (Client) p;
				break;
			}
		}
		if (l.getId() == -1 || l.getId() == 0)
			return;

		list.add(l);

		if (attacking == null) {
			showDrop(l);
			return;
		}
		try {
			attacking.outStream.createFrame(85);
			attacking.outStream
					.writeByteC((l.getY() - 8 * attacking.mapRegionY));
			attacking.outStream
					.writeByteC((l.getX() - 8 * attacking.mapRegionX));
			attacking.outStream.createFrame(44);
			attacking.outStream.writeWordBigEndianA(l.getId());
			attacking.outStream.writeWord(l.getAmount());
			attacking.outStream.writeByte(0);
			attacking.flushOutStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showDrop(FloorItem i, Client p) {
		if (p == null)
			return;
		if (!p.isActive)
			return;
		if (p.disconnected)
			return;
		Client player = p;
		if (i.getHeight() != player.getHeightLevel())
			return;
		if (ItemProtect.onProtectedList(i.getId())) {
			if (p.getUsername() != p.getUsername())
				return;
		}
		int tmpX = i.getX() - player.getAbsX();
		int tmpY = i.getY() - player.getAbsY();
		if (tmpX >= -96 && tmpX <= 96 && tmpY >= -96 && tmpX <= 96) {
			player.outStream.createFrame(85);
			player.outStream.writeByteC((i.getY() - 8 * player.mapRegionY));
			player.outStream.writeByteC((i.getX() - 8 * player.mapRegionX));
			player.outStream.createFrame(44);
			player.outStream.writeWordBigEndianA(i.getId());
			player.outStream.writeWord(i.getAmount());
			player.outStream.writeByte(0);
			player.flushOutStream();
		}
	}

	public void showDrop(FloorItem i) {
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (!p.isActive)
				continue;
			if (p.disconnected)
				continue;
			Client player = (Client) p;
			if (i.getHeight() != player.getHeightLevel())
				continue;
			if (ItemProtect.onProtectedList(i.getId())) {
				if (i.getUsername() != p.getUsername())
					continue;
			}
			int tmpX = i.getX() - player.getAbsX();
			int tmpY = i.getY() - player.getAbsY();
			if (tmpX >= -96 && tmpX <= 96 && tmpY >= -96 && tmpX <= 96) {
				player.outStream.createFrame(85);
				player.outStream.writeByteC((i.getY() - 8 * player.mapRegionY));
				player.outStream.writeByteC((i.getX() - 8 * player.mapRegionX));
				player.outStream.createFrame(44);
				player.outStream.writeWordBigEndianA(i.getId());
				player.outStream.writeWord(i.getAmount());
				player.outStream.writeByte(0);
				player.flushOutStream();
			}
		}
	}

	public void hideDrop(FloorItem i) {
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (!p.isActive)
				continue;
			if (p.disconnected)
				continue;
			Client player = (Client) p;
			int tmpX = i.getX() - player.getAbsX();
			int tmpY = i.getY() - player.getAbsY();
			if (tmpX >= -96 && tmpX <= 96 && tmpY >= -96 && tmpX <= 96
					&& player.getHeightLevel() == i.getHeight()) {
				player.outStream.createFrame(85);
				player.outStream.writeByteC((i.getY() - 8 * player.mapRegionY));
				player.outStream.writeByteC((i.getX() - 8 * player.mapRegionX));
				player.outStream.createFrame(156);
				player.outStream.writeByteS(0);
				player.outStream.writeWord(i.getId());
				player.flushOutStream();
			}
		}
	}

	public void pickupDrop(Client p, FloorItem item) {
		try {
			for (FloorItem i : list) {
				if (i != item)
					continue;
				if (i.isSpawn()) {
					if (p.getActionSender().sendInventoryItem(i.getId(),
							i.getAmount())) {
						list.remove(i);
						hideDrop(i);
						/*
						 * final FloorItem f = i;
						 * EventManager.getSingleton().addEvent(null, new
						 * Event() { public void execute(EventContainer c) {
						 * FloorItem drop = new FloorItem(f.getId(),
						 * f.getAmount(),null, f.getX(), f.getY(),
						 * f.getHeight()); drop.setSpawn(true); showDrop(drop);
						 * list.add(drop); c.stop(); }
						 * 
						 * @Override public void stop() {
						 * 
						 * } }, DROPS_RESPAWN_TIME);
						 */
						break;
					}
				} else if (i.getDroppedBy() == null) {
					if (p.getActionSender().sendInventoryItem(i.getId(),
							i.getAmount())) {
						list.remove(i);
						hideDrop(i);
						break;
					}
				} else if (i.getUsername().equalsIgnoreCase(p.getUsername())) {
					if (p.getActionSender().sendInventoryItem(i.getId(),
							i.getAmount())) {
						list.remove(i);
						hideDrop(i);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pickupDrop(Client p, int absX, int absY, int height, int itemID) {
		try {
			for (FloorItem i : list) {
				int diffX = i.getX() - p.getAbsX();
				int diffY = i.getY() - p.getAbsY();
				if (diffX <= 1 && diffX >= -1 && diffY <= 1 && diffY >= -1) {
					if (i.getId() == itemID && i.getX() == absX
							&& i.getY() == absY && i.getHeight() == height) {
						if (i.isSpawn()) {
							if (p.getActionSender().sendInventoryItem(
									i.getId(), i.getAmount())) {
								list.remove(i);
								hideDrop(i);
								/*
								 * final FloorItem f = i;
								 * EventManager.getSingleton().addEvent(null,
								 * new Event() { public void
								 * execute(EventContainer c) { FloorItem drop =
								 * new FloorItem( f.getId(), f .getAmount(),
								 * null, f.getX(), f .getY(), f .getHeight());
								 * drop.setSpawn(true); showDrop(drop);
								 * list.add(drop); c.stop(); }
								 * 
								 * @Override public void stop() {
								 * 
								 * } }, DROPS_RESPAWN_TIME);
								 */
								break;
							}
						} else if (i.getDroppedBy() == null) {
							if (p.getActionSender().sendInventoryItem(
									i.getId(), i.getAmount())) {
								list.remove(i);
								hideDrop(i);
								break;
							}
						} else if (i.getUsername().equalsIgnoreCase(
								p.getUsername())) {
							if (p.getActionSender().sendInventoryItem(
									i.getId(), i.getAmount())) {
								list.remove(i);
								hideDrop(i);
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}