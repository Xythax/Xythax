package org.xythax.world;

import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.xythax.content.actions.ActionManager;
import org.xythax.core.GameEngine;
import org.xythax.event.DeathEvent;
import org.xythax.model.Client;
import org.xythax.model.NPC;
import org.xythax.model.Player;
import org.xythax.model.PlayerDetails;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.map.FollowEngine;
import org.xythax.net.Login;
import org.xythax.net.Stream;
import org.xythax.net.db.Mysql;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;

public class PlayerManager {

	private int playerSlotSearchStart = 1;
	private Player players[] = new Player[Constants.MAX_PLAYERS];
	private Queue<Login> ioClientsToAdd = new LinkedList<Login>();
	private Queue<Login> ioClients = new LinkedList<Login>();
	private Queue<Login> ioClientsToRemove = new LinkedList<Login>();
	private Stream updateBlock = new Stream(new byte[1024]);

	private Map<String, Player> playerNameMap;
	private Map<Long, Player> playerNameLongMap;

	private String kickNick = "";
	private boolean kickAllPlayers = false;

	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {

				if (Constants.showSaveMessage) {
					System.out.println("Saving All Players...");
				}
				synchronized (this) {
					for (Player p : getPlayerManager().getPlayers()) {
						if (p == null)
							continue;
						if (((Client) p).getTradeHandler().stage > 0) {
							System.out.println("Did not save "
									+ p.getUsername()
									+ " as they are in a trade.");
							continue;
						}
						getPlayerManager().saveGame(p, true);
						System.out.println("Game saved from " + p.getUsername()
								+ ".");
					}
				}
				if (Constants.showSaveMessage) {
					System.out.println("All Players Saved");
				}
			}
		});
	}

	public PlayerManager() {
		for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
			players[i] = null;
		}
		playerNameMap = new HashMap<String, Player>();
		playerNameLongMap = new HashMap<Long, Player>();
	}

	private static PlayerManager playerManager = null;

	public static PlayerManager getPlayerManager() {
		if (playerManager == null) {
			playerManager = new PlayerManager();
		}
		return playerManager;
	}

	public Player getPlayerByName(String name) {
		return playerNameMap.get(name);
	}

	public Player getPlayerByNameLong(long name) {
		return playerNameLongMap.get(name);
	}

	public Player[] getPlayers() {
		return players;
	}

	public int getPlayerCount() {
		return playerNameMap.size();
	}

	public boolean isPlayerOn(String playerName) {
		return playerNameMap.containsKey(playerName);
	}

	public Login newPlayerClient(java.net.Socket s, String connectedFrom) {
		Login ioc;
		try {
			ioc = new Login(s, connectedFrom);
			ioc.manager = this;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		synchronized (ioClientsToAdd) {
			ioClientsToAdd.add(ioc);
		}
		return ioc;
	}

	public void addClient(int slot, Client newClient) {
		if (newClient == null)
			return;
		players[slot] = newClient;
		playerNameMap.put(newClient.getUsername(), newClient);
		playerNameLongMap.put(Utils.playerNameToInt64(newClient.getUsername()),
				newClient);
		playerSlotSearchStart = slot + 1;
		if (playerSlotSearchStart > Constants.MAX_PLAYERS)
			playerSlotSearchStart = 1;
	}

	public int getFreeSlot() {
		int slot = -1, i = 1;
		do {
			if (players[i] == null) {
				slot = i;
				break;
			}
			i++;
			if (i >= Constants.MAX_PLAYERS)
				i = 0;
		} while (i <= Constants.MAX_PLAYERS);
		return slot;
	}

	public void removeIOClient(Login ioc) {
		synchronized (ioClientsToRemove) {
			ioClientsToRemove.add(ioc);
		}
	}

	public void updateIOClients() {
		synchronized (ioClientsToAdd) {
			for (Login ioc : ioClientsToAdd) {
				ioClients.add(ioc);
			}
			ioClientsToAdd.clear();
		}
		synchronized (ioClients) {
			for (Login ioc : ioClients) {
				if (!ioc.checkTimeout()) {
					ioClientsToRemove.add(ioc);
				}
			}
		}
		synchronized (ioClientsToRemove) {
			for (Login ioc : ioClientsToRemove) {
				ioClients.remove(ioc);
			}
			ioClientsToRemove.clear();
		}
	}

	public void process() {

		updateIOClients();
		if (kickAllPlayers) {
			int kickID = 1;
			do {
				if (players[kickID] != null) {
					players[kickID].isKicked = true;
				}
				kickID++;
			} while (kickID < Constants.MAX_PLAYERS);
			kickAllPlayers = false;
		}
		DeathEvent.process();
		for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
			if (players[i] == null)
				continue;

			players[i].process();
			players[i].postProcessing();

			Client client = (Client) players[i];

			if (client == null)
				return;

			CombatEngine.mainProcessor(client);

			if (client.logoutDelay > 0)
				client.logoutDelay--;

			FollowEngine.loop(players[i]);
			players[i].getNextPlayerMovement();

			if (players[i].getUsername().equalsIgnoreCase(kickNick)) {
				players[i].kick();
				kickNick = "";
			}
			if (players[i].disconnected) {
				if (client.getTradeHandler().getStage() > 0) {
					client.getTradeHandler().abortCurrentTrade();
				}
			}
			if (players[i].disconnected && players[i].logoutDelay == 0) {

				removePlayer(players[i]);
				players[i] = null;
			}
		}

		for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
			if (players[i] == null)
				continue;
			if (players[i].disconnected && players[i].logoutDelay == 0) {
				removePlayer(players[i]);
				players[i] = null;
			} else {
				if (!players[i].initialized) {
					players[i].initialize();
					players[i].initialized = true;
				} else
					players[i].update();
			}
		}

		for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
			if (players[i] == null)
				continue;
			players[i].clearUpdateFlags();
		}

		for (Map.Entry<Integer, NPC> entry : GameEngine.getNPCManager()
				.getNPCMap().entrySet()) {
			NPC n = entry.getValue();
			n.clearUpdateFlags();
		}
	}

	public void kick(String name) {
		for (Player p : players) {
			if (p == null)
				continue;
			if (!p.isActive || p.disconnected)
				continue;
			if (p.getUsername().equalsIgnoreCase(name)) {
				p.kick();
				p.disconnected = true;
			}
		}
	}

	public void sendGlobalMessage(String message) {
		for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
			if (players[i] != null) {
				Client c = (Client) players[i];
				c.getActionSender().sendMessage(message);
			}
		}
	}

	public void updatePlayer(Player plr, Stream str) {
		synchronized (plr) {
			updateBlock.currentOffset = 0;

			plr.updateThisPlayerMovement(str);

			boolean saveChatTextUpdate = plr.chatTextUpdateRequired;
			plr.chatTextUpdateRequired = false;
			plr.appendPlayerUpdateBlock(updateBlock);
			plr.chatTextUpdateRequired = saveChatTextUpdate;

			str.writeBits(8, plr.playerListSize);
			int size = plr.playerListSize;
			plr.playerListSize = 0;
			for (int i = 0; i < size; i++) {
				if (!plr.playerList[i].didTeleport && !plr.didTeleport
						&& plr.withinDistance(plr.playerList[i])) {
					plr.playerList[i].updatePlayerMovement(str);
					plr.playerList[i].appendPlayerUpdateBlock(updateBlock);
					plr.playerList[plr.playerListSize++] = plr.playerList[i];
				} else {
					int id = plr.playerList[i].getUserID();
					plr.playerInListBitmap[id >> 3] &= ~(1 << (id & 7)); // clear
					// the
					// flag
					str.writeBits(1, 1);
					str.writeBits(2, 3); // tells client to remove this char
											// from
					// actions
				}
			}

			// iterate through all players to check whether there's new players
			// to
			// add
			for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
				if (players[i] == null)
					continue;
				if (!players[i].isActive || players[i] == plr)
					continue;
				int id = players[i].getUserID();
				if ((plr.playerInListBitmap[id >> 3] & (1 << (id & 7))) != 0)
					continue;
				if (!plr.withinDistance(players[i]))
					continue;

				plr.addNewPlayer(players[i], str, updateBlock);
			}

			if (updateBlock.currentOffset > 0) {
				str.writeBits(11, 2047);
				str.finishBitAccess();
				str.writeBytes(updateBlock.buffer, updateBlock.currentOffset, 0);
			} else
				str.finishBitAccess();

			str.endFrameVarSizeWord();
		}
	}

	public void updateNPC(Player plr, Stream str) {
		synchronized (plr) {
			updateBlock.currentOffset = 0;

			str.createFrameVarSizeWord(65);
			str.initBitAccess();

			str.writeBits(8, plr.npcListSize);
			int size = plr.npcListSize;
			plr.npcListSize = 0;
			for (int i = 0; i < size; i++) {
				if (plr.rebuildNPCList == false
						&& plr.withinDistance(plr.npcList[i]) == true
						&& !plr.npcList[i].isHidden()) {
					plr.npcList[i].updateNPCMovement(str);
					plr.npcList[i].appendNPCUpdateBlock(updateBlock);
					plr.npcList[plr.npcListSize++] = plr.npcList[i];
				} else {
					int id = plr.npcList[i].getNpcId();
					plr.npcInListBitmap[id >> 3] &= ~(1 << (id & 7)); // clear
																		// the
					// flag
					str.writeBits(1, 1);
					str.writeBits(2, 3); // tells client to remove this NPC from
					// actions
				}
			}

			// iterate through all NPC's to check whether there's new NPC to add
			// for (int i = 0; i < NPCHandler.getMaxNPCs(); i++) {
			for (int i = 1; i < NPCManager.MAXIMUM_NPCS; i++) {
				if (GameEngine.getNPCManager().getNPC(i) != null) {
					int id = GameEngine.getNPCManager().getNPC(i).getNpcId();
					if (plr.rebuildNPCList == false
							&& (plr.npcInListBitmap[id >> 3] & (1 << (id & 7))) != 0) {
						// npc already in npcList
					} else if (GameEngine.getNPCManager().getNPC(i).isHidden()) {
						// npc is hidden
					} else if (plr.withinDistance(GameEngine.getNPCManager()
							.getNPC(i)) == false) {
						// out of sight
					} else {
						plr.addNewNPC(GameEngine.getNPCManager().getNPC(i),
								str, updateBlock);
					}
				}
			}
			// }

			plr.rebuildNPCList = false;

			if (updateBlock.currentOffset > 0) {
				str.writeBits(14, 16383); // magic EOF - needed only when NPC
				// updateblock follows
				str.finishBitAccess();
				// append update block
				str.writeBytes(updateBlock.buffer, updateBlock.currentOffset, 0);
			} else {
				str.finishBitAccess();
			}
			str.endFrameVarSizeWord();
		}
	}

	public boolean saveGame(Player p, boolean loggout) {
		try {
			PlayerDetails playerDetails = new PlayerDetails(p);

			Statement s = Mysql.getConnection().createStatement();
			int online = loggout ? 0 : 1;
			s.executeUpdate("update characters set absX = '"
					+ playerDetails.absX + "', absY = '" + playerDetails.absY
					+ "', heightLevel = '" + playerDetails.heightLevel
					+ "', hitpoints = '" + playerDetails.hitpoints
					+ "', specialAmount = '" + playerDetails.specialAmount
					+ "', poisonDelay = '" + playerDetails.poisonDelay
					+ "', poisonDamage = '" + playerDetails.poisonDamage
					+ "', skullTimer = '" + playerDetails.skullTimer
					+ "', energy = '" + playerDetails.energy
					+ "', ancients = '" + (playerDetails.ancients ? 1 : 0)
					+ "', playerIsMember = '"
					+ (playerDetails.playerIsMember ? 1 : 0)
					+ "', dharokDamage = '" + playerDetails.dharokDamage
					+ "', spellBook = '" + playerDetails.spellBook
					+ "', appearanceSet = '"
					+ (playerDetails.appearanceSet ? 1 : 0) + "', ip = '"
					+ playerDetails.ConnectedFrom + "', splitChat = '"
					+ (playerDetails.SplitChat ? 1 : 0) + "', running = '"
					+ playerDetails.running + "', autoRetaliate = '"
					+ (playerDetails.autoRetaliate ? 1 : 0)
					+ "', combatMode = '" + (playerDetails.combatMode)
					+ "', lastmeleemode = '" + (playerDetails.lastMeleeMode)
					+ "', lastrangemode = '" + (playerDetails.lastRangeMode)
					+ "', starter = '" + playerDetails.starter
					+ "', online = '" + online + "' WHERE ID = '"
					+ playerDetails.ID + "'");
			String[] delete = { "banks", "friends", "ignores", "inventorys",
					"equipments", "looks", "skills", "lastkilled" };

			for (int i = 0; i < delete.length; i++)
				s.executeUpdate("DELETE FROM " + delete[i] + " WHERE ID = '"
						+ playerDetails.ID + "'");

			for (int i = 0; i < playerDetails.bankItems.length; i++) {
				long id = playerDetails.bankItems[i];
				if (id > 0 && id != -1)
					s.executeUpdate("INSERT INTO banks (ID,item,amount,slot) VALUES ('"
							+ playerDetails.ID
							+ "', '"
							+ id
							+ "', '"
							+ playerDetails.bankItemsN[i] + "', '" + i + "')");
			}

			for (int i = 0; i < playerDetails.playerItem.length; i++) {
				long id = playerDetails.playerItem[i];
				if (id > 0 && id != -1)
					s.executeUpdate("INSERT INTO inventorys (ID,item,amount,slot) VALUES ('"
							+ playerDetails.ID
							+ "', '"
							+ id
							+ "', '"
							+ playerDetails.playerItemN[i] + "', '" + i + "')");
			}

			for (int i = 0; i < playerDetails.playerEquipment.length; i++) {
				long id = playerDetails.playerEquipment[i];
				if (id > 0 && id != -1)
					s.executeUpdate("INSERT INTO equipments (ID,item,amount,slot) VALUES ('"
							+ playerDetails.ID
							+ "', '"
							+ id
							+ "', '"
							+ playerDetails.playerEquipmentN[i]
							+ "', '"
							+ i
							+ "')");
			}

			for (int i = 0; i < playerDetails.playerLevel.length; i++) {
				s.executeUpdate("INSERT INTO skills (ID,skill,xp,slot) VALUES ('"
						+ playerDetails.ID
						+ "', '"
						+ playerDetails.playerLevel[i]
						+ "', '"
						+ playerDetails.playerXP[i] + "', '" + i + "')");
			}

			for (int i = 0; i < playerDetails.playerLook.length; i++) {
				s.executeUpdate("INSERT INTO looks (ID,look,slot) VALUES ('"
						+ playerDetails.ID + "', '"
						+ playerDetails.playerLook[i] + "', '" + i + "')");
			}

			for (int i = 0; i < playerDetails.lastKilled.length; i++) {
				if (playerDetails.lastKilled[i] != null)
					s.executeUpdate("INSERT INTO lastkilled (ID,username,killed,slot) VALUES ('"
							+ playerDetails.ID
							+ "', '"
							+ playerDetails.username
							+ "', '"
							+ playerDetails.lastKilled[i] + "', '" + i + "')");
			}

			for (int i = 0; i < playerDetails.friends.length; i++) {
				long friend = playerDetails.friends[i];
				if (friend > 0)
					s.executeUpdate("INSERT INTO friends (ID,friend,slot) VALUES ('"
							+ playerDetails.ID
							+ "', '"
							+ friend
							+ "', '"
							+ i
							+ "')");
			}

			for (int i = 0; i < playerDetails.ignores.length; i++) {
				long ignore = playerDetails.ignores[i];
				if (ignore > 0)
					s.executeUpdate("INSERT INTO ignores (ID,ignored,slot) VALUES ('"
							+ playerDetails.ID
							+ "', '"
							+ ignore
							+ "', '"
							+ i
							+ "')");
			}

			s.close();
			Mysql.release();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void removePlayer(Player p) {
		if (((Client) p).getTradeHandler().stage > 0) {
			// do nothing
		} else {
			saveGame(p, true);
		}
		ActionManager.destructActions(((Client) p).getUsername());
		p.destruct();
		playerNameMap.remove(p.getUsername());
		playerNameLongMap.remove(Utils.playerNameToInt64(p.getUsername()));
	}

	public void shutdown() {
		for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
			if (players[i] == null)
				continue;
			players[i].destruct();
			players[i] = null;
		}
		playerNameMap.clear();
		playerNameLongMap.clear();
	}

}