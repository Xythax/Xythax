package org.xythax.model.map;

import org.xythax.model.MobileEntity;

/**
 * 
 * @author killamess Puts a flag at all NPC locations currently occupied.
 */
public class FlagMap {

	private static boolean[][] flaggedLocations = new boolean[4000][11000];

	public static void set(int[] pointer, boolean val) {
		flaggedLocations[pointer[0]][pointer[1]] = val;
	}

	public static boolean locationOccupied(int[] pointer, MobileEntity entity) {
		if (pointer[0] <= 0 || pointer[1] <= 0 || pointer[2] < 0) {
			return false;
		}
		if (flaggedLocations[pointer[0]][pointer[1]]) {

			Tile[] entityTiles = TileManager.getTiles(entity);

			for (Tile tiles : entityTiles)
				if (tiles.getTile()[0] == pointer[0]
						&& tiles.getTile()[1] == pointer[1])
					return false;
		}
		return flaggedLocations[pointer[0]][pointer[1]];
	}

	public static void resetFlaggedLocations() {
		for (int x = 0; x < 4000; x++)
			for (int y = 0; y < 11000; y++)
				flaggedLocations[x][y] = false;
	}
}
