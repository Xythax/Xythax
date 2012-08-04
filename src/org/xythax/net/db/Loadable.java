package org.xythax.net.db;

import java.sql.ResultSet;
import java.sql.Statement;

import org.xythax.core.GameEngine;
import org.xythax.model.NPC;
import org.xythax.model.NPCAttacks;
import org.xythax.model.NPCDrop;
import org.xythax.model.Shop;
import org.xythax.model.ShopItem;
import org.xythax.model.definition.entity.NPCDefinition;
import org.xythax.model.map.FlagMap;
import org.xythax.model.map.Tile;
import org.xythax.model.map.TileManager;
import org.xythax.utils.Constants;
import org.xythax.world.NPCManager;

/**
 * 
 * @author killamess Create 1 connection to receive all SQL features.
 */

public class Loadable extends Mysql {

	/**
	 * Stores a NPC to the database
	 * 
	 * @param n
	 */
	public static void storeNpcToDatabase(NPC n) {
		Statement s;
		try {
			s = getConnection().createStatement();
			NPC npc = n;

			s.execute("INSERT INTO `npcspawns`(`id`,`absX`,`absY`,`height`,`rangeX1`,`rangeX2`,`rangeY1`, `rangeY2`, `walktype` ) VALUES ('"
					+ npc.getNpcId()
					+ "', '"
					+ npc.getAbsX()
					+ "', '"
					+ npc.getHeightLevel()
					+ "', '"
					+ npc.getAbsY()
					+ "', '"
					+ npc.getX1()
					+ "','"
					+ npc.getY1()
					+ "','"
					+ npc.getX2()
					+ "','" + npc.getY2() + "','" + npc.getX2() + "')'");
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void runSQLQuerys() {
		try {

			Statement s = getConnection().createStatement();
			System.out.println("[SQL] Connection State: Granted.");

			/**
			 * Shops
			 */
			s.executeQuery("SELECT * FROM shops");

			ResultSet results = s.getResultSet();

			while (results.next()) {

				int id = Integer.valueOf(results.getInt("id"));
				String sname = results.getString("name");
				String type = results.getString("type");
				int currency = results.getInt("currency");
				Shop.Type t = Shop.Type.GENERAL;
				if (type.equals("SPECIALIST"))
					t = Shop.Type.SPECIALIST;
				Shop a = new Shop(id, sname, t, currency);
				String[] itemid = results.getString("itemid").split(",");
				String[] amount = results.getString("amount").split(",");
				for (int i = 0; i < itemid.length; i++) {
					a.addItem(new ShopItem(Integer.valueOf(itemid[i]), Integer
							.valueOf(amount[i]), Integer.valueOf(amount[i])));
				}
				GameEngine.getShopManager().shops.put(id, a);
			}

			/**
			 * NPC definitions
			 */
			s.executeQuery("SELECT * FROM npcdefinitions");

			results = s.getResultSet();

			while (results.next()) {
				int time = GameEngine.getNPCManager().DEFAULT_RESPAWN_TIME;
				if (results.getInt("respawn") > 0) {
					time = Integer.valueOf(results.getInt("respawn"));
				}
				NPCDefinition def = new NPCDefinition(results.getInt("id"),
						results.getInt("combat"), results.getInt("health"),
						results.getString("name"), time);
				GameEngine.getNPCManager().npcDefinitions.put(def.getType(),
						def);
			}

			System.out.println("[SQL] Loaded "
					+ GameEngine.getNPCManager().npcDefinitions.size()
					+ " different npc definitions.");

			/**
			 * NPC Attacks
			 */
			s.executeQuery("SELECT * FROM npcattacks");

			results = s.getResultSet();

			int totalLoaded = 0;

			while (results.next()) {

				int id = results.getInt("id");

				NPCAttacks.npcArray[id][0] = results.getInt("combattype");
				NPCAttacks.npcArray[id][1] = results.getInt("maxhit");
				NPCAttacks.npcArray[id][2] = results.getInt("attacklevel");
				NPCAttacks.npcArray[id][3] = results.getInt("magiclevel");
				NPCAttacks.npcArray[id][4] = results.getInt("rangedlevel");
				NPCAttacks.npcArray[id][5] = results.getInt("attackdefence");
				NPCAttacks.npcArray[id][6] = results.getInt("magicdefence");
				NPCAttacks.npcArray[id][7] = results.getInt("rangeddefence");
				totalLoaded++;
			}
			System.out.println("[SQL] Loaded " + totalLoaded
					+ " different npc attacks.");

			/**
			 * NPC Emotions
			 */
			s.executeQuery("SELECT * FROM npcemotions");

			results = s.getResultSet();

			int animationsLoaded = 0;

			while (results.next()) {
				int id = results.getInt("id");
				GameEngine.getNPCManager();
				NPCManager.emotions[id][0] = results.getInt("attack");
				NPCManager.emotions[id][1] = results.getInt("block");
				NPCManager.emotions[id][2] = results.getInt("death");
				animationsLoaded++;
			}
			System.out.println("[SQL] Loaded " + animationsLoaded
					+ " different npc animations.");

			/**
			 * NPC spawns
			 */
			s.executeQuery("SELECT * FROM npcspawns");

			results = s.getResultSet();

			while (results.next()) {

				int slot = GameEngine.getNPCManager().freeSlot();

				NPCDefinition def = GameEngine.getNPCManager().npcDefinitions
						.get(results.getInt("id"));

				if (def == null)
					continue;

				NPC npc = new NPC(slot, def, results.getInt("absX"),
						results.getInt("absY"), results.getInt("height"));

				npc.setX1(results.getInt("rangeX1"));
				npc.setY1(results.getInt("rangeY1"));
				npc.setX2(results.getInt("rangeX2"));
				npc.setY2(results.getInt("rangeY2"));

				int walkType = Integer.valueOf(results.getInt("walktype"));

				if (walkType == 1 || walkType == 2)
					npc.setWalking(true);
				if (walkType == 0)
					npc.setWalking(false);

				GameEngine.getNPCManager().npcMap.put(npc.getNpcId(), npc);

				Tile[] newLocation = TileManager.getTiles(npc);

				if (npc.getDefinition().getType() == Constants.PET_TYPE)
					continue;

				for (Tile newTiles : newLocation) {
					FlagMap.set(TileManager.currentLocation(newTiles), true);
				}
			}
			System.out.println("[SQL] Loaded "
					+ GameEngine.getNPCManager().npcMap.size()
					+ " different npc spawns.");

			/**
			 * NPC Drops
			 */
			s.executeQuery("SELECT * FROM npcdrops");
			results = s.getResultSet();

			int dropDefs = 0;
			int dropItems = 0;

			while (results.next()) {

				int npc = results.getInt("id");

				String[] id = results.getString("itemid").split(",");
				String[] amounts = results.getString("amount").split(",");
				String[] percents = results.getString("percent").split(",");
				for (int i = 0; i < id.length; i++) {
					int dropID = Integer.parseInt(id[i]);
					int amount = Integer.parseInt(amounts[i]);
					float percent = Float.parseFloat(percents[i]);
					NPCDrop drop = new NPCDrop(dropID, amount, percent);
					GameEngine.getNPCManager().npcDefinitions.get(npc).addDrop(
							drop);
					dropItems++;
				}
				dropDefs++;
			}
			System.out.println("[SQL] Loaded " + dropDefs
					+ " npc drop definitions (total " + dropItems + " items).");

			/**
			 * Close connection
			 */
			results.close();
			Mysql.release();
			System.out.println("[SQL] Connection State: Idle.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
