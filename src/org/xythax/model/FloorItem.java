package org.xythax.model;

import org.xythax.world.PlayerManager;

/**
 * Represents a dropped item
 * 
 * @author Graham
 */

public class FloorItem extends Item {

	private long droppedAt;
	private Client droppedBy;
	private int x;
	private int y;
	private int height;
	private boolean isSpawn;
	private String username;

	public Client getDroppedBy() {
		return droppedBy;
	}

	public void setPos(int x, int y, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
	}

	public long getDroppedAt() {
		return droppedAt;
	}

	public void resetOwner() {
		droppedBy = null;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public FloorItem(int id, int amount, String player, int x, int y, int height) {
		super(id, amount);
		Client client = null;
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (!p.isActive)
				continue;
			if (p.getUsername() == player) {
				client = (Client) p;
				break;
			}
		}

		this.droppedAt = System.currentTimeMillis();
		this.droppedBy = client;
		if (client != null)
			this.username = getDroppedBy().getUsername();
		this.x = x;
		this.y = y;
		this.height = height;
		this.isSpawn = false;
	}

	public void setSpawn(boolean val) {
		isSpawn = val;
	}

	public boolean isSpawn() {
		return isSpawn;
	}

	public String getUsername() {
		return username;
	}

}
