package org.xythax.content;

import org.xythax.content.controllers.Damage;
import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.Entity.CombatType;
import org.xythax.model.combat.CombatEngine;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;

/**
 * Food, pots and prayer.
 * 
 * @author Ultimate & killamess
 */

public class UsingItemHandler {

	public static final int WAIT_DELAY = 1800;

	public static Action action = null;

	public enum Action {
		NOTHING, BURY_BONE, EAT_FOOD, DRINK_POTION
	}

	public static Action getActionFor(int id) {
		for (int food : FOODS) {
			if (id == food)
				return Action.EAT_FOOD;
		}
		for (int bone : BONES) {
			if (id == bone)
				return Action.BURY_BONE;
		}
		for (int pot : POTS) {
			if (id == pot)
				return Action.DRINK_POTION;
		}
		return Action.NOTHING;
	}

	public static final int[] FOODS = { 1971, 319, 315, 2140, 2142, 1901, 2309,
			347, 355, 333, 351, 329, 361, 379, 365, 373, 7946, 385, 397, 391 };
	public static final int[] BONES = { 526, 528, 530, 532, 534, 536 };
	public static final int[] POTS = { 2436, 145, 147, 149, 2440, 157, 159,
			161, 2442, 163, 165, 167, 2428, 121, 123, 125, 113, 115, 117, 119,
			2432, 133, 135, 137, 2444, 169, 171, 173, 3040, 3042, 3044, 3046,
			2446, 175, 177, 179, 2434, 139, 141, 143, 1978 };

