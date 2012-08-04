package org.xythax.model;

/**
 * An NPC drop.
 */
public class NPCDrop extends Item {

	private float chance;

	public NPCDrop(int id, int amount, float chance) {
		super(id, amount);
		this.chance = chance;
	}

	public float getChance() {
		return this.chance;
	}

}
