package org.xythax.content.controllers;

import java.util.ArrayList;

import org.xythax.content.JailSystem;
import org.xythax.content.actions.ActionManager;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.NPC;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.combat.magic.Magic;
import org.xythax.utils.CommonStrings;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;

/**
 * 
 * @author killamess
 * 
 */
public class Location extends ControllerManager {

	private Entity victim = null;
	private int[] location2 = new int[3]; // x-y-z
	private int delay = 0;

	public Location(Entity entity, int x, int y, int z, int delay) {
		setVictim(entity);
		location2[0] = x;
		location2[1] = y;
		location2[2] = z;
		setDelay(delay);
	}

	public static void addNewRequest(Entity victim, int x, int y, int z,
			int delay) {
		location.add(new Location(victim, x, y, z, delay));
	}

	public static void process() {

		if (location.isEmpty())
			return;

		ArrayList<Location> newList = new ArrayList<Location>(location);

		for (Location task : newList) {

			if (task == null || task.getVictim() == null)
				continue;

			if (task.getDelay() > 0)
				task.deductDelay();

			if (task.getDelay() == 0) {

				if (task.getVictim() instanceof Client) {

					Client client = (Client) task.getVictim();
					ActionManager.destructActions(client.getUsername());
					client.getActionSender().sendWindowsRemoval();
					client.teleportToX = task.getLocation(0);
					client.teleportToY = task.getLocation(1);
					client.teleportToZ = task.getLocation(2);

					client.getActionSender().sendWindowsRemoval();

					if (Magic.playersProcessing.contains(client))
						Magic.playersProcessing.remove(client);

					location.remove(task);

				} else if (task.getVictim() instanceof NPC) {

					NPC npc = (NPC) task.getVictim();
					npc.setAbsX(task.getLocation(0));
					npc.setAbsY(task.getLocation(1));
					npc.setHeightLevel(task.getLocation(2));
					location.remove(task);
				}
			}
		}
		newList.clear();
	}

	public static void teleport(Entity entity, int id, String teleType) {

		if (Magic.teleport(id) == null || entity.isBusy())
			return;

		if (entity instanceof Client) {

			CombatEngine.resetAttack(entity, false);

			Client client = (Client) entity;

			if (Magic.inQueue(client) || JailSystem.inJail(client)) {
				return;
			}

			ActionManager.destructActions(client.getUsername());

			if (teleType == "magicbook") {
				if (client.playerLevel[Constants.MAGIC] < Magic.teleport(id)
						.getLevel()) {
					client.getActionSender().sendMessage(
							"You need a magic level of "
									+ Magic.teleport(id).getLevel()
									+ " to cast this spell.");
					return;
				}
				if (client.getWildernessLevel() >= 20) {
					client.getActionSender().sendMessage(
							"You can't teleport above level " + 20
									+ " wilderness.");
					return;
				}
				int[] runes = Magic.teleport(id).getRunes();
				int[] amount = Magic.teleport(id).getAmounts();

				for (int i = 0; i < 4; i++) {
					if (runes[i] > 0) {
						if (!client.getActionAssistant().playerHasItem(
								runes[i], amount[i])
								&& !client.getActionAssistant().staffType(
										runes[i])) {
							client.getActionSender().sendMessage(
									CommonStrings.NO_RUNES);
							return;
						}
					}
				}
				for (int i = 0; i < 4; i++) {
					if (runes[i] > 0) {
						if (!client.getActionAssistant().staffType(runes[i]))
							client.getActionAssistant().deleteItem(runes[i],
									amount[i]);
					}
				}
				Magic.playersProcessing.add(client);

				client.getActionAssistant().addSkillXP(
						Magic.teleport(id).getXp()
								* Constants.COMBAT_EXPERIENCE_MULTIPLIER,
						Constants.MAGIC);
				client.resetFaceDirection();
				client.resetWalkingQueue();
				entity.setBusyTimer(id > 7 ? 3 : 4);
				int s = client.getSpellBook();

				Graphics.addNewRequest(entity, s > 1 ? 392 : 308, s > 1 ? 0
						: 100, s > 1 ? 1 : 3);
				Animation.addNewRequest(entity, s > 1 ? 1979 : 714, 0);

				if (s <= 1)
					Animation.addNewRequest(entity, 715, 5);

				Location.addNewRequest(entity, Magic.teleport(id).getAbsX()
						+ Utils.random(2) - 1, Magic.teleport(id).getAbsY()
						+ Utils.random(2) - 1, Magic.teleport(id).getHeight(),
						s > 1 ? 5 : 4);

			} else if (teleType == "tablet") {

				if (client.getWildernessLevel() > 20) {
					client.getActionSender().sendMessage(
							"You can't teleport above level " + 20
									+ " wilderness.");
					return;
				}
				client.resetFaceDirection();
				client.resetWalkingQueue();
				entity.setBusyTimer(2);
				Graphics.addNewRequest(entity, 678, 0, 2);
				Animation.addNewRequest(entity, 4731, 2);
				Location.addNewRequest(entity, Magic.teleport(id).getAbsX(),
						Magic.teleport(id).getAbsY(), 0, 5);

			}
		}
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

	public int getLocation(int slot) {
		return location2[slot];
	}

	/**
	 * @param delay
	 *            the delay to set
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	/**
	 * @return the delay
	 */
	public int getDelay() {
		return delay;
	}

	public void deductDelay() {
		this.delay--;
	}
}
