package org.xythax.model.definition.skills;

public class Catches {

	private int fishingSpot;
	private int animation;
	private int[] catches = new int[3];
	private int[] tools = new int[2];
	private int[] catchLevel = new int[3];
	private int[] toolAmounts = new int[2];

	public int getFishingSpot() {
		return fishingSpot;
	}

	public int getAnimation() {
		return animation;
	}

	public int[] getCatches() {
		return catches;
	}

	public int[] getCatchLevels() {
		return catchLevel;
	}

	public int[] getTools() {
		return tools;
	}

	public int[] getToolAmounts() {
		return toolAmounts;
	}

}
