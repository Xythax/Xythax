package org.xythax.model;

/**
 * Represents a world object
 * 
 * @author Graham
 */
public class WorldObject {

	private int objectID;
	private int objectX;
	private int objectY;
	private int objectHeight;

	public static enum Face {
		NORTH, EAST, SOUTH, WEST,
	};

	private Face objectFace = Face.NORTH;

	private int objectType;

	public WorldObject(int objectID, int objectX, int objectY,
			int objectHeight, Face objectFace, int objectType) {
		this.objectID = objectID;
		this.objectX = objectX;
		this.objectY = objectY;
		this.objectHeight = objectHeight;
		this.objectFace = objectFace;
		this.objectType = objectType;
	}

	public WorldObject(int objectX, int objectY, int objectID, int objectFace,
			int objectType) {
		this.objectID = objectID;
		this.objectX = objectX;
		this.objectY = objectY;
		this.objectHeight = 0;
		this.objectFace = Face.NORTH;
		this.objectType = objectType;
	}

	public int getObjectID() {
		return objectID;
	}

	public int getObjectX() {
		return objectX;
	}

	public int getObjectY() {
		return objectY;
	}

	public int getObjectHeight() {
		return objectHeight;
	}

	public int getObjectFace() {
		if (objectFace == Face.SOUTH)
			return -3;
		if (objectFace == Face.EAST)
			return -2;
		if (objectFace == Face.NORTH)
			return -1;
		return 0;
	}

	public int getObjectType() {
		return objectType;
	}

}
