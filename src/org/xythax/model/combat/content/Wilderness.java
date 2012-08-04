package org.xythax.model.combat.content;

import org.xythax.core.Server;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.combat.CombatEngine;
import org.xythax.utils.CommonStrings;

/**
 * 
 * @author Killamess Wilderness levels and attack options.
 * 
 */
public class Wilderness {

	public static void wildernessEvent(Entity entity) {
		if (!(entity instanceof Client)) {
			return;
		}
		Client client = (Client) entity;

		if (client.multiZone() || Server.multiValve == false) {
			client.getActionSender().sendMultiInterface(1);
		} else {
			client.getActionSender().sendMultiInterface(0);
		}
		if (!client.wildSignWarning && client.getAbsY() >= 3516
				&& client.getAbsY() < 3519) {
			client.getActionSender().sendInterface(1908);
			CombatEngine.resetAttack(client, false);
			client.resetWalkingQueue();
			client.wildSignWarning = true;
			client.getActionSender().sendFlagRemoval();
		}
		if (client.getAbsY() >= 3520 && client.getAbsY() <= 3980) {
			client.setWildernessLevel((client.getAbsY() - 3520) / 8 + 1);
			client.wildSignWarning = true;
		} else {
			client.setWildernessLevel(-1);
		}
		if (client.getWildernessLevel() != -1) {
			client.outStream.createFrame(208);
			client.outStream.writeWordBigEndian_dup(197);
			client.getActionSender().sendQuest(
					"Level: " + client.getWildernessLevel(), 199);
			client.getActionSender().sendOption("Attack", 1);
			client.flushOutStream();
		} else {
			client.outStream.createFrame(208);
			client.outStream.writeWordBigEndian_dup(-1);
			client.getActionSender().sendOption("null", 1);
			client.flushOutStream();
			client.setWildernessLevel(-1);
		}
	}

	public static boolean checkPlayers(Entity attacker, Entity victim) {

		if (attacker instanceof Client && victim instanceof Client) {

			int lvldiff = Math.abs(((Client) attacker).getCombatLevel()
					- ((Client) victim).getCombatLevel());

			if (((Client) attacker).getWildernessLevel() == -1) {
				((Client) attacker).getActionSender().sendMessage(
						CommonStrings.NOT_IN_WILDY);
				return false;
			}
			if (((Client) victim).getWildernessLevel() == -1) {
				((Client) attacker).getActionSender().sendMessage(
						CommonStrings.NOT_IN_WILDY_OTHER);
				return false;
			}
			if (lvldiff > ((Client) attacker).getWildernessLevel()) {
				((Client) attacker).getActionSender().sendMessage(
						CommonStrings.WILDY_DIFFENCE);
				return false;
			}
		}
		return true;
	}

}
