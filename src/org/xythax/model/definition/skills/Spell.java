package org.xythax.model.definition.skills;

public class Spell {

	private String name;
	private int id;
	private int magicLevel;
	private int damage;
	private int freezeDelay;
	private int startGfxDelay;
	private int projectileDelay;
	private int endGfxDelay;
	private int endHitDelay;
	private int animationId;
	private int endGfxHeight;
	private int projectileStartHeight;
	private int projectileEndHeight;
	private int handGfx;
	private int airGfx;
	private int endGfx;
	private int[] runes;
	private int[] amounts;
	private int xp;

	public int getId() {
		return id;
	}

	public int getMagicLevel() {
		return magicLevel;
	}

	public int getDamage() {
		return damage;
	}

	public int getFreezeDelay() {
		return freezeDelay;
	}

	public int getStartGfxDelay() {
		return startGfxDelay;
	}

	public int getProjectileDelay() {
		return projectileDelay;
	}

	public int getEndGfxDelay() {
		return endGfxDelay;
	}

	public int getEndHitDelay() {
		return endHitDelay;
	}

	public int getAnimationId() {
		return animationId;
	}

	public int getEndGfxHeight() {
		return endGfxHeight;
	}

	public int getProjectileStartHeight() {
		return projectileStartHeight;
	}

	public int getProjectileEndHeight() {
		return projectileEndHeight;
	}

	public int getHandGfx() {
		return handGfx;
	}

	public int getAirGfx() {
		return airGfx;
	}

	public int getEndGfx() {
		return endGfx;
	}

	public int[] getRunes() {
		return runes;
	}

	public int[] getAmounts() {
		return amounts;
	}

	public String getName() {
		return name;
	}

	public int getXp() {
		return xp;
	}
}
