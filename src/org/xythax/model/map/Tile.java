package org.xythax.model.map;

/**
 * 
 * @author Killamess
 * 
 */

public class Tile {

	private Object entity;

	private int[] tileLocation;

	public Tile(Object entity, int[] location) {
		setEntity(entity);
		setTile(location);
	}

	public void setTile(int[] tileLocation) {
		this.tileLocation = tileLocation;
	}

	public int[] getTile() {
		return tileLocation;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public Object getEntity() {
		return entity;
	}
}
