package org.xythax.model.combat.content;

import org.xythax.content.controllers.Animation;
import org.xythax.content.controllers.Damage;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.Entity.CombatType;
import org.xythax.model.Equipment;
import org.xythax.model.NPC;
import org.xythax.model.NPCAttacks;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.combat.ranged.Ranged;
import org.xythax.utils.CombatUtils;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;

/**
 * 
 * @author killamess
 * 
 */
public class Hits {

	public static boolean canHitMagic(Entity attacker, Entity victim,
			int spellId) {

		double magicLevel = 0, magicBonus = 0, defenceLevel = 0, magicDefenceBonus = 0;

		if (attacker instanceof Client) {
			magicLevel = ((Client) attacker).playerLevel[Constants.MAGIC];
			magicBonus = ((Client) attacker).getBonuses().bonus[3] + 1;

		} else if (attacker instanceof NPC) {
			NPC npc = (NPC) attacker;
			int type = npc.getDefinition().getType();
			if (NPCAttacks.npcArray[type][3] > 0)
				magicLevel = NPCAttacks.npcArray[type][3];
			else
				magicLevel = 1;
			magicBonus = 0.25 * magicLevel;
		}
		if (victim instanceof Client) {
			defenceLevel = ((Client) victim).playerLevel[Constants.DEFENCE];
			magicDefenceBonus = ((Client) victim).getBonuses().bonus[8] + 1;

		} else if (victim instanceof NPC) {
			NPC npc = (NPC) victim;
			int type = npc.getDefinition().getType();
			if (NPCAttacks.npcArray[type][5] > 0)
				defenceLevel = NPCAttacks.npcArray[type][5];
			else
				defenceLevel = 1;
			magicDefenceBonus = 0.25 * defenceLevel;
		}
		double force = magicLevel * magicBonus;
		double deflection = defenceLevel * magicDefenceBonus;

		if (Utils.random((int) deflection) > force / 2
				+ (Utils.random((int) force / 2)))
			return false;

		return true;
	}

