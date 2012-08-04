package org.xythax.content.controllers;

import java.util.ArrayList;

import org.xythax.model.Entity;

/**
 * 
 * @author Killamess
 * 
 */

public class Graphics extends ControllerManager {

	private int gfxId;
	private int gfxHeight;
	private int gfxDelay;
	private Entity victim;

	public Graphics(Entity victim, int gfxId, int gfxH, int gfxD) {

		if (victim == null)
			return;

		setVictim(victim);
		setGfxId(gfxId);
		setGfxHeight(gfxH);
		setGfxDelay(gfxD);
	}

	public static void addNewRequest(Entity victim, int gfxId, int gfxHeight,
			int gfxDelay) {
		gfx.add(new Graphics(victim, gfxId, gfxHeight, gfxDelay));
	}

	public static void process() {

		if (gfx.isEmpty())
			return;

		ArrayList<Graphics> newList = new ArrayList<Graphics>(gfx);

		for (Graphics task : newList) {
			if (task == null || task.getVictim() == null)
				continue;

			if (task.getGfxDelay() > 0)
				task.deductGfxDelay();

			if (task.getGfxDelay() == 0) {
				Animation.createGraphic(task.getVictim(), task.getGfxId(), 0,
						task.getGfxHeight() > 0);
				gfx.remove(task);
			}
		}
		newList.clear();
	}

	/**
	 * @param gfxId
	 *            the gfxId to set
	 */
	public void setGfxId(int gfxId) {
		this.gfxId = gfxId;
	}

	/**
	 * @return the gfxId
	 */
	public int getGfxId() {
		return gfxId;
	}

	/**
	 * @param gfxHeight
	 *            the gfxHeight to set
	 */
	public void setGfxHeight(int gfxHeight) {
		this.gfxHeight = gfxHeight;
	}

	/**
	 * @return the gfxHeight
	 */
	public int getGfxHeight() {
		return gfxHeight;
	}

	/**
	 * @param gfxDelay
	 *            the gfxDelay to set
	 */
	public void setGfxDelay(int gfxDelay) {
		this.gfxDelay = gfxDelay;
	}

	public void deductGfxDelay() {
		this.gfxDelay--;
	}

	/**
	 * @return the gfxDelay
	 */
	public int getGfxDelay() {
		return gfxDelay;
	}

	/**
	 * @param victim
	 *            the victim to set
	 */
	public void setVictim(Entity victim) {
		this.victim = victim;
	}

	/**
	 * @return the victim
	 */
	public Entity getVictim() {
		return victim;
	}

}
