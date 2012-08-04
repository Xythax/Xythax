package org.xythax.model.definition.skills;

public class Teleport {

	private String name;
	private int id;
	private int[] location;
	private int level;
	private int xp;
	private int[] runes;
	private int[] amounts;

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getAbsX() {
		return location[0];
	}

	public int getAbsY() {
		return location[1];
	}

	public int getHeight() {
		return location[2];
	}

	public int getLevel() {
		return level;
	}

	public int getXp() {
		return xp;
	}

	public int[] getRunes() {
		return runes;
	}

	public int[] getAmounts() {
		return amounts;
	}
}