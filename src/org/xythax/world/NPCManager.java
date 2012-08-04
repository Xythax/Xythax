package org.xythax.world;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.xythax.model.NPC;
import org.xythax.model.NPCDrop;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.definition.entity.NPCDefinition;
import org.xythax.model.map.FlagMap;
import org.xythax.model.map.FollowEngine;
import org.xythax.model.map.Tile;
import org.xythax.model.map.TileManager;
import org.xythax.net.db.Mysql;

/**
 * Manages NPCs
 * 
 * @author Graham
 */

public class NPCManager {

	public static final int MAXIMUM_NPCS = 4096;

	public static int[][] emotions = new int[MAXIMUM_NPCS][3];

	public final int DEFAULT_RESPAWN_TIME = 16;

	public Map<Integer, NPC> npcMap;
	public Map<Integer, NPCDefinition> npcDefinitions;

	/**
	 * Creates the NPC manager.
	 * 
	 */
	public NPCManager() {
		npcMap = new HashMap<Integer, NPC>();
		npcDefinitions = new HashMap<Integer, NPCDefinition>();

	}

	// removed from startup
	public void loadDrops() {
		int dropDefs = 0;
		int dropItems = 0;
		try {
			Connection con = Mysql.getConnection();
			Statement s = con.createStatement();
			s.executeQuery("SELECT * FROM npcdrops");
			ResultSet rs = s.getResultSet();
			while (rs.next()) {
				int npc = rs.getInt("id");
				String[] id = rs.getString("itemid").split(",");
				String[] amount = rs.getString("amount").split(",");
				String[] percent = rs.getString("percent").split(",");
				for (int i = 0; i < id.length; i++) {
					NPCDrop drop = new NPCDrop(Integer.valueOf(id[i]),
							Integer.valueOf(amount[i]),
							Integer.valueOf(percent[i]));
					npcDefinitions.get(npc).addDrop(drop);
					dropItems++;
				}
				dropDefs++;
			}
			s.close();
			rs.close();
			con.close();
			Mysql.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Loaded " + dropDefs
				+ " npc drop definitions (total " + dropItems + " items).");
	}

	public void reloadSpawns() {
		npcMap.clear();
		loadSpawns();
	}

	// removed from startup
	private void loadSpawns() {
		try {
			Connection con = Mysql.getConnection();
			Statement s = con.createStatement();
			s.executeQuery("SELECT * FROM npcspawns");
			ResultSet rs = s.getResultSet();
			FlagMap.resetFlaggedLocations();
			while (rs.next()) {
				int slot = freeSlot();
				NPCDefinition def = npcDefinitions.get(rs.getInt("id"));
				if (def == null)
					continue;
				NPC npc = new NPC(slot, def, rs.getInt("absX"),
						rs.getInt("absY"), rs.getInt("height"));
				npc.setX1(rs.getInt("rangeX1"));
				npc.setY1(rs.getInt("rangeY1"));
				npc.setX2(rs.getInt("rangeX2"));
				npc.setY2(rs.getInt("rangeY2"));
				int walkType = Integer.valueOf(rs.getInt("walktype"));
				if (walkType == 1 || walkType == 2) {
					npc.setWalking(true);
				}
				if (walkType == 0) {
					npc.setWalking(false);
				}
				npcMap.put(npc.getNpcId(), npc);
				Tile[] newLocation = TileManager.getTiles(npc);

				for (Tile newTiles : newLocation)
					FlagMap.set(TileManager.currentLocation(newTiles), true);
			}
			s.close();
			rs.close();
			con.close();
			Mysql.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Loaded " + npcMap.size() + " npc spawns.");
	}

	/**
	 * Search for a free slot.
	 */
	private int slotSearchStart = 1;

	/**
	 * Gets a free slot.
	 * 
	 * @return
	 */
	public int freeSlot() {
		int slot = slotSearchStart;
		while (true) {
			if (npcMap.get(slot) == null) {
				slotSearchStart = slot + 1;
				return slot;
			}
		}
	}

	/**
	 * Gets the NPC with the specified id.
	 * 
	 * @param id
	 * @return
	 */
	public NPC getNPC(int id) {
		return npcMap.get(id);
	}

	/**
	 * Processes all NPCs.
	 */
	public void process() {
		for (Map.Entry<Integer, NPC> entry : npcMap.entrySet()) {
			if (entry.getValue() == null)
				continue;

			NPC n = entry.getValue();

			n.process();

			FollowEngine.loop(n);

			CombatEngine.mainProcessor(n);
		}
	}

	/**
	 * Get NPC definition.
	 * 
	 * @param npc
	 * @return
	 */
	public NPCDefinition getNPCDefinition(int npc) {
		return npcDefinitions.get(npc);
	}

	/**
	 * Gets the NPC map
	 * 
	 * @return
	 */
	public Map<Integer, NPC> getNPCMap() {
		return this.npcMap;
	}

}
