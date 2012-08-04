package org.xythax.content.controllers;

import java.util.ArrayList;

import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.Equipment;
import org.xythax.model.NPC;

public class Animation extends ControllerManager {
	private int animationId;
	private int animationDelay;
	private Entity victim;

	public Animation(Entity victim, int animationId, int animationD) {
		if (victim == null)
			return;

		setVictim(victim);
		setAnimationId(animationId);
		setAnimationDelay(animationD);
	}

	public static void addNewRequest(Entity victim, int animationId,
			int animationDelay) {
		animate.add(new Animation(victim, animationId, animationDelay));
	}

	public static void process() {
		if (animate.isEmpty())
			return;

		ArrayList<Animation> newList = new ArrayList<Animation>(animate);

		for (Animation task : newList) {
			if (task == null || task.getVictim() == null)
				continue;

			if (task.getAnimationDelay() > 0)
				task.deductAnimationDelay();

			if (task.getAnimationDelay() == 0) {
				createAnimation(task.getVictim(), task.getAnimationId());
				animate.remove(task);
				if (task.getVictim() instanceof Client)
					((Client) task.getVictim()).getActionSender()
							.sendWindowsRemoval();
			}
		}
		newList.clear();
	}

	public static void resetFaceDirection(Entity ent) {
		if (ent instanceof Client)
			((Client) ent).getActionAssistant().turnPlayerTo(32768);
		else if (ent instanceof NPC)
			((NPC) ent).faceTo(0);
	}

	public static void createAnimation(Entity ent, int animation) {
		if (ent == null)
			return;
		if (ent instanceof Client)
			((Client) ent).getActionAssistant().startAni(animation);
		else if (ent instanceof NPC) {
			((NPC) ent).setAnimNumber(animation);
			((NPC) ent).setAnimUpdateRequired(true);
		}
	}

	public static void createGraphic(Entity ent, int gfx, int delay,
			boolean tallGfx) {
		if (ent == null || gfx < 1)
			return;

		if (ent instanceof Client)
			((Client) ent).getActionAssistant().createPlayerGfx(gfx, delay,
					tallGfx);
		else if (ent instanceof NPC)
			if (tallGfx)
				((NPC) ent).gfx100(gfx);
			else if (!tallGfx)
				((NPC) ent).gfx0(gfx);
	}

	public static void face(Entity attacker, Entity target) {
		if (attacker == null || target == null)
			return;
		if (attacker instanceof Client) {
			if (target instanceof Client) {
				((Client) attacker).getActionAssistant().turnPlayerTo(
						32768 + ((Client) target).getUserID());
			} else if (target instanceof NPC) {
				((Client) attacker).getActionAssistant().turnPlayerTo(
						((NPC) target).getNpcId());
			}
		} else if (attacker instanceof NPC) {
			if (target instanceof Client) {
				((NPC) attacker).faceTo(((Client) target).getUserID());
			} else if (target instanceof NPC) {
				((NPC) attacker).faceTo(((NPC) target).getNpcId());
			}
		}
	}

	public static void createAnimation(Entity attacker) {
		if (attacker == null)
			return;
		if (attacker instanceof Client)
			createAnimation(attacker,
					Equipment.getAttackEmote((Client) attacker));
		else if (attacker instanceof NPC)
			createAnimation(attacker, 422);
	}

	/**
	 * @param animationId
	 *            the animationId to set
	 */
	public void setAnimationId(int animationId) {
		this.animationId = animationId;
	}

	/**
	 * @return the animationId
	 */
	public int getAnimationId() {
		return animationId;
	}

	/**
	 * @param animationDelay
	 *            the animationDelay to set
	 */
	public void setAnimationDelay(int animationDelay) {
		this.animationDelay = animationDelay;
	}

	public void deductAnimationDelay() {
		this.animationDelay--;
	}

	/**
	 * @return the animationDelay
	 */
	public int getAnimationDelay() {
		return animationDelay;
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
