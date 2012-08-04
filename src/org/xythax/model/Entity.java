package org.xythax.model;

import org.xythax.model.map.Tile;

public abstract class Entity {

	private Tile location;
	private int retaliateDelay;

	private MobileEntity owner;
	public MobileEntity following;

	public boolean walkingHome;
	private Entity inCombatWith;
	private int inCombatWithTimer;

	private int[] teleportTo = new int[2];

	public int getTeleportToX() {
		return teleportTo[0];
	}

	public int getTeleportToY() {
		return teleportTo[1];
	}

	public int setTeleportCordinate(int slot, int cord) {
		return teleportTo[slot] = cord;
	}

	private boolean hasHit = false;

	private boolean hasHitTarget[] = new boolean[9];

	private int freezeDelay;
	private int multiHitDelay;
	private int holdingSpell;
	private int holdingSpellDelay;
	private MobileEntity holdingSpellTarget;

	/**
	 * Current position.
	 */
	private int absY;
	private int absX;
	private int heightLevel;

	/**
	 * @param absX
	 *            the absX to set
	 */
	public void setAbsX(int absX) {
		this.absX = absX;
	}

	/**
	 * @return the absX
	 */
	public int getAbsX() {
		return absX;
	}

	/**
	 * @param absY
	 *            the absY to set
	 */
	public void setAbsY(int absY) {
		this.absY = absY;
	}

	/**
	 * @return the absY
	 */
	public int getAbsY() {
		return absY;
	}

	/**
	 * @param heightLevel
	 *            the heightLevel to set
	 */
	public void setHeightLevel(int heightLevel) {
		this.heightLevel = heightLevel;
	}

	/**
	 * @return the heightLevel
	 */
	public int getHeightLevel() {
		return heightLevel;
	}

	/**
	 * Specials.
	 */
	private boolean usingSpecial;

	public boolean usingSpecial() {
		return usingSpecial;
	}

	public void setUsingSpecial(boolean newType) {
		this.usingSpecial = newType;
	}

	private MobileEntity target = null;

	/**
	 * The attack delay.
	 */
	private int attackDelay;

	/**
	 * The combat Type.
	 */
	public CombatType combatType = null;

	/**
	 * The combatTypes this system supports.
	 */
	public enum CombatType {
		MELEE, RANGE, MAGIC, NOTHING, POISON, RECOIL, THIEF
	}

	/**
	 * @return the combatDelay.
	 */
	public int getCombatDelay() {
		return attackDelay;
	}

	/**
	 * @param combatDelay
	 *            . the combatDelay to set.
	 */
	public int setCombatDelay(int newDelay) {
		return this.attackDelay = newDelay;
	}

	/**
	 * @param combatDelay
	 *            . the combatDelay to set.
	 */
	public void deductCombatDelay() {
		this.attackDelay--;
	}

	public MobileEntity getTarget() {
		return target;
	}

	public void setTarget(Entity id) {
		this.target = (MobileEntity) id;
	}

	/**
	 * @return the combatType
	 */
	public CombatType getCombatType() {
		return combatType;
	}

	/**
	 * @param CombatType
	 *            . the combatType to set.
	 */
	public CombatType setCombatType(CombatType newType) {
		return this.combatType = newType;
	}

	private boolean isBusy;

	public void setBusy(boolean type) {
		this.isBusy = type;
	}

	public boolean isBusy() {
		return isBusy;
	}

	private int busyTimer;

	public void setBusyTimer(int newTime) {
		this.busyTimer = newTime;
		this.isBusy = newTime > 0;
	}

	public int getBusyTimer() {
		return busyTimer;
	}

	public int deductBusyTimer() {
		return busyTimer--;
	}

	private MobileEntity[] multiList = new MobileEntity[9];

	public void setMultiList(MobileEntity ent, int slot) {
		this.multiList[slot] = ent;
	}

	public MobileEntity getMultiList(int slot) {
		return multiList[slot];
	}

	private boolean charged = false;

	public void setCharged(boolean type) {
		this.charged = type;
	}

	public boolean isCharged() {
		return charged;
	}

	private int vengTimer;

	public int getVengTimer() {
		return vengTimer;
	}

	public void deductVengTimer() {
		this.vengTimer -= 1;
	}

	public void setVengTimer() {
		this.vengTimer = 60;
	}

	/**
	 * @param holdingSpell
	 *            the holdingSpell to set
	 */
	public void setHoldingSpell(int holdingSpell) {
		this.holdingSpell = holdingSpell;
	}

	/**
	 * @return the holdingSpell
	 */
	public int getHoldingSpell() {
		return holdingSpell;
	}

	/**
	 * @param holdingSpellDelay
	 *            the holdingSpellDelay to set
	 */
	public void setHoldingSpellDelay(int holdingSpellDelay) {
		this.holdingSpellDelay = holdingSpellDelay;
	}

	/**
	 * @return the holdingSpellDelay
	 */
	public int getHoldingSpellDelay() {
		return holdingSpellDelay;
	}

	/**
	 * @param holdingSpellTarget
	 *            the holdingSpellTarget to set
	 */
	public void setHoldingSpellTarget(MobileEntity holdingSpellTarget) {
		this.holdingSpellTarget = holdingSpellTarget;
	}

	/**
	 * @return the holdingSpellTarget
	 */
	public MobileEntity getHoldingSpellTarget() {
		return holdingSpellTarget;
	}

	/**
	 * @param freezeDelay
	 *            the freezeDelay to set
	 */
	public void setFreezeDelay(int freezeDelay) {
		this.freezeDelay = freezeDelay;
	}

	/**
	 * @return the freezeDelay
	 */
	public int getFreezeDelay() {
		return freezeDelay;
	}