	public static void runHitMelee(Entity attacker, Entity target,
			boolean special) {
		if (attacker == null || target == null)
			return;

		int firstHit = 0;
		int secondHit = 0;

		if (Infliction.canPierce(attacker, target)) {
			if (attacker instanceof Client) {
				if (Ranged.isUsingRange((Client) attacker)) {
					firstHit = Utils.random((int) CombatUtils
							.calculateRangeDamage((Client) attacker, target,
									special));
				} else {
					firstHit = Utils.random((int) CombatUtils
							.calculateBaseDamage((Client) attacker, special));
				}
			}
		}
		if (attacker instanceof Client) {
			if (target instanceof Client) {
				if (Ranged.isUsingRange(((Client) attacker))) {
					if (((Client) target).getPrayerHandler().clicked[13]) {
						firstHit = (int) (firstHit * 0.60);
					}
				} else {
					if (((Client) target).getPrayerHandler().clicked[14]) {
						firstHit = (int) (firstHit * 0.60);
					}
				}

				Damage.addNewHit(
						attacker,
						target,
						attacker.getCombatType(),
						firstHit,
						Ranged.isUsingRange((Client) attacker) ? Ranged
								.getHitDelay(attacker) : 1);
				if (((Client) attacker).getPrayerHandler().clicked[17])
					((Client) target).getPrayerHandler().updatePrayer(
							firstHit / 4);
				if (Specials.doubleHit((Client) attacker) && special) {
					if (Ranged.isUsingRange((Client) attacker)) {
						secondHit = Utils.random((int) CombatUtils
								.calculateRangeDamage((Client) attacker,
										target, special));
					} else {
						secondHit = Utils
								.random((int) CombatUtils.calculateBaseDamage(
										(Client) attacker, special));
					}

					if (Ranged.isUsingRange(((Client) attacker))) {
						if (((Client) target).getPrayerHandler().clicked[13]) {
							secondHit = (int) (secondHit * 0.60);
						}
					} else {
						if (((Client) target).getPrayerHandler().clicked[14]) {
							secondHit = (int) (secondHit * 0.60);
						}
					}
					if (!Infliction.canPierce(attacker, target))
						secondHit = 0;

					Damage.addNewHit(
							attacker,
							target,
							attacker.getCombatType(),
							secondHit,
							Ranged.isUsingRange((Client) attacker) ? Ranged
									.getHitDelay(attacker) : 1);

				}

				if (((Client) attacker).getPrayerHandler().clicked[17])
					((Client) target).getPrayerHandler().updatePrayer(
							secondHit / 4);

				if (special)
					Specials.specialAttack((Client) attacker);
				else
					Animation.createAnimation(attacker,
							Equipment.getAttackEmote(((Client) attacker)));

			} else if (target instanceof NPC) {

				Damage.addNewHit(
						attacker,
						target,
						attacker.getCombatType(),
						firstHit,
						Ranged.isUsingRange((Client) attacker) ? Ranged
								.getHitDelay(attacker) : 1);

				if (Specials.doubleHit((Client) attacker) && special) {
					if (Ranged.isUsingRange((Client) attacker)) {
						secondHit = Utils.random((int) CombatUtils
								.calculateRangeDamage((Client) attacker,
										target, special));
					} else {
						secondHit = Utils
								.random((int) CombatUtils.calculateBaseDamage(
										(Client) attacker, special));
					}
					if (!Infliction.canPierce(attacker, target))
						secondHit = 0;
					Damage.addNewHit(
							attacker,
							target,
							attacker.getCombatType(),
							secondHit,
							Ranged.isUsingRange((Client) attacker) ? Ranged
									.getHitDelay(attacker) : 1);
				}

				if (special)
					Specials.specialAttack((Client) attacker);
				// else
				// Animation.createAnimation(attacker,
				// Equipment.getAttackEmote(((Client)attacker)));
			}

		} else if (attacker instanceof NPC) {

			if (target instanceof Client) {

				NPC npc = (NPC) attacker;
				int type = npc.getDefinition().getType();
				int maxDamage = 0;

				if (NPCAttacks.npcArray[type][1] > 0)
					maxDamage = NPCAttacks.npcArray[type][1];
				else
					maxDamage = 1;

				if (CombatEngine.getCombatType(attacker) == CombatType.RANGE) {
					if (((Client) target).getPrayerHandler().clicked[13])
						maxDamage = 0;
				} else {
					if (((Client) target).getPrayerHandler().clicked[14])
						maxDamage = 0;
				}

				firstHit = Infliction.canPierce(attacker, target) ? (Utils
						.random(maxDamage) - 1) + 1 : 0;
				if (NPCAttacks.npcArray[type][0] == 2)
					Damage.addNewHit(attacker, target,
							attacker.getCombatType(), firstHit, 3);
				else
					Damage.addNewHit(attacker, target,
							attacker.getCombatType(), firstHit, 1);

				// if (((Client)target).getEquipment().wearingRecoil())
				// Rings.applyRecoil(target, attacker,
				// Rings.pressDetails(firstHit, secondHit), 0);

			} else if (target instanceof NPC) {
				Damage.addNewHit(attacker, target, attacker.getCombatType(),
						firstHit, 1);
			}
		}
		if (attacker instanceof Client) {

			Client client = (Client) attacker;

			switch (client.combatMode) {

			case 0:
			case 1:
			case 2:
				client.getActionAssistant().addSkillXP(
						firstHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER,
						client.combatMode);
				client.getActionAssistant()
						.addSkillXP(
								(firstHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 3,
								3);
				client.getActionAssistant().addSkillXP(
						secondHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER,
						client.combatMode);
				client.getActionAssistant()
						.addSkillXP(
								(secondHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 3,
								3);
				break;

			case 3:
				client.getActionAssistant().addSkillXP(
						firstHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER / 3,
						0);
				client.getActionAssistant().addSkillXP(
						firstHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER / 3,
						1);
				client.getActionAssistant().addSkillXP(
						firstHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER / 3,
						2);
				client.getActionAssistant()
						.addSkillXP(
								(firstHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 3,
								3);
				client.getActionAssistant().addSkillXP(
						secondHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER / 3,
						0);
				client.getActionAssistant().addSkillXP(
						secondHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER / 3,
						1);
				client.getActionAssistant().addSkillXP(
						secondHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER / 3,
						2);
				client.getActionAssistant()
						.addSkillXP(
								(secondHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 3,
								3);
				break;

			case 4:
			case 5:
				client.getActionAssistant().addSkillXP(
						firstHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER, 4);
				client.getActionAssistant()
						.addSkillXP(
								(firstHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 3,
								3);
				client.getActionAssistant().addSkillXP(
						secondHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER, 4);
				client.getActionAssistant()
						.addSkillXP(
								(secondHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 3,
								3);
				break;

			case 6:
				client.getActionAssistant().addSkillXP(
						firstHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER / 2,
						4);
				client.getActionAssistant().addSkillXP(
						firstHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER / 2,
						1);
				client.getActionAssistant()
						.addSkillXP(
								(firstHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 3,
								3);
				client.getActionAssistant().addSkillXP(
						secondHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER / 2,
						4);
				client.getActionAssistant().addSkillXP(
						secondHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER / 2,
						1);
				client.getActionAssistant()
						.addSkillXP(
								(secondHit * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 3,
								3);
				break;

			default:
				System.out.println("Client[" + client.getUsername()
						+ "] Invalid combatMode: " + client.combatMode
						+ " File: Hits.java.");
				break;
			}
		}
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
