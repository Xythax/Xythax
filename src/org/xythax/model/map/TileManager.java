package org.xythax.model.map;

import org.xythax.model.Entity;
import org.xythax.model.MobileEntity;
import org.xythax.model.NPC;

/**
 * 
 * @author Killamess
 * 
 */
public class TileManager {

	public static Tile generate(Entity entity, int x, int y, int z) {
		return new Tile(entity, new int[] { x, y, z });
	}

	public static Tile generate(Object entity, int x, int y, int z) {
		return new Tile(entity, new int[] { x, y, z });
	}

	public static Tile[] getTiles(MobileEntity mobileEntity) {

		int size = 1, tileCount = 0;

		if (mobileEntity instanceof NPC)
			size = NPC.getNPCSize(((NPC) mobileEntity).getDefinition()
					.getType());

		Tile[] tiles = new Tile[size * size];

		if (tiles.length == 1)
			tiles[0] = generate(mobileEntity, mobileEntity.getAbsX(),
					mobileEntity.getAbsY(), mobileEntity.getHeightLevel());
		else {
			for (int Xsize = 0; Xsize < size; Xsize++)
				for (int Ysize = 0; Ysize < size; Ysize++)
					tiles[tileCount++] = generate(mobileEntity,
							mobileEntity.getAbsX() + Xsize,
							mobileEntity.getAbsY() + Ysize,
							mobileEntity.getHeightLevel());
		}
		return tiles;
	}

	public static Tile[] getTiles(MobileEntity mobileEntity, int[] location) {

		int size = 1, tileCount = 0;

		if (mobileEntity instanceof NPC)
			size = NPC.getNPCSize(((NPC) mobileEntity).getDefinition()
					.getType());

		Tile[] tiles = new Tile[size * size];

		if (tiles.length == 1)
			tiles[0] = generate(mobileEntity, location[0], location[1],
					location[2]);
		else {
			for (int x = 0; x < size; x++)
				for (int y = 0; y < size; y++)
					tiles[tileCount++] = generate(mobileEntity,
							location[0] + x, location[1] + y, location[2]);
		}
		return tiles;
	}

	public static int calculateDistance(MobileEntity mobileEntity,
			MobileEntity following) {

		Tile[] tiles = getTiles(mobileEntity);

		int[] location = currentLocation(mobileEntity);
		int[] pointer = new int[tiles.length > 1 ? tiles.length : 1];

		int lowestCount = 20, count = 0;

		for (Tile newtiles : tiles) {
			if (newtiles.getTile() == location)
				pointer[count++] = 0;
			else
				pointer[count++] = TileManager.calculateDistance(newtiles,
						following);
		}
		for (int i = 0; i < pointer.length; i++)
			if (pointer[i] < lowestCount)
				lowestCount = pointer[i];

		return lowestCount;
	}

	public static int calculateDistance(Tile location, Entity other) {
		int X = Math.abs(location.getTile()[0] - other.getAbsX());
		int Y = Math.abs(location.getTile()[1] - other.getAbsY());
		return X > Y ? X : Y;
	}

	public static int calculateDistance(int[] location, Entity other) {
		int X = Math.abs(location[0] - other.getAbsX());
		int Y = Math.abs(location[1] - other.getAbsY());
		return X > Y ? X : Y;
	}

	public static int calculateDistance(int[] location, int[] other) {
		int X = Math.abs(location[0] - other[0]);
		int Y = Math.abs(location[0] - other[1]);
		return X > Y ? X : Y;
	}

	public static int[] currentLocation(Entity entity) {
		int[] currentLocation = new int[3];
		if (entity != null) {
			currentLocation[0] = entity.getAbsX();
			currentLocation[1] = entity.getAbsY();
			currentLocation[2] = entity.getHeightLevel();
		}
		return currentLocation;
	}

	public static int[] currentLocation(Tile tileLocation) {

		int[] currentLocation = new int[3];

		if (tileLocation != null) {
			currentLocation[0] = tileLocation.getTile()[0];
			currentLocation[1] = tileLocation.getTile()[1];
			currentLocation[2] = tileLocation.getTile()[2];
		}
		return currentLocation;
	}

	public static boolean inAttackablePosition(MobileEntity entity,
			MobileEntity following) {

		Tile[] tiles = getTiles(entity);
		Tile[] followingTiles = getTiles(following);

		for (Tile followTile : followingTiles) {
			for (Tile npcTile : tiles) {
				if (npcTile.getTile()[0] - 1 == followTile.getTile()[0]
						&& npcTile.getTile()[1] == followTile.getTile()[1]
						|| npcTile.getTile()[0] + 1 == followTile.getTile()[0]
						&& npcTile.getTile()[1] == followTile.getTile()[1]
						|| npcTile.getTile()[1] - 1 == followTile.getTile()[1]
						&& npcTile.getTile()[0] == followTile.getTile()[0]
						|| npcTile.getTile()[1] + 1 == followTile.getTile()[1]
						&& npcTile.getTile()[0] == followTile.getTile()[0])
					return true;
			}
		}
		return false;
	}
}