	/**
	 * @param inCombatWith
	 *            the inCombatWith to set
	 */
	public void setInCombatWith(Entity inCombatWith) {
		this.inCombatWith = inCombatWith;
	}

	/**
	 * @return the inCombatWith
	 */
	public Entity getInCombatWith() {
		return inCombatWith;
	}

	/**
	 * @param inCombatWithTimer
	 *            the inCombatWithTimer to set
	 */
	public void setInCombatWithTimer() {
		this.inCombatWithTimer = 10;
	}

	public void deductInCombatWithTimer() {
		this.inCombatWithTimer--;
	}

	public void setInCombatTimer(int i) {
		this.inCombatWithTimer = 0;
	}

	/**
	 * @return the inCombatWithTimer
	 */
	public int getInCombatWithTimer() {
		return inCombatWithTimer;
	}

	public void setLocation(int[] location) {
		this.location.setEntity(this);
		this.location.setTile(location);
	}

	public Tile getLocation() {
		return this.location;
	}

	public void setOwner(MobileEntity owner) {
		this.owner = owner;
	}

	public MobileEntity getOwner() {
		return owner;
	}

	public boolean multiZone() {
		return getAbsX() >= 3287 && getAbsX() <= 3298 && getAbsY() >= 3167
				&& getAbsY() <= 3178 || getAbsX() >= 3070 && getAbsX() <= 3095
				&& getAbsY() >= 3405 && getAbsY() <= 3448 || getAbsX() >= 2961
				&& getAbsX() <= 2981 && getAbsY() >= 3330 && getAbsY() <= 3354
				|| getAbsX() >= 2510 && getAbsX() <= 2537 && getAbsY() >= 4632
				&& getAbsY() <= 4660 || getAbsX() >= 3012 && getAbsX() <= 3066
				&& getAbsY() >= 4805 && getAbsY() <= 4858 || getAbsX() >= 2794
				&& getAbsX() <= 2813 && getAbsY() >= 9281 && getAbsY() <= 9305
				|| getAbsX() >= 3546 && getAbsX() <= 3557 && getAbsY() >= 9689
				&& getAbsY() <= 9700 || getAbsX() >= 2708 && getAbsX() <= 2729
				&& getAbsY() >= 9801 && getAbsY() <= 9829 || getAbsX() >= 3450
				&& getAbsX() <= 3525 && getAbsY() >= 9470 && getAbsY() <= 9535
				|| getAbsX() >= 3207 && getAbsX() <= 3395 && getAbsY() >= 3904
				&& getAbsY() <= 3903 || getAbsX() >= 3006 && getAbsX() <= 3072
				&& getAbsY() >= 3611 && getAbsY() <= 3712 || getAbsX() >= 3149
				&& getAbsX() <= 3395 && getAbsY() >= 3520 && getAbsY() <= 4000
				|| getAbsX() >= 2365 && getAbsX() <= 2420 && getAbsY() >= 5065
				&& getAbsY() <= 5120 || getAbsX() >= 2890 && getAbsX() <= 2935
				&& getAbsY() >= 4425 && getAbsY() <= 4470 || getAbsX() >= 2250
				&& getAbsX() <= 2290 && getAbsY() >= 4675 && getAbsY() <= 4715
				|| getAbsX() >= 2690 && getAbsX() <= 2825 && getAbsY() >= 2680
				&& getAbsY() <= 2810 || getAbsX() >= 3191 && getAbsX() <= 3200
				&& getAbsY() >= 3428 && getAbsY() <= 3445 || getAbsX() >= 2897
				&& getAbsX() <= 2924 && getAbsY() >= 3598 && getAbsY() <= 3628
				|| getAbsX() >= 3207 && getAbsX() <= 3241 && getAbsY() >= 3359
				&& getAbsY() <= 3381 || getAbsX() >= 3226 && getAbsX() <= 3233
				&& getAbsY() >= 3471 && getAbsY() <= 3490;
	}

	public void setRetaliateDelay(int retaliateDelay) {
		this.retaliateDelay = retaliateDelay;
	}

	public int getRetaliateDelay() {
		return retaliateDelay;
	}

	public void setHasHitTarget(int slot, boolean val) {
		this.hasHitTarget[slot] = val;
	}

	public boolean[] getHasHitTarget() {
		return hasHitTarget;
	}

	public void setHasHit(boolean hasHit) {
		this.hasHit = hasHit;
	}

	public boolean hasHit() {
		return hasHit;
	}

	public void setMultiHitDelay(int multiHitDelay) {
		this.multiHitDelay = multiHitDelay;
	}

	public int getMultiHitDelay() {
		return multiHitDelay;
	}

	public boolean withinDistance(Player otherPlr) {
		if (getHeightLevel() != otherPlr.getHeightLevel()) {
			return false;
		}
		int deltaX = otherPlr.getAbsX() - getAbsX(), deltaY = otherPlr
				.getAbsY() - getAbsY();
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public boolean withinInteractionDistance(Client otherClient) {
		return withinInteractionDistance(otherClient.getAbsX(),
				otherClient.getAbsY(), otherClient.getHeightLevel());
	}

	public boolean withinInteractionDistance(int x, int y, int z) {
		if (getHeightLevel() != z)
			return false;
		int deltaX = x - getAbsX(), deltaY = y - getAbsY();
		return deltaX <= 2 && deltaX >= -2 && deltaY <= 2 && deltaY >= -2;
	}

	public boolean withinDistance(NPC npc) {
		if (getHeightLevel() != npc.getHeightLevel())
			return false;
		int deltaX = npc.getAbsX() - getAbsX(), deltaY = npc.getAbsY()
				- getAbsY();
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}
}