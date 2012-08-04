package org.xythax.utils;

import org.xythax.content.controllers.Graphics;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.combat.ranged.Ranged;

public class CombatUtils {

	public static double getRangeStrBonus(Client client) {
		double effectiveStrength = 0;
		effectiveStrength = ((client.playerLevel[Constants.RANGE])
				* getRangePrayerBonus(client) + getStyleBonus(client) + getVoidRangeBonus(client));
		return effectiveStrength;
	}

	public static double getVoidRangeBonus(Client client) {
		if (hasVoid(client)) {
			int rangedLevel = client.playerLevel[Constants.RANGE];
			return (rangedLevel / 5) + 1.6;
		}
		return 0;
	}

	public static double calculateRangeDamage(Client client, Entity target,
			boolean special) {

		double base = 0;

		double eff = getRangeStrBonus(client);
		double specialStr = getSpecialStr(client);

		double bonus = Ranged.getRangedStr(client);

		base = (5 + ((eff + 8) * (bonus + 64) / 64)) / 10;

		if (special)
			base = (base * specialStr);

		if (Ranged.isUsingEnchantedBolt(client)
				&& client.playerEquipment[Constants.WEAPON] == Ranged.RUNE_CROSSBOW) {
			if (Utils.random(7) == 1 && Math.floor(base) >= 1) {
				Graphics.addNewRequest(target, Ranged.getEnchantedGfx(client),
						0, 1);
				for (int i = 0; i < boltEffect.length; i++) {
					if (client.playerEquipment[Constants.ARROWS] == boltEffect[i][0])
						base = (base * boltEffect[i][1]);
				}
			}
		}
		return Math.floor(base);
	}

	public static double getRangePrayerBonus(Client client) {
		if (client.getPrayerHandler().clicked[20])
			return 1.05;
		else if (client.getPrayerHandler().clicked[21])
			return 1.1;
		else if (client.getPrayerHandler().clicked[22])
			return 1.15;
		return 1;
	}

	/**
	 * Is the player wearing void?
	 */
	public static boolean hasVoid(Client client) {
		return client.playerEquipment[Constants.HELM] == 11664
				&& client.playerEquipment[Constants.CHEST] == 8839
				&& client.playerEquipment[Constants.BOTTOMS] == 8840
				&& client.playerEquipment[Constants.GLOVES] == 8842;
	}

	public static final double[][] boltEffect = { { 9241, 1.05 },
			{ 9242, 1.05 }, { 9243, 1.15 }, { 9244, 1.45 }, { 9245, 1.15 }, };

	public static double calculateBaseDamage(Client client, boolean special) {
		double base = 0;
		double effective = getEffectiveStr(client);
		double specialBonus = getSpecialStr(client);
		double strengthBonus = client.getBonuses().bonus[Constants.STR_BONUS];
		base = (13 + effective + (strengthBonus / 8) + ((effective * strengthBonus) / 64)) / 10;
		if (special)
			base = (base * specialBonus);
		if (hasObsidianEffect(client) || hasVoid(client))
			base = (base * 1.2);

		return Math.floor(base);
	}

	public static double getEffectiveStr(Client client) {
		return ((client.playerLevel[Constants.STRENGTH]) * getPrayerStr(client))
				+ getStyleBonus(client);
	}

	public static int getStyleBonus(Client client) {
		return client.combatMode == 2 ? 3 : client.combatMode == 3 ? 1
				: client.combatMode == 4 ? 3 : 0;
	}

	public static double getPrayerStr(Client client) {
		if (client.getPrayerHandler().clicked[1])
			return 1.05;
		else if (client.getPrayerHandler().clicked[4])
			return 1.1;
		else if (client.getPrayerHandler().clicked[10])
			return 1.15;
		else if (client.getPrayerHandler().clicked[18])
			return 1.18;
		else if (client.getPrayerHandler().clicked[19])
			return 1.23;
		return 1;
	}

	public static final double[][] special = { { 5698, 1.1 }, { 5680, 1.1 },
			{ 1231, 1.1 }, { 1215, 1.1 }, { 3204, 1.1 }, { 1305, 1.15 },
			{ 1434, 1.45 }, { 11694, 1.34375 }, { 11696, 1.1825 },
			{ 11698, 1.075 }, { 11700, 1.075 }, { 861, 1.1 } };

	public static double getSpecialStr(Client client) {
		for (double[] slot : special) {
			if (client.playerEquipment[Constants.WEAPON] == slot[0])
				return slot[1];
		}
		return 1;
	}

	public static final int[] obsidianWeapons = { 746, 747, 6523, 6525, 6526,
			6527, 6528 };

	public static boolean hasObsidianEffect(Client client) {
		if (client.playerEquipment[Constants.AMULET] != 11128)
			return false;

		for (int weapon : obsidianWeapons) {
			if (client.playerEquipment[Constants.WEAPON] == weapon)
				return true;
		}
		return false;
	}

}
