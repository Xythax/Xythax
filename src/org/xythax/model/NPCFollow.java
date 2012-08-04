package org.xythax.model;

import org.xythax.model.Entity.CombatType;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.map.FlagMap;
import org.xythax.model.map.Tile;
import org.xythax.model.map.TileManager;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;
import org.xythax.world.PlayerManager;

/**
 * 
 * @author killamess
 * 
 */
public class NPCFollow {

	public static boolean validSidewardsWalker(NPC npc) {
		return NPC.getNPCSize(npc.getDefinition().getType()) == 1;
	}

	/**
	 * work out which way we first need to go, at the most 3 tiles should be
	 * checked?
	 * 
	 * @param npc
	 * @param following
	 */
	public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;

	public static void runFollow(MobileEntity npc, MobileEntity following) {

		if (npc == null || following == null)
			return;

		if (npc.getFreezeDelay() > 0)
			return;

		int[] location = TileManager.currentLocation(npc);
		int[] locationCopy = TileManager.currentLocation(npc);
		int[] location2 = TileManager.currentLocation(following);

		Tile[] npcTiles = TileManager.getTiles(npc);
		Tile[] followingTiles = TileManager.getTiles(following);

		int actualDistance = TileManager.calculateDistance(npc, following);

		if (npc.getCombatType() != CombatType.MELEE) {
			if (actualDistance < 8 && actualDistance != 0) {
				return;
			}
		}
		if (actualDistance >= 20) {
			npc.following = null;
			CombatEngine.resetAttack(npc, true);
			return;
		}

		if (actualDistance == 0) {

			switch (Utils.random(3)) {
			case 0:
				location[0] += 1;
				break;
			case 1:
				location[1] += 1;
				break;
			case 2:
				location[0] -= 1;
				break;
			case 3:
				location[1] -= 1;
				break;
			}
			Tile[] testTiles = TileManager.getTiles(npc, location);

			for (Tile tiles : testTiles)
				if (FlagMap.locationOccupied(
						TileManager.currentLocation(tiles), npc))
					return;

		} else if (actualDistance > 1) {

			boolean[] movement = new boolean[4];

			final int noNorth = 0, noEast = 1, noSouth = 2, noWest = 3;

			for (Tile followTile : followingTiles) {
				for (Tile tiles : npcTiles) {
					if (tiles.getTile()[0] == followTile.getTile()[0]) { // same
																			// x
																			// line
						movement[noEast] = true;
						movement[noWest] = true;
					} else if (tiles.getTile()[1] == followTile.getTile()[1]) { // same
																				// y
																				// line
						movement[noNorth] = true;
						movement[noSouth] = true;
					}
				}
			}
			if (location2[0] > location[0]) {
				if (!movement[noWest])
					location[0] = location[0] + 1;
			} else if (location2[0] < location[0]) {
				if (!movement[noEast])
					location[0] = location[0] - 1;
			}
			if (location2[1] > location[1]) {
				if (!movement[noNorth])
					location[1] = location[1] + 1;
			} else if (location2[1] < location[1]) {
				if (!movement[noSouth])
					location[1] = location[1] - 1;
			}

			int[] northLine = new int[3];
			northLine[0] = locationCopy[0];
			northLine[1] = locationCopy[1] + 1;
			northLine[2] = location[2];

			int[] eastLine = new int[3];
			eastLine[0] = locationCopy[0] + 1;
			eastLine[1] = locationCopy[1];
			eastLine[2] = location[2];

			int[] southLine = new int[3];
			southLine[0] = locationCopy[0];
			southLine[1] = locationCopy[1] - 1;
			southLine[2] = location[2];

			int[] westLine = new int[3];
			westLine[0] = locationCopy[0] - 1;
			westLine[1] = locationCopy[1];
			westLine[2] = location[2];

			int[] xLine = new int[3];
			xLine[0] = location[0];
			xLine[1] = locationCopy[1];
			xLine[2] = location[2];

			int[] yLine = new int[3];
			yLine[0] = locationCopy[0];
			yLine[1] = location[1];
			yLine[2] = location[2];

			boolean[] canGo = new boolean[4];
			boolean[] invalidMove = new boolean[3];
			boolean foundPath = false;

			Tile[] north = TileManager.getTiles(npc, northLine);
			Tile[] east = TileManager.getTiles(npc, eastLine);
			Tile[] south = TileManager.getTiles(npc, southLine);
			Tile[] west = TileManager.getTiles(npc, westLine);
			Tile[] diagonalTiles = TileManager.getTiles(npc);
			Tile[] xTiles = TileManager.getTiles(npc, xLine);
			Tile[] yTiles = TileManager.getTiles(npc, yLine);

			for (Tile northTile : north)
				if (FlagMap.locationOccupied(
						TileManager.currentLocation(northTile), npc))
					canGo[0] = true;
			for (Tile eastTile : east)
				if (FlagMap.locationOccupied(
						TileManager.currentLocation(eastTile), npc))
					canGo[1] = true;
			for (Tile southTile : south)
				if (FlagMap.locationOccupied(
						TileManager.currentLocation(southTile), npc))
					canGo[2] = true;
			for (Tile westTile : west)
				if (FlagMap.locationOccupied(
						TileManager.currentLocation(westTile), npc))
					canGo[3] = true;

			for (Tile diagonalTile : diagonalTiles)
				if (FlagMap.locationOccupied(
						TileManager.currentLocation(diagonalTile), npc))
					invalidMove[0] = true;
			for (Tile sideway : xTiles)
				if (FlagMap.locationOccupied(
						TileManager.currentLocation(sideway), npc))
					invalidMove[1] = true;
			for (Tile sideway2 : yTiles)
				if (FlagMap.locationOccupied(
						TileManager.currentLocation(sideway2), npc))
					invalidMove[2] = true;

			for (int i = 0; i < canGo.length; i++) {
				if (canGo[i]) {
					if (location[1] < location2[1])
						if (i == 0)
							location[1] = locationCopy[1];
					if (location[1] > location2[1])
						if (i == 2)
							location[1] = locationCopy[1];
					if (location[0] > location2[0])
						if (i == 1)
							location[0] = locationCopy[0];
					if (location[0] < location2[0])
						if (i == 3)
							location[0] = locationCopy[0];
				}
			}
			for (int i = 0; i < invalidMove.length; i++) {
				if (invalidMove[i] == false) {
					foundPath = true;
					if (i == 0) {
						break;
					} else if (i == 1) {
						location[0] = xLine[0];
						location[1] = xLine[1];
					} else if (i == 2) {
						location[0] = yLine[0];
						location[1] = yLine[1];
					} else {
						return;
					}
				}
			}
			if (!foundPath)// && !alreadyOnNPC)
				return;

			for (int i2 = 0; i2 < Constants.MAX_PLAYERS; i2++) {

				Client client = (Client) PlayerManager.getPlayerManager()
						.getPlayers()[i2];

				if (client == null || client.isDead())
					continue;

				Tile[] testTiles = TileManager.getTiles(npc, location);

				for (Tile tiles : testTiles) {
					if (tiles.getTile()[0] == client.getAbsX()
							&& tiles.getTile()[1] == client.getAbsY())
						return;
				}
			}
		} else if (actualDistance == 1 && validSidewardsWalker((NPC) npc)
				&& !inMeleeDistance(npc, following)) {

			int randomSelection = Utils.random(1);
			int original = location[randomSelection];
			int opposite = randomSelection == 0 ? 1 : 0;

			location[randomSelection] = (location[randomSelection] > location2[randomSelection] ? location[randomSelection] -= 1
					: location[randomSelection] < location2[randomSelection] ? location[randomSelection] += 1
							: location[randomSelection]);

			if (location[randomSelection] == original)
				location[opposite] = (location[opposite] > location2[opposite] ? location[opposite] -= 1
						: location[opposite] < location2[opposite] ? location[opposite] += 1
								: location[opposite]);

			Tile[] movementTile = TileManager.getTiles(npc, location);

			for (Tile tiles : movementTile) {
				if (FlagMap.locationOccupied(
						TileManager.currentLocation(tiles), npc)) {
					return;
				}
			}
		}
		Tile[] movementTile = TileManager.getTiles(npc, location);

		for (Tile tiles : movementTile) {
			if (FlagMap.locationOccupied(TileManager.currentLocation(tiles),
					npc)) {
				return;
			}
		}
		((NPC) npc).setWalk(location[0], location[1], false);
	}

	public static boolean inMeleeDistance(Entity entity, Entity following) {
		int[] location = TileManager.currentLocation(following);
		int[] location2 = TileManager.currentLocation(entity);

		return location[0] - 1 == location2[0] && location[1] == location2[1]
				|| location[0] + 1 == location2[0]
				&& location[1] == location2[1]
				|| location[1] - 1 == location2[1]
				&& location[0] == location2[0]
				|| location[1] + 1 == location2[1]
				&& location[0] == location2[0];
	}

	public static int[] getHomeLocation(NPC npc) {
		int[] locationArray = new int[3];
		locationArray[0] = npc.spawnAbsX;
		locationArray[1] = npc.spawnAbsY;
		locationArray[2] = npc.getHeightLevel();
		return locationArray;
	}
}
