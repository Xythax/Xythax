package org.xythax.model.combat.magic;

import org.xythax.model.Client;
import org.xythax.model.Entity;

/**
 * 
 * @author killamess Some spell effects, still yet to complete.
 * 
 */
public class Effects {

	public static void doFreezeEffect(Entity entity, Entity victim, int spellId) {

		int freezeDelay = Magic.spell(spellId).getFreezeDelay();

		if (victim.getFreezeDelay() == 0) {

			victim.setFreezeDelay(freezeDelay * 2);

			if (victim instanceof Client) {
				((Client) victim).resetWalkingQueue();
				((Client) victim).getActionSender().sendMessage(
						"You have been frozen!");
			}
		}
	}

	public static void doDrainEffect(Entity entity, Entity victim, int spellId,
			int damage) {

		int drainRate = 0;

		int[][] drainEffects = { { 12901, 5 }, { 12919, 4 }, { 12911, 3 },
				{ 12929, 2 } };

		for (int i = 0; i < drainEffects.length; i++) {
			if (spellId == drainEffects[i][0])
				drainRate = drainEffects[i][1];
		}

		int amountToIncrease = damage / drainRate;

		if (amountToIncrease > 0) {

			if (entity instanceof Client) {
				if (((Client) entity)
						.getLevelForXP(((Client) entity).playerXP[3]) <= ((Client) entity).hitpoints
						+ amountToIncrease) {
					((Client) entity).getActionAssistant().addHP(
							amountToIncrease);
				} else {
					((Client) entity).hitpoints = ((Client) entity)
							.getLevelForXP(((Client) entity).playerXP[3]);
				}
				if (((Client) entity)
						.getLevelForXP(((Client) entity).playerXP[3]) < ((Client) entity).hitpoints) {
					((Client) entity).getActionSender().sendMessage(
							"You drain some of your opponents life.");
				}
			}
			if (victim instanceof Client) {
				((Client) victim).getActionSender().sendMessage(
						"Some of your life has been drained!");
				((Client) victim).getActionAssistant().refreshSkill(3);
			}
		}
	}
}