	public static void useItem(Client client, int itemID, int itemSlot) {

		action = getActionFor(itemID);

		if (client.isDead() || action == Action.NOTHING || client.isBusy()
				|| client.getBusyTimer() > 0 || client.getStunnedTimer() > 0)
			return;

		/**
		 * Real rs style - killames
		 */
		if (action == Action.EAT_FOOD) {

			if (client.foodDelay > 0 || client.potionDelay > 0)
				return;
			else {
				client.foodDelay = 4;
				client.potionDelay = 0;
			}

		} else if (action == Action.DRINK_POTION) {

			if (client.potionDelay > 0)
				return;
			else {
				client.potionDelay = 3;
				client.foodDelay = 3;
			}
		}

		int healAmount = 0;
		int buryXP = 0;

		int newItem = 0;
		int skillToIncrease = 0;
		int percentageIncrease = 0;

		if (action == Action.EAT_FOOD) {
			switch (itemID) {

			case 319: // Anchovies
				healAmount = 1;
				break;
			case 315: // Shrimps
				healAmount = 3;
				break;
			case 2140: // Cooked Chicken
				healAmount = 3;
				break;
			case 2142: // Cooked meat
				healAmount = 3;
				break;
			case 2309: // Bread
				healAmount = 3;
				break;
			case 1891: // Cake slice
				healAmount = 3;
				break;
			case 1901: // Choc slice
				healAmount = 4;
				break;
			case 347: // Herring
				healAmount = 6;
				break;
			case 333: // Trout
				healAmount = 7;
				break;
			case 351: // Pike
				healAmount = 8;
				break;
			case 329: // Salmon
				healAmount = 9;
				break;
			case 361: // Tuna
				healAmount = 10;
				break;
			case 379: // Lobster
				healAmount = 12;
				break;
			case 365: // Bass
				healAmount = 13;
				break;
			case 373: // Swordfish
				healAmount = 14;
				break;
			case 1971: // kebab

				int randomNumber = Utils.random(2);

				switch (randomNumber) {

				case 0:
					if (Utils.random(15) == 7) {
						client.getActionSender().sendMessage(
								"That was an excellent Kabab!");
						healAmount = 7 + Utils.random(5);
						break;
					} else {
						client.getActionSender().sendMessage(
								"You eat the kebab.");
						healAmount = 2;
						break;
					}

				case 1:
					if (Utils.random(10) == 6) {
						client.getActionSender().sendMessage(
								"That was one yummy Kabab!");
						healAmount = 3 + Utils.random(2);
						break;
					} else {
						client.getActionSender().sendMessage(
								"You eat the kebab.");
						healAmount = 2;
						break;
					}
				case 2:
					if (Utils.random(1) == 1) {
						client.getActionSender().sendMessage(
								"You eat the kebab, it makes your feel sick.");
						healAmount = 0;
						Damage.addNewHit(client, client, CombatType.POISON,
								2 + Utils.random(1) == 0 ? 2 : 4, 4);
						break;
					} else {
						client.getActionSender().sendMessage(
								"You eat the kebab.");
						healAmount = 2;
						break;
					}
				}
				break;
			}

			if (healAmount < 0)
				return;

			client.getActionAssistant().addHP(healAmount);
			client.getActionAssistant().deleteItem(itemID, itemSlot, 1);
			client.getActionAssistant().startAnimation(829, 0);
			((Entity) client)
					.setCombatDelay(((Entity) client).getCombatDelay() + 3);
			CombatEngine.resetAttack(client, false);
			if (itemID != 1971)
				client.getActionSender().sendMessage(
						"You eat the "
								+ GameEngine.getItemManager()
										.getItemDefinition(itemID).getName()
										.toLowerCase() + ".");

		} else if (action == Action.BURY_BONE) {

			if (client.foodDelay > 0 || client.potionDelay > 0)
				return;
			else {
				client.foodDelay = 3;
			}
			switch (itemID) {
			/**
			 * killamess
			 */
			case 526:
			case 528:
			case 2859:
				buryXP = (int) 4.5;
				break;

			case 3179:
			case 3180:
			case 3183:
			case 3185:
			case 3186:
				buryXP = 5;
				break;

			case 530:
				buryXP = (int) 5.3;
				break;

			case 532:
				buryXP = 15;
				break;

			case 3181:
			case 3182:
				buryXP = 18;
				break;

			case 3123:
				buryXP = 25;
				break;

			case 534:
				buryXP = 30;
				break;

			case 6812:
				buryXP = 50;
				break;

			case 536:
				buryXP = 72;
				break;

			case 4830:
				buryXP = 84;
				break;

			case 4832:
				buryXP = 96;
				break;

			case 6729:
				buryXP = 125;
				break;

			case 4834:
				buryXP = 140;
				break;
			}
			CombatEngine.resetAttack(client, false);
			client.getActionAssistant().deleteItem(itemID, itemSlot, 1);
			client.getActionAssistant().startAnimation(827, 0);
			client.getActionSender().sendMessage(
					"You bury the "
							+ GameEngine.getItemManager()
									.getItemDefinition(itemID).getName()
									.toLowerCase() + ".");
			client.getActionAssistant().addSkillXP(
					buryXP * Constants.COMBAT_EXPERIENCE_MULTIPLIER,
					Constants.PRAYER);
		} else if (action == Action.DRINK_POTION) {
			switch (itemID) {
			case 1978:
				newItem = 1980;
				client.getActionAssistant().addHP(2);
				client.getActionSender().sendMessage(
						"Ahhhhhh... nothing like a nice cup of tea.");
				break;

			case 2428:
				newItem = 121;
				skillToIncrease = Constants.ATTACK;
				percentageIncrease = 10;
				break;

			case 121:
				newItem = 123;
				skillToIncrease = Constants.ATTACK;
				percentageIncrease = 10;
				break;

			case 123:
				newItem = 125;
				skillToIncrease = Constants.ATTACK;
				percentageIncrease = 10;
				break;

			case 125:
				newItem = 229;
				skillToIncrease = Constants.ATTACK;
				percentageIncrease = 10;
				break;

			// Strength potion
			case 113:
				newItem = 115;
				skillToIncrease = Constants.STRENGTH;
				percentageIncrease = 10;
				break;

			case 115:
				newItem = 117;
				skillToIncrease = Constants.STRENGTH;
				percentageIncrease = 10;
				break;

			case 117:
				newItem = 119;
				skillToIncrease = Constants.STRENGTH;
				percentageIncrease = 10;
				break;

			case 119:
				newItem = 229;
				skillToIncrease = Constants.STRENGTH;
				percentageIncrease = 10;
				break;

			// Defence potion
			case 2432:
				newItem = 133;
				skillToIncrease = Constants.DEFENCE;
				percentageIncrease = 10;
				break;

			case 133:
				newItem = 135;
				skillToIncrease = Constants.DEFENCE;
				percentageIncrease = 10;
				break;

			case 135:
				newItem = 137;
				skillToIncrease = Constants.DEFENCE;
				percentageIncrease = 10;
				break;

			case 137:
				newItem = 229;
				skillToIncrease = Constants.DEFENCE;
				percentageIncrease = 10;
				break;

			// Super attack
			case 2436:
				newItem = 145;
				skillToIncrease = Constants.ATTACK;
				percentageIncrease = 20;
				break;

			case 145:
				newItem = 147;
				skillToIncrease = Constants.ATTACK;
				percentageIncrease = 20;
				break;

			case 147:
				newItem = 149;
				skillToIncrease = Constants.ATTACK;
				percentageIncrease = 20;
				break;

			case 149:
				newItem = 229;
				skillToIncrease = Constants.ATTACK;
				percentageIncrease = 20;
				break;

			// Super strength
			case 2440:
				newItem = 157;
				skillToIncrease = Constants.STRENGTH;
				percentageIncrease = 20;
				break;

			case 157:
				newItem = 159;
				skillToIncrease = Constants.STRENGTH;
				percentageIncrease = 20;
				break;

			case 159:
				newItem = 161;
				skillToIncrease = Constants.STRENGTH;
				percentageIncrease = 20;
				break;

			case 161:
				newItem = 229;
				skillToIncrease = Constants.STRENGTH;
				percentageIncrease = 20;
				break;

			// Super defence
			case 2442:
				newItem = 163;
				skillToIncrease = Constants.DEFENCE;
				percentageIncrease = 20;
				break;

			case 163:
				newItem = 165;
				skillToIncrease = Constants.DEFENCE;
				percentageIncrease = 20;
				break;

			case 165:
				newItem = 167;
				skillToIncrease = Constants.DEFENCE;
				percentageIncrease = 20;
				break;

			case 167:
				newItem = 229;
				skillToIncrease = Constants.DEFENCE;
				percentageIncrease = 20;
				break;

			// Raning potion

			case 2444:
				newItem = 169;
				skillToIncrease = Constants.RANGE;
				percentageIncrease = 10;
				break;

			case 169:
				newItem = 171;
				skillToIncrease = Constants.RANGE;
				percentageIncrease = 10;
				break;

			case 171:
				newItem = 173;
				skillToIncrease = Constants.RANGE;
				percentageIncrease = 10;
				break;

			case 173:
				newItem = 229;
				skillToIncrease = Constants.RANGE;
				percentageIncrease = 10;
				break;

			// Magic potion

			case 3040:
				newItem = 3042;
				skillToIncrease = Constants.MAGIC;
				percentageIncrease = 10;
				break;

			case 3042:
				newItem = 3044;
				skillToIncrease = Constants.MAGIC;
				percentageIncrease = 10;
				break;

			case 3044:
				newItem = 173;
				skillToIncrease = Constants.MAGIC;
				percentageIncrease = 10;
				break;

			case 3046:
				newItem = 220;
				skillToIncrease = Constants.MAGIC;
				percentageIncrease = 10;
				break;

			// anti potion

			case 2446:
				newItem = 175;
				client.poisonDelay = 0;
				client.poisonDamage = 0;
				break;

			case 175:
				newItem = 177;
				client.poisonDelay = 0;
				client.poisonDamage = 0;
				break;

			case 177:
				newItem = 179;
				client.poisonDelay = 0;
				client.poisonDamage = 0;
				break;

			case 179:
				newItem = 229;
				client.poisonDelay = 0;
				client.poisonDamage = 0;
				break;

			// Prayer potion

			case 2434:
				newItem = 139;
				skillToIncrease = Constants.PRAYER;
				percentageIncrease = 33;
				break;

			case 139:
				newItem = 141;
				skillToIncrease = Constants.PRAYER;
				percentageIncrease = 33;
				break;

			case 141:
				newItem = 143;
				skillToIncrease = Constants.PRAYER;
				percentageIncrease = 33;
				break;

			case 143:
				newItem = 229;
				skillToIncrease = Constants.PRAYER;
				percentageIncrease = 33;
				break;

			}
			CombatEngine.resetAttack(client, false);
			client.getActionAssistant().increaseStat(skillToIncrease,
					percentageIncrease);
			client.getActionAssistant().deleteItem(itemID, itemSlot, 1);
			if (newItem > 0)
				client.getActionSender()
						.sendInventoryItem(newItem, 1, itemSlot);
			client.getActionAssistant().startAnimation(829, 0);
			if (newItem != 1980)
				client.getActionSender().sendMessage(
						"You drink some of the "
								+ GameEngine.getItemManager()
										.getItemDefinition(itemID).getName()
										.toLowerCase() + ".");
		}
	}
}
