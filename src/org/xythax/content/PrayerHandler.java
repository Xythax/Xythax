package org.xythax.content;

import org.xythax.model.Client;
import org.xythax.utils.Constants;

/**
 * @author Ultimate
 * @author killamess
 */

public class PrayerHandler {
	private Client client;

	public PrayerHandler(Client client) {
		this.client = client;
	}

	public static final String[] PRAYER_NAMES = { "Thick Skin",
			"Burst of Strength", "Clarity of Thought", "Rock Skin",
			"Superhuman Strength", "Improved Reflexes", "Rapid Restore",
			"Rapid Heal", "Protect Item", "Steel Skin", "Ultimate Strength",
			"Incredible Reflexes", "Protect from Magic",
			"Protect from Missiles", "Protect from Melee", "Retribution",
			"Redemption", "Smite", "Chivarly", "Piety", "Sharp Eye",
			"Hawk Eye", "Eagle Eye", "Mystic Will", "Mystic Lore",
			"Mystic Might" };

	public static final int THICK_SKIN = 0, BURST_OF_STRENGTH = 1,
			CLARITY_OF_THOUGHT = 2, ROCK_SKIN = 3, SUPERHUMAN_STRENGTH = 4,
			IMPROVED_REFLEXES = 5, RAPID_RESTORE = 6, RAPID_HEAL = 7,
			PROTECT_ITEM = 8, STEEL_SKIN = 9, ULTIMATE_STRENGTH = 10,
			INCREDIBLE_REFLEXES = 11, PROTECT_FROM_MAGIC = 12,
			PROTECT_FROM_MISSILES = 13, PROTECT_FROM_MELEE = 14,
			RETRIBUTION = 15, REDEMPTION = 16, SMITE = 17, CHIVALRY = 18,
			PIETY = 19, SHARP_EYE = 20, HAWK_EYE = 21, EAGLE_EYE = 22,
			MYSTIC_WILL = 23, MYSTIC_LORE = 24, MYSTIC_MIGHT = 25;

	public static final int[][] PRAYER_CONFIGS = { { 0, 1, 0, 0 },
			{ 1, 4, 0, 0 }, { 2, 7, 0, 0 }, { 3, 10, 0, 0 }, { 4, 13, 0, 0 },
			{ 5, 16, 0, 0 }, { 6, 19, 0, 0 }, { 7, 22, 0, 0 }, { 8, 25, 0, 0 },
			{ 9, 28, 0, 0 }, { 10, 31, 0, 0 }, { 11, 34, 0, 0 },
			{ 12, 37, 4, 0 }, { 13, 40, 2, 0 }, { 14, 43, 1, 0 },
			{ 15, 46, 8, 0 }, { 16, 49, 32, 0 }, { 17, 52, 16, 0 },
			{ 18, 60, 0, 65 }, { 19, 70, 0, 70 }, { 20, 8, 0, 0 },
			{ 21, 26, 0, 0 }, { 22, 44, 0, 0 }, { 23, 9, 0, 0 },
			{ 24, 27, 0, 0 }, { 25, 45, 0, 0 }, };

	public static final int[] GLOW_IDS = { 83, 84, 85, 86, 87, 88, 89, 90, 91,
			92, 93, 94, 95, 96, 97, 98, 99, 100, 706, 707, 700, 702, 704, 701,
			703, 705 };
	public boolean[] clicked = new boolean[26];

	public int headIconPrayer = 0;
	public int attackPrayer = 0;
	public int defencePrayer = 0;
	public int strengthPrayer = 0;

	public void updatePrayer(int amount) {

		if (client.isDead())
			return;
		client.getActionAssistant().decreaseStat(Constants.PRAYER, amount);
		client.getActionSender().sendQuest(
				"" + client.playerLevel[Constants.PRAYER] + "", 4012);
	}

	public void setPrayerIcon(int i) {
		client.headIcon = i;
		client.updateRequired = true;
		client.appearanceUpdateRequired = true;
	}

