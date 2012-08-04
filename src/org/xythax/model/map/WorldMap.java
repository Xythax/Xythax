package org.xythax.model.map;

import java.io.RandomAccessFile;

import org.xythax.core.GameEngine;

/**
 * 
 * @author Killamess Load martins binary file into my Flag System
 * 
 */
public class WorldMap {

	private static String[] unblockObjects = { "daisies", "fern", "thistle",
			"stones" };

	@SuppressWarnings("unused")
	public static void load() {
		long start = System.currentTimeMillis();
		RandomAccessFile worldmap = null;
		byte[] cache = null;
		try {
			worldmap = new RandomAccessFile("./data/world/worldmap.bin", "r");
			cache = new byte[(int) worldmap.length()];
			worldmap.read(cache, 0, (int) worldmap.length());
			worldmap.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int ptr = 0;
		int objectsCreated = 0;
		int objectTiles = 0;
		while (ptr < 11525562) {
			try {
				boolean skipObject = false;
				int objectId = (((cache[ptr++] & 0xFF) << 8) | (cache[ptr++] & 0xFF));
				int objectX = (((cache[ptr++] & 0xFF) << 8) | (cache[ptr++] & 0xFF));
				int objectY = (((cache[ptr++] & 0xFF) << 8) | (cache[ptr++] & 0xFF));
				int objectHeight = cache[ptr++] & 0xFF;
				int objectType = cache[ptr++] & 0xFF;
				int objectFace = cache[ptr++] & 0xFF;

				if (GameEngine.getObjectManager().getDefinition(objectId) != null) {
					for (int i = 0; i < unblockObjects.length; i++) {
						if (unblockObjects[i].equalsIgnoreCase(GameEngine
								.getObjectManager().getDefinition(objectId)
								.getName())) {
							skipObject = true;
							break;
						}
					}
					if (skipObject) {
						skipObject = false;
						continue;
					}

					int x = GameEngine.getObjectManager()
							.getDefinition(objectId).getXSize();
					int y = GameEngine.getObjectManager()
							.getDefinition(objectId).getYSize();

					for (int xSize = 0; xSize < x; xSize++) {
						for (int ySize = 0; ySize < y; ySize++) {
							if (objectType != 22) {
								FlagMap.set(new int[] { objectX + xSize,
										objectY + ySize, objectHeight }, true);
								objectTiles++;
							}
						}
					}
					objectsCreated++;
				} else {
					if (objectType != 22)
						FlagMap.set(
								new int[] { objectX, objectY, objectHeight },
								true);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		System.out.println("Injected FlagMap with " + objectsCreated
				+ " objects covering " + objectTiles + " tiles in "
				+ (System.currentTimeMillis() - start) + "ms.");
	}
}