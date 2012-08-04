package org.xythax.model.combat.content;

import org.xythax.content.controllers.Animation;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.map.TileManager;
import org.xythax.utils.CommonStrings;
import org.xythax.utils.Constants;

/**
 * @author Killamess
 */

public class Specials {

	public static int[][] SPECIAL = { { 5698, 1062, 252, 25, 1 }, // d dagger
																	// (p++)
			{ 5680, 1062, 252, 25, 1 }, // d dagger (p+)
			{ 1231, 1062, 252, 25, 1 }, // d dagger (p)
			{ 1215, 1062, 252, 25, 1 }, // d dagger
			{ 4587, 1872, 347, 60, 0 }, // d scimitar
			{ 4151, 1658, 341, 50, 0 }, // whip
			{ 7158, 3157, 559, 50, 0 }, // d2h
			{ 3204, 0440, 282, 25, 0 }, // d hally
			{ 1305, 1058, 248, 25, 0 }, // d long
			{ 1434, 1060, 251, 25, 0 }, // d mace
			{ 1377, 1056, 246, 100, 0 }, // dba
			{ 861, 1074, 250, 50, 1 }, // msb
			{ 11694, 7074, 1222, 50, 0 }, // ags
			{ 11696, 7073, 1223, 100, 0 }, // bgs
			{ 11698, 7071, 1220, 50, 0 }, // sgs
			{ 11700, 7070, 1221, 60, 0 }, // zgs
			{ 4153, 1667, 340, 50, 0 }, // g maul
	};

	public static int getSpecialAnimation(Client client) {
		for (int[] slot : SPECIAL) {
			if (client.playerEquipment[Constants.WEAPON] == slot[0])
				return slot[1];
		}
		return 0;
	}

	public static int getSpecialGraphics(Client client) {
		for (int[] slot : SPECIAL) {
			if (client.playerEquipment[Constants.WEAPON] == slot[0])
				return slot[2];
		}
		return 0;
	}

	public static int getDrainAmount(Client client) {
		for (int[] slot : SPECIAL) {
			if (client.playerEquipment[Constants.WEAPON] == slot[0])
				return slot[3];
		}
		return 25;
	}

	public static void specialAttack(Client client) {

		Entity target = null;

		if (client.getTarget() != null)
			target = client.getTarget();

		for (int[] slot : SPECIAL) {
			if (client.playerEquipment[Constants.WEAPON] == slot[0]) {
				if (slot[0] == 4151 && target != null) {
					Animation
							.createGraphic(target, slot[2], 0, slot[0] != 7158);
				} else {
					Animation
							.createGraphic(client, slot[2], 0, slot[0] != 7158);
				}
				Animation.createAnimation(client, slot[1]);
				client.setSpecialAmount(client.getSpecialAmount() - slot[3]);
				turnOff(client);
				break;
			}
		}
	}

	public static boolean doubleHit(Client client) {
		for (int[] slot : SPECIAL) {
			if (client.playerEquipment[Constants.WEAPON] == slot[0]) {
				if (slot[4] == 1)
					return true;
			}
		}
		return false;
	}

	public static void turnOff(Client client) {
		client.setUsingSpecial(false);
		client.getActionSender().sendConfig(301, 0);
		updateSpecialBar(client);
	}

	public static void activateSpecial(Client client) {
		if (getDrainAmount(client) > client.getSpecialAmount()) {
			client.getActionSender().sendMessage(
					CommonStrings.NO_SPECIAL_ENERGY);
			return;
		}
		if (client.usingSpecial())
			client.setUsingSpecial(false);
		else
			client.setUsingSpecial(true);

		switch (client.playerEquipment[Constants.WEAPON]) {
		case 1377: // dba
			client.setSpecialAmount(0);
			client.getActionAssistant().increaseStat(2, 20);
			client.getActionAssistant().forceText("Raarrrrrrgggggghhhhhhh!");
			Animation.createAnimation(client, getSpecialAnimation(client));
			Animation.createGraphic(client, getSpecialGraphics(client), 0,
					false);
			turnOff(client);
			break;
		case 4153: // g mual
			if (client.getTarget() != null
					&& TileManager
							.calculateDistance(client.getTarget(), client) < 2
					&& Life.isAlive(client.getTarget())) {
				Animation.createGraphic(client, 340, 0, true);
				Animation.addNewRequest(client, 1667, 0);
				client.setSpecialAmount(client.getSpecialAmount() - 50);
				Hits.runHitMelee(client, client.getTarget(), false);
				turnOff(client);
			} else if (client.getTarget() == null
					&& client.usingSpecial()
					|| client.getTarget() != null
					&& TileManager
							.calculateDistance(client.getTarget(), client) > 1
					&& client.usingSpecial()) {
				client.getActionSender()
						.sendMessage(
								"Warning: Since the maul's special is an instant attack, It will be wasted when used on a");
				client.getActionSender().sendMessage("first strike.");
			}
			break;
		}
		client.getActionSender().sendConfig(301, client.usingSpecial() ? 1 : 0);
	}

	public static void updateSpecialBar(Client client) {
		client.getActionSender().sendConfig2(300,
				client.getSpecialAmount() * 10);
	}

	public static void deathEvent(Client client) {
		client.setSpecialAmount(100);
		turnOff(client);
	}
}
