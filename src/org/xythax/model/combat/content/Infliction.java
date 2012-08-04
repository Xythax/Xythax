package org.xythax.model.combat.content;

import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.Entity.CombatType;
import org.xythax.model.NPC;
import org.xythax.model.NPCAttacks;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;

/**
 * 
 * @author killamess
 * 
 */
public class Infliction {

	public static double ATTACK_LEVEL;
	public static double ATTACK_BONUS;

	public static double DEFENSE_LEVEL;
	public static double DEFENSE_BONUS;

	public static boolean canPierce(Entity attacker, Entity victim) {

		if (victim instanceof NPC) {
			DEFENSE_BONUS = ((NPC) victim).getDefinition().getCombat() / 3;
			if (attacker.getCombatType() == CombatType.RANGE) {
				DEFENSE_LEVEL = NPCAttacks.npcArray[((NPC) victim)
						.getDefinition().getType()][7];
			} else {
				DEFENSE_LEVEL = NPCAttacks.npcArray[((NPC) victim)
						.getDefinition().getType()][5];
			}
		} else if (victim instanceof Client) {
			DEFENSE_LEVEL = ((Client) victim).playerLevel[Constants.DEFENCE];
			if (attacker.getCombatType() == CombatType.RANGE) {
				DEFENSE_BONUS = ((Client) victim).getBonuses().bonus[9];
			} else {
				DEFENSE_BONUS = (((Client) victim).getBonuses().bonus[bestDefenceBonus((Client) victim)]);
			}
		}
		if (attacker instanceof Client) {
			if (attacker.getCombatType() == CombatType.RANGE) {
				ATTACK_LEVEL = ((Client) attacker).playerLevel[Constants.RANGE];
				ATTACK_BONUS = ((Client) attacker).getBonuses().bonus[4];
			} else {
				ATTACK_LEVEL = ((Client) attacker).playerLevel[Constants.ATTACK];
				ATTACK_BONUS = ((Client) attacker).getBonuses().bonus[bestAttackBonus((Client) attacker)];
			}
		} else if (attacker instanceof NPC) {
			if (attacker.getCombatType() == CombatType.RANGE) {
				ATTACK_LEVEL = NPCAttacks.npcArray[((NPC) attacker)
						.getDefinition().getType()][4];
			} else {
				ATTACK_LEVEL = NPCAttacks.npcArray[((NPC) attacker)
						.getDefinition().getType()][2];
			}
			ATTACK_BONUS = ATTACK_LEVEL / 3;
		}
		double a = ATTACK_LEVEL + ATTACK_BONUS;
		double b = (DEFENSE_LEVEL * 1.503) + DEFENSE_BONUS;

		if (attacker instanceof Client)
			a *= getPrayerAttack(((Client) attacker));
		if (victim instanceof Client)
			b *= getPrayerDefence(((Client) victim));

		if (attacker instanceof Client) {
			a = a * 2;

		}

		return Utils.random((int) a) > Utils.random((int) b);
	}

	public static double getPrayerAttack(Client client) {
		if (client.getPrayerHandler().clicked[2])
			return 1.05;
		else if (client.getPrayerHandler().clicked[5])
			return 1.1;
		else if (client.getPrayerHandler().clicked[11])
			return 1.15;
		else if (client.getPrayerHandler().clicked[18])
			return 1.15;
		else if (client.getPrayerHandler().clicked[19])
			return 1.20;
		return 1;
	}

	public static double getPrayerDefence(Client client) {
		if (client.getPrayerHandler().clicked[0])
			return 1.05;
		else if (client.getPrayerHandler().clicked[3])
			return 1.1;
		else if (client.getPrayerHandler().clicked[9])
			return 1.15;
		else if (client.getPrayerHandler().clicked[18])
			return 1.20;
		else if (client.getPrayerHandler().clicked[19])
			return 1.25;
		return 1;
	}

	public static int bestAttackBonus(Client client) {
		if (client.getBonuses().bonus[0] > client.getBonuses().bonus[1]
				&& client.getBonuses().bonus[0] > client.getBonuses().bonus[2])
			return 0;
		if (client.getBonuses().bonus[1] > client.getBonuses().bonus[0]
				&& client.getBonuses().bonus[1] > client.getBonuses().bonus[2])
			return 1;
		else
			return client.getBonuses().bonus[2] > client.getBonuses().bonus[1]
					&& client.getBonuses().bonus[2] > client.getBonuses().bonus[0] ? 2
					: 0;
	}

	public static int bestDefenceBonus(Client client) {
		if (client.getBonuses().bonus[5] > client.getBonuses().bonus[6]
				&& client.getBonuses().bonus[5] > client.getBonuses().bonus[7])
			return 5;
		if (client.getBonuses().bonus[6] > client.getBonuses().bonus[5]
				&& client.getBonuses().bonus[6] > client.getBonuses().bonus[7])
			return 6;
		else
			return client.getBonuses().bonus[7] > client.getBonuses().bonus[5]
					&& client.getBonuses().bonus[7] > client.getBonuses().bonus[6] ? 7
					: 5;
	}
}