	public void resetAllPrayers() {
		for (int i = 0; i < clicked.length; i++) {
			clicked[i] = false;
			client.getActionSender().sendFrame36(GLOW_IDS[i], 0);
		}
		headIconPrayer = 0;
		attackPrayer = 0;
		defencePrayer = 0;
		strengthPrayer = 0;
		setPrayerIcon(-1);
	}

	public int getActivePrayers() {
		int active = 0;
		for (boolean c : clicked) {
			if (c == true)
				active++;
		}
		return active;
	}

	public void prayerEvent() {

		if (client.playerLevel[Constants.PRAYER] <= 0 && getActivePrayers() > 0) {
			client.getActionSender().sendMessage(
					"You do not have enough prayer points.");
			resetAllPrayers();
			return;
		}
		updatePrayer(getActivePrayers());
		if (client.playerLevel[Constants.PRAYER] <= 0 && getActivePrayers() > 0) {
			client.getActionSender().sendMessage(
					"You do not have enough prayer points.");
			resetAllPrayers();
			return;
		}
	}

	public void activatePrayer(int prayerId) {
		if (client.getLevelForXP(client.playerXP[Constants.PRAYER]) < PRAYER_CONFIGS[prayerId][1]) {
			client.getActionSender().sendFrame36(GLOW_IDS[prayerId], 0);
			if (client.getLevelForXP(client.playerXP[Constants.DEFENCE]) < PRAYER_CONFIGS[prayerId][3]) {
				client.getActionSender().sendQuest("", 369);
				client.getActionSender().sendQuest(
						"You need a @blu@Prayer level of "
								+ PRAYER_CONFIGS[prayerId][1]
								+ "@bla@ to use @blu@" + PRAYER_NAMES[prayerId]
								+ ".", 370);
				client.getActionSender().sendQuest(
						"You also need a @blu@Defence Level of "
								+ PRAYER_CONFIGS[prayerId][3] + ".", 371);
				client.getActionSender().sendQuest("", 372);
				client.getActionSender().sendQuest("Click here to continue",
						373);
				client.getActionSender().sendFrame164(368);
			} else {
				client.getActionSender().sendQuest(
						"You need a @blu@Prayer level of "
								+ PRAYER_CONFIGS[prayerId][1]
								+ "@bla@ to use @blu@" + PRAYER_NAMES[prayerId]
								+ ".", 357);
				client.getActionSender().sendQuest("Click here to continue",
						358);
				client.getActionSender().sendFrame164(356);
			}
			return;

		} else if (client.getLevelForXP(client.playerXP[Constants.DEFENCE]) < PRAYER_CONFIGS[prayerId][3]) {
			client.getActionSender().sendFrame36(GLOW_IDS[prayerId], 0);
			client.getActionSender().sendQuest(
					"You need a @blu@Defence Level of "
							+ PRAYER_CONFIGS[prayerId][3]
							+ "@bla@ to use @blu@" + PRAYER_NAMES[prayerId]
							+ ".", 357);
			client.getActionSender().sendQuest("Click here to continue", 358);
			client.getActionSender().sendFrame164(356);
			return;

		} else if (client.playerLevel[Constants.PRAYER] <= 0) {

			client.getActionSender().sendMessage(
					"You do not have enough prayer points.");
			resetAllPrayers();
			return;

		} else {

			clicked[prayerId] = !clicked[prayerId];

			switch (prayerId) {

			case THICK_SKIN:
				if (clicked[prayerId]) {
					attackPrayer = THICK_SKIN;
					clicked[ROCK_SKIN] = false;
					clicked[STEEL_SKIN] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					client.getActionSender().sendConfig(102, 0);
					client.getActionSender().sendConfig(101, 0);
					client.getActionSender().sendConfig(86, 0);
					client.getActionSender().sendConfig(92, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					attackPrayer = 0;
				}
				break;

			case BURST_OF_STRENGTH:
				if (clicked[prayerId]) {
					strengthPrayer = BURST_OF_STRENGTH;
					clicked[SUPERHUMAN_STRENGTH] = false;
					clicked[ULTIMATE_STRENGTH] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					clicked[SHARP_EYE] = false;
					clicked[HAWK_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					clicked[MYSTIC_LORE] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(102, 0);
					client.getActionSender().sendConfig(101, 0);
					client.getActionSender().sendConfig(87, 0);
					client.getActionSender().sendConfig(93, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);

				} else {
					strengthPrayer = 0;
				}
				break;

			case CLARITY_OF_THOUGHT:
				if (clicked[prayerId]) {
					attackPrayer = CLARITY_OF_THOUGHT;
					clicked[IMPROVED_REFLEXES] = false;
					clicked[INCREDIBLE_REFLEXES] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					clicked[SHARP_EYE] = false;
					clicked[HAWK_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					clicked[MYSTIC_LORE] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(102, 0);
					client.getActionSender().sendConfig(101, 0);
					client.getActionSender().sendConfig(88, 0);
					client.getActionSender().sendConfig(94, 0);
					client.getActionSender().sendConfig(90, 0);
					client.getActionSender().sendConfig(89, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					attackPrayer = 0;
				}
				break;

			case ROCK_SKIN:
				if (clicked[prayerId]) {
					defencePrayer = ROCK_SKIN;
					clicked[THICK_SKIN] = false;
					clicked[STEEL_SKIN] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					client.getActionSender().sendConfig(102, 0);
					client.getActionSender().sendConfig(101, 0);
					client.getActionSender().sendConfig(83, 0);
					client.getActionSender().sendConfig(92, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					defencePrayer = 0;
				}
				break;

			case SUPERHUMAN_STRENGTH:
				if (clicked[prayerId]) {
					strengthPrayer = SUPERHUMAN_STRENGTH;
					clicked[BURST_OF_STRENGTH] = false;
					clicked[ULTIMATE_STRENGTH] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					clicked[SHARP_EYE] = false;
					clicked[HAWK_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					clicked[MYSTIC_LORE] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(102, 0);
					client.getActionSender().sendConfig(101, 0);
					client.getActionSender().sendConfig(84, 0);
					client.getActionSender().sendConfig(93, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					strengthPrayer = 0;
				}
				break;

			case IMPROVED_REFLEXES:
				if (clicked[prayerId]) {
					attackPrayer = IMPROVED_REFLEXES;
					clicked[CLARITY_OF_THOUGHT] = false;
					clicked[BURST_OF_STRENGTH] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					clicked[SHARP_EYE] = false;
					clicked[HAWK_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					clicked[MYSTIC_LORE] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(102, 0);
					client.getActionSender().sendConfig(101, 0);
					client.getActionSender().sendConfig(85, 0);
					client.getActionSender().sendConfig(94, 0);
					client.getActionSender().sendConfig(90, 0);
					client.getActionSender().sendConfig(89, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					attackPrayer = 0;
				}
				break;

			case RAPID_RESTORE:
				clicked[RAPID_RESTORE] = false;
				client.getActionSender().sendConfig(RAPID_RESTORE, 0);
				break;

			case RAPID_HEAL:
				clicked[RAPID_HEAL] = false;
				client.getActionSender().sendConfig(RAPID_HEAL, 0);
				break;

			case PROTECT_ITEM:
				System.out.println(clicked[PROTECT_ITEM]);
				// clicked[PROTECT_ITEM] = false;
				client.getActionSender().sendConfig(PROTECT_ITEM, 0);
				break;

			case STEEL_SKIN:
				if (clicked[prayerId]) {
					defencePrayer = STEEL_SKIN;
					clicked[THICK_SKIN] = false;
					clicked[ROCK_SKIN] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					client.getActionSender().sendConfig(102, 0);
					client.getActionSender().sendConfig(101, 0);
					client.getActionSender().sendConfig(83, 0);
					client.getActionSender().sendConfig(86, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					defencePrayer = 0;
				}
				break;

			case ULTIMATE_STRENGTH:
				if (clicked[prayerId]) {
					strengthPrayer = ULTIMATE_STRENGTH;
					clicked[BURST_OF_STRENGTH] = false;
					clicked[SUPERHUMAN_STRENGTH] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					clicked[SHARP_EYE] = false;
					clicked[HAWK_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					clicked[MYSTIC_LORE] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(102, 0);
					client.getActionSender().sendConfig(101, 0);
					client.getActionSender().sendConfig(84, 0);
					client.getActionSender().sendConfig(87, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					strengthPrayer = 0;
				}
				break;

			case INCREDIBLE_REFLEXES:
				if (clicked[prayerId]) {
					attackPrayer = INCREDIBLE_REFLEXES;
					clicked[CLARITY_OF_THOUGHT] = false;
					clicked[IMPROVED_REFLEXES] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					clicked[SHARP_EYE] = false;
					clicked[HAWK_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					clicked[MYSTIC_LORE] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(102, 0);
					client.getActionSender().sendConfig(101, 0);
					client.getActionSender().sendConfig(85, 0);
					client.getActionSender().sendConfig(88, 0);
					client.getActionSender().sendConfig(90, 0);
					client.getActionSender().sendConfig(89, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					attackPrayer = 0;
				}
				break;

			case PROTECT_FROM_MAGIC:
				if (clicked[prayerId]) {
					headIconPrayer = PROTECT_FROM_MAGIC;
					clicked[PROTECT_FROM_MISSILES] = false;
					clicked[PROTECT_FROM_MELEE] = false;
					clicked[RETRIBUTION] = false;
					clicked[REDEMPTION] = false;
					clicked[SMITE] = false;
					client.getActionSender().sendConfig(96, 0);
					client.getActionSender().sendConfig(97, 0);
					client.getActionSender().sendConfig(98, 0);
					client.getActionSender().sendConfig(99, 0);
					client.getActionSender().sendConfig(100, 0);
					setPrayerIcon(2);
				} else {
					headIconPrayer = 0;
					setPrayerIcon(-1);
				}
				break;

			case PROTECT_FROM_MISSILES:
				if (clicked[prayerId]) {
					headIconPrayer = PROTECT_FROM_MISSILES;
					clicked[PROTECT_FROM_MAGIC] = false;
					clicked[PROTECT_FROM_MELEE] = false;
					clicked[RETRIBUTION] = false;
					clicked[REDEMPTION] = false;
					clicked[SMITE] = false;
					client.getActionSender().sendConfig(95, 0);
					client.getActionSender().sendConfig(97, 0);
					client.getActionSender().sendConfig(98, 0);
					client.getActionSender().sendConfig(99, 0);
					client.getActionSender().sendConfig(100, 0);
					setPrayerIcon(1);
				} else {
					headIconPrayer = 0;
					setPrayerIcon(-1);
				}
				break;

			case PROTECT_FROM_MELEE:
				if (clicked[prayerId]) {
					headIconPrayer = PROTECT_FROM_MELEE;
					clicked[PROTECT_FROM_MAGIC] = false;
					clicked[PROTECT_FROM_MISSILES] = false;
					clicked[RETRIBUTION] = false;
					clicked[REDEMPTION] = false;
					clicked[SMITE] = false;
					client.getActionSender().sendConfig(95, 0);
					client.getActionSender().sendConfig(96, 0);
					client.getActionSender().sendConfig(98, 0);
					client.getActionSender().sendConfig(99, 0);
					client.getActionSender().sendConfig(100, 0);
					setPrayerIcon(0);
				} else {
					headIconPrayer = 0;
					setPrayerIcon(-1);
				}
				break;

			case RETRIBUTION:
				if (clicked[prayerId]) {
					headIconPrayer = RETRIBUTION;
					clicked[PROTECT_FROM_MAGIC] = false;
					clicked[PROTECT_FROM_MISSILES] = false;
					clicked[PROTECT_FROM_MELEE] = false;
					clicked[REDEMPTION] = false;
					clicked[SMITE] = false;
					client.getActionSender().sendConfig(95, 0);
					client.getActionSender().sendConfig(96, 0);
					client.getActionSender().sendConfig(97, 0);
					client.getActionSender().sendConfig(99, 0);
					client.getActionSender().sendConfig(100, 0);
					setPrayerIcon(3);
				} else {
					headIconPrayer = 0;
					setPrayerIcon(-1);
				}
				break;

			case REDEMPTION:
				if (clicked[prayerId]) {
					headIconPrayer = REDEMPTION;
					clicked[PROTECT_FROM_MAGIC] = false;
					clicked[PROTECT_FROM_MISSILES] = false;
					clicked[PROTECT_FROM_MELEE] = false;
					clicked[RETRIBUTION] = false;
					clicked[SMITE] = false;
					client.getActionSender().sendConfig(95, 0);
					client.getActionSender().sendConfig(96, 0);
					client.getActionSender().sendConfig(97, 0);
					client.getActionSender().sendConfig(98, 0);
					client.getActionSender().sendConfig(100, 0);
					setPrayerIcon(5);
				} else {
					headIconPrayer = 0;
					setPrayerIcon(-1);
				}
				break;

			case SMITE:
				if (clicked[prayerId]) {
					headIconPrayer = SMITE;
					clicked[PROTECT_FROM_MAGIC] = false;
					clicked[PROTECT_FROM_MISSILES] = false;
					clicked[PROTECT_FROM_MELEE] = false;
					clicked[RETRIBUTION] = false;
					clicked[REDEMPTION] = false;
					client.getActionSender().sendConfig(95, 0);
					client.getActionSender().sendConfig(96, 0);
					client.getActionSender().sendConfig(97, 0);
					client.getActionSender().sendConfig(98, 0);
					client.getActionSender().sendConfig(99, 0);
					setPrayerIcon(4);
				} else {
					headIconPrayer = 0;
					setPrayerIcon(-1);
				}
				break;

			case CHIVALRY:
				if (clicked[prayerId]) {
					attackPrayer = CHIVALRY;
					defencePrayer = CHIVALRY;
					strengthPrayer = CHIVALRY;
					clicked[THICK_SKIN] = false;
					clicked[CLARITY_OF_THOUGHT] = false;
					clicked[BURST_OF_STRENGTH] = false;
					clicked[ROCK_SKIN] = false;
					clicked[IMPROVED_REFLEXES] = false;
					clicked[SUPERHUMAN_STRENGTH] = false;
					clicked[STEEL_SKIN] = false;
					clicked[INCREDIBLE_REFLEXES] = false;
					clicked[ULTIMATE_STRENGTH] = false;
					clicked[PIETY] = false;
					clicked[SHARP_EYE] = false;
					clicked[HAWK_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					clicked[MYSTIC_LORE] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(83, 0);
					client.getActionSender().sendConfig(84, 0);
					client.getActionSender().sendConfig(85, 0);
					client.getActionSender().sendConfig(86, 0);
					client.getActionSender().sendConfig(87, 0);
					client.getActionSender().sendConfig(88, 0);
					client.getActionSender().sendConfig(92, 0);
					client.getActionSender().sendConfig(93, 0);
					client.getActionSender().sendConfig(94, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					attackPrayer = 0;
					defencePrayer = 0;
					strengthPrayer = 0;
				}
				break;

			case PIETY:
				if (clicked[prayerId]) {
					attackPrayer = PIETY;
					defencePrayer = PIETY;
					strengthPrayer = PIETY;
					clicked[THICK_SKIN] = false;
					clicked[CLARITY_OF_THOUGHT] = false;
					clicked[BURST_OF_STRENGTH] = false;
					clicked[ROCK_SKIN] = false;
					clicked[IMPROVED_REFLEXES] = false;
					clicked[SUPERHUMAN_STRENGTH] = false;
					clicked[STEEL_SKIN] = false;
					clicked[INCREDIBLE_REFLEXES] = false;
					clicked[ULTIMATE_STRENGTH] = false;
					clicked[CHIVALRY] = false;
					clicked[SHARP_EYE] = false;
					clicked[HAWK_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					clicked[MYSTIC_LORE] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(83, 0);
					client.getActionSender().sendConfig(84, 0);
					client.getActionSender().sendConfig(85, 0);
					client.getActionSender().sendConfig(86, 0);
					client.getActionSender().sendConfig(87, 0);
					client.getActionSender().sendConfig(88, 0);
					client.getActionSender().sendConfig(92, 0);
					client.getActionSender().sendConfig(93, 0);
					client.getActionSender().sendConfig(94, 0);
					client.getActionSender().sendConfig(706, 0);
				} else {
					attackPrayer = 0;
					defencePrayer = 0;
					strengthPrayer = 0;
				}
				break;

			case SHARP_EYE:
				if (clicked[prayerId]) {
					attackPrayer = 0;
					strengthPrayer = 0;
					clicked[CLARITY_OF_THOUGHT] = false;
					clicked[BURST_OF_STRENGTH] = false;
					clicked[IMPROVED_REFLEXES] = false;
					clicked[SUPERHUMAN_STRENGTH] = false;
					clicked[INCREDIBLE_REFLEXES] = false;
					clicked[ULTIMATE_STRENGTH] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					clicked[HAWK_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					clicked[MYSTIC_LORE] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(84, 0);
					client.getActionSender().sendConfig(85, 0);
					client.getActionSender().sendConfig(87, 0);
					client.getActionSender().sendConfig(88, 0);
					client.getActionSender().sendConfig(93, 0);
					client.getActionSender().sendConfig(94, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					attackPrayer = 0;
					defencePrayer = 0;
					strengthPrayer = 0;
				}
				break;
			case HAWK_EYE:
				if (clicked[prayerId]) {
					attackPrayer = 0;
					strengthPrayer = 0;
					clicked[CLARITY_OF_THOUGHT] = false;
					clicked[BURST_OF_STRENGTH] = false;
					clicked[IMPROVED_REFLEXES] = false;
					clicked[SUPERHUMAN_STRENGTH] = false;
					clicked[INCREDIBLE_REFLEXES] = false;
					clicked[ULTIMATE_STRENGTH] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					clicked[SHARP_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					clicked[MYSTIC_LORE] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(84, 0);
					client.getActionSender().sendConfig(85, 0);
					client.getActionSender().sendConfig(87, 0);
					client.getActionSender().sendConfig(88, 0);
					client.getActionSender().sendConfig(93, 0);
					client.getActionSender().sendConfig(94, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					attackPrayer = 0;
					defencePrayer = 0;
					strengthPrayer = 0;
				}
				break;
			case EAGLE_EYE:
				if (clicked[prayerId]) {
					attackPrayer = 0;
					strengthPrayer = 0;
					clicked[CLARITY_OF_THOUGHT] = false;
					clicked[BURST_OF_STRENGTH] = false;
					clicked[IMPROVED_REFLEXES] = false;
					clicked[SUPERHUMAN_STRENGTH] = false;
					clicked[INCREDIBLE_REFLEXES] = false;
					clicked[ULTIMATE_STRENGTH] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					clicked[SHARP_EYE] = false;
					clicked[HAWK_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					clicked[MYSTIC_LORE] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(84, 0);
					client.getActionSender().sendConfig(85, 0);
					client.getActionSender().sendConfig(87, 0);
					client.getActionSender().sendConfig(88, 0);
					client.getActionSender().sendConfig(93, 0);
					client.getActionSender().sendConfig(94, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					attackPrayer = 0;
					defencePrayer = 0;
					strengthPrayer = 0;
				}
				break;
			case MYSTIC_WILL:
				if (clicked[prayerId]) {
					attackPrayer = 0;
					strengthPrayer = 0;
					clicked[CLARITY_OF_THOUGHT] = false;
					clicked[BURST_OF_STRENGTH] = false;
					clicked[IMPROVED_REFLEXES] = false;
					clicked[SUPERHUMAN_STRENGTH] = false;
					clicked[INCREDIBLE_REFLEXES] = false;
					clicked[ULTIMATE_STRENGTH] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					clicked[SHARP_EYE] = false;
					clicked[HAWK_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_LORE] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(84, 0);
					client.getActionSender().sendConfig(85, 0);
					client.getActionSender().sendConfig(87, 0);
					client.getActionSender().sendConfig(88, 0);
					client.getActionSender().sendConfig(93, 0);
					client.getActionSender().sendConfig(94, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					attackPrayer = 0;
					defencePrayer = 0;
					strengthPrayer = 0;
				}
				break;
			case MYSTIC_LORE:
				if (clicked[prayerId]) {
					attackPrayer = 0;
					strengthPrayer = 0;
					clicked[CLARITY_OF_THOUGHT] = false;
					clicked[BURST_OF_STRENGTH] = false;
					clicked[IMPROVED_REFLEXES] = false;
					clicked[SUPERHUMAN_STRENGTH] = false;
					clicked[INCREDIBLE_REFLEXES] = false;
					clicked[ULTIMATE_STRENGTH] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					clicked[SHARP_EYE] = false;
					clicked[HAWK_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					clicked[MYSTIC_MIGHT] = false;
					client.getActionSender().sendConfig(705, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(84, 0);
					client.getActionSender().sendConfig(85, 0);
					client.getActionSender().sendConfig(87, 0);
					client.getActionSender().sendConfig(88, 0);
					client.getActionSender().sendConfig(93, 0);
					client.getActionSender().sendConfig(94, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					attackPrayer = 0;
					defencePrayer = 0;
					strengthPrayer = 0;
				}
				break;

			case MYSTIC_MIGHT:
				if (clicked[prayerId]) {
					attackPrayer = 0;
					strengthPrayer = 0;
					clicked[CLARITY_OF_THOUGHT] = false;
					clicked[BURST_OF_STRENGTH] = false;
					clicked[IMPROVED_REFLEXES] = false;
					clicked[SUPERHUMAN_STRENGTH] = false;
					clicked[INCREDIBLE_REFLEXES] = false;
					clicked[ULTIMATE_STRENGTH] = false;
					clicked[CHIVALRY] = false;
					clicked[PIETY] = false;
					clicked[SHARP_EYE] = false;
					clicked[HAWK_EYE] = false;
					clicked[EAGLE_EYE] = false;
					clicked[MYSTIC_WILL] = false;
					client.getActionSender().sendConfig(703, 0);
					client.getActionSender().sendConfig(701, 0);
					client.getActionSender().sendConfig(704, 0);
					client.getActionSender().sendConfig(702, 0);
					client.getActionSender().sendConfig(700, 0);
					client.getActionSender().sendConfig(84, 0);
					client.getActionSender().sendConfig(85, 0);
					client.getActionSender().sendConfig(87, 0);
					client.getActionSender().sendConfig(88, 0);
					client.getActionSender().sendConfig(93, 0);
					client.getActionSender().sendConfig(94, 0);
					client.getActionSender().sendConfig(706, 0);
					client.getActionSender().sendConfig(707, 0);
				} else {
					attackPrayer = 0;
					defencePrayer = 0;
					strengthPrayer = 0;
				}
				break;
			}
		}
	}

}